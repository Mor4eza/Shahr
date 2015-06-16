package com.ariana.shahre_ma.job_details;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOpinionJson;


public class job_details_1 extends ActionBarActivity {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details_1);


        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }



    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        TextView name;
        TextView tel;
        TextView web;
        TextView owner;
        TextView subset;
        TextView zamine;
        TextView address;
        TextView des;
        FieldClass fc=new FieldClass();
NetState ns;


        public PlaceholderFragment() {


        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_job_details_1, container, false);
ns=new NetState(getActivity());

            if(ns.checkInternetConnection()==false) {

            }
            else
            {

                DataBaseSqlite dbs=new DataBaseSqlite(getActivity());
                dbs.delete_Opinion();
                HTTPGetOpinionJson httponion = new HTTPGetOpinionJson(getActivity());
                httponion.seturl_opinion(168);
                httponion.execute();
            }

            name=(TextView) rootView.findViewById(R.id.market_name);
            tel=(TextView) rootView.findViewById(R.id.market_tel);
            web=(TextView) rootView.findViewById(R.id.market_web);
            owner=(TextView) rootView.findViewById(R.id.market_owner);
            subset=(TextView) rootView.findViewById(R.id.market_subset);
            zamine=(TextView) rootView.findViewById(R.id.market_zamine);
            address=(TextView) rootView.findViewById(R.id.market_address);
            des=(TextView) rootView.findViewById(R.id.market_desc);

            display_detail();



            return rootView;
        }


    private  void display_detail()
    {


        DataBaseSqlite mydb = new DataBaseSqlite(getActivity());
        Cursor allrows = mydb.select_business_Detail(fc.GetMarket_Business(), fc.GetAddress_Business());
        allrows.moveToNext();


        try {
            fc.SetBusiness_Id(allrows.getInt(0));
            fc.SetLatitude_Business(allrows.getString(15));
            fc.SetLongtiude_Business(allrows.getString(16));

            name.setText(allrows.getString(1));
            tel.setText(allrows.getString(3));
            web.setText(allrows.getString(6));
            owner.setText(allrows.getString(7));
            subset.setText(allrows.getString(13));
            // zamine.setText(allrows.getString(25));
            address.setText(allrows.getString(8));
            des.setText(allrows.getString(9));
        }
        catch (Exception e){Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();}






    }
}
}
