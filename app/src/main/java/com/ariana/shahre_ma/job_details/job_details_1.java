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
import com.ariana.shahre_ma.WebServiceSend.HTTPSendRateURL;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.TextSliderView;


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
        SliderLayout slider;
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
            slider = (SliderLayout) rootView.findViewById(R.id.slider_jobs);
            display_detail();
            rates_change();
            display_Images();
            return rootView;
        }

         private  void rates_change(){


            Toast.makeText(getActivity(),"this",Toast.LENGTH_LONG).show();
            rate1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                    if(query.getMemberId()>0) {
                        HTTPSendRateURL httprate = new HTTPSendRateURL(getActivity());
                        httprate.SetBusinessId(fc.GetBusiness_Id());
                        Log.i("SetBusinessId", String.valueOf(fc.GetBusiness_Id()));
                        httprate.SetMemberId(query.getMemberId());
                        Log.i("SetMemberId", String.valueOf(query.getMemberId()));
                        httprate.SetRate(Double.valueOf(rating));
                        Log.i("SetRate", String.valueOf(Double.valueOf(rating)));
                        httprate.execute();
                    }
                    else
                    {
                        Toast.makeText(getActivity(),"کاربری وارد نشده است",Toast.LENGTH_LONG).show();
                    }
                }
            });

        }

         private  void display_detail() {


        DataBaseSqlite mydb = new DataBaseSqlite(getActivity());
     //   Cursor allrows = mydb.select_business_Detail(fc.GetMarket_Business(), fc.GetAddress_Business());
        Cursor allrows = mydb.select_AllBusinessId(fc.GetBusiness_Id());
    //    Cursor row_notification=mydb.select_AllNotificaton(fc.GetBusiness_Id());
        allrows.moveToNext();
       // row_notification.moveToNext();
        DateTime time=new DateTime();


        try {
            fc.SetBusiness_Id(allrows.getInt(0));//Id
            fc.SetLatitude_Business(allrows.getDouble(10));//Latitude
            fc.SetLongtiude_Business(allrows.getDouble(11));//Longtiude

            name.setText(allrows.getString(1));//Market
            tel.setText(allrows.getString(2));//Phone
            web.setText(allrows.getString(5));//Email
            owner.setText(allrows.getString(6));//BusinessOwner
            address.setText(allrows.getString(7));//Address
            des.setText(allrows.getString(8));//Description
            subset.setText( query.getsubsetName(allrows.getInt(9)));//SubsetId







               for (int i = 0; i < 7; i++) {
                    Log.i("CounterFor", String.valueOf(allrows.getInt((12) + (i))));
                    if (allrows.getInt((12) + (i)) > 0) {

                        Cursor rows3 = mydb.select_FieldActivityName(allrows.getInt((12) + (i)));
                        rows3.moveToFirst();

                        zamine.setText(zamine.getText().toString() + rows3.getString(0) + ", ");
                    }
                }



       }
        catch (Exception e){Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();}






    }

         private void  display_Images(){



                 final TextSliderView textSliderView = new TextSliderView(getActivity());
                 textSliderView
                         .image(R.drawable.pooshak);
                 slider.addSlider(textSliderView);

                 TextSliderView textSliderView2 = new TextSliderView(getActivity());
                 textSliderView2
                         .image(R.drawable.pooshak);
                 slider.addSlider(textSliderView2);

                 TextSliderView textSliderView3 = new TextSliderView(getActivity());
                 textSliderView3
                         .image(R.drawable.pooshak);
                 slider.addSlider(textSliderView3);


                 slider.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
                 slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
         }
    }
}
