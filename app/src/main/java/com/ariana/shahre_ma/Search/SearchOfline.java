package com.ariana.shahre_ma.Search;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.Jobs_List;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOnlineSearchJson;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana on 9/2/2015.
 */
public class SearchOfline
{
    Context context;
    NetState ns;
    DataBaseSqlite db;
    Query query ;
    KeySettings setting ;
    FieldClass fc=new FieldClass();
    FieldDataBusiness fdb=new FieldDataBusiness();
    Cursor rows_Business;
    Cursor rows_Collection;
    Cursor rows_Subset;
    Cursor rows_FieldActivity;

    int length = 0;

    public  SearchOfline(Context context)
    {
        this.context=context;
        ns=new NetState(context);
        query=new Query(context);
        db=new DataBaseSqlite(context);
        setting=new KeySettings(context);

    }
        

    private List<Integer> selectId=new ArrayList<>();
    private  List<Integer> selectDisCount=new ArrayList<>();
    private  List<Integer> selectSubsetId=new ArrayList<>();
    private  List<Double>  selectLongtiude=new ArrayList<>();
    private  List<Double>  selectLatitude=new ArrayList<>();
    private  List<Double>  selectRate=new ArrayList<>();
    private  List<String>  selectPhone=new ArrayList<String>();
    private  List<String>  selectMobile=new ArrayList<String>();
    private  List<String>  selectAddress=new ArrayList<>();
    private  List<String>  selectMarketName=new ArrayList<String>();
    private  List<String>  selectSrc=new ArrayList<String>();

    public void SearchOfline(Context context)
    {
        this.context=context;
        setting= new KeySettings(context);
        query = new Query(context);
        db = new DataBaseSqlite(context);
        ns = new NetState(context);
    }

