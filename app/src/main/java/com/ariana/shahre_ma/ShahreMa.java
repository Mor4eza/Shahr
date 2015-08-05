package com.ariana.shahre_ma;

import android.app.Application;

import jonathanfinerty.once.Once;

/**
 * Created by ariana2 on 8/5/2015.
 */
public class ShahreMa extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Once.initialise(this);

    }
}
