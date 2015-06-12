package com.ariana.shahre_ma.WebServicePost;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;

import com.ariana.shahre_ma.Fields.FieldClass;

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
 * Created by ariana2 on 6/5/2015.
 */
public class HTTPPostMemberJson extends AsyncTask<String, Long, Object> {

private static  final  String url_Member="http://test.shahrma.com/api/ApiTakeMembers";


    private ProgressDialog mProgressDialog;



    // variable get json
    private static String data_json;
    // variable response
    private  static String response_message;


    FieldClass fc=new FieldClass();
    private ProgressDialog dialog;

    // get/set

    // Set json Member
    public void  SetMember_Json(String json_member)
    {
        data_json=json_member;
    }

    // Get Josn Member
    public String GetMember_json()
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
    public HTTPPostMemberJson(Context c) {
        context = c;
    }


    protected void onPostExecute_start() {
        try {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.show();

        } catch (Exception e) {
        }
    }

    @Override
    protected String doInBackground(String... params) {
        try {

            onPostExecute_start();
            JSONObject json = new JSONObject(GetMember_json()); //your array;
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext httpContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url_Member);

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
                    dbs.Add_member(ID, fc.GetMember_Name(), fc.GetMember_Email(), fc.GetMember_Mobile(), fc.GetMember_Age(), fc.GetMember_Sex(), fc.GetMember_UserName(), fc.GetMember_Password(), fc.GetMember_CityId());
                    mProgressDialog.dismiss();
                } else {
                    Toast.makeText(context, "کاربر ساخته نشد دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                }

    }
}
