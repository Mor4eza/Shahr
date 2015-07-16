package com.ariana.shahre_ma.MyBusiness;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetAreaJosn;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetFieldActivityJson;

public class My_Business extends ActionBarActivity {

    FieldClass fc=new FieldClass();
    Query query=new Query(this);
    RatingBar rate;
    TextView title;
    TextView address;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__business);

        Intialize();

        HTTPGetFieldActivityJson httpfield=new HTTPGetFieldActivityJson(this);
        httpfield.execute();

        HTTPGetAreaJosn httparea=new HTTPGetAreaJosn(this);
        httparea.execute();

        Log.i("BusinessID",String.valueOf(fc.GetBusiness_Id()));
        try {
            DataBaseSqlite db = new DataBaseSqlite(this);
            Cursor rows = db.select_AllBusinessId(fc.GetBusiness_Id());
            rows.moveToFirst();
            title.setText(rows.getString(1));
            title.setTag( String.valueOf(rows.getInt(0)));
            address.setText(rows.getString(8));
            rate.setRating(rows.getFloat(29));
        }
        catch(Exception e)
        {
            Log.e("SQLException",e.toString());
        }

    }

    public void add_business(View v){
        Intent i = new Intent(getApplicationContext(),Add_business.class);
        startActivity(i);

    }
    public void Intialize()
    {
        rate=(RatingBar) findViewById(R.id.my_business_rate);
        title=(TextView) findViewById(R.id.my_business_title);
        address=(TextView) findViewById(R.id.my_business_address);
    }
   public void discount(View v){

       Integer id=0;
       id=Integer.parseInt((String) title.getTag());
       if(id<=0)
       {
           fc.SetBusiness_Id(id);
           Toast.makeText(getApplicationContext(),"اول کسب کار ثبت کنید",Toast.LENGTH_LONG).show();
       }
       else {
           Intent i = new Intent(getApplicationContext(), Discount.class);
           startActivity(i);
       }
   }
}
