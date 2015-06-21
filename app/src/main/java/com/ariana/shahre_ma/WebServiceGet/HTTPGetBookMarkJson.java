package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
 * Created by ariana2 on 6/17/2015.
 */
public class HTTPGetBookMarkJson
        extends AsyncTask<String, String, String>
{


    private static Context context;

    private static   String url_Business;

    private    Integer MEMberID;
    Integer Id[];

    Integer len=0;



    //Url Bookmark
    public   void SetUrl_MemberId(Integer memberid)
    {
        this.MEMberID=memberid;
        url_Business="http://test.shahrma.com/api/ApiGiveBookmark?6&memberId="+memberid;
        Log.i("URLurl",url_Business);
    }

    private String GetUrl_business()
    {
        return  url_Business;
    }

    public HTTPGetBookMarkJson(Context c) {
        context = c;
    }
    protected String doInBackground(String... args) {
        try {


            InputStream jsonStream = getStreamFromURL(GetUrl_business(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            onPostExecute();
        } catch (Exception e) {
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
            Log.i("Exception",e.toString());
        }
        return null;


    }

    protected void onPostExecute() {
        try {

            //  Toast.makeText(context,market[0], Toast.LENGTH_LONG).show();
            DataBaseSqlite dbs = new DataBaseSqlite(context);


            dbs.delete_bookmark();
            for (int i = 0; i <len; i++)
            {
                dbs.Add_bookmark(Id[i], MEMberID);

            }



        } catch (Exception e) {
            Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
            Log.i("Exception", e.toString());
        }
    }


    void parseJSON(String JSONString) {


        try {



            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];

            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);

                Id[i]=area.getInt("Id");

            }

        } catch (JSONException e) {
            // Toast.makeText(getApplicationContext()," parse Json", Toast.LENGTH_LONG).show();
            Log.i("JSONException",e.toString());
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
    ///
}
