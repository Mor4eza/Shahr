package com.ariana.shahre_ma.MyProfile;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceSend.HTTPSendForgetMemberURL;

/**
 * Created by ariana2 on 7/23/2015.
 */
public class Forget_Dialog extends Dialog {

    Button btnSend;
    EditText etEmail;
    Context context;
    public Forget_Dialog(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.forget_dialog);
        btnSend=(Button)findViewById(R.id.btn_send);
        etEmail=(EditText)findViewById(R.id.et_email);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HTTPSendForgetMemberURL httpSendForgetMemberURL=new HTTPSendForgetMemberURL(context);
                httpSendForgetMemberURL.Setusername(etEmail.getText().toString());
                httpSendForgetMemberURL.execute();
                Log.i("Send", etEmail.getText().toString());
            }
        });

    }
}
