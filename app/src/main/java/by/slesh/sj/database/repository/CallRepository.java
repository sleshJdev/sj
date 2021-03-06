<<<<<<< HEAD
package by.slesh.sj.database.repository;

import android.database.Cursor;

import by.slesh.sj.database.model.Call;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.core.Repository;

/**
 * Created by slesh on 05.09.2015.
 */
public class CallRepository extends Repository<Call, Integer> {
    public Integer deleteForContact(Contact contact) {
        return super.delete(Call.WHO_CALLED_ID_FIELD + " = ?", new String[]{contact.getId().toString()});
    }

    public Integer count(Contact contact) {
        String where = Call.WHO_CALLED_ID_FIELD + " = ?";
        String[] arguments = new String[]{contact.getId().toString()};

        return super.count(where, arguments);
    }

    @Override
    public String getTableName() {
        return Call.TABLE_NAME;
    }

    @Override
    public String getIdName() {
        return Call._ID;
    }

    @Override
    protected Call readModel(Cursor cursor) {
        Call call = new Call();
        call.setId(cursor.getInt(cursor.getColumnIndex(getIdName())));
        call.setDate(cursor.getInt(cursor.getColumnIndex(Call.DATE_FIELD)));
        call.setWhoCalledId(cursor.getInt(cursor.getColumnIndex(Call.WHO_CALLED_ID_FIELD)));

        return call;
    }
}
=======
package by.slesh.sj.database.repository;

import android.database.Cursor;

import java.util.List;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Call;
import by.slesh.sj.database.model.Contact;

/**
 * Created by slesh on 05.09.2015.
 */
public class CallRepository extends Repository<Call, Integer> {
    public CallRepository(Database database) {
        super(database);
    }

    public List<Call> findAllForContact(Contact contact) {
        String where = Call.WHO_CALLED_ID_FIELD + " = ?";
        String[] arguments = new String[]{contact.getId().toString()};
        String orderBy = Call.DATE_FIELD + " ASC";

        return super.findAll(where, arguments, orderBy);
    }

    @Override
    protected Call readModel(Cursor cursor) {
        Call call = new Call();
        call.setId(cursor.getInt(cursor.getColumnIndex(getIdName())));
        call.setDate(cursor.getInt(cursor.getColumnIndex(Call.DATE_FIELD)));
        call.setWhoCalledId(cursor.getInt(cursor.getColumnIndex(Call.WHO_CALLED_ID_FIELD)));

        return call;
    }

    @Override
    public String getTableName() {
        return Call.TABLE_NAME;
    }

    @Override
    public String getIdName() {
        return Call._ID;
    }
}
>>>>>>> 49d28eb0877fb4d02ca30ad13e6abeecdf62e99e
