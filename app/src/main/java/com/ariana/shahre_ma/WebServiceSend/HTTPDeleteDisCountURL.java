package com.ariana.shahre_ma.WebServiceSend;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MessageDialog;

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

    /**
     * @param context
     */
    public HTTPDeleteDisCountURL(Context context) {
        this.context = context;
    }


    public void SetBookMark(Integer discountid) {
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
        pd.setMessage("حذف عکس ...");
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
        DataBaseSqlite db = new DataBaseSqlite(context);

        if (result == true) {
            pd.dismiss();
            db.delete_DisCountMember(discountid);
            MessageDialog messageDialog=new MessageDialog(context);
            messageDialog.ShowMessage("هشدار", "تخفیف حذف شد .", "باشه", "false");

        }
        else
        {
            pd.dismiss();
            MessageDialog messageDialog=new MessageDialog(context);
            messageDialog.ShowMessage("هشدار","تخفیف حذف نشد . دوباره امتحان کنید","باشه","false");
        }

    }
}