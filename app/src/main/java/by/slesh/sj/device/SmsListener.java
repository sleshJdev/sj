package by.slesh.sj.device;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Telephony;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by slesh on 06.09.2015.
 */
public class SmsListener extends BroadcastReceiver {
    private String TAG = SmsListener.class.getSimpleName();


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "_____________________________________________" +"asdasdasdasd");
        // Get the data (SMS data) bound to intent
        Bundle bundle = intent.getExtras();

        SmsMessage[] msgs = null;

        String str = "";

        if (bundle != null) {
            // Retrieve the SMS Messages received
            Object[] pdus = (Object[]) bundle.get("pdus");
            msgs = new SmsMessage[pdus.length];

            // For every SMS message received
            for (int i=0; i < msgs.length; i++) {
                // Convert Object array
                msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                // Sender's phone number
                str += "SMS from " + msgs[i].getOriginatingAddress() + " : ";
                // Fetch the text message
                str += msgs[i].getMessageBody().toString();
                // Newline <img src="http://codetheory.in/wp-includes/images/smilies/simple-smile.png" alt=":-)" class="wp-smiley" style="height: 1em; max-height: 1em;">
                str += "\n";
            }

            // Display the entire SMS Message
            Log.d(TAG, "_____________________________________________" +str);
        }
    }
}
