package com.ariana.shahre_ma;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;

import java.util.ArrayList;
import java.util.List;


public class BookMark extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_mark);
        bookmark();
    }



    public void bookmark()
    {
        try
        {
            ListView lv=(ListView) findViewById(R.id.lvbookmark);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getbookmark());
            lv.setAdapter(adapter);

            //  multiAutoComplete.setAdapter(adapter);

            //et2.setThreshold(1);
        }
        catch (Exception e) {
        }
    }

    private List<String> getbookmark() {
        List<String> item=new ArrayList<String>();
        DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor allrows = db.select_bookmark();


        if(allrows.moveToFirst())
        {
            do
            {
                Cursor row = db.select_business_NameMarket(allrows.getInt(1));
                row.moveToNext();
                item.add(row.getString(0));

            }while (allrows.moveToNext());

        }
        allrows.close();


        return item;
    }
}
