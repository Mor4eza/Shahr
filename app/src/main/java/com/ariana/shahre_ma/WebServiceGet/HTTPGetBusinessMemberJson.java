package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;

import com.ariana.shahre_ma.Cards.Business_Card_Adapter;
import com.ariana.shahre_ma.DateBaseSqlite.AddDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.MyBusiness.My_Business;
import com.ariana.shahre_ma.Settings.KeySettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 6/6/2015...
 */
public class HTTPGetBusinessMemberJson extends AsyncTask<String,Void, Integer>
{

    FieldDataBusiness fdb=new FieldDataBusiness();
    FieldClass fc=new FieldClass();
    Query query;
    Integer errorCode=0;

    private static Context context;
    private  String url_Business;


    Integer Id[];
    String market[];
    String code[];
    String phone[];
    String mobile[];
    String fax[];
    String email[];
    String businessowner[];
    String address[];
    String description[];
    String startdate[];
    String expirationdate[];
    String inactive[];
    String subset[];
    String src[];
    Integer subsetid[];
    Double longitude[];
    Double latitude[];
    Integer areaid[];
    String area1[];
    String user[];
    Integer userid[];
    Integer field1[];
    Integer field2[];
    Integer field3[];
    Integer field4[];
    Integer field5[];
    Integer field6[];
    Integer field7[];
    Integer ratecount[];
    Double ratevalue[];

    Integer len=0;
    Integer i=0;


    private  List<Integer> selectId=new ArrayList<>();

    private  List<Integer> selectSubsetId=new ArrayList<>();
    private  List<Double>  selectLongtiude=new ArrayList<>();
    private  List<Double>  selectLatitude=new ArrayList<>();
    private  List<Double>  selectRate=new ArrayList<>();
    private  List<String>  selectPhone=new ArrayList<String>();
    private  List<String>  selectSrc=new ArrayList<String>();
    private  List<String>  selectMobile=new ArrayList<String>();
    private  List<String>  selectAddress=new ArrayList<>();
    private  List<String>  selectMarketName=new ArrayList<String>();

    /**
     *
     * @param member
     */
    public   void SetUrl_businessMember(Integer member)
    {
        url_Business="http://test.shahrma.com/api/ApiGiveMemberBusiness?memberId="+member;
        Log.i("url_Business",url_Business);
    }

    /**
     *
     * @return
     */
    private String GetUrl_business()
    {
        return  url_Business;
    }

    /**
     *
     * @param c
     */
    public HTTPGetBusinessMemberJson(Context c) {
        context = c;
    }

    /**
     *
     * @param args
     * @return
     */
    protected Integer doInBackground(String... args) {
        Integer result=0;
        try {

            InputStream jsonStream = getStreamFromURL(GetUrl_business(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);

            result=1;
        } catch (Exception e) {
            result=0;
        }
        return result;


    }

    /**
     *
     */
    @Override
    protected void onPostExecute(Integer result) {
        try {

            Integer count=0;
            Integer cityid=0;
            Integer idsubset=0;

            AddDataBaseSqlite adb = new AddDataBaseSqlite(context);
            DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);
            SelectDataBaseSqlite sdb=new SelectDataBaseSqlite(context);
            KeySettings setting=new KeySettings(context);
            query=new Query(context);
            cityid=query.getCityId(setting.getCityName());
            idsubset=fc.GetSubsetId();
            if(len>0){
                    ddb.delete_Business(cityid, idsubset);

                    for (int i = 0; i <len; i++)
                    {
                        Cursor rows=sdb.select_CountBusinessId(Id[i]);
                        rows.moveToFirst();
                        count=rows.getInt(0);
                        rows.close();
                        Log.i("ID", String.valueOf(Id[i]));
                        Log.i("count", String.valueOf(count));



                        if(count==0)
                            adb.Add_business(Id[i], market[i], code[i], phone[i], mobile[i], fax[i], email[i], businessowner[i]
                                    , address[i], description[i], startdate[i], expirationdate[i], inactive[i], subset[i],
                                    subsetid[i],latitude[i], longitude[i], areaid[i],area1[i], user[i],cityid, userid[i],
                                    field1[i], field2[i], field3[i], field4[i], field5[i],field6[i], field7[i], ratecount[i], ratevalue[i],src[i]);

                    }

                final My_Business business=new My_Business();
                business.mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        Business_Card_Adapter adapter = new Business_Card_Adapter(context);
                        business.mRecyclerView.setAdapter(adapter);
                        business.job_list_Adapter.notifyDataSetChanged();
                        business.pg.setVisibility(View.INVISIBLE);
                    }
                });

