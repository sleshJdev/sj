package by.slesh.sj.util;

import android.content.ContentValues;
import android.text.format.DateFormat;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import by.slesh.sj.database.local.StatusResolver;
import by.slesh.sj.database.model.Contact;

/**
 * Created by slesh on 05.09.2015.
 */
public final class SjUtil {
    public static Integer getValueOrEmpty(Integer value) {
        return value == null ? 0 : value;
    }

    public static String getValueOrEmpty(String value) {
        return isBlank(value) ? "" : value;
    }

    public static final Map<String, Object> toMap(ContentValues values) {
        Map<String, Object> map = new HashMap<>();
        for (String key : values.keySet()) {
            map.put(key, values.get(key));
        }

        return map;
    }

    public static final Map<String, Object> toMap(Contact contact) {
        Integer points = contact.getQuantityCalls() + contact.getQuantitySms();
        Map<String, Object> map = new HashMap<>();
        map.put(Contact.ATTRIBUTE_ID, contact.getId());
        map.put(Contact.ATTRIBUTE_NAME, contact.getName());
        map.put(Contact.ATTRIBUTE_DATE, contact.getDate());
        map.put(Contact.ATTRIBUTE_PHONE, contact.getPhone());
        map.put(Contact.ATTRIBUTE_AVATAR, contact.getAvatarPath());
        map.put(Contact.ATTRIBUTE_STATUS, contact.getStatus());
        map.put(Contact.ATTRIBUTE_GRAPHIC, contact.getGraphic());

        return map;
    }

    public static final Integer getUnixTime() {
        return (int) (new Date().getTime() / 1000L);
    }

    public static final String getDate(String s, long millisecond) {
        return DateFormat.format("MM-dd-yyyy hh:mm", new Date(millisecond)).toString();
    }

    public static final String getDate(String format) {
        return DateFormat.format(format, new Date(new Date().getTime())).toString();
    }

    public static final boolean isBlank(String s) {
        int strLen;
        if (s == null || (strLen = s.length()) == 0) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if ((Character.isWhitespace(s.charAt(i)) == false)) {
                return false;
            }
        }
        return true;
    }
}
