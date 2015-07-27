package com.ariana.shahre_ma.DateBaseSqlite;

/**
 * Created by ariana on 7/11/2015.
 */
public class  InstructionsSqlite {


    // Database table name
    public static final String TABLE_NAME_SUBSET= "subset";
    public static final String TABLE_NAME_COLLECTION = "collection";
    public static final String TABLE_NAME_MEMBER = "member";
    public static final String TABLE_NAME_OPINION = "opinion";
    public static final String TABLE_NAME_BUSINESS = "business";
    public static final String TABLE_NAME_City   = "city";
    public static final String TABLE_NAME_Bookmark = "bookmark";
    public static final String TABLE_NAME_Area = "area";
    public static final String TABLE_NAME_UpdateTime = "UpdateTime";
    public static final String TABLE_NAME_Like = "Like";
    public static final String TABLE_NAME_Interest = "Interest";
    public static final String TABLE_NAME_NOTIFICATION="Notification";
    public static final String TABLE_NAME_SHOWNOTIFICATION="ShowNotification";
    public static final String TABLE_NAME_FieldActivity="FieldActivity";
    public static final String TABLE_NAME_DisCount="DisCount";
    public static final String TABLE_NAME_DisCountMember="DisCountMember";
    public static final String TABLE_NAME_LikeDisCount="LikeDisCount";
    




    //Notification Table Columns names
    public static final String ID_Notification="Id";
    public static final String OpinionType_Notification="OpinionType";
    public static final String ErJa_Notification="Erja";
    public static final String ExecutionTime_Notification="ExecutionTime";
    public static final String Description_Notification="Description";
    public static final String ExpirationDate_Notification="ExpirationDate";
    public static final String City_Notification="City";
    public static final String CityId_Notification="CityId";
    public static final String Subset_Notification="Subset";
    public static final String SubsetId_Notification="SubsetId";


    //FieldActivity Table Columns names
    public static final String ID_FIELDACTIVITY="Id";
    public static final String ACTIVITY_FIELDACTIVITY="Activity";


    //UpdateTime Table Columns names
    public static final String ID_Update = "Id";
    public static final String DATE_Update = "Date";

    //Area Table Columns names
    public static final String ID_area = "Id";
    public static final String NAME_area = "AreaName";
    public static final String CITYID_area = "CityId";


    //Bookmark Table Columns names
    public static final String ID_bookmark = "Id";
    public static final String BUSINESSID_bookmark = "BusinessId";
    public static final String MEMBERID_bookmark = "MemberId";

    //ShowNotification Table Columns names
    public static final String ID_SHOWNOTIFICATION = "Id";
    public static final String BUSINESSID_SHOWNOTIFICATION = "BusinessId";
    public static final String SHOW_SHOWNOTIFICATION = "Show";



    //City Table Columns names
    public static final String ID_city = "Id";
    public static final String NAME_city = "Name";
    public static final String PROVINCEID_city = "ProvinceId";

    //Like Table Columns names
    public static final String ID_Like = "Id";
    public static final String LIKE_Like = "Like";
    public static final String MEMBERID_Like = "MemberId";
    public static final String OPINION_Like = "OpinionId";

    //Interest Table Columns names

    public static final String SUBSETID_Interest = "SubsetId";
    public static final String MEMBERID_Interest = "MemberId";


    //LikeDisCount Table Columns names
    public static final String ID_LIKEDISCOUNT = "Id";
    public static final String LIKECOUNT_LIKEDISCOUNT = "Like";
    public static final String MEMBERID_LIKEDISCOUNT = "MemberId";
    public static final String DISCOUNTID_LIKEDISCOUNT = "DisCountId";
    public static final String BUSINESSID_LIKEDISCOUNT = "BusinessId";

