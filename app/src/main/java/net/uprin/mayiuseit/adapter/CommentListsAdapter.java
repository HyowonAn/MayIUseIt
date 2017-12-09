package net.uprin.mayiuseit.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.fragment.NoticeFragment;
import net.uprin.mayiuseit.model.CommentList;
import net.uprin.mayiuseit.model.NoticeList;

import java.util.List;

/**
 * Created by CJS on 2017-11-26.
 */

public class CommentListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = CommentListsAdapter.class.getSimpleName();

    public final int TYPE_DOCUMENTS = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<CommentList> commentLists;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public CommentListsAdapter(Context context, List<CommentList> commentLists) {
        this.context = context;
        this.commentLists = commentLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType== TYPE_DOCUMENTS){
            return new CommentListsHolder(inflater.inflate(R.layout.list_item_comment,parent,false));
        }else{
            return new LoadHolder(inflater.inflate(R.layout.row_load,parent,false));
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
            ((CommentListsHolder)holder).bindData(commentLists.get(position));
        }
//        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(commentLists.get(position).isLoad() !=true){
            return TYPE_DOCUMENTS;
        }else{
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return commentLists.size();
    }

    /* VIEW HOLDERS */

    static class CommentListsHolder extends RecyclerView.ViewHolder{
        LinearLayout comments_layout;
        TextView nickname, content, rgsde;

        public CommentListsHolder(View v) {
            super(v);

            comments_layout = (LinearLayout) v.findViewById(R.id.list_item_comment);
            nickname = (TextView) v.findViewById(R.id.list_item_comment_nickname);
            content = (TextView) v.findViewById(R.id.list_item_comment_content);
            rgsde = (TextView) v.findViewById(R.id.list_item_comment_rgsde);


        }

        void bindData(final CommentList commentLists){
            Log.e(TAG,""+commentLists.getNickname());
            nickname.setText(commentLists.getNickname());
            content.setText(commentLists.getContent());
            rgsde.setText(commentLists.getRgsde());


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

    public void setLoadMoreListener(CommentListsAdapter.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
