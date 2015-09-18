package com.ariana.shahre_ma.DateBaseSqlite;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana on 9/7/2015.
 */
public class AdapterDataBase
{
    Context context;

    AdapterDataBase(Context context)
    {
        this.context=context;
    }




    public ArrayAdapter getnamecollect(Cursor cursor,int idField) {
        List<String> arrayList = new ArrayList<String>();
        Cursor allrows  =cursor;
        if (allrows.moveToFirst()) {
            do
            {
                arrayList.add(allrows.getString(idField));

            } while (allrows.moveToNext());
        }

        ArrayAdapter adapter = new ArrayAdapter(context, android.R.layout.simple_list_item_1,arrayList);
        return adapter;
    }




}
