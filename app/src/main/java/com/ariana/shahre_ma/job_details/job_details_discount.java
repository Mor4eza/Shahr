package com.ariana.shahre_ma.job_details;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ariana.shahre_ma.R;


public class job_details_discount extends FragmentActivity {



    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_discount);


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

        RelativeLayout like;
        RelativeLayout dislike;
        TextView tv_like;
        TextView tv_unlike;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_job_details_discount, container, false);
            like=(RelativeLayout)rootView.findViewById(R.id.like_button);
            dislike=(RelativeLayout)rootView.findViewById(R.id.dislike_button);
            tv_like=(TextView) rootView.findViewById(R.id.tv_discount_like);
            tv_unlike=(TextView) rootView.findViewById(R.id.tv_discount_unlike);

            like.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionDrawable drawable = (TransitionDrawable) like.getBackground();
                    drawable.startTransition(500);
                    like.setEnabled(false);
                }
            });

            dislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionDrawable drawable = (TransitionDrawable) dislike.getBackground();
                    drawable.startTransition(500);
                    dislike.setEnabled(false);
                }
            });
            return rootView;
        }

    }
}
