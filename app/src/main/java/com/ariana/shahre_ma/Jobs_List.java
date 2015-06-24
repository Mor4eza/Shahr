package com.ariana.shahre_ma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ariana.shahre_ma.Cards.job_list_cards_adapter;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.job_details.Job_details;


public class Jobs_List extends ActionBarActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter job_list_Adapter;


    FieldClass fc=new FieldClass();



    TextView tvMarket;
    TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs__list);
        
                setCards();


       // mDrawer = MenuDrawer.attach(this, Position.RIGHT);

        /*mDrawer.setContentView(R.layout.activity_jobs__list);
        mDrawer.setMenuView(R.layout.activity_jobs);*/

    }
        public void img_click(View v){

          /* tvDescription=(TextView) findViewById(R.id.tv_address);
            tvMarket=(TextView) findViewById(R.id.tv_title);

            fc.SetMarket_Business(tvMarket.getText().toString());
            fc.SetAddress_Business(tvDescription.getText().toString());*/

            Intent i = new Intent(getApplicationContext(), Job_details.class);
            startActivity(i);

        }





    private void setCards(){
        try {


            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_Jobs);
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.getItemAnimator().setAddDuration(5000);
            mRecyclerView.getItemAnimator().setChangeDuration(5000);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            job_list_Adapter = new job_list_cards_adapter(this);
            mRecyclerView.setAdapter(job_list_Adapter);

        }
        catch (Exception e){}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Map) {
          //  Intent i = new Intent(this,MapsActivity.class);
          //  startActivity(i);




            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
