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
 * Created by ariana2 on 6/22/2015.
 */
public class HTTPGetNotificationJson extends AsyncTask<String,String,String> {

    public static String test;
    private static Context context;
    private static   String url_Notification;
    private Integer MEMberID;
    Integer Id[];
    Integer OpinionType[];
    Integer erja[];
    Boolean ExecutionTime[];
    String  Description[];
    String  ExpirationDate[];
    String  City[];
    Integer CityId[];
    String  Subset[];
    Integer SubsetId[];
    Integer len=0;

    /**
     *
     * @param memberid
     */
    public   void SetUrl_MemberId(Integer memberid)
    {
        this.MEMberID=memberid;
        url_Notification="http://test.shahrma.com/api/ApiGiveNotification?memberId="+memberid;
        Log.i("URLurl", url_Notification);
    }

    /**
     *
     * @return
     */
    private String GetUrl_business()
    {
        return  url_Notification;
    }

    /**
     *
     * @param c
     */
    public HTTPGetNotificationJson(Context c) {
        context = c;
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected String doInBackground(String... params) {
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

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {


        try {



            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];
            OpinionType=new Integer[areas.length()];
            erja =new Integer[areas.length()];
            ExecutionTime = new Boolean[areas.length()];
            Description = new String[areas.length()];
            ExpirationDate = new String[areas.length()];
            City=new String[areas.length()];
            CityId=new Integer[areas.length()];
            Subset=new String[areas.length()];
            SubsetId=new Integer[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {
                JSONObject area = areas.getJSONObject(i);
                Id[i]=area.getInt("Id");
                OpinionType[i]=area.getInt("OpinionType");
                erja[i]=area.getInt("ErJa");
                ExecutionTime[i]=area.getBoolean("ExecutionTime");
                Description[i]=area.getString("Description");
                ExpirationDate[i]=area.getString("ExpirationDate");
                City[i]=area.getString("City");
                CityId[i]=area.getInt("CityId");
                Subset[i]=area.getString("Subset");
                SubsetId[i]=area.getInt("SubsetId");
                test=area.getString("Description");
        }

        } catch (JSONException e) {
            // Toast.makeText(getApplicationContext()," parse Json", Toast.LENGTH_LONG).show();
            Log.i("JSONException",e.toString());
        }
    }


    /**
     *
     */
    protected void onPostExecute() {
        try {

            //  Toast.makeText(context,market[0], Toast.LENGTH_LONG).show();
            DataBaseSqlite dbs = new DataBaseSqlite(context);



            for (int i = 0; i <len; i++)
            {
                dbs.Add_Notification(Id[i], OpinionType[i],erja[i],ExecutionTime[i],Description[i],ExpirationDate[i],City[i],CityId[i],Subset[i],SubsetId[i]);

            }



        } catch (Exception e) {
            Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
            Log.i("Exception", e.toString());
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
