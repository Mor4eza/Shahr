package com.ariana.shahre_ma.WebServicePost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MainActivity;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by ariana on 8/18/2015.
 */
public class HTTPPostProductJson extends AsyncTask<String ,Long,Integer>
{

    private static  final  String url_product="";

    private static String data_json;// variable get json
    private  static Integer response_message;// variable response
    FieldClass fc=new FieldClass();
    ProgressDialog pd;
    // get/set

    // Set json Member
    public void  SetProduct_Json(String json)
    {
        data_json=json;
    }

    // Get Josn Member
    private String getProduct_json()
    {
        return data_json;
    }


    private static Context context;


    public HTTPPostProductJson(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("در حال ثبت ...");
        pd.setCancelable(false);
        pd.show();;
    }

    @Override
    protected Integer doInBackground(String... params) {
        Integer result=0;
        try
        {
            HttpClient httpClient=new DefaultHttpClient();
            HttpContext httpContext=new BasicHttpContext();
            HttpPost httpPost=new HttpPost(url_product);

            StringEntity se=new StringEntity(getProduct_json(),"UTF-8");

            httpPost.setEntity(se);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            HttpResponse httpResponse=httpClient.execute(httpPost,httpContext);

            HttpEntity entity=httpResponse.getEntity();
            InputStream webs=entity.getContent();
            try
            {
                BufferedReader reader=new BufferedReader(new InputStreamReader(webs,"UTF-8"),8);
                response_message=Integer.parseInt(reader.readLine());
                Log.i("respones", String.valueOf(response_message));

                webs.close();
                result=1;
            }
            catch (Exception e)
            {
                result=0;
            }
        }
        catch (Exception e){
            result=0;
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        try {
            if(integer==1)
            {
                    pd.dismiss();
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("پیغام");
                    alertDialog.setMessage("ثبت شد .");
                    alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();


            }
            else
            {
                pd.dismiss();
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("ارسال نشد دوباره امتحان کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }


        }
        catch (Exception e)
        {
            pd.dismiss();
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage(" .خطا ثبت نشد دوباره امتحان کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alertDialog.show();

        }

    }
}
