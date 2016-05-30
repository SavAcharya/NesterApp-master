package in.sayes.nestorapp.base.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.typeface.custom.views.RobotoEditText;

import in.sayes.nestorapp.R;
import in.sayes.nestorapp.storage.AppPreferences;
import in.sayes.nestorapp.storage.IPreferenceConstants;
import in.sayes.nestorapp.util.UIUtils;


/**
 * Created by sourav on 22/04/16.
 * Project : NesterApp , Package Name : in.sayes.nesterapp.profile
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */

public abstract class BaseActivity extends AppCompatActivity implements IPreferenceConstants {

    private Toolbar toolbar;
    private MaterialMenuIconToolbar materialMenu;

    protected AppPreferences appPreferences;
private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        appPreferences = new AppPreferences(this);

    }


    protected void initToolBar(Toolbar toolbar, String title) {

        this.toolbar = toolbar;
        toolbar.setTitle(title);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    protected void initToolBar(Toolbar toolbar) {

        this.toolbar = toolbar;
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    protected void setupToolbarBack(Toolbar toolbar) {

        initToolBar(toolbar);
        initMenu();
        setActionIconState(MaterialMenuDrawable.IconState.ARROW);
    }

    protected void setupToolbarCross(Toolbar toolbar) {

        initToolBar(toolbar);
        initMenu();
        setActionIconState(MaterialMenuDrawable.IconState.X);
    }

    public void setActionIconState(MaterialMenuDrawable.IconState iconState) {

        materialMenu.animatePressedState(iconState);
    }

    protected void initMenu() {

        materialMenu = new MaterialMenuIconToolbar(this, ContextCompat.getColor(this, android.R.color.white), MaterialMenuDrawable.Stroke.THIN) {
            @Override
            public int getToolbarViewId() {
                return R.id.toolbar;
            }
        };


    }

    /**
     * To avoid effect of theme, customize editText. Call this in every fragment where edit text is used.
     *
     * @param arrEditTexts
     */
    protected void customizeEditTexts(RobotoEditText[] arrEditTexts) {

        for (RobotoEditText editText : arrEditTexts) {

            UIUtils.setEditTextNormal(this, editText);
        }
    }

    protected void showShortToast(String message) {

        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


    protected abstract void getDataFromBundle();

}
