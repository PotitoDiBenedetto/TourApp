package com.dibenedetto.potito.tourapp;

import android.Manifest;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dibenedetto.potito.tourapp.db.TourAppRoomDatabase;
import com.dibenedetto.potito.tourapp.fragments.HomeFragment;
import com.dibenedetto.potito.tourapp.fragments.SettingsFragment;

import com.dibenedetto.potito.tourapp.util.Const;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import static com.dibenedetto.potito.tourapp.util.Const.LOCATION_PERMISSION_REQUEST_ID;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    //this app db reference
    private static TourAppRoomDatabase db;

    /*
     *
     */
    public static final String DB_NAME = Const.DB_NAME;

    /*
     *
     */
    private static long downloadID;

    /*
     * drawer layout
     */
    private DrawerLayout drawer;

    /*
     * navigationView
     */
    private NavigationView navigationView;

    /*
     * attribute listener for bottom navigation bar
     */
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            //todo: remove
            View view = findViewById(R.id.container);
            final Intent intent;

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
                    intent = new Intent(MainActivity.this, MapsActivity.class);
                    intent.putExtra("coordinates",new LatLng(41.1096154,16.8793305));
                    startActivity(intent);
                    return true;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeNoActionBar);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.home_activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        navigationView.setCheckedItem(R.id.nav_home);

        // showing the home fragment
        if (savedInstanceState == null) {


                final HomeFragment mainFragment = HomeFragment.newInstance();
                mainFragment.setRetainInstance(true);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.anchor_point, mainFragment, "")
                        //.addToBackStack(null)
                        .commit();
        }

    }

    @Override
    public void onStart(){
        super.onStart();

        managePermissions();

        // check if other activity requested to show a fragment
        respondToIntent(getIntent().getIntExtra("fragmentToOpen", 0));

    }

    /*
    private boolean areAllMyPermissionsGranted() {

        return ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)
            && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED)
            && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
    }
    */

    private boolean areStoragePermissionsGranted() {

        return ((ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED));
    }


    private void managePermissions() {
         if (areStoragePermissionsGranted()) {
             initDB();
         }  else if ((ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE))
             || (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))) {
                 // Here we show the Dialog for the message
                 new AlertDialog.Builder(this)
                         .setTitle(R.string.permission_reason_title)
                         .setMessage(R.string.permission_reason_message)
                         .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 // We retry to acquire the permission
                                 ActivityCompat.requestPermissions(MainActivity.this,
                                         new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                                 Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                         Const.STORAGE_PERMISSION_REQUEST_ID);
                             }
                         })
                         .create()
                         .show();

         } else {
                // We ask for the permission
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        Const.STORAGE_PERMISSION_REQUEST_ID);

            }
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            if (requestCode == Const.STORAGE_PERMISSION_REQUEST_ID) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED
                        && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    managePermissions();
                } else {
                    // In this case we cannot manage location so the app is not working
                    new AlertDialog.Builder(this)
                            .setTitle(R.string.no_storage_permission_title)
                            .setMessage(R.string.no_storage_permission_message)
                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    // We exit from the application
                                    finish();
                                }
                            })
                            .create()
                            .show();
                }
            }
        }


    private void initDB() {
        final Context context = getApplicationContext();

        if(TourAppRoomDatabase.haveToUpdate(context)) {
            registerReceiver(onDownloadComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
            MainActivity.downloadID = TourAppRoomDatabase.downloadDatabase(context, DB_NAME);
        } else {
            MainActivity.db = TourAppRoomDatabase.getDatabase(getApplicationContext(), DB_NAME);
        }

    }

    //Broadcast receiver fot the DB downloaded intent
    private BroadcastReceiver onDownloadComplete = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //Fetching the download id received with the broadcast
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (MainActivity.downloadID == id) {

                    //replace DB
                    TourAppRoomDatabase.replaceDownloadedDatabase(getApplicationContext(), DB_NAME);
                    //get istance
                    MainActivity.db = TourAppRoomDatabase.getDatabase(getApplicationContext(), DB_NAME);

                //unregister the broadcast receiver;
                unregisterReceiver(onDownloadComplete);
            }
        }
    };

    private void respondToIntent(int fragmentCode) {

        final Fragment nextFragment;

        switch(fragmentCode) {
            case 0:
                break;
            case 1:
                nextFragment = new SettingsFragment();
                nextFragment.setRetainInstance(true);

                navigationView.setCheckedItem(R.id.nav_settings);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.anchor_point, nextFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            default:
                break;
        }


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
        final int id = item.getItemId();
        final Fragment nextFragment;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            nextFragment = new SettingsFragment();
            nextFragment.setRetainInstance(true);

            navigationView.setCheckedItem(R.id.nav_settings);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.anchor_point, nextFragment)
                    .addToBackStack(null)
                    .commit();
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
        final Intent intent;

        switch(id) {
            case R.id.nav_settings:
                nextFragment = new SettingsFragment();
                nextFragment.setRetainInstance(true);
                navigationView.setCheckedItem(R.id.nav_settings);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.anchor_point, nextFragment)
                        .addToBackStack(null)
                        .commit();
                break;
            case R.id.nav_explore:
                intent = new Intent(this, TabbedExploreActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_home:
                nextFragment = HomeFragment.newInstance();
                nextFragment.setRetainInstance(true);
                navigationView.setCheckedItem(R.id.nav_explore);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.anchor_point, nextFragment, "")
                        //.addToBackStack(null)
                        .commit();
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
                break;*/
            case R.id.nav_map:
                intent = new Intent(this, MapsActivity.class);
                intent.putExtra("coordinates",new LatLng(-31,151));
                startActivity(intent);

                break;
            default:
                //throw new IllegalArgumentException("No Fragment for the given menu item");
                drawer.closeDrawer(GravityCompat.START);//delete me !
                return true;//delete this!
        }

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
            /*
            case R.id.home_settings:
                nextFragment = new SettingsFragment();
                nextFragment.setRetainInstance(true);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.anchor_point, nextFragment)
                        .addToBackStack(null)
                        .commit();
                break;*/
            case R.id.card_home_explore:
                Intent intent = new Intent(this, TabbedExploreActivity.class);
                startActivity(intent);

                break;
                /*
            case R.id.nav_diaires:
                //nextFragment = new DiariesFragment();
                //nextFragment.setRetainInstance(true);
                break;
            case R.id.nav_coupons:
                //nextFragment = new CouponsFragment();
                //nextFragment.setRetainInstance(true);
                break;
          */
            default:
                //throw new IllegalArgumentException("No Fragment for the given menu item");
                Snackbar.make(view, "Replace with your own action - Explore", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                return;

        }


    }
}
