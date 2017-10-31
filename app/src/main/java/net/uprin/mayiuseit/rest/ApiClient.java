package net.uprin.mayiuseit.rest;

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
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }
}
