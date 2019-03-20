package com.dibenedetto.potito.tourapp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dibenedetto.potito.tourapp.db.TourAppRoomDatabase;
import com.dibenedetto.potito.tourapp.fragments.ExploreFragment;
import com.dibenedetto.potito.tourapp.fragments.HomeFragment;
import com.dibenedetto.potito.tourapp.fragments.SettingsFragment;

import com.dibenedetto.potito.tourapp.util.FilesUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class ExploreActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //this app db reference
    public static TourAppRoomDatabase db;

    public static final String DB_NAME = "TourApp.db";

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


    /*
     * drawer layout
     */
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
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

        // for changing the fragment
        if (savedInstanceState == null) {
/*
            final Intent intent = getIntent();
            if (intent == null) { */
                //final Fragment mainFragment = new Fragment();
                final HomeFragment mainFragment = HomeFragment.newInstance();
                mainFragment.setRetainInstance(true);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.anchor_point, mainFragment, "")
                        //.addToBackStack(null)
                        .commit();
            } /* else {
                //recupera l'action dell'intent e carica il fragment idoneo
            }
        }

        /*
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.anchor_point, nextFragment)
                .commit();
         */

    ExploreActivity.db = FilesUtil.buildDatabase(getApplicationContext(), DB_NAME);

    }


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
                nextFragment = new SettingsFragment();
                nextFragment.setRetainInstance(true);
                break;
            case R.id.nav_explore:
                nextFragment = new ExploreFragment();
                nextFragment.setRetainInstance(true);
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

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.anchor_point, nextFragment)
                .addToBackStack(null)
                .commit();
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /*
     * gestione dei click nella schermata home
     */
    public void onHomeCardClicked(View view) {
        int id = view.getId();

        final Fragment nextFragment;

        switch (id) {
            case R.id.home_settings:
                nextFragment = new SettingsFragment();
                nextFragment.setRetainInstance(true);
                break;
            case R.id.home_explore:
                nextFragment = new ExploreFragment();
                nextFragment.setRetainInstance(true);
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
                Snackbar.make(view, "Replace with your own action - Explore", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;

        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.anchor_point, nextFragment)
                .addToBackStack(null)
                .commit();

    }
}
