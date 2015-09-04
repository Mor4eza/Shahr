package com.ariana.shahre_ma.WebServiceSend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MessageDialog;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessImageJson;

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
    String src = "";
    Integer BusinessId = 0;
    Integer errorCode = 0;
    Context context;
    ProgressDialog pd;
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
            db.delete_bookmark(BusinessId);
            MessageDialog messageDialog=new MessageDialog(context);
            messageDialog.ShowMessage("هشدار", "عکس حذف شد", "باشه", "false");

        }
        else
        {
            pd.dismiss();
            MessageDialog messageDialog=new MessageDialog(context);
            messageDialog.ShowMessage("هشدار","عکس حذف نشد دوباره امتحان کنید","باشه","false");
        }

    }
}
