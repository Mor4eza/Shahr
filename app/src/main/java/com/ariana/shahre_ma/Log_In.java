package com.ariana.shahre_ma;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetLoginJson;


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


        if(username.getText().toString()==null && password.getText().toString()==null) {

            error.setText("ثبت نام کنید");
        }
        else {
            final String url = "http://test.shahrma.com/api/ApiGiveMembersPermission?userName=" + username.getText().toString() + "&Password=" + password.getText().toString();
            final HTTPGetLoginJson httplogin=new HTTPGetLoginJson(this);
            httplogin.execute(url);

            android.os.Handler ha = new android.os.Handler();
            ha.postDelayed(new Runnable() {

                               @Override
                               public void run() {
                                   if (httplogin.get_Message_Login()>=0) {


                                       Intent i=new Intent(getApplicationContext(), MainActivity.class);
                                       startActivity(i);

                                   } else {

                                   }

                               }

                           },
                    3000);
        }

    }
}
