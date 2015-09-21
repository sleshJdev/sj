package by.slesh.sj.database.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Model;

/**
 * Created by slesh on 05.09.2015.
 */
abstract class Repository<T extends Model, K> {
    public static final String TAG = Repository.class.getCanonicalName();

    private Database database;
    protected SQLiteDatabase connection;
    protected Cursor cursor;

    protected Repository(Database database) {
        this.database = database;
    }

    public abstract String getTableName();

    public abstract String getIdName();

    protected abstract T readModel(Cursor cursor);

    protected SQLiteDatabase openConnection() {
        return database.getConnection();
    }

    private static final String COUNT_FIELD = "count(*)";
    private static final String COUNT_QUERY_FORMAT =
            " select " + COUNT_FIELD +
                    " from %s " +
                    " where %s ";//where clause

    protected Integer count(String whereClause, String[] arguments) {
        whereClause = whereClause == null ? "" : whereClause;
        String query = String.format(COUNT_QUERY_FORMAT, getTableName(), whereClause);
        Integer quantity = 0;
        Log.d(TAG, "counting records , query " + query + ", arguments: " + Arrays.toString(arguments));
        try {
            connection = openConnection();
            cursor = connection.rawQuery(query, arguments);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                quantity = cursor.getInt(cursor.getColumnIndex(COUNT_FIELD));
            }
        } finally {
            close();
        }
        Log.d(TAG, "is find " + quantity);
        return quantity;
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

    public Integer delete(K id) {
        try {
            connection = openConnection();
            return connection.delete(getTableName(), getIdName() + " = ?", new String[]{id.toString()});
        } catch (Exception e) {
            Log.e(TAG, "delete: error during delete item with id" + id + " from " + getTableName(), e);
        } finally {
            close();
        }
        return 0;
    }

    public Integer delete(String whereClause, String[] whereArgs) {
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

    public Long save(T item) {
        try {
            connection = openConnection();
            return connection.insert(item.getTableName(), null, item.getContentValues());
        } catch (Exception e) {
            Log.e(TAG, "save: error during saving " + item, e);
        } finally {
            close();
        }
        return 0L;
    }

    public T findOne(K id) {
        try {
            connection = openConnection();
            cursor = connection.query(getTableName(), null, getIdName() + " = ?", new String[]{id.toString()}, null, null, null);
            cursor.moveToFirst();
            if (!cursor.isAfterLast()) {
                return readModel(cursor);
            }
        } catch (Exception e) {
            Log.d(TAG, "error during finding item with id: " + id + " in table " + getTableName(), e);
        } finally {
            close();
        }
        Log.d(TAG, "findOne: contact with id: " + id + " not found");

        return null;
    }

    public List<T> findAll() {
        return findAll(null, null, null);
    }

    protected List<T> findAll(String where, String[] arguments, String orderBy) {
        List<T> list = new ArrayList<>();
        try {
            connection = openConnection();
            cursor = connection.query(getTableName(), null, where, arguments, null, null, orderBy);
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                list.add(readModel(cursor));
                cursor.moveToNext();
            }
        } catch (Exception e) {
            Log.e(TAG, "findAll: error during finding all items in table " + getTableName(), e);
        } finally {
            close();
        }
        return list;
    }

    protected void close() {
        Database.close(cursor);
        Database.close(connection);
    }
}
