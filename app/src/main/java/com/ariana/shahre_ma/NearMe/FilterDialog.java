package com.ariana.shahre_ma.NearMe;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 8/2/2015.
 */
public class FilterDialog extends Dialog {

    ListView listView;
    FieldDataBusiness fdb=new FieldDataBusiness();
    Button filter;
    FieldClass fc=new FieldClass();
    public FilterDialog(Context context) {
        super(context);
    }


    private List<Integer> selectId=new ArrayList<>();
    private  List<Double>  selectLongtiude=new ArrayList<>();
    private  List<Double>  selectLatitude=new ArrayList<>();
    private  List<Double>  selectRate=new ArrayList<>();
    private  List<String>  selectPhone=new ArrayList<String>();
    private  List<String>  selectMobile=new ArrayList<String>();
    private  List<String>  selectAddress=new ArrayList<>();
    private  List<String>  selectMarketName=new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.filter_dialog_layout);
        setTitle("فیلتر کردن");
        FilterAdapter adapter = new FilterAdapter(getContext(), generateData());
        listView = (ListView) findViewById(R.id.filter_list);
        filter=(Button)findViewById(R.id.btn_filter);
        listView.setAdapter(adapter);
        FilterAdapter.selectedsubset.clear();
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
                SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(getContext());
                Query query = new Query(getContext());
                Log.i("Filter", "start");
                Log.i("Filter", String.valueOf(FilterAdapter.selectedsubset.size()));
                for (int i = 0; i < FilterAdapter.selectedsubset.size(); i++) {
                    Cursor rows = sdb.select_BusinessSearchNearMe(fc.GetcurLatitude(), fc.GetcurLongitude(), 0.001, query.getsubsetID(FilterAdapter.selectedsubset.get(i)));
                    if (rows.moveToFirst()) {
                        do {
                            Log.i("Filter", rows.getString(1));
                            selectAddress.add(rows.getString(8));
                            selectMarketName.add(rows.getString(1));
                            selectPhone.add(rows.getString(3));
                            selectMobile.add(rows.getString(4));
                            selectId.add(rows.getInt(0));
                            selectLatitude.add(rows.getDouble(15));
                            selectLongtiude.add(rows.getDouble(16));
                            selectRate.add(rows.getDouble(30));
                        } while (rows.moveToNext());
                    }
                }

                fdb.SetIdBusiness(selectId);
                fdb.SetLatitudeBusiness(selectLatitude);
                fdb.SetLongtiudeBusiness(selectLongtiude);
                fdb.SetRateBusiness(selectRate);
                fdb.SetAddressBusiness(selectAddress);
                fdb.SetMarketBusiness(selectMarketName);
                fdb.SetPhoneBusiness(selectPhone);
                fdb.SetMobileBusiness(selectMobile);

                Intent intent = new Intent("filtered");
                intent.putExtra("received", "filters");
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(intent);

                Toast.makeText(getContext(), FilterAdapter.selectedsubset.toString(), Toast.LENGTH_LONG).show();
                dismiss();
            }

        });

    }

    public ArrayList<FilterItems> generateData(){
        Query query=new Query(getContext());
        java.util.ArrayList<FilterItems> items = new ArrayList<FilterItems>();
        List<Integer> subsetId = new ArrayList<>();

        for (int i = 0; i < fdb.GetMarketBusiness().size(); i++) {

            Log.i("for1",String.valueOf(fdb.GetSubsetId().get(i)));
                subsetId.add(fdb.GetSubsetId().get(i));

        }


        for (int i = 0; i < subsetId.size(); i++)
            for(int j = i+1; j <subsetId.size(); j++)
            {
                if(subsetId.get(i)==subsetId.get(j))
                {

                  subsetId.remove(i);
                }
            }


        for (int i = 0; i < subsetId.size(); i++) {

            Log.i("for2",String.valueOf(subsetId.get(i)));
            items.add(new FilterItems(query.getsubsetName(subsetId.get(i)),subsetId.get(i)));
        }

        return items;
    }
}
