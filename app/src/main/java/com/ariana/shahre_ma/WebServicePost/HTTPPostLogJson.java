package com.ariana.shahre_ma.WebServicePost;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Entity;
import android.os.AsyncTask;

import com.ariana.shahre_ma.Fields.FieldClass;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ariana on 8/1/2015.
 */
public class HTTPPostLogJson extends AsyncTask<String,Void,Integer>
{

    String urlDisCount="http://test.shahrma.com/api/ApiTakeDisCount";

    String mesage;
    String jsonLog;
    String urlLog="";
    Context context;
    Integer errorCode=0;
    public HTTPPostLogJson(Context context)
    {
        this.context=context;
    }

    public void SetDLogJson(String json)
    {
        jsonLog = json;

    }

    private String GetJson()
    {
        return jsonLog;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {

        Integer result=0;
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpContext httpContext=new BasicHttpContext();
            HttpPost httpPost=new HttpPost(urlLog);
            StringEntity se=new StringEntity(jsonLog,"UTF-8");

            httpPost.setEntity(se);
            httpPost.setHeader("", "");
            httpPost.setHeader("", "");

            HttpResponse httpResponse=httpClient.execute(httpPost,httpContext);
            HttpEntity entity=httpResponse.getEntity();
            InputStream webs=entity.getContent();
            try
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(webs,"UTF-8"),8);
                mesage=(reader.readLine());
                webs.close();
                result=1;
            }
            catch (Exception e)
            {
                result=0;
            }

        }
        catch (Exception e)
        {
            result=0;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
    }
}
