package com.ariana.shahre_ma.WebServicePost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.Bazarche.Select_Image;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyBusiness.Add_New_Business;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

/**
 * Created by ariana on 7/1/2015.
 */
public class HTTPPostBusinessJson extends AsyncTask<String,Long,Integer>
{

    String url_Business="http://test.shahrma.com/api/ApiTakeoneBusiness";
    String jsonstring="";
    Integer resultmessage=0;
    Query query;
    FieldClass fc=new FieldClass();
    Context context;
    Integer errorCode=0;

    /**
     *
     * @param context
     */
    public HTTPPostBusinessJson(Context context)
    {
        this.context=context;
        query=new Query(context);

    }

    /**
     *
     * @param json
     */
    public void SetBusinessJson(String json)
    {
        jsonstring=json;

    }

    private String GetJson()
    {
        return jsonstring;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }
    /**
     *
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(String... params) {
        Integer jsonResult=0;
        try
        {
            JSONArray json=new JSONArray();//Json array
            HttpClient httpclient=new DefaultHttpClient();
            HttpContext httpcontext=new BasicHttpContext();
            HttpPost httppost=new HttpPost(url_Business);

            StringEntity se=new StringEntity(GetJson(),"UTF-8");

            httppost.setEntity(se);
            httppost.setHeader("Accept", "application/json");
            httppost.setHeader("Content-type", "application/json");

            HttpResponse response=httpclient.execute(httppost,httpcontext);
            HttpEntity entity=response.getEntity();

            String jsonstring= EntityUtils.toString(entity);

            resultmessage=Integer.parseInt(jsonstring);

            jsonResult=1;
        }
        catch (Exception e)
        {
            jsonResult=0;
        }
        return jsonResult;
    }

    /**
     *
     * @param integer
     */
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1)
        {

            ((Activity)context).finish();
            fc.SetType(1);
            Intent i=new Intent(context, Select_Image.class);
            context.startActivity(i);

            Log.i("resultmessage", String.valueOf(resultmessage));
            fc.SetBusiness_Id(resultmessage);
            Add_New_Business.save_edit.setProgress(100);
            Toast.makeText(context, "کسب و کار شما پس از تایید به نمایش گذاشته میشود!", Toast.LENGTH_LONG).show();
        }
        else
        {
            Add_New_Business.save_edit.setProgress(-1);
        }


    }

}

