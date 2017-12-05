package net.uprin.mayiuseit.activity;

/**
 * Created by uPrin on 2017. 10. 29..
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.auth0.android.jwt.JWT;

import net.uprin.mayiuseit.model.AccessToken;
import net.uprin.mayiuseit.model.TokenData;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiError;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.util.TokenManager;
import net.uprin.mayiuseit.util.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {
    TokenManager tokenManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        loginChecker();

        //startLoading();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        //finish();
    }

    private void startLoading() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    private void loginChecker(){

        if(tokenManager.getToken().getAccessToken() == null){ //비어있는 경우 로그인 실패
            startActivity(new Intent(getBaseContext(),LoginActivity.class));

        } else{
            JWT jwt = new JWT(tokenManager.getToken().getAccessToken());
                Toast.makeText(getApplicationContext(), "로그인 확인중입니다", Toast.LENGTH_SHORT).show();
                ApiInterface apiService = ApiClient.createServiceWithAuth(ApiInterface.class,tokenManager);
                Call<ApiError> call = apiService.validToken(tokenManager.getToken().getAccessToken());
                call.enqueue(new Callback<ApiError>(){

                    @Override
                    public void onResponse(Call<ApiError> call, Response<ApiError> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), response.body().message(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getBaseContext(),MainActivity.class));
                            Log.e("TOKEN" , "Token Saved success");
                        } else {
                            startActivity(new Intent(getBaseContext(),LoginActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<ApiError> call, Throwable t) {
                        startActivity(new Intent(getBaseContext(),LoginActivity.class));
                    }
                });

        }
    }
}