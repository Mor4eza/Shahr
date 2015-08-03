package com.ariana.shahre_ma.WebServiceSend;

import android.content.Context;
import android.os.AsyncTask;

import com.ariana.shahre_ma.Fields.FieldDataBusiness;

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
    FieldDataBusiness fdb=new FieldDataBusiness();

    private List<Integer> selectId=new ArrayList<>();
    private  List<String>  selectLongtiude=new ArrayList<>();
    private  List<String>  selectLatitude=new ArrayList<>();
    private  List<Double>  selectRate=new ArrayList<>();
    private  List<String>  selectPhone=new ArrayList<String>();
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
    String longitude[];
    String latitude[];
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

    public void SetNearMe(String latitude,String longitude)
    {
        url_nearme="http://test.shahrma.com/api/ApiGiveNearBusiness?latitude="+latitude+"&longitude="+longitude;
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
            subsetid = new Integer[areas.length()];
            longitude = new String[areas.length()];
            latitude = new String[areas.length()];
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
                latitude[i] = area.getString("Latitude");
                longitude[i] = area.getString("Longitude");
                market[i] = area.getString("Market");
                mobile[i] = area.getString("Mobile");
                phone[i] = area.getString("Phone");
                startdate[i] = area.getString("Startdate");
                subset[i] = area.getString("Subset");
                subsetid[i] = area.getInt("SubsetId");
                user[i] = area.getString("User");
                userid[i] = area.getInt("UserId");


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


                selectId.add(area.getInt("Id"));
                selectLatitude.add(area.getString("Latitude"));
                selectLongtiude.add(area.getString("Longitude"));
                selectAddress.add(area.getString("Address"));
                selectMarketName.add(area.getString("Market"));
                selectPhone.add(area.getString("Phone"));
                selectMobile.add(area.getString("Mobile"));
                selectRate.add(area.getDouble("RateAverage"));


            }

            fdb.SetIdBusiness(selectId);
            fdb.SetLatitudeBusiness(selectLatitude);
            fdb.SetLongtiudeBusiness(selectLongtiude);
            fdb.SetRateBusiness(selectRate);
            fdb.SetAddressBusiness(selectAddress);
            fdb.SetMarketBusiness(selectMarketName);
            fdb.SetPhoneBusiness(selectPhone);
            fdb.SetMobileBusiness(selectMobile);


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}


