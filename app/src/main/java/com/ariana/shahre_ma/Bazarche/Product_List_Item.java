package com.ariana.shahre_ma.Bazarche;

/**
 * Created by ariana2 on 8/23/2015...
 */
public class Product_List_Item {

    private String mName;
    private Double mPrice;
    private int mThumbnail;
    private Integer Id;
    private String imageName;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }


    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Double getmPrice() {
        return mPrice;
    }

    public void setmPrice(Double Price) {
        this.mPrice = Price;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String image) {
        this.imageName = image;
    }

}
