package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.DateBaseSqlite.AddDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
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
 * Created by ariana2 on 6/8/2015.
 */
public class HTTPGetCityJson extends AsyncTask<String,Void,Integer>
{

    private String url_get_Proviced = "http://test.shahrma.com/api/ApiGivecity";
    ProgressDialog pd;
    Integer Id_city[];
    String  Name_city[];
    Integer PROVINCEID_city[];
    Integer len;
    Integer errorCode=0;
    private static Context context;

    /**
     *
     * @param c
     */
    public HTTPGetCityJson(Context c) {
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


            InputStream jsonStream = getStreamFromURL(url_get_Proviced, "GET");
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
     * @param result
     */
    @Override
    protected void onPostExecute(Integer result) {
        KeySettings setting=new KeySettings(context);
        try {

            Log.i("onpostCity","onpostCity");
            if(result==1)
            {
                AddDataBaseSqlite adb = new AddDataBaseSqlite(context);
                DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);
                    if (len > 0)
                    {

                        ddb.delete_City();
                        for (int i = 0; i < len; i++)
                        {
                            adb.Add_city(Id_city[i], Name_city[i], PROVINCEID_city[i]);

                        }


                        if (setting.getAllUpdate())
                        {
                            Log.i("getCity","len>0");
                            HTTPGetAreaJosn httpGetAreaJosn = new HTTPGetAreaJosn(context);
                            httpGetAreaJosn.execute();

                        }

                    }
                  else
                    {
                        if (setting.getAllUpdate())
                        {
                            Log.i("getCity","len<0");
                            Intent intent = new Intent("GetCity");
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }


                }
                else
                {

                    if (setting.getAllUpdate())
                    {
                        Log.i("getCity","else");
                        Intent intent = new Intent("GetCity");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                }
        }
        catch (Exception e)
        {
            if (setting.getAllUpdate())
            {
                Log.i("getCity","exception");
                Intent intent = new Intent("GetCity");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
            }
        }
    }




    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {
        try {

            Log.i("JSONcity",JSONString);
            JSONArray areas = new JSONArray(JSONString);

            Id_city=new Integer[areas.length()];
            Name_city=new String[areas.length()];
            PROVINCEID_city=new Integer[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                Id_city[i]=area.getInt("Id");
                Name_city[i]=area.getString("Name");
                PROVINCEID_city[i]=area.getInt("ProvinceId");

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
