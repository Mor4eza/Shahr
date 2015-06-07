package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.MemberSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.OpinionSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.SubsetSqlite;

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
    Integer opiniontype[];
    Integer erja[];
    Integer len;

    private String geturl_opinion()
    {
      //  MemberSqlite memSql=new MemberSqlite(context);
        String Result="";
        Result="http://test.shahrma.com/api/ApiGiveOpinion?businessId=186";
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

            OpinionSqlite opinSql = new OpinionSqlite(context);


            for (int i = 0; i <len; i++)
            {
                opinSql.Add(Id[i],description[i],date[i],opiniontype[i],erja[i]);

            }
        } catch (Exception e) {
            Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
        }
    }


    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];
            description=new String[areas.length()];
            date=new String[areas.length()];
            opiniontype=new Integer[areas.length()];
            erja=new Integer[areas.length()];
            len=areas.length();

            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                date[i]=area.getString("Date");
                description[i]=area.getString("Description");
                erja[i]=area.getInt("ErJa");
                Id[i]=area.getInt("Id");
                opiniontype[i]= area.getInt("OpinionType");


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
