package com.ariana.shahre_ma.WebServiceGet;

import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;

import com.ariana.shahre_ma.Bookmarks.BookMark;
import com.ariana.shahre_ma.Bookmarks.BookmarkAdapter;
import com.ariana.shahre_ma.Bookmarks.Bookmark_Item;
import com.ariana.shahre_ma.DateBaseSqlite.AddDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.DeleteDataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ariana2 on 6/17/2015.
 */
public class HTTPGetBookMarkJson extends AsyncTask<String,Void,Integer>
{

    ProgressDialog pd;
    Integer errorCode=0;
    private static Context context;//context
    private static   String bookmark;// business api url
    private    Integer MEMberID; // member id
    Integer len=0;
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
            Log.i("Exception",e.toString());
        }
        return result;


    }

    /**
     *
     */
    @Override
    protected void onPostExecute(Integer result) {
        try {
            if(result==1)
            {

                if(len==0 && errorCode==200)
                {
                    BookMark.img_null.setVisibility(View.VISIBLE);
                    BookMark.tv_null.setVisibility(View.VISIBLE);
                }
                    AddDataBaseSqlite adb = new AddDataBaseSqlite(context);
                    DeleteDataBaseSqlite ddb=new DeleteDataBaseSqlite(context);
                    ddb.delete_bookmark();
                     Log.i("len", String.valueOf(len));
                    for (int i = 0; i < len; i++)
                    {
                        Log.i("subsetid", String.valueOf(subsetid[i]));
                        ddb.delete_BusinessId(Id[i]);
                       try
                       {
                           adb.Add_bookmark(Id[i], MEMberID);
                           adb.Add_business(Id[i], market[i], code[i], phone[i], mobile[i], fax[i], email[i], businessowner[i], address[i], description[i], startdate[i], expirationdate[i], inactive[i], subset[i], subsetid[i], latitude[i], longitude[i], areaid[i], area1[i], user[i], 68, userid[i], field1[i], field2[i], field3[i], field4[i], field5[i], field6[i], field7[i], ratecount[i], ratevalue[i], src[i]);
                       }
                       catch (Exception e){}

                    }
                    pd.dismiss();
                    generateData();
                    final BookmarkAdapter adapter = new BookmarkAdapter(context, generateData());
                    BookMark.lv.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

            }
            else
            {


            }
        } catch (Exception e) {
            pd.dismiss();
            Log.i("Exception", e.toString());
        }
    }

    /**
     *
     * @param JSONString
     */
    void parseJSON(String JSONString) {


        try {


            Log.i("JSONBookMark",JSONString);

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
            longitude=new Double[areas.length()];
            latitude=new Double[areas.length()];
            areaid=new Integer[areas.length()];
            area1=new String[areas.length()];
            src=new String[areas.length()];
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

            }

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
    InputStream getStreamFromURL(String urlString, String method){
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
    ///
    public ArrayList<Bookmark_Item> generateData(){
        ArrayList<Bookmark_Item> items = new ArrayList<Bookmark_Item>();
        Query query=new Query(context);
        SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(context);
        Cursor rows=sdb.select_bookmark();
        if(rows.moveToNext())
        {
            do {
                items.add(new Bookmark_Item(query.getNameBusiness(rows.getInt(1)),rows.getInt(1)));
            }while (rows.moveToNext());
        }


        return items;
    }
}
