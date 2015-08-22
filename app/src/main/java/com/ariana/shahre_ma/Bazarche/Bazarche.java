package com.ariana.shahre_ma.Bazarche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCollectionProductJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetSubsetProductJson;

public class Bazarche extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazarche);

        HTTPGetSubsetProductJson httpGetSubsetProductJson=new HTTPGetSubsetProductJson(this);
        httpGetSubsetProductJson.execute();

        HTTPGetCollectionProductJson httpGetCollectionProductJson=new HTTPGetCollectionProductJson(this);
        httpGetCollectionProductJson.execute();
    }

    public void products(View view) {

        Intent i = new Intent(getApplicationContext(),My_products.class);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bazarche, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
