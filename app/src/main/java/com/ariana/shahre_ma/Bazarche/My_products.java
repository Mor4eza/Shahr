package com.ariana.shahre_ma.Bazarche;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Bazarche.WebServiceGet.HTTPGetProductMemberJson;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class My_products extends ActionBarActivity {


    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView.Adapter Product_Adapter;
    ProgressBar pb_myProduct;
    Query query=new Query(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);
        pb_myProduct=(ProgressBar)findViewById(R.id.pb_my_product);
        LocalBroadcastManager.getInstance(this).registerReceiver(mProductMemberReciver, new IntentFilter("ProductMember"));

        HTTPGetProductMemberJson httpGetProductMemberJson=new HTTPGetProductMemberJson(this);
        httpGetProductMemberJson.setMemberId(query.getMemberId());
        httpGetProductMemberJson.execute();


    }

   public void add_product(View v){

       Intent i = new Intent(getApplicationContext(),add_product.class);
       startActivity(i);

   }

    private void setCards(){
        try {


            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_product);
            mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
            mRecyclerView.getItemAnimator().setAddDuration(1000);
            mRecyclerView.getItemAnimator().setChangeDuration(1000);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            Product_Adapter = new My_Product_Adapter(this);
            ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(Product_Adapter);
            alphaAdapter.setDuration(400);
            mRecyclerView.setAdapter(alphaAdapter);
            Product_Adapter.notifyItemChanged(0);
            Product_Adapter.notifyDataSetChanged();
        }
        catch (Exception e){}
    }

    private BroadcastReceiver mProductMemberReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            setCards();
            pb_myProduct.setVisibility(View.GONE);
        }
    };

}
