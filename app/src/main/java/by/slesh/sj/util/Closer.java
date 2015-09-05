package by.slesh.sj.util;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by slesh on 05.09.2015.
 */
public class Closer {
    private static final String TAG = Closer.class.getCanonicalName();

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
}
