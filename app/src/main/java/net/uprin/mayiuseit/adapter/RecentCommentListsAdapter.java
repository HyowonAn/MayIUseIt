package net.uprin.mayiuseit.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.activity.DocumentActivity;
import net.uprin.mayiuseit.model.CommentList;
import net.uprin.mayiuseit.model.RecentCommentList;
import net.uprin.mayiuseit.util.CategorytoString;
import net.uprin.mayiuseit.util.CommentDialog;
import net.uprin.mayiuseit.util.RatingDialog;
import net.uprin.mayiuseit.util.TokenManager;

import java.util.List;

/**
 * Created by CJS on 2017-12-10.
 */

public class RecentCommentListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final String TAG = RecentCommentListsAdapter.class.getSimpleName();

    public final int TYPE_DOCUMENTS = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    static TokenManager tokenManager;
    List<RecentCommentList> recentcommentLists;
    RecentCommentListsAdapter.OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public RecentCommentListsAdapter(Context context, List<RecentCommentList> recentcommentLists) {
        this.context = context;
        this.recentcommentLists = recentcommentLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType== TYPE_DOCUMENTS){
            return new RecentCommentListsAdapter.RecentCommentListsHolder(inflater.inflate(R.layout.list_item_recent_comment,parent,false));
        }else{
            return new RecentCommentListsAdapter.LoadHolder(inflater.inflate(R.layout.row_load,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }
//
        if(getItemViewType(position)== TYPE_DOCUMENTS){
            ((RecentCommentListsAdapter.RecentCommentListsHolder)holder).bindData(recentcommentLists.get(position));
        }
//        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(recentcommentLists.get(position).isLoad() !=true){
            return TYPE_DOCUMENTS;
        }else{
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return recentcommentLists.size();
    }

    /* VIEW HOLDERS */

    static class RecentCommentListsHolder extends RecyclerView.ViewHolder{
        LinearLayout recent_comments_layout;
        TextView nickname, content, rgsde, title, category_id,recent_comment_rated_textview;
        CardView cardView;
        ImageView recent_comment_cardimage, recent_comment_rated_imageview;
        de.hdodenhof.circleimageview.CircleImageView recent_comment_profile_image;
        LinearLayout recent_comment_rating_btn,recent_comment_comment_btn,recent_comment_list_share_btn;

        public RecentCommentListsHolder(View v) {
            super(v);

            recent_comments_layout = (LinearLayout) v.findViewById(R.id.list_item_recent_comment);
            nickname = (TextView) v.findViewById(R.id.recent_comment_nickname);
            content = (TextView) v.findViewById(R.id.recent_comment_content);
            rgsde = (TextView) v.findViewById(R.id.recent_comment_rgsde);
            title = (TextView) v.findViewById(R.id.recent_comment_title);
            category_id = (TextView) v.findViewById(R.id.recent_comment_category_id);
            cardView = (CardView) v.findViewById(R.id.list_item_recent_comment_cardview);
            recent_comment_cardimage = (ImageView) v.findViewById(R.id.recent_comment_cardimage);
            recent_comment_rated_imageview = (ImageView) v.findViewById(R.id.recent_comment_rated_imageview);
            recent_comment_profile_image = (de.hdodenhof.circleimageview.CircleImageView) v.findViewById(R.id.recent_comment_profile_image);
            recent_comment_rating_btn = (LinearLayout) v.findViewById(R.id.recent_comment_rating_btn);
            recent_comment_comment_btn= (LinearLayout) v.findViewById(R.id.recent_comment_comment_btn);
            recent_comment_list_share_btn = (LinearLayout) v.findViewById(R.id.recent_comment_list_share_btn);
            recent_comment_rated_textview = (TextView) v.findViewById(R.id.recent_comment_rated_textview);

        }

        void bindData(final RecentCommentList recentcommentLists){

            nickname.setText(recentcommentLists.getNickname());
            content.setText(recentcommentLists.getContent());
            rgsde.setText(recentcommentLists.getRgsde());
            title.setText(recentcommentLists.getTitle());
            category_id.setText(new CategorytoString().intToString(recentcommentLists.getCategory_id()));

            if(recentcommentLists.getRate()!=0f){
                recent_comment_rated_textview.setText("내 점수 : " + recentcommentLists.getRate());
                recent_comment_rated_textview.setTextColor(ContextCompat.getColor(context, R.color .colorAccent));
                recent_comment_rated_imageview.setColorFilter(ContextCompat.getColor(context, R.color .colorAccent));
            }

            Glide.with(context).load(recentcommentLists.getImg_srl())
                    .thumbnail(Glide.with(context).load(R.drawable.fancy_loader2).apply(new RequestOptions().centerCrop()))
                    .apply(new RequestOptions()
                            //.placeholder(R.drawable.food_background)
                            .centerCrop()
                    )
                    .into(recent_comment_cardimage);

            tokenManager = TokenManager.getInstance(context.getSharedPreferences("prefs", Context.MODE_PRIVATE));

            Glide.with(context).load(tokenManager.getTokenData().getProfile_image())
                    .thumbnail(Glide.with(context).load(R.drawable.profile_default).apply(new RequestOptions().centerCrop()))
                    .apply(new RequestOptions()
                            //.placeholder(R.drawable.food_background)
                            .centerCrop()
                    )
                    .into(recent_comment_profile_image);

            cardView.setOnClickListener(new View.OnClickListener() {
                Intent intent;
                @Override
                public void onClick(View view) {
                    intent =  new Intent(context, DocumentActivity.class);
                    intent.putExtra("document_srl", recentcommentLists.getDocument_srl());
                    context.startActivity(intent);
                }
            });

            recent_comment_rating_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    RatingDialog dialog = new RatingDialog(context,recentcommentLists);
//                    dialog.show();
//                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
//                        @Override
//                        public void onDismiss(DialogInterface dialog) {
//                            if(documentLists.getRate()!=0f){
//                                document_rated_textview.setText("내 점수 : " + documentLists.getRate());
//                                document_rated_textview.setTextColor(ContextCompat.getColor(context, R.color .colorAccent));
//                                document_rated_imageview.setColorFilter(ContextCompat.getColor(context, R.color .colorAccent));
//                            } else {
//                                document_rated_textview.setText("별점 남기기");
//                                document_rated_textview.setTextColor(ContextCompat.getColor(context, R.color .colorGreyLight));
//                                document_rated_imageview.setColorFilter(ContextCompat.getColor(context, R.color .colorGreyLight));
//                            }
//                        }
//                    });
                }
            });

            recent_comment_comment_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    CommentDialog dialog = new CommentDialog(context,recentcommentLists);
