package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Jobs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ariana on 6/15/2015.
 */
public class HTTPGetInterestJson  extends AsyncTask<String, String, String>
{
    ProgressDialog pd;

    private static Context context;
    public HTTPGetInterestJson(Context c) {
        context = c;
    }
    private static  String url_Interest;

    Jobs job=new Jobs();
    Integer subsetid[];
    Integer memberid[];
    Integer len;

    public void SetUrl_Interest(Integer memberid)
    {
        url_Interest ="http://test.shahrma.com/api/ApiGiveInterest?memberId="+memberid;
    }

    private String getUrl_Interest()
    {
        return url_Interest;
    }
    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("loading");
        pd.show();
    }

    /**
     *
     * @param args
     * @return
     */
    protected String doInBackground(String... args) {
        try {


            InputStream jsonStream = getStreamFromURL(getUrl_Interest(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            onPostExecute();
        } catch (Exception e) {
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
        }
        return null;


    }

    /**
     *
     */
    protected void onPostExecute() {
        try {

            DataBaseSqlite dbs = new DataBaseSqlite(context);

          // job.mSwipeRefreshLayout.setRefreshing(false);
            for (int i = 0; i <len; i++)
            {
                dbs.Add_Interest( subsetid[i], memberid[i]);

            }
            pd.dismiss();
        } catch (Exception e) {
           // Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
            Log.e("Exception",e.toString());
        }
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            JSONArray areas = new JSONArray(JSONString);


            subsetid=new Integer[areas.length()];
            memberid=new Integer[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);

                subsetid[i]=area.getInt("SubsetId");
                memberid[i]=area.getInt("MemberId");


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



