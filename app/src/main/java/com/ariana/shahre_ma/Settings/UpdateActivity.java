package com.ariana.shahre_ma.Settings;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ariana.shahre_ma.R;

public class UpdateActivity extends ActionBarActivity {


    TextView TvLast;
    Button   BtnUpdate;
    ProgressBar PgUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        TvLast=(TextView)findViewById(R.id.tv_last_update);
        BtnUpdate=(Button)findViewById(R.id.btn_update_all);
        PgUpdate=(ProgressBar)findViewById(R.id.progressBar_update);

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
