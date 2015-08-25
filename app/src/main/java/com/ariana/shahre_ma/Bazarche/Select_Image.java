package com.ariana.shahre_ma.Bazarche;

import android.content.Intent;
import android.database.Cursor;
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
import android.widget.ImageView;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.MyBusiness.Edit_business;
import com.ariana.shahre_ma.R;
import com.ariana.shahre_ma.WebServicePost.HTTPPostUploadImage;

public class Select_Image extends ActionBarActivity implements ImageView.OnClickListener {

    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    Uri currImageURI;
    String picturePath;
    Integer ViewId=0;
    String Path="";
    Bitmap bp;
    private int maxWidth = 250;
    private int maxHeight = 250;

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
                ViewId = v.getId();
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
                //currImageURI = data.getData();

                //  *//*TextView tv_path = (TextView) findViewById(R.id.textView);
                //Path=getRealPathFromURI(currImageURI);
                //Bitmap myBitmap = BitmapFactory.decodeFile(Path);
                Bitmap myBitmap=selectPhotoControl(data);

                selectPhotoControl(data);

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
                currImageURI = data.getData();

                Path=getRealPathFromURI(currImageURI);
                Bitmap photo = (Bitmap) data.getExtras().get("data");


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


    private Bitmap selectPhotoControl(Intent data) {
            //check photo is selected

        if (data == null)
            return null;


        Uri photoUri = data.getData();
        if (photoUri != null) {
            //decode the Uri
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(photoUri,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

             bp = resize(filePath);
            //if (bp != null)
                //postImage(bp, filePath);
        }

        return bp;
    }


    private Bitmap resize(String path){
        // create the options
        BitmapFactory.Options opts = new BitmapFactory.Options();

        //just decode the file
        opts.inJustDecodeBounds = true;
        Bitmap bp = BitmapFactory.decodeFile(path, opts);

        //get the original size
        int orignalHeight = opts.outHeight;
        int orignalWidth = opts.outWidth;
        //initialization of the scale
        int resizeScale = 1;
        //get the good scale
        if ( orignalWidth > maxWidth || orignalHeight > maxHeight ) {
            final int heightRatio = Math.round((float) orignalHeight / (float) maxHeight);
            final int widthRatio = Math.round((float) orignalWidth / (float) maxWidth);
            resizeScale = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        //put the scale instruction (1 -> scale to (1/1); 8-> scale to 1/8)
        opts.inSampleSize = resizeScale;
        opts.inJustDecodeBounds = false;
        //get the futur size of the bitmap
        int bmSize = (orignalWidth / resizeScale) * (orignalHeight / resizeScale) * 4;
            //check if it's possible to store into the vm java the picture
        if ( Runtime.getRuntime().freeMemory() > bmSize ) {
            //decode the file
            bp = BitmapFactory.decodeFile(path, opts);
        } else
            return null;
        return bp;
    }


    public void UploadImage() {
        HTTPPostUploadImage uploadImage=new HTTPPostUploadImage(this);
        uploadImage.SetImage(fc.GetProductId(),fc.GetType());
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
        finish();
    }
}
