package com.ariana.shahre_ma.MyProfile;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.MainActivity;
import com.ariana.shahre_ma.R;

import java.util.ArrayList;
import java.util.List;

public class My_Profile extends Activity {


    Spinner sp_city;
    TextView tv_user_name;
    TextView tv_user_email;
    TextView tv_user_phone;
    TextView tv_user_sex;
    TextView tv_user_age;
    Button   btn_log_out;
    Button   btn_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__profile);
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar1);
        toolbar.collapseActionView();
        init_views();
        SpinnerSetUp();

    }
    public void init_views(){
        sp_city=(Spinner)findViewById(R.id.spinner_city);
        tv_user_name=(TextView)findViewById(R.id.tv_user_name);
        tv_user_email=(TextView)findViewById(R.id.tv_user_email);
        tv_user_phone=(TextView)findViewById(R.id.tv_user_phone);
        tv_user_sex=(TextView)findViewById(R.id.tv_user_sex);
        tv_user_age=(TextView)findViewById(R.id.tv_user_age);
        btn_log_out=(Button)findViewById(R.id.log_out);
        btn_edit = (Button)findViewById(R.id.user_edit);

        try {

            DataBaseSqlite db = new DataBaseSqlite(this);
            Cursor allrows = db.select_Member();
            allrows.moveToFirst();
            tv_user_email.setText(allrows.getString(2));
            tv_user_phone.setText(allrows.getString(3));
            tv_user_age.setText(String.valueOf(allrows.getInt(4)));
            tv_user_name.setText(allrows.getString(1));
            String sex=allrows.getString(5);
                if(sex.equals("true"))
                    tv_user_sex.setText("مرد");
                else
                    tv_user_sex.setText("زن");


        }
        catch (Exception e){
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_LONG).show();
        }


    }

    /**
     * Log_Out Member
     * @param v
     */
    public void Log_Out(View v){
        try {
            DataBaseSqlite db = new DataBaseSqlite(this);
            db.delete_Member();


            Intent i=new Intent(getApplicationContext(), MainActivity.class);
             startActivity(i);
        }
        catch (SQLiteException e)
        {
            Log.i("ExceptionSQL",e.toString());

        }

    }
    public void edit(View v){

        Intent i=new Intent(getApplicationContext(),Edit_User.class);
        startActivity(i);

    }
    void SpinnerSetUp(){

        DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor allrows=db.select_AllCity();

        sp_city.setPrompt("انتخاب شهر:");
        List<String> list = new ArrayList<String>();
        if(allrows.moveToFirst())
        {
            do
            {
                list.add(allrows.getString(1));

            }while (allrows.moveToNext());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_city.setAdapter(dataAdapter);

    }
}