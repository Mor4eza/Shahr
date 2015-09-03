package com.ariana.shahre_ma;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.TextView;

import com.tsengvn.typekit.TypekitContextWrapper;


public class AboutUs extends ActionBarActivity {

    TextView tvVersion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        tvVersion=(TextView)findViewById(R.id.tv_version);
        tvVersion.setText("نسخه:"+" "+ BuildConfig.VERSION_NAME);


    }

    public void share(View view) {

        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String shareBody = "شهر ما"+" \n" + " بانک اطلاعات مشاغل"+"\n"+"دانلود از:" +
                "\n"+"https://cafebazaar.ir/app/com.ariana.shahre_ma/?l=fa" ;
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "اشتراک گذاری با..."));

    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
}
