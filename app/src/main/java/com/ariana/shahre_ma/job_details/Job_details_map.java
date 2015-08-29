package com.ariana.shahre_ma.job_details;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class Job_details_map extends FragmentActivity {

    FieldClass fc=new FieldClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_map);



        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }




    }




    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        FieldClass fc=new FieldClass();
        ImageButton route;
        GoogleMap map;
        public PlaceholderFragment() {
        }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_job_details_map, container, false);
            route=(ImageButton)rootView.findViewById(R.id.btn_route);



            try {
                int v =getActivity().getPackageManager().getPackageInfo("com.google.android.gms", 0).versionCode;
                // Showing status
                if (v >=6587000) {
                    if (map==null){
                        map = ((MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.map))
                                .getMap();
                        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(fc.GetLatitude_Business(), fc.GetLongtiude_Business()), 15f));
                        Marker marker = map.addMarker(new MarkerOptions().position(new LatLng(fc.GetLatitude_Business(), fc.GetLongtiude_Business())).title("\u200e" + fc.GetMarket_Business()));
                        marker.showInfoWindow();
                        map.getUiSettings().setMapToolbarEnabled(false);
                        map.getUiSettings().setZoomControlsEnabled(true);
                    }
                } else {
                    Toast.makeText(getActivity(),"نسخه Google Play Service شما قدیمی میباشد" ,Toast.LENGTH_LONG).show();
                }

            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }



            route.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                            Uri.parse("http://maps.google.com/maps?daddr="+fc.GetLatitude_Business().toString()+","+fc.GetLongtiude_Business().toString()));
                  //  intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    startActivity(intent);

                }
            });
            return rootView;
        }

        @Override
        public void onDestroyView() {
            super.onDestroyView();
            try {

                if (map != null) {
                    getActivity().getFragmentManager().beginTransaction()
                            .remove(getActivity().getFragmentManager().findFragmentById(R.id.map)).commit();
                    map = null;
                }
            }catch (Exception e){

            }
        }
    }
}
