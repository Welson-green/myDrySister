package com.example.mydrysister.utils;

import com.example.mydrysister.DrySisterApp;


//获取文件资源工具类

public class ResUtils {

    public static String getString(int strId){
        return DrySisterApp.getContext().getResources().getString(strId);

    }
}
