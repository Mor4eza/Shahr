package com.ariana.shahre_ma.DateBaseSqlite;

import android.content.Context;
import android.database.Cursor;

/**
 * Created by ariana on 9/4/2015.
 */
public class SelectDataBaseSqlite extends DataBaseSqlite {
    public SelectDataBaseSqlite(Context context) {
        super(context);
    }

    @Override
    public Cursor select_AllBusiness() {
        return super.select_AllBusiness();
    }

    @Override
    public Cursor select_BusinessSearchNearMe(Double latitude, Double logntitude, Double near, Integer Subsetid) {
        return super.select_BusinessSearchNearMe(latitude, logntitude, near, Subsetid);
    }

    @Override
    public Cursor select_BusinessSearchNearMe(Double latitude, Double logntitude, Double near) {
        return super.select_BusinessSearchNearMe(latitude, logntitude, near);
    }

    @Override
    public Cursor select_BusinessSearch(String namemarket, Integer cityid, Integer fieldactivty) {
        return super.select_BusinessSearch(namemarket, cityid, fieldactivty);
    }

    @Override
    public Cursor select_TableSearch() {
        return super.select_TableSearch();
    }

    @Override
    public Cursor select_BusinessSearch(String namemarket, Integer cityid, Integer fieldactivity, Integer fieldactivity2) {
        return super.select_BusinessSearch(namemarket, cityid, fieldactivity, fieldactivity2);
    }

    @Override
    public Cursor select_BusinessSearch(String namemarket, Integer cityid, Integer fieldactivity, Integer fieldactivity2, Integer fieldactivity3) {
        return super.select_BusinessSearch(namemarket, cityid, fieldactivity, fieldactivity2, fieldactivity3);
    }

    @Override
    public Cursor select_BusinessAdvanceSearch(String namemarket, String address, Integer cityid, Integer subsetId) {
        return super.select_BusinessAdvanceSearch(namemarket, address, cityid, subsetId);
    }

    @Override
    public Cursor select_BusinessFieldAdvanceSearch(String namemarket, String address, Integer cityid, Integer Field) {
        return super.select_BusinessFieldAdvanceSearch(namemarket, address, cityid, Field);
    }

    @Override
    public Cursor select_SearchFieldActivityId(String fieldname) {
        return super.select_SearchFieldActivityId(fieldname);
    }

    @Override
    public Cursor select_BusinessSearchBazarYab(Integer SubsetId, String txtMarket, String address, Integer arreaId) {
        return super.select_BusinessSearchBazarYab(SubsetId, txtMarket, address, arreaId);
    }

    @Override
    public Cursor select_BusinessSearch(String namemarket1, String namemarket2, String namemarket3, String namemarket4, String namemarket5, Integer cityid) {
        return super.select_BusinessSearch(namemarket1, namemarket2, namemarket3, namemarket4, namemarket5, cityid);
    }

    @Override
    public Cursor select_BusinessSearch(String namemarket1, String namemarket2, String namemarket3, String namemarket4, String namemarket5, Integer SubsetId, Integer cityid, Integer fieldactivity) {
        return super.select_BusinessSearch(namemarket1, namemarket2, namemarket3, namemarket4, namemarket5, SubsetId, cityid, fieldactivity);
    }

    @Override
    public Cursor select_AllBusiness(Integer subsetID, Integer cityid) {
        return super.select_AllBusiness(subsetID, cityid);
    }

    @Override
    public Cursor select_CountBusiness_SubsetId(Integer subsetID) {
        return super.select_CountBusiness_SubsetId(subsetID);
    }

    @Override
    public Cursor select_CountBusinessId(Integer id) {
        return super.select_CountBusinessId(id);
    }

    @Override
    public Cursor select_AllBusinessDisCount(Integer businessid) {
        return super.select_AllBusinessDisCount(businessid);
    }

    @Override
    public Cursor select_AllBusinessDisCount() {
        return super.select_AllBusinessDisCount();
    }

    @Override
    public Cursor select_AllBusinessTops(Integer businessid) {
        return super.select_AllBusinessTops(businessid);
    }

    @Override
    public Cursor select_BusinessSearchAddreass(String address) {
        return super.select_BusinessSearchAddreass(address);
    }

    @Override
    public Cursor select_AllBusinessTops() {
        return super.select_AllBusinessTops();
    }

    @Override
    public Cursor select_AllBusiness(Integer subsetID) {
        return super.select_AllBusiness(subsetID);
    }

    @Override
    public Cursor select_SortRateBusiness(Integer subsetID, Integer cityid) {
        return super.select_SortRateBusiness(subsetID, cityid);
    }

    @Override
    public Cursor select_CollectionId(Integer subsetid) {
        return super.select_CollectionId(subsetid);
    }

    @Override
    public void DeleteAllDataBase() {
        super.DeleteAllDataBase();
    }

    @Override
    public Cursor select_TableSearchSortId() {
        return super.select_TableSearchSortId();
    }

    @Override
    public Cursor select_TableSearchSortName() {
        return super.select_TableSearchSortName();
    }

    @Override
    public Cursor select_TableSearchSortRate() {
        return super.select_TableSearchSortRate();
    }

    @Override
    public Cursor select_TableSearch(String market) {
        return super.select_TableSearch(market);
    }

    @Override
    public Cursor select_SortNameBusiness(Integer subsetID, Integer cityid) {
        return super.select_SortNameBusiness(subsetID, cityid);
    }

    @Override
    public Cursor select_SubsetProperty() {
        return super.select_SubsetProperty();
    }

