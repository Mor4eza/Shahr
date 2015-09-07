package com.ariana.shahre_ma.Cards;

import android.graphics.Bitmap;

/**
 * Created by ariana2 on 6/8/2015.
 */
public class Job_lists_card_item {



    private String mName;
    double rate;
    private Integer mRateCount;
    private String mtell;
    private Bitmap Image;
    private String mAdd;
    private String mNameImage;
    private int mThumbnail;
    private Integer mId;
    private Integer mSubsetId;
    private Integer mDiscount;




    public void setImage(Bitmap image){this.Image=image;}
    public Bitmap getImage(){return  this.Image;}


    public void setSubsetId(Integer subsetid){this.mSubsetId=subsetid;}
    public Integer getSubsetId(){return  this.mSubsetId;}

    public void setDisCount(Integer discount){this.mDiscount=discount;}
    public Integer getDisCount(){return  this.mDiscount;}


    public double getRate() {
        return rate;
    }
    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getName() {
        return mName;
    }
    public void setName(String name) {
        this.mName = name;
    }

    public String getDes() {
        return mAdd;
    }
    public void setDes(String des) {
        this.mAdd = des;
    }

    public int getThumbnail() {
        return mThumbnail;
    }
    public void setThumbnail(int thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public Integer getmId() {
        return mId;
    }
    public void setmId(Integer id) {
        this.mId = id;
    }

    public String getTell() {
        return mtell;
    }
    public void setTell(String tell) {
        this.mtell = tell;
    }


    public String getNameImage() {
        return mNameImage;
    }
    public void setNameImage(String nameimage) {
        this.mNameImage = nameimage;
    }


    public Integer getmRateCount() {
        return mRateCount;
    }
    public void setmRateCount(Integer rate) {
        this.mRateCount = rate;
    }
}