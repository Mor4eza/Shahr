package com.ariana.shahre_ma.MyBusiness;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import com.ariana.shahre_ma.R;
public class My_Business extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__business);
    }

    public void add_business(View v){
        Intent i = new Intent(getApplicationContext(),Add_business.class);
        startActivity(i);

    }
}
