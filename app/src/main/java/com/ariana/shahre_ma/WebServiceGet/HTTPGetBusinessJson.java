package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.Jobs_List;
import com.ariana.shahre_ma.Settings.KeySettings;

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
    FieldClass fc=new FieldClass();
    Query query;
    FieldDataBusiness fdb=new FieldDataBusiness();
    private  String url_Business;
    ProgressDialog pd;

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

    Integer discountid[];
    String discounttext[];
    String discountimage[];
    String discountstartdate[];
    String discountexpirationdate[];
    String discountdescription[];
    String discountpercent[];
    Integer discountbusinessid[];

    Integer likediscount[];
    Integer dislikediscount[];


    Integer len=0;
    Integer i=0;


    /**
     *
     * @param SubsetID
     */
    public   void SetUrl_business(Integer SubsetID,Integer cityname)
    {

        url_Business="http://test.shahrma.com/api/ApiGiveBusiness?subsetId="+SubsetID+"&cityid="+cityname;
        Log.i("url_Business",url_Business);

    }

    /**
     *
     * @return
     */
    private String GetUrl_business()
    {
        return  url_Business;
    }

    /**
     *
     * @param c
     */
    public HTTPGetBusinessJson(Context c) {
        context = c;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("دریافت...");
        pd.setCancelable(false);
        pd.show();
    }

    /**
     *
     * @param args
     * @return
     */
    protected String doInBackground(String... args) {
        try {

                InputStream jsonStream = getStreamFromURL(GetUrl_business(), "GET");
                String jsonString = streamToString(jsonStream);
                parseJSON(jsonString);
                onPostExecute();


        } catch (Exception e) {
            // Toast.makeText(getApplicationContext(),"do in background", Toast.LENGTH_LONG).show();
        }
        return null;


    }

    /**
     *
     */
    protected void onPostExecute() {
        try {

            Integer count=0;
            Integer cityid=0;
            Integer idsubset=0;
          //  Toast.makeText(context,market[0], Toast.LENGTH_LONG).show();
            DataBaseSqlite dbs = new DataBaseSqlite(context);
            KeySettings setting=new KeySettings(context);
            query=new Query(context);
            cityid=query.getCityId(setting.getCityName());
            idsubset=fc.GetSubsetId();
            dbs.delete_Business(cityid, idsubset);

            if(len==0) {
                //  Toast.makeText(get, "فروشگاه ثبت نشده", Toast.LENGTH_LONG).show();
                Log.i("Count Business : ","فروشگاه ثبت نشد");
                pd.dismiss();
            }

            else {




                for (int i = 0; i <len; i++)
                {
                    dbs.delete_DisCount(discountid[i]);
                    if(discountid[i]==0) {
                        Log.i("ifbusiness","0");
                    }
                    else
                    {
                        Log.i("elsebusiness", "i>0");
                        dbs.Add_DisCount(discountid[i], discounttext[i], discountimage[i], discountstartdate[i], discountexpirationdate[i], discountdescription[i], discountpercent[i], discountbusinessid[i],likediscount[i],dislikediscount[i]);
                    }
                    //dbs.Add_LikeDisCount(1,166,Id[i],likediscount[i],dislikediscount[i]);
                    dbs.Add_business(Id[i], market[i], code[i], phone[i], mobile[i], fax[i], email[i], businessowner[i], address[i], description[i], startdate[i], expirationdate[i], inactive[i], subset[i], subsetid[i], longitude[i], latitude[i], areaid[i], area1[i], user[i],cityid, userid[i], field1[i], field2[i], field3[i], field4[i], field5[i], field6[i], field7[i], ratecount[i], ratevalue[i]);

                }

                fc.SetCount_Business(query.getCountBusiness(query.getsubsetID(fc.GetSelected_job())));
                Intent intent = new Intent(this.context, Jobs_List.class);
                this.context.startActivity(intent);
                Log.i("Count Business : ", "دریافت ثبت شده ها");
                pd.dismiss();
            }

        } catch (Exception e) {
            pd.dismiss();
            Log.e("ExceptionBusinessJson",e.toString());
        }
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {

        try {

            Log.i("JSONString",JSONString);
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
            
            discountid = new Integer[areas.length()];
            discounttext = new String[areas.length()];
            discountimage=new String[areas.length()];
            discountstartdate=new String[areas.length()];
            discountexpirationdate=new String[areas.length()];
            discountdescription=new String[areas.length()];
            discountpercent=new String[areas.length()];
            discountbusinessid=new Integer[areas.length()];

            likediscount=new Integer[areas.length()];
            dislikediscount=new Integer[areas.length()];

            len=areas.length();
            for (int i = 0; i < areas.length(); i++) {


                JSONObject area = areas.getJSONObject(i);
                address[i]=area.getString("Address");
                area1[i]=area.getString("Area");
                areaid[i]=area.getInt("AreaId");
                businessowner[i]=area.getString("BusinessOwner");
                code[i]=area.getString("Code");
                description[i] = area.getString("Description");
                email[i] = area.getString("Email");
                expirationdate[i]=area.getString("ExpirationDate");
                fax[i]=area.getString("Fax");
                field1[i]=area.getInt("Field1");
                field2[i]=area.getInt("Field2");
                field3[i] = area.getInt("Field3");
                field4[i]=area.getInt("Field4");
                field5[i]=area.getInt("Field5");
                field6[i] = area.getInt("Field6");
                field7[i] = area.getInt("Field7");
                Id[i]=area.getInt("Id");
                inactive[i]=area.getString("Inactive");
                latitude[i]=area.getString("Latitude");
                longitude[i]=area.getString("Longitude");
                market[i]=area.getString("Market");
                mobile[i]=area.getString("Mobile");
                phone[i]=area.getString("Phone");
                startdate[i]=area.getString("Startdate");
                subset[i]=area.getString("Subset");
                subsetid[i]=area.getInt("SubsetId");
                user[i]=area.getString("User");
                userid[i]=area.getInt("UserId");


                ratecount[i]=area.getInt("RateCount");
                ratevalue[i]=area.getDouble("RateAverage");


                discountid[i]=area.getInt("DiscountId");
                discounttext[i]=area.getString("DiscountText");
                discountimage[i]=area.getString("DiscountImage");
                discountstartdate[i]=area.getString("DiscountStartdate");
                discountexpirationdate[i]=area.getString("DiscountExpirationDate");
                discountpercent[i]=area.getString("DiscountPercent");
                discountdescription[i]=area.getString("DiscountDescription");
                discountbusinessid[i]=area.getInt("Id");

                likediscount[i]=area.getInt("DiscountLike");
                dislikediscount[i]=area.getInt("DiscountDislike");



               /* fdb.SetIdBusiness(Id[i]);
                fdb.SetRateBusiness(area.getString(""));
                fdb.SetAddressBusiness("");
                fdb.SetMarketBusiness(market);
                fdb.SetPhoneBusiness(phone);*/

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

}
