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
 * Created by ariana on 6/15/2015.
 */
public class  HTTPSendLikeURL extends AsyncTask<String, Void, Boolean> {

    private String[] blogTitles;
    private static final String TAG = "Http Connection";
    private  String mesage;
    ProgressDialog pd;
    Boolean like;
    Integer opinionid;
    Integer memberid;

    Context context;

    public HTTPSendLikeURL(Context context)
    {
        this.context=context;
    }

    public void Setopinionid(Integer opinionid)
    {
        this.opinionid=opinionid;
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
       String url="http://test.shahrma.com/api/ApiTakeLike?opinionId="+opinionid+"&memberId="+memberid+"&value="+like;
        Log.i("URL",url);
        return url;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("در حال بروزرسانی...");

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

                HttpEntity en=response.getEntity();
                String data = EntityUtils.toString(en);


                mesage=data;
                JSONObject jsono = new JSONObject(data);

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

            Log.i("JSONLike",mesage);
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
            Log.i("JSONException",e.toString());
        }
    }
}
