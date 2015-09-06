package by.slesh.sj.device;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Contact;

import static android.provider.ContactsContract.CommonDataKinds.Phone.*;

/**
 * Created by slesh on 05.09.2015.
 */
public class ContactLoader {
    private static final String TAG = ContactLoader.class.getCanonicalName();

    private static final String[] CONTACT_INFO_COLUMNS = new String[]{
            _ID, DISPLAY_NAME, DISPLAY_NAME, PHOTO_URI, NORMALIZED_NUMBER
    };

    public Contact findOne(Integer id) {
        Cursor cursor = null;
        try {
            Context context = Database.getInstance().getContext();
            cursor = context.getContentResolver().query(CONTENT_URI, CONTACT_INFO_COLUMNS, _ID + " = ?", new String[]{id.toString()}, null);
            cursor.moveToFirst();
            return readContact(cursor);
        } catch (Exception e) {

            Log.e(TAG, "error during get contact with id " + id, e);

        } finally {
            Database.close(cursor);
        }

        return null;
    }

    public List<Contact> findAll() {
        List<Contact> contacts = new ArrayList<>();
        Cursor cursor = null;
        try {
            Context context = Database.getInstance().getContext();
            cursor = context.getContentResolver().query(CONTENT_URI, CONTACT_INFO_COLUMNS, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                contacts.add(readContact(cursor));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            Log.d(TAG, "error during loading contacts from device", e);
        } finally {
            Database.close(cursor);
        }

        return contacts;
    }

    private static final Contact readContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setId(cursor.getInt(cursor.getColumnIndex(_ID)));
        contact.setName(cursor.getString(cursor.getColumnIndex(DISPLAY_NAME)));
        contact.setPhone(cursor.getString(cursor.getColumnIndex(NORMALIZED_NUMBER)));
        contact.setAvatarPath(cursor.getString(cursor.getColumnIndex(PHOTO_URI)));

        return contact;
    }
}
