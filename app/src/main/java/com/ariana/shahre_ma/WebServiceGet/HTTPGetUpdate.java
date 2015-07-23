package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.ariana.shahre_ma.BuildConfig;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by ariana2 on 7/21/2015.
 */
 public class HTTPGetUpdate extends AsyncTask<String, String, String> {


    Context context;
    // Show Progress bar before downloading Music
    public HTTPGetUpdate(Context context)
    {
        this.context=context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        // Shows Progress Bar Dialog and then call doInBackground method

    }

    // Download Music File from Internet
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            URL url = new URL(f_url[0]);
            URLConnection conection = url.openConnection();
            conection.connect();
            // Get Music file length
            int lenghtOfFile = conection.getContentLength();
            // input stream to read file - with 8k buffer
            InputStream input = new BufferedInputStream(url.openStream(),10*1024);
            // Output stream to write file in SD card
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/myFolder/ls.cfg");
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                // Publish the progress which triggers onProgressUpdate method
                publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                // Write data to file
                output.write(data, 0, count);
            }
            // Flush output
            output.flush();
            // Close streams
            output.close();
            input.close();
        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String file_url) {
        // Dismiss the dialog after the Music file was downloaded

        int versionCode = BuildConfig.VERSION_CODE;
       if  (Integer.valueOf(readLast())>versionCode){
           HTTPGetUpdateApk apk = new HTTPGetUpdateApk(context);
           apk.execute("http://shahrma.com/app/apk_update/app-debug.zip");
           Log.i("Downloaded",readLast());

       }



    }

    public String readLast(){
        String ret = "";
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/myFolder");
        File file = new File(dir, "ls.cfg");

        try {

            FileInputStream inputStream = new FileInputStream(file);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }


        } catch (FileNotFoundException e) {
            Log.v("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.v("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

}

