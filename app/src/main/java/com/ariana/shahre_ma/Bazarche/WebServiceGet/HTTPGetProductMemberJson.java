package com.ariana.shahre_ma.Bazarche.WebServiceGet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ariana.shahre_ma.Fields.FieldDataBase;

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
 * Created by ariana on 8/18/2015.
 */
public class HTTPGetProductMemberJson extends AsyncTask<String,Void,Integer>
{
    private static Context context;
    FieldDataBase fdb=new FieldDataBase();
    private  String url_productmember="http://test.shahrma.com/api/ApiGiveMyProduct?MemberId=";
    Integer errorCode=0;

    List<Integer> selectMemberId =new ArrayList<>();
    List<Integer> selectId =new ArrayList<>();
    List<String>  selectName =new ArrayList<>();
    List<String>  selectDate =new ArrayList<>();
    List<String>  selectProperty =new ArrayList<>();
    List<String>  selectPrice =new ArrayList<>();
    List<Double>  selectLatitude =new ArrayList<>();
    List<Double>  selectLongtiude =new ArrayList<>();
    List<Boolean> selectAdaptive =new ArrayList<>();
    List<String>  selectDescription =new ArrayList<>();
    List<String>  selectImage =new ArrayList<>();
    List<String>  selectPhone =new ArrayList<>();
    List<String>  selectMobile =new ArrayList<>();
    List<String>  selectAddress =new ArrayList<>();
    List<String>  selectEmail =new ArrayList<>();
    List<Integer> selectSubsetId =new ArrayList<>();
    List<Integer> selectAreaId =new ArrayList<>();



    Integer len;


    public void  setMemberId(Integer memberId)
    {
        url_productmember=url_productmember+memberId;
    }

    private  String getMemberId()
    {
        return url_productmember;
    }
    /**
     *
     * @param c
     */
    public HTTPGetProductMemberJson(Context c) {
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


            InputStream jsonStream = getStreamFromURL(getMemberId(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            result=1;
        } catch (Exception e) {
            result=0;
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
        }
        return result;


    }

    @Override
    protected void onPostExecute(Integer result) {
        try
        {
            if(result==1)
            {
              if(len>0)
              {
                  Intent intent = new Intent("ProductMember");
                  LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
              }
            }
            else
            {

            }
        } catch (Exception e) {

        }
    }


    void parseJSON(String JSONString) {
        try {

            JSONArray areas = new JSONArray(JSONString);
            Log.i("JSONmyProduct", JSONString);
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);

                selectId.add(area.getInt("Id"));
                selectMemberId.add(area.getInt("MemberId"));
                selectName.add(area.getString("Name"));
                selectDate.add(area.getString("DateTime"));
                selectProperty.add(area.getString("Property"));
                selectPrice.add(area.getString("Price"));
                selectLatitude.add(area.getDouble("Latitude"));
                selectLongtiude.add(area.getDouble("Longtude"));
                selectAdaptive.add(area.getBoolean("Adaptive"));
                selectDescription.add(area.getString("Description"));
                selectImage.add(area.getString("Image"));
                selectPhone.add(area.getString("Phone"));
                selectMobile.add(area.getString("Mobile"));
                selectAddress.add(area.getString("Address"));
                selectEmail.add(area.getString("Email"));
                selectSubsetId.add(area.getInt("ProductSubsetId"));
                selectAreaId.add(area.getInt("AreaId"));

            }

                fdb.setId_Product(selectId);
                fdb.setMemberId_Product(selectMemberId);
                fdb.setName_Product(selectName);
                fdb.setDate_Product(selectDate);
                fdb.setProperty_Product(selectProperty);
                fdb.setPrice_Product(selectPrice);
                fdb.setLatitude_Product(selectLatitude);
                fdb.setLongtiude_Product(selectLongtiude);
                fdb.setAdaptive__Product(selectAdaptive);
                fdb.setDescription_Product(selectDescription);
                fdb.setImage_Product(selectImage);
                fdb.setPhone__Product(selectPhone);
                fdb.setMobile_Product(selectMobile);
                fdb.setAddress_Product(selectAddress);
                fdb.setEmail_Product(selectEmail);
                fdb.setSubsetId_Product(selectAreaId);
                fdb.setAreaId_Product(selectAreaId);

        } catch (JSONException e)
        {
            Log.e("JSONException", e.toString());
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
            Log.e("Exceptiongetstreamurl", e.toString());
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
