package com.ariana.shahre_ma.MyCity;

/**
 * Created by ariana2 on 7/2/2015.
 */

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import com.ariana.shahre_ma.R;
import com.panwrona.downloadprogressbar.library.DownloadProgressBar;

public class Download_dialog extends Dialog {


    protected Download_dialog(Context context) {
        super(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.download_dialog);
        DownloadProgressBar downloadProgressBar = (DownloadProgressBar)findViewById(R.id.dpv3);
        downloadProgressBar.playToError();
        downloadProgressBar.setOnProgressUpdateListener(new DownloadProgressBar.OnProgressUpdateListener() {
            @Override
            public void onProgressUpdate(float v) {

            }

            @Override
            public void onAnimationStarted() {

            }

            @Override
            public void onAnimationEnded() {

            }

            @Override
            public void onAnimationSuccess() {
                    dismiss();
            }

            @Override
            public void onAnimationError() {
                    dismiss();
            }

            @Override
            public void onManualProgressStarted() {

            }

            @Override
            public void onManualProgressEnded() {

            }
        });

    }
}
