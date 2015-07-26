package com.ariana.shahre_ma.MyInterest;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.ListExpand.Continent;
import com.ariana.shahre_ma.ListExpand.Country;
import com.ariana.shahre_ma.MyCity.TotalListener;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Service.MyReceiver;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetInterestJson;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostInterestJson;

import java.util.ArrayList;

public class My_Interest extends ActionBarActivity implements TotalListener {
    Query query;
    ExpandableListView expListView;
    int lastExpandedPosition = -1;
    Integer Id_co;
    Integer Collection_ID_subset;
    HTTPGetBusinessJson httpbusin;
    NetState ns;
    private ExpandableListView myList;
    private ArrayList<Continent> continentList = new ArrayList<Continent>();
    ArrayList<Country> countryList;

    Continent continent;
    Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__interest);
        query = new Query(this);

        httpbusin = new HTTPGetBusinessJson(this);
        ns = new NetState(this);


        Intent myIntent = new Intent(this, MyReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, myIntent, 0);

        expListView = (ExpandableListView) findViewById(R.id.expand_my_interest);

        Interest_Adapter adapter = new Interest_Adapter(this);
        adapter.setmListener(this);
        expListView.setAdapter(adapter);



        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            @Override
            public void onGroupExpand(int groupPosition) {
                if (lastExpandedPosition != -1
                        && groupPosition != lastExpandedPosition) {
                    expListView.collapseGroup(lastExpandedPosition);
                }
                lastExpandedPosition = groupPosition;
            }
        });

    }



    public void SendPostInterest(View v) {
       SqliteTOjson json=new SqliteTOjson(this);
        Log.i("",json.getSqliteInterestTOjson());

        HTTPPostInterestJson httpinterest=new HTTPPostInterestJson(this);
        httpinterest.SetInterest_Json(json.getSqliteInterestTOjson());
        httpinterest.execute();


    }


    public void GetPostInterest(View v) {


        HTTPGetInterestJson httpinterest = new HTTPGetInterestJson(this);
        httpinterest.SetUrl_Interest(query.getMemberId());

        httpinterest.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jobs, menu);



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

            return true;
        }


        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTotalChanged(int sum) {

    }

    @Override
    public void expandGroupEvent(int groupPosition, boolean isExpanded) {
        if(isExpanded)
            expListView.collapseGroup(groupPosition);
        else
            expListView.expandGroup(groupPosition);
    }
}