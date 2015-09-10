package com.ariana.shahre_ma.job_details;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessImageJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOpinionJson;
import com.ariana.shahre_ma.WebServiceSend.HTTPSendRateURL;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import fr.quentinklein.slt.LocationTracker;
import fr.quentinklein.slt.TrackerSettings;


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
        Button tel;
        TextView web;
        TextView owner;
        TextView subset;
        TextView zamine;
        TextView address;
        TextView des;
        TextView tv_distance;
        FieldClass fc=new FieldClass();
        NetState ns;
        RatingBar rate1;
        LinearLayout parent;
        boolean sended=false;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_job_details_1, container, false);
            query=new Query(getActivity());
            ns=new NetState(getActivity());
            LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mBusinessImageReceiver, new IntentFilter("BusinessImage"));

            if(ns.checkInternetConnection()==false) {

            }
            else
            {
                HTTPGetOpinionJson httponion = new HTTPGetOpinionJson(getActivity());
                httponion.seturl_opinion(fc.GetBusiness_Id());
                httponion.execute();


                HTTPGetBusinessImageJson httpGetBusinessImageJson=new HTTPGetBusinessImageJson(getActivity());
                httpGetBusinessImageJson.SetBusinessId(fc.GetBusiness_Id());
                httpGetBusinessImageJson.execute();
            }

            name=(TextView) rootView.findViewById(R.id.market_name);
            tel=(Button) rootView.findViewById(R.id.market_tel);
            web=(TextView) rootView.findViewById(R.id.market_web);
            owner=(TextView) rootView.findViewById(R.id.market_owner);
            subset=(TextView) rootView.findViewById(R.id.market_subset);
            zamine=(TextView) rootView.findViewById(R.id.market_zamine);
            address=(TextView) rootView.findViewById(R.id.market_address);
            des=(TextView) rootView.findViewById(R.id.market_desc);
            rate1=(RatingBar) rootView.findViewById(R.id.ratingBar1);
            slider = (SliderLayout) rootView.findViewById(R.id.slider_jobs);
            tv_distance=(TextView)rootView.findViewById(R.id.market_distance);
            parent=(LinearLayout)rootView.findViewById(R.id.parent_jobs);
            display_detail();
            rates_change();
            display_Images();
            distance();

            tel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + tel.getText().toString()));
                    startActivity(intent);
                }
            });

            return rootView;
        }

         private  void rates_change(){

            rate1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if (!sended) {
                        if (query.getMemberId() > 0) {
                            HTTPSendRateURL httprate = new HTTPSendRateURL(getActivity());
                            httprate.SetBusinessId(fc.GetBusiness_Id());
                            Log.i("SetBusinessId", String.valueOf(fc.GetBusiness_Id()));
                            httprate.SetMemberId(query.getMemberId());
                            Log.i("SetMemberId", String.valueOf(query.getMemberId()));
                            httprate.SetRate(Double.valueOf(rating));
                            Log.i("SetRate", String.valueOf(Double.valueOf(rating)));
                            httprate.execute();
                            rate1.setEnabled(false);
                            sended=true;
                        } else {
                            Toast.makeText(getActivity(), "ابتدا وارد حساب خود شوید", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            });
        }

         private  void display_detail() {

             SelectDataBaseSqlite db = new SelectDataBaseSqlite(getActivity());
             try {
             if(fc.GetBusinessTops())
             {

                 Cursor allrows = db.select_AllBusinessTops(fc.GetBusiness_Id());
                 allrows.moveToNext();

                 fc.SetBusiness_Id(allrows.getInt(0));//Id
                 fc.SetLatitude_Business(allrows.getDouble(15));//Latitude
                 fc.SetLongtiude_Business(allrows.getDouble(16));//Longtiude
                 Log.i("Latitude", String.valueOf(allrows.getDouble(15)));
                 Log.i("Longitude", String.valueOf(allrows.getDouble(16)));
                 name.setText(allrows.getString(1));//Market
                 tel.setText(allrows.getString(3));//Phone
                 //tv_rateCount.setText(allrows.getInt(30));

                 //check email
                 if(allrows.getString(6).equals("null") || allrows.getString(6).equals(null) || allrows.getString(6).equals("")) {
                     web.setText("");//Email
                     parent.removeView((View) web.getParent());
                 }else
                     web.setText(allrows.getString(6));//Email

                 //check businessOwner
                 if(allrows.getString(7).equals("") || allrows.getString(7).equals("نن")) {
                     owner.setText("");//BusinessOwner
                     parent.removeView((View) owner.getParent());
                 }else
                     owner.setText(allrows.getString(7));//BusinessOwner


                 address.setText(allrows.getString(8));//Address


                 //check description
                 if(allrows.getString(9).equals("") || allrows.getString(9).equals("null") || allrows.getString(9).equals(null)) {
                     des.setText("");//Description
                     parent.removeView((View) des.getParent());
                 }
                 else
                     des.setText(allrows.getString(9));//Description


                 subset.setText(query.getsubsetName(allrows.getInt(14)));//Subset

                 for (int i = 0; i < 7; i++) {
                     Log.i("CounterFor", String.valueOf(allrows.getInt((22) + (i))));
                     if (allrows.getInt((22) + (i)) > 0) {

                         Cursor rows3 = db.select_FieldActivityName(allrows.getInt((22) + (i)));
                         rows3.moveToFirst();

                         zamine.setText(zamine.getText().toString() + rows3.getString(0) + ", ");
                     }
                 }


             }
             else if(fc.GetBusinessDisCountTops())
             {
                 Cursor allrows = db.select_AllBusinessDisCount(fc.GetBusiness_Id());
                 allrows.moveToNext();

                 fc.SetBusiness_Id(allrows.getInt(0));//Id
                 fc.SetLatitude_Business(allrows.getDouble(15));//Latitude
                 fc.SetLongtiude_Business(allrows.getDouble(16));//Longtiude
                 name.setText(allrows.getString(1));//Market
                 tel.setText(allrows.getString(3));//Phone
                // tv_rateCount.setText(allrows.getInt(30)); //rateCount

                 //check email
                 if(allrows.getString(6).equals("null") || allrows.getString(6).equals(null) || allrows.getString(6).equals("")){
                     web.setText("");//Email
                     parent.removeView((View) web.getParent());
                 } else
                     web.setText(allrows.getString(6));//Email

                 //check businessOwner
                 if(allrows.getString(7).equals("") || allrows.getString(7).equals("نن")) {
                     owner.setText("");//BusinessOwner
                     parent.removeView((View) owner.getParent());
                 }else
                     owner.setText(allrows.getString(7));//BusinessOwner


                 address.setText(allrows.getString(8));//Address


                 //check description
                 if(allrows.getString(9).equals("") || allrows.getString(9).equals("null") || allrows.getString(9).equals(null)){
                     des.setText("");//Description
                     parent.removeView((View) des.getParent());
                 }else
                     des.setText(allrows.getString(9));//Description



                 subset.setText(query.getsubsetName(allrows.getInt(14)));//Subset

                 for (int i = 0; i < 7; i++) {
                     Log.i("CounterFor", String.valueOf(allrows.getInt((22) + (i))));
                     if (allrows.getInt((22) + (i)) > 0) {

                         Cursor rows3 = db.select_FieldActivityName(allrows.getInt((22) + (i)));
                         rows3.moveToFirst();

                         zamine.setText(zamine.getText().toString() + rows3.getString(0) + ", ");
                     }
                 }

             }
             else
             {

                 Cursor allrows = db.select_AllBusinessId(fc.GetBusiness_Id());
                 allrows.moveToNext();

                 fc.SetBusiness_Id(allrows.getInt(0));//Id
                 fc.SetLatitude_Business(allrows.getDouble(10));//Latitude
                 fc.SetLongtiude_Business(allrows.getDouble(11));//Longtiude
                 Log.i("Latitude", String.valueOf(allrows.getDouble(10)));
                 Log.i("Longitude", String.valueOf(allrows.getDouble(11)));
                 name.setText(allrows.getString(1));//Market
                 tel.setText(allrows.getString(2));//Phone
                // tv_rateCount.setText(allrows.getInt(30)); //rateCount
                 //check email
                 if(allrows.getString(5).equals("null") || allrows.getString(5).equals(null) || allrows.getString(5).equals("")){
                     web.setText("");//Email
                     parent.removeView((View) web.getParent());
                 } else
                     web.setText(allrows.getString(5));//Email

                 //check businessOwner
                 if(allrows.getString(6).equals("") || allrows.getString(6).equals("نن")) {
                     owner.setText("");//BusinessOwner
                     parent.removeView((View) owner.getParent());
                 }else
                     owner.setText(allrows.getString(6));//BusinessOwner


                 address.setText(allrows.getString(7));//Address


                 //check description
                 if(allrows.getString(8).equals("") || allrows.getString(8).equals("null") || allrows.getString(8).equals(null)) {
                     des.setText("");//Description
                     parent.removeView((View) des.getParent());
                 }else
                     des.setText(allrows.getString(8));//Description

                 subset.setText(query.getsubsetName(allrows.getInt(9)));//Subset

                 for (int i = 0; i < 7; i++) {
                     Log.i("CounterFor", String.valueOf(allrows.getInt((12) + (i))));
                     if (allrows.getInt((12) + (i)) > 0) {

                         Cursor rows3 = db.select_FieldActivityName(allrows.getInt((12) + (i)));
                         rows3.moveToFirst();

                         zamine.setText(zamine.getText().toString() + rows3.getString(0) + ", ");
                     }
                 }



             }

             if(tel.getText().length()<2){
                 tel.setVisibility(View.GONE);
             }
       }
        catch (Exception e){
            //Toast.makeText(getActivity(),e.toString(),Toast.LENGTH_LONG).show();
        }

    }

         private void  display_Images() {
             try {
                 slider.removeAllSliders();
                 DataBaseSqlite db = new DataBaseSqlite(getActivity());
                 Cursor rows = db.select_BusinessImage(fc.GetBusiness_Id());

                 if (rows.getCount() > 0) {

                     if (rows.moveToFirst()) {
                         do {
                             Log.i("image", rows.getString(2));
                              final TextSliderView textSliderView1 = new TextSliderView(getActivity());
                             textSliderView1.image("http://www.shahrma.com/image/business/" + rows.getString(2));
                             slider.addSlider(textSliderView1);
                             textSliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                 @Override
                                 public void onSliderClick(BaseSliderView baseSliderView) {
                                     Show_Image_Dialog dialog=new Show_Image_Dialog(getActivity(),baseSliderView.getUrl());
                                     dialog.show();
                                 }
                             });
                         } while (rows.moveToNext());

                     }
                 }else{
                     final TextSliderView textSliderView = new TextSliderView(getActivity());
                     textSliderView.image("http://www.shahrma.com/image/business/" + query.getsubsetID(subset.getText().toString()) + ".jpg");
                     slider.addSlider(textSliderView);
                     textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                         @Override
                         public void onSliderClick(BaseSliderView baseSliderView) {
                             Log.i("clicked", slider.getCurrentSlider().getUrl());
                             Show_Image_Dialog dialog = new Show_Image_Dialog(getActivity(), baseSliderView.getUrl());
                             dialog.show();
                         }
                     });
                 }

                 slider.stopAutoCycle();
                 slider.setPresetTransformer(SliderLayout.Transformer.ZoomOutSlide);
                 slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);


             }
             catch (Exception e){}

         }

        private void distance(){


            TrackerSettings settings =
                    new TrackerSettings()
                            .setUseGPS(true)
                            .setUseNetwork(true)
                            .setTimeout(20000)
                            .setUsePassive(true);

            new LocationTracker(getActivity(), settings) {

                @Override
                public void onLocationFound(Location location) {
                    // Do some stuff when a new GPS Location has been found

                    try {
                        Location locationA = new Location("point A");
                        locationA.setLatitude(location.getLatitude());
                        locationA.setLongitude(location.getLongitude());

                        Location locationB = new Location("point B");
                        locationB.setLatitude(fc.GetLatitude_Business());
                        locationB.setLongitude(fc.GetLongtiude_Business());

                        float distance = locationA.distanceTo(locationB) ;
                        distance=Math.round(distance);
                        if (distance<1000) {
                            tv_distance.setText("فاصله تقریبی " + distance + " " + "متر");
                        }else{
                            distance=distance/1000;
                            tv_distance.setText("فاصله تقریبی " + distance + " " + "کیلومتر");
                        }
                        stopListen();
                    }catch (Exception e){

                    }

                }

                @Override
                public void onTimeout() {
                  tv_distance.setText("مکان شما شناسایی نشد...!");
                }
            };



        }

        private BroadcastReceiver mBusinessImageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent)
            {
             display_Images();
            }
        };
    }
}
