package net.uprin.mayiuseit;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.glidepalette.GlidePalette;

import net.uprin.mayiuseit.login.LoginResponse;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.rest.Document;
import net.uprin.mayiuseit.rest.DocumentListsAdapter;
import net.uprin.mayiuseit.rest.DocumentResponse;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class DocumentActivity extends AppCompatActivity {
    CollapsingToolbarLayout toolbarLayout;
    TextView document_slr;
    TextView category_id;
    TextView original_slr;
    TextView Title;
    TextView reason;
    TextView company;
    TextView company_slr;
    TextView certification_id;
    ImageView img_srl_background,img_srl;
    TextView company_contact;
    TextView original_from;
    TextView original_url;
    TextView rgsde;
    TextView readed_count;
    TextView rated_count;
    Toolbar toolbar;
    Window window;

    private static final String TAG = DocumentActivity.class.getSimpleName();

    private  int param = 0;
    ApiInterface api;
    List<Document> document;
    DocumentResponse documentResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_document);
        toolbar = (Toolbar)findViewById(R.id.activity_document_toolbar);
        setSupportActionBar(toolbar);

        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_document_toolbar_layout);
        document_slr = (TextView) findViewById(R.id.detail_document_slr);
        category_id = (TextView) findViewById(R.id.detail_category_id);
        original_slr = (TextView) findViewById(R.id.detail_original_slr);
        reason = (TextView) findViewById(R.id.detail_reason);
        Title = (TextView) findViewById(R.id.detail_title);
        company = (TextView) findViewById(R.id.detail_company);
        company_slr = (TextView)findViewById(R.id.detail_company_slr);
        certification_id = (TextView) findViewById(R.id.detail_certification_id);
        img_srl_background = (ImageView) findViewById(R.id.detail_img_srl_background);
        img_srl = (ImageView) findViewById(R.id.detail_img_srl);
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
        original_from.setText(document.getOriginal_from());
        rgsde.setText(document.getRgsde());
        readed_count.setText(""+ document.getReaded_count());
        rated_count.setText(""+ document.getRated_count());
        original_slr.setText(""+ document.getRated_count());
        company_slr.setText(""+ document.getRated_count());
        certification_id.setText(""+ document.getRated_count());
        company_contact.setText(""+ document.getRated_count());
        original_url.setText(""+ document.getRated_count());
        toolbarLayout.setTitle(document.getTitle());
        //img_slr.set
        Glide.with(this).load(document.getImg_slr())
                .apply(new RequestOptions()
                        .centerCrop()
                        //.placeholder(R.drawable.fancy_loader)
                        )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(img_srl);

        Glide.with(this).load(document.getImg_slr())
                .thumbnail(Glide.with(this).load(R.drawable.fancy_loader2).apply(new RequestOptions().centerCrop()))
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.medical_background))
                .apply(bitmapTransform(new BlurTransformation(100)))
                .listener(GlidePalette.with(document.getImg_slr())
                        .use(GlidePalette.Profile.MUTED_DARK)
                        .intoCallBack(new GlidePalette.CallBack() {
                            @Override
                            public void onPaletteLoaded(Palette palette) {
                                //specific
                                //toolbar.setBackgroundColor(palette.getDarkMutedColor(0x000000));

                                Log.e(TAG,"colour is : " + palette.getDarkMutedColor(0x000000)+"");
                                changeStatusBarColor(palette.getDarkMutedColor(0x000000));
                                toolbarLayout.setContentScrimColor(palette.getDarkMutedColor(0x000000));
                                //toolbarLayout.setExpandedTitleColor(palette.getLightMutedColor(0x000000));
                            }
                        })
                )
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(img_srl_background);




    }

    private void changeStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

}
