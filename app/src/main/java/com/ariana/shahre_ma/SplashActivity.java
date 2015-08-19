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
import android.widget.TextView;

import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCityJson;

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

        try {

                if (setting.getAllUpdate()) {

                    if (net.checkInternetConnection()) {

                        HTTPGetCityJson httpGetCityJson = new HTTPGetCityJson(SplashActivity.this);
                        httpGetCityJson.execute();

                    } else {
                        AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
                        alertDialog.setTitle("اینترنت قطع می باشد");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("برای اولین بار ورود به برنامه اینترنت مورد نیاز است . اینترنت خود را روشن کنید و بر روی ادامه کلیک کنید .");
                        alertDialog.setButton("ادامه", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i("Broad", "restart1");
                                Restart();
                            }
                        });

                        alertDialog.setButton2("خروج", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                        // Showing Alert Message
                        alertDialog.show();
                    }
                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            finish();
                            Intent mainIntent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(mainIntent);

                        }
                }, 3000);
            }


            Dots();
        }catch (Exception e){}


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        t.cancel();
    }

    public void Dots(){

        try {
            t = new Timer();
            t.scheduleAtFixedRate(new TimerTask() {

                                      @Override
                                      public void run() {

                                          if (i <= 2) {
                                              text1 = text.substring(0, i + 1);
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

                    0, 500);
        }
        catch (Exception e){}
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
        try {
            if (net.checkInternetConnection()) {
                AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("اطلاعات دریافت نشد");
                alertDialog.setButton("تلاش مجدد", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        HTTPGetCityJson httpGetCityJson = new HTTPGetCityJson(SplashActivity.this);
                        httpGetCityJson.execute();
                    }
                });

                alertDialog.setButton2("خروج", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });


                // Showing Alert Message
                alertDialog.show();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(SplashActivity.this).create();
                alertDialog.setTitle("اینترنت قطع می باشد");
                //alertDialog.setCancelable(false);
                alertDialog.setMessage("برای اولین بار ورود به برنامه اینترنت مورد نیاز است . اینترنت خود را روشن کنید و بر روی ادامه کلیک کنید .");
                alertDialog.setButton("ادامه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("Broad", "restart");
                        Restart();
                    }
                });
                alertDialog.setButton2("خروج", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });

                // Showing Alert Message
                alertDialog.show();
            }
        }
        catch (Exception e){}

    }

}
