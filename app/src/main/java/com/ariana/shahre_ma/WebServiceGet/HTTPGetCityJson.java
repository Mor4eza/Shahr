package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;

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
public class HTTPGetCityJson extends AsyncTask<String, String, String>
{

    private String url_get_Proviced = "http://test.shahrma.com/api/ApiGivecity";

    Integer Id_city[];
    String  Name_city[];
    Integer PROVINCEID_city[];
    Integer len;

    private static Context context;


    public HTTPGetCityJson(Context c) {
        context = c;
    }

    protected String doInBackground(String... args) {
        try {


            InputStream jsonStream = getStreamFromURL(url_get_Proviced, "GET");
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
                dbs.Add_city(Id_city[i],Name_city[i],PROVINCEID_city[i]);

            }
        } catch (Exception e) {
            Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
        }
    }
    protected void onPostExecute(String file_url) {
        try {

        } catch (Exception e) {
        }
    }


    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

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
