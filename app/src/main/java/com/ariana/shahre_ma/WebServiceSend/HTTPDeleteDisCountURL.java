package com.ariana.shahre_ma.WebServiceSend;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.AddDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MessageDialog;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetDisCountJson;

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
public class HTTPDeleteDisCountURL extends AsyncTask<String, Void, Boolean> {

    String url = "";
    String src = "";
    Integer discountid = 0;
    Integer errorCode = 0;
    Context context;
    ProgressDialog pd;
    FieldClass fc = new FieldClass();
    MessageDialog messageDialog;
    /**
     * @param context
     */
    public HTTPDeleteDisCountURL(Context context) {
        this.context = context;
        messageDialog =new MessageDialog(context);
    }


    public void SetDisCount(Integer discountid) {
        discountid = discountid;
        url = "http://test.shahrma.com/api/ApiDeleteDisCount?DisCountId=" +discountid ;
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
        pd = new ProgressDialog(context);
        pd.setMessage("در حال حذف تخفیف...");
        pd.setCancelable(false);
        pd.show();
    }

    /**
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
     * @param result
     */
    protected void onPostExecute(Boolean result) {
        AddDataBaseSqlite adb = new AddDataBaseSqlite(context);
        DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);

        if (result == true) {
            pd.dismiss();
            ddb.delete_DisCountMember(discountid);
            messageDialog.ShowMessage("هشدار", "تخفیف حذف شد .", "باشه", "false");
            HTTPGetDisCountJson httpGetDisCountJson = new HTTPGetDisCountJson(context);
            httpGetDisCountJson.seturl_DisCount(fc.GetBusiness_Id());
            httpGetDisCountJson.execute();

        }
        else
        {
            pd.dismiss();
            messageDialog.ShowMessage("هشدار","تخفیف حذف نشد . دوباره امتحان کنید","باشه","false");
        }

    }
}