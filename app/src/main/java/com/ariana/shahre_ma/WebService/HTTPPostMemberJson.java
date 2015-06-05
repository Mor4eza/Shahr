package com.ariana.shahre_ma.WebService;

import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;

/**
 * Created by ariana2 on 6/5/2015.
 */
public class HTTPPostMemberJson extends AsyncTask<String, Long, Object> {

private static  final  String url_Member="";

    @Override
    protected String doInBackground(String... params) {
        try {
            JSONArray json = new JSONArray("Sqlite_Json"); //your array;
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext httpContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url_Member);

            StringEntity se = new StringEntity(json.toString(),"UTF-8");

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse response = httpClient.execute(httpPost, httpContext); //execute your request and parse response
            HttpEntity entity = response.getEntity();

            String jsonString = EntityUtils.toString(entity); //if response in JSON format

        } catch (Exception e) {
            e.printStackTrace();
           // Toast.makeText(, e.toString(), Toast.LENGTH_LONG).show();
        }
        String s="1";
        return s;
    }


}
