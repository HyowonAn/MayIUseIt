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

    // [START refresh_token]
    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.e(TAG,token);

        sendRegistrationToServer(token);


    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private void sendRegistrationToServer(String token) {


        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        if(tokenManager.getToken() == null){
            startActivity(new Intent(this, LoginActivity.class));
        }

        api = ApiClient.createServiceWithAuth(ApiInterface.class, tokenManager);

        Call<CallResponse> call = api.write_fcm_token(token);
        call.enqueue(new Callback<CallResponse>() {
            @Override
            public void onResponse(Call<CallResponse> call, Response<CallResponse> response) {

                if(response.isSuccessful()){
                    CallResponse callResponse = response.body();
                    Log.e(TAG,"token updated");


                }else{
                    Log.e(TAG," Response Error "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CallResponse> call, Throwable t) {
                Log.e(TAG," Response Error "+t.getMessage());
            }
        });
    }


}
