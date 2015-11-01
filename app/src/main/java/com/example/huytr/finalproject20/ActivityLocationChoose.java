package com.example.huytr.finalproject20;

import android.location.Location;
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

public class ActivityLocationChoose extends AppCompatActivity {

    Toolbar toolbar;
    Marker marker;
    GoogleMap googleMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_choose);

        toolbar = (Toolbar) findViewById(R.id.Toolbar);
        toolbar.setTitle("Choose Location");
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        findViewById(R.id.ActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

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

        LatLng currentLocation = new LatLng(ActivityTransactionAdd.lat , ActivityTransactionAdd.lng);

        marker = googleMap.addMarker(new MarkerOptions()
                .position(currentLocation).title("Current Location").snippet(
                        "(" +
                                String.format("%.3f", new BigDecimal(ActivityTransactionAdd.lat)) + " , " +
                                String.format("%.3f" , new BigDecimal(ActivityTransactionAdd.lng)) +
                                ")"
                ));
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation, 16.18f));
        UiSettings mapUI = googleMap.getUiSettings();
        mapUI.setAllGesturesEnabled(true);
        mapUI.setCompassEnabled(true);
        mapUI.setZoomControlsEnabled(true);
        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if (marker != null)
                    marker.remove();
                ActivityTransactionAdd.lat = latLng.latitude;
                ActivityTransactionAdd.lng = latLng.longitude;
                marker = googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .title("Current Location")
                        .snippet("(" +
                                String.format("%.3f", new BigDecimal(ActivityTransactionAdd.lat)) + " , " +
                                String.format("%.3f", new BigDecimal(ActivityTransactionAdd.lng)) +
                                ")"));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location_choose, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }
}
