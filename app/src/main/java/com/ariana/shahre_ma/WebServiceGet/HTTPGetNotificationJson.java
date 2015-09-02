package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import com.ariana.shahre_ma.Date.DateTime;
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
 * Created by ariana2 on 6/22/2015.
 */
public class HTTPGetNotificationJson extends AsyncTask<String,Void,Integer> {

    public static String test;
    private static Context context;
    private static String url_Notification;
    private Integer MEMberID;
    Integer errorCode=0;
    KeySettings setting;
    Integer Id[];
    Integer OpinionType[];
    Integer erja[];
    Boolean ExecutionTime[];
    String Description[];
    String ExpirationDate[];
    String City[];
    String title[];
    Integer CityId[];
    String Subset[];
    Integer SubsetId[];
    Integer len = 0;

    /**
     * @param memberid
     */
    public void SetUrl_MemberId(Integer memberid) {
        this.MEMberID = memberid;
        url_Notification = "http://test.shahrma.com/api/ApiGiveNotification?memberId=" + memberid;
        Log.i("URLurl", url_Notification);
    }

    /**
     * @return
     */
    private String GetUrl_business() {
        return url_Notification;
    }

    /**
     * @param c
     */
    public HTTPGetNotificationJson(Context c) {
        context = c;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();


    }
    /**
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(String... params) {
        Integer result =0;
        try {


            InputStream jsonStream = getStreamFromURL(GetUrl_business(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            result = 1;

        } catch (Exception e) {
            result = 0;

            Log.i("ExceptiondNotification", e.toString());
        }
        return result;
    }

    /**
     * @param JSONString
     */
    void parseJSON(String JSONString) {
        try {

            Log.i("JsonNotification", JSONString);

            JSONArray areas = new JSONArray(JSONString);

            Id = new Integer[areas.length()];
            OpinionType = new Integer[areas.length()];
            erja = new Integer[areas.length()];
            ExecutionTime = new Boolean[areas.length()];
            Description = new String[areas.length()];
            ExpirationDate = new String[areas.length()];
            City = new String[areas.length()];
            CityId = new Integer[areas.length()];
            Subset = new String[areas.length()];
            SubsetId = new Integer[areas.length()];
            title=new String[areas.length()];
            len = areas.length();

            for (int i = 0; i < areas.length(); i++) {
                JSONObject area = areas.getJSONObject(i);
                Id[i] = area.getInt("Id");
                OpinionType[i] = area.getInt("OpinionType");
                erja[i] = area.getInt("ErJa");
                ExecutionTime[i] = area.getBoolean("ExecutionTime");
                Description[i] = area.getString("Description");
                ExpirationDate[i] = area.getString("ExpirationDate");
                City[i] = area.getString("City");
                CityId[i] = area.getInt("CityId");
                Subset[i] = area.getString("Subset");
                SubsetId[i] = area.getInt("SubsetId");
                title[i]=area.getString("Title");
                test = area.getString("Description");
            }

        } catch (JSONException e) {
            Log.i("JSONException", e.toString());
        }
    }


    /**
     *
     */
    @Override
    protected void onPostExecute(Integer result) {

        setting=new KeySettings(context);
        Log.i("result", String.valueOf(result));
        if (result == 1){
            try {
               DataBaseSqlite db = new DataBaseSqlite(context);
                db.delete_Notification();
                if (len > 0) {
                    for (int i = 0; i < len; i++)
                    {
                          db.Add_Notification(Id[i], OpinionType[i], erja[i], ExecutionTime[i], Description[i], ExpirationDate[i], City[i], CityId[i], Subset[i], SubsetId[i],title[i]);
                    }


                } else
                {
                    Log.i("else", "else");
                }

            } catch (Exception e) {

                Log.i("ExceptionOnpostexcut", e.toString());
            }

        }
        else
        {

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
            errorCode=huc.getResponseCode();
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
