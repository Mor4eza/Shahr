package com.ariana.shahre_ma.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Notification.Notify;
import com.ariana.shahre_ma.Settings.KeySettings;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

/**
 * Created by ariana on 6/23/2015.
 */
public class MainService extends Service {
    AlarmManager alarmManager;
    HTTPGetNotificationJson_service noti;
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
        Toast.makeText(getApplicationContext(),"onCreate",Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this  ,  0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 60); // first time
        long frequency=60000* 1000; // in ms
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        Log.i("Service", "onStart");
        noti=new HTTPGetNotificationJson_service(this);
        noti.SetUrl_MemberId(1);


        noti.execute();
    }

    @Override
    public void onDestroy()
    {

        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("onBind","service");
        return null;
    }
}

 class HTTPGetNotificationJson_service extends AsyncTask<String,String,String> {

     DateTime t=new DateTime();
     KeySettings setting;
    public static String test;
    private static Context context;
    private static   String url_Notification;
    private Integer MEMberID;
     Integer NotifiId=0;
    Integer Id[];
    Integer OpinionType[];
    Integer erja[];
    Boolean ExecutionTime[];
    String  Description[];
    String  ExpirationDate[];
    String  City[];
    Integer CityId[];
    String  Subset[];
    Integer SubsetId[];
    Integer len=0;

    public   void SetUrl_MemberId(Integer memberid)
    {
        this.MEMberID=memberid;
        url_Notification="http://test.shahrma.com/api/ApiGiveNotification?memberId="+memberid;
        Log.i("URLurl", url_Notification);
    }

    private String GetUrl_business()
    {
        return  url_Notification;
    }

    public HTTPGetNotificationJson_service(Context c) {
        context = c;
        setting=new KeySettings(context);
    }

    @Override
    protected String doInBackground(String... params) {
        try {


            InputStream jsonStream = getStreamFromURL(GetUrl_business(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            onPostExecute();
        } catch (Exception e) {

            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
            Log.i("Exception",e.toString());
        }
        return null;
    }
    void parseJSON(String JSONString) {


        try {



            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];
            OpinionType=new Integer[areas.length()];
            erja =new Integer[areas.length()];
            ExecutionTime = new Boolean[areas.length()];
            Description = new String[areas.length()];
            ExpirationDate = new String[areas.length()];
            City=new String[areas.length()];
            CityId=new Integer[areas.length()];
            Subset=new String[areas.length()];
            SubsetId=new Integer[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                Id[i]=area.getInt("Id");
                OpinionType[i]=area.getInt("OpinionType");
                erja[i]=area.getInt("ErJa");
                ExecutionTime[i]=area.getBoolean("ExecutionTime");
                Description[i]=area.getString("Description");
                ExpirationDate[i]=area.getString("ExpirationDate");
                City[i]=area.getString("City");
                CityId[i]=area.getInt("CityId");
                Subset[i]=area.getString("Subset");
                SubsetId[i]=area.getInt("SubsetId");
                test=area.getString("Description");
            }

        } catch (JSONException e) {
            // Toast.makeText(getApplicationContext()," parse Json", Toast.LENGTH_LONG).show();
            Log.i("JSONException",e.toString());
        }
    }


    protected void onPostExecute() {
        try {

            //  Toast.makeText(context,market[0], Toast.LENGTH_LONG).show();
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            Cursor allrows=dbs.select_Notification();
            if(len>0)
            {

                for (int i = 0; i < len; i++)
                {
                    Cursor rows=dbs.select_NotificatonId(Id[i]);
                    rows.moveToFirst();
                    NotifiId=rows.getInt(0);
                    rows.close();
                    Log.i("ID", String.valueOf(Id[i]));
                    Log.i("count", String.valueOf(NotifiId));
                    if(NotifiId==0)
                    dbs.Add_Notification(Id[i], OpinionType[i], erja[i], ExecutionTime[i], Description[i], ExpirationDate[i], City[i], CityId[i], Subset[i], SubsetId[i]);


                }

                if(setting.getAMtime().equals(t.Time()))
                    Notify.Notificationm(context);
                else if(setting.getPMtime().equals(t.Time()))
                    Notify.Notificationm(context);
            }

        } catch (Exception e) {
            Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
            Log.i("Exception", e.toString());
        }
    }



    InputStream getStreamFromURL(String urlString, String method) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setReadTimeout(10000);
            huc.setConnectTimeout(15000);
            huc.setRequestMethod(method);
            huc.setDoInput(true);

            huc.connect();

            return huc.getInputStream();
        } catch (Exception e) {
            return null;
        }

    }

    String streamToString(InputStream is) {
        String result = "";
        String line = null;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                result = line + "\n";
            }
        } catch (Exception e) {
        }


        return result;
    }

}