    //DisCoutn Table Columns names
    public static final String ID_DISCOUNT = "Id";
    public static final String TEXT_DISCOUNT = "Text";
    public static final String IMAGE_DISCOUNT = "Image";
    public static final String STARTDATE_DISCOUNT = "StartDate";
    public static final String EXPIRATIONDATE_DISCOUNT = "ExpirationDate";
    public static final String DESCRIPTION_DISCOUNT= "Description";
    public static final String PERCENT_DISCOUNT = "Percent";
    public static final String BUSINESSID_DISCOUNT = "BusinessId";
    public static final String LIKE_DISCOUNT = "Like";
    public static final String DISLIKE_DISCOUNT = "DisLike";

    //DisCoutnMember Table Columns names
    public static final String ID_DISCOUNTMEMBER = "Id";
    public static final String TEXT_DISCOUNTMEMBER = "Text";
    public static final String IMAGE_DISCOUNTMEMBER = "Image";
    public static final String STARTDATE_DISCOUNTMEMBER = "StartDate";
    public static final String EXPIRATIONDATE_DISCOUNTMEMBER = "ExpirationDate";
    public static final String DESCRIPTION_DISCOUNTMEMBER= "Description";
    public static final String PERCENT_DISCOUNTMEMBER = "Percent";
    public static final String BUSINESSID_DISCOUNTMEMBER= "BusinessId";

    //Opinion Table Columns names
    public static final String ID_opinion = "Id";
    public static final String DESCRIPTION_opinion = "Description";
    public static final String DATE_opinion = "Date";
    public static final String MEMBERNAME_opinion = "MemberName";
    public static final String ERJA_opinion = "Erja";
    public static final String COUNTLIKE_opinion = "CountLike";
    public static final String COUNTDISLIKE_opinion = "CountDisLike";


    //subset Table Columns names
    public static final String ID_subset = "Id";
    public static final String NAME_subset = "SubsetName";
    public static final String COLLECTIONID_subset = "CollectionId";

    //collection Table Columns names
    public static final String ID_colection = "Id";
    public static final String NAME_collection = "CollectionName";


    //Member Table Columns names
    public static final String ID_member = "id";
    public static final String NAME_member = "Name";
    public static final String EMAIL_member = "Email";
    public static final String MOBILE_member = "Mobile";
    public static final String AGE_member = "Age";
    public static final String SEX_member = "Sex";
    public static final String USERNAME_member = "UserName";
    public static final String PASSWORD_member = "Password";
    public static final String CITYID_member = "CityId";


    //business Table Columns names
    public static final String ID_business = "Id";
    public static final String MARKET_business = "Market";
    public static final String CODE_business = "Code";
    public static final String PHONE_business = "Phone";
    public static final String MOBILE_business = "Mobile";
    public static final String FAX_business = "Fax";
    public static final String EMAIL_business = "Email";
    public static final String BUSINESSOWNER_business = "BusinessOwner";
    public static final String ADDRESS_business = "Address";
    public static final String DESCRIPTION_business = "Description";
    public static final String STARTDATE_business = "Startdate";
    public static final String EXPIRATIONDATE_business = "ExpirationDate";
    public static final String INACTIVE_business = "Inactive";
    public static final String SUBSET_business = "Subset";
    public static final String SUBSETID_business = "SubsetId";
    public static final String LONGITUDE_business = "Longitude";
    public static final String LATITUDE_business = "Latitude";
    public static final String AREAID_business = "AreaId";
    public static final String AREA_business = "Area";
    public static final String USER_business = "User";
    public static final String CITYID_business = "CityId";
    public static final String USERID_business = "UserId";
    public static final String FIELD1_business = "Field1";
    public static final String FIELD2_business = "Field2";
    public static final String FIELD3_business = "Field3";
    public static final String FIELD4_business = "Field4";
    public static final String FIELD5_business = "Field5";
    public static final String FIELD6_business = "Field6";
    public static final String FIELD7_business = "Field7";
    public static final String RATECOUNT_business = "RateCount";
    public static final String RATEVALUE_business = "RateValue";


    // SQL statement to create area table
    public static final String CREATE_TABLE_Area = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_Area + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "AreaName TEXT," +
            "CityId INTEGER" +
            ");";

