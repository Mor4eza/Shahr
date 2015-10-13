package com.ariana.shahre_ma.MyBusiness;

import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Toast;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;

public class My_Business_Map extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    FieldClass fc=new FieldClass();
    Double selecteLat=0.0;
    Double selecteLong=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_business_map);
        setUpMapIfNeeded();
        mMap.clear();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        Toast.makeText(getApplicationContext(),"انگشت خود را بر روی موقیعت مورد نظر چند لحظه نگه دارید",Toast.LENGTH_LONG).show();
        TrackerSettings settings =
                new TrackerSettings()
                        .setUseGPS(true)
                        .setUseNetwork(true)
                        .setTimeout(20000)
                        .setUsePassive(true);
        new LocationTracker(My_Business_Map.this, settings) {

            @Override
            public void onLocationFound(Location location) {
                // Do some stuff when a new GPS Location has been found
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title("\u200e" + "اینجا"));
                marker.setDraggable(true);
                stopListen();
                selecteLat=location.getLatitude();
                selecteLong=location.getLongitude();
            }

            @Override
            public void onTimeout() {
                Toast.makeText(getApplicationContext(), "مکان شما شناسایی نشد...!", Toast.LENGTH_LONG).show();
            }
        };

        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {
            @Override
            public void onMapLongClick(LatLng latLng) {
                mMap.clear();
                Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(latLng.latitude,latLng.longitude)).title("\u200e" + "اینجا"));
                marker.showInfoWindow();
                selecteLat=latLng.latitude;
                selecteLong=latLng.longitude;

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }


    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

            }
        }
    }

    public void save_position(View view) {
        if (selecteLat !=0.0 && selecteLong !=0.0) {
            fc.SetLatitude_Business(selecteLat);
            fc.SetLongtiude_Business(selecteLong);
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),"موقعیتی را انتخاب کنید",Toast.LENGTH_LONG).show();
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
}
