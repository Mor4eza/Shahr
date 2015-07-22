package com.ariana.shahre_ma.WebServiceSend;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;

import java.io.IOException;

/**
 * Created by ariana on 7/1/2015.
 */
public class HTTPSendMemberURL extends AsyncTask<String,Void,Boolean>
{
    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... params)
    {
        try
        {
            HttpGet httpget=new HttpGet("URL");
            HttpClient httpclient=new DefaultHttpClient();
            HttpResponse response=httpclient.execute(httpget);

            int status=response.getStatusLine().getStatusCode();
            if(status==200)
            {
                HttpEntity en=response.getEntity();
                String data= EntityUtils.toString(en);
                return true;
            }
        }
        catch (IOException e){

        }

        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean)
    {
        super.onPostExecute(aBoolean);
    }
}
