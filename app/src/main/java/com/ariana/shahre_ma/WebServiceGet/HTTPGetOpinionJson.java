package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ariana2 on 6/7/2015.
 */
public class HTTPGetOpinionJson extends AsyncTask<String, String, String>
{
    private static Context context;



    Integer Id[];
    String description[];
    String date[];
    String membername[];
    Integer erja[];
    Integer countlike[];
    Integer countdislike[];
    Integer len;
    Integer BusintessId;


    FieldClass fc=new FieldClass();
    public void seturl_opinion(Integer business_id) {

        this.BusintessId=business_id;
    }

    private String geturl_opinion()
    {

        String Result="";
        Result="http://test.shahrma.com/api/ApiGiveOpinion?businessId="+this.BusintessId;
        return  Result;
    }

    public HTTPGetOpinionJson(Context c) {
        context = c;
    }
    protected String doInBackground(String... args) {
        try {


            InputStream jsonStream = getStreamFromURL(geturl_opinion(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            onPostExecute();
        } catch (Exception e) {
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
        }
        return null;


    }


    protected void onPostExecute() {
        try {

            DataBaseSqlite dbs = new DataBaseSqlite(context);


            for (int i = 0; i <len; i++)
            {
                dbs.Add_opinion(Id[i],description[i],date[i],erja[i],countlike[i],countdislike[i],membername[i]);

            }
        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
        }
    }


    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            Log.i("JSON",JSONString);
            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];
            description=new String[areas.length()];
            date=new String[areas.length()];
            erja=new Integer[areas.length()];
            countlike=new Integer[areas.length()];
            countdislike=new Integer[areas.length()];
            membername=new String[areas.length()];
            len=areas.length();

            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);


                date[i]=area.getString("Date");
                description[i] = area.getString("Description");
                countdislike[i]= area.getInt("DisLikeCount");
                countlike[i]=area.getInt("LikeCount");
                erja[i]=area.getInt("ErJa");
                Id[i]=area.getInt("Id");
                membername[i]= area.getString("Member");







         
            }

        } catch (JSONException e) {
            // Toast.makeText(getApplicationContext()," parse Json", Toast.LENGTH_LONG).show();
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
