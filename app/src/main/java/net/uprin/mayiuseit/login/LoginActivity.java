package net.uprin.mayiuseit.login;

/**
 * Created by uPrin on 2017. 10. 30..
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.kakao.usermgmt.callback.MeResponseCallback;
import com.kakao.usermgmt.response.model.UserProfile;
import com.kakao.util.exception.KakaoException;
import com.kakao.util.helper.log.Logger;

import net.uprin.mayiuseit.MainActivity;
import net.uprin.mayiuseit.PrefManager;
import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.WelcomeActivity;

import org.w3c.dom.Text;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoginActivity extends AppCompatActivity {
    private SessionCallback callback;

    LoginButton kakaologinButton;
    AppCompatButton fakeKakaoButton, facebookButton, naverButton, emailButton;




    private PrefManager prefManager;
    TextView welcome_reset_txtbtn, login_txtbtn, forgot_txtbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_login);

        changeStatusBarColor();
        //다시 시작화면 보이기
        welcome_reset_txtbtn = (TextView) findViewById(R.id.welcome_reset_txtbtn);
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
        emailButton = (AppCompatButton)findViewById(R.id.com_email_login);
        login_txtbtn = (TextView)findViewById(R.id.login) ;
        forgot_txtbtn = (TextView) findViewById(R.id.forgot_password);

        fakeKakaoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                kakaologinButton.performClick();
            }
        });
        kakaologinButton = (LoginButton)findViewById(R.id.com_kakao_login);
        kakaologinButton.setVisibility(View.INVISIBLE);
        kakaologinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isConnected()){
                    Toast.makeText(getApplicationContext(),"인터넷 연결을 확인해주세요",Toast.LENGTH_SHORT).show();
                }
            }
        });

        if(Session.getCurrentSession().isOpened()){
            requestMe();
        }else{
        }

        //facebook
        facebookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"아직 준비중 입니다",Toast.LENGTH_SHORT).show();
            }
        });

        //naver
        naverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"아직 준비중 입니다",Toast.LENGTH_SHORT).show();
            }
        });

        //email
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"아직 준비중 입니다",Toast.LENGTH_SHORT).show();
            }
        });

        login_txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"아직 준비중 입니다",Toast.LENGTH_SHORT).show();
            }
        });

        forgot_txtbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"아직 준비중 입니다",Toast.LENGTH_SHORT).show();
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
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
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
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }




}
