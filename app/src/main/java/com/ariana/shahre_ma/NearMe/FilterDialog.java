package com.ariana.shahre_ma.NearMe;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;

/**
 * Created by ariana2 on 8/2/2015.
 */
public class FilterDialog extends Dialog {

    ListView listView;

    Button filter;
    public FilterDialog(Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_dialog_layout);
        FilterAdapter adapter = new FilterAdapter(getContext(), generateData());
        listView = (ListView) findViewById(R.id.filter_list);
        filter=(Button)findViewById(R.id.btn_filter);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selected = (String) listView.getItemAtPosition(position);

                Log.i("selected", selected);
            }
        });


        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Toast.makeText(getContext(),FilterAdapter.selectedsubset.toString(),Toast.LENGTH_LONG).show();
            }
        });



    }


    public ArrayList<FilterItems> generateData(){
        java.util.ArrayList<FilterItems> items = new ArrayList<FilterItems>();
        DataBaseSqlite db=new DataBaseSqlite(getContext());
        Cursor rows=db.select_Subset();
        if(rows.moveToFirst()) {
            do {
                items.add(new FilterItems(rows.getString(1),rows.getInt(0)));

            }while (rows.moveToNext());
        }

        return items;
    }
}
