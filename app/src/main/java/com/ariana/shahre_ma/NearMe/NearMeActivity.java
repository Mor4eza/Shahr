package com.ariana.shahre_ma.NearMe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceSend.HTTPSendNearMeURL;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;

public class NearMeActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    FieldDataBusiness fdb=new FieldDataBusiness();
    String Latitude[];
    String Longtitude[];
    String Market[];
    Double Rate[];
    Integer len=0;
    ProgressBar map_progress;
    LocationManager locationManager;
    Integer id[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_me);
        setUpMapIfNeeded();
        checkLoaction();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        map_progress=(ProgressBar)findViewById(R.id.map_progress);
        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("near-me"));

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
               // setUpMap();
            }
        }
    }


    private void setUpMap() {
            DataBaseSqlite db = new DataBaseSqlite(this);
            Cursor rows = db.select_BusinessSearchNearMe(35.8357895,51.0096686,0.01);
            Log.i("Count", String.valueOf(rows.getCount()));
            if (rows.moveToFirst()) {
                do {
                    mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(rows.getString(15)), Double.valueOf(rows.getString(16)))).title("\u200e" + rows.getString(1)));
                    len++;
                } while (rows.moveToNext());
            }
        }






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_near_me, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter:
              FilterDialog dialog=new FilterDialog(this);
                dialog.show();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.i("receiver", "Got message: " + message);
            setUpMap();
            map_progress.setVisibility(View.INVISIBLE);
        }

    };

    public void checkLoaction(){


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gps_enabled = false;
        boolean network_enabled = false;

        try {
            gps_enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch(Exception ex) {}

        try {
            network_enabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch(Exception ex) {}

        if(!gps_enabled && !network_enabled) {
            // notify user
            final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("برای شناسایی مکان فعلی شما،لطفا موقعیت را فعال کنید");
            dialog.setPositiveButton(("باشه"), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    Intent myIntent = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS);
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

        }else{
            TrackerSettings settings =
                    new TrackerSettings()
                            .setUseGPS(true)
                            .setUseNetwork(true)
                            .setTimeout(20000)
                            .setUsePassive(true);

            new LocationTracker(NearMeActivity.this, settings) {

                @Override
                public void onLocationFound(Location location) {
                    // Do some stuff when a new GPS Location has been found
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));

                    NetState ns=new NetState(getApplicationContext());
                    if(!ns.checkInternetConnection()) {
                        DataBaseSqlite db = new DataBaseSqlite(getApplicationContext());
                        Cursor rows = db.select_BusinessSearchNearMe(35.8357895,51.0096686,0.002);
                        Log.i("Count",String.valueOf(rows.getCount()));
                        if (rows.moveToFirst()) {
                            do {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(rows.getString(15)), Double.valueOf(rows.getString(16)))).title("\u200e" + rows.getString(1)));
                                len++;
                            } while (rows.moveToNext());

                        }
                        map_progress.setVisibility(View.INVISIBLE);
                    }else {

                        HTTPSendNearMeURL nearMeURL = new HTTPSendNearMeURL(getApplicationContext());
                        nearMeURL.SetNearMe("35.8357895", "51.0096686", 0.001);
                        nearMeURL.execute();
                    }

                    stopListen();
                }

                @Override
                public void onTimeout() {
                    Toast.makeText(getApplicationContext(), "مکان شما شناسایی نشد...!", Toast.LENGTH_LONG).show();
                }
            };


        }


    }
}
