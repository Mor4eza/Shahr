package com.ariana.shahre_ma;

import android.app.Application;

import com.tsengvn.typekit.Typekit;

import jonathanfinerty.once.Once;

/**
 * Created by ariana2 on 8/5/2015.
 */
public class ShahreMa extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Once.initialise(this);
        Typekit.getInstance()
                .addNormal(Typekit.createFromAsset(this, "font/Yekan.ttf"))
                .addBold(Typekit.createFromAsset(this, "font/Yekan.ttf"))
                .addItalic(Typekit.createFromAsset(this, "font/Yekan.ttf"))
                .addBoldItalic(Typekit.createFromAsset(this, "font/Yekan.ttf"))
                .addCustom1(Typekit.createFromAsset(this, "font/Yekan.ttf"))
                .addCustom2(Typekit.createFromAsset(this, "font/Yekan.ttf"));
    }
}
