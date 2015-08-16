package com.ariana.shahre_ma.Service;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;
import android.util.Log;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Notification.Notify;
import com.ariana.shahre_ma.Settings.KeySettings;

public class ShowNotificationService extends Service {

    Query query=new Query(this);
    KeySettings setting=new KeySettings(this);
    DataBaseSqlite db=new DataBaseSqlite(this);
    DateTime dt=new DateTime();
    FieldClass fc=new FieldClass();
    Integer clock=6;
    Integer clock1=11;

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

        Cursor rows=db.select_AllNotificaton();
        if(rows.moveToFirst())
        {
            do
            {
                Log.i("ExpirationDate",rows.getString(5));
                if(rows.getString(5).equals(dt.Now()))
                {
                    db.delete_Notification(rows.getInt(0));
                }
            }while (rows.moveToNext());
        }


          Log.i("Time", dt.Time());
          Log.i("GetAMtime",setting.getAMtime());
          Log.i("subTIMe",dt.Time().substring(0, 1));
        if(Integer.parseInt(dt.Time().substring(0, 1))<=11 && Integer.parseInt(dt.Time().substring(0, 1))>=06)
            fc.SetNotificationGooMorning(true);

        if(rows.getCount()>0)
        {
            Notify.Notificationm(this);
            // else if(setting.getPMtime().equals(dt.Time()))
            Notify.Notificationm(this);
        }

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
