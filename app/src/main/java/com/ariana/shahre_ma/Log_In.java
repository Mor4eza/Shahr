package com.ariana.shahre_ma;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;


public class Log_In extends ActionBarActivity {


    TextView username;
    TextView password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);


        username=(TextView) findViewById(R.id.et_username);
        password=(TextView) findViewById(R.id.et_password);

        DataBaseSqlite db=new DataBaseSqlite(this);
        Cursor allrows=db.select_Member();
        allrows.moveToNext();
        username.setText(allrows.getString(6));
        password.setText(allrows.getString(7));
    }

    public void  register(View v){

        Intent i = new Intent(getApplicationContext(),Sign_Up.class);
        startActivity(i);

    }

    public void Click_Login(View v)
    {

        android.os.Handler ha = new android.os.Handler();
        ha.postDelayed(new Runnable() {

                           @Override
                           public void run() {

                           }

                       },
                3000);

    }
}
