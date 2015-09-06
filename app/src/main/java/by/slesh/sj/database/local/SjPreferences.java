package by.slesh.sj.database.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import by.slesh.sj.database.core.Database;

/**
 * Created by slesh on 9/6/15.
 */
public class SjPreferences {
    public static final String SJ_PROPERTIES = "sj.properties";

    public enum Key {
        HISTORY_CLEANING_FREQUENCY("1"),
        HISTORY_CLEAN_PERIOD("1"),
        IS_SHOW_SMS_IN_LIST(Boolean.TRUE.toString()),
        IS_SHOW_CALLS_IN_LIST(Boolean.TRUE.toString());

        private String defaultValue;

        Key(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    public static final String DEFAULT_PERIOD = "1";
    public static final String DEFAULT_HISTORY_CLEAN_PERIOD = "1";
    public static final Boolean DEFAULT_IS_SHOW_SMS_IN_LIST = Boolean.TRUE;
    public static final Boolean DEFAULT_IS_SHOW_CALLS_IN_LIST = Boolean.TRUE;

    public static final void initialize() {
        for (Key key : Key.values()) {
            set(key, key.defaultValue);

            Log.d("__________", key.defaultValue);
        }
    }

    public static final String get(Key key) {
        return getPreferences().getAll().get(key.name()).toString();
    }

    public static final void set(Key key, String value) {
        SharedPreferences.Editor editor = getPreferences().edit();
        editor.putString(key.name(), value);
        editor.commit();
    }

    private static final SharedPreferences getPreferences() {
        Context context = Database.getInstance().getContext();
        SharedPreferences preferences = context.getSharedPreferences(SJ_PROPERTIES, Context.MODE_PRIVATE);

        return preferences;
    }
}
