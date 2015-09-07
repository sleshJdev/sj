package by.slesh.sj.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import by.slesh.sj.view.adapter.PlainContactAdapter;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.util.DateUtil;

/**
 * Created by slesh on 05.09.2015.
 */
public class PlainContactsActivity extends Activity implements AdapterView.OnItemClickListener, View.OnClickListener {
    private static final String TAG = PlainContactsActivity.class.getCanonicalName();

    private TextView okButton;

    private ContactRepository contactRepository;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plain_contacts);

        Log.d(TAG, "create activity");

        contactRepository = new ContactRepository(new Database(this));

        okButton = (TextView) findViewById(R.id.button_ok);
        okButton.setOnClickListener(this);

        ListView list = (ListView) findViewById(R.id.plain_contact_list);
        list.setAdapter(PlainContactAdapter.create(this));
        list.setOnItemClickListener(this);
    }

    private List<Contact> selectedContactsId = new ArrayList<>();

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> objectMap = (Map<String, Object>) parent.getItemAtPosition(position);
        Integer contactId = Integer.valueOf(objectMap.get(Contact._ID).toString());
        String phone = objectMap.get(Contact.PHONE_FIELD).toString();
        Contact contact = new Contact(contactId, phone);
        if (selectedContactsId.contains(contact)) {
            view.setBackgroundResource(R.drawable.selector_grey_item_background);
            selectedContactsId.remove(contact);

            Log.d(TAG, "unchecked contact: " + contact.getPhone() + ", id: " + contact.getId());

        } else {
            view.setBackgroundResource(R.drawable.selector_green_item_background);
            selectedContactsId.add(contact);

            Log.d(TAG, "checked contact: " + contact.getPhone() + ", id: " + contact.getId());

        }

        if (selectedContactsId.isEmpty()) {
            okButton.setVisibility(View.GONE);
        } else {
            okButton.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        Integer date = DateUtil.getUnixTime();
        for (Contact contact : selectedContactsId) {
            contact.setDate(date);
            Log.d(TAG, "save contact to sj " + contact);
            contactRepository.save(contact);
        }
        Log.d(TAG, "user select " + selectedContactsId.size() + ". move to main activity");
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "resume activity");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "destroy activity");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "stop activity");
    }
}
