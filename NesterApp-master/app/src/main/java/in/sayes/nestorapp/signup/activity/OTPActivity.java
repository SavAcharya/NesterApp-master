package in.sayes.nestorapp.signup.activity;

import android.content.IntentFilter;
import android.os.Bundle;
import android.provider.Telephony;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.typeface.custom.views.RobotoButton;
import com.typeface.custom.views.RobotoEditText;
import com.typeface.custom.views.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.sayes.nestorapp.R;
import in.sayes.nestorapp.base.activity.BaseActivity;
import in.sayes.nestorapp.signup.helper.OnSMSReceivedListener;
import in.sayes.nestorapp.signup.receiver.SMSReceiver;
import in.sayes.nestorapp.util.ValidationUtils;

/**
 * Activity to receive and send OTP
 *
 * Created by sourav on 22/04/16.
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class OTPActivity extends BaseActivity implements OnSMSReceivedListener {

    //--------------------------------------------
    //View
    //--------------------------------------------

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.edttxtOTP)
    RobotoEditText edttxtOTP;

    @Bind(R.id.btnSendCode)
    RobotoButton btnSendCode;

    @Bind(R.id.tvResend)
    RobotoTextView tvResend;

    //--------------------------------------------
    //Variables
    //--------------------------------------------

    private SMSReceiver smsReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);

        ButterKnife.bind(this);

        setupToolbarBack(toolbar);

        customizeEditTexts(new RobotoEditText[]{edttxtOTP});


        smsReceiver = new SMSReceiver(this);

    }


    @OnClick(R.id.btnSendCode)
    void onLoginClicked() {

        if (ValidationUtils.isOTPValid(edttxtOTP.getText().toString().trim())) {

            startHomeScreen();

        } else {

            edttxtOTP.setError(getString(R.string.otp_validation_msg));
        }
    }


    @OnClick(R.id.tvResend)
    void onResendClicked(){

    }


    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver(smsReceiver, new IntentFilter(Telephony.Sms.Intents.SMS_RECEIVED_ACTION));
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(smsReceiver);
    }

    @Override
    protected void getDataFromBundle() {

    }

    private void startHomeScreen() {

      /*  Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();*/
    }


    @Override
    public void onSmsReceived(String sms) {
        edttxtOTP.setText(sms);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
