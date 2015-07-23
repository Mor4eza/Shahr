package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Settings.UpdateActivity;

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
     */
    @Override
    protected void onPreExecute() {
        //super.onPreExecute();
     /*   pd = new ProgressDialog(context);
        pd.setMessage("در حال بروزرسانی...");
        pd.setCancelable(false);
        pd.show();*/
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
//        onPostExecute(result);
        if(result==1) {
            try {

                DataBaseSqlite dbs = new DataBaseSqlite(context);

                dbs.delete_City();
                for (int i = 0; i < len; i++) {
                    dbs.Add_city(Id_city[i], Name_city[i], PROVINCEID_city[i]);

                }
                UpdateActivity.PgUpdate.post(new Runnable() {
                    @Override
                    public void run() {
                        UpdateActivity.PgUpdate.setVisibility(View.INVISIBLE);
                    }
                });

            }
            catch (Exception e)
            {
                //pd.dismiss();
              //  Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            pd.dismiss();
        }
    }




    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {

        Integer ii = 0;
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
