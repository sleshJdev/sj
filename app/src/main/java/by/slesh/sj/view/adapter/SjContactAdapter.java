package by.slesh.sj.view.adapter;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.slesh.sj.activity.R;
import by.slesh.sj.database.local.SjPreferences;
import by.slesh.sj.view.adapter.binder.SjContactBinder;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Contact;

import static by.slesh.sj.database.model.Contact.*;

import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.util.Converter;

/**
 * Created by slesh on 05.09.2015.
 */
public class SjContactAdapter extends SimpleAdapter {
    private static final String TAG = SjContactAdapter.class.getCanonicalName();

    private static final int RESOURCE = R.layout.item_selected_ontact;
    private static final String[] FROM = new String[]{ATTRIBUTE_NAME, ATTRIBUTE_AVATAR, ATTRIBUTE_STATUS, ATTRIBUTE_GRAPHIC, _ID};
    private static final int[] TO = new int[]{R.id.contact_name, R.id.contact_avatar, R.id.contact_status, R.id.contact_graphic, R.id.contact_delete_button};

    private List<Map<String, Object>> items;

    private SjContactAdapter(Context context, List<Map<String, Object>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.items = data;
    }

    public List<Map<String, Object>> getItems() {
        return items;
    }

    public void setItems(List<Map<String, Object>> items) {
        this.items = items;
    }

    public Map<String, Object> deleteItemAtPosition(int location) {
        return items.remove(location);
    }

    public static SjContactAdapter create(Context context) {
        final SjContactAdapter adapter = new SjContactAdapter(context, getData(context), RESOURCE, FROM, TO);
        adapter.setViewBinder(new SjContactBinder(null));

        return adapter;
    }

    private static final List<Map<String, Object>> getData(Context context) {
        ContactRepository contactRepository = new ContactRepository(new Database(context));
        List<Contact> contacts = contactRepository.findAll();
        List<Map<String, Object>> data = new ArrayList<>(contacts.size());
        for (Contact contact : contacts) {
            Map<String, Object> objectMap = Converter.toMap(contact);
            objectMap.put(_ID, contact.getId());
            data.add(objectMap);
        }

        return data;
    }

}
