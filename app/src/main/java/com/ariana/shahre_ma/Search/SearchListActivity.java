package com.ariana.shahre_ma.Search;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class SearchListActivity extends ActionBarActivity {
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter Search_list_Adapter;
    FieldClass fc=new FieldClass();
    TextView tv_null;
    ImageView img_null;
    TextView tv_count;

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
                tv_count.setText(Search_list_Adapter.getItemCount()+" "+"مورد");
        }
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });
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

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/
}
