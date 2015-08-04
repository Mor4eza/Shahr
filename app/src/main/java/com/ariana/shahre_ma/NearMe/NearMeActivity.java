package com.ariana.shahre_ma.NearMe;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceSend.HTTPSendNearMeURL;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class NearMeActivity extends ActionBarActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    FieldDataBusiness fdb=new FieldDataBusiness();
    String Latitude[];
    String Longtitude[];
    String Market[];
    Double Rate[];
    Integer len;
    Integer id[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_me);
        setUpMapIfNeeded();

      /*  Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                    Uri.parse("http://maps.google.com/maps?daddr=35.688951,51.018301"));
        intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
        startActivity(intent);*/
/*

        Location locationA = new Location("point A");
        locationA.setLatitude(35.687543);
        locationA.setLongitude(51.019641);

        Location locationB = new Location("point B");
        locationB.setLatitude(35.688951);
        locationB.setLongitude(51.018301);

       float distance = locationA.distanceTo(locationB) ;
        Log.i("distance",String.valueOf(distance)+"M");*/
       // latitude=51.0096686&longitude=35.8357895&distance=0.01

        HTTPSendNearMeURL nearMeURL=new HTTPSendNearMeURL(this);
        nearMeURL.SetNearMe("35.8357895","51.0096686",0.1);
        nearMeURL.execute();



        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,new IntentFilter("near-me"));

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
        Latitude=new String[fdb.GetLatitudeBusiness().size()];
        Longtitude=new String[fdb.GetLongtiudeBusiness().size()];
        Market=new String[fdb.GetMarketBusiness().size()];
        Rate=new Double[fdb.GetRateBusiness().size()];
        len=fdb.GetLatitudeBusiness().size();
        id=new Integer[fdb.GetIdBusiness().size()];

        for (int i=0;i<fdb.GetIdBusiness().size();i++){
            Latitude[i]=fdb.GetLatitudeBusiness().get(i);
            Longtitude[i]=fdb.GetLongtiudeBusiness().get(i);
            Market[i]=fdb.GetMarketBusiness().get(i);
            //Rate[i]=fdb.GetRateBusiness();
            //id[i]=fdb.GetIdBusiness();
            Log.i("latitude",Latitude[i]);
            Log.i("longitude", Longtitude[i]);
            mMap.addMarker(new MarkerOptions().position(new LatLng(Double.valueOf(Latitude[i]), Double.valueOf(Longtitude[i]))).title("\u200e"+Market[i]));
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
        }

    };
}
