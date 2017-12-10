package net.uprin.mayiuseit.firebase;

/**
 * Created by uPrin on 2017. 12. 10..
 */

import android.content.Intent;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import net.uprin.mayiuseit.activity.LoginActivity;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.util.TokenManager;


public class FirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
    TokenManager tokenManager;
    ApiInterface api;

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG,token);

        sendRegistrationToServer(token);


    }

    private void sendRegistrationToServer(String token) {


        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        if(tokenManager.getToken() == null){
            startActivity(new Intent(this, LoginActivity.class));
        }

        api = ApiClient.createServiceWithAuth(ApiInterface.class, tokenManager);


    }


}
