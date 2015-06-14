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

    // Database table name
    private static final String TABLE_NAME_SUBSET= "subset";
    private static final String TABLE_NAME_COLLECTION = "collection";
    private static final String TABLE_NAME_MEMBER = "member";
    private static final String TABLE_NAME_OPINION = "opinion";
    private static final String TABLE_NAME_BUSINESS = "business";
    private static final String TABLE_NAME_City   = "city";
    private static final String TABLE_NAME_Bookmark = "bookmark";
    private static final String TABLE_NAME_Area = "area";


    //Area Table Columns names
    private static final String ID_area = "Id";
    private static final String NAME_area = "AreaName";
    private static final String CITYID_area = "CityId";


    //Bookmark Table Columns names
    private static final String ID_bookmark = "Id";
    private static final String BUSINESSID_bookmark = "BusinessId";
    private static final String MEMBERID_bookmark = "MemberId";

    //City Table Columns names
    private static final String ID_city = "Id";
    private static final String NAME_city = "Name";
    private static final String PROVINCEID_city = "ProvinceId";


    //Opinion Table Columns names
    private static final String ID_opinion = "Id";
    private static final String DESCRIPTION_opinion = "Description";
    private static final String DATE_opinion = "Date";
    private static final String OPINIONTYPE_opinion = "OpinionType";
    private static final String ERJA_opinion = "Erja";


    //subset Table Columns names
    private static final String ID_subset = "Id";
    private static final String NAME_subset = "SubsetName";
    private static final String COLLECTIONID_subset = "CollectionId";

    //collection Table Columns names
    private static final String ID_colection = "Id";
    private static final String NAME_collection = "CollectionName";


    //Member Table Columns names
    private static final String ID_member = "id";
    private static final String NAME_member = "Name";
    private static final String EMAIL_member = "Email";
    private static final String MOBILE_member = "Mobile";
    private static final String AGE_member = "Age";
    private static final String SEX_member = "Sex";
    private static final String USERNAME_member = "UserName";
    private static final String PASSWORD_member = "Password";
    private static final String CITYID_member = "CityId";


    //business Table Columns names
    private static final String ID_business = "Id";
    private static final String MARKET_business = "Market";
    private static final String CODE_business = "Code";
    private static final String PHONE_business = "Phone";
    private static final String MOBILE_business = "Mobile";
    private static final String FAX_business = "Fax";
    private static final String EMAIL_business = "Email";
    private static final String BUSINESSOWNER_business = "BusinessOwner";
    private static final String ADDRESS_business = "Address";
    private static final String DESCRIPTION_business = "Description";
    private static final String STARTDATE_business = "Startdate";
    private static final String EXPIRATIONDATE_business = "ExpirationDate";
    private static final String INACTIVE_business = "Inactive";
    private static final String SUBSET_business = "Subset";
    private static final String SUBSETID_business = "SubsetId";
    private static final String LONGITUDE_business = "Longitude";
    private static final String LATITUDE_business = "Latitude";
    private static final String AREAID_business = "AreaId";
    private static final String AREA_business = "Area";
    private static final String USER_business = "User";
    private static final String USERID_business = "UserId";
    private static final String FIELD1_business = "Field1";
    private static final String FIELD2_business = "Field2";
    private static final String FIELD3_business = "Field3";
    private static final String FIELD4_business = "Field4";
    private static final String FIELD5_business = "Field5";
    private static final String FIELD6_business = "Field6";
    private static final String FIELD7_business = "Field7";
    private static final String RATECOUNT_business = "RateCount";
    private static final String RATEVALUE_business = "RateValue";


    // SQL statement to create area table
    private static final String CREATE_TABLE_Area = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_Area + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "AreaName TEXT," +
            "CityId INTEGER" +
            ");";


    // SQL statement to create city table
    private static final String CREATE_TABLE_City  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_City + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "Name TEXT," +
            "ProvinceId INTEGER" +
            ");";

    // SQL statement to create business table
    private static final String CREATE_TABLE_Business  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_BUSINESS + " (" +
            " Id INTEGER PRIMARY KEY ," +
            " Market TEXT ," +
            " Code TEXT ," +
            " Phone TEXT ," +
            " Mobile TEXT ," +
            " Fax TEXT ," +
            " Email TEXT ," +
            " BusinessOwner TEXT ," +
            " Address TEXT ," +
            " Description TEXT ," +
            " Startdate TEXT ," +
            " ExpirationDate TEXT ," +
            " Inactive TEXT ," +
            " Subset TEXT ," +
            " SubsetId INTEGER ," +
            " Longitude TEXT," +
            " Latitude TEXT," +
            " AreaId INTEGER ," +
            " Area TEXT ," +
            " User TEXT ," +
            " UserId INTEGER," +
            " Field1 INTEGER," +
            " Field2 INTEGER," +
            " Field3 INTEGER," +
            " Field4 INTEGER," +
            " Field5 INTEGER," +
            " Field6 INTEGER," +
            " Field7 INTEGER, " +
            " RateCount INTEGER ," +
            " RateValue DOUBLE " +

            ");";


    // SQL statement to create Opinion table
    private static final String CREATE_TABLE_Opinion  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_OPINION + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "Description TEXT," +
            "Date TEXT," +
            "OpinionType INTEGER," +
            "Erja INTEGER" +
            ");";


    // SQL statement to create Member table
    private static final String CREATE_TABLE_Member  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_MEMBER + " (" +
            " Id INTEGER PRIMARY KEY," +
            " Name TEXT ," +
            " Email TEXT ," +
            " Mobile TEXT ," +
            " Age INTEGER ," +
            " Sex Boolean ," +
            " UserName TEXT ," +
            " Password TEXT ," +
            " CityId INTEGER " +
            ");";


    // SQL statement to create Collection table
    private static final String CREATE_TABLE_Collection  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_COLLECTION + " (" +
            " Id INTEGER PRIMARY KEY ," +
            " CollectionName TEXT" +
            ");";

    // SQL statement to create Subset table
    private static final String CREATE_TABLE_Subset  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_SUBSET + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "SubsetName TEXT," +
            "CollectionId INTEGER" +
            ");";


    // SQL statement to create bookmark table
    private static final String CREATE_TABLE_Bookmark = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_Bookmark + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "BusinessId INTEGER," +
            "MemberId INTEGER" +
            ");";



    public  DataBaseSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL(CREATE_TABLE_Subset);
        db.execSQL(CREATE_TABLE_Collection);
        db.execSQL(CREATE_TABLE_Member);
        db.execSQL(CREATE_TABLE_Opinion);
        db.execSQL(CREATE_TABLE_Business);
        db.execSQL(CREATE_TABLE_City);
        db.execSQL(CREATE_TABLE_Bookmark);
        db.execSQL(CREATE_TABLE_Area);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older  tables if existed
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_SUBSET);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_COLLECTION);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_MEMBER);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_OPINION);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_BUSINESS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_City);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_Bookmark);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME_Area);

        // create fresh  tables
        this.onCreate(db);
    }

    public void Add_subset(Integer id, String subsetname, Integer collectionid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(ID_subset, id);
        values.put(NAME_subset, subsetname);
        values.put(COLLECTIONID_subset, collectionid);

        // 3. insert
        db.insert(TABLE_NAME_SUBSET, // table
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
        values.put(ID_colection, id);
        values.put(NAME_collection, collectionname);

        // 3. insert
        db.insert(TABLE_NAME_COLLECTION, // table
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
        values.put(ID_member,id);
        values.put(NAME_member,name);
        values.put(EMAIL_member,email);
        values.put(MOBILE_member, mobile);
        values.put(AGE_member,age);
        values.put(SEX_member, sex);
        values.put(USERNAME_member,username);
        values.put(PASSWORD_member,password);
        values.put(CITYID_member,cityid);


        // 3. insert
        db.insert(TABLE_NAME_MEMBER, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_opinion(Integer id,String description,String date,Integer opiniontype,Integer erja)
    {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(ID_opinion,id);
        values.put(DESCRIPTION_opinion,description);
        values.put(DATE_opinion,date);
        values.put(OPINIONTYPE_opinion, opiniontype);
        values.put(ERJA_opinion, erja);



        // 3. insert
        db.insert(TABLE_NAME_OPINION, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }





    public void Add_business(Integer id,String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, String longitude, String latitude, Integer areaid, String area, String user, Integer userid,Integer field1,Integer field2,Integer field3,Integer field4,Integer field5,Integer field6,Integer field7,Integer ratecount,Double ratevalue) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(ID_business, id);
        values.put(MARKET_business, market);
        values.put(CODE_business, code);
        values.put(PHONE_business, phone);
        values.put(MOBILE_business, mobile);
        values.put(FAX_business, fax);
        values.put(EMAIL_business, email);
        values.put(BUSINESSOWNER_business, businessowner);
        values.put(ADDRESS_business, address);
        values.put(DESCRIPTION_business, description);
        values.put(STARTDATE_business, startdate);
        values.put(EXPIRATIONDATE_business, expirationdate);
        values.put(INACTIVE_business, inactive);
        values.put(SUBSET_business, subset);
        values.put(SUBSETID_business, subsetid);
        values.put(LONGITUDE_business, longitude);
        values.put(LATITUDE_business, latitude);
        values.put(AREAID_business, areaid);
        values.put(AREA_business, area);
        values.put(USER_business, user);
        values.put(USERID_business, userid);
        values.put(FIELD1_business, field1);
        values.put(FIELD2_business, field2);
        values.put(FIELD3_business, field3);
        values.put(FIELD4_business, field4);
        values.put(FIELD5_business, field5);
        values.put(FIELD6_business, field6);
        values.put(FIELD7_business, field7);
        values.put(FIELD6_business, ratecount);
        values.put(FIELD7_business, ratevalue);


        // 3. insert
        db.insert(TABLE_NAME_BUSINESS, // table
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
        values.put(ID_city, id);
        values.put(NAME_city, name);
        values.put(PROVINCEID_city, provinceid);

        // 3. insert
        db.insert(TABLE_NAME_City, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_bookmark(Integer id,Integer businessid,Integer memberid)
    {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(ID_bookmark,id);
        values.put(BUSINESSID_bookmark,businessid);
        values.put(MEMBERID_bookmark, memberid);

        // 3. insert
        db.insert(TABLE_NAME_Bookmark, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }

    public void Add_area( String areaname, Integer cityid) {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(NAME_area, areaname);
        values.put(CITYID_area, cityid);

        // 3. insert
        db.insert(TABLE_NAME_Area, // table
                null, //nullColumnHack
                values); // key/value

        // 4. close
        db.close();
    }


    public Cursor select_business_SubsetId(Integer subsetID)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_BUSINESS + "  WHERE SubsetId=" + subsetID, null);

    }

    public Cursor select_business_Detail(String market,String address)
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_BUSINESS + "  WHERE Market='" + market+"' AND Address='"+address+"'", null);

    }

    public Cursor select_opinion(Integer busintessid)
{

    SQLiteDatabase db = this.getReadableDatabase();
    return db.rawQuery("SELECT * FROM " + TABLE_NAME_OPINION+ "  WHERE Erja="+busintessid, null);

}
    public Cursor select_Member()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME_MEMBER, null);

    }

    public Cursor select_Member_Name()
    {

        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT Name FROM " + TABLE_NAME_MEMBER, null);

    }
    // Deleting Opinion
    public void delete_Opinion() {

        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM  " + TABLE_NAME_OPINION);
        // 2. delete
        // db.delete(TABLE_BOOKS_MEMBER,ID," =",id);

        // 3. close
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
