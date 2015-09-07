package by.slesh.sj.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import by.slesh.sj.callback.Action;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.local.SjPreferences;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.util.SjUtil;

/**
 * Created by slesh on 06.09.2015.
 */
public abstract class PeriodicUpdatableActivity extends Activity {
    private static final String TAG = PeriodicUpdatableActivity.class.getCanonicalName();

    private class Worker implements Runnable {
        long lastUpdateTime = 0;

        @Override
        public void run() {
            try {
                long period = SjPreferences.getInteger(getApplicationContext(), SjPreferences.Key.MAIN_PERIOD);
                long nowTime = SjUtil.getUnixTime();
                //TODO: remove
                period = 30;//for test
                if (nowTime > lastUpdateTime + period) {
                    if (mAction != null) {
                        mAction.perform(null);
                    }
                    contactRepository.updateAllStatuses();
                    lastUpdateTime = nowTime;
                }
            } catch (Exception e) {
                Log.d(getClass().getCanonicalName(), "run: error during updating sj contacts list view", e);
            }
        }
    }

    ;private Timer mTimer;
    private final Handler HANDLER = new Handler();
    private Action mAction;
    private ContactRepository contactRepository;
    private Worker mWorker = new Worker();

    public PeriodicUpdatableActivity() {
        Log.d(TAG, "PeriodicUpdatableActivity: constructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: create");
        contactRepository = new ContactRepository(new Database(this));
        runUpdating();
    }

    protected void setAction(Action action) {
        this.mAction = action;
    }

    protected void runUpdating() {
        if (mTimer == null) {
            mTimer = new Timer();
            mTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    checkForUpdate();
                }
            }, 0, TimeUnit.SECONDS.toMillis(5));
            Log.d(TAG, "runUpdating: start updating");
        }
    }

    protected void stopUpdating() {
        if (mTimer != null) {
            mTimer.cancel();
            mTimer.purge();
            mTimer = null;
            Log.d(TAG, "stopUpdating: stop updating");
        }
    }

    private void checkForUpdate() {
        HANDLER.post(mWorker);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: resume activity");
        runUpdating();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: destroy activity");
        stopUpdating();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: stop activity");
        stopUpdating();
    }
}
