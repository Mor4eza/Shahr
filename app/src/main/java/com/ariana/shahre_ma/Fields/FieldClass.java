package com.ariana.shahre_ma.Fields;

/**
 * Created by ariana2 on 6/5/2015.
 */
public class FieldClass
{



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
    private static final String TABLE_NAME_UpdateTime = "UpdateTime";
    private static final String TABLE_NAME_Like = "Like";
    private static final String TABLE_NAME_Interest = "Interest";
    private static final String TABLE_NAME_NOTIFICATION="Notification";



    // variable get json
    private static String data_json;

    // Field  member table
    private static String Name;
    private static String Email;
    private static String Mobile;
    private static Integer Age;
    private static Boolean Sex;
    private static String UserName;
    private static String Password;
    private static Integer CityId;


    //Field BookMark table
    private static Integer Id_Bookmark;
    private static Integer Businessid_Bookmark;
    private static Integer Memberid_Bookmark;

    // Field  Opinion table
    private static String Description;
    private static String Date;
    private static String MemberName;
    private static Integer Erja;
    private static Integer CountLike;
    private static Integer CountDisLike;

    private static String  selected_job;
    private static Integer Count_Business;
    private static Integer Business_SubsetId;
    private static Integer SubsetId;
    private static Integer Business_Id;
    private static String Market_Business;
    private static String Address_Business;


    private static String Latitude_Business;
    private static String Longtiude_Business;


    // get/set

//***********************************************************************
    // Set json Member
    public void  SetMember_Json(String json_member)
{
    data_json=json_member;
}

    // Get  Member
    public String GetMember_json()
    {
        return data_json;
    }
    //*********************************************************************
    // Set  Member
    public void  SetMember_Name(String name)
    {
        Name=name;
    }
    // Set  Member
    public void  SetMember_Email(String email)
    {
        Email=email;
    }
    // Set  Member
    public void  SetMember_Mobile(String mobile)
    {
        Mobile=mobile;
    }
    // Set  Member
    public void  SetMember_Age(Integer age)
    {
        Age=age;
    }
    // Set  Member
    public void  SetMember_Sex(Boolean sex)
    {
        Sex=sex;
    }
    // Set  Member
    public void  SetMember_UserName(String username)
    {
        UserName=username;
    }
    // Set  Member
    public void  SetMember_Password(String password)
    {
        Password=password;
    }
    // Set  Member
    public void  SetMember_CityId(Integer cityid)
    {
        CityId=cityid;
    }


    // Get  Member
    public String  GetMember_Name()
    {
        return  Name ;
    }
    // Get  Member
    public String  GetMember_Email()
    {
        return  Email;
    }
    // Get  Member
    public String  GetMember_Mobile()
    {
        return  Mobile;
    }
    // Get  Member
    public Integer  GetMember_Age()
    {
        return  Age;
    }
    // Get  Member
    public Boolean  GetMember_Sex()
    {
        return Sex;
    }
    // Get  Member
    public String  GetMember_UserName()
    {
        return  UserName;
    }
    // Get  Member
    public String  GetMember_Password()
    {
        return  Password;
    }
    // Get  Member
    public Integer  GetMember_CityId()
    {
        return  CityId;
    }


    // Get and Set field opinion

    // Set  opinion
    public void  SetOpinion_Description(String description)
    {
        Description=description;
    }
    // Set  opinion
    public void  SetOpinion_Date(String date)
    {
        Date=date;
    }
    // Set  opinion
    public void  SetOpinion_MemberName(String membername)
    {
        MemberName=membername;
    }
    // Set  opinion
    public void  SetOpinion_Erja(Integer erja)
    {
        Erja=erja;
    }
    // Set  opinion
    public void  SetOpinion_CountLike(Integer countlike)
    {
        CountLike=countlike;
    }
    // Set  opinion
    public void  SetOpinion_CountDisLike(Integer countdislike)
    {
        CountDisLike=countdislike;
    }


    // Get  opinion
    public String  GetOpinion_Description()
    {
        return Description;
    }
    // Get  opinion
    public String  GetOpinion_Date()
    {
        return  Date;
    }
    // Get  opinion
    public String  GetOpinion_MemberName()
    {
        return  MemberName;
    }
    // Get  opinion
    public Integer  GetOpinion_Erja()
    {
        return  Erja;
    }
    // Get  opinion
    public Integer  GetOpinion_CountLike()
    {
        return  CountLike;
    }
    // Get  opinion
    public Integer  GetOpinion_CountDisLike()
    {
        return  CountDisLike;
    }


