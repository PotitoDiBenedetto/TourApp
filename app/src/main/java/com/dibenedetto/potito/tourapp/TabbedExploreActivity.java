package com.dibenedetto.potito.tourapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dibenedetto.potito.tourapp.R;
import com.dibenedetto.potito.tourapp.db.TourAppRoomDatabase;
import com.dibenedetto.potito.tourapp.fragments.AllLocationsFragment;
import com.dibenedetto.potito.tourapp.fragments.HomeFragment;
import com.dibenedetto.potito.tourapp.fragments.SettingsFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import static com.dibenedetto.potito.tourapp.util.ViewUtility.findViewById;

//import com.google.android.material.floatingactionbutton.FloatingActionButton;
//import com.google.android.material.snackbar.Snackbar;

public class TabbedExploreActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    /**

     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /*
     * drawer layout
     */
    private DrawerLayout drawer;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;




    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);

        setContentView(R.layout.explore_activity_main);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        final TabLayout tabLayout  = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pages_container_explorer);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            //TODO : return the right fragment in switch case


            return new AllLocationsFragment();
        }

        @Override
        public int getCount() {
            // Show 5 total pages.
            return 5;
        }
    }

    /*
    @Override
    public void onResume(){
        super.onResume();
        mSectionsPagerAdapter.notifyDataSetChanged();
    }
    */

    /*
     * attribute listener for bottom navigation bar
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            //todo: remove
            View view = findViewById(R.id.container);

            switch (item.getItemId()) {
                case R.id.navigation_coupons:
                    //todo : open coupon activity
                    Snackbar.make(view, "Replace with your own action - Coupons", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                case R.id.navigation_photo:
                    //todo: open diaries activity
                    Snackbar.make(view, "Replace with your own action - Diaries", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
                case R.id.navigation_map:
                    //todo: open explore activity
                    Snackbar.make(view, "Replace with your own action - Explore", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return true;
            }
            return false;
        }
    };



    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if ( getFragmentManager().getBackStackEntryCount() > 0)
        {
            getFragmentManager().popBackStack();
            return;
        } else {
            super.onBackPressed();
            final Intent intent = new Intent(this, ExploreActivity.class);
            startActivity(intent);
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        final int id = item.getItemId();
        final Fragment nextFragment;

        switch(id) {
            case R.id.nav_settings:
               final Intent intent = new Intent(this, ExploreActivity.class);
               intent.putExtra("fragmentToOpen",1);
               startActivity(intent);
                break;
            case R.id.nav_explore:
                // this page
                break;/*
            case R.id.nav_diaires:
                //nextFragment = new DiariesFragment();
                //nextFragment.setRetainInstance(true);
                break;
            case R.id.nav_coupons:
                //nextFragment = new CouponsFragment();
                //nextFragment.setRetainInstance(true);
                break;
            case R.id.nav_photo:
                //nextFragment = new PhotoFragment();
                //nextFragment.setRetainInstance(true);
                break;
            case R.id.nav_map:
                //nextFragment = new MapFragment();
                //nextFragment.setRetainInstance(true);
                break; */
            default:
                //throw new IllegalArgumentException("No Fragment for the given menu item");
                drawer.closeDrawer(GravityCompat.START);//delete me !
                return true;//delete this!
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



}
