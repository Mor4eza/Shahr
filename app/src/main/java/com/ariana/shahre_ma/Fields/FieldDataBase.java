package com.ariana.shahre_ma.Fields;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana on 8/18/2015.
 */
public class FieldDataBase
{

    //Fields of the product
    List<Integer> Id_Product =new ArrayList<>();
    List<Integer> MemberId_Product =new ArrayList<>();
    List<String> Name_Product =new ArrayList<>();
    List<String> Property_Product =new ArrayList<>();
    List<Double>  Price_Product =new ArrayList<>();
    List<Double>  Latitude_Product =new ArrayList<>();
    List<Double>  Longtiude_Product =new ArrayList<>();
    List<Boolean> Adaptive_Product =new ArrayList<>();
    List<String> Description_Product =new ArrayList<>();
    List<String> Image_Product =new ArrayList<>();
    List<String> Phone__Product =new ArrayList<>();
    List<String> Mobile_Product =new ArrayList<>();
    List<String> Address_Product =new ArrayList<>();
    List<String> Email_Product =new ArrayList<>();
    List<Integer> SubsetId_Product =new ArrayList<>();
    List<Integer> AreaId_Product =new ArrayList<>();

    //Fields of the Subset
    List<Integer> Id_Subset =new ArrayList<>();
    List<String>  Name_Subset =new ArrayList<>();
    List<Integer> CollectionId_Subset =new ArrayList<>();

    //Fields of the Collection
    List<Integer> Id_Collection =new ArrayList<>();
    List<String>  Name_Collection =new ArrayList<>();









    //Fields set the table Product
    public void setId_Product(List<Integer> id){this.Id_Product=id;}
    public void setMemberId_Product(List<Integer> memberId){this.MemberId_Product=memberId;}
    public void setName_Product(List<String> name){this.Name_Product=name;}
    public void setProperty_Product(List<String> property){this.Property_Product=property;}
    public void setPrice_Product(List<Double> price){this.Price_Product=price;}
    public void setLatitude_Product(List<Double> latitude){this.Latitude_Product=latitude;}
    public void setLongtiude_Product(List<Double> longtiude){this.Longtiude_Product=longtiude;}
    public void setAdaptive__Product(List<Boolean> adaptive){this.Adaptive_Product=adaptive;}
    public void setDescription_Product(List<String> description){this.Description_Product=description;}
    public void setImage_Product(List<String> image){this.Image_Product=image;}
    public void setPhone__Product(List<String> phone){this.Phone__Product=phone;}
    public void setMobile_Product(List<String> mobile){this.Mobile_Product=mobile;}
    public void setAddress_Product(List<String> address){this.Address_Product=address;}
    public void setEmail_Product(List<String> email){this.Email_Product=email;}
    public void setSubsetId_Product(List<Integer> subsetid){this.SubsetId_Product=subsetid;}
    public void setAreaId_Product(List<Integer> areaid){this.AreaId_Product=areaid;}
    //Fields set the table Product
    public List<Integer> getId_Product(){return this.Id_Product;}
    public List<String> getName_Product(){return this.Name_Product;}
    public List<String> getProperty_Product(){return this.Property_Product;}
    public List<Double> getprice_Product(){return this.Price_Product;}
    public List<Double> getLatitude_Product(){return this.Latitude_Product;}
    public List<Double> getLongtiude_Product(){return this.Longtiude_Product;}
    public List<Boolean> getAdaptive_Product(){return this.Adaptive_Product;}
    public List<String> getDescription_Product(){return this.Description_Product;}
    public List<String> getImage_Product(){return this.Image_Product;}
    public List<String> getPhone__Product(){return this.Phone__Product;}
    public List<String> getMobile_Product(){return this.Mobile_Product;}
    public List<String> getAddress_Product(){return this.Address_Product;}
    public List<String> getEmail_Product(){return this. Email_Product;}
    public List<Integer> getSubsetId_Product(){return this.SubsetId_Product;}
    public List<Integer> getAreaId_Product(){return this.AreaId_Product;}
    public List<Integer> getMemberId_Product(){return this.MemberId_Product;}




    //Fields set the table Subset
    public void setId_Subset(List<Integer> id){this.Id_Subset=id;}
    public void setName_Subset(List<String> name){this.Name_Subset=name;}
    public void setCollectionId_Subset(List<Integer> collectionid){this.CollectionId_Subset=collectionid;}
    //Fields get the table Subset
    public List<Integer> getId_Subset(){return this.Id_Subset;}
    public List<String> getName_Subset(){return this.Name_Subset;}
    public List<Integer> getCollectionId_Subset(){return this.CollectionId_Subset;}




    //Fields set the table Collection
    public void setId_Collection(List<Integer> id){this.Id_Collection=id;}
    public void setName_Collection(List<String> name){this.Name_Collection=name;}
    //Fields get the table Collection
    public List<Integer> getId_Collection(){return this.Id_Collection;}
    public List<String> getName_Collection(){return this.Name_Collection;}




}
