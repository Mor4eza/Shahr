package com.ariana.shahre_ma.Cards;

/**
 * Created by ariana2 on 6/2/2015.
 */
public class TopBusiness_Item {

    private String mName;
    private String mDes;
    private Integer mId;
    private int mThumbnail;

    public void setId(Integer id)
    {
        this.mId=id;
    }

    public Integer getId()
    {
        return mId;
    }
    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getDes() {
        return mDes;
    }

    public void setDes(String des) {
        this.mDes = des;
    }

    public int getThumbnail() {
        return mThumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.mThumbnail = thumbnail;
    }
}
