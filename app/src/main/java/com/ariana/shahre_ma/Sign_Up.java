package com.ariana.shahre_ma;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.CollectionSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.MemberSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.SubsetSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.WebServicePost.HTTPPostMemberJson;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostMemberJson;


public class Sign_Up extends ActionBarActivity {


    //Class
    MemberSqlite mem;
    CollectionSqlite coll;
    SubsetSqlite sub;
    FieldClass fc = new FieldClass();
    HTTPPostMemberJson sendPost;
    SqliteTOjson json = new SqliteTOjson();


    //Component
    EditText name;
    EditText email;
    EditText city;
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

    private static final String DATABASE_NAME = "DBshahrma.db";
    // Books table name
    private static final String TABLE_MEMBER = "member";

    private static final String TABLE_NAME_COLLECTION = "collection";
    private static final String TABLE_NAME_SUBSET= "subset";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        CollectionSqlite   coll = new CollectionSqlite(Sign_Up.this);

        Intialize();

        Spinner spn1 = (Spinner) findViewById(R.id.Spiner_Sex);
        String[] list1 = {"جنسیت خود را انتخاب کنید", "مرد", "زن"};
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


            _json = (json.getSqliteTOjson(Aname, Aemail, Aphone, Aage, Asex, Ausername, Apass, Integer.parseInt(Acity)));
            fc.SetMember_Name(Aname);
            fc.SetMember_Email(Aemail);
            fc.SetMember_Mobile(Aphone);
            fc.SetMember_Age(Aage);
            fc.SetMember_Sex(Asex);
            fc.SetMember_UserName(Ausername);
            fc.SetMember_Password(Apass);
            fc.SetMember_CityId(Integer.parseInt(Acity));
            sendPost = new HTTPPostMemberJson(this);
            sendPost.SetMember_Json(_json);
            sendPost.execute();


           /* android.os.Handler ha = new android.os.Handler();
            ha.postDelayed(new Runnable() {

                               @Override
                               public void run() {

                                   }

                               },
                        3000);*/
        } catch (Exception e) {
            Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void Intialize() {
        name = (EditText) findViewById(R.id.txtName);
        email = (EditText) findViewById(R.id.txtEmail);
        city = (EditText) findViewById(R.id.txtCity);
        phone = (EditText) findViewById(R.id.txtPhone);
        age = (EditText) findViewById(R.id.txtAge);
        sex = (Spinner) findViewById(R.id.Spiner_Sex);
        username = (EditText) findViewById(R.id.txtUsername);
        pass = (EditText) findViewById(R.id.txtpass);
    }

    public void Intmeme(View v) {



    }
}