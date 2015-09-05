package by.slesh.sj.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.slesh.sj.activity.R;
import by.slesh.sj.adapter.binder.SjContactBinder;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Contact;

import static by.slesh.sj.database.model.Contact.*;

import by.slesh.sj.database.repository.CallRepository;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.database.repository.SmsRepository;
import by.slesh.sj.util.Converter;

/**
 * Created by slesh on 05.09.2015.
 */
public class SjContactAdapter extends SimpleAdapter {
    private static final String TAG = SjContactAdapter.class.getCanonicalName();

    private static ContactRepository contactRepository;
    private static SmsRepository smsRepository;
    private static CallRepository callRepository;

    private static final int RESOURCE = R.layout.item_selected_ontact;
    private static final String[] FROM = new String[]{ATTRIBUTE_NAME, ATTRIBUTE_AVATAR, ATTRIBUTE_STATUS, ATTRIBUTE_GRAPHIC, _ID};
    private static final int[] TO = new int[]{R.id.contact_name, R.id.contact_avatar, R.id.contact_status, R.id.contact_graphic, R.id.contact_delete_button};

    private SjContactAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public static SimpleAdapter build(Context context) {
        initializeRepositories(new Database(context));

        SimpleAdapter adapter = new SimpleAdapter(context, getData(), RESOURCE, FROM, TO);
        adapter.setViewBinder(new SjContactBinder(contactRepository, callRepository, smsRepository));

        return adapter;
    }

    private static void initializeRepositories(Database database) {
        contactRepository = new ContactRepository(database);
        smsRepository = new SmsRepository(database);
        callRepository = new CallRepository(database);
    }

    private static final List<? extends Map<String, ?>> getData() {
        List<Contact> contacts = contactRepository.getAll();
        List<Map<String, Object>> data = new ArrayList<>(contacts.size());
        for (Contact contact : contacts) {
            contact.setQuantityCalls(callRepository.countCallsForContact(contact));
            contact.setQuantitySms(smsRepository.countSmsForContact(contact));
            Map<String, Object> objectMap = Converter.toMap(contact);
            objectMap.put(_ID, contact);
            data.add(objectMap);

            Log.d(TAG, "from database: " + contact);
        }

        return data;
    }

}
