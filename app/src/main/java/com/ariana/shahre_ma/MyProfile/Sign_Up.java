package com.ariana.shahre_ma.MyProfile;

import android.content.Intent;
import android.database.Cursor;
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
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
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
Query query=new Query(this);

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
    String Aage="";
    String _json="";

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

                        _sex = true;//man
                        break;
                    case 1:

                        _sex = false;//woman
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
            Aage = age.getText().toString();
            Asex = _sex;
            Ausername = username.getText().toString();
            Apass = pass.getText().toString();

            Integer cityid=0;
            cityid=query.getCityId(Acity);

            if(cityid<=0)
            {
                        city.requestFocus();
                        city.setError("شهر خود را انتخاب کنید");

            }
            else if(Aname.length()<=3)
            {
                        name.setError("بیش از 3 حرف وارد کنید");
                        name.requestFocus();
            }
            else if(Ausername.length()<5)
            {
                        username.setError("نام کاربری باید حد اقل 5 حرف باشد");
                        username.requestFocus();
            }
            else if(Apass.length()<6)
            {
                        pass.setError("رمز عبور باید حداقل 6 حرف باشد");
                        pass.requestFocus();

            }
            else if(Aage.equals(null) || Aage.length()==0 || Aage.equals("null"))
            {
                        age.setError("سن خود را وارد کنید");
                        age.requestFocus();
            }
            else {
                _json = (json.getSqliteTOjson(Aname, Aemail, Aphone, Integer.parseInt(Aage), Asex, Ausername, Apass, query.getCityId(Acity)));
                fc.SetMember_Name(Aname);
                fc.SetMember_Email(Aemail);
                fc.SetMember_Mobile(Aphone);
                fc.SetMember_Age(Integer.parseInt(Aage));
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

        SelectDataBaseSqlite sdb=new SelectDataBaseSqlite(this);

        List<String> studentList = new ArrayList<>();
        Cursor allrows  = sdb.select_AllCity();
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(), Log_In.class);
        startActivity(i);

    }
}