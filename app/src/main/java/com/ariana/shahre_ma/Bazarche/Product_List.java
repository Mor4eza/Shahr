package com.ariana.shahre_ma.Bazarche;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetCollectionProductJson;
import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetProductJson;
import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetSubsetProductJson;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyProfile.Log_In;
import com.ariana.shahre_ma.R;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class Product_List extends ActionBarActivity {
    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView.Adapter Product_Adapter;
    public static ProgressBar pg;
    public static Button retry;
    int page=1;
    FieldClass fc=new FieldClass();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__list);

        pg=(ProgressBar)findViewById(R.id.pg_product_list);
        retry=(Button)findViewById(R.id.pl_retry);


        if(!fc.GetFilterProduct()) {
            HTTPGetProductJson httpGetProductJson = new HTTPGetProductJson(this);
            httpGetProductJson.setUrl_product(68, 8,page, 1);
            httpGetProductJson.execute();
        }else{
            pg.setVisibility(View.GONE);
            fc.SetFilterProduct(false);
        }
        setCards();

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView
                        .getLayoutManager();
                int size=linearLayoutManager.getItemCount();
                int last = linearLayoutManager.findLastVisibleItemPosition();
                if (last +1 >= size){
                    HTTPGetProductJson httpGetProductJson = new HTTPGetProductJson(Product_List.this);
                    httpGetProductJson.setUrl_product(68, 8,page, 1);
                    httpGetProductJson.execute();
                    page++;
                    Log.i("size", String.valueOf(linearLayoutManager.getItemCount()));
                    Log.i("Last", String.valueOf(last));
                }


            }
        });

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
        }else if(id==R.id.ac_map){
            Intent i = new Intent(this.getApplicationContext(),ProductMapsActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    public void retry(View view) {
        HTTPGetProductJson httpGetProductJson=new HTTPGetProductJson(this);
        httpGetProductJson.setUrl_product(68,0,0,1);
        httpGetProductJson.execute();
    }

    public void products(View view) {
        Query query = new Query(this);

        if (query.getMemberId() > 0) // get member
        {
            Intent i = new Intent(getApplicationContext(), My_products.class);
            startActivity(i);

        } else {
            AlertDialog alertDialog = new AlertDialog.Builder(Product_List.this).create();
            alertDialog.setTitle("وارد شوید");
            alertDialog.setMessage("وارد حساب کاربری خود شوید و از تمامی امکانات برنامه استفاده کنید.");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Intent i = new Intent(getApplicationContext(), Log_In.class);
                    startActivity(i);
                    finish();
                }
            });
            alertDialog.setButton2("بعدا", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        }
    }
}
