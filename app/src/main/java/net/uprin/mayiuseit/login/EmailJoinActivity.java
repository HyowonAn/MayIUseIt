package net.uprin.mayiuseit.login;

import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import net.uprin.mayiuseit.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class EmailJoinActivity extends AppCompatActivity {

    public static final String UPRINKEY = "$2Y$10$IMT5G4U9FP1KDOM5S7EPWU/08FFNFZMME/9JF4AQ99P";
    String sId, sPw, sPw_chk;

    AppCompatEditText et_id, et_pw, et_pw_chk;
    RelativeLayout relativeLayout;
    TextInputLayout emailLayout,passLayout,passchkLayout;
    Toolbar toolbar;
    AppCompatButton joinBtn;

    final Context context = this; //이거 onPostExecute부분에서 필요한 것이었음

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_email_join);

        et_id = (AppCompatEditText) findViewById(R.id.email_TextField);
        et_pw = (AppCompatEditText) findViewById(R.id.password_TextField);
        et_pw_chk = (AppCompatEditText) findViewById(R.id.password_check_TextField);
        emailLayout = (TextInputLayout) findViewById(R.id.email_TextInputLayout);
        passLayout = (TextInputLayout) findViewById(R.id.password_TextInputLayout);
        passchkLayout = (TextInputLayout) findViewById(R.id.password_check_TextInputLayout);
        toolbar = (Toolbar) findViewById(R.id.email_join_toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_email_join);
        joinBtn = (AppCompatButton)findViewById(R.id.email_Join_Button);

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
        passchkLayout.setCounterEnabled(true);
        passchkLayout.setCounterMaxLength(15);

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

        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sId = et_id.getText().toString();
                sPw = et_pw.getText().toString();
                sPw_chk = et_pw_chk.getText().toString();

                if(sPw.equals(sPw_chk))
                {
                    registDB rdb = new registDB();
                    rdb.execute();
                }
                else
                {
            /* 패스워드 불일치*/
                    Snackbar.make(getWindow().getDecorView().getRootView(), "패스워드가 불일치합니다", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            
                        }
                    }).show(); // show 까먹지 마세요!
                    //Toast.makeText(getApplicationContext(),"패스워드가 불일치합니다",Toast.LENGTH_LONG).show();

                }
            }
        });





    }

    public class registDB extends AsyncTask<Void, Integer, Void> {
        String data         = "";

        @Override
        protected Void doInBackground(Void... voids) {
            String param = "uprinkey="+UPRINKEY+"&u_id="+sId+"&u_pw="+sPw+"";
            try{
                URL url = new URL(
                        "http://dev.uprin.net/mayiuseit/join.php");
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


                is  = conn.getInputStream();
                in  = new BufferedReader(new InputStreamReader(is), 8 * 1024);
                String line = null;
                StringBuffer buff   = new StringBuffer();
                while ( ( line = in.readLine() ) != null )
                {
                    buff.append(line + "\n");
                }

                data    = buff.toString().trim();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch(IOException e){
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);//이거 넣어줘야 builder을 사용가능
            Log.e("RECV DATA",data);

            if(data.equals("0"))
            {
                Log.e("RESULT","성공적으로 처리되었습니다!");
                alertBuilder
                        .setTitle("알림")
                        .setMessage("성공적으로 등록되었습니다!")
                        .setCancelable(true)
                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
                            }
                        });
                AlertDialog dialog = alertBuilder.create();
                dialog.show();
            } else if (data.equals("1062")){
                Log.e("RESULT","에러 발생! ERRCODE = " + data);
                Toast.makeText(getApplicationContext(),"동일한 ID가 존재합니다",Toast.LENGTH_LONG).show();
            }else
            {
                Log.e("RESULT","에러 발생! ERRCODE = " + data);
                Toast.makeText(getApplicationContext(),data,Toast.LENGTH_LONG).show();
            }
        }

    }

}


