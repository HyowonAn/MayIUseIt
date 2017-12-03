package net.uprin.mayiuseit.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.model.JoinRequest;
import net.uprin.mayiuseit.model.JoinResponse;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EmailJoinActivity extends AppCompatActivity {

    public static final String UPRINKEY = "IMT5G4U9FP1KDOM5S7EPWU08FFNFZMME9JF4AQ99P1";
    String sId, sPw, sPw_chk;

    AppCompatEditText et_id, et_pw, et_pw_chk;
    RelativeLayout relativeLayout;
    TextInputLayout emailLayout,passLayout,passchkLayout;
    Toolbar toolbar;
    AppCompatButton joinBtn;
    InputMethodManager imm;
    TextView progresstxt;

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
        toolbar = (Toolbar) findViewById(R.id.email_login_toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_email_join);
        joinBtn = (AppCompatButton)findViewById(R.id.email_Join_Button);
        progresstxt = (TextView) findViewById(R.id.progress_join_txt);
        progresstxt.setVisibility(View.INVISIBLE);

        setSupportActionBar(toolbar);
        // ↓툴바에 홈버튼을 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //빈공간에 클릭 시 키보드가 사라지게 할 수 있음
        relativeLayout.setOnClickListener(null);
        //키보드 강제로 내리기
        imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        emailLayout.setCounterEnabled(true);
        emailLayout.setCounterMaxLength(20);
        //비밀번호 크기 제한
        passLayout.setCounterEnabled(true);
        passLayout.setCounterMaxLength(15);
        passchkLayout.setCounterEnabled(true);
        passchkLayout.setCounterMaxLength(15);
        et_pw_chk.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {

            }
        });
        et_pw_chk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
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
                hideKeyboard();
                joinBtn.setEnabled(false);
                progresstxt.setVisibility(View.VISIBLE);
                sId = et_id.getText().toString();
                sPw = et_pw.getText().toString();
                sPw_chk = et_pw_chk.getText().toString();

                if(sPw.equals(sPw_chk))
                {

                    //Async가 아닌 Retrofit을 이용해서 간단하게 연결하기 17.11.1 안효원
                    ApiInterface apiService = ApiClient.createService(ApiInterface.class);
                    Call<JoinResponse> call = apiService.postEmailJoin(new JoinRequest(sId,sPw,UPRINKEY));
                    call.enqueue(new Callback<JoinResponse>() {
                        @Override
                        public void onResponse(Call<JoinResponse> call, Response<JoinResponse> response) {
                            JoinResponse joinResponse = response.body();
                            if (joinResponse.getState()==1){
                                AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
                                alertBuilder
                                        .setTitle("환영합니다")
                                        .setMessage(joinResponse.getDescription())
                                        .setCancelable(true)
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                finish();
                                            }
                                        });
                                AlertDialog dialog = alertBuilder.create();
                                dialog.show();
                            }
                            else
                            {
                                Snackbar.make(getWindow().getDecorView().getRootView(), joinResponse.getDescription(), Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                               // joinBtn.setClickable(true);
                                joinBtn.setEnabled(true);
                                progresstxt.setVisibility(View.INVISIBLE);
                            }
                        }

                        @Override
                        public void onFailure(Call<JoinResponse> call, Throwable t) {
                        }
                    });
                }
                else
                {
            /* 패스워드 불일치*/
                    Snackbar.make(getWindow().getDecorView().getRootView(), "패스워드가 불일치합니다", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                           // joinBtn.setClickable(true);
                        }
                    }).show();
                    joinBtn.setEnabled(true);
                    progresstxt.setVisibility(View.INVISIBLE);


                }

            }

        });







    }

    private void hideKeyboard()
    {
        imm.hideSoftInputFromWindow(et_id.getWindowToken(), 0);
        imm.hideSoftInputFromWindow(et_pw.getWindowToken(), 0);
    }

}


