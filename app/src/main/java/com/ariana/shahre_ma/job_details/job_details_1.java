package com.ariana.shahre_ma.job_details;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOpinionJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPSendRateURL;


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

        Query query;
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
        RatingBar rate1;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_job_details_1, container, false);
            query=new Query(getActivity());
            ns=new NetState(getActivity());

            if(ns.checkInternetConnection()==false) {

            }
            else
            {


                HTTPGetOpinionJson httponion = new HTTPGetOpinionJson(getActivity());
                httponion.seturl_opinion(fc.GetBusiness_Id());
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
            rate1=(RatingBar) rootView.findViewById(R.id.ratingBar1);
            display_detail();
            rates_change();

            return rootView;
        }

        private  void rates_change(){


            Toast.makeText(getActivity(),"this",Toast.LENGTH_LONG).show();
            rate1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                    HTTPSendRateURL httprate=new HTTPSendRateURL(getActivity());
                    httprate.SetBusinessId(fc.GetBusiness_Id());
                    Log.i("SetBusinessId", String.valueOf(fc.GetBusiness_Id()));
                    httprate.SetMemberId(query.getMemberId());
                    Log.i("SetMemberId",String.valueOf(query.getMemberId()));
                    httprate.SetRate(Double.valueOf(rating));
                    Log.i("SetRate",String.valueOf(Double.valueOf(rating)));
                    httprate.execute();
                }
            });

        }

    private  void display_detail()
    {


        DataBaseSqlite mydb = new DataBaseSqlite(getActivity());
     //   Cursor allrows = mydb.select_business_Detail(fc.GetMarket_Business(), fc.GetAddress_Business());
        Cursor allrows = mydb.select_AllBusinessId(fc.GetBusiness_Id());
        Cursor row_notification=mydb.select_AllNotificaton(fc.GetBusiness_Id());
        allrows.moveToNext();
        row_notification.moveToNext();
        DateTime time=new DateTime();


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

            if(row_notification.getString(5).equals(time.Now()))
            {
                mydb.delete_Notification(fc.GetBusiness_Id());
            }

        }
        catch (Exception e){Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();}






    }
}
}
