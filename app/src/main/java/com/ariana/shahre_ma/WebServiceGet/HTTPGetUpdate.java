package com.ariana.shahre_ma.WebServiceGet;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import com.ariana.shahre_ma.BuildConfig;
import com.ariana.shahre_ma.R;

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
            OutputStream output = new FileOutputStream(Environment.getExternalStorageDirectory().getPath()+"/Shahre_Ma/ls.cfg");
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
    try {


        int versionCode = BuildConfig.VERSION_CODE;
        if (Integer.valueOf(readLast()) > versionCode) {

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notify = new Notification(R.mipmap.ic_launcher, "آپدیت جدید!", System.currentTimeMillis());
            notify.flags |= Notification.FLAG_AUTO_CANCEL;
            notify.flags |= Notification.FLAG_SHOW_LIGHTS;
            notify.defaults |= Notification.DEFAULT_LIGHTS;
            notify.defaults |= Notification.DEFAULT_SOUND;
            CharSequence title = "آپدیت جدید!";
            CharSequence detils = "برای دانلود نسخه جدید شهر ما " + "کلیک کنید!";

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("bazaar://details?id=" + "com.ariana.shahre_ma"));
            //intent.setPackage("com.farsitel.bazaar");
            PendingIntent pend = PendingIntent.getActivity(context, 0, intent, 0);
            notify.setLatestEventInfo(context, title, detils, pend);
            nm.notify(0, notify);
            Log.i("Downloaded", readLast());

        }
    }catch (Exception e){

    }


    }

    public String readLast(){
        String ret = "";
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/Shahre_ma");
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

