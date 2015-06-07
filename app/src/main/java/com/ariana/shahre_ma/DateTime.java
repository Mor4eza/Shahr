package com.ariana.shahre_ma;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ariana2 on 6/7/2015.
 */
public class DateTime
{
    SimpleDateFormat sdf ;
    String currentDateandTime;

    public String Yaer()
    {
        sdf=new SimpleDateFormat("yyyy");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();
    }

    public String Month() {
        sdf=new SimpleDateFormat("MM");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();
    }


    public String Day()
    {
        sdf=new SimpleDateFormat("dd");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();
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
}
