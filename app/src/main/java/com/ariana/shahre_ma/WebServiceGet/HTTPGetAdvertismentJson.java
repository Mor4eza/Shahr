package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
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
 * Created by ariana on 8/1/2015.
 */
public class HTTPGetAdvertismentJson extends AsyncTask<String,Void,Integer>
{

    private static Context context;

    private String url_advertisment="";

    Integer Id[];
    String image[];
    String link[];
    Integer len;

    /**
     *
     * @param c
     */
    public HTTPGetAdvertismentJson(Context c) {
        context = c;
    }

    public void SetAdvertisment(Integer cityid)
    {
         url_advertisment="http://test.shahrma.com/api/ApiGiveAdvertisment?cityId="+cityid;
    }

    private String getUrl_advertisment()
    {
        return url_advertisment;
    }

    /**
     *
     * @param args
     * @return
     */
    protected Integer doInBackground(String... args) {
        Integer result=0;
        try {


            InputStream jsonStream = getStreamFromURL(getUrl_advertisment(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            result=1;
        } catch (Exception e) {
            result=0;
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
        }
        return result;


    }

    @Override
    protected void onPostExecute(Integer result) {
//        onPostExecute(result);
        if(result==1) {
            try {

                if(len>0) {

                    DataBaseSqlite dbs = new DataBaseSqlite(context);
                    dbs.delete_Advertisment();

                    for (int i = 0; i < len; i++) {
                        dbs.Add_Advertisment(Id[i], image[i], link[i]);

                    }
                }

            } catch (Exception e) {
                //Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
            }
        }
        else
        {

        }
    }


    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            JSONArray areas = new JSONArray(JSONString);
            Log.i("JSONsubset", JSONString);
            Id=new Integer[areas.length()];
            image=new String[areas.length()];
            link=new String[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                image[i] = area.getString("Image");
                Id[i]=area.getInt("Id");
                link[i]=area.getString("Link");


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
