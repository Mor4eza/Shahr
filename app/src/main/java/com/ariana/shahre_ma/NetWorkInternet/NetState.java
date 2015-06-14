package com.ariana.shahre_ma.NetWorkInternet;

import android.content.Context;
import android.net.ConnectivityManager;

/**
 * Created by ariana on 6/13/2015.
 */
public class NetState
{
    Context context;
    public NetState(Context context)
    {
        this.context=context;
    }
    public boolean checkInternetConnection() {
        ConnectivityManager cm = (ConnectivityManager) this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // test for connection
        if (cm.getActiveNetworkInfo() != null
                && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        } else {
          //  Log.v(TAG, "Internet Connection Not Present");
            return false;
        }
    }
}
