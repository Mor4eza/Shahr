package com.ariana.shahre_ma.DateBaseSqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ariana2 on 6/8/2015.
 */
public class DataBaseSqlite extends SQLiteOpenHelper
{
    //Database Version
    private static final int DATABASE_VERSION = 4;

    // Database Name
    private static final String DATABASE_NAME = "DBshahrma.db";
    InstructionsSqlite instructionsSqlite=new InstructionsSqlite();

    String query="";

    Context context;



    public  DataBaseSqlite(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        this.context=context;
    }

    public void DeleteAllDataBase()
    {
       /* SQLiteDatabase db=this.getWritableDatabase();
        db.delete(DATABASE_NAME,null,null);*/
        context.deleteDatabase(DATABASE_NAME);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Subset);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Search);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Subset_Prodcut);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Collection_Product);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_SubsetProperty_Product);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Value_Product);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Property_Product);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Collection);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Member);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Opinion);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Business);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Business_Tops);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Business_DisCount);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_BusinessImage);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_City);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Bookmark);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Area);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Update);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Interest);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Notification);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_ShowNotification);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_FieldActivity);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_DisCount);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_LikeDisCount);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_DisCountMember);
        db.execSQL(InstructionsSqlite.CREATE_TABLE_Advertisment);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older  tables if existed
      /*  db.execSQL("DROP TABLE IF EXISTS "+ InstructionsSqlite.TABLE_NAME_SUBSET);
        db.execSQL("DROP TABLE IF EXISTS "+ InstructionsSqlite.TABLE_NAME_SUBSET_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_COLLECTION);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_COLLECTION_PRODUCT);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_OPINION);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_BUSINESS);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_BUSINESS_TOPS);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_BUSINESS_DISCOUNT);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_City);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_Bookmark);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_Area);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_UpdateTime);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_Interest);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_NOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_FieldActivity);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_SHOWNOTIFICATION);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_DisCount);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_LikeDisCount);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_DisCountMember);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_Advertisment);
        db.execSQL("DROP TABLE IF EXISTS " + InstructionsSqlite.TABLE_NAME_BUSINESS_IMAGE);*/

        db.execSQL("ALTER TABLE " + InstructionsSqlite.TABLE_NAME_DisCount + " ADD COLUMN Src TEXT");
        db.execSQL("ALTER TABLE " + InstructionsSqlite.TABLE_NAME_DisCountMember + " ADD COLUMN Src TEXT");

        // create fresh  tables
        this.onCreate(db);

    }

    public void Add_subset(Integer id, String subsetname, Integer collectionid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_subset, id);
        values.put(InstructionsSqlite.NAME_subset, subsetname);
        values.put(InstructionsSqlite.COLLECTIONID_subset, collectionid);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_SUBSET,null,values);
        db.close();
    }

    public void Add_subset_Product(Integer id, String subsetname, Integer collectionid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_subset, id);
        values.put(InstructionsSqlite.NAME_subset, subsetname);
        values.put(InstructionsSqlite.COLLECTIONID_subset, collectionid);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_SUBSET_PRODUCT,null,values);
        db.close();
    }
    public void Add_Value_Product(Integer id, String valuename, Integer idProperty) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_VALUE_PRODUCT, id);
        values.put(InstructionsSqlite.NAME_VALUE_PRODUCT, valuename);
        values.put(InstructionsSqlite.IDPROPERTY_VALUE_PRODUCT, idProperty);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_VALUE_PRODUCT,null,values);
        db.close();
    }

    public void Add_Property_Product(Integer id, String propertyname,Integer type) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_PROPERTY_PRODUCT, id);
        values.put(InstructionsSqlite.NAME_PROPERTY_PRODUCT, propertyname);
        values.put(InstructionsSqlite.Type_PROPERTY_PRODUCT, type);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_PROPERTY_PRODUCT,null,values);
        db.close();
    }


    /**
     *
     *
     * @param memberid
     * @param businessid
     * @param likecount
     *
     */
    public void Add_LikeDisCount(Boolean likecount,Integer memberid,Integer discountid,Integer businessid){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        //values.put(instructionsSqlite.ID_LIKEDISCOUNT, id);
        values.put(InstructionsSqlite.LIKECOUNT_LIKEDISCOUNT, likecount);
        values.put(InstructionsSqlite.MEMBERID_LIKEDISCOUNT, memberid);
        values.put(InstructionsSqlite.DISCOUNTID_LIKEDISCOUNT, discountid);
        values.put(InstructionsSqlite.BUSINESSID_LIKEDISCOUNT, businessid);





        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_LikeDisCount, // table
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
    public void Add_DisCount(Integer id,String text,String image,String startdate,String expirationdate,String description,String percent,Integer businessid,Integer like,Integer dislike){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        
        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_DISCOUNT, id);
        values.put(InstructionsSqlite.TEXT_DISCOUNT, text);
        values.put(InstructionsSqlite.IMAGE_DISCOUNT, image);
        values.put(InstructionsSqlite.STARTDATE_DISCOUNT, startdate);
        values.put(InstructionsSqlite.EXPIRATIONDATE_DISCOUNT, expirationdate);
        values.put(InstructionsSqlite.DESCRIPTION_DISCOUNT, description);
        values.put(InstructionsSqlite.PERCENT_DISCOUNT, percent);
        values.put(InstructionsSqlite.BUSINESSID_DISCOUNT, businessid);
        values.put(InstructionsSqlite.LIKE_DISCOUNT, like);
        values.put(InstructionsSqlite.DISLIKE_DISCOUNT, dislike);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_DisCount, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();

    }

    public  void Update_DisCount(Integer id,Integer businessid,Integer like,Integer dislike)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put(InstructionsSqlite.LIKE_DISCOUNT,like);
        values.put(InstructionsSqlite.DISLIKE_DISCOUNT, dislike);
        db.update(InstructionsSqlite.TABLE_NAME_DisCount, values, "  Id=" + id + " AND BusinessId=" + businessid, null);
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
    public void Add_DisCountMember(Integer id,String text,String image,String startdate,String expirationdate,String description,String percent,Integer businessid){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(InstructionsSqlite.ID_DISCOUNTMEMBER, id);
        values.put(InstructionsSqlite.TEXT_DISCOUNTMEMBER, text);
        values.put(InstructionsSqlite.IMAGE_DISCOUNTMEMBER, image);
        values.put(InstructionsSqlite.STARTDATE_DISCOUNTMEMBER, startdate);
        values.put(InstructionsSqlite.EXPIRATIONDATE_DISCOUNTMEMBER, expirationdate);
        values.put(InstructionsSqlite.DESCRIPTION_DISCOUNTMEMBER, description);
        values.put(InstructionsSqlite.PERCENT_DISCOUNTMEMBER, percent);
        values.put(InstructionsSqlite.BUSINESSID_DISCOUNTMEMBER, businessid);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_DisCountMember, // table
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
    public void Add_Notification(Integer id,Integer OpinionType,Integer erja,Boolean ExecutionTime,String Description,String ExpirationDate,String City,Integer CityId,String Subset,Integer SubsetId,String title){
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_Notification, id);
        values.put(InstructionsSqlite.OpinionType_Notification, OpinionType);
        values.put(InstructionsSqlite.ErJa_Notification, erja);
        values.put(InstructionsSqlite.ExecutionTime_Notification, ExecutionTime);
        values.put(InstructionsSqlite.Description_Notification, Description);
        values.put(InstructionsSqlite.ExpirationDate_Notification, ExpirationDate);
        values.put(InstructionsSqlite.City_Notification, City);
        values.put(InstructionsSqlite.CityId_Notification, CityId);
        values.put(InstructionsSqlite.Subset_Notification, Subset);
        values.put(InstructionsSqlite.SubsetId_Notification, SubsetId);
        values.put(InstructionsSqlite.TITEL_Notification, title);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_NOTIFICATION, // table
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
        values.put(InstructionsSqlite.ID_colection, id);
        values.put(InstructionsSqlite.NAME_collection, collectionname);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_COLLECTION,null, //nullColumnHack
                values);
        db.close();
    }


    public void Add_collection_Product(Integer id, String collectionname) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_colection, id);
        values.put(InstructionsSqlite.NAME_collection, collectionname);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_COLLECTION_PRODUCT,null, //nullColumnHack
                values);
        db.close();
    }



    public void Add_member(Integer id,String name,String email,String mobile,Integer age,Boolean sex,String username,String password,Integer cityid)
    {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_member, id);
        values.put(InstructionsSqlite.NAME_member, name);
        values.put(InstructionsSqlite.EMAIL_member, email);
        values.put(InstructionsSqlite.MOBILE_member, mobile);
        values.put(InstructionsSqlite.AGE_member, age);
        values.put(InstructionsSqlite.SEX_member, sex);
        values.put(InstructionsSqlite.USERNAME_member, username);
        values.put(InstructionsSqlite.PASSWORD_member, password);
        values.put(InstructionsSqlite.CITYID_member, cityid);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_MEMBER, // table
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
        values.put(InstructionsSqlite.ID_opinion,id);
        values.put(InstructionsSqlite.DESCRIPTION_opinion,description);
        values.put(InstructionsSqlite.DATE_opinion,date);

        values.put(InstructionsSqlite.ERJA_opinion, erja);
        values.put(InstructionsSqlite.COUNTLIKE_opinion, countlike);
        values.put(InstructionsSqlite.COUNTDISLIKE_opinion, countdislike);
        values.put(InstructionsSqlite.MEMBERNAME_opinion, membername);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_OPINION, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }




    public void Add_Search(Integer id,String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid,Double latitude,Double longitude, Integer areaid, String area, String user,Integer cityid, Integer userid,Integer field1,Integer field2,Integer field3,Integer field4,Integer field5,Integer field6,Integer field7,Integer ratecount,Double ratevalue,String src) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_business, id);
        values.put(InstructionsSqlite.MARKET_business, market);
        values.put(InstructionsSqlite.CODE_business, code);
        values.put(InstructionsSqlite.PHONE_business, phone);
        values.put(InstructionsSqlite.MOBILE_business, mobile);
        values.put(InstructionsSqlite.FAX_business, fax);
        values.put(InstructionsSqlite.EMAIL_business, email);
        values.put(InstructionsSqlite.BUSINESSOWNER_business, businessowner);
        values.put(InstructionsSqlite.ADDRESS_business, address);
        values.put(InstructionsSqlite.DESCRIPTION_business, description);
        values.put(InstructionsSqlite.STARTDATE_business, startdate);
        values.put(InstructionsSqlite.EXPIRATIONDATE_business, expirationdate);
        values.put(InstructionsSqlite.INACTIVE_business, inactive);
        values.put(InstructionsSqlite.SUBSET_business, subset);
        values.put(InstructionsSqlite.SUBSETID_business, subsetid);
        values.put(InstructionsSqlite.LATITUDE_business, latitude);
        values.put(InstructionsSqlite.LONGITUDE_business, longitude);
        values.put(InstructionsSqlite.SRC_business, src);
        values.put(InstructionsSqlite.AREAID_business, areaid);
        values.put(InstructionsSqlite.AREA_business, area);
        values.put(InstructionsSqlite.USER_business, user);
        values.put(InstructionsSqlite.CITYID_business, cityid);
        values.put(InstructionsSqlite.USERID_business, userid);
        values.put(InstructionsSqlite.FIELD1_business, field1);
        values.put(InstructionsSqlite.FIELD2_business, field2);
        values.put(InstructionsSqlite.FIELD3_business, field3);
        values.put(InstructionsSqlite.FIELD4_business, field4);
        values.put(InstructionsSqlite.FIELD5_business, field5);
        values.put(InstructionsSqlite.FIELD6_business, field6);
        values.put(InstructionsSqlite.FIELD7_business, field7);
        values.put(InstructionsSqlite.RATECOUNT_business, ratecount);
        values.put(InstructionsSqlite.RATEVALUE_business, ratevalue);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_Search, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_businessDisCount(Integer id,String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid,Double latitude,Double longitude, Integer areaid, String area, String user,Integer cityid, Integer userid,Integer field1,Integer field2,Integer field3,Integer field4,Integer field5,Integer field6,Integer field7,Integer ratecount,Double ratevalue,String src) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_business, id);
        values.put(InstructionsSqlite.MARKET_business, market);
        values.put(InstructionsSqlite.CODE_business, code);
        values.put(InstructionsSqlite.PHONE_business, phone);
        values.put(InstructionsSqlite.MOBILE_business, mobile);
        values.put(InstructionsSqlite.FAX_business, fax);
        values.put(InstructionsSqlite.EMAIL_business, email);
        values.put(InstructionsSqlite.BUSINESSOWNER_business, businessowner);
        values.put(InstructionsSqlite.ADDRESS_business, address);
        values.put(InstructionsSqlite.DESCRIPTION_business, description);
        values.put(InstructionsSqlite.STARTDATE_business, startdate);
        values.put(InstructionsSqlite.EXPIRATIONDATE_business, expirationdate);
        values.put(InstructionsSqlite.INACTIVE_business, inactive);
        values.put(InstructionsSqlite.SUBSET_business, subset);
        values.put(InstructionsSqlite.SUBSETID_business, subsetid);
        values.put(InstructionsSqlite.LATITUDE_business, latitude);
        values.put(InstructionsSqlite.LONGITUDE_business, longitude);
        values.put(InstructionsSqlite.SRC_business, src);
        values.put(InstructionsSqlite.AREAID_business, areaid);
        values.put(InstructionsSqlite.AREA_business, area);
        values.put(InstructionsSqlite.USER_business, user);
        values.put(InstructionsSqlite.CITYID_business, cityid);
        values.put(InstructionsSqlite.USERID_business, userid);
        values.put(InstructionsSqlite.FIELD1_business, field1);
        values.put(InstructionsSqlite.FIELD2_business, field2);
        values.put(InstructionsSqlite.FIELD3_business, field3);
        values.put(InstructionsSqlite.FIELD4_business, field4);
        values.put(InstructionsSqlite.FIELD5_business, field5);
        values.put(InstructionsSqlite.FIELD6_business, field6);
        values.put(InstructionsSqlite.FIELD7_business, field7);
        values.put(InstructionsSqlite.RATECOUNT_business, ratecount);
        values.put(InstructionsSqlite.RATEVALUE_business, ratevalue);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_BUSINESS_DISCOUNT, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }


    public void Add_businessTops(Integer id,String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, Double latitude,Double longitude, Integer areaid, String area, String user,Integer cityid, Integer userid,Integer field1,Integer field2,Integer field3,Integer field4,Integer field5,Integer field6,Integer field7,Integer ratecount,Double ratevalue,String src) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_business, id);
        values.put(InstructionsSqlite.MARKET_business, market);
        values.put(InstructionsSqlite.CODE_business, code);
        values.put(InstructionsSqlite.PHONE_business, phone);
        values.put(InstructionsSqlite.MOBILE_business, mobile);
        values.put(InstructionsSqlite.FAX_business, fax);
        values.put(InstructionsSqlite.EMAIL_business, email);
        values.put(InstructionsSqlite.BUSINESSOWNER_business, businessowner);
        values.put(InstructionsSqlite.ADDRESS_business, address);
        values.put(InstructionsSqlite.DESCRIPTION_business, description);
        values.put(InstructionsSqlite.STARTDATE_business, startdate);
        values.put(InstructionsSqlite.EXPIRATIONDATE_business, expirationdate);
        values.put(InstructionsSqlite.INACTIVE_business, inactive);
        values.put(InstructionsSqlite.SUBSET_business, subset);
        values.put(InstructionsSqlite.SUBSETID_business, subsetid);
        values.put(InstructionsSqlite.LATITUDE_business, latitude);
        values.put(InstructionsSqlite.LONGITUDE_business, longitude);
        values.put(InstructionsSqlite.SRC_business, src);
        values.put(InstructionsSqlite.AREAID_business, areaid);
        values.put(InstructionsSqlite.AREA_business, area);
        values.put(InstructionsSqlite.USER_business, user);
        values.put(InstructionsSqlite.CITYID_business, cityid);
        values.put(InstructionsSqlite.USERID_business, userid);
        values.put(InstructionsSqlite.FIELD1_business, field1);
        values.put(InstructionsSqlite.FIELD2_business, field2);
        values.put(InstructionsSqlite.FIELD3_business, field3);
        values.put(InstructionsSqlite.FIELD4_business, field4);
        values.put(InstructionsSqlite.FIELD5_business, field5);
        values.put(InstructionsSqlite.FIELD6_business, field6);
        values.put(InstructionsSqlite.FIELD7_business, field7);
        values.put(InstructionsSqlite.RATECOUNT_business, ratecount);
        values.put(InstructionsSqlite.RATEVALUE_business, ratevalue);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_BUSINESS_TOPS, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_business(Integer id,String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, Double latitude,Double longitude, Integer areaid, String area, String user,Integer cityid, Integer userid,Integer field1,Integer field2,Integer field3,Integer field4,Integer field5,Integer field6,Integer field7,Integer ratecount,Double ratevalue,String src) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_business, id);
        values.put(InstructionsSqlite.MARKET_business, market);
        values.put(InstructionsSqlite.CODE_business, code);
        values.put(InstructionsSqlite.PHONE_business, phone);
        values.put(InstructionsSqlite.MOBILE_business, mobile);
        values.put(InstructionsSqlite.FAX_business, fax);
        values.put(InstructionsSqlite.EMAIL_business, email);
        values.put(InstructionsSqlite.BUSINESSOWNER_business, businessowner);
        values.put(InstructionsSqlite.ADDRESS_business, address);
        values.put(InstructionsSqlite.DESCRIPTION_business, description);
        values.put(InstructionsSqlite.STARTDATE_business, startdate);
        values.put(InstructionsSqlite.EXPIRATIONDATE_business, expirationdate);
        values.put(InstructionsSqlite.INACTIVE_business, inactive);
        values.put(InstructionsSqlite.SUBSET_business, subset);
        values.put(InstructionsSqlite.SUBSETID_business, subsetid);
        values.put(InstructionsSqlite.LATITUDE_business, latitude);
        values.put(InstructionsSqlite.LONGITUDE_business, longitude);
        values.put(InstructionsSqlite.SRC_business, src);
        values.put(InstructionsSqlite.AREAID_business, areaid);
        values.put(InstructionsSqlite.AREA_business, area);
        values.put(InstructionsSqlite.USER_business, user);
        values.put(InstructionsSqlite.CITYID_business, cityid);
        values.put(InstructionsSqlite.USERID_business, userid);
        values.put(InstructionsSqlite.FIELD1_business, field1);
        values.put(InstructionsSqlite.FIELD2_business, field2);
        values.put(InstructionsSqlite.FIELD3_business, field3);
        values.put(InstructionsSqlite.FIELD4_business, field4);
        values.put(InstructionsSqlite.FIELD5_business, field5);
        values.put(InstructionsSqlite.FIELD6_business, field6);
        values.put(InstructionsSqlite.FIELD7_business, field7);
        values.put(InstructionsSqlite.RATECOUNT_business, ratecount);
        values.put(InstructionsSqlite.RATEVALUE_business, ratevalue);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_BUSINESS, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }


    public void Add_business(String queryInsert) {

        //Integer id,String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, Double latitude,Double longitude, Integer areaid, String area, String user,Integer cityid, Integer userid,Integer field1,Integer field2,Integer field3,Integer field4,Integer field5,Integer field6,Integer field7,Integer ratecount,Double ratevalue,String src
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO  "+instructionsSqlite.TABLE_NAME_BUSINESS+"  Values "+queryInsert);
        // 4. close
        db.close();
    }
    public void Add_BusinessImage(Integer id, Integer businessid, String src) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_BusinessImage, id);
        values.put(InstructionsSqlite.BUSINESSID_BusinessImage, businessid);
        values.put(InstructionsSqlite.SRC_BusinessImage, src);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_BUSINESS_IMAGE, // table
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
        values.put(InstructionsSqlite.ID_city, id);
        values.put(InstructionsSqlite.NAME_city, name);
        values.put(InstructionsSqlite.PROVINCEID_city, provinceid);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_City, // table
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

        values.put(InstructionsSqlite.BUSINESSID_bookmark,businessid);
        values.put(InstructionsSqlite.MEMBERID_bookmark, memberid);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_Bookmark, // table
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
        values.put(InstructionsSqlite.ID_area, id);
        values.put(InstructionsSqlite.NAME_area, areaname);
        values.put(InstructionsSqlite.CITYID_area, cityid);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_Area, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }


    public void Add_Update(String date) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();

        values.put(InstructionsSqlite.DATE_Update, date);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_UpdateTime, // table
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
        values.put(InstructionsSqlite.ID_Like, id);
        values.put(InstructionsSqlite.LIKE_Like, like);
        values.put(InstructionsSqlite.MEMBERID_Like, memberid);
        values.put(InstructionsSqlite.OPINION_Like, opinionid);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_Like, // table
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

        values.put(InstructionsSqlite.SUBSETID_Interest, subsetid);
        values.put(InstructionsSqlite.MEMBERID_Interest, memberid);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_Interest, // table
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
        values.put(InstructionsSqlite.ID_FIELDACTIVITY,id);
        values.put(InstructionsSqlite.ACTIVITY_FIELDACTIVITY, activity);


        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_FieldActivity, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    /**
     *
     * @param businessid
     */
    public void Add_ShowNotification(Integer id,Integer businessid,Boolean show) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_SHOWNOTIFICATION,id);
        values.put(InstructionsSqlite.BUSINESSID_SHOWNOTIFICATION,businessid);
        values.put(InstructionsSqlite.SHOW_SHOWNOTIFICATION,show);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_SHOWNOTIFICATION, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_Advertisment(Integer id,String image,String link,Integer type) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_Advertisment,id);
        values.put(InstructionsSqlite.IMAGE_Advertisment,image);
        values.put(InstructionsSqlite.LINK_Advertisment,link);
        values.put(InstructionsSqlite.TYPE_Advertisment,type);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_Advertisment, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }


    public void Add_SubsetProperty(Integer id,Integer productsubsetid,Integer propertyid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(InstructionsSqlite.ID_SubsetProperty_PRODUCT,id);
        values.put(InstructionsSqlite.PRODUCTSUBSETID_SubsetProperty_PRODUCT,productsubsetid);
        values.put(InstructionsSqlite.PROPERTYID_SubsetProperty_PRODUCT,propertyid);

        // 3. insert
        db.insert(InstructionsSqlite.TABLE_NAME_SubsetProperty_PRODUCT, // table
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
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS, null);

    }

    public Cursor select_BusinessSearchNearMe(Double latitude,Double logntitude,Double near,Integer Subsetid)
    {
        String query="";

        query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                "Latitude+ " +near+">="+latitude+" AND Latitude-"+near+"<="+latitude+
                ")AND(Longitude+ "+near+">="+logntitude+" AND Longitude-"+near+"<="+logntitude+") AND SubsetId="+Subsetid;

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);

    }

    public Cursor select_BusinessSearchNearMe(Double latitude,Double logntitude,Double near)
    {
        String query="";

        query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                "Latitude+ " +near+">="+latitude+" AND Latitude-"+near+"<="+latitude+
                ")AND(Longitude+ "+near+">="+logntitude+" AND Longitude-"+near+"<="+logntitude+")";

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);

    }


    public Cursor select_BusinessSearch(String namemarket,Integer cityid)
    {
        query="";
        Log.i("select_BusinessSearch","one");
        query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                " Market like'%" + namemarket +"%'" +
                " or Address like '%" + namemarket +
                "%'"+" ) AND (CityId="+cityid+")";

        Log.i("select_BusinessSearch",query);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }


    public Cursor select_BusinessSearch(String namemarket,Integer cityid,Integer fieldactivty)
    {
        query="";
        Log.i("select_BusinessSearch","one");
            query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    "  Field1="+fieldactivty+
                    " or Field2="+fieldactivty+
                    " or Field3="+fieldactivty+
                    " or Market like'%" + namemarket +"%'" +
                    " or Address like '%" + namemarket +
                    "%'"+" ) AND (CityId="+cityid+")";

        Log.i("select_BusinessSearch",query);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public Cursor select_BusinessSearch(String namemarket,Integer cityid,Integer fieldactivity,Integer fieldactivity2)
    {
        String query="";
        Log.i("select_BusinessSearch", "one");
        query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                "  Field1="+fieldactivity+
                " or Field1="+fieldactivity2+
                " or Field2="+fieldactivity+
                " or Field2="+fieldactivity2+
                " or Field3="+fieldactivity+
                " or Field3="+fieldactivity2+
                " or Market like'%" + namemarket +
                "%' or Address like '%" + namemarket +
                "%'"+ " ) AND (CityId="+cityid+")";

        Log.i("select_BusinessSearch",query);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public Cursor select_BusinessSearch(String namemarket,Integer cityid,Integer fieldactivity,Integer fieldactivity2,Integer fieldactivity3)
    {
        query="";
        Log.i("select_BusinessSearch", "one");
        query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                "  Field1="+fieldactivity+
                " or Field1="+fieldactivity2+
                " or Field1="+fieldactivity3+
                " or Field2="+fieldactivity+
                " or Field2="+fieldactivity2+
                " or Field2="+fieldactivity3+
                " or Field3="+fieldactivity+
                " or Field3="+fieldactivity2+
                " or Field3="+fieldactivity3+
                " or Market like'%" + namemarket +
                "%' or Address like '%" + namemarket +
                "%' "+" ) AND (CityId="+cityid+")";

        Log.i("select_BusinessSearch",query);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public Cursor select_BusinessAdvanceSearch(String namemarket,String address,Integer cityid,Integer subsetId)
    {
        query="";
        Log.i("select_BusinessSearch","one");
        query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                " SubsetId="+subsetId+
                ") and (Market like'%" + namemarket +
                "%') and ( Address like '%" + address +
                "%') AND (CityId="+cityid+")";
        Log.i("select_BusinessSearch",query);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }

    public Cursor select_BusinessFieldAdvanceSearch(String namemarket,String address,Integer cityid,Integer Field)
    {
        query="";
        Log.i("select_BusinessSearch","one");
        query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                " Field1="+Field +
                " OR Field2="+Field+
                " OR Field3="+Field+
                " OR Field4="+Field+
                " OR Field5="+Field+
                " OR Field6="+Field+
                " OR Field7="+Field+
                ") And ( Market like'%" + namemarket +
                "%' AND Address like '%" + address +
                "%') AND (Field1="+Field+" OR Field2="+Field+
                " OR Field3="+Field+" OR Field4="+Field+" OR Field5="+Field+
                " OR Field6="+Field+" OR Field7="+Field+") AND (CityId="+cityid+")";
        Log.i("select_BusinessSearch",query);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);
    }


    public Cursor select_BusinessSearchBazarYab(Integer SubsetId,String txtMarket,String address,Integer arreaId)
    {
        query="";
        Log.i("select_BusinessSearch","one");
        query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +" Market like'%" + txtMarket + "%' AND Address like '%" + address + "%') AND (AreaId="+arreaId+") AND (SubsetId="+SubsetId+")";

        Log.i("select_BusinessSearch",query);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);

    }


    public Cursor select_BusinessSearch(String namemarket1,String namemarket2,String namemarket3,String namemarket4,String namemarket5,Integer cityid)
    {

        query="";
        Log.i("namemarket[len]",String.valueOf(namemarket1.length()));
        Log.i("namemarket[len]",String.valueOf(namemarket2.length()));
        Log.i("namemarket[len]",String.valueOf(namemarket3.length()));
        Log.i("namemarket[len]",String.valueOf(namemarket4.length()));
        Log.i("namemarket[len]",String.valueOf(namemarket5.length()));

        if(namemarket1.length()>0 && namemarket2.length()>0 && namemarket3.length()>0 && namemarket4.length()>0 && namemarket5.length()>0)
        {
            Log.i("search 5 word",namemarket1+","+namemarket2+","+namemarket3+","+namemarket4+","+namemarket5);
            query = "select * from " + InstructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Market like'%" + namemarket1 +
                    "%' or Market like '%" + namemarket2 +
                    "%' or Market like '%" + namemarket3 +
                    "%' or Market like '%" + namemarket4 +
                    "%' or Market like '%" + namemarket5 +
                    "%') or (Address like '%" + namemarket1 +
                    "%' or Address like '%" + namemarket2 +
                    "%' or Address like '%" + namemarket3 +
                    "%' or Address like '%" + namemarket4 +
                    "%' or Address like '%" + namemarket5 +
                    "%') AND (CityId="+cityid+")";
        }
        else if(namemarket1.length()>0 && namemarket2.length()>0 && namemarket3.length()>0 && namemarket4.length()>0)
        {
            Log.i("search 4 word",namemarket1+","+namemarket2+","+namemarket3+","+namemarket4);
            query = "select * from " + InstructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Market like'%" + namemarket1 +
                    "%' or Market like '%" + namemarket2 +
                    "%' or Market like '%" + namemarket3 +
                    "%' or Market like '%" + namemarket4 +
                    "%') or (Address like '%" + namemarket1 +
                    "%' or Address like '%" + namemarket2 +
                    "%' or Address like '%" + namemarket3 +
                    "%' or Address like '%" + namemarket4 +
                    "%') AND (CityId="+cityid+")";
        }
        else if(namemarket1.length()>0 && namemarket2.length()>0 && namemarket3.length()>0 )
        {
            Log.i("search 3 word",namemarket1+","+namemarket2+","+namemarket3);
            query = "select * from " + InstructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Market like'%" + namemarket1 +
                    "%' or Market like '%" + namemarket2 +
                    "%' or Market like '%" + namemarket3 +
                    "%') or (Address like '%" + namemarket1 +
                    "%' or Address like '%" + namemarket2 +
                    "%' or Address like '%" + namemarket3 +
                    "%' ) AND (CityId="+cityid+")";
        }
        else if(namemarket1.length()>0 && namemarket2.length()>0  )
        {
            Log.i("search 2 word",namemarket1+","+namemarket2);
            query = "select * from " + InstructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Market like'%" + namemarket1 +
                    "%' or Market like '%" + namemarket2 +
                    "%') or (Address like '%" + namemarket1 +
                    "%' or Address like '%" + namemarket2 +
                    "%') AND (CityId="+cityid+")";
        }
        else if(namemarket1.length()>0)
        {
            Log.i("search 1 word",namemarket1);
            query = "select * from " + InstructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Market like'%" + namemarket1 +
                    "%') or (Address like '%" + namemarket1 +
                    "%') AND (CityId="+cityid+")";
        }
        Log.i("namemarket[0]",namemarket1);
        Log.i("namemarket[1]", namemarket2);
        Log.i("namemarket[2]", namemarket3);
        Log.i("namemarket[1]", namemarket4);
        Log.i("namemarket[2]", namemarket5);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);

    }

    public Cursor select_BusinessSearch(String namemarket1,String namemarket2,String namemarket3,String namemarket4,String namemarket5,Integer SubsetId,Integer cityid,Integer fieldactivity)
    {
        query="";
        Log.i("namemarket[len]",String.valueOf(namemarket1.length()));
        Log.i("namemarket[len]",String.valueOf(namemarket2.length()));
        Log.i("namemarket[len]",String.valueOf(namemarket3.length()));
        Log.i("namemarket[len]",String.valueOf(namemarket4.length()));
        Log.i("namemarket[len]",String.valueOf(namemarket5.length()));

        if(namemarket1.length()>0 && namemarket2.length()>0 && namemarket3.length()>0 && namemarket4.length()>0 && namemarket5.length()>0)
        {
            Log.i("search 5 word",namemarket1+","+namemarket2+","+namemarket3+","+namemarket4+","+namemarket5);
            query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Field1="+fieldactivity+
                    " or Field2="+fieldactivity+
                    " or Field3="+fieldactivity+
                    " or Market like'%" + namemarket1 +
                    "%' or Market like '%" + namemarket2 +
                    "%' or Market like '%" + namemarket3 +
                    "%' or Market like '%" + namemarket4 +
                    "%' or Market like '%" + namemarket5 +
                    "%') or (Address like '%" + namemarket1 +
                    "%' or Address like '%" + namemarket2 +
                    "%' or Address like '%" + namemarket3 +
                    "%' or Address like '%" + namemarket4 +
                    "%' or Address like '%" + namemarket5 +
                    "%') AND (SubsetId="+SubsetId+" or Field1="+fieldactivity+ ") AND (CityId="+cityid+")";
        }
        else if(namemarket1.length()>0 && namemarket2.length()>0 && namemarket3.length()>0 && namemarket4.length()>0 && fieldactivity>0)
        {
            Log.i("search 4 word",namemarket1+","+namemarket2+","+namemarket3+","+namemarket4);
            query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Field1="+fieldactivity+
                    " or Field2="+fieldactivity+
                    " or Field3="+fieldactivity+
                    " or Market like'%" + namemarket1 +
                    "%' or Market like '%" + namemarket2 +
                    "%' or Market like '%" + namemarket3 +
                    "%' or Market like '%" + namemarket4 +
                    "%') or (Address like '%" + namemarket1 +
                    "%' or Address like '%" + namemarket2 +
                    "%' or Address like '%" + namemarket3 +
                    "%' or Address like '%" + namemarket4 +
                    "%') AND (SubsetId="+SubsetId+ " or Field1="+fieldactivity+ ") AND (CityId="+cityid+")";
        }
        else if(namemarket1.length()>0 && namemarket2.length()>0 && namemarket3.length()>0  && fieldactivity>0 )
        {
            Log.i("search 3 word", namemarket1 + "," + namemarket2 + "," + namemarket3);
            query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Field1="+fieldactivity+
                    " or Field2="+fieldactivity+
                    " or Field3="+fieldactivity+
                    " or Market like'%" + namemarket1 +
                    "%' or Market like '%" + namemarket2 +
                    "%' or Market like '%" + namemarket3 +
                    "%') or (Address like '%" + namemarket1 +
                    "%' or Address like '%" + namemarket2 +
                    "%' or Address like '%" + namemarket3 +
                    "%') AND (SubsetId="+SubsetId+ " or Field1="+fieldactivity+ ") AND (CityId="+cityid+")";
        }
        else if(namemarket1.length()>0 && namemarket2.length()>0 && fieldactivity>0 )
        {
            Log.i("search 2 word", namemarket1 + "," + namemarket2);
            query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Field1="+fieldactivity+
                    " or Field2="+fieldactivity+
                    " or Field3="+fieldactivity+
                    " or Market like'%" + namemarket1 +
                    "%' or Market like '%" + namemarket2 +
                    "%') or (Address like '%" + namemarket1 +
                    "%' or Address like '%" + namemarket2 +
                    "%') AND (SubsetId="+SubsetId+ " or Field1="+fieldactivity+ " ) AND (CityId="+cityid+")";
        }
        else if(namemarket1.length()>0 && fieldactivity>0)
        {
            Log.i("search 1 word",namemarket1);
            query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Field1="+fieldactivity+
                    " or Market like'%" + namemarket1 +"%') " +
                    " or (Address like '%" + namemarket1 +"%') " +
                    " AND (SubsetId="+SubsetId+ " or Field1="+fieldactivity+") AND (CityId="+cityid+")";
        }
        else if(fieldactivity<=0)
        {
            Log.i("fieldactivity",String.valueOf(fieldactivity));
            query = "select * from " + instructionsSqlite.TABLE_NAME_BUSINESS + " where(" +
                    " Market like'%" + namemarket1 +"%') " +
                    " or (Address like '%" + namemarket1 +"%') " +
                    " AND (SubsetId="+SubsetId+ " or Field1="+fieldactivity+") AND (CityId="+cityid+")";
        }

        Log.i("namemarket[0]",namemarket1);
        Log.i("namemarket[1]", namemarket2);
        Log.i("namemarket[2]", namemarket3);
        Log.i("namemarket[1]", namemarket4);
        Log.i("namemarket[2]", namemarket5);
        Log.i("query", query);
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery(query, null);

    }

    public Cursor select_BusinessSearchAddreass(String address)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS +" WHERE Address like '%"+address+"%'", null);

    }

    public Cursor select_AllBusinessTops()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS_TOPS, null);

    }

    public Cursor select_AllBusinessTops(Integer businessid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS_TOPS+" WHERE Id="+businessid, null);

    }

    public Cursor select_AllBusinessDisCount()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS_DISCOUNT, null);

    }

    public Cursor select_AllBusinessDisCount(Integer businessid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS_DISCOUNT+" WHERE Id="+businessid, null);

    }


    public Cursor select_CountBusiness_SubsetId(Integer subsetID)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" + subsetID, null);

    }

    public Cursor select_CountBusinessId(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE Id=" + id, null);

    }

    public Cursor select_AllBusiness(Integer subsetID,Integer cityid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId="+subsetID+" AND CityId=" +cityid , null);

    }

    public Cursor select_AllBusiness(Integer subsetID)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId="+subsetID, null);

    }

    public Cursor select_SortRateBusiness(Integer subsetID,Integer cityid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" +subsetID+" AND CityId="+cityid+" ORDER BY (RateValue * RateCount) DESC", null);
    }

    public Cursor select_SortNameBusiness(Integer subsetID,Integer cityid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" +subsetID+" AND CityId="+cityid+" ORDER BY "+ InstructionsSqlite.MARKET_business +" ASC", null);
    }

    public Cursor select_SortDateBusiness(Integer subsetID,Integer cityid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" +subsetID+" AND CityId="+cityid+" ORDER BY "+ InstructionsSqlite.ID_business +" DESC", null);
    }

    public Cursor select_AllBusinessId(Integer id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id,Market,Phone,Mobile,Fax,Email,BusinessOwner,Address,Description,SubsetId,Latitude,Longitude,Field1,Field2,Field3,Field4,Field5,Field6,Field7,RateValue,AreaId,Src,CityId  FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE Id=" +id, null);
    }

    public Cursor select_BusinessAndBusinessImage(Integer id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("select p.Id,p.Market,p.Address,P.RateValue,H.Src from  "+ InstructionsSqlite.TABLE_NAME_BUSINESS+"  P inner join "+InstructionsSqlite.TABLE_NAME_BUSINESS_IMAGE+" H on (H.BusinessId ="+id+" and  P.Id="+id+" )" +id, null);
    }

    public Cursor select_business_NameMarket(Integer businessID)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Market FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE Id=" +businessID , null);

    }

    //end business



    public Cursor select_FieldActivity()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_FieldActivity, null);
    }
    public Cursor select_ShowNotificationBusinessId(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM " + InstructionsSqlite.TABLE_NAME_SHOWNOTIFICATION +" WHERE Id="+id , null);
    }
    public Cursor select_AllDisCount()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_DisCount, null);
    }

    public Cursor select_DisCountId(Integer businessid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + InstructionsSqlite.TABLE_NAME_DisCount +" WHERE BusinessId="+businessid , null);
    }

    public Cursor select_DisCount(Integer businessid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_DisCount +" WHERE BusinessId="+businessid , null);
    }

    public Cursor select_AllDisCountMember()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_DisCountMember, null);
    }

    public Cursor select_DisCountMember(Integer businessid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_DisCountMember+ " WHERE BusinessId="+businessid, null);
    }

    public Cursor select_AllDisCountMember(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_DisCountMember +" WHERE Id="+id , null);
    }

    public Cursor select_FieldActivityId(String fieldname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + InstructionsSqlite.TABLE_NAME_FieldActivity +" WHERE Activity='"+fieldname+"'" , null);
    }
    public Cursor select_SearchFieldActivityId(String fieldname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + InstructionsSqlite.TABLE_NAME_FieldActivity +" WHERE Activity like'%"+fieldname+"%'" , null);
    }


    public Cursor select_FieldActivityName(Integer id)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Activity FROM " + InstructionsSqlite.TABLE_NAME_FieldActivity +" WHERE Id="+id , null);
    }

    public Cursor SearchBusiness(String nameMarket,Integer subsetId,Integer cityid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE SubsetId=" +subsetId+" AND Market LIKE '%"+nameMarket+"%'" +" AND CityId="+cityid, null);

    }

    //End Select Business



    public Cursor select_TableSearch(String market)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_Search + "  WHERE Market Like '%"+market+"%'", null);

    }

    public Cursor select_TableSearch()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_Search , null);

    }

    public Cursor select_TableSearchSortRate()
    {


        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_Search + " ORDER BY (RateValue * RateCount)  DESC", null);

    }

    public Cursor select_TableSearchSortName()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_Search + " ORDER BY Market ASC", null);

    }

    public Cursor select_TableSearchSortId()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_Search+ " ORDER BY Id  ASC", null);

    }


    public Cursor select_SubsetId(String subsetname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + InstructionsSqlite.TABLE_NAME_SUBSET + "  WHERE SubsetName= '" + subsetname+"'", null);
    }

    public Cursor select_AdvanceSubsetId(String subsetname)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + InstructionsSqlite.TABLE_NAME_SUBSET + "  WHERE SubsetName='" + subsetname+"'", null);
    }

    public Cursor select_SubsetProductId(String subsetname)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + InstructionsSqlite.TABLE_NAME_SUBSET_PRODUCT + "  WHERE SubsetName='" + subsetname+"'", null);

    }

    public Cursor select_SubsetId(Integer CollectionId)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + instructionsSqlite.TABLE_NAME_SUBSET + "  WHERE CollectionId=" +CollectionId, null);

    }

    public Cursor select_SubsetName(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT SubsetName FROM " + InstructionsSqlite.TABLE_NAME_SUBSET + "  WHERE Id=" +id, null);

    }

    public  Cursor select_CollectionId(Integer subsetid)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT CollectionId FROM " + InstructionsSqlite.TABLE_NAME_SUBSET + "  WHERE Id=" +subsetid, null);

    }
    public Cursor select_CityName(Integer id)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Name FROM " + InstructionsSqlite.TABLE_NAME_City + "  WHERE id=" +id, null);

    }

    public Cursor select_business_Detail(String market,String address)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_BUSINESS + "  WHERE Market='" + market+"' AND Address='"+address+"'", null);

    }

    public  Cursor select_Interest()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite. TABLE_NAME_Interest, null);
    }
    public Cursor select_opinion()
    {

    SQLiteDatabase db = this.getReadableDatabase();
    return db.rawQuery("SELECT * FROM " + InstructionsSqlite. TABLE_NAME_OPINION, null);

    }

    public Cursor select_bookmark()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_Bookmark, null);

    }

    public Cursor select_bookmarkId(Integer businessid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + InstructionsSqlite.TABLE_NAME_Bookmark +" WHERE BusinessId="+businessid, null);

    }

    public Cursor select_opinion_BusinessId(Integer busintessid)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_OPINION + "  WHERE Erja="+busintessid, null);

    }

    public Cursor select_Member()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite.TABLE_NAME_MEMBER, null);
    }

    public Cursor select_Update()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + InstructionsSqlite. TABLE_NAME_UpdateTime, null);

    }

    public Cursor select_Member_Name()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Name FROM " + InstructionsSqlite.TABLE_NAME_MEMBER, null);
    }



    public Cursor select_Collection()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_COLLECTION+" ORDER BY CollectionName ASC",null);
    }

    public Cursor select_Collection_Product()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_COLLECTION_PRODUCT,null);
    }

    public Cursor select_Collection_Product(String namecollection)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT Id FROM "+ InstructionsSqlite.TABLE_NAME_COLLECTION_PRODUCT+" WHERE CollectionName='"+namecollection+"'",null);
    }



    public Cursor select_Collection(String collectionname)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT Id FROM "+instructionsSqlite.TABLE_NAME_COLLECTION+" WHERE CollectionName like '%"+collectionname+"%'",null);
    }


    public Cursor select_Subset(Integer CollectionId)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM " + instructionsSqlite.TABLE_NAME_SUBSET+ " WHERE CollectionId="+CollectionId, null);
    }

    public Cursor select_Subset()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_SUBSET +"  ORDER BY SubsetName ASC",null);
    }

    public Cursor select_Subset_Product()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_SUBSET_PRODUCT,null);
    }

    public Cursor select_Subset_Product(Integer collectionid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_SUBSET_PRODUCT+" WHERE CollectionId="+collectionid,null);
    }

    public Cursor select_SearchSubset(String subsetName)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return  db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_SUBSET +" WHERE SubsetName LIKE '%"+subsetName+"%'",null);
    }

    public  Cursor select_AllNotificaton()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_NOTIFICATION,null);

    }




    public  Cursor select_NotificatonId(Integer Id)
    {

            SQLiteDatabase db = this.getReadableDatabase();
            return db.rawQuery("SELECT Id FROM " + InstructionsSqlite.TABLE_NAME_NOTIFICATION + " WHERE Id=" + Id, null);

    }

    public  Cursor select_CountCollection()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM "+ InstructionsSqlite.TABLE_NAME_COLLECTION,null);
    }

    public  Cursor select_AllArea()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_Area,null);
    }

    public  Cursor select_Area(Integer cityid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_Area+" WHERE CityId="+cityid,null);
    }


    public  Cursor select_BusinessImage(Integer businessid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_BUSINESS_IMAGE+" WHERE BusinessId="+businessid,null);
    }


    public  Cursor select_Advertisment()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_Advertisment,null);
    }

    public  Cursor select_AreaId(String name)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM "+ InstructionsSqlite.TABLE_NAME_Area +" WHERE AreaName='"+name+"'",null);
    }

    public  Cursor select_AreaName(Integer id)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT AreaName FROM "+ InstructionsSqlite.TABLE_NAME_Area +" WHERE id="+id,null);
    }

    public  Cursor select_CountSubset()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT COUNT(*) FROM "+ InstructionsSqlite.TABLE_NAME_SUBSET,null);
    }

    public  Cursor select_AllCity()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_City + " ORDER BY Name",null);
    }

    public  Cursor select_CityId(String cityname)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM "+ InstructionsSqlite.TABLE_NAME_City +" WHERE Name='"+cityname+"'",null);
    }

    public  Cursor select_SubsetProperty_Product()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM "+ InstructionsSqlite.TABLE_NAME_SubsetProperty_PRODUCT,null);
    }


    public  Cursor select_SubsetProperty_Product(Integer subsetpropertyid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT PropertyId FROM "+ InstructionsSqlite.TABLE_NAME_SubsetProperty_PRODUCT+" WHERE ProductSubsetId="+subsetpropertyid,null);
    }

    public  Cursor select_Property_Product(Integer propertyid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT Name,Type FROM "+ InstructionsSqlite.TABLE_NAME_PROPERTY_PRODUCT+" WHERE Id="+propertyid,null);
    }

    public  Cursor select_Property_Product()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_PROPERTY_PRODUCT,null);
    }

    public  Cursor select_Value_Product()
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_VALUE_PRODUCT,null);
    }

    public  Cursor select_Value_Product(Integer propertyid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM "+ InstructionsSqlite.TABLE_NAME_VALUE_PRODUCT+" WHERE IdProperty="+propertyid,null);
    }

    public  Cursor select_ValueName_Product(Integer valueid)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT Name FROM "+ InstructionsSqlite.TABLE_NAME_VALUE_PRODUCT+" WHERE Id="+valueid,null);
    }

    public  Cursor select_Value_Product(String namevalue)
    {
        SQLiteDatabase db=this.getReadableDatabase();
        return db.rawQuery("SELECT Id FROM "+ InstructionsSqlite.TABLE_NAME_VALUE_PRODUCT+" WHERE Name='"+namevalue+"'",null);
    }

    // Deleting Opinion
    public void delete_Opinion() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  " + InstructionsSqlite.TABLE_NAME_OPINION);
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
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_MEMBER);
        db.close();

    }


    public void delete_Interest(Integer subsetid,Integer memberid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_Interest +" WHERE SubsetId="+subsetid+" AND MemberId="+memberid);
        db.close();

    }
    public void delete_Interest()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_Interest);
        db.close();

    }

    /**
     *
     */
    public void delete_Area()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_Area);
        db.close();

    }

    /**
     *
     */
    public void delete_City()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_City);
        db.close();

    }

    /**
     *
     */
    public void delete_FiledActivity()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_FieldActivity);
        db.close();

    }

    public void delete_DisCountMember(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_DisCountMember +" WHERE Id="+id);
        db.close();

    }

    public void delete_DisCountMember()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_DisCountMember);
        db.close();

    }
    public void delete_LikeDisCount(Integer memberid,Integer discountid,Integer businessid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_LikeDisCount +" WHERE MemberId="+memberid+" AND DisCountId="+discountid+" AND BusinessId="+businessid);
        db.close();

    }
    /**
     *
     */
    public void delete_Subset()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_SUBSET);
        db.close();

    }


    public void delete_Subset_Product()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_SUBSET_PRODUCT);
        db.close();

    }


    public void delete_BusinessImage()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_BUSINESS_IMAGE);
        db.close();
    }


    public void delete_BusinessImage(Integer Id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_BUSINESS_IMAGE+" WHERE Id="+Id);
        db.close();
    }

    public void delete_BusinessImage(String src,Integer businessid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_BUSINESS_IMAGE+" WHERE BusinessId="+businessid+" AND  Src='"+src+"'");
        db.close();
    }

    public void delete_Advertisment()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_Advertisment);
        db.close();

    }
    /**
     *
     */
    public void delete_Collection()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_COLLECTION);
        db.close();

    }

    public void delete_Collection_Product()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_COLLECTION_PRODUCT);
        db.close();

    }

    /**
     * delete all record table notification
     */

    public void delete_Notification()
    {
        Log.i("DeleteNotification", "delete Run");
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_NOTIFICATION);
        db.close();
    }

    public void delete_Notification(Integer id)
    {
        Log.i("DeleteNotification", "delete Run id");
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_NOTIFICATION+" WHERE Id="+id);
        db.close();
    }

    public void delete_BusinessId(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_BUSINESS +" WHERE Id="+id);
        db.close();
    }

    public void delete_Business(Integer cityid,Integer subsetid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_BUSINESS +" WHERE CityId="+cityid+" AND SubsetId="+subsetid);
        db.close();
    }




    public void delete_BusinessTops()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_BUSINESS_TOPS );
        db.close();
    }
    public void delete_BusinessDisCount()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_BUSINESS_DISCOUNT );
        db.close();
    }

    public void delete_DisCount(Integer id)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE  FROM "+ InstructionsSqlite.TABLE_NAME_DisCount +" WHERE Id="+id);
        db.close();
    }

