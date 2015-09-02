package com.ariana.shahre_ma.WebServiceSend;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.AddDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by ariana on 9/4/2015.
 */
public class HTTPDeleteBookMarkURL extends AsyncTask<String, Void, Boolean> {

    String url = "";
    Integer BusinessId = 0;
    Integer errorCode = 0;
    Context context;
    FieldClass fc = new FieldClass();

    /**
     * @param context
     */
    public HTTPDeleteBookMarkURL(Context context) {
        this.context = context;
    }


    public void SetBookMark(Integer businessid, Integer memberid) {
        BusinessId = businessid;
        url = "http://test.shahrma.com/api/ApiDeleteBookMark?BusinessId=" + businessid + "&MemberId=" + memberid;
    }


    /**
     * @return
     */
    public String GetURL() {
        Log.i("URLurl", url);
        return url;
    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(context,"در حال حذف...",Toast.LENGTH_LONG).show();
    }

    /**
     * @param urls
     * @return
     */
    @Override
    protected Boolean doInBackground(String... urls) {
        try {

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
     * @param result
     */
    protected void onPostExecute(Boolean result) {
        AddDataBaseSqlite adb = new AddDataBaseSqlite(context);
        DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);

        if (result == true) {

            ddb.delete_bookmark(BusinessId);
            Toast.makeText(context,"از نشانه گذاری ها حذف شد",Toast.LENGTH_LONG).show();

        }
        else
        {
            Toast.makeText(context,"حذف نشد! دوباره امتحان کنید",Toast.LENGTH_LONG).show();
        }

    }
}
