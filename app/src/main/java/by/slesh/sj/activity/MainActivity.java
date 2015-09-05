package by.slesh.sj.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import by.slesh.sj.adapter.SjContactAdapter;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.repository.CallRepository;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.database.repository.SmsRepository;
import by.slesh.sj.device.CallListener;

public class MainActivity extends Activity implements View.OnClickListener {
    public static final String TAG = MainActivity.class.getCanonicalName();

    private ContactRepository contactRepository;
    private SmsRepository smsRepository;
    private CallRepository callRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "create activity");

//        this.deleteDatabase(DatabaseConnector.DATABASE_NAME);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new CallListener(this), PhoneStateListener.LISTEN_CALL_STATE);

        ((TextView) findViewById(R.id.button_add)).setOnClickListener(this);

        Database.initialize(this);

        Database database = Database.getInstance();
        contactRepository = new ContactRepository(database);
        smsRepository = new SmsRepository(database);
        callRepository = new CallRepository(database);

        ListView list = (ListView) findViewById(R.id.selected_contact_list_view);
        list.setAdapter(SjContactAdapter.build(this));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_add:
                Intent intent = new Intent(this, PlainContactsActivity.class);
                startActivity(intent);
                break;
        }
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
