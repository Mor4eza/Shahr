package com.ariana.shahre_ma.WebServicePost;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ariana on 6/16/2015.
 */
public class HTTPPostInterestJson     extends AsyncTask<String, Long, Integer>
    {
        private static  final  String url_Interest="http://test.shahrma.com/api/ApiTakeInterest";


        // variable get json
        private static String data_json;
        // variable response
        private  static String response_message;
        private static Context context;

        FieldClass fc=new FieldClass();

        private ProgressDialog pd;

        // get/set

        // Set json opinion
    public void  SetInterest_Json(String json_Interest)
    {
        data_json=json_Interest;
    }

    // Get Josn opinion
    public String GetInterest_json()
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



    public HTTPPostInterestJson(Context c) {
        this.context = c;
    }



        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(context);
            pd.setMessage("در حال ثبت...");
            pd.setCancelable(false);
            pd.show();
        }
    @Override
    protected Integer doInBackground(String... params) {
        Integer result=0;
        try {


            Log.e("JSON: ",GetInterest_json());
           // JSONObject json = new JSONObject(GetInterest_json()); //your array;
            JSONArray json = new JSONArray(GetInterest_json()); //your array;
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext httpContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url_Interest);

            StringEntity se = new StringEntity(json.toString(),"UTF-8");

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");


            HttpResponse httpresponse = httpClient.execute(httpPost, httpContext); //execute your request and parse response


            HttpEntity entity = httpresponse.getEntity();
            InputStream webs = entity.getContent();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "UTF-8"), 8);
                response_message = (reader.readLine());
                webs.close();
                result=1;
                pd.dismiss();

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("پیام");
                alertDialog.setMessage("ثبت شد .");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

            } catch (Exception e)
            {
                pd.dismiss();
                result=0;

                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("خطا");
                alertDialog.setMessage("ثبت نشد دوباره امتحان کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
                Log.e("Error in conversion: ", e.toString());
            }


        } catch (Exception e)
        {
            pd.dismiss();
            result=0;

            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("ثبت نشد دوباره امتحان کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();

            e.printStackTrace();
            Log.e("Error in Exception: ", e.toString());
            // Toast.makeText(, e.toString(), Toast.LENGTH_LONG).show();
        }

        return result;
    }


}
