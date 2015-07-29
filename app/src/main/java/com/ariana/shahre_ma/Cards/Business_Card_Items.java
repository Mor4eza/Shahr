package com.ariana.shahre_ma.Cards;

/**
 * Created by ariana2 on 7/29/2015.
 */
public class Business_Card_Items {

    private String mName;
    private String mAddress;
    double rate;
    private int mThumbnail;

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String des) {
        this.mAddress = des;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.mThumbnail = thumbnail;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}