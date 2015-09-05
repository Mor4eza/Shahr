package com.ariana.shahre_ma.WebServiceSend;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

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
 * Created by ariana on 6/16/2015.
 */
public class HTTPSendRateURL extends AsyncTask<String, Void, Boolean> {

    ProgressDialog pd;
    Integer errorCode=0;

    Integer businessid;
    Integer memberid;

    Double rate;
    Context context;

    public HTTPSendRateURL(Context context)
    {
        this.context=context;
    }


    public void SetBusinessId(Integer opinionid)
    {
        this.businessid=opinionid;
    }

    public void SetMemberId(Integer memberid)
    {
        this.memberid=memberid;
    }

    public void SetRate(Double rate)
    {
        this.rate=rate;
    }

    public String GetURL()
    {
        String url="http://test.shahrma.com/api/ApiTakeRate?businessId="+businessid+"&memberId="+memberid+"&rate="+rate;
        return url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("ارسال...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected Boolean doInBackground(String... urls) {
        try {

            //------------------>>
            HttpGet httppost = new HttpGet(GetURL());
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);


                JSONObject jsono = new JSONObject(data);
                Log.i("", String.valueOf(jsono));

                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return false;
    }

    protected void onPostExecute(Boolean result) {


        if (result == true) {

            pd.dismiss();
        } else {
            pd.dismiss();
        }
    }


}
