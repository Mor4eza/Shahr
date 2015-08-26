package com.ariana.shahre_ma.WebServicePost;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ariana on 7/29/2015.
 */
public class HTTPPostUploadImage extends AsyncTask<String,Integer,Integer>
{
    private ProgressDialog dialog;
    Context context;
    String sourceFileUri="";


    String upLoadServerUri = "";
    String fileName = SrcImage();
    int serverResponseCode = 0;
    HttpURLConnection conn = null;
    DataOutputStream dos = null;
    String lineEnd = "\r\n";
    String twoHyphens = "--";
    String boundary = "*****";
    int bytesRead, bytesAvailable, bufferSize;
    byte[] buffer;
    int maxBufferSize = 1 * 1024 * 1024;






    public HTTPPostUploadImage(Context context)
    {
        this.context=context;
    }

    public void SetImage(Integer id,Integer type)
    {
        upLoadServerUri="http://test.shahrma.com/api/ApiTakeImage?id="+id+"&type="+type;
    }

    private String URL()
    {
        Log.i("ImageUrl",upLoadServerUri);
        return  upLoadServerUri;
    }

    public void setFileImage(String src)
    {
        sourceFileUri=src;
    }

    private String SrcImage()
    {
        return sourceFileUri;
    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(context);
        dialog.setMessage("در حال ارسال");
        dialog.setIndeterminate(false);
        dialog.setCancelable(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setProgress(0);
        dialog.show();
    }

    /**
     *
     * @param params
     * @return
     */
    @Override
    protected Integer doInBackground(String... params)
    {
        Integer result=0;
        File sourceFile = new File(SrcImage());
        if (!sourceFile.isFile()) {
            Log.e("uploadFile", "File not exist");
            return 0;
        }
        try
        { // open a URL connection to the Servlet
            FileInputStream fileInputStream = new FileInputStream(sourceFile);
            URL url = new URL(URL());
            conn = (HttpURLConnection) url.openConnection(); // Open a HTTP  connection to  the URL
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", fileName);
            dos = new DataOutputStream(conn.getOutputStream());


            Log.i("linedEnd+towhyphend",String.valueOf(twoHyphens + boundary + twoHyphens + lineEnd));
            Log.i("linedEnd",String.valueOf(lineEnd));



            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""+ fileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);

            bytesAvailable = fileInputStream.available(); // create a buffer of  maximum size

            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }


            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // Responses from the server (code and message)
            serverResponseCode = conn.getResponseCode();
            String serverResponseMessage = conn.getResponseMessage();


            Log.i("uploadFile", "HTTP Response is : " + serverResponseMessage + ": " + serverResponseCode);
            if(serverResponseCode == 200)
            {
                Log.i("File Upload ","Complete");
                result=1;
            }

            //close the streams //
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            result=0;
            ex.printStackTrace();
            Log.e("Upload file to server", "error: " + ex.getMessage(), ex);
        } catch (Exception e) {
            result=0;
            e.printStackTrace();
            Log.e("Upload file to server ","error"+ e.getMessage());
        }
        return result;
    }


    @Override
    protected void onProgressUpdate(Integer... progress)
    {
        dialog.setProgress(progress[0]);
    }

    /**
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if(result==1)
        {
            dialog.dismiss();

            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("پیام");
            alertDialog.setMessage("عکس ثبت شد .");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            alertDialog.show();

        }
        else
        {
            dialog.dismiss();

            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("عکس ثبت نشد . دوباره امتحان کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            alertDialog.show();


        }
    }
}
