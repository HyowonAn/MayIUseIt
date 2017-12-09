package net.uprin.mayiuseit.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatRatingBar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.activity.LoginActivity;
import net.uprin.mayiuseit.model.Comment;
import net.uprin.mayiuseit.model.DocumentList;
import net.uprin.mayiuseit.model.Rate;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;


public class CommentDialog extends Dialog{

    private static final String TAG = CommentDialog.class.getSimpleName();

    private DocumentList documentList;
    AppCompatEditText commentEditText;
    Button rating_dialog_button;
    ApiInterface api;
    TokenManager tokenManager;



    public CommentDialog(Context context, DocumentList documentList) {
            super(context);
            this.documentList =  documentList;
            }

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.comment_dialog);
            tokenManager = TokenManager.getInstance(super.getContext().getSharedPreferences("prefs", MODE_PRIVATE));

            if(tokenManager.getToken() == null){
                super.getContext().startActivity(new Intent(super.getContext(), LoginActivity.class));
                ((Activity)getContext()).finish();
            }

            commentEditText = (AppCompatEditText) findViewById(R.id.comment_dialog_bar);
            rating_dialog_button = (Button) findViewById(R.id.comment_dialog_button);


            rating_dialog_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    api = ApiClient.createServiceWithAuth(ApiInterface.class, tokenManager);

                    if ( commentEditText.getText().toString().length() == 0 ) {

                        Toast.makeText(getContext().getApplicationContext(), "한줄평이 입력되지 않았습니다", Toast.LENGTH_SHORT).show();

                    } else {

                        Call<Comment> call = api.write_comment(documentList.getDocument_srl(), commentEditText.getText().toString());
                        call.enqueue(new Callback<Comment>() {
                            @Override
                            public void onResponse(Call<Comment> call, Response<Comment> response) {

                                if(response.isSuccessful()){
                                    Comment comment = response.body();
                                    Toast.makeText(getContext().getApplicationContext(), "갱신완료", Toast.LENGTH_SHORT).show();
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
                            public void onFailure(Call<Comment> call, Throwable t) {
                                Log.e(TAG," Response Error "+t.getMessage());
                            }
                        });


                    }

                }
            });

        }




    }
