package com.ariana.shahre_ma;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.ImageDownload.ImageLoader;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCityJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetOpinionJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetSubsetJson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostMemberJson;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostOpinionJson;

import java.util.ArrayList;
import java.util.List;


public class Sign_Up extends ActionBarActivity {


    //Class

    CalendarTool ct=new CalendarTool();
    FieldClass fc = new FieldClass();
    HTTPPostMemberJson sendPost;
    SqliteTOjson json = new SqliteTOjson();


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
                        // Toast.makeText(getApplicationContext(), "3", Toast.LENGTH_LONG).show();
                        _sex = true;
                        break;
                    case 1:
                        //Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_LONG).show();
                        _sex = false;
                        break;

                }
            }


            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });


        imgLoader = new ImageLoader(this); // important

        ImageView iv_1 = (ImageView) findViewById(R.id.imageView2);
        String image_url_1 = "https://www.google.com/images/srpr/logo11w.png";
        imgLoader.DisplayImage(image_url_1, iv_1);
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
            _json = (json.getSqliteTOjson(Aname, Aemail, Aphone, Aage, Asex, Ausername, Apass,cityid));
          //  email.setText(_json);
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

        SQLiteDatabase mydb = openOrCreateDatabase(fc.GetDataBaseName(), Context.MODE_PRIVATE,null);
        List<String> studentList = new ArrayList<>();
        Cursor allrows  = mydb.rawQuery("SELECT * FROM "+  fc.GetTableNamecity(), null);
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));

            } while (allrows.moveToNext());
        }
        mydb.close();
        return studentList;
    }

    public void getNameCity()
    {
        try
        {
            AutoCompleteTextView et2=(AutoCompleteTextView) findViewById(R.id.autoCompletecCity);
            ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,getId());
            et2.setAdapter(adapter);

            //  multiAutoComplete.setAdapter(adapter);

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
}