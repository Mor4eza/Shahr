package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by ariana on 7/12/2015.
 */
public class HTTPSendLikeDisCount extends AsyncTask<String,Void,Boolean>
{
    ProgressDialog pd;
    String mesage;
    Context context;
    Boolean like;
    Integer discountid;
    Integer memberid;

    public HTTPSendLikeDisCount(Context context){this.context=context;}
    public void SetDiscountid(Integer discountid)
    {
        this.discountid=discountid;
    }

    public void SetMemberid(Integer memberid)
    {
        this.memberid=memberid;
    }

    public void SetLike(Boolean like)
    {
        this.like=like;
    }

    public String GetURL()
    {
        String url="http://test.shahrma.com/api/ApiTakeLike?erjaId="+discountid+"&erjaType=0&memberId="+memberid+"&value="+like;
        Log.i("URL", url);
        return url;

    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(context);
        pd.setMessage("ثبت...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {
        try
        {
            HttpGet httpget=new HttpGet(GetURL());
            HttpClient httpClient=new DefaultHttpClient();
            HttpResponse httpResponse=httpClient.execute(httpget);

            int status=httpResponse.getStatusLine().getStatusCode();
            if(status==200)
            {
                HttpEntity en=httpResponse.getEntity();
                String data= EntityUtils.toString(en);
                 mesage = data;
                return true;
            }


        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        DataBaseSqlite db=new DataBaseSqlite(context);
        if(aBoolean==true)
        {

            pd.dismiss();
        }
        else
        {
            pd.dismiss();
        }
    }
}
