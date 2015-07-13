package com.ariana.shahre_ma;

import android.content.Intent;
import android.database.Cursor;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.MarkerOptions;
import com.androidmapsextensions.SupportMapFragment;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyInterest.Interest_Adapter;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.job_details.Job_details;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;

public class MapsActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    String Latitude[];
    String Longtitude[];
    String Market[];
    Double Rate[];
    Integer len;
    Integer id[];
    FieldClass fc=new FieldClass();
    Query query=new Query(this);
    Integer cityid=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        KeySettings setting=new KeySettings(getApplicationContext());
        Integer cityid=0;
        cityid=query.getCityId(setting.getCityName());

        setUpMapIfNeeded();
        mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {


            @Override
            public View getInfoWindow(Marker marker) {

                final View window = getLayoutInflater().inflate(
                        R.layout.jobs_info_windows, null);
                KeySettings setting=new KeySettings(getApplicationContext());
                Integer cityid=0;
                cityid=query.getCityId(setting.getCityName());
                DataBaseSqlite mydb = new DataBaseSqlite(getApplicationContext());
                Cursor allrows = mydb.select_AllBusiness(fc.GetBusiness_SubsetIdb(),cityid);
                len=fc.GetCount_Business();


                String title = marker.getTitle();
                double rate = Double.parseDouble(marker.getSnippet());
                Integer Id=marker.getData();
                TextView tv_title = (TextView) window.findViewById(R.id.tv_info_title);
                RatingBar Rates = (RatingBar) window.findViewById(R.id.info_rates);
                TextView tv_id=(TextView)window.findViewById(R.id.tv_info_id);
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








        // DataBase



        DataBaseSqlite mydb = new DataBaseSqlite(this);
        Cursor allrows = mydb.select_AllBusiness(fc.GetBusiness_SubsetIdb(),cityid);
        String lat[] = new String[fc.GetCount_Business()];
        String Longt[] = new String[fc.GetCount_Business()];
        Integer l=0;
            if (allrows.moveToFirst()) {

                do {
                    lat[l] =allrows.getString(15);
                    Longt[l] =allrows.getString(16);
                    l++;

                } while (allrows.moveToNext());
            }

        TrackerSettings settings =
                new TrackerSettings()
                        .setUseGPS(true)
                        .setUseNetwork(true)
                        .setTimeout(20000)
                        .setUsePassive(true);

        new LocationTracker(MapsActivity.this, settings) {

            @Override
            public void onLocationFound(Location location) {
                // Do some stuff when a new GPS Location has been found
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15f));
                stopListen();
            }

            @Override
            public void onTimeout() {
                Toast.makeText(getApplicationContext(),"مکان شما شناسایی نشد...!",Toast.LENGTH_LONG).show();
            }
        };


        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat[0]), Double.parseDouble(Longt[0])), 12.0f), 2000, null);

         mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                fc.SetMarket_Business(marker.getTitle().toString());
                fc.SetBusiness_Id((Integer) marker.getData());
                Toast.makeText(getApplicationContext(), marker.getTitle(), Toast.LENGTH_LONG).show();
                Intent i=new Intent(getApplicationContext(), Job_details.class);
                startActivity(i);
            }
         });
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getExtendedMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        Integer l=0;
        Latitude=new String[fc.GetCount_Business()];
        Longtitude=new String[fc.GetCount_Business()];
        Market=new String[fc.GetCount_Business()];
        Rate=new Double[fc.GetCount_Business()];
        len=fc.GetCount_Business();
        id=new Integer[fc.GetCount_Business()];

        DataBaseSqlite mydb = new DataBaseSqlite(this);
        Cursor allrows = mydb.select_AllBusiness(fc.GetBusiness_SubsetIdb(),cityid);
       try {


            if (allrows.moveToFirst()) {

                do {
                        Market[l] = allrows.getString(1);
                        Latitude[l] = allrows.getString(15);
                        Longtitude[l] = allrows.getString(16);

                        Rate[l] = allrows.getDouble(29);
                        id[l]=allrows.getInt(0);

                        Log.v("id", String.valueOf(Rate[l]));

                    l++;

                } while (allrows.moveToNext());
            }



            for (int i = 0; i < len; i++) {

                    Marker  marker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(Latitude[i]), Double.parseDouble(Longtitude[i] ))).title("\u200e"+Market[i]).snippet(String.valueOf(Rate[i])));
                    marker.setData(id[i]);
                    marker.showInfoWindow();
                    Log.i("id",String.valueOf(id[i]));


            }


       }catch (Exception e)
        {
            Log.i("ExceptionMAPS",e.toString());
        }

    }
}
