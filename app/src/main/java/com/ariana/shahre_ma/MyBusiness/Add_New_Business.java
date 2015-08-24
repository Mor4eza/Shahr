package com.ariana.shahre_ma.MyBusiness;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.DataBaseSqlite;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostBusinessJson;
import com.dd.CircularProgressButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.nhaarman.supertooltips.ToolTip;
import com.nhaarman.supertooltips.ToolTipRelativeLayout;
import com.nhaarman.supertooltips.ToolTipView;

import java.util.ArrayList;
import java.util.List;

public class Add_New_Business extends ActionBarActivity implements ImageView.OnClickListener {


    EditText Market_name;
    EditText Market_tell;
    EditText Market_mobile;
    EditText Market_fax;
    EditText Market_email;
    EditText Market_owner;
    EditText Market_address;
    EditText Market_desc;
    AutoCompleteTextView Market_subset;
    AutoCompleteTextView Market_city;
    AutoCompleteTextView Market_area;
    EditText Market_field;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;

    private ToolTipView myToolTipView;
    Integer Fields_ID[]=new Integer[7];
    Integer ViewId=0;
    String Path="";
    DateTime dt=new DateTime();
    FieldClass fc=new FieldClass();
    Query query=new Query(this);
    NetState net=new NetState(this);
    Uri currImageURI;
    String picturePath;
    Integer modatgh=3;
    Integer month;
    String date;
    Integer year;
    ToolTipRelativeLayout toolTipRelativeLayout;
    String str="";
    String latitude ="";
    String longitude ="";
    DataBaseSqlite db=new DataBaseSqlite(this);
    SqliteTOjson json=new SqliteTOjson(this);
    public static CircularProgressButton save_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_business);

        Initialize_Views();
        GetSubSet();
        GetNameCity();

        toolTipRelativeLayout = (ToolTipRelativeLayout) findViewById(R.id.tooltipRelativeLayout);



        Market_field.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ToolTip toolTip = new ToolTip()
                            .withText("زمینه کاری کسب و کار خودرا وارد کنید")
                            .withColor(getResources().getColor(R.color.accent_material_light))
                            .withShadow()
                            .withTextColor(Color.WHITE)
                            .withAnimationType(ToolTip.AnimationType.FROM_MASTER_VIEW);
                    myToolTipView = toolTipRelativeLayout.showToolTipForView(toolTip, Market_field);
                }else {
                    myToolTipView.remove();
                }
            }
        });


        Market_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("onItemClick", Market_city.getText().toString());
                GetNameArea(Market_city.getText().toString());
            }
        });


        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);



    }


    void Initialize_Views(){

        Market_name =(EditText)findViewById(R.id.add_market_name);
        Market_tell =(EditText)findViewById(R.id.add_market_tell);
        Market_mobile =(EditText)findViewById(R.id.add_market_phone);
        Market_fax =(EditText)findViewById(R.id.add_market_fax);
        Market_email =(EditText)findViewById(R.id.add_market_email);
        Market_owner =(EditText)findViewById(R.id.add_market_owner);
        Market_address =(EditText)findViewById(R.id.add_market_address);
        Market_desc =(EditText)findViewById(R.id.add_market_desc);
        Market_subset =(AutoCompleteTextView)findViewById(R.id.ac_subset);
        Market_area =(AutoCompleteTextView)findViewById(R.id.ac_area);
        Market_field =(EditText)findViewById(R.id.ac_field);
        Market_city =(AutoCompleteTextView)findViewById(R.id.ac_city);
        save_edit=(CircularProgressButton)findViewById(R.id.btn_save_edit);
        image1=(ImageView)findViewById(R.id.add_image1);
        image2=(ImageView)findViewById(R.id.add_image2);
        image3=(ImageView)findViewById(R.id.add_image3);
        image4=(ImageView)findViewById(R.id.add_image4);

    }


    public void add_business(View view)
    {
        try {

            if (Market_name.getText().toString().trim().equals("")) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("نام فروشگاه را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        Market_name.requestFocus();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else if (Market_tell.getText().length() == 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("شماره تلفن را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        Market_tell.requestFocus();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else if (Market_owner.getText().length() == 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("نام مدیر فروشگاه را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        //  Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        Market_owner.requestFocus();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else if (Market_address.getText().length() == 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("آدرس را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Market_address.requestFocus();

                    }
                });


                alertDialog.show();

            } else if (query.getAreaID(Market_area.getText().toString().trim()) < 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("منطقه خود را انتخاب کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Market_area.requestFocus();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else if (query.getsubsetID(Market_subset.getText().toString().trim()) < 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("زیر گروه را انتخاب کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Market_subset.requestFocus();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else if (Market_field.length() == 0) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("زمینه فعالیت خود را وارد کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog closed
                        // Toast.makeText(getApplicationContext(), "You clicked on OK", Toast.LENGTH_SHORT).show();
                        Market_field.requestFocus();
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else if (fc.GetLatitude_Business() == 0 || fc.GetLatitude_Business() == 0.0 || fc.GetLatitude_Business().equals(null)) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("چی پی اس مقداری ندارد");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else if (fc.GetLongtiude_Business().equals(null) || fc.GetLongtiude_Business() == 0 || fc.GetLongtiude_Business() == 0.0) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("موقیعت خود را انتخاب کنید");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                // Showing Alert Message
                alertDialog.show();
            } else if (net.checkInternetConnection() == false) {
                AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
                alertDialog.setTitle("هشدار ");
                alertDialog.setMessage("اینترنت قطع می باشد");
                alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();

            } else {
                save_edit.setIndeterminateProgressMode(true);
                save_edit.setProgress(50);


                str = json.getBusinessTOjsonArray(fc.GetBusiness_Id(), query.getMemberId(), Market_name.getText().toString().trim(),
                        Market_tell.getText().toString().trim(), Market_mobile.getText().toString().trim(),
                        Market_fax.getText().toString().trim(), Market_email.getText().toString().trim(),
                        Market_owner.getText().toString().trim(), Market_address.getText().toString().trim(),
                        Market_desc.getText().toString().trim(), dt.Now(), EXPDateTime(), "null"
                        , query.getsubsetID(Market_subset.getText().toString().trim()),
                        fc.GetLatitude_Business(), fc.GetLongtiude_Business(), query.getAreaID(Market_area.getText().toString().trim()),
                        "null", "null", Fields_ID[0], Fields_ID[1], Fields_ID[2],
                        Fields_ID[3], Fields_ID[4], Fields_ID[5], Fields_ID[6]);


                Log.i("JSONnewBusiness", str);
                HTTPPostBusinessJson httpbusiness = new HTTPPostBusinessJson(this);
                httpbusiness.SetBusinessJson(str);
                httpbusiness.execute();

            }

        }
        catch (Exception e){
            AlertDialog alertDialog = new AlertDialog.Builder(Add_New_Business.this).create();
            alertDialog.setTitle("هشدار ");
            alertDialog.setMessage("ثبت نشد دوباره امتحان کنید");
            alertDialog.setButton("باشه", new DialogInterface.OnClickListener()
            {
                public void onClick(DialogInterface dialog, int which)
                {

                }
            });
            alertDialog.show();
        }
    }




    public List<String> getId1() {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  =db.select_Subset();
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));


            } while (allrows.moveToNext());
        }

        return studentList;
    }

    public void GetSubSet()
    {
        try {

            ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getId1());
            Market_subset.setAdapter(adapter);

            //  multiAutoComplete.setAdapter(adapter);

            Market_subset.setThreshold(1);
        }
        catch (Exception e)
        {

        }
    }


    public void GetNameArea(String namecity)
    {
        try {

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getId(query.getCityId(namecity)));
            Market_area.setAdapter(adapter);

            Market_area.setThreshold(1);
        }
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }
    }


    public List<String> getId(Integer cityid) {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_Area(cityid);
        if (allrows.moveToFirst()) {
            do {
                Log.i("area",allrows.getString(1));
                studentList.add(allrows.getString(1));
            } while (allrows.moveToNext());
        }

        return studentList;
    }



    public void GetNameCity()
    {
        try {

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getId2());
            Market_city.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }
    }
    public List<String> getId2() {

        DataBaseSqlite db=new DataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_AllCity();
        if (allrows.moveToFirst()) {
            do {

                Log.i("city",allrows.getString(1));
                studentList.add(allrows.getString(1));


            } while (allrows.moveToNext());
        }

        return studentList;
    }


    private String EXPDateTime()
    {
        month=dt.Month();
        month=month+modatgh;

        if(month>12)
        {
            year=((dt.Yaer())+1);
            month=(12-month);
            date=year.toString()+"-"+month.toString()+"-"+dt.Day();
        }

        else
        {

            date=dt.Yaer()+"-"+month.toString()+"-"+dt.Day();
        }


        Toast.makeText(getApplicationContext(), date, Toast.LENGTH_LONG).show();
        return  date;

    }

    public void select_map(View view) {
        try {
            int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
            // Showing status
            if (status == ConnectionResult.SUCCESS) {
                Intent i = new Intent(getBaseContext(), My_Business_Map.class);
                startActivity(i);
            } else {
                int requestCode = 10;
                AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                alertDialog.setTitle("هشدار");
                alertDialog.setMessage("نسخه Google Play Service  شما قدیمی می باشد. لطفا بروز رسانی کنید");
                alertDialog.setButton("خب", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                alertDialog.show();
            }

        } catch (Exception e) {

        }
    }

    @Override
    public void onClick(final View v) {

        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.image_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                ViewId=v.getId();
                if (item.getTitle().equals("دوربین"))
                    //my_business.openCamera();
                    openCamera();
                else if (item.getTitle().equals("گالری"))
                    selectImageFromGallery();

                else if (item.getTitle().equals("حذف"))
                    Log.i("", "");
                return true;
            }
        });
        popup.show();//showing popup menu



    }

    public void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
       // intent.putExtra(MediaStore.EXTRA_OUTPUT, currImageURI);
        startActivityForResult(intent, 100);

    }
    public void selectImageFromGallery() {

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");
        startActivityForResult(pickIntent, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                // currImageURI is the global variable I’m using to hold the content:
                currImageURI = data.getData();
                System.out.println("Current image Path is —--->" + getRealPathFromURI(currImageURI));
                //  /*TextView tv_path = (TextView) findViewById(R.id.textView);
                 Path=getRealPathFromURI(currImageURI);

                Bitmap myBitmap = BitmapFactory.decodeFile(getTitle().toString());
                if(ViewId==image1.getId()){
                    image1.setImageBitmap(myBitmap);
                }else if(ViewId==image2.getId()){
                    image2.setImageBitmap(myBitmap);
                }else if(ViewId==image3.getId()){
                    image3.setImageBitmap(myBitmap);
                }else if(ViewId==image4.getId()){
                    image4.setImageBitmap(myBitmap);
                }


            }else if(requestCode == 100){
                currImageURI = data.getData();
                Path=getRealPathFromURI(currImageURI);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                if(ViewId==image1.getId()){
                    image1.setImageBitmap(photo);
                }else if(ViewId==image2.getId()){
                    image2.setImageBitmap(photo);
                }else if(ViewId==image3.getId()){
                    image3.setImageBitmap(photo);
                }else if(ViewId==image4.getId()){
                    image4.setImageBitmap(photo);
                }
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
