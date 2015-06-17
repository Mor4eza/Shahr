package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Jobs_List;

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

    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    RecyclerView.Adapter job_list_Adapter;

    private static Context context;

    private static   String url_Business;

    private    Integer MEMberID;
    Integer Id[];

    Integer len=0;

    Integer End=0;

    //Url Bookmark
    public   void SetUrl_MemberId(Integer memberid)
    {
        this.MEMberID=memberid;
        url_Business="http://test.shahrma.com/api/ApiTakeBookmark?businessId=186&memberId="+memberid;
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
        }
        return null;


    }

    protected void onPostExecute() {
        try {

            //  Toast.makeText(context,market[0], Toast.LENGTH_LONG).show();
            DataBaseSqlite dbs = new DataBaseSqlite(context);


            dbs.delete_Business();
            for (int i = 0; i <len; i++)
            {
                dbs.Add_bookmark(Id[i], MEMberID);

            }

            if(len==0) {
                //  Toast.makeText(get, "فروشگاه ثبت نشده", Toast.LENGTH_LONG).show();
                Log.i("Count Business : ", "فروشگاه ثبت نشد");
            }
            else {
                Intent i = new Intent(this.context, Jobs_List.class);
                this.context.startActivity(i);
            }

        } catch (Exception e) {
            Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
        }
    }


    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            FieldClass fc=new FieldClass();

            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];

            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);

                Id[i]=area.getInt("Id");

            }

        } catch (JSONException e) {
            // Toast.makeText(getApplicationContext()," parse Json", Toast.LENGTH_LONG).show();
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
