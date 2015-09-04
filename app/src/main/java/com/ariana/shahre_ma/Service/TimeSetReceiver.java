package com.ariana.shahre_ma.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Notification.Notify;
import com.ariana.shahre_ma.Settings.KeySettings;

/**
 * Created by ariana on 7/27/2015.
 */
public class TimeSetReceiver  extends BroadcastReceiver
{
    DateTime dt=new DateTime();

    FieldClass fc=new FieldClass();

    @Override
    public void onReceive(Context context, Intent intent) {
        KeySettings setting=new KeySettings(context);
        SelectDataBaseSqlite sdb=new SelectDataBaseSqlite(context);
        DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);
        Log.d("AM",setting.getAMtime() );
        Log.d("Time",dt.Time() );

        if(dt.Time().equals(setting.getAMtime()) )
        {

            Cursor rows=sdb.select_AllNotificaton();
            if(rows.moveToFirst())
            {
                do
                {
                    Log.i("ExpirationDate",rows.getString(5));
                    if(rows.getString(5).equals(dt.Now()))
                    {
                        ddb.delete_Notification(rows.getInt(0));
                    }
                }while (rows.moveToNext());
            }


            Log.i("Time", dt.Time());
            Log.i("GetAMtime",setting.getAMtime());
            Log.i("subTIMe",dt.Time().substring(0, 2));
            if(Integer.parseInt(dt.Time().substring(0, 2))<=11 && Integer.parseInt(dt.Time().substring(0, 2))>=06)
                fc.SetNotificationGooMorning(true);

            if(rows.getCount()>0)
            {
                Notify.Notificationm(context);

            }

            /*Intent service1 = new Intent(context, ShowNotificationService.class);
            context.startService(service1);*/
        }
        else if(setting.getPMtime().equals(dt.Time()))
        {

            Cursor rows=sdb.select_AllNotificaton();
            if(rows.moveToFirst())
            {
                do
                {
                    Log.i("ExpirationDate",rows.getString(5));
                    if(rows.getString(5).equals(dt.Now()))
                    {
                        ddb.delete_Notification(rows.getInt(0));
                    }
                }while (rows.moveToNext());
            }


            Log.i("Time", dt.Time());
            Log.i("GetAMtime",setting.getAMtime());
            Log.i("subTIMe",dt.Time().substring(0, 2));
            if(Integer.parseInt(dt.Time().substring(0, 2))<=11 && Integer.parseInt(dt.Time().substring(0, 2))>=06)
                fc.SetNotificationGooMorning(true);

            if(rows.getCount()>0)
            {
                Notify.Notificationm(context);

            }


           /* Intent service1 = new Intent(context, ShowNotificationService.class);
            context.startService(service1);*/
        }




    }
}
