package by.slesh.sj.adapter;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import by.slesh.sj.activity.R;
import by.slesh.sj.adapter.binder.PlainContactBinder;
import by.slesh.sj.adapter.util.StatusResolver;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.device.ContactLoader;
import by.slesh.sj.util.Converter;

import static by.slesh.sj.database.model.Contact.*;



/**
 * Created by slesh on 05.09.2015.
 */
public class PlainContactAdapterBuilder {
    private static final int RESOURCE = R.layout.item_plain_contact;
    private static final String[] FROM = new String[]{ATTRIBUTE_NAME, ATTRIBUTE_PHONE, ATTRIBUTE_AVATAR};
    private static final int[] TO = new int[]{R.id.plain_contact_name, R.id.plain_contact_phone, R.id.plain_contact_avatar};

    private static ContactLoader contactLoader;

    public static SimpleAdapter build(Context context){
        contactLoader = new ContactLoader(context);
        List<Map<String, Object>> data = getData();

        SimpleAdapter adapter = new SimpleAdapter(context, getData(), RESOURCE, FROM, TO);
        adapter.setViewBinder(new PlainContactBinder());

        return adapter;
    }

    private static final List<Map<String, Object>> getData(){
        List<Map<String, Object>> data = new ArrayList<>();
        List<Contact> contacts = contactLoader.getAll();
        for(Contact contact : contacts){
            data.add(Converter.toMap(contact));
        }

        return data;
    }
}
