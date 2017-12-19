package net.uprin.mayiuseit.firebase;

/**
 * Created by uPrin on 2017. 12. 10..
 */

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import net.uprin.mayiuseit.activity.LoginActivity;
import net.uprin.mayiuseit.model.CallResponse;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.util.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    TokenManager tokenManager;
    ApiInterface api;
    String token = FirebaseInstanceId.getInstance().getToken();
    private static FirebaseInstanceIDService INSTANCE = null;

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.

        Log.e(TAG,token);

        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));
        tokenManager.sendRegistrationToServer();

    }

    private FirebaseInstanceIDService(){

    }


    public static synchronized FirebaseInstanceIDService getInstance(){
        if(INSTANCE == null){
            INSTANCE = new FirebaseInstanceIDService();
        }
        return INSTANCE;
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }




}
