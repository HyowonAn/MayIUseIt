package net.uprin.mayiuseit.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.uprin.mayiuseit.model.SearchList;
import net.uprin.mayiuseit.util.CategorytoString;
import net.uprin.mayiuseit.activity.DocumentActivity;
import net.uprin.mayiuseit.R;

import java.util.List;

/**
 * Created by CJS on 2017-12-02.
 */

public class SearchListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final int TYPE_LOAD = 1;

    static Context context;
    private static final String TAG = DocumentActivity.class.getSimpleName();

    public final int TYPE_DOCUMENTS = 0;
    List<SearchList> searchLists;
    SearchListsAdapter.OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public SearchListsAdapter(Context context, List<SearchList> searchLists) {
        this.context = context;
        this.searchLists = searchLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType== TYPE_DOCUMENTS){
            return new SearchListsAdapter.SearchListsHolder(inflater.inflate(R.layout.list_item_search,parent,false));
        }else{
            return new SearchListsAdapter.LoadHolder(inflater.inflate(R.layout.row_load,parent,false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(position>=getItemCount()-1 && isMoreDataAvailable && !isLoading && loadMoreListener!=null){
            isLoading = true;
            loadMoreListener.onLoadMore();
        }

        if(getItemViewType(position)== TYPE_DOCUMENTS){
            ((SearchListsAdapter.SearchListsHolder)holder).bindData(searchLists.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(searchLists.get(position).getCategory_id() !=999){
            return TYPE_DOCUMENTS;
        }else{
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return searchLists.size();
    }

    /* VIEW HOLDERS */

    static class SearchListsHolder extends RecyclerView.ViewHolder{
        LinearLayout searchLayout;
        TextView category_id;
        TextView Title;
        TextView reason;
        TextView company;
        TextView original_from;
        TextView rgsde;
        TextView readed_count;
        TextView rated_count;
        ImageView imageView;
        CardView cardView;
        public SearchListsHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.list_item_search_cardview);
            searchLayout = (LinearLayout) v.findViewById(R.id.search_layout);
            category_id = (TextView) v.findViewById(R.id.search_category_id);
            reason = (TextView) v.findViewById(R.id.search_reason);
            Title = (TextView) v.findViewById(R.id.search_title);
            company = (TextView) v.findViewById(R.id.search_company);
            original_from = (TextView) v.findViewById(R.id.search_original_from);
            rgsde = (TextView) v.findViewById(R.id.search_rgsde);
            readed_count = (TextView) v.findViewById(R.id.search_readed_count);
            rated_count = (TextView) v.findViewById(R.id.search_rated_count);
            imageView = (ImageView) v.findViewById(R.id.search_cardimage);
        }

        void bindData(final SearchList searchLists){
            category_id.setText(new CategorytoString().intToString(searchLists.getCategory_id()));
            reason.setText(searchLists.getReason());
            Title.setText(searchLists.getTitle());
            company.setText(searchLists.getCompany());
            original_from.setText(searchLists.getOriginal_from());
            rgsde.setText(searchLists.getRgsde());
            readed_count.setText(""+searchLists.getReaded_count());
            rated_count.setText(String.format("%.1f",searchLists.getRated_count()));
            // Glide.with(context).load(R.drawable.visit_background).into(visit);

            Glide.with(context).load(searchLists.getImg_slr())
                    .thumbnail(Glide.with(context).load(R.drawable.fancy_loader2).apply(new RequestOptions().centerCrop()))
                    .apply(new RequestOptions()
                            //.placeholder(R.drawable.food_background)
                            .centerCrop()
                    )
                    .into(imageView);

            cardView.setOnClickListener(new View.OnClickListener() {
                Intent intent;
                @Override
                public void onClick(View view) {
                    intent =  new Intent(context, DocumentActivity.class);
                    intent.putExtra("document_slr", searchLists.getDocument_slr());
                    context.startActivity(intent);
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

    public void setLoadMoreListener(SearchListsAdapter.OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}
