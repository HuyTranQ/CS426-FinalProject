package com.example.huytr.finalproject20;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.math.BigDecimal;

public class ActivityLocationView extends AppCompatActivity {

    Toolbar toolbar;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_view);

        toolbar = (Toolbar) findViewById(R.id.Toolbar);
        toolbar.setTitle("Choose Location");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);


        SetUpMap();
    }

    public void SetUpMap()
    {
        if (googleMap == null)
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.GoogleMap)).getMap();
        if (googleMap == null)
            return;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setIndoorEnabled(false);

        Intent intent = getIntent();

        LatLng currentLocation = new LatLng(intent.getDoubleExtra("lat" , 0f) , intent.getDoubleExtra("lng", 0f));
        Marker marker = googleMap.addMarker(new MarkerOptions()
                .position(currentLocation)
                .title(Wallet.current.currency + " " + String.valueOf(intent.getIntExtra("money", 0)))
                .snippet(intent.getStringExtra("category")));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16.18f));
        UiSettings mapUI = googleMap.getUiSettings();
        mapUI.setAllGesturesEnabled(true);
        mapUI.setCompassEnabled(true);
        mapUI.setZoomControlsEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                // This activity is NOT part of this app's task, so create a new task
                // when navigating up, with a synthesized back stack.
                TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                                // Navigate up to the closest parent
                        .startActivities();
            } else {
                // This activity is part of this app's task, so simply
                // navigate up to the logical parent activity.
                NavUtils.navigateUpTo(this, upIntent);
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
