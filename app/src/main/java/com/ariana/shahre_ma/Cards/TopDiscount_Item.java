package com.ariana.shahre_ma.Cards;

/**
 * Created by ariana2 on 8/2/2015.
 */
public class TopDiscount_Item {

    private String mName;
    private String mDes;
    private Integer mId;
    private int mThumbnail;
    private Integer mSubsetId;
    private String ImageName;

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

    public void setSubsetId(Integer subsetid){this.mSubsetId=subsetid;}
    public Integer getSubsetId(){return  this.mSubsetId;}

    public void setImageName(String imageName) {
        this.ImageName = imageName;
    }

    public String getImageName() {
        return ImageName;
    }
}


