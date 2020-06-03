package com.example.mydrysister;

import android.app.Application;

public class DrySisterApp extends Application {

    private static DrySisterApp context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        DryInit.initTimber();
        DryInit.initOkHttp(this);
    }

    public static DrySisterApp getContext(){
        return context;
    }

}
