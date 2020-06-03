package com.example.mydrysister.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

//网络相关工具类

public class NetworkUtils {
    //获取网络信息
    private static NetworkInfo getActiveNetworkInfo(Context context){
        ConnectivityManager cm = (ConnectivityManager)context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo();
    }

    public static boolean isAvailable(Context context){
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isAvailable();
    }
}
