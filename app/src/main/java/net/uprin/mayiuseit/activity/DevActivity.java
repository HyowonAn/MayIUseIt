package net.uprin.mayiuseit.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.util.TokenManager;

public class DevActivity extends AppCompatActivity {
    private static final String TAG = DevActivity.class.getSimpleName();

    TokenManager tokenManager;
    AppCompatButton deleteAccessTokenBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dev);

        deleteAccessTokenBtn = (AppCompatButton) findViewById(R.id.delete_access_token_btn);

        deleteAccessTokenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenManager =         tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
                if(tokenManager.getToken() == null){
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }

                tokenManager.deleteAccessToken();
                Log.e(TAG,"DETETE ACCESS TOKEN SUCCESSFUL");
                Toast.makeText(getApplicationContext(),"refreshToken :" + tokenManager.getToken().getRefreshToken().toString(),Toast.LENGTH_SHORT).show();
            }
        });
    }
}
