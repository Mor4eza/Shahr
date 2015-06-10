package com.ariana.shahre_ma;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ariana.shahre_ma.Cards.CardAdapter;
import com.ariana.shahre_ma.Cards.job_list_cards_adapter;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.ImageDownload.ImageLoader;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.job_details.Job_details;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;


public class Jobs_List extends ActionBarActivity {

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter job_list_Adapter;

    private static final String DATABASE_NAME = "DBshahrma.db";
    private static final String TABLE_NAME_COLLECTION = "collection_tbl";
    private static final String TABLE_NAME_SUBSET= "subset";

    FieldClass fc=new FieldClass();
    MenuDrawer mDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);









        HTTPGetBusinessJson httpbusin=new HTTPGetBusinessJson(this);
        httpbusin.SetUrl_business(getsubsetID());
        httpbusin.execute();



        setContentView(R.layout.activity_jobs__list);
        setCards();

       // mDrawer = MenuDrawer.attach(this, Position.RIGHT);

        /*mDrawer.setContentView(R.layout.activity_jobs__list);
        mDrawer.setMenuView(R.layout.activity_jobs);*/

    }
        public void img_click(View v){

            Intent i = new Intent(getApplicationContext(),Job_details.class);
            startActivity(i);

        }

    private Integer getsubsetID() {



            Integer Result = 0;


                SQLiteDatabase mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                Cursor allrows = mydb.rawQuery("SELECT Id FROM " + TABLE_NAME_SUBSET+ "  WHERE SubsetName='" +fc.GetSelected_job()+ "'", null);
                allrows.moveToFirst();
                Result = allrows.getInt(0);
                allrows.close();
                mydb.close();

                fc.SetBusiness_SubsetId(Result);
        return Result;
    }
    private void setCards(){
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
}
