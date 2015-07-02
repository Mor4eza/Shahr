package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;

/**
 * Created by ariana on 7/1/2015.
 */
public class HTTPGetFieldActivityJson extends AsyncTask<String,Void,Integer>
{

    private String url1_get_FieldActivity= "http://test.shahrma.com/api/ApiGiveFieldActivity";

    Integer id[];
    String activity[];
    Context context;
    Integer len;

    String mesage="";

    /**
     *
     * @param context
     */
    public HTTPGetFieldActivityJson(Context context)
    {
        this.context=context;
    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(String... params) {
        Integer result=0;
        try {


            InputStream jsonStream = getStreamFromURL(url1_get_FieldActivity, "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);

            result=1;
        } catch (Exception e) {
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
            result=0;
        }
        return result;
    }

    /**
     *
     * @param values
     */
    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            JSONArray areas = new JSONArray(JSONString);
            id=new Integer[areas.length()];
            activity=new String[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                id[i]=area.getInt("Id");
                Log.i("Id",String.valueOf(id[i]));
                activity[i]=area.getString("Activity");
                Log.i("Activity",activity[i]);

            }

        } catch (JSONException e) {
            Log.e("JSONException",e.toString());
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

    /**
     *
     * @param o
     */
    @Override
    protected void onPostExecute(Integer o) {
        super.onPostExecute(o);
        try {
            Log.e("Integer",String.valueOf(o));
            if (o == 1) {
                if (len > 0) {
                    DataBaseSqlite db = new DataBaseSqlite(context);
                    for (int i = 0; i < len; i++)
                        db.Add_FieldActivity(id[i], activity[i]);

                } else {

                }
            } else {

            }
        }
        catch (Exception e){
            Log.e("ExceptionFiledActivity",e.toString());
        }
    }


}
