package com.ariana.shahre_ma.Notification;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.ariana.shahre_ma.Cards.Notify_Card_Adapter;
import com.ariana.shahre_ma.R;
import com.tsengvn.typekit.TypekitContextWrapper;

import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;

public class Activity_notify extends ActionBarActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter Notify_list_Adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);



        mRecyclerView = (RecyclerView) findViewById(R.id.notify_recycle);
        mRecyclerView.setItemAnimator(new OvershootInRightAnimator());

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Notify_list_Adapter = new Notify_Card_Adapter(this);
        mRecyclerView.setAdapter(Notify_list_Adapter);

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
