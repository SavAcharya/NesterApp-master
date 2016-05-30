package in.sayes.nestorapp.home.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.typeface.custom.views.RobotoTextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.sayes.nestorapp.R;
import in.sayes.nestorapp.profile.ProfileActivity;
import in.sayes.nestorapp.storage.AppPreferences;
import in.sayes.nestorapp.storage.IPreferenceConstants;

/**
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class HandleNavigationView implements IPreferenceConstants {

    //------------------------------------------------
    //Views
    //------------------------------------------------
    @Bind(R.id.layHeader)
    LinearLayout layHeader;

    @Bind(R.id.tvUsernameHeader)
    RobotoTextView tvUsernameHeader;

    @Bind(R.id.tvEmail)
    RobotoTextView tvEmail;

    @Bind(R.id.tvMobile)
    RobotoTextView tvMobile;

    //------------------------------------------------
    //Variables
    //------------------------------------------------

    private Context context;

    private View headerView;

    private AppPreferences appPreferences;

    /**
     * @param context
     * @param headerView
     */
    public HandleNavigationView(Context context, View headerView) {
        this.context = context;
        this.headerView = headerView;

        appPreferences = new AppPreferences(context);

        ButterKnife.bind(this, headerView);
    }

    public void setNavigationHeader() {

        setHeaderBackground();

        setUserData();
    }


    private void setHeaderBackground() {

        Bitmap headerBitmap = BitmapFactory.decodeResource(context.getResources(),
                R.drawable.home_list_header);

        Drawable drawable = new BitmapDrawable(context.getResources(), headerBitmap);
        layHeader.setBackground(drawable);

    }

    private void setUserData() {

        tvUsernameHeader.setText(appPreferences.getString(PREF_USERNAME, ""));
        tvMobile.setText(appPreferences.getString(PREF_MOBILE, ""));

        if (TextUtils.isEmpty(appPreferences.getString(PREF_EMAIL, ""))) {

            tvEmail.setVisibility(View.GONE);

        } else {

            tvEmail.setVisibility(View.VISIBLE);

            tvEmail.setText(appPreferences.getString(PREF_EMAIL, ""));

        }

    }


    @OnClick(R.id.layHeader)
    void onHeaderClick() {

        showProfile();
    }


    private void showProfile() {

        Intent intent = new Intent(context, ProfileActivity.class);
        context.startActivity(intent);
    }
}
