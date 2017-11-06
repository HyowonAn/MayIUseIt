package net.uprin.mayiuseit.login;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.uprin.mayiuseit.MainActivity;
import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.ScrollingActivity;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailLoginActivity extends AppCompatActivity {

    public static final String UPRINKEY = "IMT5G4U9FP1KDOM5S7EPWU08FFNFZMME9JF4AQ99P1";
    String sId, sPw;

    AppCompatEditText et_id, et_pw;
    RelativeLayout relativeLayout;
    TextInputLayout emailLayout,passLayout;
    Toolbar toolbar;
    AppCompatButton loginBtn;
    TextView findmypassword_button;

    final Context context = this; //이거 onPostExecute부분에서 필요한 것이었음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_login);

        et_id = (AppCompatEditText) findViewById(R.id.email_TextField);
        et_pw = (AppCompatEditText) findViewById(R.id.password_TextField);
        emailLayout = (TextInputLayout) findViewById(R.id.email_TextInputLayout);
        passLayout = (TextInputLayout) findViewById(R.id.password_TextInputLayout);
        toolbar = (Toolbar) findViewById(R.id.email_login_toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_email_login);
        loginBtn = (AppCompatButton)findViewById(R.id.email_Login_Button);
        findmypassword_button = (TextView) findViewById(R.id.findmypassword_button) ;

        findmypassword_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ScrollingActivity.class));
                finish();
            }
        });


        setSupportActionBar(toolbar);
        // ↓툴바에 홈버튼을 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //빈공간에 클릭 시 키보드가 사라지게 할 수 있음
        relativeLayout.setOnClickListener(null);
        emailLayout.setCounterEnabled(true);
        emailLayout.setCounterMaxLength(20);
        //비밀번호 크기 제한
        passLayout.setCounterEnabled(true);
        passLayout.setCounterMaxLength(15);

        et_id.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (et_id.getText().toString().isEmpty()){
                    emailLayout.setErrorEnabled(true);
                    emailLayout.setError("이메일 주소를 입력 해 주세요");
                }else{
                    emailLayout.setErrorEnabled(false);
                }
            }
        });

        et_id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //작성중에 Error 지우기 위해
                if (et_id.getText().toString().isEmpty()){
                    emailLayout.setErrorEnabled(true);
                    emailLayout.setError("이메일 주소를 입력 해 주세요");
                }else{
                    emailLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sId = et_id.getText().toString();
                sPw = et_pw.getText().toString();

                //Async가 아닌 Retrofit을 이용해서 간단하게 연결하기 17.11.1 안효원
                ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
                Call<LoginResponse> call = apiService.postEmailLogin(new JoinRequest(sId,sPw,UPRINKEY));
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        LoginResponse loginResponse = response.body();
                        if (loginResponse.getState()==1){
                            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                            alertBuilder
                                    .setTitle("환영합니다")
                                    .setMessage(loginResponse.getDescription())
                                    .setCancelable(true)
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                                finish();
                                            }
                                        });
                            AlertDialog dialog = alertBuilder.create();
                            dialog.show();
                        }
                        else
                        {
                            Snackbar.make(getWindow().getDecorView().getRootView(), loginResponse.getDescription(), Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            }).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                    }
                });

            }
        });
    }
}