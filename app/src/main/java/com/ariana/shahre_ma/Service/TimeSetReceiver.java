package com.ariana.shahre_ma.Service;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.Fields.FieldClass;

/**
 * Created by ariana on 7/27/2015.
 */
public class TimeSetReceiver  extends WakefulBroadcastReceiver
{
    DateTime dt=new DateTime();

    FieldClass fc=new FieldClass();

    @Override
    public void onReceive(Context context, Intent intent)
    {
            Intent service1 = new Intent(context, ShowNotificationService.class);
            context.startService(service1);
        }
}
