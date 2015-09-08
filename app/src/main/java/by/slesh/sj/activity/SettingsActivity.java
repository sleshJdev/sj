package by.slesh.sj.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import by.slesh.sj.callback.Action;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.local.SjPreferences;
import by.slesh.sj.database.repository.CallRepository;
import by.slesh.sj.database.repository.SmsRepository;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private static final String TAG = SettingsActivity.class.getCanonicalName();

    public static class Period {
        public String name;
        public Integer value;

        public Period(String name, Integer value) {
            this.name = name;
            this.value = value;
        }
    }

    private static final Map<Integer, Period> MAIN_PERIOD_OPTIONS = new HashMap<>();
    private static final Map<Integer, Period> HISTORY_CLEAN_PERIOD_OPTIONS = new HashMap<>();

    static {
        MAIN_PERIOD_OPTIONS.put(R.id.hour, new Period("1 минута", (int) TimeUnit.MINUTES.toSeconds(1)));
        MAIN_PERIOD_OPTIONS.put(R.id.day, new Period("5 минут", (int) TimeUnit.MINUTES.toSeconds(5)));
        MAIN_PERIOD_OPTIONS.put(R.id.day3, new Period("30 минут", (int) TimeUnit.MINUTES.toSeconds(30)));
        MAIN_PERIOD_OPTIONS.put(R.id.day7, new Period("1 час", (int) TimeUnit.HOURS.toSeconds(1)));
    }

    static {
        HISTORY_CLEAN_PERIOD_OPTIONS.put(R.id.no_delete, new Period("Не далять", -1));
        HISTORY_CLEAN_PERIOD_OPTIONS.put(R.id.hour, new Period("1 минута", (int) TimeUnit.MINUTES.toSeconds(1)));
        HISTORY_CLEAN_PERIOD_OPTIONS.put(R.id.day, new Period("5 минут", (int) TimeUnit.MINUTES.toSeconds(5)));
    }
    /*static {
        PERIODS.put(R.id.hour, new Period("1 час", (int) TimeUnit.HOURS.toSeconds(1)));
        PERIODS.put(R.id.day, new Period("1 день", (int) TimeUnit.DAYS.toSeconds(1)));
        PERIODS.put(R.id.day3, new Period("3 дня", (int) TimeUnit.DAYS.toSeconds(3)));
        PERIODS.put(R.id.day7, new Period("7 дней", (int) TimeUnit.DAYS.toSeconds(7)));
    }*/

    private AlertDialog.Builder builder;


    private SmsRepository smsRepository;
    private CallRepository callRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        builder = new AlertDialog.Builder(this);

        Database database = new Database(this);
        smsRepository = new SmsRepository(database);
        callRepository = new CallRepository(database);

        findViewById(R.id.settings_period).setOnClickListener(this);
        findViewById(R.id.is_show_call_checkbox).setOnClickListener(this);
        findViewById(R.id.is_show_sms_checkbox).setOnClickListener(this);
        findViewById(R.id.delete_history_button).setOnClickListener(this);

        setupCheckbox(R.id.is_show_call_checkbox, SjPreferences.Key.IS_SHOW_CALLS_IN_LIST);
        setupCheckbox(R.id.is_show_sms_checkbox, SjPreferences.Key.IS_SHOW_SMS_IN_LIST);
    }

    private void setupCheckbox(Integer id, SjPreferences.Key propertyToCheck) {
        CheckBox checkBox = (CheckBox) findViewById(id);
        Boolean currentState = SjPreferences.getBoolean(getApplicationContext(), propertyToCheck);
        checkBox.setChecked(currentState);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.settings_period:
                changePeriod(R.layout.dialog_period,
                        MAIN_PERIOD_OPTIONS,
                        SjPreferences.Key.MAIN_PERIOD,
                        new Action() {
                            @Override
                            public void performAction(Object data) {
                                Period period = (Period) data;
                                SjPreferences.set(getApplicationContext(), SjPreferences.Key.MAIN_PERIOD, period.value.toString());
                                Toast.makeText(getApplicationContext(), "Период изменен на " + period.name, Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                break;
            case R.id.delete_history_button:
                changePeriod(R.layout.dialog_delete_period,
                        HISTORY_CLEAN_PERIOD_OPTIONS,
                        SjPreferences.Key.HISTORY_CLEAN_PERIOD,
                        new Action() {
                            @Override
                            public void performAction(Object data) {
                                Period period = (Period) data;
                                SjPreferences.set(getApplicationContext(), SjPreferences.Key.HISTORY_CLEAN_PERIOD, period.value.toString());
                                Toast.makeText(getApplicationContext(), "Период чистки истории изменен на " + period.name, Toast.LENGTH_SHORT).show();
                            }
                        });
                break;
            case R.id.is_show_call_checkbox:
                setIsShowCalls(((CheckBox) v).isChecked());
                break;
            case R.id.is_show_sms_checkbox:
                setIsShowSms(((CheckBox) v).isChecked());
                break;
        }
    }

    private void changePeriod(final Integer dialogId, final Map<Integer, Period> options, final SjPreferences.Key changeableProperty, final Action action) {
        View dialog = getLayoutInflater().inflate(dialogId, null);

        Integer currentPeriod = SjPreferences.getInteger(getApplicationContext(), changeableProperty);
        for (Integer id : options.keySet()) {
            if (currentPeriod.equals(options.get(id).value)) {
                ((RadioButton) dialog.findViewById(id)).setChecked(true);
                break;
            }
        }

        final AlertDialog alertDialog = builder.create();
        alertDialog.setView(dialog);
        alertDialog.show();
        ((RadioGroup) dialog.findViewById(R.id.period_options)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (action != null) {
                    if (options.containsKey(checkedId)) {
                        action.performAction(options.get(checkedId));
                    } else {
                        Toast.makeText(getApplicationContext(), "Вы ничего не выбрали", Toast.LENGTH_SHORT).show();
                    }
                }
                alertDialog.dismiss();
            }
        });
    }

    private void setIsShowCalls(Boolean state) {
        SjPreferences.set(getApplicationContext(), SjPreferences.Key.IS_SHOW_CALLS_IN_LIST, state.toString());
        Toast.makeText(getApplicationContext(), "Теперь вызовы " + (state ? "будут" : "не будут") + " показываться", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Изменены настройки. Показывать звонки в спике: " + state);
    }

    private void setIsShowSms(Boolean state) {
        SjPreferences.set(getApplicationContext(), SjPreferences.Key.IS_SHOW_SMS_IN_LIST, state.toString());
        Toast.makeText(getApplicationContext(), "Теперь смс " + (state ? "будут" : "не будут") + " показываться", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Изменены настройки. Показывать смс в спике: " + state);
    }
}
