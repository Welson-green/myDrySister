package com.example.mydrysister;

import android.app.Application;

import com.example.mydrysister.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;


//完成一些基本构造

public class DryInit {
    private static Long  HTTP_CONNECT_TIMEOUT = 30L;
    private static Long  HTTP_WRITE_TIMEOUT = 30L;
    private static Long  HTTP_READ_TIMEOUT = 30L;

    public static OkHttpClient mOkHttpClient;

    //初始化OkHttpClient
    static void initOkHttp(Application app){
        HttpLoggingInterceptor logging =
                new HttpLoggingInterceptor(message -> Timber.tag("OkHttps").d(message));
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(HTTP_CONNECT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(HTTP_WRITE_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(HTTP_READ_TIMEOUT,TimeUnit.SECONDS)
                .addInterceptor(logging);
        mOkHttpClient = builder.build();
    }

    //初始化Timber
    static void initTimber(){
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }
    }

}
