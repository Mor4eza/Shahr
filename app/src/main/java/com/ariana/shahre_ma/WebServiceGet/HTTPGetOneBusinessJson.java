package com.ariana.shahre_ma.WebServiceGet;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.Jobs_List;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.job_details.Job_details;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana on 8/26/2015.
 */
public class HTTPGetOneBusinessJson extends AsyncTask<String,Void,Integer>
{

    private static Context context;
    FieldClass fc=new FieldClass();
    Query query;
    Integer errorCode=0;
    private  String url_Business;
    ProgressDialog pd;




    Integer Id=0;
    String market="";
    String code="";
    String phone="";
    String mobile="";
    String fax="";
    String email="";
    String businessowner="";
    String address="";
    String description="";
    String startdate="";
    String expirationdate="";
    String inactive="";
    String subset="";
    String src="";
    Integer subsetid=0;
    Double longitude=0.0;
    Double latitude=0.0;
    Integer areaid=0;
    String area1="";
    String user="";
    Integer userid=0;
    Integer field1=0;
    Integer field2=0;
    Integer field3=0;
    Integer field4=0;
    Integer field5=0;
    Integer field6=0;
    Integer field7=0;
    Integer ratecount=0;
    Double ratevalue=0.0;

    Integer discountid=0;
    String discounttext="";
    String discountimage="";
    String discountstartdate="";
    String discountexpirationdate="";
    String discountdescription="";
    String discountpercent="";
    Integer discountbusinessid=0;

    Integer likediscount=0;
    Integer dislikediscount=0;
    Integer Notification=0;

    Integer len=0;
    Integer i=0;


    public   void SetUrl_business(Integer businessid,Integer showNotification)
    {

        Notification=showNotification;
        url_Business="http://test.shahrma.com/api/ApiGiveoneBusiness?BusinessId="+businessid;
        Log.i("url_Business", url_Business);

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
    public HTTPGetOneBusinessJson(Context c) {
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
    protected Integer doInBackground(String... args) {
        Integer result=0;
        try {

            InputStream jsonStream = getStreamFromURL(GetUrl_business(), "GET");
            String jsonString = streamToString(jsonStream);
            parseJSON(jsonString);
            result=1;
        } catch (Exception e) {
            result=0;
        }
        return result;


    }

    /**
     *
     * @param result
     */
    @Override
    protected void onPostExecute(Integer result) {
        try {

            if(result==1) {
                KeySettings setting = new KeySettings(context);
                query = new Query(context);


                Integer cityid = 0;
                Integer idsubset = 0;

                DataBaseSqlite dbs = new DataBaseSqlite(context);


                cityid = query.getCityId(setting.getCityName());
                idsubset = fc.GetSubsetId();


                Log.i("count", String.valueOf(len));
                if (len == 0) {
                    pd.dismiss();

                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("هشدار");
                    alertDialog.setMessage("اطلاعات دریافت نشد . دوباره امتحان کنید");
                    alertDialog.setButton("خب", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();

                }
                else
                {





                    dbs.delete_BusinessId(Id);
                    dbs.delete_DisCount(discountid);
                        if (discountid == 0) {
                            Log.i("ifbusiness", "0");
                        } else {
                            Log.i("elsebusiness", "i>0");
                            dbs.Add_DisCount(discountid, discounttext, discountimage, discountstartdate, discountexpirationdate, discountdescription, discountpercent, discountbusinessid, likediscount, dislikediscount);
                        }

                        dbs.Add_business(Id, market, code, phone, mobile, fax, email, businessowner, address, description, startdate, expirationdate, inactive, subset, subsetid, latitude, longitude, areaid, area1, user, cityid, userid, field1, field2, field3, field4, field5, field6, field7, ratecount, ratevalue, src);


                    fc.SetCount_Business(query.getCountBusiness(query.getsubsetID(fc.GetSelected_job())));
                    pd.dismiss();


                    fc.SetShowNotification(true);
                    fc.SetShowNotificationId(Notification);
                    fc.SetMarket_Business(market);
                    fc.SetBusiness_Id(Id);
                    Intent i = new Intent(context, Job_details.class);
                    context.startActivity(i);
                }
            }
            else
            {
                if (len == 0) {
                    pd.dismiss();

                    AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                    alertDialog.setTitle("هشدار");
                    alertDialog.setMessage("دریافت نشد . دوباره امتحان کنید");
                    alertDialog.setButton("خب", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alertDialog.show();

                }
            }

        } catch (Exception e)
        {
            pd.dismiss();

            if(len==0)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("مشاغلی برای این شهر و این زیرمجموعه پیدا نشد!!!");
                alertDialog.setButton("خب", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
            else
            {
                AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("دوباره امتحان کنید");
                alertDialog.setButton("خب", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }
            Log.e("ExceptionBusinessJson",e.toString());
        }
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {

        try {

            Log.i("JSONString", JSONString);

                JSONObject area = new JSONObject(JSONString);
                 len=area.length();
                address=area.getString("Address");
                area1=area.getString("Area");
                areaid=area.getInt("AreaId");
                businessowner=area.getString("BusinessOwner");
                code=area.getString("Code");
                description = area.getString("Description");
                email = area.getString("Email");
                expirationdate=area.getString("ExpirationDate");
                fax=area.getString("Fax");
                field1=area.getInt("Field1");
                field2=area.getInt("Field2");
                field3 = area.getInt("Field3");
                field4=area.getInt("Field4");
                field5=area.getInt("Field5");
                field6 = area.getInt("Field6");
                field7 = area.getInt("Field7");
                Id=area.getInt("Id");
                inactive=area.getString("Inactive");
                latitude=Double.valueOf(area.getString("Latitude"));
                longitude=Double.valueOf(area.getString("Longitude"));
                market=area.getString("Market");
                mobile=area.getString("Mobile");
                phone=area.getString("Phone");
                startdate=area.getString("Startdate");
                subset=area.getString("Subset");
                subsetid=area.getInt("SubsetId");
                user=area.getString("User");
                userid=area.getInt("UserId");
                src=area.getString("Src");


                ratecount=area.getInt("RateCount");
                ratevalue=area.getDouble("RateAverage");


                discountid=area.getInt("DiscountId");
                discounttext=area.getString("DiscountText");
                discountimage=area.getString("DiscountImage");
                discountstartdate=area.getString("DiscountStartdate");
                discountexpirationdate=area.getString("DiscountExpirationDate");
                discountpercent=area.getString("DiscountPercent");
                discountdescription=area.getString("DiscountDescription");
                discountbusinessid=area.getInt("Id");

                likediscount=area.getInt("DiscountLike");
                dislikediscount=area.getInt("DiscountDislike");



        } catch (JSONException e) {
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
            errorCode=huc.getResponseCode();
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
