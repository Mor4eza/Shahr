package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Settings.KeySettings;
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
 * Created by ariana on 7/1/2015.
 */
public class HTTPGetFieldActivityJson extends AsyncTask<String,Void,Integer>
{

    private String url1_get_FieldActivity= "http://test.shahrma.com/api/ApiGiveFieldActivity";
    ProgressDialog pd;
    Integer id[];
    String activity[];
    Context context;
    Integer len;
    DateTime dt=new DateTime();
    CalendarTool ct=new CalendarTool();

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

            Log.i("jsonFieldActivity",JSONString);

            JSONArray areas = new JSONArray(JSONString);
            id=new Integer[areas.length()];
            activity=new String[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                id[i]=area.getInt("Id");
                activity[i]=area.getString("Activity");

            }

        } catch (JSONException e) {
            Log.e("JSONException",e.toString());

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
      //  super.onPostExecute(o);
        try {
            KeySettings setting=new KeySettings(context);
            Log.e("Integer",String.valueOf(o));
            if (o == 1)
            {
                DataBaseSqlite db = new DataBaseSqlite(context);
                if (len > 0)
                {

                    db.delete_Update();
                    db.delete_FiledActivity();
                    for (int i = 0; i < len; i++)
                        db.Add_FieldActivity(id[i], activity[i]);

                    db.Add_Update(ct.getIranianDate());


                    UpdateActivity.PgUpdate.post(new Runnable() {
                        @Override
                        public void run() {
                            UpdateActivity.PgUpdate.setVisibility(View.INVISIBLE);
                        }
                    });

                    //end All update
                    setting.setUpdateAll(false);
                    //Get FieldActivity
                    setting.saveFieldActivity(true);

                }

                else
                {

                }

            }
            else
            {

            }
        }
        catch (Exception e)
        {

            Log.e("ExceptionFiledActivity",e.toString());
        }
    }


}