                Intent intent = new Intent("MyBusiness");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            } else {
                final My_Business business=new My_Business();
                business.mRecyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        Business_Card_Adapter adapter = new Business_Card_Adapter(context);
                        business.mRecyclerView.setAdapter(adapter);
                        business.job_list_Adapter.notifyDataSetChanged();
                        business.pg.setVisibility(View.INVISIBLE);
                    }
                });
                        Log.i("Count Business : ", "دریافت ثبت شده ها");

                Intent intent = new Intent("MyBusiness");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

            }

        } catch (Exception e) {

        }
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {

        try {

            Log.i("json",JSONString);
            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];
            market=new String[areas.length()];
            code=new String[areas.length()];
            phone=new String[areas.length()];
            mobile=new String[areas.length()];
            fax=new String[areas.length()];
            email=new String[areas.length()];
            businessowner=new String[areas.length()];
            address=new String[areas.length()];
            description=new String[areas.length()];
            startdate=new String[areas.length()];
            expirationdate=new String[areas.length()];
            inactive=new String[areas.length()];
            subset=new String[areas.length()];
            src=new String[areas.length()];
            subsetid=new Integer[areas.length()];
            longitude=new Double[areas.length()];
            latitude=new Double[areas.length()];
            areaid=new Integer[areas.length()];
            area1=new String[areas.length()];
            user=new String[areas.length()];
            userid=new Integer[areas.length()];
            field1=new Integer[areas.length()];
            field2=new Integer[areas.length()];
            field3=new Integer[areas.length()];
            field4=new Integer[areas.length()];
            field5=new Integer[areas.length()];
            field6=new Integer[areas.length()];
            field7=new Integer[areas.length()];
            ratecount=new Integer[areas.length()];
            ratevalue=new Double[areas.length()];
            len=areas.length();

            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);

                Id[i]=area.getInt("Id");

                inactive[i]=area.getString("Inactive");

                market[i]=area.getString("Market");
                mobile[i]=area.getString("Mobile");
                phone[i]=area.getString("Phone");
                startdate[i]=area.getString("Startdate");
                subset[i]=area.getString("Subset");
                subsetid[i]=area.getInt("SubsetId");
                user[i]=area.getString("User");
                userid[i]=area.getInt("UserId");
                src[i]=area.getString("Src");

                ratecount[i]=area.getInt("RateCount");
                ratevalue[i]=area.getDouble("RateAverage");

                address[i]=area.getString("Address");
                area1[i]=area.getString("Area");
                areaid[i]=area.getInt("AreaId");
                businessowner[i]=area.getString("BusinessOwner");
                code[i]=area.getString("Code");
                description[i]=area.getString("Description");
                email[i]=area.getString("Email");
                expirationdate[i]=area.getString("ExpirationDate");
                fax[i]=area.getString("Fax");
                field1[i]=area.getInt("Field1");
                field2[i]=area.getInt("Field2");
                field3[i]=area.getInt("Field3");
                field4[i]=area.getInt("Field4");
                field5[i]=area.getInt("Field5");
                field6[i] = area.getInt("Field6");
                field7[i] = area.getInt("Field7");

                fc.SetBusiness_Id(area.getInt("Id"));

                latitude[i] = Double.valueOf(area.getString("Latitude"));
                longitude[i] = Double.valueOf(area.getString("Longitude"));


                selectId.add(area.getInt("Id"));
                selectSubsetId.add(area.getInt("SubsetId"));
                selectLatitude.add(Double.valueOf(area.getString("Latitude")));
                selectLongtiude.add(Double.valueOf(area.getString("Longitude")));
                selectAddress.add(area.getString("Address"));
                selectMarketName.add(area.getString("Market"));
                selectSrc.add(area.getString("Src"));
                selectPhone.add(area.getString("Phone"));
                selectMobile.add(area.getString("Mobile"));
                selectRate.add(area.getDouble("RateAverage"));


            }

            fdb.SetIdBusiness(selectId);
            fdb.SetSubsetId(selectSubsetId);
            fdb.SetLatitudeBusiness(selectLatitude);
            fdb.SetLongtiudeBusiness(selectLongtiude);
            fdb.SetRateBusiness(selectRate);
            fdb.SetAddressBusiness(selectAddress);
            fdb.SetSrc(selectSrc);
            fdb.SetMarketBusiness(selectMarketName);
            fdb.SetPhoneBusiness(selectPhone);
            fdb.SetMobileBusiness(selectMobile);

        } catch (JSONException e) {
            Log.i("JSONException",e.toString());
        }
    }


    /**
     *
     * @param urlString
     * @param method
     * @return
     */
    InputStream getStreamFromURL(String urlString, String method) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setReadTimeout(10000);
            huc.setConnectTimeout(15000);
            huc.setRequestMethod(method);
            huc.setDoInput(true);

            huc.connect();
            errorCode=huc.getResponseCode();
            return huc.getInputStream();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param is
     * @return
     */
    String streamToString(InputStream is) {
        String result = "";
        String line = null;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                result = line + "\n";
            }
        } catch (Exception e) {

        }


        return result;
    }

}
