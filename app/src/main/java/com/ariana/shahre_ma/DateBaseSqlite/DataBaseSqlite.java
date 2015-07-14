package com.ariana.shahre_ma.DateBaseSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ariana2 on 6/8/2015.
 */
public class DataBaseSqlite extends SQLiteOpenHelper
{
    //Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DBshahrma.db";

    InstructionsSqlite instructionsSqlite=new InstructionsSqlite();



    public  DataBaseSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(instructionsSqlite.CREATE_TABLE_Subset);
        db.execSQL(instructionsSqlite.CREATE_TABLE_Collection);
        db.execSQL(instructionsSqlite.CREATE_TABLE_Member);
        db.execSQL(instructionsSqlite.CREATE_TABLE_Opinion);
        db.execSQL(instructionsSqlite.CREATE_TABLE_Business);
        db.execSQL(instructionsSqlite.CREATE_TABLE_City);
        db.execSQL(instructionsSqlite.CREATE_TABLE_Bookmark);
        db.execSQL(instructionsSqlite.CREATE_TABLE_Area);
        db.execSQL(instructionsSqlite.CREATE_TABLE_UpdateTime);
        db.execSQL(instructionsSqlite.CREATE_TABLE_Interest);
        db.execSQL(instructionsSqlite.CREATE_TABLE_Notification);
        db.execSQL(instructionsSqlite.CREATE_TABLE_FieldActivity);
        db.execSQL(instructionsSqlite.CREATE_TABLE_DisCount);
        db.execSQL(instructionsSqlite.CREATE_TABLE_LikeDisCount);
        db.execSQL(instructionsSqlite.CREATE_TABLE_DisCountMember);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older  tables if existed
        db.execSQL("DROP TABLE IF EXISTS "+instructionsSqlite.TABLE_NAME_SUBSET);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_COLLECTION);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_OPINION);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_BUSINESS);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_City);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_Bookmark);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_Area);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_UpdateTime);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_Interest);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_FieldActivity);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_DisCount);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_LikeDisCount);
        db.execSQL("DROP TABLE IF EXISTS " + instructionsSqlite.TABLE_NAME_DisCountMember);
        // create fresh  tables
        this.onCreate(db);
    }

    public void Add_subset(Integer id, String subsetname, Integer collectionid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_subset, id);
        values.put(instructionsSqlite.NAME_subset, subsetname);
        values.put(instructionsSqlite.COLLECTIONID_subset, collectionid);

        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_SUBSET, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    /**
     *
     * @param id
     * @param memberid
     * @param businessid
     * @param likecount
     * @param dislikecount
     */
    public void Add_LikeDisCount(Integer id,Integer memberid,Integer businessid,Integer likecount,Integer dislikecount){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_LIKEDISCOUNT, id);
        values.put(instructionsSqlite.MEMBERID_LIKEDISCOUNT, memberid);
        values.put(instructionsSqlite.BUSINESSID_LIKEDISCOUNT, businessid);
        values.put(instructionsSqlite.LIKECOUNT_LIKEDISCOUNT, likecount);
        values.put(instructionsSqlite.DISLIKECOUNT_LIKEDISCOUNT, dislikecount );



        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_LikeDisCount, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();

    }

    /**
     *
     * @param id
     * @param text
     * @param image
     * @param startdate
     * @param expirationdate
     * @param description
     * @param percent
     * @param businessid
     */
    public void Add_DisCount(Integer id,String text,String image,String startdate,String expirationdate,String description,String percent,Integer businessid){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_DISCOUNT, id);
        values.put(instructionsSqlite.TEXT_DISCOUNT, text);
        values.put(instructionsSqlite.IMAGE_DISCOUNT, image);
        values.put(instructionsSqlite.STARTDATE_DISCOUNT, startdate);
        values.put(instructionsSqlite.EXPIRATIONDATE_DISCOUNT, expirationdate);
        values.put(instructionsSqlite.DESCRIPTION_DISCOUNT, description);
        values.put(instructionsSqlite.PERCENT_DISCOUNT, percent);
        values.put(instructionsSqlite.BUSINESSID_DISCOUNT, businessid);


        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_DisCount, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();

    }
    public void Add_DisCountMember(Integer id,String text,String image,String startdate,String expirationdate,String description,String percent,Integer businessid){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(instructionsSqlite.ID_DISCOUNTMEMBER, id);
        values.put(instructionsSqlite.TEXT_DISCOUNTMEMBER, text);
        values.put(instructionsSqlite.IMAGE_DISCOUNTMEMBER, image);
        values.put(instructionsSqlite.STARTDATE_DISCOUNTMEMBER, startdate);
        values.put(instructionsSqlite.EXPIRATIONDATE_DISCOUNTMEMBER, expirationdate);
        values.put(instructionsSqlite.DESCRIPTION_DISCOUNTMEMBER, description);
        values.put(instructionsSqlite.PERCENT_DISCOUNTMEMBER, percent);
        values.put(instructionsSqlite.BUSINESSID_DISCOUNTMEMBER, businessid);


        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_DisCountMember, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();

    }
    /**
     *
     * @param id
     * @param OpinionType
     * @param erja
     * @param ExecutionTime
     * @param Description
     * @param ExpirationDate
     * @param City
     * @param CityId
     * @param Subset
     * @param SubsetId
     */
    public void Add_Notification(Integer id,Integer OpinionType,Integer erja,Boolean ExecutionTime,String Description,String ExpirationDate,String City,Integer CityId,String Subset,Integer SubsetId){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_Notification, id);
        values.put(instructionsSqlite.OpinionType_Notification, OpinionType);
        values.put(instructionsSqlite.ErJa_Notification, erja);
        values.put(instructionsSqlite.ExecutionTime_Notification, ExecutionTime);
        values.put(instructionsSqlite.Description_Notification, Description);
        values.put(instructionsSqlite.ExpirationDate_Notification, ExpirationDate);
        values.put(instructionsSqlite.City_Notification, City);
        values.put(instructionsSqlite.CityId_Notification, CityId);
        values.put(instructionsSqlite.Subset_Notification, Subset);
        values.put(instructionsSqlite.SubsetId_Notification, SubsetId);

        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_NOTIFICATION, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();

}


    public void Add_collection(Integer id, String collectionname) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_colection, id);
        values.put(instructionsSqlite.NAME_collection, collectionname);

        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_COLLECTION, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_member(Integer id,String name,String email,String mobile,Integer age,Boolean sex,String username,String password,Integer cityid)
    {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_member, id);
        values.put(instructionsSqlite.NAME_member, name);
        values.put(instructionsSqlite.EMAIL_member, email);
        values.put(instructionsSqlite.MOBILE_member, mobile);
        values.put(instructionsSqlite.AGE_member, age);
        values.put(instructionsSqlite.SEX_member, sex);
        values.put(instructionsSqlite.USERNAME_member, username);
        values.put(instructionsSqlite.PASSWORD_member, password);
        values.put(instructionsSqlite.CITYID_member, cityid);


        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_MEMBER, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_opinion(Integer id,String description,String date,Integer erja,Integer countlike,Integer countdislike,String membername)
    {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_opinion,id);
        values.put(instructionsSqlite.DESCRIPTION_opinion,description);
        values.put(instructionsSqlite.DATE_opinion,date);

        values.put(instructionsSqlite.ERJA_opinion, erja);
        values.put(instructionsSqlite.COUNTLIKE_opinion, countlike);
        values.put(instructionsSqlite.COUNTDISLIKE_opinion, countdislike);
        values.put(instructionsSqlite.MEMBERNAME_opinion, membername);


        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_OPINION, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }





    public void Add_business(Integer id,String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, String longitude, String latitude, Integer areaid, String area, String user,Integer cityid, Integer userid,Integer field1,Integer field2,Integer field3,Integer field4,Integer field5,Integer field6,Integer field7,Integer ratecount,Double ratevalue) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_business, id);
        values.put(instructionsSqlite.MARKET_business, market);
        values.put(instructionsSqlite.CODE_business, code);
        values.put(instructionsSqlite.PHONE_business, phone);
        values.put(instructionsSqlite.MOBILE_business, mobile);
        values.put(instructionsSqlite.FAX_business, fax);
        values.put(instructionsSqlite.EMAIL_business, email);
        values.put(instructionsSqlite.BUSINESSOWNER_business, businessowner);
        values.put(instructionsSqlite.ADDRESS_business, address);
        values.put(instructionsSqlite.DESCRIPTION_business, description);
        values.put(instructionsSqlite.STARTDATE_business, startdate);
        values.put(instructionsSqlite.EXPIRATIONDATE_business, expirationdate);
        values.put(instructionsSqlite.INACTIVE_business, inactive);
        values.put(instructionsSqlite.SUBSET_business, subset);
        values.put(instructionsSqlite.SUBSETID_business, subsetid);
        values.put(instructionsSqlite.LONGITUDE_business, longitude);
        values.put(instructionsSqlite.LATITUDE_business, latitude);
        values.put(instructionsSqlite.AREAID_business, areaid);
        values.put(instructionsSqlite.AREA_business, area);
        values.put(instructionsSqlite.USER_business, user);
        values.put(instructionsSqlite.CITYID_business, cityid);
        values.put(instructionsSqlite.USERID_business, userid);
        values.put(instructionsSqlite.FIELD1_business, field1);
        values.put(instructionsSqlite.FIELD2_business, field2);
        values.put(instructionsSqlite.FIELD3_business, field3);
        values.put(instructionsSqlite.FIELD4_business, field4);
        values.put(instructionsSqlite.FIELD5_business, field5);
        values.put(instructionsSqlite.FIELD6_business, field6);
        values.put(instructionsSqlite.FIELD7_business, field7);
        values.put(instructionsSqlite.RATECOUNT_business, ratecount);
        values.put(instructionsSqlite.RATEVALUE_business, ratevalue);


        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_BUSINESS, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }


    public void Add_city(Integer id, String name, Integer provinceid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_city, id);
        values.put(instructionsSqlite.NAME_city, name);
        values.put(instructionsSqlite.PROVINCEID_city, provinceid);

        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_City, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_bookmark(Integer businessid,Integer memberid)
    {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(instructionsSqlite.BUSINESSID_bookmark,businessid);
        values.put(instructionsSqlite.MEMBERID_bookmark, memberid);

        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_Bookmark, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_area(Integer id ,String areaname, Integer cityid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_area, id);
        values.put(instructionsSqlite.NAME_area, areaname);
        values.put(instructionsSqlite.CITYID_area, cityid);

        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_Area, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }


    public void Add_UpdateTime(String fieldname ,String time,String date) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.FIELDNAME_UpdateTime, fieldname);
        values.put(instructionsSqlite.SAAT_UpdateTime, time);
        values.put(instructionsSqlite.TARIKH_UpdateTime, date);

        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_UpdateTime, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }


    public void Add_Like(Integer id,Boolean like,Integer memberid,Integer opinionid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_Like, id);
        values.put(instructionsSqlite.LIKE_Like, like);
        values.put(instructionsSqlite.MEMBERID_Like, memberid);
        values.put(instructionsSqlite.OPINION_Like, opinionid);

        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_Like, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }


    public void Add_Interest(Integer subsetid,Integer memberid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(instructionsSqlite.SUBSETID_Interest, subsetid);
        values.put(instructionsSqlite.MEMBERID_Interest, memberid);


        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_Interest, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_FieldActivity(Integer id,String activity) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(instructionsSqlite.ID_FIELDACTIVITY,id);
        values.put(instructionsSqlite.ACTIVITY_FIELDACTIVITY, activity);


        // 3. insert
        db.insert(instructionsSqlite.TABLE_NAME_FieldActivity, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    /**
     * SelectBusiness
     * @param
     * @return
     */

    public Cursor select_AllBusiness()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT* FROM " + instructionsSqlite.TABLE_NAME_BUSINESS , null);

    }

    public Cursor select_CountBusiness_SubsetId(Integer subsetID)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" + subsetID, null);

    }

    public Cursor select_CountBusinessId(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE Id=" + id, null);

    }

    public Cursor select_AllBusiness(Integer subsetID,Integer cityid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE CityId="+cityid+" AND SubsetId=" +subsetID , null);

    }

    public Cursor select_SortRateBusiness(Integer subsetID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" +subsetID+" ORDER BY "+instructionsSqlite.RATEVALUE_business +" DESC", null);
    }

    public Cursor select_SortNameBusiness(Integer subsetID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" +subsetID+" ORDER BY "+instructionsSqlite.MARKET_business +" ASC", null);
    }

    public Cursor select_SortDateBusiness(Integer subsetID)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" +subsetID+" ORDER BY "+instructionsSqlite.ID_business +" DESC", null);
    }

    public Cursor select_AllBusinessId(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE Id=" +id, null);

    }

    public Cursor select_business_NameMarket(Integer businessID)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Market FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE Id=" +businessID , null);

    }

    public Cursor select_FieldActivity()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_FieldActivity , null);
    }
    public Cursor select_AllDisCount()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_DisCount , null);
    }

    public Cursor select_AllDisCountMember()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_DisCountMember , null);
    }

    public Cursor select_AllDisCountMember(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_DisCountMember +" WHERE Id="+id , null);
    }

    public Cursor select_FieldActivityId(String fieldname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + instructionsSqlite.TABLE_NAME_FieldActivity+" WHERE Activity='"+fieldname+"'" , null);
    }

    public Cursor select_FieldActivityName(Integer id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Activity FROM " + instructionsSqlite.TABLE_NAME_FieldActivity+" WHERE Id="+id , null);
    }

    public Cursor SearchBusiness(String nameMarket,Integer subsetId)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" +subsetId+" AND Market LIKE '%"+nameMarket+"%'" , null);

    }
    //End Select Business




    public Cursor select_SubsetId(String subsetname)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + instructionsSqlite.TABLE_NAME_SUBSET + "  WHERE SubsetName='" + subsetname+"'", null);

    }

    public Cursor select_SubsetName(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT SubsetName FROM " + instructionsSqlite.TABLE_NAME_SUBSET + "  WHERE id=" +id, null);

    }

    public Cursor select_CityName(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Name FROM " + instructionsSqlite.TABLE_NAME_City + "  WHERE id=" +id, null);

    }

    public Cursor select_business_Detail(String market,String address)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE Market='" + market+"' AND Address='"+address+"'", null);

    }

    public  Cursor select_Interest()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " +instructionsSqlite. TABLE_NAME_Interest, null);
    }
    public Cursor select_opinion()
    {

    SQLiteDatabase db = this.getReadableDatabase();
    return db.rawQuery("SELECT * FROM " +instructionsSqlite. TABLE_NAME_OPINION, null);

    }

    public Cursor select_bookmark()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_Bookmark, null);

    }

    public Cursor select_opinion_BusinessId(Integer busintessid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_OPINION+ "  WHERE Erja="+busintessid, null);

    }

    public Cursor select_Member()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + instructionsSqlite.TABLE_NAME_MEMBER, null);
    }

    public Cursor select_UpdateTime(String fieldname)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " +instructionsSqlite. TABLE_NAME_UpdateTime +" WHERE FieldName='"+fieldname+"'", null);

    }

    public Cursor select_Member_Name()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Name FROM " + instructionsSqlite.TABLE_NAME_MEMBER, null);
    }

    public Cursor select_Notification() {
    SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+instructionsSqlite.TABLE_NAME_NOTIFICATION,null);
    }

    public Cursor select_Collection()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+instructionsSqlite.TABLE_NAME_COLLECTION,null);
    }

    public Cursor select_Subset()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+instructionsSqlite.TABLE_NAME_SUBSET,null);
    }

    public Cursor select_SearchSubset(String subsetName)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+instructionsSqlite.TABLE_NAME_SUBSET+" WHERE SubsetName LIKE '%"+subsetName+"%'",null);
    }

    public  Cursor select_AllNotificaton(Integer erja)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+instructionsSqlite.TABLE_NAME_NOTIFICATION,null);
    }

    public  Cursor select_NotificatonId(Integer Id)
    {

            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT Id FROM " + instructionsSqlite.TABLE_NAME_NOTIFICATION + " WHERE Id=" + Id, null);

    }

    public  Cursor select_CountCollection()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM "+instructionsSqlite.TABLE_NAME_COLLECTION,null);
    }

    public  Cursor select_AllArea()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+instructionsSqlite.TABLE_NAME_Area,null);
    }



    public  Cursor select_AreaId(String name)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM "+instructionsSqlite.TABLE_NAME_Area+" WHERE AreaName='"+name+"'",null);
    }

    public  Cursor select_AreaName(Integer id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT AreaName FROM "+instructionsSqlite.TABLE_NAME_Area+" WHERE id="+id,null);
    }

    public  Cursor select_CountSubset()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM "+instructionsSqlite.TABLE_NAME_SUBSET,null);
    }

    public  Cursor select_AllCity()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+instructionsSqlite.TABLE_NAME_City,null);
    }

    public  Cursor select_CityId(String cityname)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM "+instructionsSqlite.TABLE_NAME_City +" WHERE Name='"+cityname+"'",null);
    }

    // Deleting Opinion
    public void delete_Opinion() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  " + instructionsSqlite.TABLE_NAME_OPINION);
        // 2. delete
        // db.delete(TABLE_BOOKS_MEMBER,ID," =",id);

        // 3. close
        db.close();
    }

    /**
     * delete all record table member
     */
    public void delete_Member()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_MEMBER);
        db.close();

    }

    /**
     *
     */
    public void delete_Area()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_Area);
        db.close();

    }

    /**
     *
     */
    public void delete_City()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_City);
        db.close();

    }

    /**
     *
     */
    public void delete_FiledActivity()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_FieldActivity);
        db.close();

    }

    public void delete_DisCountMember(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_DisCountMember +" WHERE Id="+id);
        db.close();

    }

    /**
     *
     */
    public void delete_Subset()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_SUBSET);
        db.close();

    }
    /**
     *
     */
    public void delete_Collection()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_COLLECTION);
        db.close();

    }
    /**
     * delete all record table notification
     */

    public void delete_Notification()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_NOTIFICATION );
        db.close();
    }

    public void delete_BusinessId(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_BUSINESS+" WHERE Id="+id);
        db.close();
    }

    public void delete_Business(Integer cityid,Integer subsetid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_BUSINESS+" WHERE CityId="+cityid+" AND SubsetId="+subsetid);
        db.close();
    }

    public void delete_DisCount(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ instructionsSqlite.TABLE_NAME_DisCount+" WHERE Id="+id);
        db.close();
    }
/*    public void delete_Notification(Integer erja)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_NOTIFICATION+" WHERE ErJa="+erja,null);
        db.close();
    }*/


    // Deleting ZamanSanj
    public void delete_UpdateTime(String fieldname) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.execSQL("DELETE FROM  " + instructionsSqlite.TABLE_NAME_UpdateTime +" WHERE FieldName='"+fieldname+"'");



        // 3. close
        db.close();



    }

    public  void delete_Business()
    {
        SQLiteDatabase db=this.getWritableDatabase();
       // delete
        db.execSQL("DELETE FROM  " + instructionsSqlite.TABLE_NAME_BUSINESS);
        //close
        db.close();
    }

    public  void delete_bookmark()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+instructionsSqlite.TABLE_NAME_Bookmark);
        db.close();
    }
    // Updating single
   /* public int update(Integer id) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put("title", book.getTitle()); // get title
        //values.put("author", book.getAuthor()); // get author

        // 3. updating row
        int i=0 ;
     = db.update(TABLE_BOOKS_MEMBER, //table
                values, // column/value
                ID+" = ?", // selections
                new String[] { String.valueOf(book.getId()) }); //selection args

        // 4. close
        db.close();

        return i;

    }*/

}
