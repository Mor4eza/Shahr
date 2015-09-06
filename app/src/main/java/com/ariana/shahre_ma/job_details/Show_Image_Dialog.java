package com.ariana.shahre_ma.job_details;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.ariana.shahre_ma.Fields.FieldClass;
import com.ariana.shahre_ma.NetWorkInternet.NetState;
import com.ariana.shahre_ma.R;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by ariana2 on 9/6/2015...
 */
public class Show_Image_Dialog extends Dialog {

    ImageView imgPreview;
    ImageView close;
    Button download;
    String Url;
    NetState ns=new NetState(getContext());
   FieldClass fc=new FieldClass();
    public Show_Image_Dialog(Context context,String url) {
        super(context);
        Url=url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.show_image_dialog);
        super.onCreate(savedInstanceState);
        imgPreview=(ImageView)findViewById(R.id.img_preview);
        close=(ImageView)findViewById(R.id.img_close);
        download=(Button)findViewById(R.id.btn_download);
        Log.i("url", Url);

        if (!ns.checkInternetConnection()){
            download.setVisibility(View.GONE);
        }else{
            download.setVisibility(View.VISIBLE);
        }

        Picasso.with(getOwnerActivity()).load(Url).placeholder(R.drawable.img_not_found).into(imgPreview);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BitmapDrawable btmpDr = (BitmapDrawable) imgPreview.getDrawable();
                Bitmap bmp = btmpDr.getBitmap();

/*              File sdCardDirectory = Environment.getExternalStorageDirectory();*/
                try {
                    File sdCardDirectory = new File(Environment.getExternalStorageDirectory() + File.separator + "Shahre_Ma" + File.separator + "Images");
                    sdCardDirectory.mkdirs();

                    String imageNameForSDCard = fc.GetMarket_Business() + "-" + System.currentTimeMillis() + ".jpg";

                    File image = new File(sdCardDirectory, imageNameForSDCard);
                    FileOutputStream outStream;

                    outStream = new FileOutputStream(image);
                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    /* 100 to keep full quality of the image */
                    outStream.flush();
                    outStream.close();
                    Toast.makeText(getContext(), "در پوشه Shahre_Ma/Images ذخیره شد!", Toast.LENGTH_LONG).show();
                    download.setVisibility(View.GONE);
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "ذخیره نشد!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
