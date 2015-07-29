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
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
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
    FieldDataBusiness fdb=new FieldDataBusiness();
    Integer cityid=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        KeySettings setting=new KeySettings(getApplicationContext());

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



        String lat[] = new String[fc.GetCount_Business()];
        String Longt[] = new String[fc.GetCount_Business()];


        if(fdb.GetMarketBusiness().size()==0) {
            DataBaseSqlite mydb = new DataBaseSqlite(this);
            Cursor allrows = mydb.select_AllBusiness(fc.GetBusiness_SubsetIdb(), cityid);
            Integer l = 0;
            if (allrows.moveToFirst()) {

                do {
                    lat[l] = allrows.getString(15);
                    Longt[l] = allrows.getString(16);
                    l++;

                } while (allrows.moveToNext());
            }
        }
        else {
            for (int i = 0; i < fdb.GetMarketBusiness().size(); i++) {
                Longt[i] = fdb.GetLongtiudeBusiness().get(i);
                lat[i] = fdb.GetLatitudeBusiness().get(i);

            }
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

    private void setUpMap() {

        Integer l=0;
        Latitude=new String[fc.GetCount_Business()];
        Longtitude=new String[fc.GetCount_Business()];
        Market=new String[fc.GetCount_Business()];
        Rate=new Double[fc.GetCount_Business()];
        len=fc.GetCount_Business();
        id=new Integer[fc.GetCount_Business()];

       try {
        DataBaseSqlite mydb = new DataBaseSqlite(this);
        Cursor allrows = mydb.select_AllBusiness(fc.GetBusiness_SubsetIdb(),cityid);



            if(fdb.GetMarketBusiness().size()==0) {
                if (allrows.moveToFirst()) {

                    do {

                        Market[l] = allrows.getString(1);
                        Longtitude[l] = allrows.getString(15);
                        Latitude[l] = allrows.getString(16);
                        Rate[l] = allrows.getDouble(29);
                        id[l] = allrows.getInt(0);



                        l++;

                    } while (allrows.moveToNext());
                }
            }
            else {

                for (int i = 0; i < fdb.GetMarketBusiness().size(); i++) {
                    id[i] = fdb.GetIdBusiness().get(i);
                    Market[i] = fdb.GetMarketBusiness().get(i);
                    Longtitude[i] = fdb.GetLongtiudeBusiness().get(i);
                    Latitude[i] = fdb.GetLatitudeBusiness().get(i);
                    Rate[i] = fdb.GetRateBusiness().get(i);
                }
            }


            for (int i = 0; i < len; i++) {

                    Log.i("Longtitude",Longtitude[i]);
                    Log.i("Latitude",Latitude[i]);

                    Marker  marker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(Longtitude[i]), Double.parseDouble(Latitude[i] ))).title("\u200e"+Market[i]).snippet(String.valueOf(Rate[i])));
                    marker.setData(id[i]);
                    marker.showInfoWindow();



            }


       }catch (Exception e)
        {
            Log.i("ExceptionMAPS",e.toString());
        }

    }
}
