package net.uprin.mayiuseit.rest;

import com.facebook.stetho.okhttp3.StethoInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by uPrin on 2017. 11. 1..
 */

public class ApiClient {

    public static final String BASE_URL ="https://dev.uprin.net/mayiuseit/";
    private static Retrofit retrofit =null;



    public static Retrofit getClient(){
        if (retrofit==null){
            //StethoInsperceptor를 통해서 크롬으로 네트워크 감지를 하기 위해 생성 17.11.2 안효원
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

        }

        return retrofit;
    }
}