/*    public void delete_Notification(Integer erja)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+TABLE_NAME_NOTIFICATION+" WHERE ErJa="+erja,null);
        db.close();
    }*/


    // Deleting ZamanSanj
    public void delete_Update() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. delete
        db.execSQL("DELETE FROM  " + InstructionsSqlite.TABLE_NAME_UpdateTime);



        // 3. close
        db.close();



    }

    public  void delete_Business()
    {
        SQLiteDatabase db=this.getWritableDatabase();
       // delete
        db.execSQL("DELETE FROM  " + InstructionsSqlite.TABLE_NAME_BUSINESS);
        //close
        db.close();
    }

    public  void delete_Search()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        // delete
        db.execSQL("DELETE FROM  " + InstructionsSqlite.TABLE_NAME_Search);
        //close
        db.close();
    }

    public  void delete_bookmark()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ InstructionsSqlite.TABLE_NAME_Bookmark);
        db.close();
    }

    public  void delete_Value_Product()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ InstructionsSqlite.TABLE_NAME_VALUE_PRODUCT);
        db.close();
    }

    public  void delete_SubsetProperty_Product()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ InstructionsSqlite.TABLE_NAME_SubsetProperty_PRODUCT);
        db.close();
    }



    public  void delete_Property_Product()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ InstructionsSqlite.TABLE_NAME_PROPERTY_PRODUCT);
        db.close();
    }


    public  void delete_bookmark(Integer businessid)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        db.execSQL("DELETE FROM "+ InstructionsSqlite.TABLE_NAME_Bookmark+" WHERE BusinessId="+businessid);
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
