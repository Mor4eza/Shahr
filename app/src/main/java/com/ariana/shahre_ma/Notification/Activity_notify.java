package com.ariana.shahre_ma.Notification;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.ariana.shahre_ma.Cards.Notify_Card_Adapter;
import com.ariana.shahre_ma.R;
import com.neno0o.lighttextviewlib.LightTextView;

import jp.wasabeef.recyclerview.animators.OvershootInRightAnimator;

public class Activity_notify extends ActionBarActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter Notify_list_Adapter;
    LightTextView hint;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notify);

        hint=new LightTextView(this);
        hint.setText("جدید");
        hint.setPosition(LightTextView.Position.LEFT_CORNER);

        mRecyclerView = (RecyclerView) findViewById(R.id.notify_recycle);
        mRecyclerView.setItemAnimator(new OvershootInRightAnimator());

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        Notify_list_Adapter = new Notify_Card_Adapter(this);
        mRecyclerView.setAdapter(Notify_list_Adapter);
        hint.setCurrentView(mRecyclerView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_notify, menu);
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
