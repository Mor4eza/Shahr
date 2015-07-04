package com.ariana.shahre_ma.MyProfile;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

public class Edit_User extends ActionBarActivity {

    EditText name;
    EditText email;
    AutoCompleteTextView city;
    EditText phone;
    EditText age;
    Spinner sex;
    EditText pass;
    EditText user;
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
      //  city.setText(String.valueOf(allrows.getInt(8)));
        getNameCity();
        phone.setText(allrows.getString(3));
        age.setText(String.valueOf(allrows.getInt(4)));
        pass.setText(allrows.getString(7));
        user.setText(allrows.getString(6));


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
        v.animate();

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

}
