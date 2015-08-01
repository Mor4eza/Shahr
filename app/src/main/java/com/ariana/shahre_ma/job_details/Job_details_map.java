package com.ariana.shahre_ma.job_details;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;


public class Job_details_map extends FragmentActivity {



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
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_job_details_map, container, false);
            route=(ImageButton)rootView.findViewById(R.id.btn_route);

/*
            TrackerSettings settings =
                    new TrackerSettings()
                            .setUseGPS(true)
                            .setUseNetwork(true)
                            .setTimeout(20000)
                            .setUsePassive(true);

            new LocationTracker(getActivity(), settings) {

                @Override
                public void onLocationFound(final Location location) {
                    // Do some stuff when a new GPS Location has been found

                    route.setEnabled(true);
                    stopListen();
                }

                @Override
                public void onTimeout() {
                  route.setEnabled(false);
                }
            };

            */


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

    }
}
