package com.ariana.shahre_ma;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetAreaJosn;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCityJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCollectionJson;

import java.util.Timer;
import java.util.TimerTask;


public class SplashActivity extends ActionBarActivity {

    KeySettings setting=new KeySettings(this);
    NetState net=new NetState(this);
    Timer t;
    String text="....";
    String text1="";
    TextView thread;
    Integer i=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        thread=(TextView ) findViewById(R.id.dots3);
        IntializeSetting();

        Log.i("Update", setting.getAllUpdate().toString());

        if (net.checkInternetConnection() == false)
        {
            Toast.makeText(getApplication(), "شبکه اینترنت قطع می باشد", Toast.LENGTH_LONG).show();
        }

        else if(setting.getAllUpdate()==false)
        {
            Log.i("Update", "true");

            HTTPGetAreaJosn httpGetAreaJosn =new HTTPGetAreaJosn(this);
            httpGetAreaJosn.execute();

            HTTPGetCityJson httpGetCityJson=new HTTPGetCityJson(this);
            httpGetCityJson.execute();
        }
        else
        {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }


        Dots();

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                 finish();
                 Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                 startActivity(mainIntent);

            }
        }, 7000);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.cancel();
    }

    public void Dots(){

        text="...";

        //Declare the timer
         t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {

                                      if (i <= 2)
                                      {

                                          Log.i("i",String.valueOf(i));
                                          text1 += text.substring(i,i+1);


                                          runOnUiThread(new Runnable() {
                                              @Override
                                              public void run() {
                                                  setTitle(text1);
                                                  thread.setText(text1);
                                              }
                                          });

                                      } else {
                                          i = 0;
                                          text1 = "";

                                      }
                                      i++;

                                  }

                              },

                0,500);
    }


    private  void IntializeSetting()
    {


        //Intializetion Field to keysetting
        if(setting.getinitialization())
        {

        }
        else
        {
            setting.saveAMtime("09:00");
            setting.savePMtime("16:00");
            setting.saveSearchBusiness(false);
            setting.saveCityName("");
            setting.saveCacheImage(true);
            setting.setinitialization(true);

        }
    }

}
