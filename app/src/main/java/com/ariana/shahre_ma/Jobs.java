package com.ariana.shahre_ma;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetInterestJson;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostInterestJson;

import net.simonvt.menudrawer.MenuDrawer;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Jobs extends ActionBarActivity {


    Integer count = 0;

    String time="";
    String date="";

    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;
    int lastExpandedPosition = -1;
DateTime dt=new DateTime();
    MenuDrawer mDrawer;

    Integer id[];
    Integer Collection_id[];
    Integer Id_co;
    Integer Collection_ID_subset;

    FieldClass fc=new FieldClass();

    HTTPGetBusinessJson httpbusin;
    NetState ns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);

/*

        mDrawer = MenuDrawer.attach(this, Position.RIGHT);
        mDrawer.setMenuView(R.layout.activity_jobs);
        mDrawer.setContentView(R.layout.activity_job_details_map);
*/



        httpbusin=new HTTPGetBusinessJson(this);
        ns=new NetState(this);

        createCollection();



        expListView = (ExpandableListView) findViewById(R.id.laptop_list);

        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList,laptopCollection) {

        };
        expListView.setAdapter(expListAdapter);


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

                        // setGroupIndicatorToRight();


                        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                            public boolean onChildClick(ExpandableListView parent, View v,
                                                        int groupPosition, int childPosition, long id) {
                                final String selected = (String) expListAdapter.getChild(
                                        groupPosition, childPosition);
                                Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_LONG)
                                        .show();

                                fc.SetSelected_job(selected);

                               count = getCountBusiness();

                                time=getTime_ZamanSanj();
                                 date=getDate_ZamanSanj();



                                Toast.makeText(getApplicationContext(),count.toString(),Toast.LENGTH_LONG).show();
                               if (ns.checkInternetConnection() == false) {

                                    if (count == 0)
                                    {
                                        Toast.makeText(getApplicationContext(),"فروشگاه ثبت نشده",Toast.LENGTH_LONG).show();
                                    }
                                    else
                                    {
                                        Intent i = new Intent(getApplicationContext(), Jobs_List.class);
                                        startActivity(i);

                                    }

                                }
                                else
                                {


                                    if (count == 0 ) {
                                      //  Toast.makeText(getApplicationContext(),"فروشگاه ثبت نشده",Toast.LENGTH_LONG).show();
                                        httpbusin = new HTTPGetBusinessJson(Jobs.this);
                                        httpbusin.SetUrl_business(getsubsetID());
                                        httpbusin.execute();

                                        Log.i("Count ==0 "," ok");
                                    }
                                    else
                                    {
                                                    if (time == "")
                                                    {
                                                        Toast.makeText(getApplicationContext(), "Time: " + time, Toast.LENGTH_LONG).show();
                                                        DataBaseSqlite dbs = new DataBaseSqlite(Jobs.this);
                                                        dbs.Add_UpdateTime(fc.GetTableNameUpdateTime(), dt.Hours(), dt.Now());

                                                        httpbusin = new HTTPGetBusinessJson(Jobs.this);
                                                        httpbusin.SetUrl_business(getsubsetID());
                                                        httpbusin.execute();
                                                        Log.i("Time == 0", " ok");
                                                    }
                                                    else
                                                    {
                                                            if (Integer.parseInt(dt.Hours()) >= Integer.parseInt(time + 3) || date != dt.Now()) {
                                                                DataBaseSqlite dbs = new DataBaseSqlite(Jobs.this);
                                                                dbs.delete_UpdateTime(fc.GetTableNameUpdateTime());
                                                                dbs.Add_UpdateTime(fc.GetTableNameUpdateTime(), dt.Hours(), dt.Now());

                                                                httpbusin = new HTTPGetBusinessJson(Jobs.this);
                                                                httpbusin.SetUrl_business(getsubsetID());
                                                                httpbusin.execute();
                                                                Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
                                                                Log.i("Time > Time+3 ", "ok");
                                                            }
                                                            else
                                                            {
                                                                Intent i = new Intent(getApplicationContext(), Jobs_List.class);
                                                                startActivity(i);
                                                                Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_LONG).show();
                                                                Log.i("NOT IF And Eles ", "Ok");
                                                            }
                                                    }
                                    }
               }


                return true;
            }

        });




    }



    private void createCollection() {

           Boolean f=true;

        try {
            SQLiteDatabase mydb = openOrCreateDatabase(fc.GetDataBaseName(), Context.MODE_PRIVATE, null);
            Cursor allrows_Collection = mydb.rawQuery("SELECT * FROM " + fc.GetTableNameCollection(), null);
            Cursor allrows_Subset = mydb.rawQuery("SELECT * FROM " + fc.GetTableNameSubset(), null);
            groupList = new ArrayList<String>();


            String laptop="";
            laptopCollection = new LinkedHashMap<String, List<String>>();

            if (allrows_Collection.moveToFirst()) {
                do {
                    childList = new ArrayList<String>();
                    Id_co = allrows_Collection.getInt(0);

                   groupList.add(allrows_Collection.getString(1));
                    laptop=allrows_Collection.getString(1);
                            if (allrows_Subset.moveToFirst())
                            {
                                do {

                                    Collection_ID_subset = allrows_Subset.getInt(2);


                                         if (Collection_ID_subset == Id_co)
                                         {
                                             childList.add(allrows_Subset.getString(1));



                                         }

                                } while (allrows_Subset.moveToNext());

                            }

                      laptopCollection.put(laptop,childList);

                } while (allrows_Collection.moveToNext());
            }

            mydb.close();
        }
        catch (Exception e){ Toast.makeText(getBaseContext(),e.toString(), Toast.LENGTH_LONG).show();}



    }


    private void setGroupIndicatorToRight() {
		/* Get the screen width */
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(100), width
                - getDipsFromPixel(5));
    }

    // Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 5f);
    }


    private Integer getsubsetID() {



        Integer Result = 0;

            try {
                SQLiteDatabase mydb = openOrCreateDatabase(fc.GetDataBaseName(), Context.MODE_PRIVATE, null);
                Cursor allrows = mydb.rawQuery("SELECT Id FROM " + fc.GetTableNameSubset() + "  WHERE SubsetName='" + fc.GetSelected_job() + "'", null);
                allrows.moveToFirst();
                Result = allrows.getInt(0);
                allrows.close();
                mydb.close();

                fc.SetBusiness_SubsetId(Result);
            }
            catch (Exception e){Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();}
        return Result;
    }

        private Integer getCountBusiness() {
        Integer Result = 0;

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(this);
            Cursor allrows = dbs.select_business_SubsetId(getsubsetID());
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }


    private String getTime_ZamanSanj() {
        String Result ="";

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(this);
            Cursor allrows = dbs.select_UpdateTime(fc.GetTableNameUpdateTime());
            allrows.moveToFirst();
            Result = allrows.getString(1);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }

    private String getDate_ZamanSanj() {
        String Result ="";

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(this);
            Cursor allrows = dbs.select_UpdateTime(fc.GetTableNameUpdateTime());
            allrows.moveToFirst();
            Result = allrows.getString(2);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }



    public void SendPostInterest(View v)
    {
        SqliteTOjson json=new SqliteTOjson(this);
        Toast.makeText(getApplicationContext(),json.getSqliteInterestTOjson(),Toast.LENGTH_LONG).show();
        HTTPPostInterestJson httpinterest=new HTTPPostInterestJson(this);
        httpinterest.SetInterest_Json(json.getSqliteInterestTOjson());
        httpinterest.execute();
    }


    public void GetPostInterest(View v)
    {


        HTTPGetInterestJson httpinterest=new HTTPGetInterestJson(this);

        httpinterest.execute();
    }
}
