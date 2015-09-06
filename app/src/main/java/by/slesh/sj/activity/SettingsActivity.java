package by.slesh.sj.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.HashMap;
import java.util.Map;

import by.slesh.sj.database.local.SjPreferences;

public class SettingsActivity extends Activity implements View.OnClickListener {
    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        builder = new AlertDialog.Builder(this);
        dialog = getLayoutInflater().inflate(R.layout.dialog_period, null);

        findViewById(R.id.settings_period).setOnClickListener(this);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_period:

                break;
        }
    }

    private static final Map<String, Integer> PERIODS = new HashMap<>();

    static {
        PERIODS.put("1", R.id.hour);
        PERIODS.put("24", R.id.day);
        PERIODS.put("72", R.id.day3);
        PERIODS.put("168", R.id.day7);
    }

    private View dialog;

    private void setPeriod(View v) {
        int id = PERIODS.get(SjPreferences.get(SjPreferences.Key.HISTORY_CLEANING_FREQUENCY));
        ((RadioButton) dialog.findViewById(id)).setChecked(true);
        builder.setView(dialog);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();
        ((RadioGroup) dialog.findViewById(R.id.period_options)).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (PERIODS.values().contains(checkedId)) {
                    for (String key : PERIODS.keySet()) {
                        if (PERIODS.get(key) == checkedId) {
                            SjPreferences.set(SjPreferences.Key.HISTORY_CLEANING_FREQUENCY, PERIODS.get(PERIODS.get(key)).toString());
                            return;
                        }
                    }
                }
                SjPreferences.set(SjPreferences.Key.HISTORY_CLEANING_FREQUENCY, SjPreferences.Key.HISTORY_CLEANING_FREQUENCY.getDefault());

            }
        });

    }
}
