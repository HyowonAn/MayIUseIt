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
import android.widget.Toast;

import net.uprin.mayiuseit.MainActivity;
import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;

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

               loginDB lDB = new loginDB();
                lDB.execute();

            }
        });





    }

    public class loginDB extends AsyncTask<Void, Integer, Void> {

        @Override
        protected Void doInBackground(Void... unused) {
        /* 인풋 파라메터값 생성 */
            String param = "uprinkey="+UPRINKEY+"&u_id="+sId+"&u_pw="+sPw+"";
            Log.e("POST",param);
            try {
            /* 서버연결 */
                URL url = new URL(
                        "http://dev.uprin.net/mayiuseit/login.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.connect();

            /* 안드로이드 -> 서버 파라메터값 전달 */
                OutputStream outs = conn.getOutputStream();
                outs.write(param.getBytes("UTF-8"));
                outs.flush();
                outs.close();

            /* 서버 -> 안드로이드 파라메터값 전달 */
                InputStream is      = null;
                BufferedReader in   = null;
                String data         = "";

                is  = conn.getInputStream();
                in  = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff   = new StringBuffer();
                while ( ( line = in.readLine() ) != null )
                {
                    buff.append(line + "\n");
                }
                data    = buff.toString().trim();

            /* 서버에서 응답 */
                Log.e("RECV DATA",data);

                if(data.equals("0"))
                {
                    Log.e("RESULT","성공적으로 처리되었습니다!");
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();

                }
                else
                {
                    Log.e("RESULT","에러 발생! ERRCODE = " + data);
                }

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }




    }
}