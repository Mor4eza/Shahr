package com.ariana.shahre_ma.Cards;

/**
 * Created by ariana2 on 6/8/2015.
 */
public class Job_lists_card_item {



    private String mName;
    double rate;
    private String mAdd;
    private int mThumbnail;

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
}