package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
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
public class HTTPGetProductPropertyJson extends AsyncTask<String,Void,Integer>
{
    private static Context context;
    FieldDataBase fdb=new FieldDataBase();
    private static String url_productproperty="http://test.shahrma.com/api/ApiGiveProduct?Id=";

    List<Integer> selectMemberId =new ArrayList<>();
    List<String>  selectName =new ArrayList<>();
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


    Integer len;

    /**
     *
     * @param c
     */
    public HTTPGetProductPropertyJson(Context c) {
        context = c;
    }


    public void setProductId(Integer productId)
    {
        url_productproperty=url_productproperty+productId;
    }

    private String getUrl_productproperty()
    {
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
            Log.i("JSONsubset", JSONString);
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);

                selectMemberId.add(area.getInt("MemberId"));
                selectName.add(area.getString("Name"));
                selectProperty.add(area.getString("Property"));
                selectPrice.add(area.getDouble("Price"));
                selectLatitude.add(area.getDouble("Latitude"));
                selectLongtiude.add(area.getDouble("Longtiude"));
                selectAdaptive.add(area.getBoolean("Adaptive"));
                selectDescription.add(area.getString("Description"));
                selectImage.add(area.getString("Image"));
                selectPhone.add(area.getString("Phone"));
                selectMobile.add(area.getString("Mobile"));
                selectAddress.add(area.getString("Address"));
                selectEmail.add(area.getString("Email"));
                selectSubsetId.add(area.getInt("SubsetId"));
                selectAreaId.add(area.getInt("AreaId"));

            }

            fdb.setMemberId_Product(selectMemberId);
            fdb.setName_Product(selectName);
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

        } catch (JSONException e) {

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

