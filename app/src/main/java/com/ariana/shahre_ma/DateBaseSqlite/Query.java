package com.ariana.shahre_ma.DateBaseSqlite;

import android.content.Context;
import android.database.Cursor;

import com.ariana.shahre_ma.Fields.FieldClass;

/**
 * Created by ariana2 on 6/17/2015.
 */
public class Query {

    Context context;


    FieldClass fc=new FieldClass();
   public Query(Context context)
   {
       this.context=context;

   }



    public String getsubsetName(Integer id) {

        String Result = "";

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_SubsetName(id);
            allrows.moveToFirst();
            Result = allrows.getString(0);
            allrows.close();


        //    fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {
            // Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
        return Result;
    }

    public Integer getAreaID(String Name)
    {
        Integer Result=0;
        try {

            SelectDataBaseSqlite db=new SelectDataBaseSqlite(context);
            Cursor allrows = db.select_AreaId(Name);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();

        }

        catch (Exception e)
        {
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return  Result;
    }

    public Integer getCollectionId(Integer subsetid)
    {
        Integer Result=0;
        try {

            SelectDataBaseSqlite db=new SelectDataBaseSqlite(context);
            Cursor allrows = db.select_CollectionId(subsetid);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();

        }

        catch (Exception e)
        {
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return  Result;
    }

    public Integer getCollectionIdProduct(String namecollection)
    {
        Integer Result=0;
        try {

            SelectDataBaseSqlite db=new SelectDataBaseSqlite(context);
            Cursor allrows = db.select_Collection_Product(namecollection);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();

        }

        catch (Exception e)
        {
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return  Result;
    }

    public Integer getValueId(String nameValue)
    {
        Integer Result=0;
        try {

            SelectDataBaseSqlite db=new SelectDataBaseSqlite(context);
            Cursor allrows = db.select_Value_Product(nameValue);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return  Result;
    }

    public String getPropertyName(Integer propertyid)
    {
        String Result="";
        try {

            SelectDataBaseSqlite db=new SelectDataBaseSqlite(context);
            Cursor allrows = db.select_Property_Product(propertyid);
            allrows.moveToFirst();
            Result = allrows.getString(0);
            allrows.close();
        }
        catch (Exception e)
        {
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return  Result;
    }

    public String getValueName(Integer valueid)
    {
        String Result="";
        try {

            SelectDataBaseSqlite db=new SelectDataBaseSqlite(context);
            Cursor allrows = db.select_ValueName_Product(valueid);
            allrows.moveToFirst();
            Result = allrows.getString(0);
            allrows.close();
        }
        catch (Exception e)
        {
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return  Result;
    }
    /**
     *
     * @param id
     * @return
     */
    public String getCityName(Integer id) {

        String Result = "";

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_CityName(id);
            allrows.moveToFirst();
            Result = allrows.getString(0);
            allrows.close();


            //    fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {
            // Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
        return Result;
    }

    public String getFieldActivityName(Integer id) {

        String Result = "";

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_FieldActivityName(id);
            allrows.moveToFirst();
            Result = allrows.getString(0);
            allrows.close();


            //    fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {
            // Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
        return Result;
    }

    public Integer getFieldActivityId(String nameactivity) {

        Integer Result = 0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_FieldActivityId(nameactivity);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();


            //    fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {
            // Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
        return Result;
    }



    public  Integer getDisCountId(Integer businessid)
    {
        Integer id=0;
        try
        {
            SelectDataBaseSqlite db=new SelectDataBaseSqlite(context);
            Cursor allrows=db.select_DisCountId(businessid);
            allrows.moveToFirst();
            id=allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {

        }
        return  id;
    }
    /**
     *
     * @param subsetname
     * @return
     */
    public Integer getsubsetID(String subsetname) {

        Integer Result = 0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_SubsetId(subsetname);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();


            fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {
            // Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
        return Result;
    }

    public Integer getAdvancesubsetID(String subsetname) {

        Integer Result = 0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_AdvanceSubsetId(subsetname);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();


            fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {
            // Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
        return Result;
    }


    public Integer getsubsetProductID(String subsetname) {

        Integer Result = 0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_SubsetProductId(subsetname);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();


            fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {
            // Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
        return Result;
    }


    public Integer getCityId(String cityName) {

        Integer Result = 0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_CityId(cityName);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();


           // fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {
            // Toast.makeText(activity, e.toString(), Toast.LENGTH_LONG).show();
        }
        return Result;
    }

    public Integer getCountSubset() {
        Integer Result = 0;
        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_CountSubset();
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();


            fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {

        }
        return Result;
    }

    public Integer getCountCollection() {
        Integer Result = 0;
        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_CountCollection();
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();


            fc.SetBusiness_SubsetId(Result);
        }
        catch (Exception e) {

        }
        return Result;
    }

    public Integer getCountBusiness(Integer subsetid) {
        Integer Result = 0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_CountBusiness_SubsetId(subsetid);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }

    public String getNameBusiness(Integer businessid) {
        String Result = "";

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_business_NameMarket(businessid);
            allrows.moveToFirst();
            Result = allrows.getString(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }



    public Integer getMemberId() {
        Integer Result =0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_Member();
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {

        }

        return Result;
    }

    /**
     * Geting Businessid in Table ShowNotification
     * @return
     */
    public Integer getShowNotification(Integer id) {
        Integer Result =0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_ShowNotificationBusinessId(id);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {

        }

        return Result;
    }


    public Integer getBookMarkId(Integer businessid) {
        Integer Result =0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_bookmarkId(businessid);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }

    public Integer getOpinionId() {
        Integer Result =0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_opinion();
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }

    public Integer getBusinessId() {
        Integer Result =0;

        try {
            SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(context);
            Cursor allrows = dbs.select_CountBusiness_SubsetId(14);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }
        catch (Exception e)
        {}

        return Result;
    }



/*
    if (ns.checkInternetConnection() == false) {

        if (count == 0)
        {
            Toast.makeText(getApplicationContext(), "فروشگاه ثبت نشده", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent i = new Intent(getApplicationContext(), Jobs_List.class);
            startActivity(i);

        }

    }
    else
    {


        if (count == 0 ) {
            //  Toast.makeText(getApplicationContext(),"فروشگاه ثبت نشده",Toast.LENGTH_LONG).show();
            httpbusin = new HTTPGetBusinessJson(Jobs.this);
            httpbusin.SetUrl_business(query.getsubsetID(selected));
            httpbusin.execute();

            Log.i("Count ==0 ", " ok");
        }
        else
        {
            if (time == "")
            {
                Toast.makeText(getApplicationContext(), "Time: " + time, Toast.LENGTH_LONG).show();
                SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(Jobs.this);
                dbs.Add_UpdateTime(fc.GetTableNameUpdateTime(), dt.Hours(), dt.Now());

                httpbusin = new HTTPGetBusinessJson(Jobs.this);
                httpbusin.SetUrl_business(query.getsubsetID(selected));
                httpbusin.execute();
                Log.i("Time == 0", " ok");
            }
            else
            {
                if (Integer.parseInt(dt.Hours()) >= Integer.parseInt(time + 3) || date != dt.Now()) {
                    SelectDataBaseSqlite dbs = new SelectDataBaseSqlite(Jobs.this);
                    dbs.delete_UpdateTime(fc.GetTableNameUpdateTime());
                    dbs.Add_UpdateTime(fc.GetTableNameUpdateTime(), dt.Hours(), dt.Now());

                    httpbusin = new HTTPGetBusinessJson(Jobs.this);
                    httpbusin.SetUrl_business(query.getsubsetID(selected));
                    httpbusin.execute();
                    Toast.makeText(getApplicationContext(), "1", Toast.LENGTH_LONG).show();
                    Log.i("Time > Time+3 ", "ok");
                }
                else
                {
                    Intent i = new Intent(getApplicationContext(), Jobs_List.class);
                    startActivity(i);
                    Toast.makeText(getApplicationContext(), "0", Toast.LENGTH_LONG).show();
                    Log.i("NOT IF And Eles ", "Ok");
                }
            }
        }
    }*/
}
