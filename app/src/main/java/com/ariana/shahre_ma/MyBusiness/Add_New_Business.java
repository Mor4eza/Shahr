package com.ariana.shahre_ma.MyBusiness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostBusinessJson;
import com.dd.CircularProgressButton;

import java.util.ArrayList;
import java.util.List;

public class Add_New_Business extends ActionBarActivity {


    EditText Market_name;
    EditText Market_tell;
    EditText Market_mobile;
    EditText Market_fax;
    EditText Market_email;
    EditText Market_owner;
    EditText Market_address;
    EditText Market_desc;
    AutoCompleteTextView Market_subset;
    AutoCompleteTextView Market_zone;
    EditText Market_field;

    Integer Fields_ID[]=new Integer[7];
    DateTime dt=new DateTime();
    FieldClass fc=new FieldClass();
    Query query=new Query(this);
    NetState net=new NetState(this);

    Integer modatgh=3;
    Integer month;
    String date;
    Integer year;

    String str="";
    DataBaseSqlite db=new DataBaseSqlite(this);
    SqliteTOjson json=new SqliteTOjson(this);
    public static CircularProgressButton save_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_business);

        Initialize_Views();


        GetAreaName();
        GetSubSet();
    }


    void Initialize_Views(){

        Market_name =(EditText)findViewById(R.id.add_market_name);
        Market_tell =(EditText)findViewById(R.id.add_market_tell);
        Market_mobile =(EditText)findViewById(R.id.add_market_phone);
        Market_fax =(EditText)findViewById(R.id.add_market_fax);
        Market_email =(EditText)findViewById(R.id.add_market_email);
        Market_owner =(EditText)findViewById(R.id.add_market_owner);
        Market_address =(EditText)findViewById(R.id.add_market_address);
        Market_desc =(EditText)findViewById(R.id.add_market_desc);
        Market_subset =(AutoCompleteTextView)findViewById(R.id.ac_subset);
        Market_zone =(AutoCompleteTextView)findViewById(R.id.ac_area);
        Market_field =(EditText)findViewById(R.id.ac_field);



    }


    public void add_business(View view)
    {

        if(Market_name.getText().toString().trim().equals("")) {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
            alertDialog.setTitle("هشدار");
            alertDialog.setMessage("نام فروشگاه را وارد کنید");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    Market_name.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
        else if(Market_tell.getText().length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
            alertDialog.setTitle("هشدار");
            alertDialog.setMessage("شماره تلفن را وارد کنید");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    Market_tell.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
        else if(Market_owner.getText().length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
            alertDialog.setTitle("هشدار");
            alertDialog.setMessage("نام مدیر فروشگاه را وارد کنید");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    Market_owner.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
        else if(Market_address.getText().length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("آدرس را وارد کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Market_address.requestFocus();

                }
            });


            alertDialog.show();

        }
     /*   else if(AreaID(Market_zone.getText().toString().trim())>0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("نام منطقه را صحیح وارد کنید");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    Market_zone.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }*/

        else if(query.getsubsetID(Market_subset.getText().toString().trim())<0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("نام زیر گروه را درست وارد کنید");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    Market_subset.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }

        else if(Market_field.length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("زمینه فعالیت خود را وارد کنید");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    Market_field.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
  /*      else if(Lati.equals(""))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("چی پی اس مقداری ندارد");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    multiautoField.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
        else if(Longti.equals(""))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("چی پی اس مقداری ندارد");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    multiautoField.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();*/

        else if(net.checkInternetConnection()==false)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("اینترنت قطع می باشد");
            alertDialog.setButton("تایید", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    // Market_field.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }

        else
        {
            save_edit.setIndeterminateProgressMode(true);
            save_edit.setProgress(50);


            str = json.getBusinessTOjsonArray(fc.GetBusiness_Id(),query.getMemberId(), Market_name.getText().toString().trim(),
                        Market_tell.getText().toString().trim(), Market_mobile.getText().toString().trim(),
                        Market_fax.getText().toString().trim(), Market_email.getText().toString().trim(),
                        Market_owner.getText().toString().trim(), Market_address.getText().toString().trim(),
                        Market_desc.getText().toString().trim(), dt.Now(), EXPDateTime(), "null"
                        , query.getsubsetID(Market_subset.getText().toString().trim()),
                        fc.GetLatitude_Business(), fc.GetLongtiude_Business(),query.getAreaID(Market_zone.getText().toString().trim()), "null", "null", Fields_ID[0], Fields_ID[1], Fields_ID[2],
                        Fields_ID[3], Fields_ID[4], Fields_ID[5], Fields_ID[6]);


                Log.i("JSONnewBusiness", str);
                HTTPPostBusinessJson httpbusiness = new HTTPPostBusinessJson(this);
                httpbusiness.SetBusinessJson(str);
                httpbusiness.execute();






        }
    }


    public List<String> getId() {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_AllArea();
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));


            } while (allrows.moveToNext());
        }

        return studentList;
    }

    public void GetAreaName()
    {
        try {



            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getId());
            Market_zone.setAdapter(adapter);

            //  multiAutoComplete.setAdapter(adapter);

            Market_zone.setThreshold(1);
        }
        catch (Exception e)
        {

        }
    }

    public List<String> getId1() {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  =db.select_Subset();
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));


            } while (allrows.moveToNext());
        }

        return studentList;
    }

    public void GetSubSet()
    {
        try {

            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getId1());
            Market_subset.setAdapter(adapter);

            //  multiAutoComplete.setAdapter(adapter);

            Market_subset.setThreshold(1);
        }
        catch (Exception e)
        {

        }
    }


    private String EXPDateTime()
    {
        month=dt.Month();
        month=month+modatgh;

        if(month>12)
        {
            year=((dt.Yaer())+1);
            month=(12-month);
            date=year.toString()+"-"+month.toString()+"-"+dt.Day();
        }

        else
        {

            date=dt.Yaer()+"-"+month.toString()+"-"+dt.Day();
        }


        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
        return  date;

    }

    public void select_map(View view) {
        Intent i = new Intent(getBaseContext(),My_Business_Map.class);
        startActivity(i);

    }
}
