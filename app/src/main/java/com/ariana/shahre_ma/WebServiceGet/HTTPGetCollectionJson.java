package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
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
 * Created by ariana2 on 6/6/2015.
 */
public class HTTPGetCollectionJson extends AsyncTask<String,Void, Integer> {

        ProgressDialog pd;


        private static Context context;

        public HTTPGetCollectionJson(Context c) {
        context = c;


    }
        private static final String url_collection="http://test.shahrma.com/api/apigivecollection";

        Integer Id[];
        String collectionname[];
        Integer len;

    /**
     *
     * @param args
     * @return
     */
    protected Integer doInBackground(String... args) {
        Integer result=0;
        try {



            InputStream jsonStream = getStreamFromURL(url_collection, "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            result=1;
        } catch (Exception e) {
            result=0;
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
        }
        return result;


    }

    /**
     *
     */
    @Override
    protected void onPostExecute(Integer result)
    {
       // onPostExecute(result);
        KeySettings setting=new KeySettings(context);
        Log.i("result",String.valueOf(result));
        if (result==1) {
            try {
                if(len>0) {
                    DataBaseSqlite db = new DataBaseSqlite(context);
                    db.delete_Collection();

                    for (int i = 0; i < len; i++) {
                        db.Add_collection(Id[i], collectionname[i]);

                    }
                }

                setting.saveCollection(true);
                Intent intent = new Intent("Collection");
                LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

                if(setting.getAllUpdate())
                {
                    HTTPGetSubsetJson httpGetSubsetJson = new HTTPGetSubsetJson(context);
                    httpGetSubsetJson.execute();
                }


            } catch (Exception e) {

            }
        }
        else
        {
            //pd.dismiss();
        }
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            JSONArray areas = new JSONArray(JSONString);
            Log.i("JSONCollection",JSONString);
            Id=new Integer[areas.length()];
            collectionname=new String[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                Id[i]=area.getInt("Id");
                collectionname[i]=area.getString("Name");


            }

        } catch (JSONException e) {
            // Toast.makeText(getApplicationContext()," parse Json", Toast.LENGTH_LONG).show();
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
