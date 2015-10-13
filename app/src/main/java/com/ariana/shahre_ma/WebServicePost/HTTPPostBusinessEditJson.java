package com.ariana.shahre_ma.WebServicePost;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.AddDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyBusiness.Edit_business;

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
 * Created by ariana on 7/5/2015.
 */
public class HTTPPostBusinessEditJson extends AsyncTask<String,Long,Integer>
{
    String url_Business="http://test.shahrma.com/api/ApiUpdateBusiness";
    String jsonstring="";
    Query query;
    FieldClass fc=new FieldClass();
    Context context;
    Integer errorCode=0;


    /**
     *
     * @param context
     */
    public HTTPPostBusinessEditJson(Context context)
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

            jsonResult=Integer.parseInt(jsonstring);

        }
        catch (Exception e){

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
            AddDataBaseSqlite adb = new AddDataBaseSqlite(context);
            DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);
            ddb.delete_BusinessId(fc.GetBusiness_Id());
            ((Activity)context).finish();
            Toast.makeText(context, "تغییرات شما پس از تایید اعمال میشود!", Toast.LENGTH_LONG).show();
            Edit_business.save_edit.setProgress(100);
        }
        else
        {
            Edit_business.save_edit.setProgress(-1);
            Toast.makeText(context, "تغییرات ثبت نشد. دوباره امتحان کنید", Toast.LENGTH_LONG).show();
        }
    }
}
