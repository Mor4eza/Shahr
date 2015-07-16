package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.ariana.shahre_ma.BookMark;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ariana2 on 6/17/2015.
 */
public class HTTPGetBookMarkJson
        extends AsyncTask<String,Void,Integer>
{

    ProgressDialog pd;
    private static Context context;//context

    private static   String bookmark;// business api url

    private    Integer MEMberID; // member id
    Integer Id[];

    Integer len=0;


    /**
     * Set url Member
     * @param memberid
     */
    public   void SetUrl_MemberId(Integer memberid)
    {
        this.MEMberID=memberid;
        bookmark="http://test.shahrma.com/api/ApiGiveBookmark?6&memberId="+memberid;
        Log.i("URLurl",bookmark);
    }

    /**
     * Return url bookmark
     * @return
     */
    private String GetUrl_Bookmark()
    {
        return  bookmark;
    }

    public HTTPGetBookMarkJson(Context c) //Constructor
    {
        context = c;
    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("دریافت اطلاعات...");
        pd.setCancelable(false);
        pd.show();
    }
    /**
     * doInBackground
     * @param args
     * @return
     */
    protected Integer doInBackground(String... args) {
        Integer result=0;
        try {


            InputStream jsonStream = getStreamFromURL(GetUrl_Bookmark(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
             result=1;

        } catch (Exception e) {
             result=0;
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
            Log.i("Exception",e.toString());
        }
        return result;


    }

    /**
     *
     */
    @Override
    protected void onPostExecute(Integer result) {
        //onPostExecute(result);
        if(result==1) {
            try {

                //  Toast.makeText(context,market[0], Toast.LENGTH_LONG).show();
                DataBaseSqlite dbs = new DataBaseSqlite(context);


                dbs.delete_bookmark();
                for (int i = 0; i < len; i++) {
                    dbs.Add_bookmark(Id[i], MEMberID);

                }
                pd.dismiss();
                BookMark book=new BookMark();
               // ArrayAdapter adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,book.getbookmark());
               // BookMark.lv.setAdapter(adapter);
               // BookMark.lv.deferNotifyDataSetChanged();
                book.bookmark();

            } catch (Exception e) {
                pd.dismiss();
                Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
                Log.i("Exception", e.toString());
            }
        }
        else
        {

        }
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {


        try {


            Log.i("JSONBookmark",JSONString);
            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];

            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);

                Id[i]=area.getInt("Id");

            }

        } catch (JSONException e) {
            // Toast.makeText(getApplicationContext()," parse Json", Toast.LENGTH_LONG).show();
            Log.i("JSONException",e.toString());
        }
    }


    /**
     *
     * @param urlString
     * @param method
     * @return
     */
    InputStream getStreamFromURL(String urlString, String method) {
        try {
            URL url = new URL(urlString);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            huc.setReadTimeout(10000);
            huc.setConnectTimeout(15000);
            huc.setRequestMethod(method);
            huc.setDoInput(true);

            huc.connect();

            return huc.getInputStream();
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @param is
     * @return
     */
    String streamToString(InputStream is) {
        String result = "";
        String line = null;

        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                result = line + "\n";
            }
        } catch (Exception e) {
        }


        return result;
    }
    ///
}
