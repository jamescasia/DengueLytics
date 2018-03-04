package xyz.aetherapps1.a8;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapter extends FragmentStatePagerAdapter
{

    int mNoOfTabs;

    public PagerAdapter(FragmentManager fm, int NumberOfTabs)
    {
        super(fm);
        this.mNoOfTabs = NumberOfTabs;
    }
    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                DashboardFragment dashboardFragment= new DashboardFragment();
                return dashboardFragment;

            case 1:
                HomeFragment homeFragment = new HomeFragment();
                return homeFragment;
            case 2:
                AlertsFragment alertsFragment = new AlertsFragment();
                return alertsFragment;
            default:
                return null;
        }



    }

    @Override
    public int getCount() {
        return mNoOfTabs;

    }
}