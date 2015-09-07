package by.slesh.sj.database.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.local.StatusResolver;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.device.ContactLoader;

/**
 * Created by slesh on 05.09.2015.
 */
public class ContactRepository extends Repository<Contact, Integer> {
    private ContactLoader mContactLoader;
    private SmsRepository mSmsRepository;
    private CallRepository mCallRepository;

    public ContactRepository(Database database) {
        super(database);
        mContactLoader = new ContactLoader(database.getContext());
        mSmsRepository = new SmsRepository(database);
        mCallRepository = new CallRepository(database);
    }

    public void updateAll() {
        try {
            List<Contact> contacts = super.findAll();
            if (contacts != null && contacts.size() > 0) {
                final ContentValues values = new ContentValues();
                final String whereFormat = Contact._ID + " = ?";
                final String[] arguments = new String[1];
                connection = openConnection();
                for (Contact contact : contacts) {
                    String status = StatusResolver.getStatus(contact.getQuantityCalls() + contact.getQuantitySms()).getName();
                    values.put(Contact.STATUS_FIELD, status);
                    arguments[0] = contact.getId().toString();
                    connection.update(getTableName(), values, whereFormat, arguments);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "updateAll: error during updating contacts", e);
        } finally {
            close();
        }
    }

    private Contact join(Contact sjContact, Contact notSjContact) {
        sjContact.setPhone(notSjContact.getPhone());
        sjContact.setAvatarPath(notSjContact.getAvatarPath());
        sjContact.setName(notSjContact.getName());

        return sjContact;
    }

    @Override
    public Contact findOne(Integer id) {
        Contact sjContact = super.findOne(id);
        sjContact = join(sjContact, mContactLoader.findOne(id));
        sjContact.setCalls(mCallRepository.findAllForContact(sjContact));
        sjContact.setSms(mSmsRepository.findAllForContact(sjContact));

        Log.d(TAG, "findOne: contact:" + sjContact);

        return sjContact;
    }

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();
        try {
            for (Contact sjContact : super.findAll()) {
                Contact contact = join(sjContact, mContactLoader.findOne(sjContact.getId()));
                contact.setCalls(mCallRepository.findAllForContact(contact));
                contact.setSms(mSmsRepository.findAllForContact(contact));
                contacts.add(contact);
            }
        } catch (Exception e) {
            Log.e(TAG, "error during fetch contacts", e);
        }

        return contacts;
    }

    @Override
    public Integer delete(Integer id) {
        Log.d(TAG, "delete: delete contact with id: " + id);
        mSmsRepository.deleteForContact(id);
        mCallRepository.deleteForContact(id);
        return super.delete(id);
    }

    public Contact findByPhone(String phone) {
        try {
            String where = Contact.PHONE_FIELD + " like ?";
            String[] arguments = new String[]{String.format("%%%s%%", phone)};
            String[] columns = new String[]{getIdName()};
            connection = openConnection();
            cursor = connection.query(Contact.TABLE_NAME, columns, where, arguments, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                Contact contact = findOne(cursor.getInt(cursor.getColumnIndex(getIdName())));
                Log.d(TAG, "findByPhone: found:" + contact + ",where:" + where + ",columns:" + Arrays.toString(columns) + ",arguments:" + Arrays.toString(arguments));
                return contact;
            }
        } catch (Exception e) {
            Log.e(TAG, "findByPhone: error during search contact by phone " + phone, e);
        } finally {
            close();
        }
        Log.d(TAG, "findByPhone: not found contact with phone " + phone);
        return null;
    }

    @Override
    public String getTableName() {
        return Contact.TABLE_NAME;
    }

    @Override
    public String getIdName() {
        return Contact._ID;
    }

    @Override
    protected Contact readModel(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getInt(cursor.getColumnIndex(getIdName())));
        contact.setDate(cursor.getInt(cursor.getColumnIndex(Contact.DATE_FIELD)));
        contact.setPhone(cursor.getString(cursor.getColumnIndex(Contact.PHONE_FIELD)));
        contact.setStatus(cursor.getString(cursor.getColumnIndex(Contact.STATUS_FIELD)));

        return contact;
    }
}
