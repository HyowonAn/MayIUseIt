package net.uprin.mayiuseit.rest;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.uprin.mayiuseit.NoticeFragment;
import net.uprin.mayiuseit.R;

import java.util.List;

/**
 * Created by CJS on 2017-11-26.
 */

public class NoticeListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final String TAG = NoticeFragment.class.getSimpleName();

    public final int TYPE_DOCUMENTS = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<NoticeList> noticeLists;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public NoticeListsAdapter(Context context, List<NoticeList> noticeLists) {
        this.context = context;
        this.noticeLists = noticeLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType== TYPE_DOCUMENTS){
            return new NoticeListsHolder(inflater.inflate(R.layout.list_item_notice,parent,false));
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
            ((NoticeListsHolder)holder).bindData(noticeLists.get(position));
        }
//        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(noticeLists.get(position).isLoad() !=true){
            return TYPE_DOCUMENTS;
        }else{
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return noticeLists.size();
    }

    /* VIEW HOLDERS */

    static class NoticeListsHolder extends RecyclerView.ViewHolder{
        Button detailbutton;
        LinearLayout notices_layout;
        TextView title;
        TextView content;
        TextView rgsde;
        CardView cardView;

        public NoticeListsHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.list_item_notice_cardview);
            notices_layout = (LinearLayout) v.findViewById(R.id.notices_layout);
            title = (TextView) v.findViewById(R.id.notice_title);
            content = (TextView) v.findViewById(R.id.notice_content);
            rgsde = (TextView) v.findViewById(R.id.notice_rgsde);
            detailbutton = (Button) v.findViewById(R.id.notice_detail_button);
            detailbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(content.getVisibility()== View.GONE){
                        content.setVisibility(View.VISIBLE);
                        detailbutton.setText("▲");
                    }else{
                        detailbutton.setText("▼");
                        content.setVisibility(View.GONE);

                    }
                }
            });
        }

        void bindData(final NoticeList noticeLists){
            Log.e(TAG,""+noticeLists.getTitle());
            title.setText(noticeLists.getTitle());
            content.setText(noticeLists.getContent());
            rgsde.setText(noticeLists.getRgsde());

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

    public void setLoadMoreListener(NoticeListsAdapter.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
