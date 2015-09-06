package by.slesh.sj.database.repository.core;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.core.Model;

/**
 * Created by slesh on 05.09.2015.
 */
public abstract class Repository<T extends Model, K> {
    public static final String TAG = Repository.class.getCanonicalName();

    protected SQLiteDatabase connection;
    protected Cursor cursor;

    public abstract String getTableName();

    public abstract String getIdName();

    protected abstract T readModel(Cursor cursor);

    public SQLiteDatabase openConnection() {
        return Database.getInstance().getConnection();
    }

    private static final String COUNT_FIELD = "count(*)";
    private static final String COUNT_SMS_FOR_CONTACT_QUERY_FORMAT =
            " select " + COUNT_FIELD +
                    " from %s " +
                    " %s ";//where clause

    protected Integer count(String whereClause, String[] arguments) {
        Integer quantity = 0;
        try {
            connection = openConnection();
            cursor = connection.rawQuery(buildQuery(whereClause), arguments);
            cursor.moveToFirst();
            quantity = cursor.getInt(cursor.getColumnIndex(COUNT_FIELD));
        } finally {
            close();
        }

        return quantity;
    }

    private String buildQuery(String whereClause) {
        whereClause = whereClause == null ? "" : whereClause;
        return String.format(COUNT_SMS_FOR_CONTACT_QUERY_FORMAT, getTableName(), whereClause);
    }

    public Integer deleteAll() {
        try {
            connection = openConnection();
            return connection.delete(getTableName(), null, null);
        } catch (Exception e) {
            Log.e(TAG, "error during delete all records from " + getTableName(), e);
        } finally {
            close();
        }
        return 0;
    }

    public Integer delete(T item) {
        try {
            String[] arguments = new String[]{item.getId().toString()};
            connection = openConnection();
            return connection.delete(getTableName(), item.getIdName() + " = ?", arguments);
        } catch (Exception e) {
            Log.e(TAG, "error during delete item " + item, e);
        } finally {
            close();
        }
        return 0;
    }

    public Long save(T item) {
        try {
            connection = openConnection();
            return connection.insert(item.getTableName(), null, item.getContentValues());
        } catch (Exception e) {
            Log.e(TAG, "error during saving " + item, e);
        } finally {
            close();
        }
        return 0L;
    }

    public T findOne(K id) {
        try {
            connection = openConnection();
            cursor = connection.query(getTableName(), null, getIdName() + " = ?", new String[]{id.toString()}, null, null, null);
            return readModel(cursor);
        } catch (Exception e) {
            Log.d(TAG, "error during finding item with id: " + id + " in table " + getTableName(), e);
        } finally {
            close();
        }
        return null;
    }

    public List<T> findAll() {
        List<T> list = new ArrayList<>();
        try {
            connection = openConnection();
            cursor = connection.query(getTableName(), null, null, null, null, null, null);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(readModel(cursor));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            Log.e(TAG, "error during finding all items in table " + getTableName());
        } finally {
            close();
        }
        return list;
    }

    protected Integer delete(String whereClause, String[] whereArgs) {
        try {
            connection = openConnection();
            return connection.delete(getTableName(), whereClause, whereArgs);
        } catch (Exception e) {
            Log.e(TAG, "error during deleting records from " + getTableName());
        } finally {
            close();
        }

        return 0;
    }

    protected void close() {
        Database.close(cursor);
        Database.close(connection);
    }
}
