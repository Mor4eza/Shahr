package com.ariana.shahre_ma;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Service.MainService;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCityJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCollectionJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetInterestJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetSubsetJson;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Jobs extends ActionBarActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    private SearchView search;
    Integer count = 0;
    String time="";
    String date="";
    public SwipeRefreshLayout mSwipeRefreshLayout = null;
    Query query;
    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;
    int lastExpandedPosition = -1;
    DateTime dt=new DateTime();

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
        query=new Query(this);



        httpbusin=new HTTPGetBusinessJson(this);
        ns=new NetState(this);

        Context context=getApplication();
        Intent intent=new Intent(context, MainService.class);
        context.startService(intent);

        createCollection();




        //Initialize swipe to refresh view
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //Refreshing data on server
              //  Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_LONG).show();
                mSwipeRefreshLayout.setRefreshing(false);
                HTTPGetCollectionJson http = new HTTPGetCollectionJson(Jobs.this,mSwipeRefreshLayout);
                http.execute();

            /*    new CountDownTimer(5000, 1000) {

                    public void onTick(long millisUntilFinished) {

                    }

                    public void onFinish() {
                      mSwipeRefreshLayout.setRefreshing(false);
                    }
                }.start();


*/
            }
        });



        expListView = (ExpandableListView) findViewById(R.id.laptop_list);

      final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(this, groupList,laptopCollection) {

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
                                //query=new Query(Jobs.this,Jobs.this);
                                fc.SetSelected_job(selected);

                                count = query.getCountBusiness(query.getsubsetID(selected));

                                time = query.getTime_ZamanSanj();
                                date = query.getDate_ZamanSanj();


                                Toast.makeText(getApplicationContext(), query.getsubsetID(selected).toString(), Toast.LENGTH_LONG).show();
                                if (ns.checkInternetConnection() == false)
                                {
                                    Intent i = new Intent(getApplicationContext(), Jobs_List.class);
                                    startActivity(i);
                                }
                                else
                                {
                                    if(count>0)
                                    {
                                        Intent i = new Intent(getApplicationContext(), Jobs_List.class);
                                        startActivity(i);
                                        Log.i("Count>","1");
                                    }
                                    else
                                    {
                                        httpbusin = new HTTPGetBusinessJson(Jobs.this);
                                        httpbusin.SetUrl_business(query.getsubsetID(selected));
                                        httpbusin.execute();
                                        Log.i("Count<", "0");
                                    }
                                }

                                return true;
                            }

                        });

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search=(SearchView)findViewById(R.id.search_jobs);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);




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



    public void SendPostInterest()
    {
       /* SqliteTOjson json=new SqliteTOjson(this);
        Toast.makeText(getApplicationContext(),json.getSqliteInterestTOjson(),Toast.LENGTH_LONG).show();
        HTTPPostInterestJson httpinterest=new HTTPPostInterestJson(this);
        httpinterest.SetInterest_Json(json.getSqliteInterestTOjson());
        httpinterest.execute();*/

        Log.i("mSwipeRefreshLayout","true");
        if(mSwipeRefreshLayout.isRefreshing()) {
            Log.i("mSwipeRefreshLayout","true");
            mSwipeRefreshLayout.setRefreshing(false);
            Log.i("mSwipeRefreshLayout", "false");
        }

    }


    public void GetPostInterest(View v)
    {



        HTTPGetInterestJson httpinterest=new HTTPGetInterestJson(this);

        httpinterest.execute();
    }

    @Override
    public boolean onClose() {
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        Toast.makeText(getApplicationContext(),"Typing...",Toast.LENGTH_LONG).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }
}
