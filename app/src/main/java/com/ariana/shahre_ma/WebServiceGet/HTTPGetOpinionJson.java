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
 * Created by ariana2 on 6/7/2015.
 */
public class HTTPGetOpinionJson extends AsyncTask<String, String, String>
{
    private static Context context;



    Integer Id[];
    String description[];
    String date[];
    String membername[];
    Integer erja[];
    Integer countlike[];
    Integer countdislike[];

    Integer len;
    Integer BusintessId;
    ProgressDialog pd;



    /**
     *
     * @param business_id
     */
    public void seturl_opinion(Integer business_id) {

        this.BusintessId=business_id;
    }

    /**
     *
     * @return
     */
    private String geturl_opinion()
    {

        String Result="";
        Result="http://test.shahrma.com/api/ApiGiveOpinion?businessId="+this.BusintessId;
        return  Result;
    }

    /**
     *
     * @param c
     */
    public HTTPGetOpinionJson(Context c) {
        context = c;

    }


    /**
     *
     * @param args
     * @return
     */
    protected String doInBackground(String... args) {
        try {


            InputStream jsonStream = getStreamFromURL(geturl_opinion(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            onPostExecute();
        } catch (Exception e) {

        }
        return null;


    }

    /**
     *
     */
    protected void onPostExecute() {
        try {

            DataBaseSqlite dbs = new DataBaseSqlite(context);


            dbs.delete_Opinion();
            for (int i = 0; i <len; i++)
            {
                dbs.Add_opinion(Id[i], description[i], date[i], erja[i], countlike[i], countdislike[i], membername[i]);
            }


            final Job_details_comment.PlaceholderFragment fragment=new Job_details_comment.PlaceholderFragment();
            fragment.mRecyclerView.post(new Runnable() {
                @Override
                public void run() {

                    Comment_Card_Adapter adapter = new Comment_Card_Adapter(context);
                    fragment.mRecyclerView.setAdapter(adapter);
                    fragment.mRecyclerView.scrollToPosition(adapter.getItemCount()-1);
                    fragment.Comment_adapter.notifyDataSetChanged();


                }
            });

        } catch (Exception e) {
            Toast.makeText(context.getApplicationContext(), "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
        }
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {
        try {
            Log.i("JSON",JSONString);
            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];
            description=new String[areas.length()];
            date=new String[areas.length()];
            erja=new Integer[areas.length()];
            countlike=new Integer[areas.length()];
            countdislike=new Integer[areas.length()];
            membername=new String[areas.length()];
            len=areas.length();

            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                date[i]=area.getString("Date");
                description[i] = area.getString("Description");
                countdislike[i]= area.getInt("DisLikeCount");
                erja[i]=area.getInt("Erja");
                Id[i]=area.getInt("Id");
                countlike[i]=area.getInt("LikeCount");
                membername[i]= area.getString("Member");
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
