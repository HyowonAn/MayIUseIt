package net.uprin.mayiuseit.activity;

/**
 * Created by uPrin on 2017. 10. 29..
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startLoading();
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
}