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
     *
     * @param cityname
     */
    public  void saveCityName(String cityname)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putString("CityName",cityname);
        editor.apply();
    }

    /**
     *
     * @param bool
     */
    public  void saveCacheImage(Boolean bool)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("CacheImageDownload",true);
        editor.putBoolean("CacheImageDownload", bool);
        editor.apply();

    }


    public Boolean getCacheImage()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("CacheImageDownload",false);
    }

    /**
     * CityName
     * @return
     */
    public String getCityName()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return  prefernc.getString("CityName", "");
    }

    /**
     * Return AM Time
     * @return
     */
    public String getAMtime()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getString("AMtime", "");
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
     *
     * @param initializ
     */
    public void setinitialization(Boolean initializ)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting", Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("initializSetting", initializ);
        editor.apply();
    }


    public Boolean getinitialization()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("initializSetting", false);
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

    public void setUpdateAll(Boolean update)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting", Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("AllUpdate", update);
        editor.apply();
    }


    public Boolean getAllUpdate()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("AllUpdate", false);
    }

    public void saveCollection(Boolean update)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting", Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("collection", update);
        editor.apply();
    }


    public Boolean getCollection()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("collection", false);
    }

    public void saveSubset(Boolean update)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting", Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("Subset", update);
        editor.apply();
    }


    public Boolean getSubset()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("Subset", false);
    }

    public void saveFieldActivity(Boolean update)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting", Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("FieldActivity", update);
        editor.apply();
    }


    public Boolean getFieldActivity()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("FieldActivity", false);
    }


    public void saveArea(Boolean update)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting", Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("Area", update);
        editor.apply();
    }


    public Boolean getArea()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("Area", false);
    }

}
