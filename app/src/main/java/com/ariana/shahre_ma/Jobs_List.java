package com.ariana.shahre_ma;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.ariana.shahre_ma.job_details.Job_details;


public class Jobs_List extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs__list);
        ImageView img_m =(ImageView)findViewById(R.id.image_market);

        img_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i =new Intent(getApplicationContext(),Job_details.class);
                startActivity(i);

            }
        });
    }



}
