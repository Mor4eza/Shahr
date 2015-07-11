package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MainActivity;
import com.ariana.shahre_ma.MyProfile.Log_In;

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

    String mesage;
    Integer ID=0;
    ProgressDialog pd;
    FieldClass fc=new FieldClass();
    private String[] blogTitles;
    Context context;

    /**
     *
     * @param context
     */
    public HTTPGetLoginJson(Context context)
    {
        this.context=context;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("ورود...");
        pd.setCancelable(false);
        pd.show();
    }


    /**
     *
     * @param params
     * @return
     */
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
                mesage =reader.readLine();
               // Toast.makeText(context,mesage, Toast.LENGTH_LONG).show();
                webs.close();
            } catch (Exception e) {
             Log.e("Exception",e.toString());
            }
            int statusCode = httpResponse.getStatusLine().getStatusCode();

                /* 200 represents HTTP OK */
            if (statusCode == 200) {

                    /* receive response as inputStream */
              /* inputStream = httpResponse.getEntity().getContent();

                String response = convertInputStreamToString(inputStream);

                parseResult(response);*/

                result = 1; // Successful

            } else {
                result = 0; //"Failed to fetch data!";
            }

        } catch (Exception e) {
            Log.e("Exception",e.toString());
        }


        return result; //"Failed to fetch data!";
    }

    /**
     *
     * @param result
     */
    protected void onPostExecute(Integer result) {


        DataBaseSqlite dbs = new DataBaseSqlite(context);

        super.onPostExecute(result);

        if(result==1)
        {

            try {
                Log.i("mesage",mesage);
                JSONObject area = new JSONObject(mesage);

                try{
                ID = area.getInt("Id");
                fc.SetMember_Name(area.getString("Name"));
                fc.SetMember_Email(area.getString("Email"));
                fc.SetMember_Mobile(area.getString("Mobile"));
                fc.SetMember_Age(area.getInt("Age"));
                fc.SetMember_Sex(area.getBoolean("Sex"));
                fc.SetMember_UserName(area.getString("UserName"));
                fc.SetMember_Password(area.getString("Password"));
                fc.SetMember_CityId(area.getInt("CityId"));}
                catch (Exception e){}

                if (ID >= 0) {
                    Log.i("ID", String.valueOf(ID));
                    dbs.Add_member(ID, fc.GetMember_Name(), fc.GetMember_Email(), fc.GetMember_Mobile(), fc.GetMember_Age(), fc.GetMember_Sex(), fc.GetMember_UserName(), fc.GetMember_Password(), fc.GetMember_CityId());
                    Intent  i=new Intent(context.getApplicationContext(), MainActivity.class);
                    context.startActivity(i);
                    Log_In log = new Log_In();
                    log.finish();
                } else {
                    pd.dismiss();
                    Toast.makeText(context, "کاربر ساخته نشد دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                }
                pd.dismiss();
            }
            catch (Exception e){
                Log.e("Exception SaVe", e.toString());
                pd.dismiss();
            }
        }
        else
        {
            pd.dismiss();
        }

    }

    /**
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
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

    /**
     *
     * @param result
     */
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

    /**
     *
     * @return
     */
    public  String get_Message_Login()
    {
        return mesage;
    }
}
