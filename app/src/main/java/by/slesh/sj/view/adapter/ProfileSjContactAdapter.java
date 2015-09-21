package by.slesh.sj.view.adapter;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import by.slesh.sj.activity.R;
import by.slesh.sj.database.local.StatusResolver;
import by.slesh.sj.util.SjUtil;
import by.slesh.sj.view.adapter.binder.SjContactActivityBinder;
import by.slesh.sj.database.core.Database;
import by.slesh.sj.database.local.SjPreferences;
import by.slesh.sj.database.model.Call;
import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.model.Sms;
import by.slesh.sj.database.repository.ContactRepository;

import static by.slesh.sj.database.model.Contact.*;

/**
 * Created by slesh on 06.09.2015.
 */
public class ProfileSjContactAdapter extends SimpleAdapter {
    private static final String TAG = ProfileSjContactAdapter.class.getCanonicalName();

    private static final int RESOURCE = R.layout.item_stat_in_profile;
    private static final String[] FROM = new String[]{ATTRIBUTE_DATE, ATTRIBUTE_QUANTITY_SMS, ATTRIBUTE_QUANTITY_CALLS, ATTRIBUTE_STATUS, "share"};
    private static final int[] TO = new int[]{R.id.date, R.id.count_sms, R.id.count_call, R.id.status, R.id.share};

    private List<Map<String, Object>> mData;

    private ProfileSjContactAdapter(Context context, List<Map<String, Object>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        this.mData = data;
    }

    public List<Map<String, Object>> getData() {
        return mData;
    }

    public static final ProfileSjContactAdapter create(Context context, Integer contactId) {
        int period = SjPreferences.getInteger(context, SjPreferences.Key.MAIN_PERIOD);
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
        int now = SjUtil.getUnixTime();
        List<Map<String, Object>> data = new ArrayList<>();
        boolean isShowCalls = SjPreferences.getBoolean(context, SjPreferences.Key.IS_SHOW_CALLS_IN_LIST);
        boolean isShowSms = SjPreferences.getBoolean(context, SjPreferences.Key.IS_SHOW_SMS_IN_LIST);
        while (start < now) {
            int quantityCalls = 0;
            int quantitySms = 0;
            int end = start + period;
            if (isShowCalls) {
                for (Call call : contact.getCalls()) {
                    if (call.getDate() >= start && call.getDate() < end) {
                        ++quantityCalls;
                    }
                }
            }
            if (isShowSms) {
                for (Sms sms : contact.getSms()) {
                    if (sms.getDate() >= start && sms.getDate() < end) {
                        ++quantitySms;
                    }
                }
            }
            start += period;

            String beginDate = SjUtil.getDate("yyyy-MM-dd HH:mm", TimeUnit.SECONDS.toMillis(start));
            String endDate = SjUtil.getDate("yyyy-MM-dd HH:mm", TimeUnit.SECONDS.toMillis(end));
            String status = StatusResolver.getStatus(quantityCalls + quantitySms);
            Map<String, Object> item = new HashMap<>();
            item.put(ATTRIBUTE_DATE, beginDate);
            item.put(ATTRIBUTE_QUANTITY_SMS, quantitySms);
            item.put(ATTRIBUTE_QUANTITY_CALLS, quantityCalls);
            item.put(ATTRIBUTE_STATUS, status);
            item.put("share", "C " + beginDate + " по " + endDate +
                    " было: " + quantityCalls + " звонков и " + quantitySms + " смс - "
                    + status + ", отправлно из приложения Шкала Жалимова");
            data.add(item);
        }

        return data;
    }
}
