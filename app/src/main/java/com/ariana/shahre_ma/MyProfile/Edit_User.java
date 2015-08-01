package com.ariana.shahre_ma.MyProfile;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostMemberEditJson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostMemberJson;

import java.util.ArrayList;
import java.util.List;

public class Edit_User extends ActionBarActivity {

    CalendarTool ct=new CalendarTool();
    FieldClass fc = new FieldClass();
    HTTPPostMemberEditJson sendPost;
    Query query=new Query(this);
    SqliteTOjson json = new SqliteTOjson(this);

    //Variable
    Boolean _sex = false;

    String Aname, Aemail, Acity, Aphone, Ausername, Apass;
    Boolean Asex=false;
    Integer Aage;
    String _json;

    EditText name;
    EditText email;
    AutoCompleteTextView city;
    EditText phone;
    EditText age;
    Spinner sex;
    EditText pass;
    EditText user;
    String change_pass="";
    String change_user="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__user);
            Intialize();

        DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor allrows=db.select_Member();
        allrows.moveToFirst();

        name.setText(allrows.getString(1));
        email.setText(allrows.getString(2));
        city.setText(query.getCityName(allrows.getInt(8)));
        getNameCity();
        phone.setText(allrows.getString(3));
        age.setText(String.valueOf(allrows.getInt(4)));
        pass.setText(allrows.getString(7));
        change_pass=allrows.getString(7);
        user.setText(allrows.getString(6));
        change_user=allrows.getString(6);
        SpinnerSetUp();

    }


    public void Intialize() {
        name = (EditText) findViewById(R.id.edit_name);
        email = (EditText) findViewById(R.id.edit_email);
        city = (AutoCompleteTextView) findViewById(R.id.edit_city);
        phone = (EditText) findViewById(R.id.edit_phone);
        age = (EditText) findViewById(R.id.edit_age);
        sex = (Spinner) findViewById(R.id.edit_sex);
        pass = (EditText) findViewById(R.id.edit_pass);
        user=(EditText)findViewById(R.id.edit_username);
    }

    public void edit_member(View v){


        Aname = name.getText().toString();
        Aemail = email.getText().toString();
        Acity = city.getText().toString();
        Aphone = phone.getText().toString();
        Aage = Integer.parseInt(age.getText().toString());
        if(sex.equals("مرد"))
        Asex = true;
            else
        Asex = false;
        Ausername = user.getText().toString();
        Apass = pass.getText().toString();

        Integer cityid=query.getCityId(Acity);

        if(cityid<=0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_User.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("شهر خود را انتخاب کنید");
            alertDialog.setButton("تایید", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    city.requestFocus();

                }
            });

            alertDialog.show();
        }
        else if(Aname.length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_User.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("نام خود را وارد کنید");
            alertDialog.setButton("تایید", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    name.requestFocus();

                }
            });

            alertDialog.show();
        }
        else if(Ausername.length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_User.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("نام کاربری را وارد کنید");
            alertDialog.setButton("تایید", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            alertDialog.show();
        }
        else if(Apass.length()==0)
        {
            AlertDialog alertDialog = new AlertDialog.Builder(Edit_User.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("رمز را وارد کنید");
            alertDialog.setButton("تایید", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {


                }
            });

            alertDialog.show();
        }
        else
        {
            if(Apass.equals(change_pass) || Ausername.equals(change_user))
            {

                _json = (json.getSqliteTOjson(Aname, Aemail, Aphone, Aage, Asex, Ausername, Apass, query.getCityId(Acity)));
                fc.SetMember_Name(Aname);
                fc.SetMember_Email(Aemail);
                fc.SetMember_Mobile(Aphone);
                fc.SetMember_Age(Aage);
                fc.SetMember_Sex(Asex);
                fc.SetMember_UserName(Ausername);
                fc.SetMember_Password(Apass);
                fc.SetMember_CityId(query.getCityId(Acity));

                sendPost = new HTTPPostMemberEditJson(this);
                sendPost.SetMember_Json(_json);
                Log.i("MemberJson", _json);
                sendPost.execute();
            }
            else {

                Old_Password old_password=new Old_Password(this);
                old_password.show();
            }
        }
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
          //  AutoCompleteTextView et2=(AutoCompleteTextView) findViewById(R.id.autoCompletecCity);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getId());
            city.setAdapter(adapter);


            city.setThreshold(1);
        }
        catch (Exception e) {
        }
    }


    void SpinnerSetUp(){

        sex.setPrompt("جنسیت:");
        List<String> list = new ArrayList<String>();
       list.add("مرد");
        list.add("زن");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sex.setAdapter(dataAdapter);

    }

}
