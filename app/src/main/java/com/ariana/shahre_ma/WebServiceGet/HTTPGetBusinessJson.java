package com.ariana.shahre_ma.WebServiceGet;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.BusinessSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.CollectionSqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ariana2 on 6/6/2015.
 */
public class HTTPGetBusinessJson extends AsyncTask<String, String, String>
{

    private static Context context;

    private static final String url_Business="http://test.shahrma.com/api/test";

    Integer Id[];
    String market[];
    String code[];
    String phone[];
    String mobile[];
    String fax[];
    String email[];
    String businessowner[];
    String address[];
    String description[];
    String startdate[];
    String expirationdate[];
    String inactive[];
    String subset[];
    Integer subsetid[];
    String longitude[];
    String latitude[];
    Integer areaid[];
    String area1[];
    String user[];
    Integer userid[];
    Integer field1[];
    Integer field2[];
    Integer field3[];
    Integer field4[];
    Integer field5[];
    Integer field6[];
    Integer field7[];
    Integer ratecount[];
    Double ratevalue[];

    Integer len;

    public HTTPGetBusinessJson(Context c) {
        context = c;
    }
    protected String doInBackground(String... args) {
        try {


            InputStream jsonStream = getStreamFromURL(url_Business, "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            onPostExecute();
        } catch (Exception e) {
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
        }
        return null;


    }

    protected void onPostExecute() {
        try {

            Toast.makeText(context,market[0], Toast.LENGTH_LONG).show();
            BusinessSqlite businSql = new BusinessSqlite(context);


            for (int i = 0; i <len; i++)
            {
                businSql.Add(Id[i],market[i], code[i],phone[i],mobile[i],fax[i], email[i],businessowner[i],address[i],description[i],startdate[i], expirationdate[i], inactive[i],subset[i],  subsetid[i], longitude[i], latitude[i],  areaid[i], area1[i], user[i],userid[i],field1[i],field2[i],field3[i],field4[i],field5[i],field6[i],field7[i],ratecount[i],ratevalue[i]);

            }
        } catch (Exception e) {
            Toast.makeText(context, "در پایگاه داده ذخیره نشد", Toast.LENGTH_LONG).show();
        }
    }


    void parseJSON(String JSONString) {

        Integer ii = 0;
        try {

            JSONArray areas = new JSONArray(JSONString);

            Id=new Integer[areas.length()];
            market=new String[areas.length()];
            code=new String[areas.length()];
            phone=new String[areas.length()];
            mobile=new String[areas.length()];
            fax=new String[areas.length()];
            email=new String[areas.length()];
            businessowner=new String[areas.length()];
            address=new String[areas.length()];
            description=new String[areas.length()];
            startdate=new String[areas.length()];
            expirationdate=new String[areas.length()];
            inactive=new String[areas.length()];
            subset=new String[areas.length()];
            subsetid=new Integer[areas.length()];
            longitude=new String[areas.length()];
            latitude=new String[areas.length()];
            areaid=new Integer[areas.length()];
            area1=new String[areas.length()];
            user=new String[areas.length()];
            userid=new Integer[areas.length()];
           field1=new Integer[areas.length()];
            field2=new Integer[areas.length()];
            field3=new Integer[areas.length()];
            field4=new Integer[areas.length()];
            field5=new Integer[areas.length()];
            field6=new Integer[areas.length()];
            field7=new Integer[areas.length()];
            ratecount=new Integer[areas.length()];
            ratevalue=new Double[areas.length()];
            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {

                JSONObject area = areas.getJSONObject(i);
                Id[i]=area.getInt("Id");
                market[i]=area.getString("Market");
                code[i]=area.getString("Code");
                phone[i]=area.getString("Phone");
                mobile[i]=area.getString("Mobile");
                fax[i]=area.getString("Fax");
                email[i]=area.getString("Email");
                businessowner[i]=area.getString("BusinessOwner");
                address[i]=area.getString("Address");
                description[i]=area.getString("Description");
                startdate[i]=area.getString("Startdate");
                expirationdate[i]=area.getString("ExpirationDate");
                inactive[i]=area.getString("Inactive");
                subset[i]=area.getString("Subset");
                subsetid[i]=area.getInt("SubsetId");
                longitude[i]=area.getString("Longitude");
                latitude[i]=area.getString("Latitude");
                areaid[i]=area.getInt("AreaId");
                area1[i]=area.getString("Area");
                user[i]=area.getString("User");
                userid[i]=area.getInt("UserId");

               /* field1[i]=area.getInt("Field1");
                field2[i]=area.getInt("Field2");
                field3[i]=area.getInt("Field3");
                field4[i]=area.getInt("Field4");
                field5[i]=area.getInt("Field5");
                field6[i]=area.getInt("Field6");
                field7[i]=area.getInt("Field7");*/
                /*ratecount[i]=area.getInt("Field6");
                ratevalue[i]=area.getDouble("Field7");*/


            }

        } catch (JSONException e) {
            // Toast.makeText(getApplicationContext()," parse Json", Toast.LENGTH_LONG).show();
        }
    }



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

    public  String GetM()
    {
        String ss;
        ss=market[0];
        return ss ;
    }
}
