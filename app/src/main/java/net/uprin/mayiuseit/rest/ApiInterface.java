package net.uprin.mayiuseit.rest;

import net.uprin.mayiuseit.login.JoinRequest;
import net.uprin.mayiuseit.login.JoinResponse;
import net.uprin.mayiuseit.login.LoginResponse;

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
    Call<JoinResponse> postEmailJoin (@Body JoinRequest joinRequest);
    @POST("login.php")
    Call<LoginResponse> postEmailLogin (@Body JoinRequest joinRequest);
}
