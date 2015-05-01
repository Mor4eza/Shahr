package com.ariana.shahre_ma.MyBusiness;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.content.LocalBroadcastManager;
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
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.ariana.shahre_ma.Bazarche.CropingOption;
import com.ariana.shahre_ma.Bazarche.CropingOptionAdapter;
import com.ariana.shahre_ma.Date.CalendarTool;
import com.ariana.shahre_ma.Date.DateTime;
import com.ariana.shahre_ma.DateBaseSqlite.Query;
import com.ariana.shahre_ma.DateBaseSqlite.SelectDataBaseSqlite;
import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MessageDialog;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServiceGet.HTTPGetBusinessImageJson;
import com.ariana.shahre_ma.WebServiceGet.SqliteTOjson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostBusinessEditJson;
import com.ariana.shahre_ma.WebServicePost.HTTPPostUploadImage;
import com.ariana.shahre_ma.WebServiceSend.HTTPDeleteBusinessImageURL;
import com.dd.CircularProgressButton;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.squareup.picasso.Picasso;
import com.tsengvn.typekit.TypekitContextWrapper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Edit_business extends ActionBarActivity implements ImageView.OnClickListener{
    
    EditText Market_name;
    EditText Market_tell;
    EditText Market_mobile;
    EditText Market_fax;
    EditText Market_email;
    EditText Market_owner;
    EditText Market_address;
    EditText Market_desc;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    AutoCompleteTextView Market_subset;
    AutoCompleteTextView Market_area;
    AutoCompleteTextView Market_city;
    Integer Rate = 0;
    Double value =0.0;
    String src="";
    MultiAutoCompleteTextView Market_field;
    Integer Fields_ID[]=new Integer[7];
    DateTime dt=new DateTime();
    FieldClass fc=new FieldClass();
    Query query=new Query(Edit_business.this);
    CalendarTool ct=new CalendarTool();
    NetState net=new NetState(this);

    private Uri mImageCaptureUri;
    private File outPutFile = null;
    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;

    String picturePath;
    String Path="";
    Integer ViewId=0;
    Integer modatgh=3;
    Integer month;
    String date;
    Integer year;
    MessageDialog messageDialog=new MessageDialog(this);
    public static CircularProgressButton save_edit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_business);

        Initialize_Views();
        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");

        GetSubSet();
        GetNameCity();
        GetNameActivity();
        Show_Business();


        HTTPGetBusinessImageJson httpGetBusinessImageJson=new HTTPGetBusinessImageJson(this);
        httpGetBusinessImageJson.SetBusinessId(fc.GetBusiness_Id());
        httpGetBusinessImageJson.execute();

        LocalBroadcastManager.getInstance(this).registerReceiver(mImageReceiver, new IntentFilter("BusinessImage"));

        Market_city.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
       Market_field =(MultiAutoCompleteTextView)findViewById(R.id.ac_field);
       Market_city =(AutoCompleteTextView)findViewById(R.id.ac_city);
       save_edit = (CircularProgressButton)findViewById(R.id.btn_save_edit);
       image1=(ImageView)findViewById(R.id.edit_image1);
       image2=(ImageView)findViewById(R.id.edit_image2);
       image3=(ImageView)findViewById(R.id.edit_image3);
       image4=(ImageView)findViewById(R.id.edit_image4);
       
   }

    void Show_Business()
    {

       try {
           SelectDataBaseSqlite sdb=new SelectDataBaseSqlite(this);
            Cursor rows = sdb.select_AllBusinessId(fc.GetBusiness_Id());
            rows.moveToFirst();

            Market_name.setText(rows.getString(1));//Market
            Market_tell.setText(rows.getString(2));//Phone
            Market_mobile.setText(rows.getString(3));//Mobile
            Market_fax.setText(rows.getString(4));//Fax
            Market_email.setText(rows.getString(5));//Email
            Market_owner.setText(rows.getString(6));//BusinessOwner
            Market_address.setText(rows.getString(7));//Address
            Market_desc.setText(rows.getString(8));//Description
            Market_city.setText(query.getCityName(rows.getInt(22)));//City Name
            Market_subset.setText(query.getsubsetName(rows.getInt(9)));//Subset Name

            value=rows.getDouble(19);
            Rate=rows.getInt(23);
            src=rows.getString(21);

            Cursor rows2 = sdb.select_AreaName(rows.getInt(20));
            rows2.moveToFirst();
            Market_area.setText(rows2.getString(0));

           for (int i = 0; i < 7; i++) {
                if (rows.getInt((12) + (i)) > 0) {

                    Cursor rows3 = sdb.select_FieldActivityName(rows.getInt((12) + (i)));
                    rows3.moveToFirst();

                    Market_field.setText(Market_field.getText().toString() + rows3.getString(0) + ", ");
                }
            }
        }
        catch (Exception e)
        {
            Log.i("Exception", e.toString());
        }
       // Market_gharar.setText(rows.getString(8));
    }
    public void save_edit(View v){

        try {

            String str = "";
            String strJson="";
            SelectDataBaseSqlite db = new SelectDataBaseSqlite(this);
            SqliteTOjson json = new SqliteTOjson(this);

            String strmulti = Market_field.getText().toString();
            String strCount[] = strmulti.split(",");
            for (int i = 0; i < 7; i++)
                Fields_ID[i] = 0;
            for (int i = 0; i < strCount.length - 1; i++) {

                String resultNameField = strCount[i].replace(",", "");
                String namefield = resultNameField.replaceAll("^\\s+|\\s+$", "");
                Log.e("field",namefield);
                Cursor rows = db.select_FieldActivityId(namefield.trim());
                if(rows.getCount() > 0) {
                    rows.moveToFirst();
                    try {
                        Fields_ID[i] = rows.getInt(0);


                    } catch (Exception e) {
                        Log.e("ExceptionFieldsID", e.toString());
                    }
                }


            }


            if (Market_name.getText().toString().trim().equals("")) {
                Market_name.requestFocus();
                Market_name.setError("نام فروشگاه را وارد کنید");

            } else if (Market_tell.getText().length() == 0) {
                Market_tell.requestFocus();
                Market_tell.setError("تلقن را وارد کنید");
            } else if (Market_owner.getText().length() == 0) {
                Market_owner.requestFocus();
                Market_owner.setError("نام مدیر فروشگاه را وارد کنید");

            } else if (Market_address.getText().length() == 0) {
                Market_address.requestFocus();
                Market_address.setError("آدرس را وارد کنید");

            } else if (query.getAreaID(Market_area.getText().toString().trim()) < 0) {
                Market_area.requestFocus();
                Market_area.setError("نام منطقه را وارد کنید");

            } else if (query.getsubsetID(Market_subset.getText().toString().trim()) < 0) {
                Market_subset.requestFocus();
                Market_subset.setError("نام منطقه را وارد کنید");

            } else if (Fields_ID[0]==0) {
                Market_field.setError("زمینه فعالیت خود را وارد کنید");
                Market_field.requestFocus();

            } else if (fc.GetLatitude_Business() == 0 || fc.GetLatitude_Business() == 0.0 || fc.GetLatitude_Business().equals(null)) {
                messageDialog.ShowMessage("هشدار", "موقیعت کسب و کار خود را روی نقشه انتخاب کنید", "باشه", "false");
            } else if (fc.GetLongtiude_Business().equals(null) || fc.GetLongtiude_Business() == 0 || fc.GetLongtiude_Business() == 0.0) {
                messageDialog.ShowMessage("هشدار", "موقیعت کسب و کار خود را روی نقشه انتخاب کنید", "باشه", "false");
            } else if (net.checkInternetConnection() == false) {
                messageDialog.ShowMessage("هشدار", "اینترنت قطع می باشد", "باشه", "false");
            } else {
                save_edit.setIndeterminateProgressMode(true);
                save_edit.setProgress(50);

                str = json.getBusinessTOjson(fc.GetBusiness_Id(), Market_name.getText().toString().trim(),
                        Market_tell.getText().toString().trim(), Market_mobile.getText().toString().trim(),
                        Market_fax.getText().toString().trim(), Market_email.getText().toString().trim(),
                        Market_owner.getText().toString().trim(), Market_address.getText().toString().trim(),
                        Market_desc.getText().toString().trim(), ct.getGregorianDate()+dt.Time(), EXPDateTime(), "null"
                        , query.getsubsetID(Market_subset.getText().toString().trim()),
                        fc.GetLatitude_Business(), fc.GetLongtiude_Business(),
                        query.getAreaID(Market_area.getText().toString().trim()),
                        "null", "null",query.getMemberId(), Fields_ID[0], Fields_ID[1], Fields_ID[2],
                        Fields_ID[3], Fields_ID[4], Fields_ID[5], Fields_ID[6]);

               strJson = json.getBusinessTOjson(fc.GetBusiness_Id(), Market_name.getText().toString().trim(),
                        Market_tell.getText().toString().trim(), Market_mobile.getText().toString().trim(),
                        Market_fax.getText().toString().trim(), Market_email.getText().toString().trim(),
                        Market_owner.getText().toString().trim(), Market_address.getText().toString().trim(),
                        Market_desc.getText().toString().trim(), ct.getGregorianDate()+dt.Time(), EXPDateTime(), "null"
                        , query.getsubsetID(Market_subset.getText().toString().trim()),
                        fc.GetLatitude_Business(), fc.GetLongtiude_Business(),
                        query.getAreaID(Market_area.getText().toString().trim()),
                        "null", "null",query.getMemberId(), Fields_ID[0], Fields_ID[1], Fields_ID[2],
                        Fields_ID[3], Fields_ID[4], Fields_ID[5], Fields_ID[6],Rate,value,src);

                    fc.SetEditBusiness_Json(strJson);
                HTTPPostBusinessEditJson httpbusiness = new HTTPPostBusinessEditJson(this);
                httpbusiness.SetBusinessJson(str);
                httpbusiness.execute();
            }
        }
        catch (Exception e){
           MessageDialog messageDialog=new MessageDialog(this);
            messageDialog.ShowMessage("پیام", "ویرایش نشد . دوباره امتحان کنید", "باشه", "false");
        }
    }


    public List<String> getId2() {

        SelectDataBaseSqlite db=new SelectDataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows=db.select_FieldActivity();
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));


            } while (allrows.moveToNext());
        }

        return studentList;
    }

    public void GetNameActivity()
    {
        try {

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getId2());
            Market_field.setAdapter(adapter);
            Market_field.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        }
        catch (Exception e)
        {
          Log.e("ExceptionSQL", e.toString());
        }
    }


    public List<String> getId1() {

        SelectDataBaseSqlite db=new SelectDataBaseSqlite(this);
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

        SelectDataBaseSqlite db=new SelectDataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_Area(cityid);
        if (allrows.moveToFirst()) {
            do {

                studentList.add(allrows.getString(1));


            } while (allrows.moveToNext());
        }

        return studentList;
    }

    public void GetNameCity()
    {
        try {

            ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getId3());
            Market_city.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Log.e("ExceptionSQL", e.toString());
        }
    }
    public List<String> getId3() {

        SelectDataBaseSqlite db=new SelectDataBaseSqlite(this);
        List<String> studentList = new ArrayList<String>();
        Cursor allrows  = db.select_AllCity();
        if (allrows.moveToFirst()) {
            do {

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


       //  Toast.makeText(getApplicationContext(),date,Toast.LENGTH_LONG).show();
        return  date;

    }
    public void select_map(View v ) {

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
                ViewId = v.getId();
                if (item.getTitle().equals("دوربین")) {
                    if (v.getTag() != null) {
                        Toast.makeText(getApplicationContext(), "ابتدا تصویر قبلی را حذف نمایید", Toast.LENGTH_LONG).show();
                    }else{
                        openCamera();
                    }

                } else if (item.getTitle().equals("گالری"))
                    if (v.getTag() != null) {
                        Toast.makeText(getApplicationContext(), "ابتدا تصویر قبلی را حذف نمایید", Toast.LENGTH_LONG).show();
                    }else{
                        selectImageFromGallery();
                    }

                else if (item.getTitle().equals("حذف")) {

                        HTTPDeleteBusinessImageURL deleteBusinessImageURL = new HTTPDeleteBusinessImageURL(Edit_business.this);
                        deleteBusinessImageURL.SetDeleteBusinessImage(fc.GetBusiness_Id(), String.valueOf(v.getTag()), 1);
                        deleteBusinessImageURL.execute();


                }
                return true;
            }
        });
        popup.show();//showing popup menu



    }

    public void openCamera() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
        mImageCaptureUri = Uri.fromFile(f);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
        startActivityForResult(intent, CAMERA_CODE);
    }
    public void selectImageFromGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, GALLERY_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {
            mImageCaptureUri = data.getData();
            CropingIMG();

        } else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {
            CropingIMG();
        } else if (requestCode == CROPING_CODE) {
            try {
                if(outPutFile.exists()){
                    Bitmap photo = decodeFile(outPutFile);
                    Path= outPutFile.toString();
                    if(ViewId==image1.getId()){
                        image1.setImageBitmap(photo);
                        UploadImage();
                    }else if(ViewId==image2.getId()){
                        image2.setImageBitmap(photo);
                        UploadImage();
                    }else if(ViewId==image3.getId()){
                        image3.setImageBitmap(photo);
                        UploadImage();
                    }else if(ViewId==image4.getId()){
                        image4.setImageBitmap(photo);
                        UploadImage();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "خطایی رخ داد! دوباره امتحان کنید", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void UploadImage()
    {
        HTTPPostUploadImage uploadImage=new HTTPPostUploadImage(this);
        uploadImage.SetImage(fc.GetBusiness_Id(),1);
        uploadImage.setFileImage(Path);
        uploadImage.execute();
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

    private void LoadImage()
    {
        String urlImage[]=new String[4];
        String imageName[]=new String[4];
        try
        {
            SelectDataBaseSqlite db=new SelectDataBaseSqlite(this);
            int i=0;
            Cursor rows=db.select_BusinessImage(fc.GetBusiness_Id());
            if(rows.moveToFirst())
            {
                do
                {
                    if(i<=3) {
                        urlImage[i] = "http://www.shahrma.com/image/business/" + rows.getString(2);
                        imageName[i] = rows.getString(2);
                    }
                    i++;

                }while (rows.moveToNext());
                image1.setTag(imageName[0]);
                image2.setTag(imageName[1]);
                image3.setTag(imageName[2]);
                image4.setTag(imageName[3]);
                Picasso.with(this).load(urlImage[0]).placeholder(R.drawable.fab_plus_icon).error(R.drawable.img_not_found).into(image1);
                Picasso.with(this).load(urlImage[1]).placeholder(R.drawable.fab_plus_icon).error(R.drawable.img_not_found).into(image2);
                Picasso.with(this).load(urlImage[2]).placeholder(R.drawable.fab_plus_icon).error(R.drawable.img_not_found).into(image3);
                Picasso.with(this).load(urlImage[3]).placeholder(R.drawable.fab_plus_icon).error(R.drawable.img_not_found).into(image4);

            }



        }
        catch (Exception e)
        {

        }

    }
    private BroadcastReceiver mImageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent)
        {
                LoadImage();

        }

    };
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }
    private void CropingIMG()
    {
        final ArrayList localArrayList = new ArrayList();
        Intent localIntent1 = new Intent("com.android.camera.action.CROP");
        localIntent1.setType("image/*");
        List localList = getPackageManager().queryIntentActivities(localIntent1, 0);
        int i = localList.size();
        if (i == 0)
        {
            Toast.makeText(this, "برنامه ای برای ویرایش عکس پیدا نشد...!", Toast.LENGTH_LONG).show();
            return;
        }
        localIntent1.setData(this.mImageCaptureUri);
        localIntent1.putExtra("outputX", 800);
        localIntent1.putExtra("outputY", 600);
        localIntent1.putExtra("aspectX", 1);
        localIntent1.putExtra("aspectY", 1);
        localIntent1.putExtra("scale", true);
        localIntent1.putExtra("output", Uri.fromFile(this.outPutFile));
        if (i == 1)
        {
            Intent localIntent2 = new Intent(localIntent1);
            ResolveInfo localResolveInfo1 = (ResolveInfo)localList.get(0);
            localIntent2.setComponent(new ComponentName(localResolveInfo1.activityInfo.packageName, localResolveInfo1.activityInfo.name));
            startActivityForResult(localIntent2, 301);
            return;
        }
        Iterator localIterator = localList.iterator();
        for (;;)
        {
            if (!localIterator.hasNext())
            {
                CropingOptionAdapter localCropingOptionAdapter = new CropingOptionAdapter(getApplicationContext(), localArrayList);
                AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
                localBuilder.setTitle("برش عکس با:");
                localBuilder.setCancelable(false);
                localBuilder.setAdapter(localCropingOptionAdapter, new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
                    {
                        startActivityForResult(((CropingOption)localArrayList.get(paramAnonymousInt)).appIntent, 301);
                    }
                });
                localBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
                {
                    public void onCancel(DialogInterface paramAnonymousDialogInterface)
                    {
                        if (mImageCaptureUri != null)
                        {
                            getContentResolver().delete(mImageCaptureUri, null, null);
                            mImageCaptureUri = null;
                        }
                    }
                });
                localBuilder.create().show();
                return;
            }
            ResolveInfo localResolveInfo2 = (ResolveInfo)localIterator.next();
            CropingOption localCropingOption = new CropingOption();
            localCropingOption.title = getPackageManager().getApplicationLabel(localResolveInfo2.activityInfo.applicationInfo);
            localCropingOption.icon = getPackageManager().getApplicationIcon(localResolveInfo2.activityInfo.applicationInfo);
            localCropingOption.appIntent = new Intent(localIntent1);
            localCropingOption.appIntent.setComponent(new ComponentName(localResolveInfo2.activityInfo.packageName, localResolveInfo2.activityInfo.name));
            localArrayList.add(localCropingOption);
        }
    }


    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 512;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
        }
        return null;
    }
}
