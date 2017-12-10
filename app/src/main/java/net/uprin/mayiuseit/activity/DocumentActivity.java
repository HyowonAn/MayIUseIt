package net.uprin.mayiuseit.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.florent37.glidepalette.GlidePalette;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.adapter.CommentListsAdapter;
import net.uprin.mayiuseit.adapter.NoticeListsAdapter;
import net.uprin.mayiuseit.model.Comment;
import net.uprin.mayiuseit.model.CommentList;
import net.uprin.mayiuseit.model.CommentListResponse;
import net.uprin.mayiuseit.model.NoticeList;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.model.Document;
import net.uprin.mayiuseit.adapter.DocumentListsAdapter;
import net.uprin.mayiuseit.model.DocumentResponse;
import net.uprin.mayiuseit.util.CategorytoString;
import net.uprin.mayiuseit.util.TokenManager;
import net.uprin.mayiuseit.util.VerticalLineDecorator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DocumentActivity extends AppCompatActivity {

    int mCartItemCount = 10;

    CollapsingToolbarLayout toolbarLayout;
    LinearLayout head_layout;
    CoordinatorLayout root_layout;
    AppBarLayout app_bar_layout;
    Context context;
    TextView document_srl;
    TextView category_id;
    TextView original_srl;
    TextView Title;
    TextView reason,detail_detail;
    TextView company;
    TextView company_srl;
    TextView certification_id;
    ImageView img_srl_background,img_srl;
    TextView company_contact;
    TextView original_from,original_from2;
    TextView original_url;
    TextView rgsde;
    TextView readed_count;
    TextView rated_count;
    TextView detail_commented_count, detail_commented_count2;
    Toolbar toolbar;
    Window window;
    Button detailbutton;
    Boolean detail_all=false;
    FloatingActionButton floatingActionButton;
    TextView textCartItemCount;

    TokenManager tokenManager;

    List<CommentList> commentLists;
    RecyclerView recyclerView;
    private  int pageNum = 1;
    CommentListsAdapter adapter;

    Button document_comment_button;

    AppCompatEditText document_comment_editText;


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
       adapter = new CommentListsAdapter(this, commentLists);
        Intent intent = getIntent();
        param = intent.getExtras().getInt("document_srl");

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


        document_comment_button = (Button) findViewById(R.id.document_comment_button);
        document_comment_editText = (AppCompatEditText) findViewById(R.id.document_comment_editText);

        document_comment_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                api = ApiClient.createServiceWithAuth(ApiInterface.class, tokenManager);

                if ( document_comment_editText.getText().toString().length() == 0 ) {

                    Toast.makeText(getApplicationContext(), "한줄평이 입력되지 않았습니다", Toast.LENGTH_SHORT).show();

                } else {

                    Call<Comment> call = api.write_comment(param, document_comment_editText.getText().toString());
                    call.enqueue(new Callback<Comment>() {
                        @Override
                        public void onResponse(Call<Comment> call, Response<Comment> response) {

                            if(response.isSuccessful()){
                                Comment comment = response.body();
                                Toast.makeText(getApplicationContext(), "갱신완료", Toast.LENGTH_SHORT).show();
                                finish();
                                startActivity(getIntent());

                            }else{
                                Log.e(TAG," Response Error "+String.valueOf(response.code()));
                                tokenManager.deleteToken();
                                Snackbar.make(getWindow().getDecorView().getRootView(), "에러가 발생했습니다", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                }).show();

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


        context = this;
        recyclerView = (RecyclerView) findViewById(R.id.document_comment_recycler_view);
        commentLists = new ArrayList<>();

        adapter = new CommentListsAdapter(this, commentLists);
        adapter.setLoadMoreListener(new CommentListsAdapter.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        loadMore(pageNum);
                    }
                });
                //Calling loadMore function in Runnable to fix the
                // java.lang.IllegalStateException: Cannot call this method while RecyclerView is computing a layout or scrolling error
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new VerticalLineDecorator(2));
        recyclerView.setAdapter(adapter);
        load(pageNum);


    }

    private void load(int index){
        Call<CommentListResponse> call = api.comment_list(index,param);
        call.enqueue(new Callback<CommentListResponse>() {
            @Override
            public void onResponse(Call<CommentListResponse> call, Response<CommentListResponse> response) {
                if(response.isSuccessful()){
                    List<CommentList> result = response.body().getResults();

                    if(result!=null){
                        Log.e(TAG,"Result is " + result.size());
                        //add loaded data
                        commentLists.addAll(result);
                        pageNum =response.body().getPage()+1;
                        adapter.notifyDataChanged();

                    }else{//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
//                        Snackbar.make(findViewById(android.R.id.content), "마지막 페이지입니다", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                            }
//                        }).show();
                    }
                }else{
                    Log.e(TAG," Response Error "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CommentListResponse> call, Throwable t) {
                Log.e(TAG," Response Error "+t.getMessage());
            }
        });
    }

    private void loadMore(int index){

        //add loading progress view
        commentLists.add(new CommentList(true));
        adapter.notifyItemInserted(commentLists.size()-1);

        Call<CommentListResponse> call = api.comment_list(index,param);
        call.enqueue(new Callback<CommentListResponse>() {
            @Override
            public void onResponse(Call<CommentListResponse> call, Response<CommentListResponse> response) {
                if(response.isSuccessful()){

                    //remove loading view
                    commentLists.remove(commentLists.size()-1);

                    List<CommentList> result = response.body().getResults();
                    if(result!=null){
                        Log.e(TAG,"Result is " + result.size());
                        //add loaded data
                        commentLists.addAll(result);
                        pageNum =response.body().getPage()+1;
                    }else{//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
//                        Snackbar.make(findViewById(android.R.id.content), "마지막 페이지입니다", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//
//                            }
//                        }).show();
                    }
                    adapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                }else{
                    Log.e(TAG," Load More Response Error "+String.valueOf(response.code()));
                }
            }

            @Override
            public void onFailure(Call<CommentListResponse> call, Throwable t) {
                Log.e(TAG," Load More Response Error "+t.getMessage());
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
        detail_commented_count = (TextView) findViewById(R.id.detail_commented_count);
        detail_commented_count2 =(TextView) findViewById(R.id.detail_commented_count2);
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
        detail_commented_count.setText(""+ document.getCommented_count());
        detail_commented_count2.setText(""+ document.getCommented_count());

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

        Glide.with(this).load(document.getImg_srl())
                .thumbnail(Glide.with(this).load(R.drawable.fancy_loader2).apply(new RequestOptions().centerCrop()))
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.medical_background).centerCrop())
                //.apply(bitmapTransform(new BlurTransformation(100)))
                .listener(GlidePalette.with(document.getImg_srl())
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
                String text = "리콜정보 : " + document.getTitle() + "\n 리콜사유 : " + document.getReason() + "\n 등록일자 : " + document.getRgsde() + "\n" + document.getImg_srl();
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

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id==R.id.settings){

            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));

        }else if (id==R.id.dev_id){

            startActivity(new Intent(getApplicationContext(), DevActivity.class));


        }else if (id==R.id.item3_id){

            Toast.makeText(getApplicationContext(),"item3 is selected",Toast.LENGTH_SHORT).show();

        }else if (id==R.id.logout_menu_item){

        }else if (id==R.id.search_id){

            startActivity(new Intent(getApplicationContext(), SearchActivity.class));

        }else if (id==R.id.alarm_history){

            Toast.makeText(getApplicationContext(),"alarm_history",Toast.LENGTH_SHORT).show();

        }else if (id==android.R.id.home){

            onBackPressed();

        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setupBadge() {

        if (textCartItemCount != null) {
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
    }

}
