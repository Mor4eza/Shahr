package com.ariana.shahre_ma.WebServicePost;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by ariana on 7/29/2015.
 */
public class HTTPPostUploadImage extends AsyncTask<String,Void,Integer>
{

    String urlDisCount="http://test.shahrma.com/api/ApiTakeImage";
    FieldClass fc=new FieldClass();
    ProgressDialog pd;
    Context context;
    String mesage;
    String jsonstring;


    public HTTPPostUploadImage(Context context)
    {
        this.context=context;
    }

    /**
     *
     * @param json
     */
    public void SetDisCountJson(String json)
    {
        jsonstring = json;

    }

    private String GetJson()
    {
        return jsonstring;
    }


    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("Saveing...");
        pd.setCancelable(false);
        pd.show();
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(String... params) {
        Integer result=0;
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpContext httpContext=new BasicHttpContext();
            HttpPost httpPost=new HttpPost(urlDisCount);
            StringEntity se=new StringEntity(GetJson(),"UTF-8");

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

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
                Log.e("Error in conversion", e.toString());
            }
        }
        catch (Exception e)
        {
            result=0;
        }
        return result;
    }



    /**
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if(result==1)
        {

        }
        else
        {


        }
    }
}
