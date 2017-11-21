package net.uprin.mayiuseit.rest;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import net.uprin.mayiuseit.DocumentActivity;
import net.uprin.mayiuseit.MainActivity;
import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.ScrollingActivity;
import net.uprin.mayiuseit.WelcomeActivity;

import java.util.List;

/**
 * Created by CJS on 2017-11-08.
 */

public class DocumentListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final int TYPE_DOCUMENTS = 0;
    public final int TYPE_LOAD = 1;

    static Context context;
    List<DocumentList> documentLists;
    OnLoadMoreListener loadMoreListener;
    boolean isLoading = false, isMoreDataAvailable = true;

    /*
    * isLoading - to set the remote loading and complete status to fix back to back load more call
    * isMoreDataAvailable - to set whether more data from server available or not.
    * It will prevent useless load more request even after all the server data loaded
    * */


    public DocumentListsAdapter(Context context, List<DocumentList> documentLists) {
        this.context = context;
        this.documentLists = documentLists;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if(viewType== TYPE_DOCUMENTS){
            return new DocumentListsHolder(inflater.inflate(R.layout.list_item_document,parent,false));
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
            ((DocumentListsHolder)holder).bindData(documentLists.get(position));
        }
        //No else part needed as load holder doesn't bind any data
    }

    @Override
    public int getItemViewType(int position) {
        if(documentLists.get(position).getCategory_id() !=999){
            return TYPE_DOCUMENTS;
        }else{
            return TYPE_LOAD;
        }
    }

    @Override
    public int getItemCount() {
        return documentLists.size();
    }

    /* VIEW HOLDERS */

    static class DocumentListsHolder extends RecyclerView.ViewHolder{
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
        CardView cardView;
        public DocumentListsHolder(View v) {
            super(v);
            cardView = (CardView) v.findViewById(R.id.list_item_document_cardview);
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

        void bindData(final DocumentList documentLists){
            category_id.setText(""+documentLists.getCategory_id());
            reason.setText(documentLists.getReason());
            Title.setText(documentLists.getTitle());
            company.setText(documentLists.getCompany());
            original_from.setText(documentLists.getOriginal_from());
            rgsde.setText(documentLists.getRgsde());
            readed_count.setText(""+documentLists.getReaded_count());
            rated_count.setText(String.format("%.1f",documentLists.getRated_count()));
            // Glide.with(context).load(R.drawable.visit_background).into(visit);

            Glide.with(context)
                    .load(documentLists.getImg_slr())
                    .apply(new RequestOptions()
                            //.placeholder(R.drawable.food_background)
                            .centerCrop()
                            .error(R.drawable.medical_background))
                    .into(imageView);

            cardView.setOnClickListener(new View.OnClickListener() {
                Intent intent;
                @Override
                public void onClick(View view) {
                    intent =  new Intent(context, DocumentActivity.class);
                    context.startActivity(intent);
                    Snackbar.make(view, "클릭한 slr num : " + documentLists.getDocument_slr(), Snackbar.LENGTH_SHORT).setAction("확인", new View.OnClickListener() {

                        @Override
                        public void onClick(View view) {

                        }
                    }).show();
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

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}