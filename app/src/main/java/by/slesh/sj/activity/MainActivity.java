package by.slesh.sj.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import by.slesh.sj.database.model.Call;
import by.slesh.sj.database.model.Sms;
import by.slesh.sj.database.repository.CallRepository;
import by.slesh.sj.database.repository.SmsRepository;
import by.slesh.sj.util.SjUtil;
import by.slesh.sj.view.adapter.binder.SjContactBinder;
import by.slesh.sj.view.adapter.SjContactAdapter;
import by.slesh.sj.callback.Action;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.local.SjPreferences;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.device.CallListener;
import by.slesh.sj.view.animation.ScrollListenerAnimator;

public class MainActivity extends PeriodicUpdatableActivity implements View.OnClickListener, AdapterView.OnItemClickListener, SjContactBinder.DeleteContactListener, Action {
    public static final String TAG = MainActivity.class.getCanonicalName();

    private ListView mSjContactsList;
    private SjContactAdapter mSjContactAdapter;
    private ContactRepository mContactRepository;
    private SmsRepository mSmsRepository;
    private CallRepository mCallRepository;

    private Timer mDeleteTimer;
    private Handler mDeleteHandler = new Handler();

    private class HistoryCleaner implements Runnable {
        long lastTime = 0;

        @Override
        public void run() {
            long period = SjPreferences.getInteger(getApplicationContext(), SjPreferences.Key.MAIN_PERIOD);
            long nowTime = SjUtil.getUnixTime();
            if (nowTime - lastTime > period) {
                Integer periodSeconds = (int) TimeUnit.HOURS.toSeconds(Long.valueOf(period));
                Integer date = SjUtil.getUnixTime() - periodSeconds;
                Integer deletedSms = mSmsRepository.delete(Sms.DATE_FIELD + " > ?", new String[]{date.toString()});
                Integer deletedCalls = mCallRepository.delete(Call.DATE_FIELD + " > ?", new String[]{date.toString()});
                
                Toast.makeText(getApplicationContext(), String.format("История очищена за период . Удалено %d звонков и %d смс.", deletedCalls, deletedSms), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private HistoryCleaner mHistoryCleaner = new HistoryCleaner();

    public MainActivity() {
        Log.d(TAG, "MainActivity: constructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        this.deleteDatabase(Database.NAME);
        Log.d(TAG, "onCreate: create activity");
        Database database = new Database(this);
        mContactRepository = new ContactRepository(database);
        mSmsRepository = new SmsRepository(database);
        mCallRepository = new CallRepository(database);
        SjPreferences.initialize(this);
        findViewById(R.id.add_button).setOnClickListener(this);
        findViewById(R.id.settings_button).setOnClickListener(this);
        mSjContactAdapter = SjContactAdapter.create(this);
        mSjContactAdapter.setViewBinder(new SjContactBinder(this));
        mSjContactsList = (ListView) findViewById(R.id.selected_contact_list_view);
        mSjContactsList.setOnScrollListener(new ScrollListenerAnimator(findViewById(R.id.main_texts)));
        mSjContactsList.setAdapter(mSjContactAdapter);
        mSjContactsList.setOnItemClickListener(this);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new CallListener(this), PhoneStateListener.LISTEN_CALL_STATE);

        setAction(this);
    }

    private void cleanHistory(SettingsActivity.Period period) {
        mDeleteHandler.post(mHistoryCleaner);
    }

    private void startCleaning(){
        if(mDeleteTimer == null){
            mDeleteTimer = new Timer();
        }
    }

    private void updateSjContactsList() {
        Log.d(TAG, "updateSjContactsList: updating...");
        List<Map<String, Object>> items = mSjContactAdapter.getItems();
        for (Map<String, Object> item : items) {
            Integer id = (Integer) item.get(Contact._ID);
            String status = mContactRepository.getStatus(id);
            item.put(Contact.ATTRIBUTE_STATUS, status);
            Log.d(TAG, "updateSjContactsList: new status: " + status);
        }
        mSjContactAdapter.notifyDataSetChanged();
        Log.d(TAG, "updateSjContactsList: update is done at " + SjUtil.getDate("MM-dd-yyyy hh:mm:ss"));
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
