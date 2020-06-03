package com.example.mydrysister.net;

import com.example.mydrysister.DryInit;
import com.example.mydrysister.data.result.GankResult;

import io.reactivex.Flowable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

public class APIService {

    private static String BASE_URL = "http://www.coderpig.com/";   //未启用

    public APIs apis;

    private static APIService instance;

    public static APIService getInstance(){
        if(instance == null){
            instance = new APIService();
        }
        return instance;
    }

    private APIService(){
        Retrofit storeRestAPI = new Retrofit.Builder().baseUrl(BASE_URL)
                .client(DryInit.mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apis = storeRestAPI.create(APIs.class);
    }


    public interface APIs{

        @GET("http://gank.io/api/data/福利/{count}/{page}")
        Flowable<GankResult> fetchGankMZ(
                @Path("count") int count,
                @Path("page") int page
        );
    }

}
