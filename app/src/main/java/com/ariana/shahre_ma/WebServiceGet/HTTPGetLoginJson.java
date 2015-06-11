package com.ariana.shahre_ma.WebServiceGet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.ariana.shahre_ma.MainActivity;

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
 * Created by ariana2 on 6/8/2015.
 */
public class HTTPGetLoginJson extends AsyncTask<String, Void, Integer>{

    Integer mesage;
    private String[] blogTitles;
    Context context;

    public HTTPGetLoginJson(Context context)
    {
        this.context=context;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // progressDialog = ProgressDialog.show(LoginActivity.this, "صبر کنید", "در حال دریافت اطلاعات ...");
    }
    @Override
    protected Integer doInBackground(String... params) {
        InputStream inputStream = null;
        Integer result = 0;
        try {
                /* create Apache HttpClient */
            HttpClient httpclient = new DefaultHttpClient();

                /* HttpGet Method */

            // String paramString = URLEncodedUtils.format(params, "utf-8");
            String sss = URLDecoder.decode(params[0], "UTF-8");

            HttpGet httpGet = new HttpGet(params[0]);




                /* optional request header */
            httpGet.setHeader("Content-Type", "application/json");


                /* optional request header */
            httpGet.setHeader("Accept", "application/json");

                /* Make http request call */
            HttpResponse httpResponse = httpclient.execute(httpGet);


            //mesage=httpResponse.getStatusLine().toString();

            HttpEntity entity = httpResponse.getEntity();
            InputStream webs = entity.getContent();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "UTF-8"), 8);
                mesage = Integer.parseInt(reader.readLine());
                webs.close();
            } catch (Exception e) {

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

        }

        return result; //"Failed to fetch data!";
    }


    @Override
    protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */
        // progressDialog.dismiss();
        if (mesage == 1) {


            Intent i=new Intent(context, MainActivity.class);
            context.startActivity(i);

        } else {

        }
    }


    private String convertInputStreamToString(InputStream inputStream) throws IOException {

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        String line = "";
        String result = "";

        while ((line = bufferedReader.readLine()) != null) {
            result += line;
        }

            /* Close Stream */
        if (null != inputStream) {
            inputStream.close();
        }

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
           // e.printStackTrace();
        }
    }
}
