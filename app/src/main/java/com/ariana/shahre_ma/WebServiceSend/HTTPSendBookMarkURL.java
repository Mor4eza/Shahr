package com.ariana.shahre_ma.WebServiceSend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ariana2 on 6/17/2015.
 */
public class HTTPSendBookMarkURL extends AsyncTask<String, Void, Boolean> {

    Integer errorCode=0;
    Integer businessid;
    Integer memberid;

    Context context;

    /**
     *
     * @param context
     */
    public HTTPSendBookMarkURL(Context context)
    {
        this.context=context;
    }

    /**
     *
     * @param businessid
     */
    public void SetBusinessid(Integer businessid)
    {
        this.businessid=businessid;
    }

    /**
     *
     * @param memberid
     */
    public void SetMemberid(Integer memberid)
    {
        this.memberid=memberid;
    }


    /**
     *
     * @return
     */
    public String GetURL()
    {
        String url="";
        url="http://test.shahrma.com/api/ApiTakeBookmark?businessId="+businessid+"&memberId="+memberid;
        Log.i("URLurl", url);
        return url;
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
     * @param urls
     * @return
     */
    @Override
    protected Boolean doInBackground(String... urls) {
        try {

            //------------------>>
            HttpGet httpGet = new HttpGet(GetURL());
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpGet);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);

                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     *
     * @param result
     */
    protected void onPostExecute(Boolean result) {
        DataBaseSqlite dbs = new DataBaseSqlite(context);

        if(result==true) {

                dbs.Add_bookmark(businessid, memberid);
        }
        else {

        }

    }

}
