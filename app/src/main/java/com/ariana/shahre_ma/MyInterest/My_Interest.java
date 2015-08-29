package com.ariana.shahre_ma.MyInterest;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.ListExpand.Continent;
import com.ariana.shahre_ma.ListExpand.Country;
import com.ariana.shahre_ma.MyCity.My_city;
import com.ariana.shahre_ma.MyCity.TotalListener;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Service.MyReceiver;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostInterestJson;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import java.util.ArrayList;

import jonathanfinerty.once.Once;

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
        String showWhatsNew = "showHelpInterest";

        if (!Once.beenDone(Once.THIS_APP_VERSION, showWhatsNew)) {
            help3();
            Once.markDone(showWhatsNew);
        }
    }



    public void SendPostInterest(View v) {
       SqliteTOjson json=new SqliteTOjson(this);
        Log.i("", json.getSqliteInterestTOjson());
        if(ns.checkInternetConnection()) {
            HTTPPostInterestJson httpinterest = new HTTPPostInterestJson(this);
            httpinterest.SetInterest_Json(json.getSqliteInterestTOjson());
            httpinterest.execute();
        }else{
            AlertDialog alertDialog = new AlertDialog.Builder(My_Interest.this).create();
            alertDialog.setTitle("هشدار");
            alertDialog.setMessage("اینترنت قطع می باشد");
            alertDialog.setButton("خب", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();
        }

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



    public void help3(){
        ViewTarget Hdiscount=new ViewTarget(R.id.expand_my_interest,this);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv= new ShowcaseView.Builder(this)
                .setTarget(Hdiscount)
                .setContentTitle("علایق من")
                .setContentText("از این قسمت شما میتوانید زیر مجموعه ای را به علایق خودتان اضافه کنید تا اعلاناتی را دریافت کنید که نیاز دارید.")
                .hideOnTouchOutside()
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
        sv.setButtonText("خب");
        sv.setButtonPosition(lps);

    }
}