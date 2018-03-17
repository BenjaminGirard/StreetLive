package com.example.tetard.streetlive;

import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.twitter.sdk.android.core.models.Image;
import com.twitter.sdk.android.core.models.Search;

public class MapActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {
    private DrawerLayout    _drawerLayout;
    private Toolbar         _toolbar;
    private GoogleMap       _map;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        initGoogleMaps();
        toolbarConfig();
        initImageButton();
    }

    private void searchViewConfig(Menu menu) {

        /**
         * Configuration search bar
         */
        SearchManager searchManager =
                (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = //(SearchView) MenuItemCompat.getActionView(search);
                (SearchView)menu.findItem(R.id.search).getActionView();
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

    public void initImageButton() {

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View hView =  navigationView.getHeaderView(0);
        ImageButton nav_user = hView.findViewById(R.id.profile_pic);

        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.forest);
        RoundedBitmapDrawable mDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
        mDrawable.setCircular(true);
        mDrawable.setColorFilter(ContextCompat.getColor(this, R.color.colorAccent), PorterDuff.Mode.DST_OVER);
        nav_user.setImageDrawable(mDrawable);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //ajoute les entrées de menu_test à l'ActionBar
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);

        /**
         *  change location in map with searchview
         */
        searchViewConfig(menu);
        return true;
    }

    private void initGoogleMaps() {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

    @Override
    public void onMapReady(GoogleMap googleMap) {
        _map = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        _map.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        _map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

}
