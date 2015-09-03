package com.ariana.shahre_ma.MyCity;

/**
 * Created by ariana2 on 7/2/2015.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJsonArray;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

public class Download_dialog extends Dialog {

    public static City_Dialog_Adapter adapter;
    ListView listView;
    Button cancel;
    FieldClass fc=new FieldClass();
    Query query=new Query(getContext());
    ArrayList<City_Dialog_Items> items;
    public Download_dialog(Context context) {
        super(context);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.download_dialog);
        setCanceledOnTouchOutside(false);
        listView=(ListView)findViewById(R.id.dialog_listview);
        adapter= new City_Dialog_Adapter(getContext(),generateData());
        listView.setAdapter(adapter);

        final RotateLoading loading=(RotateLoading)findViewById(R.id.loading);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //adapter.notifyDataSetChanged();
            }
        });

        cancel=(Button)findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HTTPGetBusinessJsonArray business = new HTTPGetBusinessJsonArray(getContext());
                business.cancel(true);
                dismiss();
            }
        });
    }
    public ArrayList<City_Dialog_Items> generateData(){

    items = new ArrayList<City_Dialog_Items>();
       // int i=0;
    /*    for (String subset: fc.GetNameSubset())
        {
            Log.i("Subsetid",subset);
            items.add(new City_Dialog_Items(query.getsubsetName(Integer.valueOf(subset)),i++));
        }
*/
        Log.i("selectedSubset",String.valueOf(My_City_Adapter.selectedsubset.size()));
        for(int i=0;i<My_City_Adapter.selectedsubset.size();i++){
            items.add(new City_Dialog_Items(query.getsubsetName(Integer.valueOf(My_City_Adapter.selectedsubset.get(i))),0));
        }

        return items;
    }

}
