package com.ariana.shahre_ma;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.job_details.Job_details;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    String Latitude[];
    String Longtitude[];
    String Market[];
    Double Rate[];
    Integer len;
    FieldClass fc=new FieldClass();
    Query query=new Query(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        setUpMapIfNeeded();
   /*     mMap.setMyLocationEnabled(true);
        mMap.getUiSettings().setMapToolbarEnabled(false);
        mMap.getUiSettings().setZoomControlsEnabled(true);*/

        DataBaseSqlite mydb = new DataBaseSqlite(this);
        Cursor allrows = mydb.select_AllBusiness(fc.GetBusiness_SubsetIdb());
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
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.parseDouble(lat[0]),Double.parseDouble(Longt[0])), 12.0f), 2000, null);


         mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                fc.SetMarket_Business(marker.getTitle().toString());
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
                    .getMap();
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

        DataBaseSqlite mydb = new DataBaseSqlite(this);
        Log.i("SubsetID",String.valueOf(fc.GetBusiness_SubsetIdb()) );
        Cursor allrows = mydb.select_AllBusiness(fc.GetBusiness_SubsetIdb());
       try {


            if (allrows.moveToFirst()) {

                do {
                    Log.i("Latitude", allrows.getString(15));



                        Market[l] = allrows.getString(1);
                        Latitude[l] = allrows.getString(15);
                        Longtitude[l] = allrows.getString(16);
                        Log.i("Latitude", String.valueOf(Latitude[l]));
                        Log.i("Longtitude", String.valueOf(Longtitude[l]));
                        Log.i("Market", String.valueOf(Market[l]));
                        Rate[l] = allrows.getDouble(29);
                        Log.i("Rate", String.valueOf(Rate[l]));

                    l++;

                } while (allrows.moveToNext());
            }



            for (int i = 0; i < len; i++) {

                    Marker  marker = mMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(Latitude[i]), Double.parseDouble(Longtitude[i] ))).title("\u200e"+Market[i]).snippet(String.valueOf(Rate[i])));
                    marker.showInfoWindow();
            }


       }catch (Exception e)
        {
            Log.i("ExceptionMAPS",e.toString());
        }

    }
}
