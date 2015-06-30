package com.ariana.shahre_ma.MyProfile;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.ariana.shahre_ma.R;

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
        toolbar.setTitle("نام");
        toolbar.collapseActionView();
        init_views();


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

    }

    public void edit(View v){

        Intent i=new Intent(getApplicationContext(),Edit_User.class);
        startActivity(i);

    }
}