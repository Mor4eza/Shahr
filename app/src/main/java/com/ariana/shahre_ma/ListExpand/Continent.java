package com.ariana.shahre_ma.ListExpand;

import java.util.ArrayList;

/**
 * Created by ariana2 on 6/28/2015.
 */
public class Continent
{
    private String name;
    private String ImageUrlColection;
    private ArrayList<Country> countryList = new ArrayList<Country>();

    public Continent(String name, ArrayList<Country> countryList) {
        super();
        this.name = name;
        this.countryList = countryList;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return ImageUrlColection;
    }
    public void setUrl(String url) {
        this.name = url;
    }


    public ArrayList<Country> getCountryList() {
        return countryList;
    }
    public void setCountryList(ArrayList<Country> countryList) {
        this.countryList = countryList;
    };


}
