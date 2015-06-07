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



    Integer Id_co;
    Integer Collection_ID_subset;

    private static final String DATABASE_NAME = "DBshahrma.db";
    private static final String TABLE_NAME_COLLECTION = "collection";
    private static final String TABLE_NAME_SUBSET= "subset";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs);



        createGroupList();
        createCollection();
       // LoadCollecdtion_Subset();


        expListView = (ExpandableListView) findViewById(R.id.laptop_list);

        final ExpandableListAdapter expListAdapter = new ExpandableListAdapter(
                this, groupList, laptopCollection) {

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
                Intent i = new Intent (getApplicationContext(),Jobs_List.class);
                startActivity(i);
                return true;
            }
        });

    }




    /* private void createGroupIcon(){



       //  for (String laptop : groupList) {
           //  if (laptop.equals("پوشاک")) {

         img.setImageResource(R.drawable.marker);
          //   } else if (laptop.equals("لوازم آرایشی"))
              //   img.setImageResource(R.drawable.marker);
         }
     //}*/
    private void createGroupList() {
        groupList = new ArrayList<String>();
        groupList.add("پوشاک");
        groupList.add("لوازم آرایشی");
        groupList.add("رستوران");
        groupList.add("لوازم یدکی");
        groupList.add("بیشتر...");
    }

    private void createCollection() {
        // preparing laptops collection(child)
        String[] pooshak = { "زنانه" ,"بچه گانه" ,"مردانه" };
        String[] arayeshi = { "پوست" ,"مو" ,"بهداشتی و آرایشی" };
        String[] resturan = { "فست فود" ,"چلوکبابی" ,"جگرکی" ,"بگیروببر"  };
        String[] lavazem_yadaki = {"سبک" ,"سنگین" ,"ایرانخودرو" ,"سایپا" };
        String[] more = {"لوازم التحریر" ,"کتابفروشی" ,"ابزارآلات" ,"آموزشگاه" ,"عطاری"  };
        String[] samsungModels = {"شغل" ,"شغل" ,"شغل" ,"شغل" ,"شغل" ,"شغل" };

        laptopCollection = new LinkedHashMap<String, List<String>>();

        for (String laptop : groupList) {
            if (laptop.equals("پوشاک")) {
                loadChild(pooshak);
            } else if (laptop.equals("لوازم آرایشی"))
                loadChild(arayeshi);
            else if (laptop.equals("رستوران"))
                loadChild(resturan);
            else if (laptop.equals("لوازم یدکی"))
                loadChild(lavazem_yadaki);
            else if (laptop.equals("بیشتر..."))
                loadChild(more);
            else
                loadChild(samsungModels);

            laptopCollection.put(laptop, childList);
        }
    }

    private void loadChild(String[] laptopModels) {
        childList = new ArrayList<String>();
        for (String model : laptopModels)
            childList.add(model);
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

   private void LoadCollecdtion_Subset() {

       try {
           SQLiteDatabase mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
           Cursor allrows_Collection = mydb.rawQuery("SELECT * FROM " + TABLE_NAME_COLLECTION, null);
           Cursor allrows_Subset = mydb.rawQuery("SELECT * FROM " + TABLE_NAME_SUBSET, null);
           groupList = new ArrayList<String>();
           childList = new ArrayList<String>();
           laptopCollection = new LinkedHashMap<String, List<String>>();
           if (allrows_Collection.moveToFirst()) {
               do {

                   Id_co = allrows_Collection.getInt(0);
                   groupList.add(allrows_Collection.getString(1));
                   if (allrows_Subset.moveToFirst()) {
                       do {
                           Collection_ID_subset = allrows_Subset.getInt(0);
                           if (Collection_ID_subset == Id_co) {
                               childList.add(allrows_Subset.getString(1));
                           }
                       } while (allrows_Subset.moveToNext());
                   }
                   laptopCollection.put(allrows_Collection.getString(1), groupList);
               } while (allrows_Collection.moveToNext());
           }
           mydb.close();





       }
       catch (Exception e){}
    }



}
