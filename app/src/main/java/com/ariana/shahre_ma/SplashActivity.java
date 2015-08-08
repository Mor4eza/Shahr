package com.ariana.shahre_ma;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;

import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCityJson;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends ActionBarActivity {

    KeySettings setting=new KeySettings(this);
    NetState net=new NetState(this);

    String text="....";
    String text1="";
    TextView thread;
    Integer i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        thread=(TextView ) findViewById(R.id.dots3);

        Log.i("collection",String.valueOf(setting.getCollectionDownload()));
        Log.i("subset",String.valueOf(setting.getSubsetDownload()));
        Log.i("fieldactivity",String.valueOf(setting.getFieldActivityDownload()));
        Log.i("city",String.valueOf(setting.getCityDownload()));

       /* if (net.checkInternetConnection() == false)
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
        {*/
            HTTPGetCityJson httpGetCityJson=new HTTPGetCityJson(this);
            httpGetCityJson.execute();

        /*}
        else
        {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }*/


        Dots();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
              startActivity(mainIntent);
                finish();
            }
        }, 5000);
    }





    public void Dots(){

        text="....";

        //Declare the timer
        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {

                                      if (i <= 3) {
                                          text1 += text.substring(i, i + 1);


                                          runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  setTitle(text1);
                                                  thread.setText(text1);
                                              }
                                          });

                                         // Log.i("kkk", text1);
                                      } else {
                                          i = 0;
                                          text1 = "";

                                      }
                                      i++;

                                  }

                              },

                0,

                300);
    }

}
