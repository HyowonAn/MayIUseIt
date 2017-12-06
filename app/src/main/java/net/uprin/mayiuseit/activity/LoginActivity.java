package net.uprin.mayiuseit.activity;

/**
 * Created by uPrin on 2017. 10. 30..
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import net.uprin.mayiuseit.activity.MainActivity;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.util.FacebookManager;
import net.uprin.mayiuseit.util.PrefManager;
import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.activity.WelcomeActivity;
import net.uprin.mayiuseit.activity.EmailJoinActivity;
import net.uprin.mayiuseit.activity.EmailLoginActivity;
import net.uprin.mayiuseit.util.TokenManager;

public class LoginActivity extends AppCompatActivity {
    private SessionCallback callback;


    FacebookManager facebookManager;
    TokenManager tokenManager;


    LoginButton kakaologinButton;
    AppCompatButton fakeKakaoButton, facebookButton, naverButton ;


    private VideoView mVideoView;

    private PrefManager prefManager;
    TextView welcome_reset_txtbtn, login_txtbtn, email_join_txtbtn;

    ApiInterface service;
    boolean fb_clicked,kakao_clicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fb_clicked=false;
        kakao_clicked=false;

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_login);
        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        changeStatusBarColor();
        //다시 시작화면 보이기
        welcome_reset_txtbtn = (TextView) findViewById(R.id.welcome_reset_txtbtn);

        mVideoView = (VideoView) findViewById(R.id.mVideoView);
        Uri uri = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.media1);
        mVideoView.setVideoURI(uri);

        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setLooping(true);
                mediaPlayer.start();
            }
        });



        prefManager = new PrefManager(this);
        welcome_reset_txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManager.setFirstTimeLaunch(true);
                startActivity(new Intent(getApplicationContext(), WelcomeActivity.class));
                finish();
            }
        });



        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        // 카카오톡 로그인 버튼
        fakeKakaoButton = (AppCompatButton)findViewById(R.id.fake_com_kakao_login);
        facebookButton = (AppCompatButton)findViewById(R.id.com_facebook_login);
        naverButton = (AppCompatButton)findViewById(R.id.com_naver_login);
        email_join_txtbtn = (TextView)findViewById(R.id.email_join);
        login_txtbtn = (TextView)findViewById(R.id.email_login) ;
        service = ApiClient.createService(ApiInterface.class);

        facebookManager = new FacebookManager(service, tokenManager);

        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fb_clicked=true;
                facebookManager.login(LoginActivity.this, new FacebookManager.FacebookLoginListener() {
                    @Override
                    public void onSuccess() {
                        facebookManager.clearSession();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(String message) {
                        //showForm();
                        Toast.makeText(LoginActivity.this, message, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        fakeKakaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kakao_clicked=true;
                kakaologinButton.performClick();
            }
        });
        kakaologinButton = (LoginButton)findViewById(R.id.com_kakao_login);
        kakaologinButton.setVisibility(View.INVISIBLE);
        kakaologinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnected()){
                    Snackbar.make(getWindow().getDecorView().getRootView(), "인터넷 연결을 확인 해 주세요", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                        }
                    }).show();
                }
            }
        });

        if(Session.getCurrentSession().isOpened()){
            requestMe();
        }else{
        }


        //naver
        naverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"아직 준비중 입니다",Toast.LENGTH_SHORT).show();
            }
        });

        //email
        email_join_txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EmailJoinActivity.class));
            }
        });

        login_txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), EmailLoginActivity.class));
            }
        });

    }


    //인터넷 연결상태 확인
    public boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }

        return false;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fb_clicked == true)
        {
            facebookManager.onActivityResult(requestCode, resultCode, data);
            fb_clicked=false;
            return;
        }
        else if(kakao_clicked == true)
        {
            Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data);
            kakao_clicked=false;
            return;
        }

    }


    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            //access token을 성공적으로 발급 받아 valid access token을 가지고 있는 상태. 일반적으로 로그인 후의 다음 activity로 이동한다.
            if(Session.getCurrentSession().isOpened()){ // 한 번더 세션을 체크해주었습니다.
                Toast.makeText(getApplicationContext(),"AccessToken : "+Session.getCurrentSession().getAccessToken(),Toast.LENGTH_LONG).show();
                requestMe();
            }
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if(exception != null) {
                Logger.e(exception);
            }
        }
    }


    private void requestMe() {

        UserManagement.requestMe(new MeResponseCallback() {
            @Override
            public void onFailure(ErrorResult errorResult) {
                Log.e("onFailure", errorResult + "");
            }

            @Override
            public void onSessionClosed(ErrorResult errorResult) {
                Log.e("onSessionClosed",errorResult + "");
            }

            @Override
            public void onSuccess(UserProfile userProfile) {
                Log.e("onSuccess",userProfile.toString());
                Intent outIntent = new Intent(getApplicationContext(), MainActivity.class);
                outIntent.putExtra("user_nickname",userProfile.getNickname());
                outIntent.putExtra("user_email",userProfile.getEmail());
                outIntent.putExtra("user_img",userProfile.getThumbnailImagePath());
                startActivity(outIntent);
                finish();
            }

            @Override
            public void onNotSignedUp() {
                Log.e("onNotSignedUp","onNotSignedUp");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
        facebookManager.onDestroy();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }




}
