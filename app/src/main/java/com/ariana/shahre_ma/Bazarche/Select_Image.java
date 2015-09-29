package com.ariana.shahre_ma.Bazarche;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Select_Image extends ActionBarActivity implements ImageView.OnClickListener {

    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    Uri currImageURI;
    String picturePath;
    Integer ViewId=0;
    String Path="";
    private Uri mImageCaptureUri;
    private File outPutFile = null;
    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;
    FieldClass fc=new FieldClass();
    Edit_business edit_business=new Edit_business();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select__image);
        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
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
                    Toast.makeText(getApplicationContext(), "در قسمت ویرایش کسب و کار میتوانید تصاویر را حذف کنید", Toast.LENGTH_LONG).show();
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
        localIntent1.putExtra("outputX", 512);
        localIntent1.putExtra("outputY", 512);
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
