package net.uprin.mayiuseit.rest;

import net.uprin.mayiuseit.login.AccessToken;
import net.uprin.mayiuseit.login.JoinRequest;
import net.uprin.mayiuseit.login.JoinResponse;
import net.uprin.mayiuseit.login.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
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
    Call<DocumentListResponse> getDocumentList(@Query("pageNum") int pageNum, @Query("category") int category,@Query("rankBy") String rankBy);
    @GET("document.php")
    Call<DocumentResponse> getDocument(@Query("document_slr") int document_slr);
    @GET("notice.php")
    Call<NoticeListResponse> getNotice(@Query("pageNum") int pageNum);

    @POST("tokenget.php")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    @POST("tokenget.php")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password);

    @GET("search.php")
    Call<SearchListResponse> getSearchList(@Query("pageNum") int pageNum, @Query("keyword") String keyword, @Query("category") int category, @Query("rankBy") String rankBy);
}
