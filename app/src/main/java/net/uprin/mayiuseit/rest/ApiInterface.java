package net.uprin.mayiuseit.rest;

import net.uprin.mayiuseit.login.JoinRequest;
import net.uprin.mayiuseit.login.JoinResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by uPrin on 2017. 11. 1..
 */

public interface ApiInterface {
    @POST("join.php")
    Call<JoinResponse> postEmailLogin (@Body JoinRequest joinRequest);

    @GET("join.php")
    Call<JoinResponse> getEmailLogin (@Query("email") String email, @Query("password") String password , @Query("uprinkey") String apiKey);
}
