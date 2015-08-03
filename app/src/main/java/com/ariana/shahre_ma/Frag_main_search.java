package com.ariana.shahre_ma;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOnlineSearchJson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 7/16/2015.
 */
public class Frag_main_search extends Fragment {
    private String title;
    private int page;
    private Button btnSearch;
    private TextView txtWhat;
    private TextView txtWhere;
    FieldClass fc=new FieldClass();
    FieldDataBusiness fdb=new FieldDataBusiness();
    Cursor rows;


    private List<Integer>  selectId=new ArrayList<>();
    private  List<String>  selectLongtiude=new ArrayList<>();
    private  List<String>  selectLatitude=new ArrayList<>();
    private  List<Double>  selectRate=new ArrayList<>();
    private  List<String>  selectPhone=new ArrayList<String>();
    private  List<String>  selectMobile=new ArrayList<String>();
    private  List<String>  selectAddress=new ArrayList<>();
    private  List<String>  selectMarketName=new ArrayList<String>();

    // newInstance constructor for creating fragment with arguments
    public static Frag_main_search newInstance(int page, String title) {
        Frag_main_search fragmentFirst = new Frag_main_search();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frag_main_search, container, false);
        TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        tvLabel.setText(page + " -- " + title);
        btnSearch=(Button)view.findViewById(R.id.btn_search);
        txtWhat=(EditText)view.findViewById(R.id.et_search_what);
        txtWhere=(EditText)view.findViewById(R.id.et_search_where);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NetState ns= new NetState(getActivity());
                DataBaseSqlite db=new DataBaseSqlite(getActivity());
                if(ns.checkInternetConnection())
                {
                    Log.i("ss","s");
                    HTTPGetOnlineSearchJson httpGetOnlineSearchJson = new HTTPGetOnlineSearchJson(getActivity());
                    httpGetOnlineSearchJson.SetValueSearch(txtWhat.getText().toString(), 68);
                    httpGetOnlineSearchJson.execute();
                }
                else
                {
                     rows=db.select_BusinessSearchNameMarket(txtWhat.getText().toString());
                     Cursor rows1=db.select_BusinessSearchAddreass(txtWhat.getText().toString());
                    if(rows.moveToFirst())
                    {
                        do
                        {
                            selectAddress.add(rows.getString(8));
                            selectMarketName.add(rows.getString(1));
                            selectPhone.add(rows.getString(3));
                            selectMobile.add(rows.getString(4));
                            selectId.add(rows.getInt(0));
                            selectLatitude.add(rows.getString(16));
                            selectLongtiude.add(rows.getString(15));
                            selectRate.add(rows.getDouble(30));

                        }while (rows.moveToNext());


                    }
                    rows.close();


                    if(rows1.moveToFirst())
                    {
                        do
                        {
                            selectAddress.add(rows1.getString(8));
                            selectMarketName.add(rows1.getString(1));
                            selectPhone.add(rows1.getString(3));
                            selectMobile.add(rows1.getString(4));
                            selectId.add(rows1.getInt(0));
                            selectLatitude.add(rows1.getString(16));
                            selectLongtiude.add(rows1.getString(15));
                            selectRate.add(rows1.getDouble(30));

                        }while (rows1.moveToNext());


                    }

                    fdb.SetIdBusiness(selectId);
                    fdb.SetLatitudeBusiness(selectLatitude);
                    fdb.SetLongtiudeBusiness(selectLongtiude);
                    fdb.SetRateBusiness(selectRate);
                    fdb.SetAddressBusiness(selectAddress);
                    fdb.SetMarketBusiness(selectMarketName);
                    fdb.SetPhoneBusiness(selectPhone);
                    fdb.SetMobileBusiness(selectMobile);

                    Log.i("Count", String.valueOf(rows.getCount()));
                    Log.i("Count1", String.valueOf(rows1.getCount()));

                    if(rows.getCount()>0) {
                        fc.SetSearchOffline(true);
                        Intent intent = new Intent(getActivity(), Jobs_List.class);
                        getActivity().startActivity(intent);

                    }
                }




            }
        });

        return view;
    }
}
