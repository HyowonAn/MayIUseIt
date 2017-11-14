package net.uprin.mayiuseit.rest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import net.uprin.mayiuseit.R;

import java.util.List;

/**
 * Created by CJS on 2017-11-08.
 */

public class DocumentsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_DOCUMENTS = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<DocumentList> movies;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public DocumentsAdapter(Context context, List<DocumentList> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType== TYPE_DOCUMENTS){
            return new MovieHolder(inflater.inflate(R.layout.list_item_document,parent,false));
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

        if(getItemViewType(position)== TYPE_DOCUMENTS){
            ((MovieHolder)holder).bindData(movies.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(movies.get(position).getCategory_id() !=999){
            return TYPE_DOCUMENTS;
        }else{
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    /* VIEW HOLDERS */

    static class MovieHolder extends RecyclerView.ViewHolder{
        LinearLayout documentsLayout;
        TextView category_id;
        TextView Title;
        TextView reason;
        TextView company;
        TextView original_from;
        TextView rgsde;
        TextView readed_count;
        TextView rated_count;
        ImageView imageView;
        public MovieHolder(View v) {
            super(v);
            documentsLayout = (LinearLayout) v.findViewById(R.id.documents_layout);
            category_id = (TextView) v.findViewById(R.id.category_id);
            reason = (TextView) v.findViewById(R.id.reason);
            Title = (TextView) v.findViewById(R.id.title);
            company = (TextView) v.findViewById(R.id.company);
            original_from = (TextView) v.findViewById(R.id.original_from);
            rgsde = (TextView) v.findViewById(R.id.rgsde);
            readed_count = (TextView) v.findViewById(R.id.readed_count);
            rated_count = (TextView) v.findViewById(R.id.rated_count);
            imageView = (ImageView) v.findViewById(R.id.cardimage);
        }

        void bindData(DocumentList documentLists){
            category_id.setText(""+documentLists.getCategory_id());
            reason.setText(documentLists.getReason());
            Title.setText(documentLists.getTitle());
            company.setText(documentLists.getCompany());
            original_from.setText(documentLists.getOriginal_from());
            rgsde.setText(documentLists.getRgsde());
            readed_count.setText(""+documentLists.getReaded_count());
            rated_count.setText(String.format("%.1f",documentLists.getRated_count()));
            // Glide.with(context).load(R.drawable.visit_background).into(visit);

            Glide.with(context).load(documentLists.getImg_slr())
                    .into(imageView);
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

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}