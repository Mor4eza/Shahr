package com.ariana.shahre_ma.DateBaseSqlite;

import android.content.Context;
import android.database.Cursor;

import com.ariana.shahre_ma.Fields.FieldClass;

/**
 * Created by ariana2 on 6/17/2015.
 */
public class Query {

    Context context;


    FieldClass fc=new FieldClass();
   public Query(Context context)
   {
       this.context=context;

   }




    public Integer getsubsetID(String subsetname) {



        Integer Result = 0;

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            Cursor allrows = dbs.select_SubsetId(subsetname);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();


            fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {
            // Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
        return Result;
    }

    public Integer getCountBusiness(Integer subsetid) {
        Integer Result = 0;

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            Cursor allrows = dbs.select_business_SubsetId(subsetid);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }


    public String getTime_ZamanSanj() {
        String Result ="";

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            Cursor allrows = dbs.select_UpdateTime(fc.GetTableNameUpdateTime());
            allrows.moveToFirst();
            Result = allrows.getString(1);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }

    public String getDate_ZamanSanj() {
        String Result ="";

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            Cursor allrows = dbs.select_UpdateTime(fc.GetTableNameUpdateTime());
            allrows.moveToFirst();
            Result = allrows.getString(2);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }

    public Integer getMemberId() {
        Integer Result =0;

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            Cursor allrows = dbs.select_Member();
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }

    public Integer getOpinionId() {
        Integer Result =0;

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            Cursor allrows = dbs.select_opinion();
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }

    public Integer getBusinessId() {
        Integer Result =0;

        try {
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            Cursor allrows = dbs.select_business(1);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }


}
