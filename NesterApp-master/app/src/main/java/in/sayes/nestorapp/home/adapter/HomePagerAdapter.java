package in.sayes.nestorapp.home.adapter;


import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.TabLayout;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.List;

import in.sayes.nestorapp.R;

/**
 * Pager adapter for home screen
 *
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class HomePagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener {


    private List<Fragment> listFragments = new ArrayList<>();
    private List<String> listTitles = new ArrayList<>();

    private Context context;
    private ArrayList<Drawable> icons = new ArrayList<>();
    private ArrayList<Drawable> iconsHilighted = new ArrayList<>();

    private TabLayout tabLayout;

    public HomePagerAdapter(Context context, FragmentManager fm, TabLayout tabLayout) {
        super(fm);

        this.context = context;
        this.tabLayout = tabLayout;
    }

    @Override
    public Fragment getItem(int position) {


        return listFragments.get(position);
    }

    public void addFrament(Fragment fragment, String title) {
        listFragments.add(fragment);
        listTitles.add(title);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return listFragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return listTitles.get(position);

    }
// Home page tab icon
    public void setTabTitlesToIcons() {
        Drawable icon1 = ContextCompat.getDrawable(context, R.drawable.ic_chat_black);
        Drawable icon2 = ContextCompat.getDrawable(context, R.drawable.ic_home_black);

        Drawable icon1Hilighted = ContextCompat.getDrawable(context, R.drawable.ic_chat_white_24dp);
        Drawable icon2Hilighted = ContextCompat.getDrawable(context, R.drawable.ic_home_white_24dp);

        icons.add(icon1);
        icons.add(icon2);

        iconsHilighted.add(icon1Hilighted);
        iconsHilighted.add(icon2Hilighted);

        for (int i = 0; i < icons.size(); i++) {
            if (i == 0) {
                tabLayout.getTabAt(i).setIcon(iconsHilighted.get(i));
            } else {
                tabLayout.getTabAt(i).setIcon(icons.get(i));
            }
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        for (int i = 0; i < listFragments.size(); i++) {
            if (i == position) {
                tabLayout.getTabAt(i).setIcon(iconsHilighted.get(i));
            } else {
                tabLayout.getTabAt(i).setIcon(icons.get(i));
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}