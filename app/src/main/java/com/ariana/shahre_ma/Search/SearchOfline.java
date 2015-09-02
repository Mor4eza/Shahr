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
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBusiness;
import com.ariana.shahre_ma.MessageDialog;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOnlineAdvancedSearchJson;
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
    SelectDataBaseSqlite sdb;
    Query query ;
    KeySettings setting ;
    FieldClass fc=new FieldClass();
    FieldDataBusiness fdb=new FieldDataBusiness();
    Cursor rows_Business;
    Cursor rows_Collection;
    Cursor rows_Subset;
    Cursor rows_FieldActivity;
    Integer _FieldActivityId[]=new Integer[6];
    Integer onesearch=1;
    Integer twosearch=1;
    Integer threesearch=0;
    Integer SubsetCount=0;

    int length = 0;

    public  SearchOfline(Context context)
    {
        this.context=context;
        ns=new NetState(context);
        query=new Query(context);
        sdb=new SelectDataBaseSqlite(context);
        setting=new KeySettings(context);

    }
        

    private  List<Integer> selectId=new ArrayList<>();
    private  List<Integer> selectDisCount=new ArrayList<>();
    private  List<Integer> selectRateCount=new ArrayList<>();
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
        sdb = new SelectDataBaseSqlite(context);
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
         if (ns.checkInternetConnection())
         {
             try {
                 if (cityid > 0)
                 {
                     String textwhat = URLEncoder.encode(textSearch.toString().trim(), "UTF-8");
                     HTTPGetOnlineSearchJson httpGetOnlineSearchJson = new HTTPGetOnlineSearchJson(context);
                     httpGetOnlineSearchJson.SetValueSearch(textwhat, cityid);
                     httpGetOnlineSearchJson.execute();

                 } else
                 {
                     MessageDialog messageDialog=new MessageDialog(context);
                     messageDialog.ShowMessage("هشدار","شهر مورد نظر یافت نشد","باشه","false");
                 }
             } catch (Exception e) {
                 Log.e("search", e.toString());
             }

         }
         else
         {
             //Word
             for (String currentWord : textSearch.toString().trim().split(" ")) {

                 if (i <= 4) {
                     selectedWord[i] = currentWord;
                     i++;
                 }
                 Log.i("length", String.valueOf(length));
             }

             i=0;
             //Search FieldActivity
             SearchFieldActivity(textSearch.toString());

             OneSearch(selectedWord[0], cityid, _FieldActivityId[0],_FieldActivityId[1],_FieldActivityId[2]);
             if(onesearch>0)//One Search
             {

             }
             else
             {

                 TwoSearch(selectedWord[0],selectedWord[1],selectedWord[2],selectedWord[3],selectedWord[4],cityid);
                 if(twosearch>0) //Two Search
                 {

                 }
                 else
                 {
                   ThreeSearch(selectedWord[0],selectedWord[1],selectedWord[2],selectedWord[3],selectedWord[4],cityid);
                 }
             }


         }
     }



    public void TextAdvancedSearch(Integer cityId,String Market,String address,String subset) {

        if (ns.checkInternetConnection())
        {
            try {
                String MARKET = URLEncoder.encode(Market.toString().trim(), "UTF-8");
                String ADDRESS = URLEncoder.encode(address.toString().trim(), "UTF-8");
                String SUBSET = URLEncoder.encode(subset.toString().trim(), "UTF-8");

                HTTPGetOnlineAdvancedSearchJson httpGetOnlineAdvancedSearchJson = new HTTPGetOnlineAdvancedSearchJson(context);
                httpGetOnlineAdvancedSearchJson.SetValueSearch(MARKET, cityId, ADDRESS, SUBSET);
                httpGetOnlineAdvancedSearchJson.execute();
            }
            catch (Exception e)
            {
                Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
            }
        }
        else
        {

            rows_Business = sdb.select_BusinessAdvanceSearch(Market, address, cityId, query.getsubsetID(subset));
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
                Log.i("BusinessgetCount", String.valueOf(rows_Business.getCount()));
                fc.SetSearchOffline(true);
                Intent intent = new Intent(context, SearchListActivity.class);
                context.startActivity(intent);
            }
         }

    }


    public void TextAdvancedSearch2(Integer cityId,String Market,String address,String subset) {

        if (ns.checkInternetConnection())
        {
            try {
                String MARKET = URLEncoder.encode(Market.toString().trim(), "UTF-8");
                String ADDRESS = URLEncoder.encode(address.toString().trim(), "UTF-8");
                String SUBSET = URLEncoder.encode(subset.toString().trim(), "UTF-8");

                HTTPGetOnlineAdvancedSearchJson httpGetOnlineAdvancedSearchJson = new HTTPGetOnlineAdvancedSearchJson(context);
                httpGetOnlineAdvancedSearchJson.SetValueSearch(MARKET, cityId, ADDRESS, SUBSET);
                httpGetOnlineAdvancedSearchJson.execute();
            }
            catch (Exception e)
            {
                Toast.makeText(context,e.toString(),Toast.LENGTH_LONG).show();
            }
        }
        else
        {

            rows_Business = sdb.select_BusinessFieldAdvanceSearch(Market, address, cityId, query.getFieldActivityId(subset));
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
                        selectRateCount.add(rows_Business.getInt(29));
                        selectSrc.add(rows_Business.getString(31));

                    } while (rows_Business.moveToNext());


                }
                fdb.SetIdBusiness(selectId);
                fdb.SetLatitudeBusiness(selectLatitude);
                fdb.SetLongtiudeBusiness(selectLongtiude);
                fdb.SetRateBusiness(selectRate);
                fdb.SetRateCount(selectRateCount);
                fdb.SetSubsetId(selectSubsetId);
                fdb.SetAddressBusiness(selectAddress);
                fdb.SetMarketBusiness(selectMarketName);
                fdb.SetPhoneBusiness(selectPhone);
                fdb.SetMobileBusiness(selectMobile);
                fdb.SetSrc(selectSrc);
                Log.i("BusinessgetCount", String.valueOf(rows_Business.getCount()));
                fc.SetSearchOffline(true);
                Intent intent = new Intent(context, SearchListActivity.class);
                context.startActivity(intent);
            }
        }

    }


    private void SearchFieldActivity(String text)
    {
        int i=0;
        _FieldActivityId[0]=0;
        _FieldActivityId[1]=0;
        _FieldActivityId[2]=0;
        _FieldActivityId[3]=0;
        _FieldActivityId[4]=0;
        _FieldActivityId[5]=0;


        try {
            rows_FieldActivity = sdb.select_SearchFieldActivityId(text);
            if (rows_FieldActivity.moveToFirst()) {
                do {
                    _FieldActivityId[i] = rows_FieldActivity.getInt(0);
                    Log.i("_FieldActivityId",String.valueOf(_FieldActivityId[i]));
                    i++;
                } while (rows_FieldActivity.moveToNext());
            }
        }
        catch (Exception  e)
        {

        }
    }


    private void OneSearch(String textSearch,Integer cityid,Integer fieldActivity1,Integer fieldActivity2,Integer fieldActivity3)
    {
        if(fieldActivity1>0 && fieldActivity2>0 && fieldActivity3>0)
        rows_Business = sdb.select_BusinessSearch(textSearch.toString(), cityid,fieldActivity1,fieldActivity2,fieldActivity3);
        else if(fieldActivity1>0 && fieldActivity2>0)
        rows_Business = sdb.select_BusinessSearch(textSearch.toString(), cityid,fieldActivity1,fieldActivity2);
        else if(fieldActivity1>0)
        rows_Business = sdb.select_BusinessSearch(textSearch.toString(), cityid,fieldActivity1);


        Log.i("OneSearchBusiness", String.valueOf(rows_Business.getCount()));
        if (rows_Business.getCount() > 0)
        {
            Log.i("Businessget", "on");
            if (rows_Business.moveToFirst())
            {
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
        }
        else
        {
          onesearch=0;
        }
    }

    private void TwoSearch(String word1,String word2,String word3,String word4,String word5,Integer cityid)
    {

        SearchFieldActivity(word1);
        rows_Collection = sdb.select_Collection(word2);
        Log.i("CollectiongetCount", String.valueOf(rows_Collection.getCount()));
        if (rows_Collection.getCount() > 0) {
            rows_Collection.moveToFirst();
            //SubsetId Search
            rows_Subset = sdb.select_SubsetId(rows_Collection.getInt(0));
            Log.i("SubsetgetCount", String.valueOf(rows_Subset.getCount()));
            if(rows_Subset.getCount()>0)
            rows_Subset.moveToFirst();

            rows_Business = sdb.select_BusinessSearch(word1, word2, word3, word4, word5, rows_Subset.getInt(0), cityid, _FieldActivityId[0]);
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
        }
        else if( _FieldActivityId[0]>0)
        {
            rows_Business = sdb.select_BusinessSearch(word1, word2, word3, word4, word5,0, cityid, _FieldActivityId[0]);
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
        }
        else
        {
            twosearch=0;
        }
    }

    private  void ThreeSearch(String word1,String word2,String word3,String word4,String word5,Integer cityid)
    {

        SearchFieldActivity(word1);
        rows_Subset = sdb.select_SubsetId(word1);
        Log.i("SubsetgetCount", String.valueOf(rows_Subset.getCount()));
        if (rows_Subset.getCount() > 0 || _FieldActivityId[0]>0) {
            Log.i("Subsetget", "on");
            rows_Subset.moveToFirst();
            rows_Business = sdb.select_BusinessSearch(word1,word2,word3,word4,word5, rows_Subset.getInt(0), cityid,_FieldActivityId[0]);
            Log.i("ThreeSearch", String.valueOf(rows_Business.getCount()));
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
        }
        else
        {
            fc.SetSearchOffline(true);
            Intent intent = new Intent(context, SearchListActivity.class);
            context.startActivity(intent);
        }
    }

}
