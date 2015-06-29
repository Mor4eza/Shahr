package com.ariana.shahre_ma.Settings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hamed on 24/06/2015.
 */
public class KeySettings
{

    Context context;
    SharedPreferences prefernc;
    SharedPreferences.Editor editor;

    public KeySettings(Context context)
    {
    this.context=context;
    }

    public  void saveAMtime(String amtiem)
    {
       prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
       editor=prefernc.edit();
       editor.putString("AMtime",amtiem);
       editor.apply();
    }

    public  void savePMtime(String pmtime)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putString("PMtime",pmtime);
        editor.apply();
    }

    public String getAMtime()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getString("AMtime","");
    }

    public String getPMtime()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getString("PMtime","");
    }

    public void saveSearchBusiness(Boolean bool)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("SearchBool",bool);
        editor.apply();
    }

    public Boolean getSearchBusiness()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("SearchBool",false);
    }
}
