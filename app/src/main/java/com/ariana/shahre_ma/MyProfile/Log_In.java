package com.ariana.shahre_ma.MyProfile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.MainActivity;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetLoginJson;
import com.dd.CircularProgressButton;

import java.net.URLEncoder;


public class Log_In extends ActionBarActivity {


    EditText username;
    EditText password;
    TextView error;
    public static CircularProgressButton btn;
    String mesage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log__in);

        username = (EditText) findViewById(R.id.et_username);
        password = (EditText) findViewById(R.id.et_password);
        error = (TextView) findViewById(R.id.tverror);
        btn=(CircularProgressButton)findViewById(R.id.btn_login);


        password.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean isValidKey = event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER;
                boolean isValidAction = actionId == EditorInfo.IME_ACTION_DONE;

                if (isValidKey || isValidAction) {
                  Click_Login(v);
                }
                return false;
            }
        });
        
    }

    public void register(View v) {

        Intent i = new Intent(getApplicationContext(), Sign_Up.class);
        startActivity(i);
        finish();

    }
    public void forget(View v){
    Forget_Dialog dialog=new Forget_Dialog(this);
        dialog.show();


    }

    public void Click_Login(View v) {

        //Code Login
        if (username.getText().toString().equals("") || password.getText().toString().equals("")) {

            error.setText("نام کاربری و رمز عبور را وارد کنید");
        } else {
            try {
                btn.setIndeterminateProgressMode(true);
                btn.setProgress(50);
                    String nameuser = URLEncoder.encode(username.getText().toString(), "UTF-8");
                    String passworduser = URLEncoder.encode(password.getText().toString(), "UTF-8");
                    final String url = "http://test.shahrma.com/api/ApiGiveMembersPermission?userName=" + nameuser + "&Password=" + passworduser;
                     new HTTPGetLoginJson(this).execute(url);

            } catch (Exception e)
            {
                Toast.makeText(getApplication(), e.toString(), Toast.LENGTH_LONG).show();
            }
        }



    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent i=new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i=new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                }
                return true;

    }
}
