package com.ariana.shahre_ma.WebServiceSend;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.Settings.KeySettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 8/3/2015.
 */
public class HTTPSendNearMeURL extends AsyncTask<String,Void,Integer >
{
    Context context;
    String url_nearme="";
    Integer errorCode=0;
    FieldClass fc=new FieldClass();
    Query query;
    FieldDataBusiness fdb=new FieldDataBusiness();

    private List<Integer> selectId=new ArrayList<>();
    private  List<Integer> selectDiscountId=new ArrayList<>();
    private List<Integer> selectsubsetId=new ArrayList<>();
    private  List<Double>  selectLongtiude=new ArrayList<>();
    private  List<Double>  selectLatitude=new ArrayList<>();
    private  List<Double>  selectRate=new ArrayList<>();
    private  List<String>  selectPhone=new ArrayList<String>();
    private  List<String>  selectSrc=new ArrayList<String>();
    private  List<String>  selectMobile=new ArrayList<String>();
    private  List<String>  selectAddress=new ArrayList<>();
    private  List<String>  selectMarketName=new ArrayList<String>();

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
    Integer subsetid[];
    Double longitude[];
    Double latitude[];
    Integer areaid[];
    String area1[];
    String user[];
    String src[];
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

    Integer discountid[];
    String discounttext[];
    String discountimage[];
    String discountstartdate[];
    String discountexpirationdate[];
    String discountdescription[];
    String discountpercent[];
    Integer discountbusinessid[];

    Integer likediscount[];
    Integer dislikediscount[];


    Integer len=0;
    Integer i=0;



    public HTTPSendNearMeURL(Context context)
    {
        this.context=context;

    }

    public void SetNearMe(String latitude,String longitude,double distance)
    {
        url_nearme="http://test.shahrma.com/api/ApiGiveNearBusiness?latitude="+latitude+"&longitude="+longitude+"&distance="+distance;
    }

    private String getNearMe()
    {
        return url_nearme;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        Integer result=0;

        try
        {
            Log.i("URL",getNearMe());
            InputStream stream=getStreamFromUrl(getNearMe(),"GET");
            String Json=streamToString(stream);
            parseJSON(Json);

            result=1;
        }
        catch (Exception e)
        {
            result=0;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1) {
            try {

                KeySettings setting = new KeySettings(context);
                query = new Query(context);


                Integer cityid = 0;
                Integer idsubset = 0;

                DataBaseSqlite dbs = new DataBaseSqlite(context);


                cityid = query.getCityId(setting.getCityName());
                idsubset = fc.GetSubsetId();

                dbs.delete_Business(cityid, idsubset);

                if (len == 0) {
                    //  Toast.makeText(get, "فروشگاه ثبت نشده", Toast.LENGTH_LONG).show();
                    Log.i("Count Business : ", "فروشگاه ثبت نشد");

                } else {



                    for (int i = 0; i < len; i++)
                    {
                        dbs.delete_BusinessId(Id[i]);
                        dbs.delete_DisCount(discountid[i]);
                        if (discountid[i] == 0) {
                            Log.i("ifbusiness", "0");
                        } else {
                            Log.i("elsebusiness", "i>0");
                            dbs.Add_DisCount(discountid[i], discounttext[i], discountimage[i], discountstartdate[i], discountexpirationdate[i], discountdescription[i], discountpercent[i], discountbusinessid[i], likediscount[i], dislikediscount[i]);
                        }
                        //dbs.Add_LikeDisCount(1,166,Id[i],likediscount[i],dislikediscount[i]);
                        dbs.Add_business(Id[i], market[i], code[i], phone[i], mobile[i], fax[i], email[i], businessowner[i], address[i], description[i], startdate[i], expirationdate[i], inactive[i], subset[i], subsetid[i],latitude[i], longitude[i], areaid[i], area1[i], user[i], cityid, userid[i], field1[i], field2[i], field3[i], field4[i], field5[i], field6[i], field7[i], ratecount[i], ratevalue[i],src[i]);

                    }
                    Intent intent = new Intent("near-me");
                    intent.putExtra("received", "Markets");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                    fc.SetCount_Business(query.getCountBusiness(query.getsubsetID(fc.GetSelected_job())));
                }

            } catch (Exception e) {

                Log.e("ExceptionBusinessJson", e.toString());
            }
        }
        else
        {

        }
    }

    InputStream getStreamFromUrl(String url,String method) {

        try
        {
            URL url1=new URL(url);
            HttpURLConnection huc=(HttpURLConnection) url1.openConnection();
            huc.setReadTimeout(10000);
            huc.setConnectTimeout(15000);
            huc.setDoInput(true);

            return huc.getInputStream();
        } catch (Exception e)
        {
            return null;
        }
    }

