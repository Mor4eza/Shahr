package com.ariana.shahre_ma;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.MemberSqlite;
import com.ariana.shahre_ma.WebService.SqliteTOjson;


public class Sign_Up extends ActionBarActivity {


    MemberSqlite mem;


    EditText name;
    EditText email;
    EditText city;
    EditText phone;
    EditText age;
    Spinner sex;
    EditText username;
    EditText pass;

    Boolean _sex=false;


    private static final String DATABASE_NAME = "DBshahrma.db";
    // Books table name
    private static final String TABLE_MEMBER = "member";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__up);
        mem=new MemberSqlite(this);
        Intialize();

        Spinner spn1 = (Spinner) findViewById(R.id.Spiner_Sex);
        String[] list1 = {"مرد", "زن"};
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
                        _sex=true;
                        break;
                    case 1:
                        //Toast.makeText(getApplicationContext(), "6", Toast.LENGTH_LONG).show();
                        _sex=false;
                        break;

                }
            }


            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

    public  void  AddTOmember(View v)
    {
        String Aname,Aemail,Acity,Aphone,Ausername,Apass;
        Boolean Asex;
        Integer Aage;

        Aname=name.getText().toString();
        Aemail=email.getText().toString();
        Acity=city.getText().toString();
        Aphone=phone.getText().toString();
        Aage=Integer.parseInt(age.getText().toString());
        Asex=_sex;
        Ausername=username.getText().toString();
        Apass=pass.getText().toString();
        try {
            mem.Add(Aname, Aemail, Aphone, Aage, Asex, Ausername, Apass, Integer.parseInt(Acity));
            String Result;
            SQLiteDatabase mydb = openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
            Cursor allrows = mydb.rawQuery("SELECT Name FROM " +TABLE_MEMBER , null);
            allrows.moveToFirst();
            Result = allrows.getString(0);
            allrows.close();
            mydb.close();
            SqliteTOjson json=new SqliteTOjson();
           // Toast.makeText(getApplication(),json.getSqliteTOjson(),Toast.LENGTH_LONG).show();
            name.setText(json.getSqliteTOjson());

        }
        catch (Exception e){
            Toast.makeText(getApplication(),e.toString(),Toast.LENGTH_LONG).show();}
    }

    public  void Intialize()
    {
        name=(EditText) findViewById(R.id.txtName);
        email=(EditText) findViewById(R.id.txtEmail);
        city=(EditText) findViewById(R.id.txtCity);
        phone=(EditText) findViewById(R.id.txtPhone);
        age=(EditText) findViewById(R.id.txtAge);
        sex=(Spinner) findViewById(R.id.Spiner_Sex);
        username=(EditText) findViewById(R.id.txtUsername);
        pass=(EditText) findViewById(R.id.txtpass);
    }

}
