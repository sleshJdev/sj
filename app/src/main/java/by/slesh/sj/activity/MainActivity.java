package by.slesh.sj.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;
import java.util.Map;

import by.slesh.sj.view.adapter.binder.SjContactBinder;
import by.slesh.sj.view.adapter.SjContactAdapter;
import by.slesh.sj.callback.Action;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.local.SjPreferences;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.device.CallListener;
import by.slesh.sj.view.animation.ScrollListenerAnimator;
import by.slesh.sj.util.DateUtil;

public class MainActivity extends UpdatableActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SjContactBinder.DeleteContactListener, Action {
    public static final String TAG = MainActivity.class.getCanonicalName();

    private ListView mSjContactsList;
    private SjContactAdapter mSjContactAdapter;
    private ContactRepository mContactRepository;

    public MainActivity() {
        Log.d(TAG, "MainActivity: constructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: create activity");
        SjPreferences.initialize(this);
//        this.deleteDatabase(Database.NAME);
        findViewById(R.id.add_button).setOnClickListener(this);
        findViewById(R.id.settings_button).setOnClickListener(this);
        mSjContactsList = (ListView) findViewById(R.id.selected_contact_list_view);
        mSjContactsList.setOnScrollListener(new ScrollListenerAnimator(findViewById(R.id.main_texts)));

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new CallListener(this), PhoneStateListener.LISTEN_CALL_STATE);

        Database database = new Database(this);
        mContactRepository = new ContactRepository(database);

        setAction(this);
    }

    private void updateSjContactsList() {
        Log.d(TAG, "updateSjContactsList: updating...");
        mSjContactAdapter = SjContactAdapter.create(this);
        mSjContactAdapter.setViewBinder(new SjContactBinder(this));
        mSjContactsList.setAdapter(mSjContactAdapter);
        mSjContactsList.setOnItemClickListener(this);
        mSjContactAdapter.notifyDataSetChanged();
        Log.d(TAG, "updateSjContactsList: update is done at " + DateUtil.getDate("MM-dd-yyyy hh:mm:ss"));
    }

    @Override
    public void perform(Object data) {
        updateSjContactsList();
    }

    @Override
    public void performDeleting(Integer id) {
        Log.d(TAG, "performDeleting: click on delete contact button. contact id:" + id);
        List<Map<String, Object>> items = mSjContactAdapter.getItems();
        Integer position = 0;
        for (Map<String, Object> item : items) {
            if (id.equals(item.get(Contact._ID))) {
                Integer number = Integer.valueOf(item.get(Contact._ID).toString());
                mSjContactAdapter.deleteItemAtPosition(position);
                mContactRepository.delete(number);
                mSjContactsList.invalidateViews();
                Log.d(TAG, "performDeleting: contact " + item + " has been deleted from sj");
                return;
            }
            ++position;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, Object> selectedContact = (Map<String, Object>) mSjContactsList.getItemAtPosition(position);
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra(Contact._ID, Integer.valueOf(selectedContact.get(Contact._ID).toString()));
        Log.d(TAG, "onItemClick: view profile of contact " + selectedContact + ", move to profile activity...");
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_button:
                startActivity(new Intent(this, PlainContactsActivity.class));
                break;
            case R.id.settings_button:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }
}
