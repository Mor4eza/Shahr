package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Settings.KeySettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ariana on 8/17/2015.
 */
public class HTTPGetBusinessImageJson extends AsyncTask<String,Void,Integer>
{

    private static Context context;
    private String url_businessimage="http://test.shahrma.com/api/ApiGiveBusinessImage?BusinessId=";

    Integer Id[];
    String src[];
    Integer businessId[];
    Integer len;

    FieldClass fc=new FieldClass();

    public void SetBusinessId(Integer BusinessId)
    {
        url_businessimage=url_businessimage+BusinessId;
    }

    private String GetURL()
    {
        Log.i("ImageUrl",url_businessimage);
        return  url_businessimage;
    }
    /**
     *
     * @param c
     */
    public HTTPGetBusinessImageJson(Context c) {
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
            InputStream jsonStream = getStreamFromURL(GetURL(), "GET");
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
        if(result==1) {
            try {
                KeySettings setting=new KeySettings(context);
                if(len>0) {

                    DataBaseSqlite dbs = new DataBaseSqlite(context);


                    for (int i = 0; i < len; i++)
                    {
                        dbs.delete_BusinessImage(Id[i]);
                        dbs.Add_BusinessImage(Id[i], businessId[i],src[i]);

                    }

                    Log.i("Productproperty",String.valueOf(fc.GetProductReceiver()));
                    if(fc.GetProductReceiver())
                    {
                        Intent intent = new Intent("Product_property");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                    else
                    {
                        Intent intent = new Intent("BusinessImage");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                }


            } catch (Exception e)
            {
            }
        }
        else
        {

        }
    }


    void parseJSON(String JSONString) {
        try {

            JSONArray areas = new JSONArray(JSONString);
            Log.i("JSONBusinessImage", JSONString);
            Id=new Integer[areas.length()];
            src=new String[areas.length()];
            businessId=new Integer[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                businessId[i]=area.getInt("BusinessId");
                Id[i]=area.getInt("Id");
                src[i]=area.getString("Src");


            }

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
