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
import com.ariana.shahre_ma.MessageDialog;

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
public class HTTPPostMemberJson extends AsyncTask<String, Long,Integer> {

private static  final  String url_Member="http://test.shahrma.com/api/ApiTakeMembers";

    private static String data_json;// variable get json
    private  static Integer response_message;// variable response
    FieldClass fc=new FieldClass();
    MessageDialog messageDialog;
    ProgressDialog pd;
    Integer errorCode=0;
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

    // Get response
    public Integer GetResponse()
    {
        return response_message;
    }

    private static Context context;


   public HTTPPostMemberJson(Context c) {
        context = c;
       messageDialog=new MessageDialog(context);
    }


    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("در حال ثبت کاربر...");
        pd.setCancelable(false);
        pd.setButton("توقف", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                cancel(true);
            }
        });
        pd.show();
    }

    @Override
    protected Integer doInBackground(String... params)
    {
        Integer result=0;
        try {

            Log.i("JsonMember",GetMember_json());
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
                response_message = Integer.parseInt(reader.readLine());
                Log.i("respones",String.valueOf(response_message));
                webs.close();
                result=1;
            } catch (Exception e)
            {
                result=0;
                Log.e("Error in conversion: ", e.toString());
            }
        }
        catch (Exception e)
        {
            result=0;
            e.printStackTrace();
           // Toast.makeText(, e.toString(), Toast.LENGTH_LONG).show();
        }
        String s="1";
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {

        try {
            if(result==1)
            {
                DataBaseSqlite dbs = new DataBaseSqlite(context);
                Integer ID = GetResponse();
                Log.i("respones1", String.valueOf(ID));
                if (ID >0) {
                    Log.i("fc.GetMember_Name()", fc.GetMember_Name());
                    dbs.Add_member(ID, fc.GetMember_Name(), fc.GetMember_Email(), fc.GetMember_Mobile(), fc.GetMember_Age(), fc.GetMember_Sex(), fc.GetMember_UserName(), fc.GetMember_Password(), fc.GetMember_CityId());
                    pd.dismiss();

                    messageDialog.ShowMessage("هشدار","ورود شما را به خانواده بزرگ شهر ما تبریک می گوییم .","باشه","false");
                }
                else
                {
                    pd.dismiss();
                    if(response_message==0)
                        messageDialog.ShowMessage("هشدار","این نام کاربری قبلا ساخته شده لطفا نام کاربری دیگری وارد کنید","باشه","false");

                }
            }
            else
            {
                pd.dismiss();
               messageDialog.ShowMessage("هشدار","پاسخی از سمت سرور دریافت نشد . دوباره امتحان کنید","باشه","false");
            }

        }
        catch (Exception e)
        {
            pd.dismiss();
            messageDialog.ShowMessage("هشدار","لطفا دوباره امتحان کنید","باشه","false");
        }



    }
}
