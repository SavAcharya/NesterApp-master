package in.sayes.nestorapp.home.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.typeface.custom.views.RobotoButton;
import com.typeface.custom.views.RobotoEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.sayes.nestorapp.R;
import in.sayes.nestorapp.base.activity.BaseActivity;

/**
 *Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class ReferralActivity extends BaseActivity {

    //--------------------------------------------
    //View
    //--------------------------------------------
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.edttxtReferal)
    RobotoEditText edttxtReferal;

    @Bind(R.id.btnSendCode)
    RobotoButton btnSendCode;

    //--------------------------------------------
    //Variables
    //--------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_referral);

        ButterKnife.bind(this);

        setupToolbarCross(toolbar);

        customizeEditTexts(new RobotoEditText[]{edttxtReferal});

    }


    @OnClick(R.id.edttxtReferal)
    void onSendReferralClick() {


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

    @Override
    protected void getDataFromBundle() {

    }

}
