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
     * State Download Collection
     * @param bool
     */
    public void saveCollectionDownload(Boolean bool)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("CollectionDownload",bool);
        editor.apply();
    }
    public Boolean getCollectionDownload()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("CollectionDownload", false);
    }

    /**
     *State Download Subset
     * @param bool
     */
    public void saveSubsetDownload(Boolean bool)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("SubsetDownload",bool);
        editor.apply();
    }
    public Boolean getSubsetDownload()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("SubsetDownload", false);
    }


    /**
     * State Download City
     * @param bool
     */
    public void saveCityDownload(Boolean bool)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("CityDownload",bool);
        editor.apply();
    }
    public Boolean getCityDownload()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("CityDownload", false);
    }

    /**
     * State Download FieldActivity
     * @param bool
     */
    public void saveFieldActivityDownload(Boolean bool)
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        editor=prefernc.edit();
        editor.putBoolean("FieldActivityDownload",bool);
        editor.apply();
    }
    public Boolean getFieldActivityDownload()
    {
        prefernc=context.getSharedPreferences("com.ariana.shahrema.setting",Context.MODE_PRIVATE);
        return prefernc.getBoolean("FieldActivityDownload", false);
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
