package by.slesh.sj.database.repository;

import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.core.Repository;
import by.slesh.sj.device.ContactLoader;

/**
 * Created by slesh on 05.09.2015.
 */
public class ContactRepository extends Repository<Contact, Integer> {
    private ContactLoader mContactLoader = new ContactLoader();
    private SmsRepository smsRepository = new SmsRepository();
    private CallRepository callRepository = new CallRepository();

    @Override
    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();
        try {
            for (Contact contact : super.findAll()) {
                contacts.add(mContactLoader.findOne(contact.getId()));
                contact.setQuantityCalls(callRepository.count(contact));
                contact.setQuantitySms(smsRepository.count(contact));
            }
        } catch (Exception e) {
            Log.e(TAG, "error during fetch contacts", e);
        }

        return contacts;
    }

    @Override
    public Integer delete(Contact contact) {
        smsRepository.deleteForContact(contact);
        callRepository.deleteForContact(contact);

        return super.delete(contact);
    }

    public Contact findByPhone(String phone) {
        try {
            String[] arguments = new String[]{phone};
            String[] columns = new String[]{getIdName()};
            connection = openConnection();
            cursor = connection.query(Contact.TABLE_NAME, columns, Contact.PHONE_FIELD + " like %?%", arguments, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                return mContactLoader.findOne(cursor.getInt(cursor.getColumnIndex(getIdName())));
            }
        } catch (Exception e) {
            Log.e(TAG, "error during search contact by phone " + phone, e);
        } finally {
            close();
        }
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
        contact.setPhone(cursor.getString(cursor.getColumnIndex(Contact.PHONE_FIELD)));

        return contact;
    }
}
