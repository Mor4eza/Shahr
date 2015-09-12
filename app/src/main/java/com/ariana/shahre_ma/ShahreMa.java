package com.ariana.shahre_ma;

import android.app.Application;

import com.drivemode.android.typeface.TypefaceHelper;

import jonathanfinerty.once.Once;

/**
 * Created by ariana2 on 8/5/2015.
 */
public class ShahreMa extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Once.initialise(this);
        TypefaceHelper.initialize(this);
    }

    @Override
    public void onTerminate() {
        TypefaceHelper.destroy();
        super.onTerminate();
    }
}
