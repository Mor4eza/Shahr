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
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.MarkerOptions;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceSend.HTTPSendNearMeURL;
import com.ariana.shahre_ma.job_details.Job_details;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;

public class NearMeActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    FieldDataBusiness fdb=new FieldDataBusiness();
    FieldClass fc=new FieldClass();
    Integer len=0;
    ProgressBar map_progress;
    LocationManager locationManager;
    Double curLat=0.0;
    Double curLong=0.0;

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
        LocalBroadcastManager.getInstance(this).registerReceiver(filters, new IntentFilter("filtered"));

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

            @Override
            public View getInfoWindow(Marker marker) {

                final View window = getLayoutInflater().inflate(
                        R.layout.jobs_info_windows, null);


                String title = marker.getTitle();
                double rate = Double.parseDouble(marker.getSnippet());
                Integer Id = marker.getData();
                TextView tv_title = (TextView) window.findViewById(R.id.tv_info_title);
                RatingBar Rates = (RatingBar) window.findViewById(R.id.info_rates);
                TextView tv_id = (TextView) window.findViewById(R.id.tv_info_id);
                tv_id.setText(String.valueOf(Id));

                Rates.setRating((float) rate);
                tv_title.setText(title);
                return window;
            }

            @Override
            public View getInfoContents(Marker marker) {

                return null;
            }
        });


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                fc.SetMarket_Business(marker.getTitle().toString());
                fc.SetBusiness_Id((Integer) marker.getData());
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(), Job_details.class);
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
            mMap = ((com.androidmapsextensions.SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getExtendedMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
               // setUpMap();
            }
        }
    }

    public void filtered(){
        mMap.clear();
        for(int i=0;i<fdb.GetMarketBusiness().size();i++)
        {
            Marker marker=   mMap.addMarker(new MarkerOptions().position(new LatLng(fdb.GetLatitudeBusiness().get(i), fdb.GetLongtiudeBusiness().get(i))).title("\u200e" + fdb.GetMarketBusiness().get(i)).snippet(String.valueOf(fdb.GetRateBusiness().get(i))));
            marker.setData(fdb.GetIdBusiness().get(i));
           // marker.animatePosition(new LatLng(Double.valueOf(rows.getString(15)), Double.valueOf(rows.getString(16))));
        }

    }

    private void setUpMap() {
        SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(this);
            Cursor rows = sdb.select_BusinessSearchNearMe(curLat,curLong,0.01);
            Log.i("Count", String.valueOf(rows.getCount()));
            if (rows.moveToFirst()) {
                do {
                    Marker marker=   mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(rows.getString(15)), Double.valueOf(rows.getString(16)))).title("\u200e" + rows.getString(1)).snippet(String.valueOf(rows.getDouble(30))));
                    marker.setData(rows.getInt(0));
                    marker.animatePosition(new LatLng(Double.valueOf(rows.getString(15)), Double.valueOf(rows.getString(16))));
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
    private BroadcastReceiver filters = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra("message");
            Log.i("receiver", "Got message: " + message);
           filtered();
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
                        SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(getApplicationContext());
                        Cursor rows = sdb.select_BusinessSearchNearMe(location.getLatitude(),location.getLongitude(),0.002);
                        Log.i("Count",String.valueOf(rows.getCount()));
                        if (rows.moveToFirst()) {
                        do
                        {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(rows.getString(15)), Double.valueOf(rows.getString(16)))).title("\u200e" + rows.getString(1)));
                            len++;
                        } while (rows.moveToNext());

                    }
                    map_progress.setVisibility(View.INVISIBLE);
                }else {

                    HTTPSendNearMeURL nearMeURL = new HTTPSendNearMeURL(getApplicationContext());
                    nearMeURL.SetNearMe(String.valueOf(location.getLatitude()), String.valueOf(location.getLongitude()), 0.002);
                    nearMeURL.execute();
                        curLat=location.getLatitude();
                        curLong=location.getLongitude();
                        fc.SetcurLatitude(location.getLatitude());
                        fc.SetcurLongtitude(location.getLongitude());
                    }

                    stopListen();
                }

                @Override
                public void onTimeout() {
                    Toast.makeText(getApplicationContext(), "مکان شما شناسایی نشد...!", Toast.LENGTH_LONG).show();
                    curLat=0.0;
                    curLong=0.0;
                }
            };


        }


    }
}
