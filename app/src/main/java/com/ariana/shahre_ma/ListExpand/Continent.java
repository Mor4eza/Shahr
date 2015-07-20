package com.ariana.shahre_ma.ListExpand;

import java.util.ArrayList;

/**
 * Created by ariana2 on 6/28/2015.
 */
public class Continent
{
    private String name;
    private Integer ImageUrlColection;
    private ArrayList<Country> countryList = new ArrayList<Country>();

    public Continent(String name, ArrayList<Country> countryListk,Integer url) {
        super();
        this.ImageUrlColection=url;
        this.name = name;
        this.countryList = countryList;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public Integer getUrl() {
        return ImageUrlColection;
    }
    public void setUrl(Integer url) {
        this.ImageUrlColection = url;
    }


    public ArrayList<Country> getCountryList() {
        return countryList;
    }
    public void setCountryList(ArrayList<Country> countryList) {
        this.countryList = countryList;
    };


}
