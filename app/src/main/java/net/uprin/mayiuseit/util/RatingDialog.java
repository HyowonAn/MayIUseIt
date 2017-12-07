package net.uprin.mayiuseit.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.activity.DocumentActivity;
import net.uprin.mayiuseit.activity.LoginActivity;
import net.uprin.mayiuseit.model.Document;
import net.uprin.mayiuseit.model.DocumentList;
import net.uprin.mayiuseit.model.DocumentResponse;
import net.uprin.mayiuseit.model.Rate;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static android.support.v4.content.ContextCompat.startActivity;


public class RatingDialog extends Dialog{

    private static final String TAG = RatingDialog.class.getSimpleName();

    private DocumentList documentList;
    AppCompatRatingBar ratingBar;
    Button rating_dialog_button;
    ApiInterface api;
    TokenManager tokenManager;



    public RatingDialog(Context context, DocumentList documentList) {
            super(context);
            this.documentList =  documentList;
            }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.rating_dialog);
            tokenManager = TokenManager.getInstance(super.getContext().getSharedPreferences("prefs", MODE_PRIVATE));

            if(tokenManager.getToken() == null){
                super.getContext().startActivity(new Intent(super.getContext(), LoginActivity.class));
                ((Activity)getContext()).finish();
            }

            ratingBar = (AppCompatRatingBar) findViewById(R.id.rating_dialog_bar);
            rating_dialog_button = (Button) findViewById(R.id.rating_dialog_button);

            if(documentList.getRate()!=0f){
                ratingBar.setRating(documentList.getRate());
            }

            rating_dialog_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    api = ApiClient.createServiceWithAuth(ApiInterface.class, tokenManager);

                    Call<Rate> call = api.write_rate(documentList.getDocument_srl(), ratingBar.getRating());
                    call.enqueue(new Callback<Rate>() {
                        @Override
                        public void onResponse(Call<Rate> call, Response<Rate> response) {

                            if(response.isSuccessful()){
                                Rate rate = response.body();
                                Toast.makeText(getContext().getApplicationContext(), "갱신완료", Toast.LENGTH_SHORT).show();
                                documentList.setRate(ratingBar.getRating());

                                dismiss();

                            }else{
                                Log.e(TAG," Response Error "+String.valueOf(response.code()));
                                tokenManager.deleteToken();
                                Snackbar.make(getWindow().getDecorView().getRootView(), "에러가 발생했습니다", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();
                                dismiss();

                            }
                        }

                        @Override
                        public void onFailure(Call<Rate> call, Throwable t) {
                            Log.e(TAG," Response Error "+t.getMessage());
                        }
                    });

                }
            });

        }




    }
