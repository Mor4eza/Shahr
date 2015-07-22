package com.ariana.shahre_ma.ListExpand;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.ExpandableListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;

public class ListActivity extends ActionBarActivity implements
        SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private SearchView search;
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
        setContentView(R.layout.activity_list);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search = (SearchView) findViewById(R.id.search2);
        search.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search.setIconifiedByDefault(false);
        search.setOnQueryTextListener(this);
        search.setOnCloseListener(this);

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
        myList = (ExpandableListView) findViewById(R.id.expandableList);
        //create the adapter by passing your ArrayList data
        listAdapter = new MyListAdapter(ListActivity.this, continentList);
        //attach the adapter to the list
        myList.setAdapter(listAdapter);

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
                        continent = new Continent(allrows_Collection.getString(1),countryList,Id_co);
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

}
