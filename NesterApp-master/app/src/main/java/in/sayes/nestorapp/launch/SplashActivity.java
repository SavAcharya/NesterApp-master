package in.sayes.nestorapp.launch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import in.sayes.nestorapp.home.activity.HomeActivity;
import in.sayes.nestorapp.signup.activity.SignUpActivity;
import in.sayes.nestorapp.storage.AppPreferences;
import in.sayes.nestorapp.storage.IPreferenceConstants;

/**
 * Created by sourav on 22/04/16.
 * Project : NesterApp , Package Name : in.sayes.nesterapp.profile
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class SplashActivity extends AppCompatActivity {

    private AppPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appPreferences = new AppPreferences(this);



        startNextScreen();
    }

    private void startNextScreen() {


        if (TextUtils.isEmpty(appPreferences.getString(IPreferenceConstants.PREF_MOBILE, ""))) {

            startSigninScreen();

        } else {


            startHomeScreen();
        }

    }


    private void startHomeScreen() {

        Intent homeIntent = new Intent(this, HomeActivity.class);
        startActivity(homeIntent);
        finish();
    }

    private void startSigninScreen() {

        Intent homeIntent = new Intent(this, SignUpActivity.class);
        startActivity(homeIntent);
        finish();
    }


}
