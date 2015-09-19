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
 * Created by ariana2 on 9/22/2015...
 */
public class HTTPGetAroundMeProduct extends AsyncTask<String,Void,Integer>
{
    private static Context context;
    FieldDataBase fdb=new FieldDataBase();
    private String url_product="http://test.shahrma.com/api/ApiGiveNearProduct";
    Integer errorCode=0;

    List<Integer> selectId =new ArrayList<>();
    List<String>  selectName =new ArrayList<>();
    List<String>  selectPrice =new ArrayList<>();
    List<Double>  selectLat =new ArrayList<>();
    List<Double>  selectLongt =new ArrayList<>();
    List<Boolean> selectAdaptive =new ArrayList<>();
    List<String>  selectImage =new ArrayList<>();



    public  void setUrl_product(Double Lat,Double Longt,Double distance)
    {
        url_product="http://test.shahrma.com/api/ApiGiveNearProduct?latitude="+Lat+"&longitude="+Longt+"&distance="+distance;
        Log.i("url", url_product);
    }

    private String getUrl_product()
    {
        return  url_product;
    }
    Integer len;

    /**
     *
     * @param c
     */
    public HTTPGetAroundMeProduct(Context c) {
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
            InputStream jsonStream = getStreamFromURL(getUrl_product(), "GET");
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
        try
        {
            if(result==1)
            {

                Intent intent = new Intent("product-near");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
            else
            {

            }
        } catch (Exception e) {

        }
    }


    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            JSONArray areas = new JSONArray(JSONString);
            Log.i("JSONProduct", JSONString);
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);

                selectId.add(area.getInt("Id"));
                selectName.add(area.getString("Name"));
                selectPrice.add(area.getString("Price"));
                selectAdaptive.add(area.getBoolean("Adaptive"));
                selectImage.add(area.getString("Image"));
                selectLat.add(area.getDouble("Latitude"));
                selectLongt.add(area.getDouble("Longtude"));

            }

            fdb.setId_Product(selectId);
            fdb.setName_Product(selectName);
            fdb.setPrice_Product(selectPrice);
            fdb.setAdaptive__Product(selectAdaptive);
            fdb.setImage_Product(selectImage);
            fdb.setLongtiude_Product(selectLongt);
            fdb.setLatitude_Product(selectLat);

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
