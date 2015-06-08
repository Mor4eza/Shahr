package com.ariana.shahre_ma;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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


    private static final String DATABASE_NAME = "DBshahrma";
    private static final String TABLE_NAME_COLLECTION = "collection_tbl";
    private static final String TABLE_NAME_SUBSET= "subset";

    FieldClass fc=new FieldClass();
    MenuDrawer mDrawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        setContentView(R.layout.activity_jobs__list);
        ImageView img_m =(ImageView)findViewById(R.id.image_market);




        HTTPGetBusinessJson httpbusin=new HTTPGetBusinessJson(this);
        httpbusin.SetUrl_business(getsubsetID());
        httpbusin.execute();


        img_m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getApplicationContext(), Job_details.class);
                startActivity(i);

            }
        });


       // mDrawer = MenuDrawer.attach(this, Position.RIGHT);

        /*mDrawer.setContentView(R.layout.activity_jobs__list);
        mDrawer.setMenuView(R.layout.activity_jobs);*/

    }


    private Integer getsubsetID() {



            Integer Result = 0;


                SQLiteDatabase mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
                Cursor allrows = mydb.rawQuery("SELECT Id FROM " + TABLE_NAME_SUBSET+ " WHERE SubsetName='" +fc.GetSelected_job()+ "'", null);
                allrows.moveToFirst();
                Result = allrows.getInt(0);
                allrows.close();
                mydb.close();

        return Result;
    }

}
