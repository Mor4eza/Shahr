package com.ariana.shahre_ma.Bazarche.WebServicePost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.Bazarche.Product_List;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.MessageDialog;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana2 on 9/22/2015...
 */
public class HTTPPostFilterJson extends AsyncTask<String ,Long,Integer>
{

    private static  final  String url_product="http://test.shahrma.com/api/ApiProductFilter";

    private static String data_json;// variable get json
    private  String response_message;// variable response
    FieldClass fc=new FieldClass();
    Integer errorCode=0;
    ProgressDialog pd;
    Integer len;
    FieldDataBase fdb=new FieldDataBase();
    List<Integer> selectId =new ArrayList<>();
    List<String>  selectName =new ArrayList<>();
    List<Double>  selectPrice =new ArrayList<>();
    List<Boolean> selectAdaptive =new ArrayList<>();
    List<String>  selectImage =new ArrayList<>();
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


    public HTTPPostFilterJson(Context context) {
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("دریافت...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        Integer result=0;
        try
        {
            Log.i("JsonFilter", getProduct_json());
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
                BufferedReader reader=new BufferedReader(new InputStreamReader(webs,"UTF-8"),1000);
                response_message=reader.readLine();
                Log.i("respones", String.valueOf(response_message));

                webs.close();
                result=1;
            }
            catch (Exception e)
            {
                Log.i("Error",e.toString());
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
        Log.i("Integer",integer.toString());
        try {
            if(integer==1)
            {
                parseJSON(response_message);
                pd.dismiss();
                ((Activity)context).finish();
                fc.SetFilterProduct(true);
                Intent i = new Intent(context, Product_List.class);
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
            messageDialog.ShowMessage("2پیام", "ثبت نشد . دوباره امتحان کنید", "باشه", "false");

        }

    }
    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            JSONArray areas = new JSONArray(JSONString);
            Log.i("JSONProduct", JSONString);
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);

                selectId.add(area.getInt("Id"));
                selectName.add(area.getString("Name"));
                selectPrice.add(area.getDouble("Price"));
                selectAdaptive.add(area.getBoolean("Adaptive"));
                selectImage.add(area.getString("Image"));

            }

            fdb.setId_Product(selectId);
            fdb.setName_Product(selectName);
            fdb.setPrice_Product(selectPrice);
            fdb.setAdaptive__Product(selectAdaptive);
            fdb.setImage_Product(selectImage);

        } catch (JSONException e) {

        }
    }

}
