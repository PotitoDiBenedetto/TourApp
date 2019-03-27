package com.dibenedetto.potito.tourapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends AppCompatActivity
        implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener {

    private GoogleMap mMap;

  /*
     * drawer layout
     */
    private DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_map);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        


// Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        final Intent intent = getIntent();
        final LatLng coordinates = intent.getParcelableExtra("coordinates");

        // LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(coordinates)
                .title(getResources().getString(R.string.target_position)));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(coordinates));
        /*
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
    }

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
                        //this page
                    return true;
            }
            return false;
        }
    };


 @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
            NavUtils.navigateUpFromSameTask(this);
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
        final Intent intent;

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(this, MainActivity.class);
            intent.putExtra("fragmentToOpen",1);
            startActivity(intent);
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
                intent = new Intent(this, MainActivity.class);
                intent.putExtra("fragmentToOpen",1);
                startActivity(intent);
                break;
            case R.id.nav_explore:
                intent = new Intent(this, TabbedExploreActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.nav_home:
                NavUtils.navigateUpFromSameTask(this);
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
               //this page

                break;
            default:
                //throw new IllegalArgumentException("No Fragment for the given menu item");
                drawer.closeDrawer(GravityCompat.START);//delete me !
                return true;//delete this!
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