    //SQL statement to create notification table
    public static final String CREATE_TABLE_Notification = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_NOTIFICATION + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "OpinionType INTEGER," +
            "ErJa INTEGER," +
            "ExecutionTime BOOLEAN ," +
            "Description TEXT ," +
            "ExpirationDate TEXT ," +
            "City TEXT," +
            "CityId INTEGER," +
            "Subset TEXT ," +
            "SubsetId INTEGER " +
            ");";

    //SQL statement to create DisCoutn table
    public static final String CREATE_TABLE_DisCount = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_DisCount + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "Text TEXT," +
            "Image TEXT," +
            "StartDate TEXT ," +
            "ExpirationDate TEXT ," +
            "Description TEXT ," +
            "Percent TEXT," +
            "BusinessId INTEGER," +
            "Like INTEGER," +
            "DisLike INTEGER" +
            ");";

    //SQL statement to create DisCoutn table
    public static final String CREATE_TABLE_DisCountMember = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_DisCountMember + " (" +
            "Id INTEGER PRIMARY KEY  ," +
            "Text TEXT," +
            "Image TEXT," +
            "StartDate TEXT ," +
            "ExpirationDate TEXT ," +
            "Description TEXT ," +
            "Percent TEXT," +
            "BusinessId INTEGER" +
            ");";

    //SQL statement to create DisCoutn table
    public static final String CREATE_TABLE_LikeDisCount = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_LikeDisCount + " (" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "Like BOOLEAN, " +
            "MemberId INTEGER," +
            "DisCountId INTEGER," +
            "BusinessId INTEGER" +
            ");";
    // SQL statement to create fieldactivity table
    public static final String CREATE_TABLE_FieldActivity = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_FieldActivity + " (" +
            " Id INTEGER PRIMARY KEY ," +
            " Activity TEXT" +
            ");";

    // SQL statement to create city table
    public static final String CREATE_TABLE_City  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_City + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "Name TEXT," +
            "ProvinceId INTEGER" +
            ");";

    // SQL statement to create ZamanSanj table
    public static final String CREATE_TABLE_Update = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_UpdateTime + " (" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "Date TEXT" +
            ");";

    // SQL statement to create business table
    public static final String CREATE_TABLE_Business  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_BUSINESS + " (" +
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
            " CityId INTEGER ," +
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

    // SQL statement to create Like table
    public static final String CREATE_TABLE_Like  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_Like + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "Like BOOLEAN," +
            "MemberId TEXT," +
            "OpinionID INTEGER" +

            ");";

    // SQL statement to create Interest table
    public static final String CREATE_TABLE_Interest = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_Interest + " (" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT ," +
            "SubsetId INTEGER," +
            "MemberId INTEGER" +
            ");";

    // SQL statement to create Opinion table
    public static final String CREATE_TABLE_Opinion  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_OPINION + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "Description TEXT," +
            "Date TEXT," +
            "Erja INTEGER," +
            "CountLike INTEGER," +
            "CountDisLike INTEGER," +
            "MemberName TEXT" +
            ");";


    // SQL statement to create Member table
    public static final String CREATE_TABLE_Member  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_MEMBER + " (" +
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
    public static final String CREATE_TABLE_Collection  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_COLLECTION + " (" +
            " Id INTEGER PRIMARY KEY ," +
            " CollectionName TEXT" +
            ");";

    // SQL statement to create Subset table
    public static final String CREATE_TABLE_Subset  = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_SUBSET + " (" +
            "Id INTEGER PRIMARY KEY ," +
            "SubsetName TEXT," +
            "CollectionId INTEGER" +
            ");";


    // SQL statement to create bookmark table
    public static final String CREATE_TABLE_Bookmark = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_Bookmark + " (" +
            "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "BusinessId INTEGER," +
            "MemberId INTEGER" +
            ");";

    // SQL statement to create ShowNotification table
    public static final String CREATE_TABLE_ShowNotification = "CREATE TABLE  IF  NOT EXISTS " + TABLE_NAME_SHOWNOTIFICATION + " (" +
            "Id INTEGER PRIMARY KEY, " +
            "BusinessId INTEGER , " +
            "Show BOOLEAN " +
            ");";
}
