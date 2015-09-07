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
import by.slesh.sj.database.local.model.Period;
import by.slesh.sj.database.model.Call;
import by.slesh.sj.database.model.Sms;
import by.slesh.sj.database.repository.CallRepository;
import by.slesh.sj.database.repository.SmsRepository;
import by.slesh.sj.util.DateUtil;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private static final String TAG = SettingsActivity.class.getCanonicalName();

    private AlertDialog.Builder builder;

    private static final Map<Integer, Period> PERIODS = new HashMap<>();

    static {
        PERIODS.put(R.id.hour, new Period("1 час", "1"));
        PERIODS.put(R.id.day, new Period("День", "24"));
        PERIODS.put(R.id.day3, new Period("3 дня", "72"));
        PERIODS.put(R.id.day7, new Period("7 дней", "172"));
    }

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
        Boolean currentState = Boolean.parseBoolean(SjPreferences.get(getApplicationContext(), propertyToCheck));
        checkBox.setChecked(currentState);
    }

    @Override
    public void onClick(final View v) {
        switch (v.getId()) {
            case R.id.settings_period:
                changePeriod(SjPreferences.Key.HISTORY_PERIOD, new Action() {
                            @Override
                            public void perform(Object data) {
                                Period period = (Period) data;
                                Toast.makeText(getApplicationContext(), "Период истории изменен на " + period.getName(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );
                break;
            case R.id.delete_history_button:
                changePeriod(SjPreferences.Key.HISTORY_CLEAN_PERIOD, new Action() {
                    @Override
                    public void perform(Object data) {
                        cleanHistory((Period) data);
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

    private void changePeriod(final SjPreferences.Key changeableProperty, final Action action) {
        View dialog = getLayoutInflater().inflate(R.layout.dialog_period, null);

        String currentPeriod = SjPreferences.get(getApplicationContext(), changeableProperty);
        for (Integer id : PERIODS.keySet()) {
            if (currentPeriod.equals(PERIODS.get(id).getValue())) {
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
                Log.d(TAG, "view id: " + checkedId);
                if (PERIODS.containsKey(checkedId)) {
                    Period period = PERIODS.get(checkedId);
                    SjPreferences.set(getApplicationContext(), changeableProperty, period.getValue());
                    if (action != null) {
                        action.perform(period);
                    }
                }

                alertDialog.dismiss();
            }
        });
    }

    private void setIsShowCalls(Boolean state) {
        SjPreferences.set(getApplicationContext(), SjPreferences.Key.IS_SHOW_CALLS_IN_LIST, state.toString());
        Toast.makeText(getApplicationContext(), "Теперь вызовы " + (state ? "будут" : "не будут") + " показывться", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Изменены настройки. Показывать звонки в спике: " + state);
    }

    private void setIsShowSms(Boolean state) {
        SjPreferences.set(getApplicationContext(), SjPreferences.Key.IS_SHOW_CALLS_IN_LIST, state.toString());
        Toast.makeText(getApplicationContext(), "Теперь смс " + (state ? "будут" : "не будут") + " показывться", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Изменены настройки. Показывать смс в спике: " + state);

    }

    private void cleanHistory(Period period) {
        Integer periodSeconds = (int) TimeUnit.HOURS.toSeconds(Long.valueOf(period.getValue()));
        Integer date = DateUtil.getUnixTime() - periodSeconds;
        Integer deletedSms = smsRepository.delete(Sms.DATE_FIELD + " > ?", new String[]{date.toString()});
        Integer deletedCalls = callRepository.delete(Call.DATE_FIELD + " > ?", new String[]{date.toString()});

        Toast.makeText(getApplicationContext(), String.format("История очищена за период %s. Удалено %d звонков и %d смс.", period.getName(), deletedCalls, deletedSms), Toast.LENGTH_SHORT).show();
    }
}
