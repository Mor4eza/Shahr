package com.ariana.shahre_ma;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCityJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCollectionJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetFieldActivityJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetSubsetJson;


public class SplashActivity extends ActionBarActivity {

    KeySettings setting=new KeySettings(this);
    NetState net=new NetState(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        Log.i("collection",String.valueOf(setting.getCollectionDownload()));
        Log.i("subset",String.valueOf(setting.getSubsetDownload()));
        Log.i("fieldactivity",String.valueOf(setting.getFieldActivityDownload()));
        Log.i("city",String.valueOf(setting.getCityDownload()));

        if (net.checkInternetConnection() == false)
        {
            Toast.makeText(getApplication(), "شبکه اینترنت قطع می باشد", Toast.LENGTH_LONG).show();
        }

        else if(setting.getCollectionDownload()==false)
        {
            HTTPGetCollectionJson httpGetCollectionJson=new HTTPGetCollectionJson(this);
            httpGetCollectionJson.execute();
        }
        else if(setting.getSubsetDownload()==false)
        {
            HTTPGetSubsetJson httpGetSubsetJson=new HTTPGetSubsetJson(this);
            httpGetSubsetJson.execute();

        }
        else if(setting.getFieldActivityDownload()==false)
        {
            HTTPGetFieldActivityJson httpGetFieldActivityJson=new HTTPGetFieldActivityJson(this);
            httpGetFieldActivityJson.execute();

        }
        else if(setting.getCityDownload()==false)
        {
            HTTPGetCityJson httpGetCityJson=new HTTPGetCityJson(this);
            httpGetCityJson.execute();

        }
        else
        {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }


    }
}
