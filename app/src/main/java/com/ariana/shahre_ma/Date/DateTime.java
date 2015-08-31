package com.ariana.shahre_ma.Date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
        sdf=new SimpleDateFormat("MM/dd/yyyy");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();
    }

    public String Time()
    {
        sdf=new SimpleDateFormat("HH:mm");
        currentDateandTime = sdf.format(new Date());
        return currentDateandTime.toString();
    }

    public String Add(Integer day,String date)
    {
      /*  sdf=new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        currentDateandTime = sdf.format(date);
        c.add(Calendar.DATE, day);

        String output = sdf.format(c.getTime());*/

        String dt = date;  // Start date
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, day);  // number of days to add, can also use Calendar.DAY_OF_MONTH in place of Calendar.DATE
        SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd/yyyy");
        String output = sdf1.format(c.getTime());

        return output.toString();
    }

    public String Date()
    {
        sdf=new SimpleDateFormat("MM/dd/yyyy");
        Calendar c = Calendar.getInstance();
        currentDateandTime = sdf.format(new Date());
        c.add(Calendar.DATE, 0);
        String output = sdf.format(c.getTime());
        return output.toString();
    }


}
