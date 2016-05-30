package in.sayes.nestorapp.signup.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.typeface.custom.views.RobotoButton;
import com.typeface.custom.views.RobotoEditText;

import org.json.JSONObject;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.sayes.nestorapp.NestorApplication;
import in.sayes.nestorapp.R;
import in.sayes.nestorapp.base.activity.BaseActivity;
import in.sayes.nestorapp.home.activity.HomeActivity;
import in.sayes.nestorapp.signup.helper.model.GSON_Signup;
import in.sayes.nestorapp.network.utils.NetworkUtils;
import in.sayes.nestorapp.util.APIConstants;
import in.sayes.nestorapp.util.ValidationUtils;


/**
 * Created by sourav on 22/04/16.
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */

public class SignUpActivity extends BaseActivity implements APIConstants{


    //--------------------------------------------
    //View
    //--------------------------------------------

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.edttxtName)
    RobotoEditText edttxtName;

    @Bind(R.id.edttxtMobile)
    RobotoEditText edttxtMobile;

    @Bind(R.id.edttxtEmail)
    RobotoEditText edttxtEmail;

    @Bind(R.id.btnSignIn)
    RobotoButton btnSignIn;
    private String tag_json_obj="register";
    private String TAG="SignupActivity";
    private HashMap<String, String> mKeys;
    GSON_Signup registerGSON;
    public static String SIGNUP_TAG="signup_activity";

    //--------------------------------------------
    //Variables
    //--------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        customizeEditTexts(new RobotoEditText[]{edttxtName, edttxtEmail, edttxtMobile});



    }


    @OnClick(R.id.btnSignIn)
    void onSignInClicked() {


        if (!ValidationUtils.isNameValid(edttxtName.getText().toString().trim())) {

            edttxtName.setError(getString(R.string.validate_name_error_msg));

            return;
        }


        if (!ValidationUtils.isPhoneNumberValid(edttxtMobile.getText().toString().trim())) {

            edttxtEmail.setError(getString(R.string.validate_mobile_error_msg));

            return;
        }

        if (!TextUtils.isEmpty(edttxtEmail.getText().toString()) && !ValidationUtils.isEmailValid(edttxtEmail.getText().toString().trim())) {

            edttxtEmail.setError(getString(R.string.validate_email_error_msg));

            return;
        }


        saveUserData();
        registerRequest();
        //startOTPScreen();
    }

    private void registerRequest(){
        String url = BASE_URL+REGISTER_URL;

       final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();
        mKeys = new HashMap<>();
        mKeys.put(REGISTER_URL_PARAM,edttxtMobile.getText().toString().trim());
        String urlKeys = NetworkUtils.getURLKeyParameters(mKeys);

        if (!urlKeys.isEmpty()) {
            url = url + urlKeys;
        }



        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,url,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());
                          Gson gson = new Gson();
                        registerGSON=gson.fromJson(response.toString(),GSON_Signup.class);
                       // String s="{\"failure_message\":\"\",\"next_endpoint\":\"/root_level\",\"questions\":[{\"input_form\":{\"type\":\"cascade_cards\"},\"name_var\":\"user_query\",\"options\":[{\"value\":\"I am new to tax saving investment. Please start from scratch.\"},{\"value\":\"I pay rent. Can you help me with HRA exemption?\"},{\"value\":\"I always feel my in hand salary is way less than my CTC.\"},{\"value\":\"I have done investment in PPF. Can I take benefit?\"},{\"value\":\"I have done investment in PF. Can I get benefit?\"}],\"select_type\":\"single\",\"statements\":[\"This is Nestor!\",\"Welcome, select the option you care about?\"]},{\"input_form\":{\"height\":1,\"type\":\"cascade_cards\",\"width\":1},\"name_var\":\"user_start\",\"options\":[{\"value\":\"Lets start to plan it\"},{\"value\":\"Lets start to plan it\"},{\"value\":\"Lets start to plan it\"}],\"select_type\":\"single\",\"statements\":[\"Cool, like everything else this too needs some planning.\"]}],\"status\":\"success\",\"user_id\":\"9972210077\"}";
                       // String s="{"failure_message":"","next_endpoint":"/root_level","questions":[{"input_form":{"type":"cascade_cards"},"name_var":"user_query","options":[{"value":"I am new to tax saving investment. Please start from scratch."},{"value":"I pay rent. Can you help me with HRA exemption?"},{"value":"I always feel my in hand salary is way less than my CTC."},{"value":"I have done investment in PPF. Can I take benefit?"},{"value":"I have done investment in PF. Can I get benefit?"}],"select_type":"single","statements":["This is Nestor!","Welcome, select the option you care about?"]},{"input_form":{"height":1,"type":"horizontal_scroll","width":1},"name_var":"user_start","options":[{"value":"Lets start to plan it"}],"select_type":"single","statements":["Cool, like everything else this too needs some planning."]}],"status":"success","user_id":"9972210077"}";
                       // registerGSON=gson.fromJson(s,GSON_Signup.class);
                        Log.i("Signup",response.toString());
                        openChatScreen(response.toString());
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        NestorApplication.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);
    }

    private void openChatScreen(String mData) {
        Intent i= new Intent(SignUpActivity.this, HomeActivity.class);
        i.putExtra("signup_activity",mData);
        startActivity(i);
        finish();
    }

    private void startOTPScreen() {

        Intent otpIntent = new Intent(this, OTPActivity.class);
        startActivity(otpIntent);
        finish();
    }


    private void saveUserData() {

        appPreferences.putString(PREF_USERNAME, edttxtName.getText().toString().trim());
        appPreferences.putString(PREF_MOBILE, edttxtMobile.getText().toString().trim());
        appPreferences.putString(PREF_EMAIL, edttxtEmail.getText().toString().trim());

    }


    @Override
    protected void getDataFromBundle() {

    }


}
