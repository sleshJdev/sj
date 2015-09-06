package by.slesh.sj.adapter.buider;

import android.util.Log;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.slesh.sj.activity.R;
import by.slesh.sj.adapter.binder.SjContactBinder;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.util.Converter;

import static by.slesh.sj.database.model.Contact.ATTRIBUTE_AVATAR;
import static by.slesh.sj.database.model.Contact.ATTRIBUTE_GRAPHIC;
import static by.slesh.sj.database.model.Contact.ATTRIBUTE_NAME;
import static by.slesh.sj.database.model.Contact.ATTRIBUTE_STATUS;
import static by.slesh.sj.database.model.Contact._ID;

/**
 * Created by slesh on 05.09.2015.
 */
public class SjContactAdapterBuilder {
    private static final String TAG = SjContactAdapterBuilder.class.getCanonicalName();

    private static final int RESOURCE = R.layout.item_selected_ontact;
    private static final String[] FROM = new String[]{ATTRIBUTE_NAME, ATTRIBUTE_AVATAR, ATTRIBUTE_STATUS, ATTRIBUTE_GRAPHIC, _ID};
    private static final int[] TO = new int[]{R.id.contact_name, R.id.contact_avatar, R.id.contact_status, R.id.contact_graphic, R.id.contact_delete_button};

    private static ContactRepository contactRepository = new ContactRepository();

    private SjContactAdapterBuilder() {
    }

    public static SimpleAdapter build() {
        SimpleAdapter adapter = new SimpleAdapter(Database.getInstance().getContext(), getData(), RESOURCE, FROM, TO);
        adapter.setViewBinder(new SjContactBinder());

        return adapter;
    }

    private static final List<? extends Map<String, ?>> getData() {
        List<Contact> contacts = contactRepository.findAll();
        List<Map<String, Object>> data = new ArrayList<>(contacts.size());
        for (Contact contact : contacts) {
            Map<String, Object> objectMap = Converter.toMap(contact);
            objectMap.put(_ID, contact);
            data.add(objectMap);

            Log.d(TAG, "from database: " + contact);
        }

        return data;
    }

}