    // Set and Get Selected_job
    public void  SetSelected_job(String selected)
    {
        selected_job=selected;
    }
    public String  GetSelected_job()
    {
        return  selected_job;
    }


    // Set and Get Selected_job
    public void  SetBusiness_SubsetId(Integer SubsetId)
    {
        Business_SubsetId=SubsetId;
    }
    public Integer  GetBusiness_SubsetIdb()
    {
        return  Business_SubsetId;
    }


    // Set  BookMark
    public void  SetMemberid_BookMark(Integer memberid)
    {
        Memberid_Bookmark=memberid;
    }
    public void  SetBusinessid_BookMark(Integer businessid)
    {
          Businessid_Bookmark=businessid;
    }


    //  Get BookMark
    public Integer  GetMemberid_BookMark()
    {
        return Memberid_Bookmark;
    }
    public Integer  GetBusinessid_BookMark()
    {
        return  Businessid_Bookmark;
    }


    // Set and Get Description_business/Market_Business

    // Set  Market_Business
    public void  SetMarket_Business(String market)
    {
        Market_Business=market;
    }

    // Set  Description_business
    public void  SetAddress_Business(String tozih)
    {
        Address_Business=tozih;
    }


    // Get  Market_Business
    public String  GetMarket_Business()
    {
        return Market_Business;
    }

    // Get  Description_business
    public String  GetAddress_Business()
    {
        return  Address_Business;
    }


    // Set and Get Business_Id
    public void  SetBusiness_Id(Integer id)
    {
        Business_Id=id;
    }
    public Integer  GetBusiness_Id()
    {
        return  Business_Id;
    }



    // Get database

    // Get  subset
    public String  GetTableNameSubset()
    {
        return  TABLE_NAME_SUBSET ;
    }
    // Get  collection
    public String  GetTableNameCollection()
    {
        return  TABLE_NAME_COLLECTION;
    }
    // Get  member
    public String  GetTableNameMember()
    {
        return  TABLE_NAME_MEMBER;
    }
    // Get  opinion
    public String  GetTableNameOpinion()
    {
        return  TABLE_NAME_OPINION;
    }
    // Get  business
    public String  GeTableNameBusiness()
    {
        return TABLE_NAME_BUSINESS;
    }
    // Get  city
    public String  GetTableNamecity()
    {
        return  TABLE_NAME_City;
    }
    // Get  bookmark
    public String  GetTableNameBookmark()
    {
        return  TABLE_NAME_Bookmark;
    }
    // Get  area
    public String  GetTableNameArea()
    {
        return  TABLE_NAME_Area;
    }
    // Get  Interest
    public String  GetTableNameInterest()
    {
        return  TABLE_NAME_Interest;
    }
    // Get  Like
    public String  GetMeTableNameLike()
    {
        return  TABLE_NAME_Like;
    }
    // Get  ZamanSanj
    public String  GetTableNameUpdateTime()
    {
        return  TABLE_NAME_UpdateTime;
    }
    // Get  Notification
    public String  GetTableNameNotification()
    {
        return  TABLE_NAME_NOTIFICATION;
    }

    // Get  database
    public String  GetDataBaseName()
    {
        return  DATABASE_NAME;
    }


   // get and set latitude/longtiude

    public String  GetLatitude_Business()
    {
        return Latitude_Business;
    }
    public String  GetLongtiude_Business()
    {
        return  Longtiude_Business;
    }

    public void  SetLatitude_Business(String latitude)
    {
        Latitude_Business=latitude;
    }
    public void  SetLongtiude_Business(String longtiude)
    {
        Longtiude_Business=longtiude;
    }

    /**
     * Get and Set Count Business
     */
    public void  SetCount_Business(Integer count)
    {
        Count_Business=count;
    }
    public Integer  GetCount_Business()
    {
        return Count_Business;
    }

    /**
     * Get and Set Count SubsetId
     */
    public void  SetSubsetId(Integer Idsubset)
    {
        this.SubsetId=Idsubset;
    }
    public Integer  GetSubsetId()
    {
        return this.SubsetId;
    }
}
