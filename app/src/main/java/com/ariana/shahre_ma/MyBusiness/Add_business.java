package com.ariana.shahre_ma.MyBusiness;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.*;
import android.view.*;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostBusinessEditJson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostBusinessJson;

import java.util.ArrayList;
import java.util.List;

public class Add_business extends ActionBarActivity {
    
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
    MultiAutoCompleteTextView Market_field;
    Spinner Market_gharar;
    Integer Fields_ID[]=new Integer[7];
    DateTime dt=new DateTime();
    FieldClass fc=new FieldClass();
    Query query=new Query(Add_business.this);
    NetState net=new NetState(this);
    Integer modatgh=3;
    Integer month;
    String date;
    Integer year;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_business);

        Initialize_Views();

        GetAreaName();
        GetSubSet();
        GetNameActivity();
        Show_Business();

        Spinner spn1 = (Spinner) findViewById(R.id.spinner_gh);
        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                switch (pos) {
                    case 0:
                        // Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_LONG).show();
                        modatgh = 3;
                        EXPDateTime();
                        break;
                    case 1:
                        //Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_LONG).show();
                        modatgh = 6;
                        EXPDateTime();
                        break;
                    case 2:
                        //Toast.makeText(getApplicationContext(), "9", Toast.LENGTH_LONG).show();
                        modatgh = 9;
                        EXPDateTime();
                        break;
                    case 3:
                        // Toast.makeText(getApplicationContext(), "12", Toast.LENGTH_LONG).show();
                        modatgh = 12;
                        EXPDateTime();
                        break;
                }
            }


            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

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
       Market_field =(MultiAutoCompleteTextView)findViewById(R.id.ac_field);
       Market_gharar=(Spinner)findViewById(R.id.spinner_gh);
       SpinnerSetUp();
       
   }

    void Show_Business()
    {

        DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor rows=db.select_AllBusinessId(fc.GetBusiness_Id());
        rows.moveToFirst();
        Market_name.setText(rows.getString(1));
        Market_tell.setText(rows.getString(2));
        Market_mobile.setText(rows.getString(3));
        Market_fax.setText(rows.getString(5));
        Market_email.setText(rows.getString(6));
        Market_owner.setText(rows.getString(7));
        Market_address.setText(rows.getString(8));

        Cursor rows1=db.select_SubsetName(rows.getInt(14));
        rows1.moveToFirst();
        Market_subset.setText(rows1.getString(0));

        Cursor rows2=db.select_AreaName(rows.getInt(17));
        rows2.moveToFirst();
        Market_zone.setText(rows2.getString(0));

       // for(int i=0;i<6;i++)
        //{
           // if(rows.getInt(21+i)>0) {
        try {
            Cursor rows3 = db.select_FieldActivityName(rows.getInt(21));
            rows3.moveToFirst();
            Log.i("FieldActivity",String.valueOf(rows.getInt(21)));
        }
        catch (Exception e)
        {
            Log.i("FieldActivity",String.valueOf(rows.getInt(21)));
            Log.i("ExceptionFieldActivity", e.toString());
        }
           // }
        //}

       // Market_gharar.setText(rows.getString(8));
    }
    void SpinnerSetUp(){


        Market_gharar.setPrompt("مدت قرارداد");
        List<String> list = new ArrayList<String>();
        list.add("3");
        list.add("6");
        list.add("9");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Market_gharar.setAdapter(dataAdapter);

    }
    public void save_edit(View v){

        String str="";
        DataBaseSqlite db=new DataBaseSqlite(this);
        SqliteTOjson json=new SqliteTOjson(this);

        String strmulti=Market_field.getText().toString();
        String strCount[]=strmulti.split(",");
        Log.i("length", String.valueOf(strCount.length));
        for(int i=0;i<7;i++)
            Fields_ID[i] =0;
        for(int i=0; i<strCount.length-1;i++)
        {

            String resultNameField=strCount[i].replace(",","");
            String namefield=resultNameField.replaceAll("^\\s+|\\s+$", "");
            Log.i("resultNameField",resultNameField);
            Cursor rows=  db.select_FieldActivityId(namefield);
            rows.moveToFirst();
            try {
                Log.e("rows.getInt",String.valueOf(rows.getInt(0)));
                Fields_ID[i] = rows.getInt(0);


            }
            catch (Exception e)
            {
                Log.e("ExceptionFieldsID",e.toString());
            }


        }


        if(Market_name.getText().toString().trim().equals("")) {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_business.this).create();
            alertDialog.setTitle("هشدار");
            alertDialog.setIcon(R.drawable.caution_sing_64);
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
            AlertDialog alertDialog = new AlertDialog.Builder(Add_business.this).create();
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
            AlertDialog alertDialog = new AlertDialog.Builder(Add_business.this).create();
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
            AlertDialog alertDialog = new AlertDialog.Builder(Add_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("آدرس را وارد کنید");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    // Write your code here to execute after dialog closed
                    //Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                    Market_address.requestFocus();

                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
  /*      else if(StrAreaId==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_business.this).create();
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

        }

        else if(StrSubsetId==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_business.this).create();
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

        }*/

        else if(Fields_ID[0]==0 && Fields_ID[1]==0 && Fields_ID[2]==0 && Fields_ID[3]==0 && Fields_ID[4]==0 &&Fields_ID[5]==0 && Fields_ID[6]==0  )
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Add_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("حداقل یک زمینه فعالیت وارد کنید");
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
            AlertDialog alertDialog = new AlertDialog.Builder(Add_business.this).create();
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
            AlertDialog alertDialog = new AlertDialog.Builder(Add_business.this).create();
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

        else
        {

            if(net.checkInternetConnection())
            {
                str=  json.getBusinessTOjson(fc.GetBusiness_Id(), Market_name.getText().toString(),
                        Market_tell.getText().toString(), Market_mobile.getText().toString(),
                        Market_fax.getText().toString(), Market_email.getText().toString(),
                        Market_owner.getText().toString(), Market_address.getText().toString(),
                        Market_desc.getText().toString(),dt.Now(),EXPDateTime(),"null"
                        ,SubsetID(Market_subset.getText().toString().trim()),
                        "31.63636", "51.252525",AreaID(Market_zone.getText().toString().trim()),"null","null",Fields_ID[0],Fields_ID[1],Fields_ID[2],
                        Fields_ID[3],Fields_ID[4],Fields_ID[5],Fields_ID[6]);


                Log.i("JSON", str);
                HTTPPostBusinessEditJson httpbusiness=new HTTPPostBusinessEditJson(this);
                httpbusiness.SetBusinessJson(str);
                httpbusiness.execute();
            }
            else
            {

            }
        }




      /*  HTTPPostBusinessJson httpPostBusinessJson=new HTTPPostBusinessJson(this);
        httpPostBusinessJson.SetBusinessJson(str);
        httpPostBusinessJson.execute();*/

    }


    public List<String> getId2() {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows=db.select_FieldActivity();
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));
                Log.i("FieldActivity", String.valueOf(allrows.getInt(0)) + " : " + allrows.getString(1));


            } while (allrows.moveToNext());
        }

        return studentList;
    }

    public void GetNameActivity()
    {
        try {

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getId2());
            Market_field.setAdapter(adapter);
            Market_field.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        }
        catch (Exception e)
        {
          Log.e("ExceptionSQL",e.toString());
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

    public Integer AreaID(String Name)
    {
        Integer Result=0;
        try {

          DataBaseSqlite db=new DataBaseSqlite(this);
            Cursor allrows = db.select_AreaId(Name);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();

        }

        catch (Exception e)
        {
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return  Result;
    }

    public Integer SubsetID(String Name)
    {
        Integer Result=0;
        try {

            DataBaseSqlite db=new DataBaseSqlite(this);
            Cursor allrows = db.select_SubsetId(Name);
            allrows.moveToFirst();
            Result = allrows.getInt(0);
            allrows.close();
        }

        catch (Exception e)
        {
            // Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
        return  Result;
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


         Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
        return  date;

    }
    public void select_map(View v ){

        Intent i = new Intent(getBaseContext(),My_Business_Map.class);
        startActivity(i);

    }
}
