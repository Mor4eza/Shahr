package com.ariana.shahre_ma.Search;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.androidmapsextensions.GoogleMap;
import com.androidmapsextensions.Marker;
import com.androidmapsextensions.MarkerOptions;
import com.androidmapsextensions.SupportMapFragment;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.job_details.Job_details;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;

public class SearchMap extends AppCompatActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    FieldClass fc=new FieldClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_map);
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
        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(Marker marker) {

                fc.SetMarket_Business(marker.getTitle().toString());
                fc.SetBusiness_Id((Integer) marker.getData());
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
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        SelectDataBaseSqlite sdb=new SelectDataBaseSqlite(this);
        Cursor rows=sdb.select_TableSearch();
        if (rows.moveToFirst()) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(Double.valueOf(rows.getString(15)), Double.valueOf(rows.getString(16))), 15f));

            do {
                Marker marker=mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(rows.getString(15)), Double.valueOf(rows.getString(16)))).title("\u200e" + rows.getString(1)).snippet(String.valueOf(rows.getDouble(30))));
                marker.setData(rows.getInt(0));
                marker.showInfoWindow();
            }while (rows.moveToNext());

        }
    }
}
