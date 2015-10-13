package com.ariana.shahre_ma.MyCity;

/**
 * Created by ariana2 on 7/2/2015.
 */

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.victor.loading.rotate.RotateLoading;

import java.util.ArrayList;

public class Download_dialog extends Dialog {

    public static City_Dialog_Adapter adapter;
    ListView listView;
    Button cancel;
    TextView downloadCount;
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
        downloadCount =(TextView)findViewById(R.id.tv_download_count);
        adapter= new City_Dialog_Adapter(getContext(),generateData());
        listView.setAdapter(adapter);
        listView.setEnabled(false);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver, new IntentFilter("myCity_Download"));
        cancel=(Button)findViewById(R.id.cancel);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                My_city.business.cancel(true);
                dismiss();
            }
        });

    }
    public ArrayList<City_Dialog_Items> generateData(){

    items = new ArrayList<City_Dialog_Items>();

        for(int i=0;i<My_City_Adapter.selectedsubset.size();i++){
            items.add(new City_Dialog_Items(query.getsubsetName(Integer.valueOf(My_City_Adapter.selectedsubset.get(i))),0));
        }

        return items;
    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            try {

                final Integer position = intent.getIntExtra("received", 0);
                downloadCount.setText("در حال دانلود "+(position+1) + " از " +My_City_Adapter.selectedsubset.size()+"مورد");
                listView.smoothScrollToPosition(position + 2);
                View load = listView.getChildAt(position);
                RotateLoading loading = (RotateLoading) load.findViewById(R.id.loading);
                loading.stop();
                View dine = listView.getChildAt(position);
                ImageView done = (ImageView) dine.findViewById(R.id.downloaded);
                done.setVisibility(View.VISIBLE);

                listView.setOnScrollListener(new AbsListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                        View load = listView.getChildAt(position);
                        RotateLoading loading = (RotateLoading) load.findViewById(R.id.loading);
                        loading.stop();
                        View dine = listView.getChildAt(position);
                        ImageView done = (ImageView) dine.findViewById(R.id.downloaded);
                        done.setVisibility(View.VISIBLE);
                    }
                });
            }catch (Exception e){

            }

        }
    };

}
