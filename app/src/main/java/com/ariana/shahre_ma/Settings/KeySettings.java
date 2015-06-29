package com.ariana.shahre_ma.Settings;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by hamed on 24/06/2015.
 *
 *
 */
public class KeySettings
{

    Context context;// context
    SharedPreferences prefernc; // keep setting
    SharedPreferences.Editor editor;

    public KeySettings(Context context)//Constructor
    {
    this.context=context;
    }


    /**
     * keep AM Time
     * @param amtiem
     */
    public  void saveAMtime(String amtiem)
    {
       prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
       editor=prefernc.edit();
       editor.putString("AMtime",amtiem);
       editor.apply();
    }

    /**
     * Keep PM Time
     * @param pmtime
     */
    public  void savePMtime(String pmtime)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putString("PMtime",pmtime);
        editor.apply();
    }

    /**
     * Return AM Time
     * @return
     */
    public String getAMtime()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getString("AMtime","");
    }

    /**
     * Return PM Time
     * @return
     */
    public String getPMtime()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getString("PMtime","");
    }

    /**
     * Keep Boolean Search True/False
     * @param bool
     */
    public void saveSearchBusiness(Boolean bool)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("SearchBool",bool);
        editor.apply();
    }

    /**
     * Keep Boolean Sort True/False
     * @param sortvalue
     */
    public void saveSortBusiness(String sortvalue)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting", Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putString("SortBool", sortvalue);
        editor.apply();
    }

    /**
     * Return Boolean Sort True/False
     * @return
     */
    public String getSortBusiness()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getString("SortBool", "");
    }

    /**
     * Return Boolean Search True/False
     * @return
     */
    public Boolean getSearchBusiness()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("SearchBool",false);
    }
}