//                    dialog.show();
                }
            });

            recent_comment_list_share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    // Set default text message
                    // 카톡, 이메일, MMS 다 이걸로 설정 가능
                    intent.setType("text/plain");
                    String subject = "리콜정보 안내";
                    String text = "리콜정보 : " + recentcommentLists.getTitle() + "\n 리콜사유 : " + recentcommentLists.getReason() + "\n 등록일자 : " + recentcommentLists.getRgsde() + "\n" + recentcommentLists.getImg_srl();
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, text);
                    // Title of intent
                    Intent chooser = Intent.createChooser(intent, "공유하기");
                    context.startActivity(chooser);

                }
            });
        }
    }

    static class LoadHolder extends RecyclerView.ViewHolder{
        public LoadHolder(View itemView) {
            super(itemView);
        }
    }

    public void setMoreDataAvailable(boolean moreDataAvailable) {
        isMoreDataAvailable = moreDataAvailable;
    }

    /* notifyDataSetChanged is final method so we can't override it
         call adapter.notifyDataChanged(); after update the list
         */
    public void notifyDataChanged(){
        notifyDataSetChanged();
        isLoading = false;
    }


    public interface OnLoadMoreListener{
        void onLoadMore();
    }

    public void setLoadMoreListener(RecentCommentListsAdapter.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