    @Override
    public Cursor select_SortDateBusiness(Integer subsetID, Integer cityid) {
        return super.select_SortDateBusiness(subsetID, cityid);
    }

    @Override
    public Cursor select_AllBusinessId(Integer id) {
        return super.select_AllBusinessId(id);
    }

    @Override
    public Cursor select_BusinessAndBusinessImage(Integer id) {
        return super.select_BusinessAndBusinessImage(id);
    }

    @Override
    public Cursor select_business_NameMarket(Integer businessID) {
        return super.select_business_NameMarket(businessID);
    }

    @Override
    public Cursor select_FieldActivity() {
        return super.select_FieldActivity();
    }

    @Override
    public Cursor select_ShowNotificationBusinessId(Integer id) {
        return super.select_ShowNotificationBusinessId(id);
    }

    @Override
    public Cursor select_AllDisCount() {
        return super.select_AllDisCount();
    }

    @Override
    public Cursor select_CityName(Integer id) {
        return super.select_CityName(id);
    }

    @Override
    public Cursor select_SubsetId(Integer CollectionId) {
        return super.select_SubsetId(CollectionId);
    }

    @Override
    public Cursor select_AdvanceSubsetId(String subsetname) {
        return super.select_AdvanceSubsetId(subsetname);
    }

    @Override
    public Cursor select_FieldActivityName(Integer id) {
        return super.select_FieldActivityName(id);
    }

    @Override
    public Cursor select_FieldActivityId(String fieldname) {
        return super.select_FieldActivityId(fieldname);
    }

    @Override
    public Cursor select_DisCountMember(Integer businessid) {
        return super.select_DisCountMember(businessid);
    }

    @Override
    public Cursor select_AllDisCountMember() {
        return super.select_AllDisCountMember();
    }

    @Override
    public Cursor select_DisCount(Integer businessid) {
        return super.select_DisCount(businessid);
    }

    @Override
    public Cursor select_DisCountId(Integer businessid) {
        return super.select_DisCountId(businessid);
    }

    @Override
    public Cursor select_AllDisCountMember(Integer id) {
        return super.select_AllDisCountMember(id);
    }

    @Override
    public Cursor SearchBusiness(String nameMarket, Integer subsetId, Integer cityid) {
        return super.SearchBusiness(nameMarket, subsetId, cityid);
    }

    @Override
    public Cursor select_SubsetId(String subsetname) {
        return super.select_SubsetId(subsetname);
    }

    @Override
    public Cursor select_SubsetProductId(String subsetname) {
        return super.select_SubsetProductId(subsetname);
    }

    @Override
    public Cursor select_SubsetName(Integer id) {
        return super.select_SubsetName(id);
    }

    @Override
    public Cursor select_Collection(String collectionname) {
        return super.select_Collection(collectionname);
    }

    @Override
    public Cursor select_Member_Name() {
        return super.select_Member_Name();
    }

    @Override
    public Cursor select_Update() {
        return super.select_Update();
    }

    @Override
    public Cursor select_bookmarkId(Integer businessid) {
        return super.select_bookmarkId(businessid);
    }

    @Override
    public Cursor select_opinion() {
        return super.select_opinion();
    }

    @Override
    public Cursor select_Interest() {
        return super.select_Interest();
    }

    @Override
    public Cursor select_business_Detail(String market, String address) {
        return super.select_business_Detail(market, address);
    }

    @Override
    public Cursor select_bookmark() {
        return super.select_bookmark();
    }

    @Override
    public Cursor select_opinion_BusinessId(Integer busintessid) {
        return super.select_opinion_BusinessId(busintessid);
    }

    @Override
    public Cursor select_Member() {
        return super.select_Member();
    }

    @Override
    public Cursor select_Advertisment() {
        return super.select_Advertisment();
    }

    @Override
    public Cursor select_AllArea() {
        return super.select_AllArea();
    }

    @Override
    public Cursor select_AllNotificaton() {
        return super.select_AllNotificaton();
    }

    @Override
    public Cursor select_Subset_Product() {
        return super.select_Subset_Product();
    }

    @Override
    public Cursor select_Subset() {
        return super.select_Subset();
    }

    @Override
    public Cursor select_Subset(Integer CollectionId) {
        return super.select_Subset(CollectionId);
    }

    @Override
    public Cursor select_Collection_Product() {
        return super.select_Collection_Product();
    }

    @Override
    public Cursor select_Collection() {
        return super.select_Collection();
    }

    @Override
    public Cursor select_SearchSubset(String subsetName) {
        return super.select_SearchSubset(subsetName);
    }

    @Override
    public Cursor select_Collection_Product(String namecollection) {
        return super.select_Collection_Product(namecollection);
    }

    @Override
    public Cursor select_NotificatonId(Integer Id) {
        return super.select_NotificatonId(Id);
    }

    @Override
    public Cursor select_CountCollection() {
        return super.select_CountCollection();
    }

    @Override
    public Cursor select_CityId(String cityname) {
        return super.select_CityId(cityname);
    }

    @Override
    public Cursor select_AllCity() {
        return super.select_AllCity();
    }

    @Override
    public Cursor select_CountSubset() {
        return super.select_CountSubset();
    }

    @Override
    public Cursor select_AreaName(Integer id) {
        return super.select_AreaName(id);
    }

    @Override
    public Cursor select_AreaId(String name) {
        return super.select_AreaId(name);
    }

    @Override
    public Cursor select_BusinessImage(Integer businessid) {
        return super.select_BusinessImage(businessid);
    }

    @Override
    public Cursor select_Area(Integer cityid) {
        return super.select_Area(cityid);
    }
}
