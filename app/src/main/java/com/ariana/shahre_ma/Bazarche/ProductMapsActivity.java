package com.ariana.shahre_ma.Bazarche;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.MarkerOptions;
import com.androidmapsextensions.SupportMapFragment;
import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetAroundMeProduct;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;

public class ProductMapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    FieldDataBase fdb=new FieldDataBase();
    FieldClass fc=new FieldClass();
    ProgressBar pbMap;
    LocationManager locationManager;
    Double curLat=0.0;
    Double curLong=0.0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_maps);
        setUpMapIfNeeded();
        pbMap=(ProgressBar)findViewById(R.id.map_progress);
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        checkLoaction();
        LocalBroadcastManager.getInstance(this).registerReceiver(nearReceiver, new IntentFilter("product-near"));

        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {
                fc.SetProductId((Integer) marker.getData());
                Intent i=new Intent(ProductMapsActivity.this, product_Details.class);
                startActivity(i);

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
                    .getExtendedMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {

            }
        }
    }

    private void setUpMap() {
        Log.i("size", String.valueOf(fdb.getName_Product().size()));
        for (int i=0;i<fdb.getName_Product().size();i++) {
           mMap.addMarker(new MarkerOptions().position(new LatLng(fdb.getLatitude_Product().get(i),fdb.getLongtiude_Product().get(i))).title("\u200e"+fdb.getName_Product().get(i)).snippet(fdb.getprice_Product().get(i).toString()).data(fdb.getId_Product().get(i)));

        }
    }

    private BroadcastReceiver nearReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            setUpMap();
            pbMap.setVisibility(View.INVISIBLE);
        }
    };

    public void checkLoaction() {


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception ex) {
        }

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception ex) {
        }

        if (!gps_enabled && !network_enabled) {
            // notify user
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("برای شناسایی مکان فعلی شما،لطفا موقعیت را فعال کنید");
            dialog.setPositiveButton(("باشه"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(myIntent);
                    //get gps
                }
            });
            dialog.setNegativeButton("انصراف", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    finish();
                }
            });
            dialog.show();

        } else {
            TrackerSettings settings =
                    new TrackerSettings()
                            .setUseGPS(true)
                            .setUseNetwork(true)
                            .setTimeout(20000)
                            .setUsePassive(true);

            new LocationTracker(ProductMapsActivity.this, settings) {

                @Override
                public void onLocationFound(Location location) {
                    // Do some stuff when a new GPS Location has been found
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));

                    HTTPGetAroundMeProduct aroundMeProduct= new HTTPGetAroundMeProduct(ProductMapsActivity.this);
                    aroundMeProduct.setUrl_product(location.getLatitude(),location.getLongitude(),0.2);
                    aroundMeProduct.execute();

                    stopListen();
                }

                @Override
                public void onTimeout() {
                    Toast.makeText(getApplicationContext(), "مکان شما شناسایی نشد...!", Toast.LENGTH_LONG).show();
                    curLat = 0.0;
                    curLong = 0.0;
                }
            };


        }
    }
}
