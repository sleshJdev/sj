package by.slesh.sj.database.core;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by slesh on 05.09.2015.
 */
public class Database {
    class DatabaseConnector extends SQLiteOpenHelper{
        DatabaseConnector(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        DatabaseConnector(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
            super(context, name, factory, version, errorHandler);
        }

        @Override
        public void onCreate(SQLiteDatabase connection) {

            Log.d(TAG, "creating database...");

            DatabaseSchemaCreator.initialize(connection);

            Log.d(TAG, "done");

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            Log.d(TAG, "upgrade database...");

        }
    }

    public static final String TAG = Database.class.getCanonicalName();

    public static final String NAME = "sj";
    public static final Integer VERSION = 1;

    private static Database mSelf;
    private DatabaseConnector mConnector;
    private Context mContext;

    private Database(Context context){
        this.mContext = context;
        this.mConnector = new DatabaseConnector(context, NAME, null, VERSION);
    }

    public static final void initialize(Context context){
        mSelf = new Database(context);
    }

    public static Database getInstance(){
        synchronized (Database.class){
            return mSelf;
        }
    }

    public static final void close(SQLiteDatabase connection) {
        try {
            if (connection != null && connection.isOpen()) {
                connection.close();
            }
        } catch (Exception e) {

            Log.e(TAG, "error during closing connection", e);

        }
    }

    public static final void close(Cursor cursor) {
        try {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        } catch (Exception e) {

            Log.e(TAG, "error during closing cursor", e);

        }
    }

    public Context getContext(){
        return mContext;
    }

    public SQLiteDatabase getConnection(){
        return mConnector.getReadableDatabase();
    }
}
