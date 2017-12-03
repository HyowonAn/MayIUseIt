package net.uprin.mayiuseit.rest;

import net.uprin.mayiuseit.model.AccessToken;
import net.uprin.mayiuseit.model.DocumentListResponse;
import net.uprin.mayiuseit.model.DocumentResponse;
import net.uprin.mayiuseit.model.JoinRequest;
import net.uprin.mayiuseit.model.JoinResponse;
import net.uprin.mayiuseit.model.LoginResponse;
import net.uprin.mayiuseit.model.NoticeListResponse;
import net.uprin.mayiuseit.model.SearchListResponse;

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
    Call<DocumentListResponse> getDocumentList(@Query("pageNum") int pageNum, @Query("category") int category, @Query("rankBy") String rankBy);
    @GET("document.php")
    Call<DocumentResponse> getDocument(@Query("document_slr") int document_slr);
    @GET("notice.php")
    Call<NoticeListResponse> getNotice(@Query("pageNum") int pageNum);

    @POST("refreshToken.php")
    @FormUrlEncoded
    Call<AccessToken> refresh(@Field("refresh_token") String refreshToken);

    @POST("token.php")
    @FormUrlEncoded
    Call<AccessToken> login(@Field("username") String username, @Field("password") String password);

    @GET("search.php")
    Call<SearchListResponse> getSearchList(@Query("pageNum") int pageNum, @Query("keyword") String keyword, @Query("category") int category, @Query("rankBy") String rankBy);
}
