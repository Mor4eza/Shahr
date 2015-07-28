package com.ariana.shahre_ma.MyBusiness;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetAreaJosn;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetFieldActivityJson;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

public class My_Business extends ActionBarActivity {

    FieldClass fc=new FieldClass();
    Query query=new Query(this);
    RatingBar rate;
    TextView title;
    TextView address;
    FloatingActionButton discount;
    FloatingActionButton edit;
    FloatingActionButton add;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__business);

        Intialize();

        HTTPGetFieldActivityJson httpfield=new HTTPGetFieldActivityJson(this);
        httpfield.execute();

        HTTPGetAreaJosn httparea=new HTTPGetAreaJosn(this);
        httparea.execute();

        Log.i("BusinessID",String.valueOf(fc.GetBusiness_Id()));
        try {
            DataBaseSqlite db = new DataBaseSqlite(this);
            Cursor rows = db.select_AllBusinessId(fc.GetBusiness_Id());
            rows.moveToFirst();
            title.setText(rows.getString(1));//Market
            title.setTag( String.valueOf(rows.getInt(0)));//Id
            address.setText(rows.getString(7));//Address
            rate.setRating(rows.getFloat(19));//RateValue
        }
        catch(Exception e)
        {
            Log.e("SQLException",e.toString());
        }

    }

    public void add_business(View v){
        Intent i = new Intent(getApplicationContext(),Add_business.class);
        startActivity(i);

    }
    public void Intialize()
    {
        rate=(RatingBar) findViewById(R.id.my_business_rate);
        title=(TextView) findViewById(R.id.my_business_title);
        address=(TextView) findViewById(R.id.my_business_address);
        discount=(FloatingActionButton)findViewById(R.id.btn_discount);
        edit=(FloatingActionButton)findViewById(R.id.btn_edit_business);
        add=(FloatingActionButton)findViewById(R.id.btn_add_business);
    }
   public void discount(View v){

       Integer id=0;
       id=Integer.parseInt((String) title.getTag());
       if(id<=0)
       {
           fc.SetBusiness_Id(id);
           Toast.makeText(getApplicationContext(),"اول کسب کار ثبت کنید",Toast.LENGTH_LONG).show();
       }
       else {
           Intent i = new Intent(getApplicationContext(), Discount.class);
           startActivity(i);
       }
   }

    public void help1(){
        ViewTarget Hdiscount=new ViewTarget(R.id.btn_discount,this);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv=new ShowcaseView.Builder(this)
                // .setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                .setTarget(Hdiscount)
                .setContentTitle("تخفیف ها")
                .setContentText("برای ثبت تخفیف جدید و مدیریت تخفیف های قبلی از این دکمه استفاده کنید")
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
                sv.setButtonText("بعدی");
        sv.setButtonPosition(lps);
      sv.setOnShowcaseEventListener(new OnShowcaseEventListener() {
          @Override
          public void onShowcaseViewHide(ShowcaseView showcaseView) {
             help2();
          }

          @Override
          public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

          }

          @Override
          public void onShowcaseViewShow(ShowcaseView showcaseView) {

          }
      });
    }
    public void help2(){
        ViewTarget Hdiscount=new ViewTarget(R.id.btn_edit_business,this);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv=new ShowcaseView.Builder(this)
                // .setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                .setTarget(Hdiscount)
                .setContentTitle("ویرایش کسب و کار")
                .setContentText("برای ویرایش کسب و کار فعلی از این دکمه استفاده کنید")
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
        sv.setButtonText("بعدی");
        sv.setButtonPosition(lps);
        sv.setOnShowcaseEventListener(new OnShowcaseEventListener() {
            @Override
            public void onShowcaseViewHide(ShowcaseView showcaseView) {
              help3();
            }

            @Override
            public void onShowcaseViewDidHide(ShowcaseView showcaseView) {

            }

            @Override
            public void onShowcaseViewShow(ShowcaseView showcaseView) {

            }
        });
    }
    public void help3(){
        ViewTarget Hdiscount=new ViewTarget(R.id.btn_add_business,this);

        RelativeLayout.LayoutParams lps = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lps.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lps.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lps.addRule(RelativeLayout.CENTER_IN_PARENT);
        int margin = ((Number) (getResources().getDisplayMetrics().density * 80)).intValue();
        lps.setMargins(margin, margin, 10, margin);

        ShowcaseView sv=new ShowcaseView.Builder(this)
                // .setTarget(new ActionViewTarget(this, ActionViewTarget.Type.HOME))
                .setTarget(Hdiscount)
                .setContentTitle("اضافه کردن کسب و کار")
                .setContentText("با استفاده از این دکمه میتوانید کسب و کار خودتان را برای ما ارسال کنید تا در برنامه ثبت شود")
                .setStyle(R.style.CustomShowcaseTheme)
                .build();
        sv.setButtonText("خب");
        sv.setButtonPosition(lps);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_my_city, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement

        if (id == R.id.help) {

        help1();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
