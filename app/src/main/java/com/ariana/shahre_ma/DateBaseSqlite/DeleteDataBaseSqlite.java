package com.ariana.shahre_ma.DateBaseSqlite;

import android.content.Context;

/**
 * Created by ariana on 9/4/2015.
 */
public class DeleteDataBaseSqlite extends DataBaseSqlite
{
    public DeleteDataBaseSqlite(Context context) {
        super(context);
    }

    @Override
    public void DeleteAllDataBase() {
        super.DeleteAllDataBase();
    }

    @Override
    public void delete_Search() {
        super.delete_Search();
    }

    @Override
    public void delete_Property_Product() {
        super.delete_Property_Product();
    }

    @Override
    public void delete_SubsetProperty_Product() {
        super.delete_SubsetProperty_Product();
    }

    @Override
    public void delete_Value_Product() {
        super.delete_Value_Product();
    }

    @Override
    public void delete_bookmark(Integer businessid) {
        super.delete_bookmark(businessid);
    }

    @Override
    public void delete_bookmark() {
        super.delete_bookmark();
    }

    @Override
    public void delete_Business() {
        super.delete_Business();
    }

    @Override
    public void delete_Update() {
        super.delete_Update();
    }

    @Override
    public void delete_DisCount(Integer id) {
        super.delete_DisCount(id);
    }

    @Override
    public void delete_BusinessDisCount() {
        super.delete_BusinessDisCount();
    }

    @Override
    public void delete_BusinessTops() {
        super.delete_BusinessTops();
    }

    @Override
    public void delete_Business(Integer cityid, Integer subsetid) {
        super.delete_Business(cityid, subsetid);
    }

    @Override
    public void delete_BusinessId(Integer id) {
        super.delete_BusinessId(id);
    }

    @Override
    public void delete_Notification(Integer id) {
        super.delete_Notification(id);
    }

    @Override
    public void delete_Notification() {
        super.delete_Notification();
    }

    @Override
    public void delete_Collection_Product() {
        super.delete_Collection_Product();
    }

    @Override
    public void delete_Collection() {
        super.delete_Collection();
    }

    @Override
    public void delete_Advertisment() {
        super.delete_Advertisment();
    }

    @Override
    public void delete_BusinessImage(String src, Integer businessid) {
        super.delete_BusinessImage(src, businessid);
    }

    @Override
    public void delete_BusinessImage(Integer Id) {
        super.delete_BusinessImage(Id);
    }

    @Override
    public void delete_BusinessImage() {
        super.delete_BusinessImage();
    }

    @Override
    public void delete_Subset_Product() {
        super.delete_Subset_Product();
    }

    @Override
    public void delete_Subset() {
        super.delete_Subset();
    }

    @Override
    public void delete_LikeDisCount(Integer memberid, Integer discountid, Integer businessid) {
        super.delete_LikeDisCount(memberid, discountid, businessid);
    }

    @Override
    public void delete_DisCountMember() {
        super.delete_DisCountMember();
    }

    @Override
    public void delete_DisCountMember(Integer id) {
        super.delete_DisCountMember(id);
    }

    @Override
    public void delete_FiledActivity() {
        super.delete_FiledActivity();
    }

    @Override
    public void delete_City() {
        super.delete_City();
    }

    @Override
    public void delete_Area() {
        super.delete_Area();
    }

    @Override
    public void delete_Interest() {
        super.delete_Interest();
    }

    @Override
    public void delete_Interest(Integer subsetid, Integer memberid) {
        super.delete_Interest(subsetid, memberid);
    }

    @Override
    public void delete_Opinion() {
        super.delete_Opinion();
    }

    @Override
    public void delete_Member() {
        super.delete_Member();
    }
}
