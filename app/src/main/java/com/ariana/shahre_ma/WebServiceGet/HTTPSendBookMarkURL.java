package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;

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
 * Created by ariana2 on 6/17/2015.
 */
public class HTTPSendBookMarkURL extends AsyncTask<String, Void, Integer> {

    private String[] blogTitles;
    private static final String TAG = "Http Connection";
    private  String mesage;


    Integer businessid;
    Integer memberid;

    Context context;

    FieldClass fc=new FieldClass();

    public HTTPSendBookMarkURL(Context context)
    {
        this.context=context;
    }

    public void SetBusinessid(Integer businessid)
    {
        this.businessid=businessid;
    }

    public void SetMemberid(Integer memberid)
    {
        this.memberid=memberid;
    }



    public String GetURL()
    {
        String url="";
        url="http://test.shahrma.com/api/ApiTakeBookmark?businessId="+businessid+"&memberId="+memberid;
        return url;
    }

    // RunTime Methode
    @Override
    protected Integer doInBackground(String... params) {
        InputStream inputStream = null;

        Integer result = 0;
        try {
                /* create Apache HttpClient */
            HttpClient httpclient = new DefaultHttpClient();

            String sss = URLDecoder.decode(params[0], "UTF-8");

            HttpGet httpGet = new HttpGet(GetURL());
                /* optional request header */
            httpGet.setHeader("Content-Type", "application/json");


                /* optional request header */
            httpGet.setHeader("Accept", "application/json");

                /* Make http request call */
            // Toast.makeText(getApplicationContext(), "send", Toast.LENGTH_LONG).show();
            HttpResponse httpResponse = httpclient.execute(httpGet);

            HttpEntity entity = httpResponse.getEntity();
            InputStream webs = entity.getContent();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "UTF-8"), 8);
                mesage = (reader.readLine());
                // Toast.makeText(getApplicationContext(), mesage.toString(), Toast.LENGTH_LONG).show();
                webs.close();
            } catch (Exception e) {
                Log.e("Error in conversion: ", e.toString());
                // Toast.makeText(getApplicationContext(), "errorr", Toast.LENGTH_LONG).show();
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
            Log.d(TAG, e.getLocalizedMessage());
        }

        return result; //"Failed to fetch data!";
    }


    @Override
    protected void onPostExecute(Integer result) {

        //  Toast.makeText(getApplicationContext(),mesage,Toast.LENGTH_LONG).show();

        if (result == 1) {

            DataBaseSqlite db=new DataBaseSqlite(context);
            db.Add_bookmark(fc.GetBusinessid_BookMark(),fc.GetMemberid_BookMark());
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
        }
    }
}
