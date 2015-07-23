package com.ariana.shahre_ma.ImageDownload;

import android.content.Context;
import android.util.Log;

import com.ariana.shahre_ma.Settings.KeySettings;

import java.io.File;

/**
 * Created by ariana2 on 6/6/2015.
 */
public class FileCache {



    private File cacheDir;

    public FileCache(Context context){
        //Find the dir to save cached images
        KeySettings setting=new KeySettings(context);

        if(setting.getCacheImage())
        {
            if (android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED))
                cacheDir = new File(android.os.Environment.getExternalStorageDirectory(), "myFolder/image_folder");
            else
                cacheDir = context.getCacheDir();
            if (!cacheDir.exists())
                cacheDir.mkdirs();
        }
        else
        {


                cacheDir = context.getCacheDir();
            if (!cacheDir.exists())
                cacheDir.mkdirs();
        }

    }

    public File getFile(String url){
        //I identify images by hashcode. Not a perfect solution, good for the demo.
        String filename=String.valueOf(url.hashCode());
        //Another possible solution (thanks to grantland)
        //String filename = URLEncoder.encode(url);
        File f = new File(cacheDir, filename);
        return f;

    }

    public void clear(){
        File[] files=cacheDir.listFiles();
        if(files==null)
            return;
        for(File f:files)
            f.delete();
    }
}
