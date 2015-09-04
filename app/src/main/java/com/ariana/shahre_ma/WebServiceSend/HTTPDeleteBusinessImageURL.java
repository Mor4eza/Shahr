package com.ariana.shahre_ma.WebServiceSend;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.AddDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessImageJson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by ariana on 8/25/2015.
 */
public class HTTPDeleteBusinessImageURL extends AsyncTask<String, Void, Boolean> {

    String url="";

    String src="";
    Integer BusinessId=0;
    Integer errorCode=0;
    Context context;
    ProgressDialog pd;
    FieldClass fc=new FieldClass();

    /**
     *
     * @param context
     */
    public HTTPDeleteBusinessImageURL(Context context)
    {
        this.context=context;
    }


    public void SetDeleteBusinessImage(Integer businessid,String imagename,Integer type)
    {
        src=imagename;
        BusinessId=businessid;
        url="http://test.shahrma.com/api/ApiDeleteImage?Id="+businessid+"&img="+imagename+"&type="+type;
    }



    /**
     *
     * @return
     */
    public String GetURL()
    {
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
     *
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
     *
     * @param result
     */
    protected void onPostExecute(Boolean result) {
        AddDataBaseSqlite adb = new AddDataBaseSqlite(context);
        DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);

        if(result==true)
        {
            ddb.delete_BusinessImage(src,BusinessId);

            pd.dismiss();

            HTTPGetBusinessImageJson httpGetBusinessImageJson=new HTTPGetBusinessImageJson(context);
            httpGetBusinessImageJson.SetBusinessId(fc.GetBusiness_Id());
            httpGetBusinessImageJson.execute();

            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("پیام");
            alertDialog.setMessage("عکس حذف شد .");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            alertDialog.show();

        }
        else
        {
            pd.dismiss();

            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("عکس حذف نشد. دوباره امتحان کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            alertDialog.show();
        }

    }

}
