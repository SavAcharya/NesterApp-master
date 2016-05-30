package in.sayes.nestorapp.signup.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import in.sayes.nestorapp.signup.helper.OnSMSReceivedListener;
import in.sayes.nestorapp.util.AppUtils;
import in.sayes.nestorapp.util.Logger;

/**
 * Created by sourav on 22/04/16.
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class SMSReceiver extends BroadcastReceiver {

    private OnSMSReceivedListener onSMSReceivedListener;


    public SMSReceiver(OnSMSReceivedListener onSMSReceivedListener) {
        this.onSMSReceivedListener = onSMSReceivedListener;
    }


    @Override
    public void onReceive(Context context, Intent intent) {


        Bundle bundle = intent.getExtras();
        SmsMessage[] msgs = null;
        if (bundle != null) {
            try {
                Object[] pdus = (Object[]) bundle.get("pdus");
                msgs = new SmsMessage[pdus.length];
                for (int i = 0; i < msgs.length; i++) {
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String msgBody = msgs[i].getMessageBody();
                    onSMSReceivedListener.onSmsReceived(AppUtils.getDigitFromSms(msgBody));
                    Logger.d(AppUtils.getDigitFromSms(msgBody));
                }
            } catch (Exception e) {
                Logger.e(e.getMessage());
            }
        }

    }
}
