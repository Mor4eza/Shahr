package com.ariana.shahre_ma;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.ListExpand.Continent;
import com.ariana.shahre_ma.ListExpand.Country;
import com.ariana.shahre_ma.ListExpand.MyListAdapter;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCollectionJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetSubsetJson;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.ArrayList;
import java.util.List;

import jonathanfinerty.once.Once;


public class Jobs extends ActionBarActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {


    Query query;
    int lastExpandedPosition = -1;
    Integer id[];
    Integer Id_co;
    Integer Collection_ID_subset;
    HTTPGetBusinessJson httpbusin;
    NetState ns;
    boolean expand=false;
    Boolean refresh_display = true;
    private SearchView mSearchView;
    private MyListAdapter listAdapter;
    private ExpandableListView myList;
    private ArrayList<Continent> continentList = new ArrayList<Continent>();
    ArrayList<Country> countryList;
    KeySettings setting = new KeySettings(this);
    Continent continent;
    Country country;
    public static ProgressBar PgUpdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);
        query = new Query(this);
        setTitle(" مشاغل " + setting.getCityName());

        KeySettings setting = new KeySettings(this);
        httpbusin = new HTTPGetBusinessJson(this);
        ns = new NetState(this);

        PgUpdate = (ProgressBar) findViewById(R.id.progressBar_update);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver, new IntentFilter("City"));
        LocalBroadcastManager.getInstance(this).registerReceiver(mCollectionReceiver, new IntentFilter("Collection"));
        if (!setting.getCollection()) {


            PgUpdate.setVisibility(View.VISIBLE);

            Log.i("getCollection", "1");
            HTTPGetCollectionJson httpGetCollectionJson = new HTTPGetCollectionJson(this);
            httpGetCollectionJson.execute();

            HTTPGetSubsetJson httpGetSubsetJson = new HTTPGetSubsetJson(this);
            httpGetSubsetJson.execute();
        } else {
            Log.i("getCollection", "2");
            displayList();
        }


        try {
            myList.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                @Override
                public void onGroupExpand(int groupPosition) {
                    expand=true;
                    Log.i("Expand",String.valueOf(expand));
                    if (lastExpandedPosition != -1
                            && groupPosition != lastExpandedPosition) {
                            myList.collapseGroup(lastExpandedPosition);
                    }
                    lastExpandedPosition = groupPosition;
                }
            });

            myList.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                @Override
                public void onGroupCollapse(int groupPosition) {
                    expand=false;
                }
            });

        } catch (Exception e) {

        }

        String showWhatsNew = "showHelpJobs";

        if (!Once.beenDone(Once.THIS_APP_INSTALL, showWhatsNew)) {
            help1();
            Once.markDone(showWhatsNew);
        }
    }


    //method to expand all groups
    private void expandAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
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


    private void loadSomeData() {


        try {
            SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(this);
            Cursor allrows_Collection = sdb.select_Collection();
            Cursor allrows_Subset = sdb.select_Subset();

            if (allrows_Collection.moveToFirst()) {

                do {

                    Id_co = allrows_Collection.getInt(0);
                    countryList = new ArrayList<Country>();
                    if (allrows_Subset.moveToFirst()) {
                        do {
                            Collection_ID_subset = allrows_Subset.getInt(2);
                            if (Collection_ID_subset == Id_co) {
                                country = new Country(allrows_Subset.getString(1));
                                countryList.add(country);
                            }
                        } while (allrows_Subset.moveToNext());
                        continent = new Continent(allrows_Collection.getString(1), countryList, Id_co);
                    }
                    continentList.add(continent);
                } while (allrows_Collection.moveToNext());
            }
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public boolean onClose() {
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
        listAdapter.filterData(query);
        return false;
    }

    private void collapseAll() {
        int count = listAdapter.getGroupCount();
        for (int i = 0; i < count; i++) {
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
        }

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }

    public void help1() {
        ViewTarget Hdiscount = new ViewTarget(R.id.laptop_list, this);
        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv = new ShowcaseView.Builder(this)
                .setTarget(Hdiscount)
                .setContentTitle("مشاغل")
                .setContentText("از این قسمت میتوانید دسته بندی کسب و کار را مشاهده کنید")
                .hideOnTouchOutside()
                .setStyle(R.style.CustomShowcaseTheme)
                .build();

        sv.setButtonText("بعدی");
        sv.setButtonPosition(lps);
        sv.setOnShowcaseEventListener(new OnShowcaseEventListener() {
            @Override
            public void onShowcaseViewHide(ShowcaseView showcaseView) {
                help2();
            }

            @Override
            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

            }

            @Override
            public void onShowcaseViewShow(ShowcaseView showcaseView) {

            }
        });
    }

    public void help2() {
        ViewTarget Hdiscount = new ViewTarget(R.id.select_city, this);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv = new ShowcaseView.Builder(this)
                //.setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                .setTarget(Hdiscount)
                .setContentTitle("تغیر شهر")
                .setContentText("برای تغیر شهر فعلی و مشاهده کسب و کار های شهرهای دیگر از این گزینه استفاده کنید")
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
        sv.setButtonText("بعدی");
        sv.setButtonPosition(lps);
        sv.setOnShowcaseEventListener(new OnShowcaseEventListener() {
            @Override
            public void onShowcaseViewHide(ShowcaseView showcaseView) {
                help3();
            }

            @Override
            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

            }

            @Override
            public void onShowcaseViewShow(ShowcaseView showcaseView) {

            }
        });
    }

    public void help3() {
        ViewTarget Hdiscount = new ViewTarget(R.id.action_search, this);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv = new ShowcaseView.Builder(this)
                // .setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                .setTarget(new ViewTarget(((ViewGroup) findViewById(R.id.action_bar)).getChildAt(2)))
                        //  .setTarget(Hdiscount)
                .setContentTitle("فیلتر کردن لیست")
                .setContentText("برای فیلتر کردن لیست مشاغل از این گزینه استفاده کنید")
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
        sv.setButtonText("خب");
        sv.setButtonPosition(lps);

    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setTitle(" مشاغل " + setting.getCityName());
        }

    };

    private BroadcastReceiver mCollectionReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            if (setting.getCollection() && setting.getSubset() && refresh_display) {
                refresh_display = false;
                displayList();
                PgUpdate.setVisibility(View.INVISIBLE);
            }
        }
    };

    @Override
    public void onBackPressed() {

        if (expand) {
            myList.collapseGroup(lastExpandedPosition);
            expand=false;
        }else{
           finish();
        }

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
