package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.Cards.Comment_Card_Adapter;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyBusiness.Discount;
import com.ariana.shahre_ma.MyBusiness.discount_Adapter;
import com.ariana.shahre_ma.job_details.Job_details_comment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ariana on 7/12/2015.
 */
public class HTTPGetDisCountJson extends AsyncTask<String,Void,Integer> {
    private static Context context;



    Integer Id[];
    String description[];
    String startdate[];
    String image[];
    String text[];
    String percent[];
    String expirationdate[];
    Integer businessid[];

    Integer len;
    Integer memberid;
    ProgressDialog pd;

    FieldClass fc=new FieldClass();

    /**
     *
     * @param memberid
     */
    public void seturl_DisCount(Integer memberid) {

        this.memberid=memberid;
    }

    /**
     *
     * @return
     */
    private String geturl_discount()
    {

        String Result="";
        Result="http://test.shahrma.com/api/apiGiveDisCount?memberid="+memberid;
        Log.i("URLdiscount",Result);
        return  Result;
    }

    /**
     *
     * @param c
     */
    public HTTPGetDisCountJson(Context c) {
        context = c;

    }

    /**
     *
     */
    @Override
    protected void onPreExecute() {
       super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("دریافت..." );
        pd.setCancelable(false);
        pd.show();
    }

    /**
     *
     * @param args
     * @return
     */
    protected Integer doInBackground(String... args) {
        Integer result=0;
        try {


            InputStream jsonStream = getStreamFromURL(geturl_discount(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            result=1;

        } catch (Exception e) {
            result=0;
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
        }
        return result;


    }

    /**
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Integer result) {
        if(result==1) {
            try {


                DataBaseSqlite dbs = new DataBaseSqlite(context);

                dbs.delete_DisCountMember();
                for (int i = 0; i < len; i++) {
                    dbs.Add_DisCountMember(Id[i], text[i], image[i], startdate[i], expirationdate[i], description[i], percent[i], businessid[i]);
                }
                pd.dismiss();
                Discount dis=new Discount();
                discount_Adapter adapter = new discount_Adapter(context,dis.generateData());
                Discount.listView.setAdapter(adapter);
                Discount.listView.deferNotifyDataSetChanged();
           } catch (Exception e) {
                pd.dismiss();
               // Toast.makeText(context.getApplicationContext(), "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
            }
        }
        else
        {
            pd.dismiss();
        }
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {


        try {

            Log.i("JSONdiscountMember", JSONString);
            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];
            text=new String[areas.length()];
            image=new String[areas.length()];
            startdate=new String[areas.length()];
            expirationdate=new String[areas.length()];
            description=new String[areas.length()];
            percent=new String[areas.length()];
            businessid=new Integer[areas.length()];
            len=areas.length();

            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                Id[i]=area.getInt("Id");
                text[i]=area.getString("Text");
                image[i]=area.getString("Image");
                startdate[i]=area.getString("Startdate");
                expirationdate[i]=area.getString("ExpirationDate");
                description[i]=area.getString("Description");
                percent[i]=area.getString("Percent");
                businessid[i]=area.getInt("BusinessId");


            }

        } catch (JSONException e) {
            // Toast.makeText(activity,e.toString(), Toast.LENGTH_LONG).show();
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
}
