package net.uprin.mayiuseit.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.activity.LoginActivity;
import net.uprin.mayiuseit.adapter.RecentCommentListsAdapter;
import net.uprin.mayiuseit.model.RecentCommentList;
import net.uprin.mayiuseit.model.RecentCommentListResponse;
import net.uprin.mayiuseit.rest.ApiClient;
import net.uprin.mayiuseit.rest.ApiInterface;
import net.uprin.mayiuseit.util.TokenManager;
import net.uprin.mayiuseit.util.VerticalLineDecorator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ProfileFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = ProfileFragment.class.getSimpleName();
    private final int TOP_REFRESH = 1;
    public final static String ITEMS_COUNT_KEY = "MainActivityFragment$ItemsCount";
    ImageView profile_default_background;
    TextView profile_name,user_profile_short;
    TokenManager tokenManager;
    de.hdodenhof.circleimageview.CircleImageView profile_image;

    private SwipeRefreshLayout refreshLayout;
    private  int pageNum = 1;
    RecyclerView recyclerView;
    List<RecentCommentList> recentcommentLists;
    RecentCommentListsAdapter adapter;
    ApiInterface api;

    public static ProfileFragment createInstance() {
        ProfileFragment homeFragment = new ProfileFragment();
        Bundle bundle = new Bundle();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        tokenManager = TokenManager.getInstance(this.getActivity().getSharedPreferences("prefs", Context.MODE_PRIVATE));

        if(tokenManager.getToken() == null){
            startActivity(new Intent(super.getContext().getApplicationContext(), LoginActivity.class));
            ((Activity)getContext()).finish();
        }

        refreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipeRefreshLo);
        refreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        refreshLayout.setOnRefreshListener(this);

        recyclerView = (RecyclerView) v.findViewById(R.id.recent_comment_recycler_view);
        recentcommentLists = new ArrayList<>();

        adapter = new RecentCommentListsAdapter(this.getActivity(), recentcommentLists);
        adapter.setLoadMoreListener(new RecentCommentListsAdapter.OnLoadMoreListener() {
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.addItemDecoration(new VerticalLineDecorator(2));
        recyclerView.setAdapter(adapter);

        api = ApiClient.createServiceWithAuth(ApiInterface.class, tokenManager);
        load(pageNum);

        profile_default_background = v.findViewById(R.id.profile_background_image);
        profile_name = v.findViewById(R.id.profile_name);
        user_profile_short = v.findViewById(R.id.user_profile_short);
        profile_image = v.findViewById(R.id.profile_image);


        profile_name.setText(tokenManager.getTokenData().getNickname().toString());
        user_profile_short.setText(tokenManager.getTokenData().getEmail_address().toString());

        Glide.with(this).load(R.drawable.profile_default_background).into(profile_default_background);

        Glide.with(this).load(tokenManager.getTokenData().getProfile_image())
                .thumbnail(Glide.with(this).load(R.drawable.profile_default).apply(new RequestOptions().centerCrop()))
                .apply(new RequestOptions()
                        .centerCrop()
                        .error(R.drawable.profile_default).centerCrop())
                //.apply(bitmapTransform(new BlurTransformation(100)))
               .into(profile_image);

        return v;

    }

    private void load(int index){
        Call<RecentCommentListResponse> call = api.recent_comment_list(index);
        call.enqueue(new Callback<RecentCommentListResponse>() {
            @Override
            public void onResponse(Call<RecentCommentListResponse> call, Response<RecentCommentListResponse> response) {
                if(response.isSuccessful()){
                    recentcommentLists.addAll(response.body().getResults());
                    adapter.notifyDataChanged();
                    pageNum = pageNum +1;
                }else{
                    Log.e(TAG," Response Error "+String.valueOf(response.code()));
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<RecentCommentListResponse> call, Throwable t) {
                Log.e(TAG," Response Error "+t.getMessage());
                refreshLayout.setRefreshing(false);
            }
        });
    }

    private void loadMore(int index){

        //add loading progress view
        recentcommentLists.add(new RecentCommentList(true));
        adapter.notifyItemInserted(recentcommentLists.size()-1);

        Call<RecentCommentListResponse> call = api.recent_comment_list(index);
        call.enqueue(new Callback<RecentCommentListResponse>() {
            @Override
            public void onResponse(Call<RecentCommentListResponse> call, Response<RecentCommentListResponse> response) {
                if(response.isSuccessful()){

                    //remove loading view
                    recentcommentLists.remove(recentcommentLists.size()-1);

                    List<RecentCommentList> result = response.body().getResults();
                    if(result!=null){
                        Log.e(TAG,"Result is " + result.size());
                        //add loaded data
                        recentcommentLists.addAll(result);
                        pageNum =response.body().getPage()+1;
                    }else{//result size 0 means there is no more data available at server
                        adapter.setMoreDataAvailable(false);
                        //telling adapter to stop calling load more as no more server data available
                        Snackbar.make(getActivity().findViewById(android.R.id.content), "마지막 페이지입니다", Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                            }
                        }).show();
                    }
                    adapter.notifyDataChanged();
                    //should call the custom method adapter.notifyDataChanged here to get the correct loading status
                }else{
                    Log.e(TAG," Load More Response Error "+String.valueOf(response.code()));
                }
                refreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<RecentCommentListResponse> call, Throwable t) {
                Log.e(TAG," Load More Response Error "+t.getMessage());
                refreshLayout.setRefreshing(false);
            }

        });
    }

    @Override
    public void onRefresh() {
        dataOption(TOP_REFRESH);
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
        Toast.makeText(getContext(),"로딩완료",Toast.LENGTH_SHORT).show();
    }

    private void dataOption(int option){
        switch (option) {
            case TOP_REFRESH:
                recentcommentLists.clear();
                pageNum=1;
                //load(pageNum);
                break;
        }
        // adapter.notifyDataSetChanged();

    }
}
