package by.slesh.sj.view.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.slesh.sj.activity.R;
import by.slesh.sj.util.SjUtil;
import by.slesh.sj.view.adapter.binder.PlainContactBinder;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.device.ContactLoader;

import static by.slesh.sj.database.model.Contact.*;


/**
 * Created by slesh on 05.09.2015.
 */
public class PlainContactAdapter extends SimpleAdapter {
    private static final String TAG = PlainContactAdapter.class.getCanonicalName();

    private static final int RESOURCE = R.layout.item_plain_contact;
    private static final String[] FROM = new String[]{ATTRIBUTE_NAME, ATTRIBUTE_PHONE, ATTRIBUTE_AVATAR};
    private static final int[] TO = new int[]{R.id.plain_contact_name, R.id.plain_contact_phone, R.id.plain_contact_avatar};

    private PlainContactAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public static PlainContactAdapter create(Context context) {
        ContactLoader contactLoader = new ContactLoader(context);
        ContactRepository contactRepository = new ContactRepository(new Database(context));
        List<Map<String, Object>> data = getData(contactLoader, contactRepository);
        PlainContactAdapter adapter = new PlainContactAdapter(context, data, RESOURCE, FROM, TO);
        adapter.setViewBinder(new PlainContactBinder());

        return adapter;
    }

    private static final List<Map<String, Object>> getData(ContactLoader contactLoader, ContactRepository contactRepository) {
        List<Map<String, Object>> data = new ArrayList<>();
        List<Contact> allContacts = contactLoader.findAll();
        List<Contact> sjContacts = contactRepository.findAll();

        for (Contact contact : allContacts) {
            if (SjUtil.isBlank(contact.getPhone())) {
                Log.d(TAG, "contact will be skipped, don't have phone. " + contact);
                continue;
            }
            if (!sjContacts.contains(contact)) {
                data.add(SjUtil.toMap(contact));
            }
        }

        return data;
    }
}
