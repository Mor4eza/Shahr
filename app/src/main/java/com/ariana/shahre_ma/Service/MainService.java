package com.ariana.shahre_ma.Service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetInterestJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetNotificationJson;

import java.util.Calendar;

/**
 * Created by ariana on 6/23/2015.
 */
public class MainService extends Service {
    HTTPGetNotificationJson noti;
    Query query=new Query(this);
    @Override
    public void onCreate()
    {
        // TODO Auto-generated method stub
        super.onCreate();
       // Toast.makeText(getApplicationContext(),"onCreate",Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(this, MyReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this  ,  0, myIntent, 0);

        AlarmManager alarmManager = (AlarmManager)this.getSystemService(this.ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 5); // first time
        long frequency=10* 1000; // in ms
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), frequency, pendingIntent);
    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);

        Log.i("Service", "onStart");

        try {
            noti = new HTTPGetNotificationJson(this);
            noti.SetUrl_MemberId(query.getMemberId());
            noti.execute();

            HTTPGetInterestJson httpGetInterestJson = new HTTPGetInterestJson(this);
            httpGetInterestJson.SetUrl_Interest(query.getMemberId());
            httpGetInterestJson.execute();
        }
        catch (Exception e)
        {

        }
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


