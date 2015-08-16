package com.ariana.shahre_ma.MyBusiness;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ariana.shahre_ma.Cards.Business_Card_Adapter;
import com.ariana.shahre_ma.Cards.Business_Card_Items;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.Settings.KeySettings;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetAreaJosn;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessMemberJson;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetFieldActivityJson;
import com.github.amlcurran.showcaseview.OnShowcaseEventListener;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.ViewTarget;

import jp.wasabeef.recyclerview.animators.OvershootInLeftAnimator;
import jp.wasabeef.recyclerview.animators.adapters.ScaleInAnimationAdapter;

public class My_Business extends ActionBarActivity {

    FieldClass fc=new FieldClass();
    Query query=new Query(this);
    KeySettings setting=new KeySettings(this);
    TextView title;
    FloatingActionButton discount;
    public static ProgressBar pg;
    Uri currImageURI;
    String picturePath;
    String ba1;

    public static RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static RecyclerView.Adapter job_list_Adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my__business);
        pg=(ProgressBar)findViewById(R.id.pb_business);




        HTTPGetBusinessMemberJson httpGetBusinessMemberJson = new HTTPGetBusinessMemberJson(My_Business.this);
        httpGetBusinessMemberJson.SetUrl_businessMember(query.getMemberId());// get id member
        httpGetBusinessMemberJson.execute();// Run


         Intialize();
         setCards();


        if(!setting.getFieldActivity())
        {
            HTTPGetFieldActivityJson httpGetFieldActivityJson = new HTTPGetFieldActivityJson(this);
            httpGetFieldActivityJson.execute();
        }
        else  if(!setting.getArea())
        {
            HTTPGetAreaJosn httparea = new HTTPGetAreaJosn(this);
            httparea.execute();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
       /* job_list_Adapter = new Business_Card_Adapter(this);
        ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(job_list_Adapter);
        alphaAdapter.setDuration(400);
        mRecyclerView.setAdapter(alphaAdapter);
        job_list_Adapter.notifyItemChanged(0);
        job_list_Adapter.notifyDataSetChanged();
        Log.i("Resume", "Resume");*/
    }

    public void add_business(View v){

        Intent i = new Intent(getApplicationContext(),Add_New_Business.class);
        startActivity(i);
    }
    public void Intialize()
    {
        //rate=(RatingBar) findViewById(R.id.my_business_rate);
        title=(TextView) findViewById(R.id.my_business_title);
       // address=(TextView) findViewById(R.id.my_business_address);
        discount=(FloatingActionButton)findViewById(R.id.btn_discount);

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

    private void setCards(){
        try {


            mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_business);
            mRecyclerView.setItemAnimator(new OvershootInLeftAnimator());
            mRecyclerView.getItemAnimator().setAddDuration(1000);
            mRecyclerView.getItemAnimator().setChangeDuration(1000);
            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(this);
            mRecyclerView.setLayoutManager(mLayoutManager);
            job_list_Adapter = new Business_Card_Adapter(this);
            ScaleInAnimationAdapter alphaAdapter = new ScaleInAnimationAdapter(job_list_Adapter);
            alphaAdapter.setDuration(400);
            mRecyclerView.setAdapter(alphaAdapter);
            job_list_Adapter.notifyItemChanged(0);
            job_list_Adapter.notifyDataSetChanged();
        }
        catch (Exception e){}
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_my_city, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.help) {

        help1();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1 || requestCode==100) {
                // currImageURI is the global variable I’m using to hold the content:
                currImageURI = data.getData();
                System.out.println("Current image Path is —--->" + getRealPathFromURI(currImageURI));
              //  /*TextView tv_path = (TextView) findViewById(R.id.textView);
               setTitle(getRealPathFromURI(currImageURI));

                Bitmap myBitmap = BitmapFactory.decodeFile(getTitle().toString());
                ImageView myImage = (ImageView) findViewById(R.id.my_business_image1);
                myImage.setImageBitmap(myBitmap);
                Business_Card_Items business_card_items=new Business_Card_Items();
                business_card_items.setThumbnail(1);

            }
        }
    }
    public String getRealPathFromURI(Uri contentUri) {
        String [] proj={MediaStore.Images.Media.DATA};
        android.database.Cursor cursor = managedQuery( contentUri,
                proj, // Which columns to return
                null, // WHERE clause; which rows to return (all rows)
                null, // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        picturePath = cursor.getString(column_index);
        return cursor.getString(column_index);
    }

}
