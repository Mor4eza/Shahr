package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
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
 * Created by ariana on 6/15/2015.
 */
public class HTTPGETLikeJson  extends AsyncTask<String, String, String>
{
    ProgressDialog pd;

    private static Context context;

    private static final String url_Like="http://test.shahrma.com/api/apigivecollection";

    Integer Id[];
    Boolean like[];
    Integer opinionid[];
    Integer memberid[];
    Integer len;


    /**
     *
     * @param c
     */
    public HTTPGETLikeJson(Context c) {
        context = c;
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


            InputStream jsonStream = getStreamFromURL(url_Like, "GET");
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


            for (int i = 0; i <len; i++)
            {
                dbs.Add_Like(Id[i],like[i], memberid[i], opinionid[i]);

            }
            pd.dismiss();
        } catch (Exception e) {
            Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
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

            Id=new Integer[areas.length()];
            like=new Boolean[areas.length()];
            memberid=new Integer[areas.length()];
            opinionid=new Integer[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                Id[i]=area.getInt("Id");
                like[i]=area.getBoolean("Id");
                opinionid[i]=area.getInt("Id");
                memberid[i]=area.getInt("Id");


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
