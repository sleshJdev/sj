package by.slesh.sj.database.repository.core;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Model;
import by.slesh.sj.util.Closer;

/**
 * Created by slesh on 05.09.2015.
 */
public abstract class Repository {
    public static final String TAG = Repository.class.getCanonicalName();

    private Database connector;
    protected SQLiteDatabase connection;
    protected Cursor cursor;

    public Repository(Database connector) {
        this.connector = connector;
    }

    protected Context getContext() {
        return connector.getContext();
    }

    public SQLiteDatabase openConnection() {
        return Database.getInstance().getConnection();
    }

    public abstract Integer deleteAll();

    protected Integer delete(String table, String whereClause, String[] whereArgs) {
        try {
            connection = openConnection();
            return connection.delete(table, whereClause, whereArgs);
        } catch (Exception e) {

            Log.e(TAG, "error during deleting records from " + table);

        } finally {
            close();
        }

        return 0;
    }

    public void save(Model model) {
        try {
            connection = openConnection();
            connection.insert(model.getTableName(), null, model.getContentValues());
        } finally {
            close();
        }
    }

    protected void close() {
        Closer.close(cursor);
        Closer.close(connection);
    }
}
