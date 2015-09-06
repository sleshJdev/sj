package by.slesh.sj.device;

import android.content.Context;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import by.slesh.sj.database.model.Contact;
import by.slesh.sj.database.model.Sms;
import by.slesh.sj.database.repository.CallRepository;
import by.slesh.sj.database.repository.ContactRepository;
import by.slesh.sj.database.repository.SmsRepository;
import by.slesh.sj.util.DateUtil;

/**
 * Created by slesh on 05.09.2015.
 */
public class CallListener extends PhoneStateListener {
    private static final String TAG = CallListener.class.getCanonicalName();

    private Context context;
    private ContactRepository contactRepository;
    private SmsRepository smsRepository;
    private CallRepository callRepository;

    public CallListener(Context context) {
        this.context = context;
        contactRepository = new ContactRepository();
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        String number = incomingNumber;
        if (state == TelephonyManager.CALL_STATE_RINGING) {
            Log.d(TAG, "incoming call from " + incomingNumber);

            Contact caller = contactRepository.findByPhone(incomingNumber);
            if(caller != null){
                smsRepository.save(new Sms(DateUtil.getUnixTime(), caller.getId()));

                Log.d(TAG, "incoming call is saved");

            }
        }

        if (state == TelephonyManager.CALL_STATE_OFFHOOK) {
        }

        if (state == TelephonyManager.CALL_STATE_IDLE) {
        }
    }

    private void addPointToContact(String number) {

    }
};
