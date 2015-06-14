package com.ariana.shahre_ma;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetLoginJson;

import java.net.URLEncoder;


public class Log_In extends ActionBarActivity {


    EditText username;
    EditText password;
    TextView error;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);


        username=(EditText) findViewById(R.id.et_username);
        password=(EditText) findViewById(R.id.et_password);
        error=(TextView) findViewById(R.id.tverror);

        try {
            DataBaseSqlite db = new DataBaseSqlite(this);
            Cursor allrows = db.select_Member();
            allrows.moveToNext();
            username.setText(allrows.getString(6));
            password.setText(allrows.getString(7));
        }
        catch (Exception e){}
    }

    public void  register(View v){

        Intent i = new Intent(getApplicationContext(),Sign_Up.class);
        startActivity(i);

    }

    public void Click_Login(View v)
    {


        //Code Login
        if(username.getText().toString()==null || password.getText().toString()==null) {

            error.setText("نام کاربری و رمز عبور را وارد کنید");
        }
        else {
            try {
                String nameuser = URLEncoder.encode(username.getText().toString(), "UTF-8");
                String passworduser = URLEncoder.encode(password.getText().toString(), "UTF-8");
                final String url = "http://test.shahrma.com/api/ApiGiveMembersPermission?userName=" + nameuser + "&Password=" + passworduser;
                final HTTPGetLoginJson httplogin = new HTTPGetLoginJson(this);
                httplogin.execute(url);

                android.os.Handler ha = new android.os.Handler();
                ha.postDelayed(new Runnable() {

                                   @Override
                                   public void run() {
                                 /*  if (httplogin.get_Message_Login()>0) {



                                       Intent i=new Intent(getApplicationContext(), MainActivity.class);
                                       startActivity(i);

                                   } else {*/
                                       // error.setText("نام کاربری یا رمز عبور اشتباه است");
                                       //  Toast.makeText(getApplication(), httplogin.get_Message_Login().toString(), Toast.LENGTH_LONG).show();
                                       //}

                                   }

                               },
                        3000);
            }
            catch (Exception e){}
        }

    }
}
