package com.ariana.shahre_ma.Cards;

/**
 * Created by ariana2 on 6/11/2015.
 */

public class Comment_Card_items {


    private String mUser;
    private String mDate;
    private String mComm;
    private Integer tag;
    int mlike;
    int mdisslike;

    public String getmUser() {
        return mUser;
    }

    public void setmUser(String user) {
        this.mUser = user;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String date) {
        this.mDate = date;
    }

    public String getmComm() {
        return mComm;
    }

    public void setmComm(String comm) {
        this.mComm = comm;
    }

    public int getmlike() {
        return mlike;
    }

    public void setmlike(int like) {
        this.mlike = like;
    }

    public int getmdisslike() {
        return mdisslike;
    }

    public void setmdisslike(int disslike) {
        this.mdisslike = disslike;
    }

    public Integer gettag() {
        return tag;
    }

    public void settag(Integer tag) {
        this.tag = tag;
    }



}





