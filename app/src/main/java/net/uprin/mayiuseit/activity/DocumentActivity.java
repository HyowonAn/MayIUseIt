package net.uprin.mayiuseit.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.florent37.glidepalette.GlidePalette;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.model.Document;
import net.uprin.mayiuseit.adapter.DocumentListsAdapter;
import net.uprin.mayiuseit.model.DocumentResponse;
import net.uprin.mayiuseit.util.CategorytoString;
import net.uprin.mayiuseit.util.TokenManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentActivity extends AppCompatActivity {
    CollapsingToolbarLayout toolbarLayout;
    LinearLayout head_layout;
    CoordinatorLayout root_layout;
    AppBarLayout app_bar_layout;

    TextView document_slr;
    TextView category_id;
    TextView original_slr;
    TextView Title;
    TextView reason,detail_detail;
    TextView company;
    TextView company_slr;
    TextView certification_id;
    ImageView img_srl_background,img_srl;
    TextView company_contact;
    TextView original_from,original_from2;
    TextView original_url;
    TextView rgsde;
    TextView readed_count;
    TextView rated_count;
    Toolbar toolbar;
    Window window;
    Button detailbutton;
    Boolean detail_all=false;
    FloatingActionButton floatingActionButton;

    TokenManager tokenManager;


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
        floatingActionButton = (FloatingActionButton)findViewById(R.id.document_floating_action_button);
        head_layout = (LinearLayout) findViewById(R.id.head_layout);
        root_layout = (CoordinatorLayout) findViewById(R.id.root_layout);

        app_bar_layout = (AppBarLayout) findViewById(R.id.activity_document_appbar_layout);



        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        document = new ArrayList<>();


        tokenManager = TokenManager.getInstance(getSharedPreferences("prefs", MODE_PRIVATE));

        if(tokenManager.getToken() == null){
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        api = ApiClient.createServiceWithAuth(ApiInterface.class, tokenManager);
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
                    tokenManager.deleteToken();
                    Snackbar.make(getWindow().getDecorView().getRootView(), "로그인이 만료되었습니다", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    finish();
                }
            }

            @Override
            public void onFailure(Call<DocumentResponse> call, Throwable t) {
                Log.e(TAG," Response Error "+t.getMessage());
            }
        });

    }

    public void bindData(final Document document) {
        toolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.activity_document_toolbar_layout);
        category_id = (TextView) findViewById(R.id.detail_category_id);
        reason = (TextView) findViewById(R.id.detail_reason);
        Title = (TextView) findViewById(R.id.detail_title);
        company = (TextView) findViewById(R.id.detail_company);
        company_contact = (TextView) findViewById(R.id.detail_company_contact);
        original_from = (TextView) findViewById(R.id.detail_original_from);
        original_from2 = (TextView) findViewById(R.id.detail_original_from2);
        original_url = (TextView) findViewById(R.id.detail_original_url);
        rgsde = (TextView) findViewById(R.id.detail_rgsde);
        readed_count = (TextView) findViewById(R.id.detail_readed_count);
        rated_count = (TextView) findViewById(R.id.detail_rated_count);
        img_srl_background = (ImageView) findViewById(R.id.detail_img_srl_background);
        detail_detail = (TextView) findViewById(R.id.detail_detail);
        img_srl = (ImageView) findViewById(R.id.head_iv);
        category_id.setText(new CategorytoString().intToString(document.getCategory_id()));
        reason.setText(document.getReason());
        Title.setText(document.getTitle());
        company.setText(document.getCompany());
        original_from.setText(document.getOriginal_from());
        original_from2.setText(document.getOriginal_from());
        rgsde.setText(document.getRgsde());
        detail_detail.setText(""+document.getDetail());
        readed_count.setText(""+ document.getReaded_count());
        rated_count.setText(String.format("%.1f",document.getRated_count()));
        company_contact.setText(""+ document.getCompany_contact());
        original_url.setText(""+ document.getOriginal_url());

        Log.e(TAG,"original url is : "+ document.getOriginal_url());

        detailbutton = (Button) findViewById(R.id.dtail_button);
        detailbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!detail_all){
                    detail_detail.setMaxLines(Integer.MAX_VALUE);
                    detailbutton.setText("숨김");
                    detail_all=true;
                }else{
                    detailbutton.setText("전체 보기");
                    detail_detail.setMaxLines(10);
                    detail_all=false;
                }
            }
        });

        Glide.with(this).load(document.getImg_slr())
                .thumbnail(Glide.with(this).load(R.drawable.fancy_loader2).apply(new RequestOptions().centerCrop()))
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.medical_background).centerCrop())
                //.apply(bitmapTransform(new BlurTransformation(100)))
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
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable drawable, Transition<? super Drawable> transition) {
                        img_srl_background.setBackground(drawable);
                        //img_srl.setBackground(drawable);

                    }
                });


        app_bar_layout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset <= -head_layout.getHeight() / 2) {
                    toolbarLayout.setTitle(document.getTitle());
                } else {
                    toolbarLayout.setTitle("");
                }
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                // Set default text message
                // 카톡, 이메일, MMS 다 이걸로 설정 가능
                intent.setType("text/plain");
                String subject = "리콜정보 안내";
                String text = "리콜정보 : " + document.getTitle() + "\n 리콜사유 : " + document.getReason() + "\n 등록일자 : " + document.getRgsde() + "\n" + document.getImg_slr();
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, text);
                // Title of intent
                Intent chooser = Intent.createChooser(intent, "공유하기");
                startActivity(chooser);

            }
        });

    }

    private void changeStatusBarColor(int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(color);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.alarm_history);
        return true;
    }

}
