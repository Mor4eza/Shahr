package com.ariana.shahre_ma.DateBaseSqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by ariana on 9/4/2015.
 */
public class AddDataBaseSqlite extends DataBaseSqlite{

    public AddDataBaseSqlite(Context context) {
        super(context);
    }

    @Override
    public void Add_subset(Integer id, String subsetname, Integer collectionid) {
        super.Add_subset(id, subsetname, collectionid);
    }

    @Override
    public void Add_LikeDisCount(Boolean likecount, Integer memberid, Integer discountid, Integer businessid) {
        super.Add_LikeDisCount(likecount, memberid, discountid, businessid);
    }

    @Override
    public void Add_subset_Product(Integer id, String subsetname, Integer collectionid) {
        super.Add_subset_Product(id, subsetname, collectionid);
    }

    @Override
    public void Add_Search(Integer id, String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, Double latitude, Double longitude, Integer areaid, String area, String user, Integer cityid, Integer userid, Integer field1, Integer field2, Integer field3, Integer field4, Integer field5, Integer field6, Integer field7, Integer ratecount, Double ratevalue, String src) {
        super.Add_Search(id, market, code, phone, mobile, fax, email, businessowner, address, description, startdate, expirationdate, inactive, subset, subsetid, latitude, longitude, areaid, area, user, cityid, userid, field1, field2, field3, field4, field5, field6, field7, ratecount, ratevalue, src);
    }

    @Override
    public void Add_DisCount(Integer id, String text, String image, String startdate, String expirationdate, String description, String percent, Integer businessid, Integer like, Integer dislike) {
        super.Add_DisCount(id, text, image, startdate, expirationdate, description, percent, businessid, like, dislike);
    }

    @Override
    public void Update_DisCount(Integer id, Integer businessid, Integer like, Integer dislike) {
        super.Update_DisCount(id, businessid, like, dislike);
    }

    @Override
    public void Add_Property_Product(Integer id, String propertyname, Integer type) {
        super.Add_Property_Product(id, propertyname, type);
    }

    @Override
    public void Add_SubsetProperty(Integer id, Integer propertyid, Integer productsubsetid) {
        super.Add_SubsetProperty(id, propertyid, productsubsetid);
    }

    @Override
    public void Add_Value_Product(Integer id, String valuename, Integer idProperty) {
        super.Add_Value_Product(id, valuename, idProperty);
    }

    @Override
    public void Add_ShowNotification(Integer id, Integer businessid, Boolean show) {
        super.Add_ShowNotification(id, businessid, show);
    }

    @Override
    public void Add_FieldActivity(Integer id, String activity) {
        super.Add_FieldActivity(id, activity);
    }

    @Override
    public void Add_Interest(Integer subsetid, Integer memberid) {
        super.Add_Interest(subsetid, memberid);
    }

    @Override
    public void Add_Like(Integer id, Boolean like, Integer memberid, Integer opinionid) {
        super.Add_Like(id, like, memberid, opinionid);
    }

    @Override
    public void Add_Update(String date) {
        super.Add_Update(date);
    }

    @Override
    public void Add_area(Integer id, String areaname, Integer cityid) {
        super.Add_area(id, areaname, cityid);
    }

    @Override
    public void Add_city(Integer id, String name, Integer provinceid) {
        super.Add_city(id, name, provinceid);
    }

    @Override
    public void Add_bookmark(Integer businessid, Integer memberid) {
        super.Add_bookmark(businessid, memberid);
    }

    @Override
    public void Add_BusinessImage(Integer id, Integer businessid, String src) {
        super.Add_BusinessImage(id, businessid, src);
    }

    @Override
    public void Add_businessTops(Integer id, String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, Double latitude, Double longitude, Integer areaid, String area, String user, Integer cityid, Integer userid, Integer field1, Integer field2, Integer field3, Integer field4, Integer field5, Integer field6, Integer field7, Integer ratecount, Double ratevalue, String src) {
        super.Add_businessTops(id, market, code, phone, mobile, fax, email, businessowner, address, description, startdate, expirationdate, inactive, subset, subsetid, latitude, longitude, areaid, area, user, cityid, userid, field1, field2, field3, field4, field5, field6, field7, ratecount, ratevalue, src);
    }

    @Override
    public void Add_businessDisCount(Integer id, String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, Double latitude, Double longitude, Integer areaid, String area, String user, Integer cityid, Integer userid, Integer field1, Integer field2, Integer field3, Integer field4, Integer field5, Integer field6, Integer field7, Integer ratecount, Double ratevalue, String src) {
        super.Add_businessDisCount(id, market, code, phone, mobile, fax, email, businessowner, address, description, startdate, expirationdate, inactive, subset, subsetid, latitude, longitude, areaid, area, user, cityid, userid, field1, field2, field3, field4, field5, field6, field7, ratecount, ratevalue, src);
    }

    @Override
    public void Add_opinion(Integer id, String description, String date, Integer erja, Integer countlike, Integer countdislike, String membername) {
        super.Add_opinion(id, description, date, erja, countlike, countdislike, membername);
    }

    @Override
    public void Add_member(Integer id, String name, String email, String mobile, Integer age, Boolean sex, String username, String password, Integer cityid) {
        super.Add_member(id, name, email, mobile, age, sex, username, password, cityid);
    }

    @Override
    public void Add_collection_Product(Integer id, String collectionname) {
        super.Add_collection_Product(id, collectionname);
    }

    @Override
    public void Add_collection(Integer id, String collectionname) {
        super.Add_collection(id, collectionname);
    }

    @Override
    public void Add_Notification(Integer id, Integer OpinionType, Integer erja, Boolean ExecutionTime, String Description, String ExpirationDate, String City, Integer CityId, String Subset, Integer SubsetId, String title) {
        super.Add_Notification(id, OpinionType, erja, ExecutionTime, Description, ExpirationDate, City, CityId, Subset, SubsetId, title);
    }

    @Override
    public void Add_DisCountMember(Integer id, String text, String image, String startdate, String expirationdate, String description, String percent, Integer businessid) {
        super.Add_DisCountMember(id, text, image, startdate, expirationdate, description, percent, businessid);
    }

    @Override
    public void Add_Advertisment(Integer id, String image, String link, Integer type) {
        super.Add_Advertisment(id, image, link, type);
    }

    @Override
      public void Add_business(Integer id, String market, String code, String phone, String mobile, String fax, String email, String businessowner, String address, String description, String startdate, String expirationdate, String inactive, String subset, Integer subsetid, Double latitude, Double longitude, Integer areaid, String area, String user, Integer cityid, Integer userid, Integer field1, Integer field2, Integer field3, Integer field4, Integer field5, Integer field6, Integer field7, Integer ratecount, Double ratevalue, String src) {
        super.Add_business(id, market, code, phone, mobile, fax, email, businessowner, address, description, startdate, expirationdate, inactive, subset, subsetid, latitude, longitude, areaid, area, user, cityid, userid, field1, field2, field3, field4, field5, field6, field7, ratecount, ratevalue, src);
    }

    @Override
    public void Add_business(String queryInsert) {
        super.Add_business(queryInsert);
    }
}
