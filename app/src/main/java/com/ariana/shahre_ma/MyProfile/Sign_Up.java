package com.ariana.shahre_ma.MyProfile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.ImageDownload.ImageLoader;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessJson;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostMemberJson;

import java.util.ArrayList;
import java.util.List;


public class Sign_Up extends ActionBarActivity {


    //Class

    CalendarTool ct=new CalendarTool();
    FieldClass fc = new FieldClass();
    HTTPPostMemberJson sendPost;
    SqliteTOjson json = new SqliteTOjson(this);

    NetState net=new NetState(this);
    //Component
    EditText name;
    EditText email;
    AutoCompleteTextView city;
    EditText phone;
    EditText age;
    Spinner sex;
    EditText username;
    EditText pass;

    //Variable
    Boolean _sex = false;

    String Aname, Aemail, Acity, Aphone, Ausername, Apass;
    Boolean Asex;
    Integer Aage;
    String _json;


    private ImageLoader imgLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);



        getNameCity();
        Intialize();





        Spinner spn1 = (Spinner) findViewById(R.id.Spiner_Sex);
        String[] list1 = { "مرد", "زن"};
        ArrayAdapter<String> Adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list1);
        Adapter1.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spn1.setAdapter(Adapter1);
        spn1.setSelection(0);

        spn1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

                switch (pos) {
                    case 0:

                        _sex = true;
                        break;
                    case 1:

                        _sex = false;
                        break;

                }
            }


            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


    }

    public void AddTOmember(View v) {
        try {

            Aname = name.getText().toString();
            Aemail = email.getText().toString();
            Acity = city.getText().toString();
            Aphone = phone.getText().toString();
            Aage = Integer.parseInt(age.getText().toString());
            Asex = _sex;
            Ausername = username.getText().toString();
            Apass = pass.getText().toString();

            Integer cityid=0;
            cityid=getCityId();

            if(net.checkInternetConnection())
            {
                AlertDialog alertDialog=new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("اینترنت قطع می باشد");
                alertDialog.setButton("خب",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();

                    }
                });
                alertDialog.show();
            }
            else if(Aname.length()==0)
            {
                AlertDialog alertDialog=new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("نام خود را وارد کنید");
                alertDialog.setButton("خب", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        name.requestFocus();
                    }
                });
                alertDialog.show();
            }
            else if(cityid==0)
            {
                AlertDialog alertDialog=new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("نام شهر خود را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        city.requestFocus();
                    }
                });
                alertDialog.show();
            }
            else if(Asex.equals(null))
            {
                AlertDialog alertDialog=new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("جنسیت خود را انتخاب کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        sex.requestFocus();
                    }
                });
                alertDialog.show();
            }
            else if(Ausername.length()==0)
            {
                AlertDialog alertDialog=new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("نام کاربری خود را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        username.requestFocus();
                    }
                });
                alertDialog.show();
            }
            else if(Apass.length()==0)
            {
                AlertDialog alertDialog=new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("رمز عبور خود را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        pass.requestFocus();
                    }
                });
                alertDialog.show();
            }
            else {
                _json = (json.getSqliteTOjson(Aname, Aemail, Aphone, Aage, Asex, Ausername, Apass, cityid));
                fc.SetMember_Name(Aname);
                fc.SetMember_Email(Aemail);
                fc.SetMember_Mobile(Aphone);
                fc.SetMember_Age(Aage);
                fc.SetMember_Sex(Asex);
                fc.SetMember_UserName(Ausername);
                fc.SetMember_Password(Apass);
                fc.SetMember_CityId(cityid);

                sendPost = new HTTPPostMemberJson(this);
                sendPost.SetMember_Json(_json);
                sendPost.execute();
            }



        } catch (Exception e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Intialize() {
        name = (EditText) findViewById(R.id.txtName);
        email = (EditText) findViewById(R.id.txtEmail);
        city = (AutoCompleteTextView) findViewById(R.id.autoCompletecCity);
        phone = (EditText) findViewById(R.id.txtPhone);
        age = (EditText) findViewById(R.id.txtAge);
        sex = (Spinner) findViewById(R.id.Spiner_Sex);
        username = (EditText) findViewById(R.id.txtUsername);
        pass = (EditText) findViewById(R.id.txtpass);
    }




    public List<String> getId() {

       DataBaseSqlite db=new DataBaseSqlite(this);

        List<String> studentList = new ArrayList<>();
        Cursor allrows  = db.select_AllCity();
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));

            } while (allrows.moveToNext());
        }

        return studentList;
    }

    public void getNameCity()
    {
        try
        {
            AutoCompleteTextView et2=(AutoCompleteTextView) findViewById(R.id.autoCompletecCity);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getId());
            et2.setAdapter(adapter);


            et2.setThreshold(1);
        }
        catch (Exception e) {
        }
    }

    private Integer getCityId() {
        Integer Result = 0;

        SQLiteDatabase mydb = openOrCreateDatabase(fc.GetDataBaseName(), Context.MODE_PRIVATE, null);
        Cursor allrows = mydb.rawQuery("SELECT Id FROM " + fc.GetTableNamecity()+ "  WHERE Name='" +city.getText().toString()+ "'", null);
        allrows.moveToFirst();
        Result = allrows.getInt(0);
        allrows.close();
        mydb.close();


        return Result;
    }

    public  void fahmidan(View v)
    {

       // Toast.makeText(getApplication(),fc.GetMember_Email(), Toast.LENGTH_LONG).show();
    /*    DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor allrows=db.select_AllBusiness();


        try {
            if ( allrows.moveToFirst()) {
                do {
                   // Toast.makeText(getApplication(), String.valueOf(allrows.getString(0)), Toast.LENGTH_LONG).show();
                    Log.i("Business ID", String.valueOf(allrows.getString(0)));
                }while (allrows.moveToNext());
            }
            allrows.close();
        }
        catch (Exception e){ Toast.makeText(getApplication(),e.toString(), Toast.LENGTH_LONG).show();}
*/
     /*   SqliteTOjson json=new SqliteTOjson(this);
      //  json.getSqliteInterestTOjson();
      email.setText(json.getSqliteInterestTOjson());*/

       /* KeySettings sett=new KeySettings(this);
        Toast.makeText(getApplication(),sett.getPMtime(), Toast.LENGTH_LONG).show();*/

        String links[]=new String[]{
                "http://test.shahrma.com/api/ApiGiveBusiness?subsetId=14&cityid=68",
                "http://test.shahrma.com/api/ApiGiveBusiness?subsetId=16&cityid=68",
                "http://test.shahrma.com/api/ApiGiveBusiness?subsetId=24&cityid=68"
        };

        HTTPGetBusinessJson getbusiness=new HTTPGetBusinessJson(this);
        getbusiness.execute(links);


    }
}