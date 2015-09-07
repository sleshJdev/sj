package by.slesh.sj.database.local;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import by.slesh.sj.util.DateUtil;

/**
 * Created by slesh on 9/6/15.
 */
public class SjPreferences {
    public static final String SJ_PROPERTIES = "sj.properties";

    public enum Key {
        LAST_UPDATE_TIME("0"),
        HISTORY_PERIOD("1"),
        HISTORY_CLEAN_PERIOD("1"),
        IS_SHOW_SMS_IN_LIST(Boolean.TRUE.toString()),
        IS_SHOW_CALLS_IN_LIST(Boolean.TRUE.toString());

        private String defaultValue;

        public String getDefault(){
            return defaultValue;
        }

        Key(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    public static final String DEFAULT_PERIOD = "1";
    public static final String DEFAULT_HISTORY_CLEAN_PERIOD = "1";
    public static final Boolean DEFAULT_IS_SHOW_SMS_IN_LIST = Boolean.TRUE;
    public static final Boolean DEFAULT_IS_SHOW_CALLS_IN_LIST = Boolean.TRUE;

    public static final void initialize(Context context) {
        for (Key key : Key.values()) {
            set(context, key, key.defaultValue);
        }
    }

    public static final String get(Context context, Key key) {
        return getPreferences(context).getAll().get(key.name()).toString();
    }

    public static final void set(Context context,Key key, String value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key.name(), value);
        editor.commit();
    }

    private static final SharedPreferences getPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SJ_PROPERTIES, Context.MODE_PRIVATE);

        return preferences;
    }
}
