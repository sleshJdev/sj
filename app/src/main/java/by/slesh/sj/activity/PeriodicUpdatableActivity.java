package by.slesh.sj.activity;

import android.app.Activity;
import android.content.Intent;
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
                if (nowTime - period > lastUpdateTime) {
                    if (mAction != null) {
                        mAction.performAction(null);
                    }
                    contactRepository.updateAllStatuses();
                    lastUpdateTime = nowTime;
                }
            } catch (Exception e) {
                Log.d(getClass().getCanonicalName(), "run: error during updating sj contacts list view", e);
            }
        }
    }

    private Timer mTimer;
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
        Intent.ShortcutIconResource icon =
                Intent.ShortcutIconResource.fromContext(this, R.drawable.icon);

        Intent intent = new Intent();

        Intent launchIntent = new Intent(this, PeriodicUpdatableActivity.class);

        intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, launchIntent);
        intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "slesh");
        intent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, R.string.app_name_short);

        setResult(RESULT_OK, intent);
        contactRepository = new ContactRepository(new Database(this));
        mTimer = startTimer(mTimer);
    }

    protected void setPeriodicAction(Action action) {
        this.mAction = action;
    }

    private Timer startTimer(Timer timer) {
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

        return timer;
    }

    private Timer stopTime(Timer time) {
        if (time != null) {
            time.cancel();
            time.purge();
            time = null;
            Log.d(TAG, "stopUpdating: stop updating");
        }

        return time;
    }

    private void checkForUpdate() {
        HANDLER.post(mWorker);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        Log.d(TAG, "onResume: resume activity");
//        mTimer = startTimer(mTimer);
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: destroy activity");
//        mTimer = startTimer(mTimer);
    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        Log.d(TAG, "onStop: stop activity");
//        mTimer = stopTime(mTimer);
//    }
}
