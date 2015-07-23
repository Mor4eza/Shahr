package com.ariana.shahre_ma.Fields;

/**
 * Created by ariana on 7/23/2015.
 */
public class FieldDataBusiness
{
    private static Integer[] Id;
    private static Double[] Rate;
    private static String[] Phone;
    private static String[] Address;
    private static String[] MarketName;


    public void SetIdBusiness(Integer[] id)
    {
        Id=id;
    }

    public Integer[] GetIdBusiness()
    {
        return Id;
    }

    /**
     * Get rate Business
     * @param rate
     */
    public void SetRateBusiness(Double[] rate)
    {
        Rate=rate;
    }

    public Double[] GetRateBusiness()
    {
        return Rate;
    }

    /**
     * Get phone business
     * @param phone
     */
    public void SetPhoneBusiness(String[] phone)
    {
        Phone=phone;
    }

    public String[] GetPhoneBusiness()
    {
        return Phone;
    }


    /**
     * Get address business
     * @param address
     */
    public void SetAddressBusiness(String[] address)
    {
        Address=address;
    }

    public String[] GetAddressBusiness()
    {
        return Address;
    }

    /**
     * Get market name business
     * @param marketName
     */
    public void SetMarketBusiness(String[] marketName)
    {
        MarketName=marketName;
    }

    public String[] GetMarketBusiness()
    {
        return MarketName;
    }
}
