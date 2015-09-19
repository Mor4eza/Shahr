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
public class HTTPGetProductpropertiesJson extends AsyncTask<String,Void,Integer>
{
    private static Context context;
    FieldDataBase fdb=new FieldDataBase();
    private static String url_productproperty="http://test.shahrma.com/api/ApiGiveProduct?Id=";
    Integer errorCode=0;

    List<Integer> selectMemberId =new ArrayList<>();
    List<Integer> selectId =new ArrayList<>();
    List<String>  selectName =new ArrayList<>();
    List<String>  selectDate =new ArrayList<>();
    List<String>  selectProperty =new ArrayList<>();
    List<Double>  selectPrice =new ArrayList<>();
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
    List<Integer> selectPropertyId =new ArrayList<>();
    List<String>  selectValue =new ArrayList<>();


    Integer len;

    /**
     *
     * @param c
     */
    public HTTPGetProductpropertiesJson(Context c) {
        context = c;
    }


    public void setProductId(Integer productId)
    {
        url_productproperty=url_productproperty+productId;
    }

    private String getUrl_productproperty()
    {
        Log.i("url",url_productproperty);
        return url_productproperty;

    }
    /**
     *
     * @param args
     * @return
     */
    protected Integer doInBackground(String... args) {
        Integer result=0;
        try {
            InputStream jsonStream = getStreamFromURL(getUrl_productproperty(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            result=1;
        } catch (Exception e) {
            result=0;
        }
        return result;


    }

    @Override
    protected void onPostExecute(Integer result) {
        //        onPostExecute(result);

        try
        {
            if(result==1)
            {

                Intent intent = new Intent("Product_property");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
            else
            {

            }
        } catch (Exception e) {
        }
    }


    void parseJSON(String JSONString) {
        try {

                Log.i("ProductProperty", JSONString);
                JSONObject area =new JSONObject(JSONString);
                selectId.add(area.getInt("Id"));
                selectMemberId.add(area.getInt("MemberId"));
                selectName.add(area.getString("Name"));
                selectDate.add(area.getString("DateTime"));
                selectProperty.add(area.getString("Property"));
                selectPrice.add(area.getDouble("Price"));
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


            JSONArray array = area.getJSONArray("Properties");
            for (int i=0; i<array.length(); i++)
            {
                JSONObject object = array.getJSONObject(i);
                selectPropertyId.add(object.getInt("PropertyId"));
                selectValue.add(object.getString("Value"));
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
            fdb.setPropertyId_Product(selectPropertyId);
            fdb.setValue_Product(selectValue);

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
            Log.i("getStreamFromURL", "true");
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
            Log.e("ExceptiongetStream",e.toString());
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

