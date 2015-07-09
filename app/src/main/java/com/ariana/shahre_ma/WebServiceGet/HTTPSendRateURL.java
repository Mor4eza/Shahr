package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ariana on 6/16/2015.
 */
public class HTTPSendRateURL extends AsyncTask<String, Void, Boolean> {

    ProgressDialog pd;
    private String[] blogTitles;
    private static final String TAG = "Http Connection";
    private  String mesage;


    Integer businessid;
    Integer memberid;

    Double rate;
    Context context;

    public HTTPSendRateURL(Context context)
    {
        this.context=context;
    }


    public void SetBusinessId(Integer opinionid)
    {
        this.businessid=opinionid;
    }

    public void SetMemberId(Integer memberid)
    {
        this.memberid=memberid;
    }

    public void SetRate(Double rate)
    {
        this.rate=rate;
    }

    public String GetURL()
    {
        String url="http://test.shahrma.com/api/ApiTakeRate?businessId="+businessid+"&memberId="+memberid+"&rate="+rate;
        return url;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("ارسال...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected Boolean doInBackground(String... urls) {
        try {

            //------------------>>
            HttpGet httppost = new HttpGet(GetURL());
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httppost);

            // StatusLine stat = response.getStatusLine();
            int status = response.getStatusLine().getStatusCode();

            if (status == 200) {
                HttpEntity entity = response.getEntity();
                String data = EntityUtils.toString(entity);


                JSONObject jsono = new JSONObject(data);
                Log.i("", String.valueOf(jsono));

                return true;
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {

            e.printStackTrace();
        }
        return false;
    }

    protected void onPostExecute(Boolean result) {


        if (result == true) {

            pd.dismiss();
        } else {
            pd.dismiss();
        }
    }

    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        String line = "";
        String result = "";
        try
        {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));



            while ((line = bufferedReader.readLine()) != null) {
                result += line;
            }

            /* Close Stream */
            if (null != inputStream) {
                inputStream.close();
            }


        }
        catch (Exception e){}
        return result;
    }

    private void parseResult(String result) {

        try {
            JSONObject response = new JSONObject(result);

            JSONArray posts = response.optJSONArray("posts");

            blogTitles = new String[posts.length()];

            for (int i = 0; i < posts.length(); i++) {
                JSONObject post = posts.optJSONObject(i);
                String title = post.optString("title");

                blogTitles[i] = title;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
