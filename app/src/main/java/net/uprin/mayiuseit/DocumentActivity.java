package net.uprin.mayiuseit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.uprin.mayiuseit.login.LoginResponse;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.rest.Document;
import net.uprin.mayiuseit.rest.DocumentListsAdapter;
import net.uprin.mayiuseit.rest.DocumentResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentActivity extends AppCompatActivity {

    LinearLayout detailsLayout;
    TextView document_slr;
    TextView category_id;
    TextView original_slr;
    TextView Title;
    TextView reason;
    TextView company;
    TextView company_slr;
    TextView certification_id;
    TextView img_slr;
    TextView company_contact;
    TextView original_from;
    TextView original_url;
    TextView rgsde;
    TextView readed_count;
    TextView rated_count;

    private static final String TAG = DocumentActivity.class.getSimpleName();

    private  int param = 0;
    ApiInterface api;
    List<Document> document;
    DocumentResponse documentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_item_detail);
        document = new ArrayList<>();
        api = ApiClient.getClient().create(ApiInterface.class);
        DocumentListsAdapter adapter;
       // adapter = new DocumentListsAdapter(this, documentLists);
        Intent intent = getIntent();
        param = intent.getExtras().getInt("document_slr");

        Call<DocumentResponse> call = api.getDocument(param);
        call.enqueue(new Callback<DocumentResponse>() {
            @Override
            public void onResponse(Call<DocumentResponse> call, Response<DocumentResponse> response) {

                if(response.isSuccessful()){
                    DocumentResponse documentResponse = response.body();
                    document.addAll(documentResponse.getResults());
                    Log.e(TAG," Response Error "+document.get(0).getReason());
                    bindData(document.get(0));
                }else{
                    Log.e(TAG," Response Error "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<DocumentResponse> call, Throwable t) {
                Log.e(TAG," Response Error "+t.getMessage());
            }
        });

    }

    public void bindData(Document document) {
        detailsLayout = (LinearLayout) findViewById(R.id.details_layout);
        document_slr = (TextView) findViewById(R.id.detail_document_slr);
        category_id = (TextView) findViewById(R.id.detail_category_id);
        original_slr = (TextView) findViewById(R.id.detail_original_slr);
        reason = (TextView) findViewById(R.id.detail_reason);
        Title = (TextView) findViewById(R.id.detail_title);
        company = (TextView) findViewById(R.id.detail_company);
        company_slr = (TextView)findViewById(R.id.detail_company_slr);
        certification_id = (TextView) findViewById(R.id.detail_certification_id);
        img_slr = (TextView) findViewById(R.id.detail_img_slr);
        company_contact = (TextView) findViewById(R.id.detail_company_contact);
        original_from = (TextView) findViewById(R.id.detail_original_from);
        original_url = (TextView) findViewById(R.id.detail_original_url);
        rgsde = (TextView) findViewById(R.id.detail_rgsde);
        readed_count = (TextView) findViewById(R.id.detail_readed_count);
        rated_count = (TextView) findViewById(R.id.detail_rated_count);

        document_slr.setText(""+ document.getDocument_slr());
        category_id.setText(""+ document.getCategory_id());
        reason.setText(document.getReason());
        Title.setText(document.getTitle());
        company.setText(document.getCompany());
        img_slr.setText(document.getImg_slr());
        original_from.setText(document.getOriginal_from());
        rgsde.setText(document.getRgsde());
        readed_count.setText(""+ document.getReaded_count());
        rated_count.setText(""+ document.getRated_count());
        original_slr.setText(""+ document.getRated_count());
        company_slr.setText(""+ document.getRated_count());
        certification_id.setText(""+ document.getRated_count());
        company_contact.setText(""+ document.getRated_count());
        original_url.setText(""+ document.getRated_count());
    }
}
