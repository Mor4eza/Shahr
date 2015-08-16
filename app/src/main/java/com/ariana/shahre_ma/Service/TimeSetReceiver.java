package com.ariana.shahre_ma.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.Settings.KeySettings;

/**
 * Created by ariana on 7/27/2015.
 */
public class TimeSetReceiver  extends BroadcastReceiver
{
    DateTime dt=new DateTime();
    CalendarTool ct=new CalendarTool();

    @Override
    public void onReceive(Context context, Intent intent) {
        KeySettings setting=new KeySettings(context);

        Log.d("AM",setting.getAMtime() );
        Log.d("Time",dt.Time() );

        if(dt.Time().equals(setting.getAMtime()) )
        {
            Intent service1 = new Intent(context, ShowNotificationService.class);
            context.startService(service1);
        }
        else if(setting.getPMtime().equals(dt.Time()))
        {
            Intent service1 = new Intent(context, ShowNotificationService.class);
            context.startService(service1);
        }




    }
}
