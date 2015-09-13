package com.ariana.shahre_ma.Bazarche;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyBusiness.Edit_business;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServicePost.HTTPPostUploadImage;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class Select_Image extends ActionBarActivity implements ImageView.OnClickListener {

    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    Uri currImageURI;
    String picturePath;
    Integer ViewId=0;
    String Path="";

    FieldClass fc=new FieldClass();
    Edit_business edit_business=new Edit_business();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__image);
        image1=(ImageView)findViewById(R.id.ap_image1);
        image2=(ImageView)findViewById(R.id.ap_image2);
        image3=(ImageView)findViewById(R.id.ap_image3);
        image4=(ImageView)findViewById(R.id.ap_image4);

        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image4.setOnClickListener(this);


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
                    Toast.makeText(getApplicationContext(),"در قسمت ویرایش کسب و کار میتوانید تصاویر را حذف کنید",Toast.LENGTH_LONG).show();
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
                BufferedOutputStream out = null;
                //  /*TextView tv_path = (TextView) findViewById(R.id.textView);
                Path=getRealPathFromURI(currImageURI);
                Bitmap myBitmap = BitmapFactory.decodeFile(Path);
                try {
                    File dump = new File(Path);
                    out = new BufferedOutputStream(new FileOutputStream(dump));
                    myBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                if(ViewId==image1.getId())
                {
                    image1.setImageBitmap(myBitmap);
                    UploadImage();
                }
                else if(ViewId==image2.getId())
                {
                    image2.setImageBitmap(myBitmap);
                    UploadImage();
                }
                else if(ViewId==image3.getId())
                {
                    image3.setImageBitmap(myBitmap);
                    UploadImage();
                }
                else if(ViewId==image4.getId())
                {
                    image4.setImageBitmap(myBitmap);
                    UploadImage();
                }


            }else if(requestCode == 100){
                BufferedOutputStream out = null;
                currImageURI = data.getData();
                Path=getRealPathFromURI(currImageURI);
                Bitmap photo = (Bitmap) data.getExtras().get("data");


                try {
                    File dump = new File(Path);
                    out = new BufferedOutputStream(new FileOutputStream(dump));
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, out);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    if (out != null) try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                if(ViewId==image1.getId())
                {
                    image1.setImageBitmap(photo);
                    UploadImage();
                }
                else if(ViewId==image2.getId())
                {
                    image2.setImageBitmap(photo);
                    UploadImage();
                }
                else if(ViewId==image3.getId())
                {
                    image3.setImageBitmap(photo);
                    UploadImage();
                }
                else if(ViewId==image4.getId())
                {
                    image4.setImageBitmap(photo);
                    UploadImage();
                }
            }
        }
    }

    public void UploadImage()
    {
        HTTPPostUploadImage uploadImage=new HTTPPostUploadImage(this);
        uploadImage.SetImage(fc.GetBusiness_Id(),fc.GetType());
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


    public void submit(View view) {

        AlertDialog alertDialog = new AlertDialog.Builder(Select_Image.this).create();
        alertDialog.setTitle("تبریک");
        alertDialog.setMessage("کسب وکار شما ثبت شد،از این پس میتوانید برای کسب وکار خود امتیاز جمع آوری کرده و بیشتر در معرض دید باشید.");
        alertDialog.setButton("باشه", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        });
        alertDialog.show();
    }
}
