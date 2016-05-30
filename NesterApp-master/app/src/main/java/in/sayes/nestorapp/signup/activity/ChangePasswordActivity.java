package in.sayes.nestorapp.signup.activity;

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
import in.sayes.nestorapp.util.ValidationUtils;

/**
 * Change password
 *
 * Created by sourav on 22/04/16.
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class ChangePasswordActivity extends BaseActivity {


    //--------------------------------------------
    //View
    //--------------------------------------------
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.edttxtOldPawd)
    RobotoEditText edttxtOldPawd;

    @Bind(R.id.edttxtNewPwd)
    RobotoEditText edttxtNewPwd;

    @Bind(R.id.edttxtConfirmPwd)
    RobotoEditText edttxtConfirmPwd;

    @Bind(R.id.btnResetPassword)
    RobotoButton btnResetPassword;

    //--------------------------------------------
    //Variables
    //--------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ButterKnife.bind(this);

        setupToolbarCross(toolbar);

        customizeEditTexts(new RobotoEditText[]{edttxtOldPawd, edttxtNewPwd, edttxtConfirmPwd});


    }

    @Override
    protected void getDataFromBundle() {

    }

    @OnClick(R.id.btnResetPassword)
    void onResetPasswordClicked(){

        if (!ValidationUtils.isPasswordValid(edttxtOldPawd.getText().toString())){

            edttxtOldPawd.setError(getString(R.string.validation_error_old_password));

            return;
        }

        if (!ValidationUtils.isPasswordValid(edttxtNewPwd.getText().toString())){

            edttxtNewPwd.setError(getString(R.string.validation_error_new_password));

            return;
        }

        if (!ValidationUtils.isPasswordValid(edttxtConfirmPwd.getText().toString())){

            edttxtConfirmPwd.setError(getString(R.string.validation_error_confirm_password));

            return;
        }

        if (!edttxtNewPwd.getText().toString().equals(edttxtConfirmPwd.getText().toString())){

            edttxtConfirmPwd.setError(getString(R.string.validation_error_password_mismatch));

            return;
        }


        showShortToast(getString(R.string.password_updated));

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
