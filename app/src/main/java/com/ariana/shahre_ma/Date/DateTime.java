package com.ariana.shahre_ma.Date;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ariana2 on 6/7/2015.
 */
public class DateTime
{
    SimpleDateFormat sdf ;
    String currentDateandTime;

    public Integer Yaer()
    {
        sdf=new SimpleDateFormat("yyyy");
        currentDateandTime = sdf.format(new Date());
        return Integer.parseInt(currentDateandTime);
    }

    public Integer Month() {
        sdf=new SimpleDateFormat("MM");
        currentDateandTime = sdf.format(new Date());
        return Integer.parseInt(currentDateandTime);
    }


    public Integer Day()
    {
        sdf=new SimpleDateFormat("dd");
        currentDateandTime = sdf.format(new Date());
        return Integer.parseInt(currentDateandTime);
    }

    public  String Hours()
    {
        sdf= new SimpleDateFormat("HH");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();
    }

    public  String Minute()
    {
        sdf= new SimpleDateFormat("mm");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();
    }

    public String Second()
    {
        sdf=new SimpleDateFormat("ss");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();

    }

    public String Now()
    {
        sdf=new SimpleDateFormat("yyyy-MM-dd");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();
    }

    public String Time()
    {
        sdf=new SimpleDateFormat("hh-mm");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();
    }


}
