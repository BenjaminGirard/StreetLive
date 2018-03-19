package com.example.tetard.streetlive;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.twitter.sdk.android.core.models.Image;
import com.twitter.sdk.android.core.models.Search;

public class MapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        OnMapReadyCallback,
        GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        ActivityCompat.OnRequestPermissionsResultCallback,
        LocationListener {

    /**
     * navigation drawer variables
     */
    private DrawerLayout _drawerLayout;

    /**
     * Toolbar variables
     */
    private Toolbar _toolbar;


    /**
     * Google maps Variables
     */
    private View mapView;
    private boolean mPermissionDenied = false;
    private GoogleMap _map;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Location variable
     */
    private LocationManager locationManager;
    private static final long MIN_TIME = 400;
    private static final float MIN_DISTANCE = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initGoogleMaps();
        toolbarConfig();
        initImageButton();
    }

            /** * * * * * * * * * * * * * *
             * Toolbar + navigation bar   *
             * <p>                        *
             * layout: activity_map.xml   *
             * * * * * * * * * * * * * *  */

    private void searchViewConfig(Menu menu) {

        /**
         * Configuration search bar
         *
         * layout: activity_map.xml
         */
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = //(SearchView) MenuItemCompat.getActionView(search);
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(new ComponentName(getApplicationContext(), SearchResultsActivity.class)));

        searchView.setIconifiedByDefault(false);
        searchView.setBackgroundColor(Color.WHITE);
        /*        ImageView view = (ImageView) menu.findItem(R.id.search);
        view.setImageResource(R.drawable.ic_voice);
  */
    }

    private void toolbarConfig() {
        /**
         * Configuration tool bar
         *
         * layout: activity_map.xml
         */
        _toolbar = (Toolbar) findViewById(R.id.mapToolbar);
        _drawerLayout = findViewById(R.id.drawer_layout);

        setSupportActionBar(_toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_tab);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        /**
         *  change location in map with searchview
         *
         */
        searchViewConfig(menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                _drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

                /** * * * * * * * * * * * * * * * * * * * * * * * *
                 * Navigation drawer initialisation and callback  *
                 * <p>                                            *
                 * layout: nav_header.xml                         *
                 * * * * * * * * * * * * * * * * * * * * * * * *  */

    public void initImageButton() {

        /**
         * Image button (profile picture) initialization
         *
         * layout: nav_header.xml
         */
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView = navigationView.getHeaderView(0);
        ImageButton nav_user = hView.findViewById(R.id.profile_pic);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.forest);
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        mDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.DST_OVER);
        nav_user.setImageDrawable(mDrawable);
    }

    /**
     * Buttons in navigation drawer
     * <p>
     * layout: nav_header.xml
     *
     * @param menuItem
     * @return
     */

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        // set item as selected to persist highlight
        menuItem.setChecked(true);
        // close drawer when item is tapped
        _drawerLayout.closeDrawers();

        // Add code here to update the UI based on the item selected
        // For example, swap UI fragments here

        switch (menuItem.getItemId()) {
            case R.id.profile_pic:
                Intent image = new Intent(this, AccountActivity.class);
                startActivity(image);
                // go to account
                return true;
            case R.id.nav_account:
                Intent account = new Intent(this, AccountActivity.class);
                startActivity(account);
                // go to account
                return true;
            case R.id.nav_favorites:
                // go to favorites
                return true;
            case R.id.nav_events:
                // go to events
                return true;
            case R.id.nav_settings:
                Intent settings = new Intent(this, SettingsActivity.class);
                startActivity(settings);
                return true;
            case R.id.nav_sign_out:
                Intent sign_out = new Intent(this, AuthentificationActivity.class);
                startActivity(sign_out);
                return true;
        }
        return true;
    }

            /** * * * * * * * * * * * * *
             *     Google map API       *
             * <p>                      *
             * layout: activity_map.xml *
             * * * * * * * * * * * * *  */

    private void initGoogleMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapView = mapFragment.getView();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _map = googleMap;

        /**
         *  Move location button to the bottom right of the screen
         */
        if (mapView != null &&
                mapView.findViewById(Integer.parseInt("1")) != null) {
            // Get the button view
            View locationButton = ((View) mapView.findViewById(Integer.parseInt("1")).getParent()).findViewById(Integer.parseInt("2"));
            // and next place it, on bottom right (as Google Maps app)
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams)
                    locationButton.getLayoutParams();
            // position on right bottom
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP, 0);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE);
            layoutParams.setMargins(0, 0, 30, 30);
        }
        _map.setOnMyLocationButtonClickListener(this);
        _map.setOnMyLocationClickListener(this);
        enableMyLocation();
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
                    Manifest.permission.ACCESS_FINE_LOCATION, true);
        } else if (_map != null) {
            // Access to the location has been granted to the app.
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            _map.setMyLocationEnabled(true);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, MIN_TIME, MIN_DISTANCE, this);
        }
    }

    @Override
    public void onMyLocationClick(@NonNull Location location) {
        //Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onMyLocationButtonClick() {
        //Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }
        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.

            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15);
        _map.animateCamera(cameraUpdate);
        locationManager.removeUpdates(this);
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        if (mPermissionDenied) {
            // Permission was not granted, display error dialog.
            Toast.makeText(this, "You should grant location permission to get an optimal experience...", Toast.LENGTH_SHORT).show();
            mPermissionDenied = false;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) { }

    @Override
    public void onProviderEnabled(String provider) { }

    @Override
    public void onProviderDisabled(String provider) { }
}