package by.slesh.sj.view.adapter;

import android.content.Context;
import android.util.Log;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import by.slesh.sj.activity.R;
import by.slesh.sj.view.adapter.binder.SjContactActivityBinder;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.local.SjPreferences;
import by.slesh.sj.database.local.model.ContactActivity;
import by.slesh.sj.database.model.Call;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.model.Sms;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.util.Converter;
import by.slesh.sj.util.DateUtil;

import static by.slesh.sj.database.model.Contact.*;

/**
 * Created by slesh on 06.09.2015.
 */
public class ProfileSjContactAdapter extends SimpleAdapter {
    private static final String TAG = ProfileSjContactAdapter.class.getCanonicalName();

    private static final int RESOURCE = R.layout.item_stat_in_profile;
    private static final String[] FROM = new String[]{ATTRIBUTE_DATE, ATTRIBUTE_QUANTITY_SMS, ATTRIBUTE_QUANTITY_CALLS, ATTRIBUTE_STATUS, "share"};
    private static final int[] TO = new int[]{R.id.date, R.id.count_sms, R.id.count_call, R.id.status, R.id.share};

    public ProfileSjContactAdapter(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
    }

    public static final ProfileSjContactAdapter create(Context context, Integer contactId) {
        int period = Integer.valueOf(SjPreferences.get(context, SjPreferences.Key.HISTORY_PERIOD));
        period = (int) TimeUnit.HOURS.toSeconds(period);
        //TODO: remove
        period = 600;//for test
        ProfileSjContactAdapter adapter = new ProfileSjContactAdapter(context, getData(context, period, contactId), RESOURCE, FROM, TO);
        adapter.setViewBinder(new SjContactActivityBinder());

        return adapter;
    }

    private static final List<Map<String, Object>> getData(Context context, Integer period, Integer contactId) {
        ContactRepository contactRepository = new ContactRepository(new Database(context));
        Contact contact = contactRepository.findOne(contactId);

        int start = contact.getDate();
        int now = DateUtil.getUnixTime();
        List<Map<String, Object>> data = new ArrayList<>();
        boolean isShowCalls = Boolean.parseBoolean(SjPreferences.get(context, SjPreferences.Key.IS_SHOW_CALLS_IN_LIST));
        boolean isShowSms = Boolean.parseBoolean(SjPreferences.get(context, SjPreferences.Key.IS_SHOW_SMS_IN_LIST));
        while (start < now) {
            ContactActivity activity = new ContactActivity(start, start + period);
            Log.d(TAG, "counting: " + activity);
            if (isShowCalls) {
                for (Call call : contact.getCalls()) {
                    if (call.getDate() >= activity.begin() && call.getDate() < activity.end()) {
                        activity.plusCall();
                    }
                }
            }
            if (isShowSms) {
                for (Sms sms : contact.getSms()) {
                    if (sms.getDate() >= activity.begin() && sms.getDate() < activity.end()) {
                        activity.plusSms();
                    }
                }
            }
            start += period;

            Map<String, Object> item = Converter.toMap(activity);
            item.put("share", "C " + activity.beginAsString() + " по " + activity.endAsString() +
                    " было: " + activity.getQuantityCalls() + " звонков и " + activity.getQuantitySms() + " смс - "
                    + activity.getStatus().getName() + ", отправлно из приложения Шкала Жалимова");
            data.add(item);
        }

        return data;
    }
}
