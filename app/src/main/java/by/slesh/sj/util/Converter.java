package by.slesh.sj.util;

import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;

import by.slesh.sj.database.local.StatusResolver;
import by.slesh.sj.database.local.model.ContactActivity;
import by.slesh.sj.database.model.Contact;

/**
 * Created by slesh on 05.09.2015.
 */
public final class Converter {
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
        map.put(Contact.ATTRIBUTE_GRAPHIC, StatusResolver.getStatus(points).getGraphic());

        return map;
    }

    public static Map<String, Object> toMap(ContactActivity activity) {
        Integer points = activity.getQuantityCalls() + activity.getQuantitySms();
        Map<String, Object> map = new HashMap<>();
        map.put(Contact.ATTRIBUTE_DATE, activity.beginAsString());
        map.put(Contact.ATTRIBUTE_QUANTITY_SMS, activity.getQuantitySms());
        map.put(Contact.ATTRIBUTE_QUANTITY_CALLS, activity.getQuantityCalls());
        map.put(Contact.ATTRIBUTE_STATUS, activity.getStatus().getName());

        return map;
    }
}