     public void TextSearch(String cityname,String textSearch) {
         int i = 0;
         String selectedWord[] = new String[]{"", "", "", "", ""};
         selectedWord[0] = "";
         selectedWord[1] = "";
         selectedWord[2] = "";
         selectedWord[3] = "";
         selectedWord[4] = "";


         fc.SetSelected_job(cityname.toString());

         Integer cityid = query.getCityId(cityname.toString().trim());
         if (ns.checkInternetConnection()) {
             try {
                 if (cityid > 0) {
                     String textwhat = URLEncoder.encode(textSearch.toString().trim(), "UTF-8");
                     HTTPGetOnlineSearchJson httpGetOnlineSearchJson = new HTTPGetOnlineSearchJson(context);
                     httpGetOnlineSearchJson.SetValueSearch(textwhat, cityid);
                     httpGetOnlineSearchJson.execute();

                 } else {
                     //Toast.makeText(context, "در کجا؟!", Toast.LENGTH_LONG).show();

                     AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                     alertDialog.setTitle("هشدار");
                     alertDialog.setMessage("شهر مورد نظر یافت نشد دوباره انتخاب کنید");
                     alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {

                         }
                     });
                     alertDialog.show();
                 }
             } catch (Exception e) {
                 Log.e("search", e.toString());
             }

         } else {
             for (String currentWord : textSearch.toString().trim().split(" ")) {

                 if (i <= 4) {
                     selectedWord[i] = currentWord;
                     i++;
                 }
                 Log.i("length", String.valueOf(length));

             }


             //جستجو کامل متن در نام مشاغل و آدرس
             rows_Business = db.select_BusinessSearch(textSearch.toString(), cityid);
             Log.i("BusinessgetCount", String.valueOf(rows_Business.getCount()));
             if (rows_Business.getCount() > 0) {
                 Log.i("Businessget", "on");
                 if (rows_Business.moveToFirst()) {
                     do {

                         selectId.add(rows_Business.getInt(0));
                         selectMarketName.add(rows_Business.getString(1));
                         selectPhone.add(rows_Business.getString(3));
                         selectMobile.add(rows_Business.getString(4));
                         selectAddress.add(rows_Business.getString(8));
                         selectSubsetId.add(rows_Business.getInt(14));
                         selectLongtiude.add(rows_Business.getDouble(15));
                         selectLatitude.add(rows_Business.getDouble(16));
                         selectRate.add(rows_Business.getDouble(30));
                         selectSrc.add(rows_Business.getString(31));

                     } while (rows_Business.moveToNext());


                 }

                 //نمایش به کاربر

                 fdb.SetIdBusiness(selectId);
                 fdb.SetLatitudeBusiness(selectLatitude);
                 fdb.SetLongtiudeBusiness(selectLongtiude);
                 fdb.SetRateBusiness(selectRate);
                 fdb.SetSubsetId(selectSubsetId);
                 fdb.SetAddressBusiness(selectAddress);
                 fdb.SetMarketBusiness(selectMarketName);
                 fdb.SetPhoneBusiness(selectPhone);
                 fdb.SetMobileBusiness(selectMobile);
                 fdb.SetSrc(selectSrc);

                 fc.SetSearchOffline(true);
                 Intent intent = new Intent(context, SearchListActivity.class);
                 context.startActivity(intent);
             } else {
                 //جستجو کلمه اول در مجموعه
                 rows_Collection = db.select_Collection(selectedWord[0]);
                 Log.i("CollectiongetCount", String.valueOf(rows_Collection.getCount()));
                 if (rows_Collection.getCount() > 0) {
                     Log.i("Collection", "on");
                     rows_Collection.moveToFirst();
                     //جستجوآی دی مجوعه در زیر مجموعه
                     rows_Subset = db.select_SubsetId(rows_Collection.getInt(0));
                     Log.i("SubsetgetCount", String.valueOf(rows_Subset.getCount()));
                     rows_Subset.moveToFirst();
                     //جستجو آی دی زیر مجموعه و کلمات دیگر در مشاغل
                     rows_Business = db.select_BusinessSearch(selectedWord[0], selectedWord[1], selectedWord[2], selectedWord[3], selectedWord[4], rows_Subset.getInt(0), cityid);
                     if (rows_Business.moveToFirst()) {
                         do {
                             selectAddress.add(rows_Business.getString(8));
                             selectMarketName.add(rows_Business.getString(1));
                             selectPhone.add(rows_Business.getString(3));
                             selectMobile.add(rows_Business.getString(4));
                             selectId.add(rows_Business.getInt(0));
                             selectSubsetId.add(rows_Business.getInt(14));
                             selectLatitude.add(rows_Business.getDouble(16));
                             selectLongtiude.add(rows_Business.getDouble(15));
                             selectRate.add(rows_Business.getDouble(30));
                             selectSrc.add(rows_Business.getString(31));

                         } while (rows_Business.moveToNext());

                     }
                     //نمایش به کاربر

                     fdb.SetIdBusiness(selectId);
                     fdb.SetLatitudeBusiness(selectLatitude);
                     fdb.SetLongtiudeBusiness(selectLongtiude);
                     fdb.SetRateBusiness(selectRate);
                     fdb.SetSubsetId(selectSubsetId);
                     fdb.SetAddressBusiness(selectAddress);
                     fdb.SetMarketBusiness(selectMarketName);
                     fdb.SetPhoneBusiness(selectPhone);
                     fdb.SetMobileBusiness(selectMobile);
                     fdb.SetSrc(selectSrc);

                     fc.SetSearchOffline(true);
                     Intent intent = new Intent(context, SearchListActivity.class);
                     context.startActivity(intent);
                 } else {
                     //جستجو کلمه اول در زیرمجموعه
                     rows_Subset = db.select_SubsetId(selectedWord[0]);
                     Log.i("SubsetgetCount", String.valueOf(rows_Subset.getCount()));
                     if (rows_Subset.getCount() > 0) {
                         Log.i("Subsetget", "on");
                         rows_Subset.moveToFirst();
                         //جستجو آی دی زیر مجموعه و کلمات دیگر در مشاغل
                         rows_Business = db.select_BusinessSearch(selectedWord[0], selectedWord[2], selectedWord[2], selectedWord[3], selectedWord[4], rows_Subset.getInt(0), cityid);
                         if (rows_Business.moveToFirst()) {
                             do {
                                 selectAddress.add(rows_Business.getString(8));
                                 selectMarketName.add(rows_Business.getString(1));
                                 selectPhone.add(rows_Business.getString(3));
                                 selectMobile.add(rows_Business.getString(4));
                                 selectId.add(rows_Business.getInt(0));
                                 selectSubsetId.add(rows_Business.getInt(14));
                                 selectLatitude.add(rows_Business.getDouble(16));
                                 selectLongtiude.add(rows_Business.getDouble(15));
                                 selectRate.add(rows_Business.getDouble(30));
                                 selectSrc.add(rows_Business.getString(31));

                             } while (rows_Business.moveToNext());

                         }
                         //نمایش به کاربر

                         fdb.SetIdBusiness(selectId);
                         fdb.SetLatitudeBusiness(selectLatitude);
                         fdb.SetLongtiudeBusiness(selectLongtiude);
                         fdb.SetRateBusiness(selectRate);
                         fdb.SetSubsetId(selectSubsetId);
                         fdb.SetAddressBusiness(selectAddress);
                         fdb.SetMarketBusiness(selectMarketName);
                         fdb.SetPhoneBusiness(selectPhone);
                         fdb.SetMobileBusiness(selectMobile);
                         fdb.SetSrc(selectSrc);


                         fc.SetSearchOffline(true);
                         Intent intent = new Intent(context, SearchListActivity.class);
                         context.startActivity(intent);
                     } else {

                     }
                 }
             }


         }
     }
}
