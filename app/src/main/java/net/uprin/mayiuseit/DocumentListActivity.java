package net.uprin.mayiuseit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.rest.DocumentList;
import net.uprin.mayiuseit.rest.DocumentListResponse;
import net.uprin.mayiuseit.rest.DocumentsAdater;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentListActivity extends AppCompatActivity {

    private static final String TAG = DocumentListActivity.class.getSimpleName();


    private  int pageNum = 1;
    private  int category= 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document_list);
        initToolbar();

        Intent intent = getIntent();
        category = intent.getExtras().getInt("category");

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.documents_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ApiInterface apiService =
                ApiClient.getClient().create(ApiInterface.class);

        Call<DocumentListResponse> call = apiService.getDocumentList(pageNum, category);
        call.enqueue(new Callback<DocumentListResponse>() {
            @Override
            public void onResponse(Call<DocumentListResponse> call, Response<DocumentListResponse> response) {
                int statusCode = response.code();
                List<DocumentList> movies = response.body().getResults();
                Log.d(TAG, "Number of documents received: " + movies.size());
                recyclerView.setAdapter(new DocumentsAdater(movies, R.layout.list_item_document, getApplicationContext()));

            }

            @Override
            public void onFailure(Call<DocumentListResponse> call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        setTitle("뒤로가기");
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        //mToolbar.setLogo(R.drawable.toolbar_logo);
        // ↓툴바에 홈버튼을 활성화
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
    }
}
