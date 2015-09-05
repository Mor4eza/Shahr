package com.ariana.shahre_ma.Search;

/**
 * Created by ariana2 on 9/5/2015...
 */
public class SearchItems {
    private String mName;
    double rate;
    private String mtell;
    private String mAdd;
    private String mNameImage;
    private int mThumbnail;
    private Integer mId;
    private Integer mSubsetId;
    private Integer mDiscount;



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

}
