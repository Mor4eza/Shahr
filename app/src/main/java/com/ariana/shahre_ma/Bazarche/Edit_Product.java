package com.ariana.shahre_ma.Bazarche;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.Fields.FieldDataBase;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServicePost.HTTPPostUploadImage;

public class Edit_Product extends ActionBarActivity implements ImageView.OnClickListener
{
    EditText tv_product_name;
    EditText tv_product_price;
    EditText tv_product_tell;
    EditText tv_product_mobile;
    EditText tv_product_email;
    EditText tv_product_desc;
    EditText tv_product_property;
    EditText tv_product_address;
    AutoCompleteTextView tv_product_subset;
    AutoCompleteTextView tv_product_city;
    AutoCompleteTextView tv_product_area;
    RadioGroup radioGroup;
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    Uri currImageURI;
    String picturePath;
    String Path="";
    Integer ViewId=0;
    FieldDataBase fieldDataBase=new FieldDataBase();
    FieldClass fc=new FieldClass();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__product);
        initViews();
        ShowData();
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.tavafoq) {
                    Log.i("cheked", "tavafoq");
                } else {
                    Log.i("cheked", "maqtoo");
                }
            }
        });

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);
    }

    public void product_saveEdit(View view)
    {


    }

    private  void ShowData()
    {

        Integer i=fc.GetNumber();
        tv_product_tell.setText(fieldDataBase.getPhone__Product().get(i));
        tv_product_mobile.setText(fieldDataBase.getMobile_Product().get(i));
        tv_product_desc.setText(fieldDataBase.getDescription_Product().get(i));
        tv_product_property.setText(fieldDataBase.getProperty_Product().get(i));
        tv_product_email.setText(fieldDataBase.getEmail_Product().get(i));
        tv_product_address.setText(fieldDataBase.getAddress_Product().get(i));
        tv_product_name.setText(fieldDataBase.getName_Product().get(i));
        tv_product_price.setText(String.valueOf(fieldDataBase.getprice_Product().get(i)));
    }


    private void initViews(){
        tv_product_name=(EditText)findViewById(R.id.edit_product_name);
        tv_product_price=(EditText)findViewById(R.id.edit_product_price);
        tv_product_tell=(EditText)findViewById(R.id.edit_product_tell);
        tv_product_mobile=(EditText)findViewById(R.id.edit_product_phone);
        tv_product_email=(EditText)findViewById(R.id.edit_product_email);
        tv_product_desc=(EditText)findViewById(R.id.edit_product_desc);
        tv_product_property=(EditText)findViewById(R.id.edit_product_property);
        tv_product_address=(EditText)findViewById(R.id.edit_product_address);
        tv_product_subset=(AutoCompleteTextView)findViewById(R.id.edit_ac_product_subset);
        tv_product_city=(AutoCompleteTextView)findViewById(R.id.edit_ac_product_city);
        tv_product_area=(AutoCompleteTextView)findViewById(R.id.edit_ac_product_area);
        radioGroup=(RadioGroup)findViewById(R.id.radio_price);
        image1=(ImageView)findViewById(R.id.edit_image1);
        image2=(ImageView)findViewById(R.id.edit_image2);
        image3=(ImageView)findViewById(R.id.edit_image3);
        image4=(ImageView)findViewById(R.id.edit_image4);

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

                Bitmap myBitmap = BitmapFactory.decodeFile(Path);
                if(ViewId==image1.getId()){
                    image1.setImageBitmap(myBitmap);
                    UploadImage();
                }else if(ViewId==image2.getId()){
                    image2.setImageBitmap(myBitmap);
                    UploadImage();
                }else if(ViewId==image3.getId()){
                    image3.setImageBitmap(myBitmap);
                    UploadImage();
                }else if(ViewId==image4.getId()){
                    image4.setImageBitmap(myBitmap);
                    UploadImage();
                }


            }else if(requestCode == 100){
                currImageURI = data.getData();
                Path=getRealPathFromURI(currImageURI);
                Bitmap photo = (Bitmap) data.getExtras().get("data");
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
        }
    }


    public void UploadImage()
    {
        HTTPPostUploadImage uploadImage=new HTTPPostUploadImage(this);
        uploadImage.SetImage(fc.GetProductId(), 2);
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


    public void select_map(View view) {


    }
}
