package com.ariana.shahre_ma.MyBusiness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.ariana.shahre_ma.R;

public class Add_New_Business extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_business);



    }



    public void add_business(View view) {

    }

    public void select_map(View view) {
        Intent i = new Intent(getBaseContext(),My_Business_Map.class);
        startActivity(i);

    }
}
