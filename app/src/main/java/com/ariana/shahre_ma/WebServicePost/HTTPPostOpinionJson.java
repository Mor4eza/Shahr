package com.ariana.shahre_ma.WebServicePost;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOpinionJson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ariana2 on 6/6/2015.
 */
public class HTTPPostOpinionJson extends AsyncTask<String, Long, Object>
{
    private static  final  String url_Opinion="http://test.shahrma.com/api/ApiTakeOpinion";


    // variable get json
    private static String data_json;
    // variable response
    private  static String response_message;


    FieldClass fc=new FieldClass();
    private ProgressDialog dialog;

    // get/set

    // Set json opinion
    public void  SetOpinion_Json(String json_member)
    {
        data_json=json_member;
    }

    // Get Josn opinion
    public String GetOpinion_json()
    {
        return data_json;
    }


    // Set response
    private void  SetResponse(String json_member)
    {
        response_message=json_member;
    }
    // Get response
    public String GetResponse()
    {
        return response_message;
    }

    private static Context context;
    public HTTPPostOpinionJson(Context c) {
        context = c;
    }


    protected void onPostExecute(String file_url) {
        try {

            this.dialog.setMessage("صبر کنید ...");
            this.dialog.show();

        } catch (Exception e) {
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            //onPostExecute();
            JSONObject json = new JSONObject(GetOpinion_json()); //your array;
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext httpContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url_Opinion);

            StringEntity se = new StringEntity(json.toString(),"UTF-8");

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse httpresponse = httpClient.execute(httpPost, httpContext); //execute your request and parse response


            //  String json_String = EntityUtils.toString(entity); //if response in JSON format

            HttpEntity entity = httpresponse.getEntity();
            InputStream webs = entity.getContent();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "UTF-8"), 8);
                response_message = (reader.readLine());
                webs.close();
            } catch (Exception e) {
                Log.e("Error in conversion: ", e.toString());
            }

            //SetResponse(json_String);
           onPostExecute();
        } catch (Exception e) {
            e.printStackTrace();
            // Toast.makeText(, e.toString(), Toast.LENGTH_LONG).show();
        }
        String s="1";
        return s;
    }

    protected void onPostExecute() {
            /* Download complete. Lets update UI */

        DataBaseSqlite dbs = new DataBaseSqlite(context);
        Integer ID = Integer.parseInt(GetResponse());
        if (ID >= 0) {
            dbs.Add_opinion(ID, fc.GetOpinion_Description(), fc.GetOpinion_Date(), fc.GetOpinion_OpinionType(), fc.GetOpinion_Erja());          if (dialog.isShowing()) {
              //  dialog.dismiss();
            }

            HTTPGetOpinionJson httponion = new HTTPGetOpinionJson(context);
            httponion.seturl_opinion(186);
            httponion.execute();
        } else {
            Toast.makeText(context, "کاربر ساخته نشد دوباره امتحان کنید", Toast.LENGTH_LONG).show();
        }

    }
}
