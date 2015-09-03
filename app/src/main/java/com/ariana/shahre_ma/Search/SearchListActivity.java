package com.ariana.shahre_ma.Search;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.Cards.job_list_cards_adapter;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.MapsActivity;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.util.List;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class SearchListActivity extends ActionBarActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener {
    RecyclerView mRecyclerView;
    private SearchView mSearchView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter Search_list_Adapter;
    FieldClass fc=new FieldClass();
    TextView tv_null;
    ImageView img_null;
    TextView tv_count;
    KeySettings setting=new KeySettings(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_list);
        try {
            getSupportActionBar().setElevation(0);
        }catch (Exception e){}
        setCards();
        setTitle("نتایج جستجو...");
        img_null=(ImageView)findViewById(R.id.img_null);
        tv_null=(TextView)findViewById(R.id.tv_null);
        tv_count=(TextView)findViewById(R.id.tv_count);
        if(Search_list_Adapter.getItemCount()!=0){
                img_null.setVisibility(View.INVISIBLE);
                tv_null.setVisibility(View.INVISIBLE);
                tv_count.setText(Search_list_Adapter.getItemCount()+" "+"مورد پیدا شد!");
        }
     /*   mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                try {
                    if (dy > 0)
                        getSupportActionBar().hide();
                    else
                        getSupportActionBar().show();
                }catch (Exception e){}
            }
        });*/
    }


    private void setCards(){
       // try {


            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_Search);
            mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
            mRecyclerView.getItemAnimator().setAddDuration(1000);
            mRecyclerView.getItemAnimator().setChangeDuration(1000);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            Search_list_Adapter = new SearchListAdapter(this);
            ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(Search_list_Adapter);
            alphaAdapter.setDuration(400);
            mRecyclerView.setAdapter(alphaAdapter);
            Search_list_Adapter.notifyItemChanged(0);
            Search_list_Adapter.notifyDataSetChanged();
        /*}
        catch (Exception e){}*/
    }
    private void setCardsforsearch(){
        try {


            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_Search);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            Search_list_Adapter = new SearchListAdapter(this);
            mRecyclerView.setAdapter(Search_list_Adapter);

            if(Search_list_Adapter.getItemCount()==0){
                img_null.setVisibility(View.VISIBLE);
                tv_null.setVisibility(View.VISIBLE);
            }else {
                img_null.setVisibility(View.INVISIBLE);
                tv_null.setVisibility(View.INVISIBLE);

            }

        }
        catch (Exception e){}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mSearchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        setupSearchView();
        if(Search_list_Adapter.getItemCount()==0)
        {
            return  false;
        }
        else
        {
            return true;
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
        int id = item.getItemId();
        View btnsort=findViewById(R.id.sort);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Map) {


            if(status== ConnectionResult.SUCCESS) {
                Intent i = new Intent(this,MapsActivity.class);
                startActivity(i);
            }else{
                int requestCode = 10;
                AlertDialog alertDialog = new AlertDialog.Builder(SearchListActivity.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("نسخه Google Play Service  شما قدیمی می باشد. لطفا بروز رسانی کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }

            return true;

        }else if(id== R.id.sort){

            final PopupMenu popupMenu=new PopupMenu(SearchListActivity.this,btnsort);
            popupMenu.getMenuInflater().inflate(R.menu.job_list_popupmenu, popupMenu.getMenu());
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getTitle().equals("مرتب سازی بر اساس نام"))
                    {
                        Toast.makeText(getApplicationContext(), item.getItemId(), Toast.LENGTH_LONG).show();
                        setting.saveSortBusiness("name");
                        setCards();
                    }
                    else if(item.getTitle().equals("مرتب سازی بر اساس امتیاز"))
                    {
                        setting.saveSortBusiness("rate");

                        setCards();
                    } else if(item.getTitle().equals("مرتب سازی بر اساس جدیدترینها")){

                        setting.saveSortBusiness("date");
                        setCards();

                    }

                    return false;
                }
            });
        }else if(id==android.R.id.home){
            FieldDataBusiness fdb=new FieldDataBusiness();
            fdb.ClearAll();
            fc.SetSearchOffline(false);
            fc.SetSearchOnline(false);
        }


        return super.onOptionsItemSelected(item);
    }

    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(true);
        mSearchView.setQueryHint("جستجو");
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        if (searchManager != null) {
            List<SearchableInfo> searchables = searchManager.getSearchablesInGlobalSearch();

        }

        mSearchView.setOnQueryTextListener(this);
        mSearchView.setOnCloseListener(this);
    }

    /**
     * Search Jobs_List
     * Search to Business
     * @return
     */
    @Override
    public boolean onClose()
    {
        //Search stop Select Business All
        KeySettings setting=new KeySettings(this);
        setting.saveSearchBusiness(false);
        setCards();
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query)
    {
        //Submit
        // Search Sumbit To Business Parameters SubsetID and NameMarket
        Log.i("GetMarket",query);
        if(query.equals(""))
        {
            setting.saveSearchBusiness(false);
            setCardsforsearch();

        }
        else
        {
            setting.saveSearchBusiness(true);
            fc.SetMarket_Business(query);
           setCardsforsearch();
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText)
    {

        if(newText.equals("")) // Text Empty Select Business All
        {
            Log.i("textserch", "null");
            setting.saveSearchBusiness(false);
           // setCardsforsearch();
        }
        else // Text Not Empty  Search Business
        {

            setting.saveSearchBusiness(true);
            fc.SetMarket_Business(newText);
           // setCardsforsearch();

        }
        return false;
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
