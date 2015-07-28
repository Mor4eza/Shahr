package com.ariana.shahre_ma.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by ariana on 7/27/2015.
 */
public class TimeSetReceiver  extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("TimeSetReceiver", "111");
    }
}
