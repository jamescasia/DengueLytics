package xyz.aetherapps1.a8;

import android.content.res.Resources;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import static xyz.aetherapps1.a8.R.id.scrollview;

public class MainActivity extends AppCompatActivity implements AlertsFragment.OnFragmentInteractionListener, HomeFragment.OnFragmentInteractionListener, DashboardFragment.OnFragmentInteractionListener{
    public static TabLayout tabLayout;
    float posy;
    Animation hidetablayout;
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        tabLayout = (TabLayout) findViewById(R.id.tablayout);



        final TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        ConstraintLayout main = (ConstraintLayout)findViewById(R.id.main);
        tabLayout.addTab(tabLayout.newTab().setText("board").setIcon(getResources().getDrawable(R.drawable.ic_dashboard_black_24dp)));
        tabLayout.addTab(tabLayout.newTab().setText("Home").setIcon(getResources().getDrawable(R.drawable.ic_home_black_24dp)));
        tabLayout.addTab(tabLayout.newTab().setText("Alerts").setIcon(getResources().getDrawable(R.drawable.ic_notifications_black_24dp)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL  );

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1, true);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public  void hidetoolbar()
    {

      tabLayout.setVisibility(View.GONE);




    }
    public   void showtoolbar()
    {
        tabLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
