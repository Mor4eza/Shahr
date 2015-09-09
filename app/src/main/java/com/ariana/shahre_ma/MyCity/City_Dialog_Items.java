package com.ariana.shahre_ma.MyCity;

/**
 * Created by ariana2 on 9/8/2015...
 */
public class City_Dialog_Items {

    private String title;
    private Integer Id;

    public City_Dialog_Items(String title,Integer Id) {
        super();
        this.title = title;
        this.Id=Id;

    }


    public String getTitle() {
        return title;
    }
    public int GetId() {
        return Id;
    }
}
