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
import by.slesh.sj.util.DateUtil;

/**
 * Created by slesh on 06.09.2015.
 */
public abstract class UpdatableActivity extends Activity {
    private static final String TAG = UpdatableActivity.class.getCanonicalName();

    private Timer timer;
    private final Handler HANDLER = new Handler();
    private Runnable worker = new Runnable() {
        @Override
        public void run() {
            try {
                long period = TimeUnit.HOURS.toSeconds(Integer.valueOf(SjPreferences.get(getApplicationContext(), SjPreferences.Key.HISTORY_PERIOD)));
                long lastUpdateTime = Integer.valueOf(SjPreferences.get(getApplicationContext(), SjPreferences.Key.LAST_UPDATE_TIME));
                long nowTime = DateUtil.getUnixTime();
                //TODO: remove
                period = 30;//for test
                if (nowTime > lastUpdateTime + period) {
                    if (action != null) {
                        action.perform(null);
                    }
                    contactRepository.updateAll();
                    SjPreferences.set(getApplicationContext(), SjPreferences.Key.LAST_UPDATE_TIME, Long.toString(nowTime));
                }
            } catch (Exception e) {
                Log.d(getClass().getCanonicalName(), "run: error during updating sj contacts list view", e);
            }
        }
    };
    private Action action;
    private ContactRepository contactRepository;

    public UpdatableActivity() {
        Log.d(TAG, "UpdatableActivity: constructor");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: create");
        contactRepository = new ContactRepository(new Database(this));
        runUpdating();
    }

    protected void setAction(Action action) {
        this.action = action;
    }

    protected void runUpdating() {
        if (timer == null) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    checkForUpdate();
                }
            }, 0, TimeUnit.SECONDS.toMillis(5));
            Log.d(TAG, "runUpdating: start updating");
        }
    }

    protected void stopUpdating() {
        if (timer != null) {
            timer.cancel();
            timer.purge();
            timer = null;
            Log.d(TAG, "stopUpdating: stop updating");
        }
    }

    private void checkForUpdate() {
        if (worker != null) {
            HANDLER.post(worker);
        }
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
