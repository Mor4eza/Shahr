package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
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
 * Created by ariana on 7/2/2015.
 */
public class HTTPGetAreaJosn extends AsyncTask<String,Void,Integer>

{
    Query query;
    FieldClass fc=new FieldClass();
    Context context;
    Integer Id_area[];
    Integer CityId_area[];
    String Name_Area[];
    Integer len_Area=0;
    ProgressDialog pd;
    private String url1_get_Area = "http://test.shahrma.com/api/apigivearea";


    public HTTPGetAreaJosn(Context context)
    {
        this.context=context;
        query=new Query(context);
    }

        protected Integer  doInBackground(String... args) {
            Integer result=0;
            try {


                InputStream jsonStream = getStreamFromURL(url1_get_Area, "GET");
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
     * @param result
     */
        @Override
        protected void onPostExecute(Integer result) {

            KeySettings setting=new KeySettings(context);
            DataBaseSqlite db = new DataBaseSqlite(context);
            try
            {
                if(result==1)
                {
                if (len_Area > 0)
                {
                        db.delete_Area();
                        for (int i = 0; i < len_Area; i++)
                        {
                            db.Add_area(Id_area[i], Name_Area[i], CityId_area[i]);
                        }


                        setting.saveArea(true);


                        if(setting.getAllUpdate())
                        {
                                HTTPGetSubsetJson httpGetSubsetJson = new HTTPGetSubsetJson(context);
                                httpGetSubsetJson.execute();
                        }
                    }
                    else
                    {
                        if (setting.getAllUpdate())
                        {
                            Intent intent = new Intent("GetCity");
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                    }
                }
                else
                {
                    if (setting.getAllUpdate())
                    {
                        Intent intent = new Intent("GetCity");
                        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                    }
                }
            }
            catch (Exception e)
            {
                if (setting.getAllUpdate())
                {
                    Intent intent = new Intent("GetCity");
                    LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                }
            }
        }


        void parseJSON(String JSONString) {
            try {
                Log.i("jsonArea", JSONString);
                JSONArray areas = new JSONArray(JSONString);

                Id_area=new Integer[areas.length()];
                CityId_area=new Integer[areas.length()];
                Name_Area=new String[areas.length()];
                len_Area=areas.length();
                for (int i = 0; i < areas.length(); i++) {

                    JSONObject area = areas.getJSONObject(i);

                    Name_Area[i]=area.getString("AreaName");
                    CityId_area[i]=area.getInt("CityId");
                    Id_area[i]=area.getInt("Id");

                }

            } catch (JSONException e) {
                //  Toast.makeText(getApplicationContext()," parse Json", Toast.LENGTH_LONG).show();
            }
        }



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
