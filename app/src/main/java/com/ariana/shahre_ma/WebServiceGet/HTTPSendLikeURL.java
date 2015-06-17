package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

/**
 * Created by ariana on 6/15/2015.
 */
public class HTTPSendLikeURL extends AsyncTask<String, Void, Integer> {

    private String[] blogTitles;
    private static final String TAG = "Http Connection";
    private  String mesage;

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
    protected Integer doInBackground(String... params) {
        InputStream inputStream = null;

        Integer result = 0;
        try {

            HttpClient httpclient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(GetURL());
            HttpResponse httpResponse = httpclient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            InputStream webs = entity.getContent();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "UTF-8"), 8);
                mesage = (reader.readLine());

                webs.close();
            } catch (Exception e) {
                Log.e("Error in conversion: ", e.toString());
            }
            int statusCode = httpResponse.getStatusLine().getStatusCode();
                /* 200 represents HTTP OK */
            if (statusCode == 200) {
                    /* receive response as inputStream */
                inputStream = httpResponse.getEntity().getContent();
                String response = convertInputStreamToString(inputStream);
                parseResult(response);
                result = 1; // Successful
            } else {
                result = 0; //"Failed to fetch data!";
            }
        } catch (Exception e) {
            Log.i(TAG, e.getLocalizedMessage());
            Log.i("Exception", e.toString());
        }

        return result; //"Failed to fetch data!";
    }


    @Override
    protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
        //  Toast.makeText(getApplicationContext(),mesage,Toast.LENGTH_LONG).show();

        if (result == 1) {
            Log.i("mesage ",mesage);
            //arrayAdapter = new ArrayAdapter(AreaActivity.this, android.R.layout.simple_list_item_1, blogTitles);

            //listView.setAdapter(arrayAdapter);
        } else {
            Log.e(TAG, "Failed to fetch data!");
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
