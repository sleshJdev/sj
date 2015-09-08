package by.slesh.sj.database.local;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.concurrent.TimeUnit;

import by.slesh.sj.database.model.Contact;

/**
 * Created by slesh on 9/6/15.
 */
public class SjPreferences {
    public static final String SJ_PROPERTIES = "sj.properties";

    public enum Key {
        LAST_UPDATE_TIME("0"),
        MAIN_PERIOD(Long.toString(TimeUnit.MINUTES.toSeconds(1))),
        HISTORY_CLEAN_PERIOD(Long.toString(TimeUnit.MINUTES.toSeconds(1))),
        IS_SHOW_SMS_IN_LIST(Boolean.TRUE.toString()),
        IS_SHOW_CALLS_IN_LIST(Boolean.TRUE.toString());
//        LAST_UPDATE_TIME("0"),
//        MAIN_PERIOD(Long.toString(TimeUnit.HOURS.toSeconds(1))),
//        HISTORY_CLEAN_PERIOD(Long.toString(TimeUnit.HOURS.toSeconds(1))),
//        IS_SHOW_SMS_IN_LIST(Boolean.TRUE.toString()),
//        IS_SHOW_CALLS_IN_LIST(Boolean.TRUE.toString());

        private String defaultValue;

        public String getDefault() {
            return defaultValue;
        }

        Key(String defaultValue) {
            this.defaultValue = defaultValue;
        }
    }

    public static Integer getInteger(Context context, Key key) {
        return Integer.valueOf(getPreferences(context).getString(key.toString(), key.getDefault()));
    }

    public static Boolean getBoolean(Context context, Key key) {
        return Boolean.valueOf(getPreferences(context).getString(key.toString(), key.getDefault()));
    }

    public static final void initialize(Context context) {
        for (Key key : Key.values()) {
            set(context, key, key.defaultValue);
        }
    }

    public static final void set(Context context, Key key, String value) {
        SharedPreferences.Editor editor = getPreferences(context).edit();
        editor.putString(key.name(), value);
        editor.commit();
    }

    private static final SharedPreferences getPreferences(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(SJ_PROPERTIES, Context.MODE_PRIVATE);
        return preferences;
    }
}
