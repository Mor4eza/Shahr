package com.ariana.shahre_ma.Service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Notification.Notify;
import com.ariana.shahre_ma.Settings.KeySettings;

public class ShowNotificationService extends Service {

    Query query=new Query(this);
    KeySettings setting=new KeySettings(this);
    DateTime dt=new DateTime();

    public ShowNotificationService()
    {
    }



    @Override
    public void onCreate()
    {
        super.onCreate();


    }

    @SuppressWarnings("static-access")
    @Override
    public void onStart(Intent intent, int startId)
    {
        super.onStart(intent, startId);


          Log.i("Time", dt.Time());
          Log.i("GetAMtime",setting.getAMtime());
          if(setting.getAMtime().equals(dt.Time()))
          Notify.Notificationm(this);
          else if(setting.getPMtime().equals(dt.Time()))
          Notify.Notificationm(this);

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
