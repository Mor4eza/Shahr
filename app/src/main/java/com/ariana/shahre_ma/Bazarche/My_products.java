package com.ariana.shahre_ma.Bazarche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.ariana.shahre_ma.R;

public class My_products extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
    }

   public void add_product(View v){

       Intent i = new Intent(getApplicationContext(),add_product.class);
       startActivity(i);

   }
}
