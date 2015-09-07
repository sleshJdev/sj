package by.slesh.sj.database.repository;

import android.database.Cursor;

import java.util.List;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.model.Sms;

/**
 * Created by slesh on 05.09.2015.
 */
public class SmsRepository extends Repository<Sms, Integer> {
    private static final String TAG = SmsRepository.class.getCanonicalName();

    public SmsRepository(Database database) {
        super(database);
    }

    public List<Sms> findAllForContact(Contact contact) {
        return super.findAll(Sms.SENDER_ID_FIELD + " = ?", new String[]{contact.getId().toString()}, Sms.DATE_FIELD + " ASC");
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
