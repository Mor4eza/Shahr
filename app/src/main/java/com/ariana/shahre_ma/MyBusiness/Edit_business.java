package com.ariana.shahre_ma.MyBusiness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostBusinessEditJson;
import com.dd.CircularProgressButton;

import java.util.ArrayList;
import java.util.List;

public class Edit_business extends ActionBarActivity {
    
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
    Query query=new Query(Edit_business.this);
    NetState net=new NetState(this);
    Integer modatgh=3;
    Integer month;
    String date;
    Integer year;
    public static CircularProgressButton save_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business);

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
       save_edit = (CircularProgressButton)findViewById(R.id.btn_save_edit);
       SpinnerSetUp();
       
   }

    void Show_Business()
    {

       try {
            DataBaseSqlite db = new DataBaseSqlite(this);
            Log.i("id",String.valueOf(fc.GetBusiness_Id()));
            Cursor rows = db.select_AllBusinessId(fc.GetBusiness_Id());
            rows.moveToFirst();
           /* Log.i("1", rows.getString(1));
            Log.i("2",rows.getString(2));
            Log.i("3",rows.getString(3));*/
            Market_name.setText(rows.getString(1));//Market
            Market_tell.setText(rows.getString(2));//Phone
            Market_mobile.setText(rows.getString(3));//Mobile
            Market_fax.setText(rows.getString(4));//Fax
            Market_email.setText(rows.getString(5));//Email
            Market_owner.setText(rows.getString(6));//BusinessOwner
            Market_address.setText(rows.getString(7));//Address
            Market_desc.setText(rows.getString(8));//Description


            Market_subset.setText(query.getsubsetName(rows.getInt(9)));

            Cursor rows2 = db.select_AreaName(rows.getInt(20));
            rows2.moveToFirst();
            Market_zone.setText(rows2.getString(0));

           for (int i = 0; i < 7; i++) {
                Log.i("CounterFor", String.valueOf(rows.getInt((12) + (i))));
                if (rows.getInt((12) + (i)) > 0) {

                    Cursor rows3 = db.select_FieldActivityName(rows.getInt((12) + (i)));
                    rows3.moveToFirst();

                    Market_field.setText(Market_field.getText().toString() + rows3.getString(0) + ", ");
                }
            }
        }
        catch (Exception e)
        {
            Log.i("Exception",e.toString());
        }
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
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار");
            alertDialog.setMessage("نام فروشگاه را وارد کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
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
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار");
            alertDialog.setMessage("شماره تلفن را وارد کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Market_tell.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
        else if(Market_owner.getText().length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار");
            alertDialog.setMessage("نام مدیر فروشگاه را وارد کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Market_owner.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
        else if(Market_address.getText().length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("آدرس را وارد کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Market_address.requestFocus();

                }
            });


            alertDialog.show();

        }
        else if(query.getAreaID(Market_zone.getText().toString().trim())<0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("منطقه خود را انتخاب کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    Market_zone.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }

        else if(query.getsubsetID(Market_subset.getText().toString().trim())<0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("زیر گروه را انتخاب کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Market_subset.requestFocus();
                }
            });

            // Showing Alert Message
            alertDialog.show();

        }

        else if(Market_field.length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
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
        else if(fc.GetLatitude_Business().equals(""))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("چی پی اس مقداری ندارد");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            // Showing Alert Message
            alertDialog.show();

        }
        else if(fc.GetLongtiude_Business().equals(""))
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("موقیعت خود را انتخاب کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });

            // Showing Alert Message
            alertDialog.show();
        }
        else if(net.checkInternetConnection()==false)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("اینترنت قطع می باشد");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                }
            });

            // Showing Alert Message
            alertDialog.show();

        }

        else
        {
            save_edit.setIndeterminateProgressMode(true);
            save_edit.setProgress(50);

                str = json.getBusinessTOjson(fc.GetBusiness_Id(), Market_name.getText().toString().trim(),
                        Market_tell.getText().toString().trim(), Market_mobile.getText().toString().trim(),
                        Market_fax.getText().toString().trim(), Market_email.getText().toString().trim(),
                        Market_owner.getText().toString().trim(), Market_address.getText().toString().trim(),
                        Market_desc.getText().toString().trim(), dt.Now(), EXPDateTime(), "null"
                        ,query.getsubsetID(Market_subset.getText().toString().trim()),
                            fc.GetLatitude_Business(), fc.GetLongtiude_Business(),
                        query.getAreaID(Market_zone.getText().toString().trim()),
                        "null", "null", Fields_ID[0], Fields_ID[1], Fields_ID[2],
                        Fields_ID[3], Fields_ID[4], Fields_ID[5], Fields_ID[6]);


                Log.i("JSONeditBusines", str);
                HTTPPostBusinessEditJson httpbusiness = new HTTPPostBusinessEditJson(this);
                httpbusiness.SetBusinessJson(str);
                httpbusiness.execute();
        }
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
