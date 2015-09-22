package com.ariana.shahre_ma.Bazarche;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetCollectionProductJson;
import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetProductJson;
import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetSubsetProductJson;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class Product_List extends ActionBarActivity {
    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView.Adapter Product_Adapter;
    public static ProgressBar pg;
    public static Button retry;
    FieldClass fc=new FieldClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list);

        pg=(ProgressBar)findViewById(R.id.pg_product_list);
        retry=(Button)findViewById(R.id.pl_retry);

        if(!fc.GetFilterProduct()) {
            HTTPGetProductJson httpGetProductJson = new HTTPGetProductJson(this);
            httpGetProductJson.setUrl_product(68, 0, 0, 1);
            httpGetProductJson.execute();
        }else{
            pg.setVisibility(View.GONE);
            fc.SetFilterProduct(false);
        }
        setCards();

        HTTPGetSubsetProductJson httpGetSubsetProductJson=new HTTPGetSubsetProductJson(this);
        httpGetSubsetProductJson.execute();

        HTTPGetCollectionProductJson httpGetCollectionProductJson=new HTTPGetCollectionProductJson(this);
        httpGetCollectionProductJson.execute();


    }

    private void setCards(){
        try {


            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_products);
            mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
            mRecyclerView.getItemAnimator().setAddDuration(1000);
            mRecyclerView.getItemAnimator().setChangeDuration(1000);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new GridLayoutManager(this,2);
            mRecyclerView.setLayoutManager(mLayoutManager);
            Product_Adapter = new Product_List_Adapter(this);
            ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(Product_Adapter);
            alphaAdapter.setDuration(400);
            mRecyclerView.setAdapter(alphaAdapter);
            Product_Adapter.notifyItemChanged(0);
            Product_Adapter.notifyDataSetChanged();
        }
        catch (Exception e){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_product__list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.ac_filter) {
            Intent i = new Intent(this.getApplicationContext(),FilterActivity.class);
            startActivity(i);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void retry(View view) {
        HTTPGetProductJson httpGetProductJson=new HTTPGetProductJson(this);
        httpGetProductJson.setUrl_product(68,0,0,1);
        httpGetProductJson.execute();
    }

    public void products(View view) {

        Intent i = new Intent(getApplicationContext(),My_products.class);
        startActivity(i);
    }
}
