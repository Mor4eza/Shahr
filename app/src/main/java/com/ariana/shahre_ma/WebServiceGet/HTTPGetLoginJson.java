package com.ariana.shahre_ma.WebServiceGet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MainActivity;
import com.ariana.shahre_ma.MyProfile.Log_In;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URLDecoder;

/**
 * Created by ariana2 on 6/8/2015.
 */


public class HTTPGetLoginJson extends AsyncTask<String, Void, Integer>{

    String mesage;
    Integer ID=0;
    FieldClass fc=new FieldClass();
    Context context;
    Integer errorCode=0;
    /**
     *
     * @param context
     */
    public HTTPGetLoginJson(Context context)
    {
        this.context=context;
    }

    /**
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
            String sss = URLDecoder.decode(params[0], "UTF-8");

            HttpGet httpGet = new HttpGet(params[0]);
            httpGet.setHeader("Content-Type", "application/json");
            httpGet.setHeader("Accept", "application/json");
                /* Make http request call */
            HttpResponse httpResponse = httpclient.execute(httpGet);

            HttpEntity entity = httpResponse.getEntity();
            InputStream webs = entity.getContent();
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(webs, "UTF-8"), 8);
                mesage =reader.readLine();
                webs.close();
            } catch (Exception e) {
             Log.e("Exception",e.toString());
            }
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            errorCode=httpResponse.getStatusLine().getStatusCode();
            if (statusCode == 200) {

                result = 1; // Successful

            }
            else
            {
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

        super.onPostExecute(result);
        Log.i("resultLogin", String.valueOf(result));
        if(result==1)
        {
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            try {
                Log.i("JsonLoginMember",mesage);
                try
                {
                JSONObject area = new JSONObject(mesage);


                ID = area.getInt("Id");
                fc.SetMember_Name(area.getString("Name"));
                fc.SetMember_Email(area.getString("Email"));
                fc.SetMember_Mobile(area.getString("Mobile"));
                fc.SetMember_UserName(area.getString("UserName"));
                fc.SetMember_Password(area.getString("Password"));
                fc.SetMember_Email(area.getString("Email"));
                fc.SetMember_Mobile(area.getString("Mobile"));


                    if(String.valueOf(area.getString("CityId")).equals(null) || String.valueOf(area.getString("CityId")).equals("null"))
                        fc.SetMember_CityId(1);
                    else
                        fc.SetMember_CityId(area.getInt("CityId"));

                    if(String.valueOf(area.getString("Sex")).equals(null) || String.valueOf(area.getString("Sex")).equals("null"))
                    fc.SetMember_Sex(true);
                        else
                    fc.SetMember_Sex(area.getBoolean("Sex"));

                    if(String.valueOf(area.getString("Age")).equals(null) || String.valueOf(area.getString("Age")).equals("null"))
                        fc.SetMember_Age(0);
                        else
                       fc.SetMember_Age(area.getInt("Age"));

                }
                catch (Exception e){
                    Log.i("ExceptionMemberJson",e.toString());
                }


                if (ID > 0)
                {
                    Log.i("ID", String.valueOf(ID));
                    dbs.Add_member(ID, fc.GetMember_Name(), fc.GetMember_Email(), fc.GetMember_Mobile(), fc.GetMember_Age(), fc.GetMember_Sex(), fc.GetMember_UserName(), fc.GetMember_Password(), fc.GetMember_CityId());

                    Log_In.btn.setProgress(100);

                    Log_In.btn.setProgress(0);
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("خوش آمدید");
                    alertDialog.setMessage("ورود شما را به خانواده بزرگ شهر ما تبریک می گوییم .");
                    alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(context, MainActivity.class);
                            context.startActivity(i);
                            ((Activity) context).finish();
                        }
                    });
                    alertDialog.show();



                    fc.SetMember_Name("");
                    fc.SetMember_Email("");
                    fc.SetMember_Mobile("");
                    fc.SetMember_Age(0);
                    fc.SetMember_Sex(true);
                    fc.SetMember_UserName("");
                    fc.SetMember_Password("");
                    fc.SetMember_CityId(0);



                } else
                {
                    Log_In.btn.setProgress(0);
                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("خطا");
                    alertDialog.setMessage("رمز و نام کاربری اشتباه است");
                    alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();

                }

            }
            catch (Exception e)
            {
                Log_In.btn.setProgress(0);
                Log.e("ExceptionLogin", e.toString());
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("خطا");
                alertDialog.setMessage("دوباره امتحان کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

            }
        }
        else
        {
            Log_In.btn.setProgress(0);
            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("خطا");
            alertDialog.setMessage("رمز و نام کاربری اشتباه است");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            alertDialog.show();
        }

    }

}
