package com.ariana.shahre_ma.MyBusiness;

/**
 * Created by ariana2 on 7/12/2015.
 */
public class discount_item {
    private String title;
    private String description;
    private String expireDate;

    public discount_item(String title, String description,String expireDate) {
        super();
        this.title = title;
        this.description = description;
        this.expireDate=expireDate;
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
    // getters and setters...
}

