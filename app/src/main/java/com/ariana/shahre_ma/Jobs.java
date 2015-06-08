package com.ariana.shahre_ma;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.Toast;


import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCollectionJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetSubsetJson;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class Jobs extends ActionBarActivity {




    List<String> groupList;
    List<String> childList;
    Map<String, List<String>> laptopCollection;
    ExpandableListView expListView;
    int lastExpandedPosition = -1;


    Integer id[];
    Integer Collection_id[];
    Integer Id_co;
    Integer Collection_ID_subset;

    FieldClass fc=new FieldClass();
    private static final String DATABASE_NAME = "DBshahrma";
    private static final String TABLE_NAME_COLLECTION = "collection_tbl";
    private static final String TABLE_NAME_SUBSET= "subset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);




        createCollection();



        expListView = (ExpandableListView) findViewById(R.id.laptop_list);

        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList,laptopCollection) {

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
                Toast.makeText(getBaseContext(), selected, Toast.LENGTH_LONG)
                        .show();
                fc.SetSelected_job(selected);
                Intent i = new Intent(getApplicationContext(), Jobs_List.class);
                startActivity(i);


                return true;
            }
        });

    }



    private void createCollection() {

           Boolean f=true;

        try {
            SQLiteDatabase mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            Cursor allrows_Collection = mydb.rawQuery("SELECT * FROM " + TABLE_NAME_COLLECTION, null);
            Cursor allrows_Subset = mydb.rawQuery("SELECT * FROM " + TABLE_NAME_SUBSET, null);
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




}
