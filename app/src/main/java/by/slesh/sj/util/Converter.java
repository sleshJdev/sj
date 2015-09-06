package by.slesh.sj.util;

import android.content.ContentValues;

import java.util.HashMap;
import java.util.Map;

import by.slesh.sj.database.local.StatusResolver;
import by.slesh.sj.database.model.Contact;

/**
 * Created by slesh on 05.09.2015.
 */
public final class Converter {
    public static final Map<String, Object> toMap(ContentValues values){
        Map<String, Object> map = new HashMap<>();
        for(String key : values.keySet()){
            map.put(key, values.get(key));
        }

        return map;
    }

    public static final Map<String, Object> toMap(Contact contact){
        Integer points = contact.getQuantityCalls() + contact.getQuantitySms();
        Map<String, Object> map = new HashMap<>();
        map.put(Contact.ATTRIBUTE_ID, contact.getId());
        map.put(Contact.ATTRIBUTE_NAME, contact.getName());
        map.put(Contact.ATTRIBUTE_PHONE, contact.getPhone());
        map.put(Contact.ATTRIBUTE_AVATAR, contact.getAvatarPath());
        map.put(Contact.ATTRIBUTE_STATUS, StatusResolver.getStatusName(points));
        map.put(Contact.ATTRIBUTE_GRAPHIC, StatusResolver.getStatusGraphic(points));

        return map;
    }
}
