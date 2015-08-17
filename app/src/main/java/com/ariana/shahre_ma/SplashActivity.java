package com.ariana.shahre_ma;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
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
        LocalBroadcastManager.getInstance(this).registerReceiver(mCityReceiver, new IntentFilter("GetCity"));



         if(setting.getAllUpdate())
        {

            if(net.checkInternetConnection())
            {
                AlertDialog alertDialog=new AlertDialog.Builder(SplashActivity.this).create();
                alertDialog.setTitle("پیام");
                alertDialog.setMessage("برای اینکه اولین بار وارد برنامه شدید ممکن است برای اولین بار دریافت اطلاعات کمی طولانی شود ");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {

                    }
                });

                // Showing Alert Message
                alertDialog.show();

                HTTPGetCityJson httpGetCityJson = new HTTPGetCityJson(SplashActivity.this);
                httpGetCityJson.execute();

            }
            else
            {
                AlertDialog alertDialog=new AlertDialog.Builder(SplashActivity.this).create();
                alertDialog.setTitle("اینترنت قطع می باشد");
                alertDialog.setCancelable(false);
                alertDialog.setMessage("برای اولین باز ورود به برنامه اینترنت مورد نیاز است . اینترنت خود را روشن کنید و بر روی ادامه کلیک کنید .");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Log.i("Broad","restart1");
                        Restart();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        }

        else
        {
            Intent i=new Intent(this,MainActivity.class);
            startActivity(i);
        }


        Dots();

    /*    new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                *//* Create an Intent that will start the Menu-Activity. *//*
                 finish();
                 Intent mainIntent = new Intent(getApplicationContext(),MainActivity.class);
                 startActivity(mainIntent);

            }
        }, 7000);*/
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.cancel();
    }

    public void Dots(){

        //Declare the timer
         t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {

                                  @Override
                                  public void run() {

                                      if (i <= 2)
                                      {
                                          text1 += text.substring(0,i+1);
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
            setting.setUpdateAll(true);

        }
    }


    private BroadcastReceiver mCityReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Log.i("Broad","restart");
            Restart();
        }
    };

    public void Restart()
    {
        if(net.checkInternetConnection())
        {
            AlertDialog alertDialog=new AlertDialog.Builder(SplashActivity.this).create();
            alertDialog.setTitle("پیام");
            alertDialog.setMessage("اطلاعات دریافت نشد");
            alertDialog.setButton("تلاش مجدد", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                    HTTPGetCityJson httpGetCityJson=new HTTPGetCityJson(SplashActivity.this);
                    httpGetCityJson.execute();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        else
        {
            AlertDialog alertDialog=new AlertDialog.Builder(SplashActivity.this).create();
            alertDialog.setTitle("اینترنت قطع می باشد");
            //alertDialog.setCancelable(false);
            alertDialog.setMessage("برای اولین باز ورود به برنامه اینترنت مورد نیاز است . اینترنت خود را روشن کنید و بر روی ادامه کلیک کنید .");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {
                    Log.i("Broad","restart");
                    Restart();
                }
            });

            // Showing Alert Message
            alertDialog.show();
        }

    }

}
