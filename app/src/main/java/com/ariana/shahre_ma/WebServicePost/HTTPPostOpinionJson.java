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
public class HTTPPostOpinionJson extends AsyncTask<String, Void, Integer>
{
    private static  final  String url_Opinion="http://test.shahrma.com/api/ApiTakeOpinion";


    // variable get json
    private static String data_json;
    // variable response
    private  static String response_message;
    ProgressDialog pd;

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


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("ارسال...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        Integer s=0;
        try {

            //onPostExecute();
            Log.i("JSONopinion",GetOpinion_json());
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
                s=1;
            } catch (Exception e) {
                Log.e("Error in conversion: ", e.toString());
            }

            //SetResponse(json_String);

        } catch (Exception e) {
            e.printStackTrace();
            s=0;
            // Toast.makeText(, e.toString(), Toast.LENGTH_LONG).show();
        }

        return s;
    }

    @Override
    protected void onPostExecute(Integer result) {
            /* Download complete. Lets update UI */

        if(result==1) {
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            Integer ID = Integer.parseInt(GetResponse());
            if (ID >= 0) {
                dbs.Add_opinion(ID, fc.GetOpinion_Description(), fc.GetOpinion_Date(), fc.GetOpinion_Erja(), fc.GetOpinion_CountLike(), fc.GetOpinion_CountDisLike(), fc.GetOpinion_MemberName());


                    HTTPGetOpinionJson httponion = new HTTPGetOpinionJson(context);
                    httponion.seturl_opinion(fc.GetBusiness_Id());
                    httponion.execute();
                    pd.dismiss();
                    Toast.makeText(context,"نظر شما پس از تایید به نمایش گذاشته میشود!",Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "ارسال نشد، دوباره امتحان کنید", Toast.LENGTH_LONG).show();
                pd.dismiss();
            }
        }
        else
        {
            pd.dismiss();
        }

    }
}
