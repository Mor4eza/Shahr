package com.ariana.shahre_ma;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.Cards.job_list_cards_adapter;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.job_details.Job_details;

import java.util.List;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;


public class Jobs_List extends ActionBarActivity implements SearchView.OnQueryTextListener, SearchView.OnCloseListener{

    private SearchView mSearchView;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter job_list_Adapter;

    KeySettings setting = new KeySettings(this);
    FieldClass fc=new FieldClass();


    TextView tvMarket;
    TextView tvDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs__list);
            setTitle(fc.GetSelected_job());
                setCards();
                mRecyclerView.setAdapter(job_list_Adapter);



    }
        public void img_click(View v){
            Intent i = new Intent(getApplicationContext(), Job_details.class);
            startActivity(i);

        }





    private void setCards(){
        try {


            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_Jobs);
            mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
            mRecyclerView.getItemAnimator().setAddDuration(1000);
            mRecyclerView.getItemAnimator().setChangeDuration(1000);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            job_list_Adapter = new job_list_cards_adapter(this);
            ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(job_list_Adapter);
            alphaAdapter.setDuration(400);
            mRecyclerView.setAdapter(alphaAdapter);
            job_list_Adapter.notifyItemChanged(0);
            job_list_Adapter.notifyDataSetChanged();
        }
        catch (Exception e){}
    }
    private void setCardsforsearch(){
        try {


            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_Jobs);
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
        View btnsort=findViewById(R.id.sort);
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_Map) {
           Intent i = new Intent(this,MapsActivity.class);
           startActivity(i);
            return true;

        }else if(id== R.id.sort){

            final PopupMenu popupMenu=new PopupMenu(Jobs_List.this,btnsort);
            popupMenu.getMenuInflater().inflate(R.menu.job_list_popupmenu, popupMenu.getMenu());
            popupMenu.show();
            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getTitle().equals("مرتب سازی بر اساس نام"))
                    {
                        Toast.makeText(getApplicationContext(),item.getItemId(),Toast.LENGTH_LONG).show();
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
        }


        return super.onOptionsItemSelected(item);
    }

    private void setupSearchView() {

        mSearchView.setIconifiedByDefault(true);

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

        //Online Type change
        // Search Online To Business Parameters SubsetID and NameMarket

        if(newText.equals("")) // Text Empty Select Business All
        {
            setting.saveSearchBusiness(false);
            setCardsforsearch();
        }
        else // Text Not Empty  Search Business
        {

            setting.saveSearchBusiness(true);
            fc.SetMarket_Business(newText);
            setCardsforsearch();
        }
        return false;
    }
}
