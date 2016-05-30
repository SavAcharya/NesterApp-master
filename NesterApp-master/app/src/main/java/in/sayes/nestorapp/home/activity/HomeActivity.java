package in.sayes.nestorapp.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.sayes.nestorapp.R;
import in.sayes.nestorapp.base.activity.BaseActivity;
import in.sayes.nestorapp.chat.fragment.ChatFragment;
import in.sayes.nestorapp.home.adapter.HomePagerAdapter;
import in.sayes.nestorapp.home.fragment.HomeFragment;
import in.sayes.nestorapp.home.helper.HandleNavigationView;
import in.sayes.nestorapp.storage.AppPreferences;

/**
 * Home screen of app when user is already logged in
 * <p/>
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class HomeActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {


    //------------------------------------------------
    //Views
    //------------------------------------------------
    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.tabLayout)
    TabLayout tabLayout;

    @Bind(R.id.viewPagerHome)
    ViewPager viewPagerHome;

    //------------------------------------------------
    //Variables
    //------------------------------------------------
    String mSignupResponse;
    private HomePagerAdapter homePagerAdapter;
    protected AppPreferences appPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setupNavigation();

        setupTabs();

        // Read signup response and store in preference for future use.
        appPreferences = new AppPreferences(this);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                mSignupResponse = null;
            } else {
                mSignupResponse = extras.getString("signup_activity");
                appPreferences.putString(PREF_SIGNUP_REPLY, mSignupResponse);
            }
        } else {
            mSignupResponse = (String) savedInstanceState.getSerializable("signup_activity");
        }
    }

    @Override
    protected void getDataFromBundle() {

    }


    private void setupNavigation() {

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        HandleNavigationView handleNavigationView = new HandleNavigationView(this, navigationView.getHeaderView(0));
        handleNavigationView.setNavigationHeader();
    }


    private void setupTabs() {

        homePagerAdapter = new HomePagerAdapter(this, getFragmentManager(), tabLayout);


        homePagerAdapter.addFrament(new ChatFragment(), getString(R.string.chats));
        homePagerAdapter.addFrament(new HomeFragment(), getString(R.string.home));

        viewPagerHome.setAdapter(homePagerAdapter);
        tabLayout.setupWithViewPager(viewPagerHome);

        homePagerAdapter.setTabTitlesToIcons();
        viewPagerHome.addOnPageChangeListener(homePagerAdapter);


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_refer) {

            startReferral();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void startReferral() {

        Intent intentReferral = new Intent(this, ReferralActivity.class);
        startActivity(intentReferral);
    }
}
