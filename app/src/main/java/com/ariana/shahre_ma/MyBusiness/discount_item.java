package com.ariana.shahre_ma.MyBusiness;

import com.ariana.shahre_ma.MyInterest.Interest_Adapter;

/**
 * Created by ariana2 on 7/12/2015.
 */
public class discount_item {
    private String title;
    private String description;
    private String expireDate;
    private String MainTitle;
    private String StartDate;
    private Integer Id;
    public discount_item(String title, String description,String expireDate,String MainTitle,String StartDate,Integer Id) {
        super();
        this.title = title;
        this.description = description;
        this.expireDate=expireDate;
        this.MainTitle=MainTitle;
        this.StartDate=StartDate;
        this.Id=Id;

    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }
    public String getExpireDate() {
        return expireDate;
    }

    public String getMainTitle() {
        return MainTitle;
    }

    public String getStartDate() {
        return StartDate;
    }

    public int GetId() {
        return Id;
    }
}

