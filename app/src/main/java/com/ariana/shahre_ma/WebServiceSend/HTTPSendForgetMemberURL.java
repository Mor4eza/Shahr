package com.ariana.shahre_ma.WebServiceSend;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by ariana on 7/21/2015.
 */
public class HTTPSendForgetMemberURL extends AsyncTask<String,Integer,Integer> {

    String url="";

    Context context;
    ProgressDialog pd;

    /**
     *
     * @param email
     */
    public void SetEmail(String email)
    {
        url=""+email;
    }

    /**
     *
     * @return
     */
    private String geturl(){
        return  url;
    }


    /**
     * Constructor
     * @param context
     */
    public  HTTPSendForgetMemberURL(Context context)
    {
        this.context=context;
    }

    /**
     *
     */
    @Override
    protected void onPreExecute(){
        super.onPreExecute();

        pd = new ProgressDialog(context);
        pd.setMessage("منتظر بمانید...");
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(true);

        pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {

            }
        });
    }

    /**
     *
     * @param params
     * @return
     */

    @Override
    protected Integer doInBackground(String... params) {
        Integer result=0;
        try{
            HttpGet httpGet=new HttpGet(geturl());
            HttpClient httpClient=new DefaultHttpClient();
            HttpResponse response=httpClient.execute(httpGet);

            int status=response.getStatusLine().getStatusCode();
            if(status==200) {

                HttpEntity entity=response.getEntity();
                String data= EntityUtils.toString(entity);
                result = 1;
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
     * @param progress
     */
    @Override
    protected void onProgressUpdate(Integer... progress) {
        super.onProgressUpdate(progress);
        pd.setIndeterminate(false);
        pd.setMax(100);
        pd.setProgress(progress[0]);
    }

    /**
     *
     * @param integer
     */
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer==1){
            pd.dismiss();
        }
        else
        {
            pd.dismiss();
        }
    }
}
