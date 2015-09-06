package com.ariana.shahre_ma.Settings;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetCollectionJson;
import com.drivemode.android.typeface.TypefaceHelper;

public class UpdateActivity extends ActionBarActivity {


    TextView tv_last;
    Button   BtnUpdate;

  public static   ProgressBar PgUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        try {
            tv_last = (TextView) findViewById(R.id.tv_lastUpdate);
            SelectDataBaseSqlite sdb = new SelectDataBaseSqlite(this);
            Cursor rows = sdb.select_Update();
            rows.moveToFirst();
            tv_last.setText(rows.getString(1));
        }
        catch (Exception e){}
        BtnUpdate=(Button)findViewById(R.id.btn_update_all);
        PgUpdate=(ProgressBar)findViewById(R.id.progressBar_update);
        PgUpdate.setVisibility(View.INVISIBLE);
        TypefaceHelper.getInstance().setTypeface(this, "font/Yekan.ttf");

        BtnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PgUpdate.setVisibility(View.VISIBLE);

                KeySettings setting=new KeySettings(UpdateActivity.this);
                setting.setUpdateAll(true);
                HTTPGetCollectionJson httpGetCollectionJson = new HTTPGetCollectionJson(UpdateActivity.this);
                httpGetCollectionJson.execute();
            }
        });
    }

}
