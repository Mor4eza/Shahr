package com.ariana.shahre_ma.NearMe;

/**
 * Created by ariana2 on 7/12/2015.
 */
public class FilterItems {
    private String title;
    private Integer Id;


    public FilterItems(String title,Integer Id) {
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

