package com.ariana.shahre_ma.Cards;

/**
 * Created by ariana2 on 6/24/2015.
 */
public class Notify_Card_Items {

    private String Ndate;
    private String Nmarket;
    private String Ndetail;
    private Integer NId;
    private Integer NotiyId;


    public Integer getNotiyId(){return  NotiyId;}

    public void setNotiyId(Integer notiyId){this.NotiyId=notiyId;}

    public Integer getNId(){return  NId;}

    public void setNId(Integer id){NId=id;}

    public String getNdate() {
        return Ndate;
    }

    public void setNdate(String date) {
        this.Ndate = date;
    }

    public String getNmarket() {
        return Nmarket;
    }

    public void setNmarket(String market) {
        this.Nmarket = market;
    }

    public String getNdetail() {
        return Ndetail;
    }

    public void setNdetail(String detail) {
        this.Ndetail = detail;
    }
}




