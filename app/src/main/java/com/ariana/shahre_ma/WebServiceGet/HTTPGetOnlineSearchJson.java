package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.Jobs_List;
import com.ariana.shahre_ma.Search.SearchListActivity;
import com.ariana.shahre_ma.Settings.KeySettings;

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
 * Created by ariana on 7/31/2015.
 */
public class HTTPGetOnlineSearchJson extends AsyncTask<String,Void,Integer>
{
    ProgressDialog pd;
    String urlsearch="";
    Context context;
    FieldClass fc=new FieldClass();
    Query query;
    Integer errorCode=0;
    FieldDataBusiness fdb=new FieldDataBusiness();

    private List<Integer> selectId=new ArrayList<>();
    private  List<Integer> selectDiscountId=new ArrayList<>();
    private  List<Integer> selectSubsetId=new ArrayList<>();
    private  List<Double>  selectLongtiude=new ArrayList<>();
    private  List<Double>  selectLatitude=new ArrayList<>();
    private  List<Double>  selectRate=new ArrayList<>();
    private  List<String>  selectPhone=new ArrayList<String>();
    private  List<String>  selectSrc=new ArrayList<String>();
    private  List<String>  selectMobile=new ArrayList<String>();
    private  List<String>  selectAddress=new ArrayList<>();
    private  List<String>  selectMarketName=new ArrayList<String>();

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
    String src[];
    Integer subsetid[];
    Double longitude[];
    Double latitude[];
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


    public HTTPGetOnlineSearchJson(Context context)
    {
        this.context=context;
    }

    public void SetValueSearch(String value,Integer cityid)
    {
       urlsearch="http://test.shahrma.com/api/ApiSearch?search="+value+"&cityId="+cityid;
    }

    private String getUrlsearch()
    {
        return urlsearch;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd = new ProgressDialog(context);
        pd.setMessage("درحال دریافت نتایج جستجو...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected Integer doInBackground(String... params) {
        Integer result=0;
        try
        {
            Log.i("URL",getUrlsearch());
            InputStream inputStream=getStreamFromURL(getUrlsearch(),"GET");
            String json=streamToString(inputStream);
            ParsJSON(json);
            result=1;
        }
        catch (Exception e)
        {
            result=0;
        }
        return result;
    }

     void ParsJSON(String JsonString)
        {

            try {

                Log.i("JSONString", JsonString);
                JSONArray areas = new JSONArray(JsonString);

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
                src=new String[areas.length()];
                subsetid=new Integer[areas.length()];
                longitude=new Double[areas.length()];
                latitude=new Double[areas.length()];
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
                    latitude[i]=Double.valueOf(area.getString("Latitude"));
                    longitude[i]=Double.valueOf(area.getString("Longitude"));
                    market[i]=area.getString("Market");
                    mobile[i]=area.getString("Mobile");
                    phone[i]=area.getString("Phone");
                    startdate[i]=area.getString("Startdate");
                    subset[i]=area.getString("Subset");
                    subsetid[i]=area.getInt("SubsetId");
                    user[i]=area.getString("User");
                    userid[i]=area.getInt("UserId");
                    src[i]=area.getString("Src");

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



                    selectId.add(area.getInt("Id"));
                    selectDiscountId.add(area.getInt("DiscountId"));
                    selectSubsetId.add(area.getInt("SubsetId"));
                    selectLatitude.add(Double.valueOf(area.getString("Latitude")));
                    selectLongtiude.add(Double.valueOf(area.getString("Longitude")));
                    selectAddress.add(area.getString("Address"));
                    selectMarketName.add(area.getString("Market"));
                    selectSrc.add(area.getString("Src"));
                    selectPhone.add(area.getString("Phone"));
                    selectMobile.add(area.getString("Mobile"));
                    selectRate.add(area.getDouble("RateAverage"));


                }

                fdb.SetIdBusiness(selectId);
                fdb.SetDisCountId(selectDiscountId);
                fdb.SetSubsetId(selectSubsetId);
                fdb.SetLatitudeBusiness(selectLatitude);
                fdb.SetLongtiudeBusiness(selectLongtiude);
                fdb.SetRateBusiness(selectRate);
                fdb.SetAddressBusiness(selectAddress);
                fdb.SetSrc(selectSrc);
                fdb.SetMarketBusiness(selectMarketName);
                fdb.SetPhoneBusiness(selectPhone);
                fdb.SetMobileBusiness(selectMobile);


            } catch (JSONException e) {
                Log.i("JSONException",e.toString());
            }
        }

     InputStream getStreamFromURL(String urlString,String method)
        {
            try
            {
                URL url=new URL(urlString);
                HttpURLConnection huc=(HttpURLConnection) url.openConnection();
                huc.setReadTimeout(10000);
                huc.setConnectTimeout(15000);
                huc.setRequestMethod(method);
                huc.setDoInput(true);
                huc.connect();
                errorCode=huc.getResponseCode();
                return huc.getInputStream();
            }
            catch (Exception e)
            {
                Log.e("getStreamFromURL",e.toString());
                return null;
            }
        }

    String streamToString(InputStream is)
        {
            String result="";
            String line=null;

            try
            {
                BufferedReader br=new BufferedReader(new InputStreamReader(is));
                while((line=br.readLine()) !=null)
                {
                    result=line+"\n";
                }
            }
            catch (Exception e)
            {
                Log.e("streamToString",e.toString());
            }
            return  result;
        }

    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);

        try {

            KeySettings setting=new KeySettings(context);
            query=new Query(context);


            Integer cityid=0;
            Integer idsubset=0;

            DataBaseSqlite dbs = new DataBaseSqlite(context);


            cityid=query.getCityId(setting.getCityName());
            idsubset=fc.GetSubsetId();



            if(len==0)
            {
                //  Toast.makeText(get, "فروشگاه ثبت نشده", Toast.LENGTH_LONG).show();
                Log.i("Count Business : ", "فروشگاه ثبت نشد");
                pd.dismiss();
                Toast.makeText(context,"نتیجه ای یافت نشد",Toast.LENGTH_LONG).show();
            }

            else {

                fc.SetSearchOnline(true);
                Intent intent = new Intent(this.context, SearchListActivity.class);
                this.context.startActivity(intent);
                Log.i("Count Business : ", "دریافت ثبت شده ها");
                pd.dismiss();

                for (int i = 0; i <len; i++)
                {
                    dbs.delete_BusinessId(Id[i]);
                    dbs.delete_DisCount(discountid[i]);
                    if(discountid[i]==0) {
                        Log.i("ifbusiness","0");
                    }
                    else
                    {
                        Log.i("elsebusiness", "i>0");
                        dbs.Add_DisCount(discountid[i], discounttext[i], discountimage[i], discountstartdate[i], discountexpirationdate[i], discountdescription[i], discountpercent[i], discountbusinessid[i],likediscount[i],dislikediscount[i]);
                    }
                    dbs.Add_business(Id[i], market[i], code[i], phone[i], mobile[i], fax[i], email[i], businessowner[i], address[i], description[i], startdate[i], expirationdate[i], inactive[i], subset[i], subsetid[i],latitude[i], longitude[i], areaid[i], area1[i], user[i],cityid, userid[i], field1[i], field2[i], field3[i], field4[i], field5[i], field6[i], field7[i], ratecount[i], ratevalue[i],src[i]);

                }

                fc.SetCount_Business(query.getCountBusiness(query.getsubsetID(fc.GetSelected_job())));
            }

        } catch (Exception e) {

            Log.e("ExceptionBusinessJson",e.toString());
        }
    }
}
