package com.ariana.shahre_ma.Bazarche.WebServicePost;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.Bazarche.Select_Image;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MessageDialog;

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

    private static  final  String url_product="http://test.shahrma.com/api/ApiTakeProduct";

    private static String data_json;// variable get json
    private   Integer response_message;// variable response
    FieldClass fc=new FieldClass();
    Integer errorCode=0;
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
            Log.i("JsonProductMember",getProduct_json());
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
                    fc.SetBusiness_Id(response_message);
                    pd.dismiss();

                  ((Activity)context).finish();
                  fc.SetType(2);
                  Intent i=new Intent(context, Select_Image.class);
                  context.startActivity(i);


            }
            else
            {
                pd.dismiss();
                MessageDialog messageDialog=new MessageDialog(context);
                messageDialog.ShowMessage("پیام","ثبت نشد . دوباره امتحان کنید","باشه","false");
            }


        }
        catch (Exception e)
        {
            pd.dismiss();
            MessageDialog messageDialog=new MessageDialog(context);
            messageDialog.ShowMessage("پیام", "ثبت نشد . دوباره امتحان کنید", "باشه", "false");

        }

    }
}
