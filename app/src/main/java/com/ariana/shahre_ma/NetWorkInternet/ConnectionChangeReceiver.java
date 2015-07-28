package com.ariana.shahre_ma.NetWorkInternet;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.Service.MainService;

import java.util.List;

/**
 * Created by ariana2 on 7/21/2015.
 */
public class ConnectionChangeReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive( Context context, Intent intent )
    {
        NetState net=new NetState(context);



        Log.i("ConnectionChange","000");

        if(net.checkInternetConnection())//Show Net State
        {
            //Run Service
            Intent service1 = new Intent(context, MainService.class);
            context.startService(service1);
            Log.i("ServiceStart","true");
        }
        else
        {
           //Stop Service
            Intent service1 = new Intent(context, MainService.class);
            context.stopService(service1);
            Log.i("ServiceStop", "true");
        }

        //Show Net state
        if (isAppForground(context)) {
            if (net.checkInternetConnection()) {
                Toast.makeText(context, "شبکه اینترنت متصل شد", Toast.LENGTH_LONG).show();
            } else
            {
                Toast.makeText(context, "شبکه اینترنت قطع شد", Toast.LENGTH_LONG).show();
                /*   Intent i = new Intent(context, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(i);
               */
            }
        }

    }
    public boolean isAppForground(Context mContext) {

        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> tasks = am.getRunningTasks(1);
        if (!tasks.isEmpty()) {
            ComponentName topActivity = tasks.get(0).topActivity;
            if (!topActivity.getPackageName().equals(mContext.getPackageName())) {
                return false;
            }
        }

        return true;
    }

}
