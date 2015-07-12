package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.Cards.Comment_Card_Adapter;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.job_details.Job_details_comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ariana on 7/12/2015.
 */
public class HTTPGetDisCountJson extends AsyncTask<String,Void,Integer> {
    private static Context context;



    Integer Id[];
    String description[];
    String startdate[];
    String image[];
    String text[];
    String percent[];
    String expirationdate[];
    Integer businessid[];

    Integer len;
    Integer BusintessId;
    ProgressDialog pd;

    FieldClass fc=new FieldClass();

    /**
     *
     * @param business_id
     */
    public void seturl_DisCount(Integer business_id) {

        this.BusintessId=business_id;
    }

    /**
     *
     * @return
     */
    private String geturl_discount()
    {

        String Result="";
        Result="";
        return  Result;
    }

    /**
     *
     * @param c
     */
    public HTTPGetDisCountJson(Context c) {
        context = c;

    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
       super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("دریافت...");
        pd.setCancelable(false);
        pd.show();
    }

    /**
     *
     * @param args
     * @return
     */
    protected Integer doInBackground(String... args) {
        Integer result=0;
        try {


            InputStream jsonStream = getStreamFromURL(geturl_discount(), "GET");
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
     */
    @Override
    protected void onPostExecute(Integer result) {
        if(result==12) {
            try {

                DataBaseSqlite dbs = new DataBaseSqlite(context);


                dbs.delete_Opinion();
                for (int i = 0; i < len; i++) {
                    dbs.Add_DisCount(Id[i], text[i], image[i], startdate[i], expirationdate[i], description[i], percent[i], businessid[i]);
                }
                pd.dismiss();

            } catch (Exception e) {
                pd.dismiss();
                Toast.makeText(context.getApplicationContext(), "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
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


        try {

            Log.i("JSON", JSONString);
            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];
            text=new String[areas.length()];
            image=new String[areas.length()];
            startdate=new String[areas.length()];
            expirationdate=new String[areas.length()];
            description=new String[areas.length()];
            percent=new String[areas.length()];
            businessid=new Integer[areas.length()];
            len=areas.length();

            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);




            /*    date[i]=area.getString("Date");
                description[i] = area.getString("Description");
                countdislike[i]= area.getInt("DisLikeCount");
                erja[i]=area.getInt("Erja");
                Id[i]=area.getInt("Id");
                countlike[i]=area.getInt("LikeCount");
                membername[i]= area.getString("Member");*/


            }

        } catch (JSONException e) {
            // Toast.makeText(activity,e.toString(), Toast.LENGTH_LONG).show();
            Log.i("JSONException",e.toString());
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
