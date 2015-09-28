package com.ariana.shahre_ma.Bazarche;

/**
 * Created by ariana2 on 7/29/2015.
 */
public class My_Product_Items {

    private String mName;
    private String mPrice;
    private int mThumbnail;
    private Integer Id;
    private Integer mi;


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        this.Id = id;
    }


    public Integer getmI() {
        return mi;
    }

    public void setmI(Integer mi) {
        this.mi = mi;
    }



    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String Price) {
        this.mPrice = Price;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.mThumbnail = thumbnail;
    }

}