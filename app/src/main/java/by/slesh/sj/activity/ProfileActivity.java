package by.slesh.sj.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import by.slesh.sj.view.adapter.ProfileSjContactAdapter;
import by.slesh.sj.callback.Action;
import by.slesh.sj.database.model.Contact;


public class ProfileActivity extends UpdatableActivity implements Action, View.OnClickListener {
    private static final String TAG = ProfileActivity.class.getCanonicalName();

    private ListView mSjContactActivityList;
    private ProfileSjContactAdapter mProfileSjContactAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        findViewById(R.id.button_settings).setOnClickListener(this);
        mSjContactActivityList = (ListView) findViewById(R.id.list_stat);
        updateActivity();
        setAction(this);
    }

    private void updateActivity() {
        int id = getIntent().getIntExtra(Contact._ID, -1);
        if (id > 0) {
            mProfileSjContactAdapter = ProfileSjContactAdapter.create(this, id);
            mSjContactActivityList.setAdapter(mProfileSjContactAdapter);
            mProfileSjContactAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "Выбранный контакт не существует в базе данных... Это странно.", Toast.LENGTH_LONG).show();
            Log.d(TAG, "onCreate: selected contact with id " + id + " not found");
        }
    }

    @Override
    public void perform(Object data) {
        updateActivity();
    }

    @Override
    public boolean onTouchEvent(MotionEvent evt) {
        switch (evt.getAction()) {

        }

        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
    }
}
