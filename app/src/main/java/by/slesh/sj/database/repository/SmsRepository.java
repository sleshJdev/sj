package by.slesh.sj.database.repository;

import android.database.Cursor;

import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.model.Sms;
import by.slesh.sj.database.repository.core.Repository;

/**
 * Created by slesh on 05.09.2015.
 */
public class SmsRepository extends Repository<Sms, Integer> {
    private static final String TAG = SmsRepository.class.getCanonicalName();

    public Integer deleteForContact(Contact contact) {
        return super.delete(Sms.SENDER_ID_FIELD + " = ?", new String[]{contact.getId().toString()});
    }

    public Integer count(Contact contact) {
        String where = Sms.SENDER_ID_FIELD + " = ?";
        String[] arguments = new String[]{contact.getId().toString()};

        return super.count(where, arguments);
    }

    @Override
    public String getTableName() {
        return Sms.TABLE_NAME;
    }

    @Override
    public String getIdName() {
        return Sms._ID;
    }

    @Override
    protected Sms readModel(Cursor cursor) {
        Sms sms = new Sms();
        sms.setId(cursor.getInt(cursor.getColumnIndex(getIdName())));
        sms.setDate(cursor.getInt(cursor.getColumnIndex(Sms.DATE_FIELD)));
        sms.setSenderId(cursor.getInt(cursor.getColumnIndex(Sms.SENDER_ID_FIELD)));

        return sms;
    }
}
