package com.ariana.shahre_ma.Fields;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ariana on 7/23/2015.
 */
public class FieldDataBusiness
{
    private static List<Integer> Id=new ArrayList<>();
    private static List<Double> Rate=new ArrayList<>();
    private static List<String> Phone=new ArrayList<String>();
    private static List<String> Address=new ArrayList<>();
    private static List<String> MarketName=new ArrayList<String>();


    public void SetIdBusiness(List<Integer> id)
    {
        Id=id;
    }

    public List<Integer> GetIdBusiness()
    {
        return Id;
    }

    /**
     * Get rate Business
     * @param rate
     */
    public void SetRateBusiness(List<Double>  rate)
    {
        Rate=rate;
    }

    public List<Double>  GetRateBusiness()
    {
        return Rate;
    }

    /**
     * Get phone business
     * @param phone
     */
    public void SetPhoneBusiness(List<String> phone)
    {
        Phone=phone;
    }

    public List<String> GetPhoneBusiness()
    {
        return Phone;
    }


    /**
     * Get address business
     * @param address
     */
    public void SetAddressBusiness(List<String> address)
    {
        Address=address;
    }

    public List<String> GetAddressBusiness()
    {
        return Address;
    }

    /**
     * Get market name business
     * @param marketName
     */
    public void SetMarketBusiness(List<String> marketName)
    {
        MarketName=marketName;
    }

    public List<String> GetMarketBusiness()
    {
        return MarketName;
    }
}
