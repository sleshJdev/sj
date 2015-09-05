package by.slesh.sj.database.repository;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.core.Repository;
import by.slesh.sj.device.ContactLoader;

/**
 * Created by slesh on 05.09.2015.
 */
public class ContactRepository extends Repository {
    private ContactLoader mContactLoader;

    public ContactRepository(Database connector) {
        super(connector);

        mContactLoader = new ContactLoader(getContext());
    }

    @Override
    public Integer deleteAll() {
        return super.delete(Contact.TABLE_NAME, null, null);
    }

    private static final String DELETE_CONTACT_WHERE_CLAUSE =
            Contact._ID + " = ?";

    public Integer delete(Contact contact) {
        String[] arguments = new String[]{Integer.toString(contact.getId())};

        return super.delete(Contact.TABLE_NAME, DELETE_CONTACT_WHERE_CLAUSE, arguments);
    }

    public List<Contact> getAll() {
        List<Contact> contacts = new ArrayList<>();
        try {
            connection = openConnection();
            cursor = connection.query(Contact.TABLE_NAME, null, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                Integer id = cursor.getInt(cursor.getColumnIndex(Contact._ID));
                contacts.add(mContactLoader.getByIp(id));
                cursor.moveToNext();
            }
        } catch (Exception e) {

            Log.e(TAG, "error during fetch contacts", e);

        } finally {
            close();
        }

        return contacts;
    }

    private static final String FIND_CONTACT_BY_PHONE_WHERE_CLAUSE =
            Contact.PHONE_FIELD + " like %?%";

    public Contact findByPhone(String phone) {
        try {
            String[] arguments = new String[]{phone};
            String[] columns = new String[]{Contact._ID};
            connection = openConnection();
            cursor = connection.query(Contact.TABLE_NAME, columns, FIND_CONTACT_BY_PHONE_WHERE_CLAUSE, arguments, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                return mContactLoader.getByIp(cursor.getInt(cursor.getColumnIndex(Contact._ID)));
            }
        } catch (Exception e) {

            Log.e(TAG, "error during search contact by phone " + phone, e);

        } finally {
            close();
        }

        return null;
    }
}