    String streamToString(InputStream is){

        String result="";
        String line=null;
        BufferedReader br=new BufferedReader(new InputStreamReader(is));

        try {
            while ((line=br.readLine()) !=null)
            {
                result=line+"\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    void parseJSON(String Json) {


        try {
            JSONArray areas = new JSONArray(Json);
            Id = new Integer[areas.length()];
            market = new String[areas.length()];
            code = new String[areas.length()];
            phone = new String[areas.length()];
            mobile = new String[areas.length()];
            fax = new String[areas.length()];
            email = new String[areas.length()];
            businessowner = new String[areas.length()];
            address = new String[areas.length()];
            description = new String[areas.length()];
            startdate = new String[areas.length()];
            expirationdate = new String[areas.length()];
            inactive = new String[areas.length()];
            subset = new String[areas.length()];
            src = new String[areas.length()];
            subsetid = new Integer[areas.length()];
            longitude = new Double[areas.length()];
            latitude = new Double[areas.length()];
            areaid = new Integer[areas.length()];
            area1 = new String[areas.length()];
            user = new String[areas.length()];
            userid = new Integer[areas.length()];

            field1 = new Integer[areas.length()];
            field2 = new Integer[areas.length()];
            field3 = new Integer[areas.length()];
            field4 = new Integer[areas.length()];
            field5 = new Integer[areas.length()];
            field6 = new Integer[areas.length()];
            field7 = new Integer[areas.length()];

            ratecount = new Integer[areas.length()];
            ratevalue = new Double[areas.length()];

            discountid = new Integer[areas.length()];
            discounttext = new String[areas.length()];
            discountimage = new String[areas.length()];
            discountstartdate = new String[areas.length()];
            discountexpirationdate = new String[areas.length()];
            discountdescription = new String[areas.length()];
            discountpercent = new String[areas.length()];
            discountbusinessid = new Integer[areas.length()];

            likediscount = new Integer[areas.length()];
            dislikediscount = new Integer[areas.length()];

            len = areas.length();
            for (int i = 0; i < areas.length(); i++) {


                JSONObject area = areas.getJSONObject(i);
                address[i] = area.getString("Address");
                area1[i] = area.getString("Area");
                areaid[i] = area.getInt("AreaId");
                businessowner[i] = area.getString("BusinessOwner");
                code[i] = area.getString("Code");
                description[i] = area.getString("Description");
                email[i] = area.getString("Email");
                expirationdate[i] = area.getString("ExpirationDate");
                fax[i] = area.getString("Fax");
                field1[i] = area.getInt("Field1");
                field2[i] = area.getInt("Field2");
                field3[i] = area.getInt("Field3");
                field4[i] = area.getInt("Field4");
                field5[i] = area.getInt("Field5");
                field6[i] = area.getInt("Field6");
                field7[i] = area.getInt("Field7");
                Id[i] = area.getInt("Id");
                inactive[i] = area.getString("Inactive");

                Log.i("latitude",area.getString("Latitude"));
                Log.i("longitude",area.getString("Longitude"));


                market[i] = area.getString("Market");
                mobile[i] = area.getString("Mobile");
                phone[i] = area.getString("Phone");
                startdate[i] = area.getString("Startdate");
                subset[i] = area.getString("Subset");
                subsetid[i] = area.getInt("SubsetId");
                user[i] = area.getString("User");
                userid[i] = area.getInt("UserId");
                src[i] = area.getString("Src");

                ratecount[i] = area.getInt("RateCount");
                ratevalue[i] = area.getDouble("RateAverage");


                discountid[i] = area.getInt("DiscountId");
                discounttext[i] = area.getString("DiscountText");
                discountimage[i] = area.getString("DiscountImage");
                discountstartdate[i] = area.getString("DiscountStartdate");
                discountexpirationdate[i] = area.getString("DiscountExpirationDate");
                discountpercent[i] = area.getString("DiscountPercent");
                discountdescription[i] = area.getString("DiscountDescription");
                discountbusinessid[i] = area.getInt("Id");

                likediscount[i] = area.getInt("DiscountLike");
                dislikediscount[i] = area.getInt("DiscountDislike");
                latitude[i] = Double.valueOf(area.getString("Latitude"));
                longitude[i] = Double.valueOf(area.getString("Longitude"));

                selectsubsetId.add(area.getInt("SubsetId"));
                selectDiscountId.add(area.getInt("DiscountId"));
                selectId.add(area.getInt("Id"));
                selectLatitude.add(Double.valueOf(area.getString("Latitude")));
                selectLongtiude.add(Double.valueOf(area.getString("Longitude")));
                selectAddress.add(area.getString("Address"));
                selectSrc.add(area.getString("Src"));
                selectMarketName.add(area.getString("Market"));
                selectPhone.add(area.getString("Phone"));
                selectMobile.add(area.getString("Mobile"));
                selectRate.add(area.getDouble("RateAverage"));


            }

            fdb.SetSubsetId(selectsubsetId);
            fdb.SetDisCountId(selectDiscountId);
            fdb.SetIdBusiness(selectId);
            fdb.SetLatitudeBusiness(selectLatitude);
            fdb.SetLongtiudeBusiness(selectLongtiude);
            fdb.SetRateBusiness(selectRate);
            fdb.SetAddressBusiness(selectAddress);
            fdb.SetSrc(selectSrc);
            fdb.SetMarketBusiness(selectMarketName);
            fdb.SetPhoneBusiness(selectPhone);
            fdb.SetMobileBusiness(selectMobile);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}


