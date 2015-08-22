package com.ariana.shahre_ma.Bazarche;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.ariana.shahre_ma.CityDialog;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.ListExpand.Continent;
import com.ariana.shahre_ma.ListExpand.Country;
import com.ariana.shahre_ma.ListExpand.MyListAdapter;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCollectionProductJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetSubsetProductJson;

import java.util.ArrayList;
import java.util.List;

public class Bazarche extends ActionBarActivity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private SearchView mSearchView;
    private MyListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<Continent> continentList = new ArrayList<Continent>();
    ArrayList<Country> countryList;
    private Integer Id_co;
    Continent continent;
    Country country;
    Integer Collection_ID_subset;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazarche);

        HTTPGetSubsetProductJson httpGetSubsetProductJson=new HTTPGetSubsetProductJson(this);
        httpGetSubsetProductJson.execute();

        HTTPGetCollectionProductJson httpGetCollectionProductJson=new HTTPGetCollectionProductJson(this);
        httpGetCollectionProductJson.execute();


        //display the list
        displayList();
        //expand all Groups
        expandAll();

    }

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
        myList = (ExpandableListView) findViewById(R.id.expandable_bazarche);
        //create the adapter by passing your ArrayList data
        listAdapter = new MyListAdapter(Bazarche.this, continentList);
        //attach the adapter to the list
        myList.setAdapter(listAdapter);

    }

    private void loadSomeData() {


        try {
            DataBaseSqlite db=new DataBaseSqlite(this);
            Cursor allrows_Collection =db.select_Collection_Product();
            Cursor allrows_Subset =db.select_Subset_Product();
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
                        continent = new Continent(allrows_Collection.getString(1),countryList,Id_co);
                    }
                    continentList.add(continent);

                    //   laptopCollection.put(laptop,childList);

                } while (allrows_Collection.moveToNext());
            }


        }
        catch (Exception e){ Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();}

    }

    public void products(View view) {

        Intent i = new Intent(getApplicationContext(),My_products.class);
        startActivity(i);
    }

    @Override
    public boolean onClose() {
        Log.i("onClose", "close");
        listAdapter.filterData("");
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String query) {
        Log.i("onQueryTextChange","change");
        listAdapter.filterData(query);
        expandAll();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.i("onQueryTextSubmit","submit");
        listAdapter.filterData(query);
        expandAll();
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_jobs, menu);
        mSearchView = (android.support.v7.widget.SearchView) menu.findItem(R.id.action_search).getActionView();
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
