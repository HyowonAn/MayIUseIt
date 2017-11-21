package net.uprin.mayiuseit.rest;

import net.uprin.mayiuseit.login.JoinRequest;
import net.uprin.mayiuseit.login.JoinResponse;
import net.uprin.mayiuseit.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
    @GET("list.php")
    Call<DocumentListResponse> getDocumentList(@Query("pageNum") int pageNum, @Query("category") int category);
    @GET("document.php")
    Call<DocumentResponse> getDocument(@Query("document_slr") int document_slr);
}
