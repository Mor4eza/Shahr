package com.ariana.shahre_ma;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.ListExpand.Continent;
import com.ariana.shahre_ma.ListExpand.Country;
import com.ariana.shahre_ma.ListExpand.MyListAdapter;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Service.MyReceiver;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCollectionJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetInterestJson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static android.graphics.Color.WHITE;


public class Jobs extends ActionBarActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {

    Integer count = 0;
    String time="";
    String date="";
    public static SwipeRefreshLayout mSwipeRefreshLayout = null;
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

    private SearchView mSearchView;
    private SearchView search;
    private MyListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<Continent> continentList = new ArrayList<Continent>();
    ArrayList<Country> countryList;

    Continent continent;
    Country country;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        query=new Query(this);

        setTitle("لیست مشاغل");

        httpbusin=new HTTPGetBusinessJson(this);
        ns=new NetState(this);


        Intent myIntent = new Intent(this, MyReceiver.class);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this  ,  0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
        final Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 60); // first time
        long frequency= 60000 * 1000; // in ms
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);

        displayList();
        //expandAll();
      //  collapseAll();



        //Initialize swipe to refresh view
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setEnabled(false);
                HTTPGetCollectionJson http = new HTTPGetCollectionJson(Jobs.this);
                http.execute();


            }
        });



       expListView = (ExpandableListView) findViewById(R.id.laptop_list);

      final MyListAdapter expListAdapter = new MyListAdapter(getApplication(),continentList) {

        };
        //expListView.setAdapter(expListAdapter);*/

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


            /*            expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

                            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id) {

                                Continent headerInfo = continentList.get(groupPosition);

                                Country detailInfo = headerInfo.getCountryList().get(childPosition);

                               // Toast.makeText(getApplicationContext(),String.valueOf(id), Toast.LENGTH_LONG).show();

                                final String selected = detailInfo.getName();

                                Toast.makeText(getApplicationContext(), selected, Toast.LENGTH_LONG)
                                        .show();
                                //query=new Query(Jobs.this,Jobs.this);
                                fc.SetSelected_job(selected);

                                count = query.getCountBusiness(query.getsubsetID(selected));
                                fc.SetSubsetId(query.getsubsetID(selected));


                                Toast.makeText(getApplicationContext(), query.getsubsetID(selected).toString(), Toast.LENGTH_LONG).show();
                                if (ns.checkInternetConnection() == false) {
                                    Intent i = new Intent(getApplicationContext(), Jobs_List.class);
                                    startActivity(i);
                                } else {
                                    if (count > 0) {
                                        fc.SetCount_Business(query.getCountBusiness(query.getsubsetID(fc.GetSelected_job())));
                                        Intent i = new Intent(getApplicationContext(), Jobs_List.class);
                                        startActivity(i);
                                        Log.i("Count>", "1");
                                    } else {
                                        httpbusin = new HTTPGetBusinessJson(Jobs.this);
                                        httpbusin.SetUrl_business(query.getsubsetID(selected));
                                        httpbusin.execute();
                                        Log.i("Count<", "0");
                                    }
                                }

                                return true;
                            }

                        });*/

       /* SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (android.widget.SearchView) findViewById(R.id.search_jobs);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);*/




    }

/*



  /*  private void setGroupIndicatorToRight() {
		*//* Get the screen width *//*
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;

        expListView.setIndicatorBounds(width - getDipsFromPixel(100), width
                - getDipsFromPixel(5));
    }*/

    /*// Convert pixel to dip
    public int getDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 5f);
    }*/

    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            myList.expandGroup(i);
        }
    }

    //method to expand all groups
    private void displayList() {

        //display the list
        loadSomeData();

        //get reference to the ExpandableListView
        myList = (ExpandableListView) findViewById(R.id.laptop_list);
        //create the adapter by passing your ArrayList data
        listAdapter = new MyListAdapter(Jobs.this, continentList);
        //attach the adapter to the list
        myList.setAdapter(listAdapter);

    }

    public void SendPostInterest()
    {
       /* SqliteTOjson json=new SqliteTOjson(this);
        Toast.makeText(getApplicationContext(),json.getSqliteInterestTOjson(),Toast.LENGTH_LONG).show();
        HTTPPostInterestJson httpinterest=new HTTPPostInterestJson(this);
        httpinterest.SetInterest_Json(json.getSqliteInterestTOjson());
        httpinterest.execute();*/

        Log.i("mSwipeRefreshLayout", "true");
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



    private void loadSomeData() {


        try {
            DataBaseSqlite db=new DataBaseSqlite(this);
            Cursor allrows_Collection =db.select_Collection();
            Cursor allrows_Subset =db.select_Subset();

            if (allrows_Collection.moveToFirst()) {

                do {

                    Id_co = allrows_Collection.getInt(0);

                    countryList = new ArrayList<Country>();
                    if (allrows_Subset.moveToFirst())
                    {
                        do {

                            Collection_ID_subset = allrows_Subset.getInt(2);


                            if (Collection_ID_subset == Id_co)
                            {
                                // childList.add(allrows_Subset.getString(1));
                                country = new Country(allrows_Subset.getString(1));
                                countryList.add(country);


                            }


                        } while (allrows_Subset.moveToNext());
                        continent = new Continent(allrows_Collection.getString(1),countryList);
                    }
                    continentList.add(continent);

                    //   laptopCollection.put(laptop,childList);

                } while (allrows_Collection.moveToNext());
            }


        }
        catch (Exception e){ Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();}

    }

    @Override
    public boolean onClose() {
        Log.i("onClose", "close");
        listAdapter.filterData("");
        collapseAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        Log.i("onQueryTextChange", "change");
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        expandAll();
        Log.i("onQueryTextSubmit", "submit");
        listAdapter.filterData(query);
        return false;
    }
    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++){
            myList.collapseGroup(i);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jobs, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.select_city) {
            CityDialog cd = new CityDialog(this);
            cd.show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(true);
        mSearchView.setQueryHint("جستجو در مشاغل");
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

            // Try to use the "applications" global search provider
            SearchableInfo info = searchManager.getSearchableInfo(getComponentName());
            for (SearchableInfo inf : searchables) {
                if (inf.getSuggestAuthority() != null
                        && inf.getSuggestAuthority().startsWith("applications")) {
                    info = inf;
                }
            }
            mSearchView.setSearchableInfo(info);
        }

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }

}
