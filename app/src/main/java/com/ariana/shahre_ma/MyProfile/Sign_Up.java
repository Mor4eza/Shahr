package com.ariana.shahre_ma.MyProfile;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.ImageDownload.ImageLoader;
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
    Integer Aage=0;
    String _json="";


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

                        _sex = false;
                        break;
                    case 1:

                        _sex = true;
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
            cityid=query.getCityId(Acity);

            if(cityid<=0)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("شهر خود را انتخاب کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        city.requestFocus();

                    }
                });

                alertDialog.show();
            }
            else if(Aname.length()==0)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("نام خود را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        name.requestFocus();

                    }
                });

                alertDialog.show();
            }
            else if(Ausername.length()==0)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("نام کاربری را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                alertDialog.show();
            }
            else if(Apass.length()==0)
            {
                AlertDialog alertDialog = new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("رمز را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                alertDialog.show();
            }
            else if(Aage==0 || Aage.equals(null))
            {
                AlertDialog alertDialog = new AlertDialog.Builder(Sign_Up.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("سن خود را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                    }
                });

                alertDialog.show();
            }
            else {
                _json = (json.getSqliteTOjson(Aname, Aemail, Aphone, Aage, Asex, Ausername, Apass, query.getCityId(Acity)));
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(), Log_In.class);
        startActivity(i);

    }
}