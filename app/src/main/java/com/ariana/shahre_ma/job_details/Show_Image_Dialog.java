package com.ariana.shahre_ma.job_details;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.ariana.shahre_ma.R;
import com.squareup.picasso.Picasso;

/**
 * Created by ariana2 on 9/6/2015...
 */
public class Show_Image_Dialog extends Dialog {

    ImageView imgPreview;
    ImageView close;
    Button download;
    String Url;
    public Show_Image_Dialog(Context context,String url) {
        super(context);
        Url=url;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        setContentView(R.layout.show_image_dialog);
        super.onCreate(savedInstanceState);
        imgPreview=(ImageView)findViewById(R.id.img_preview);
        close=(ImageView)findViewById(R.id.img_close);
        download=(Button)findViewById(R.id.btn_download);
        Log.i("url",Url);
        Picasso.with(getOwnerActivity()).load(Url).placeholder(R.drawable.img_not_found).into(imgPreview);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }
}
