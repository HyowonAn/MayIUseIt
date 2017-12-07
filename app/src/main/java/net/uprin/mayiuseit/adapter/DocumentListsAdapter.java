package net.uprin.mayiuseit.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
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

import net.uprin.mayiuseit.activity.DocumentListActivity;
import net.uprin.mayiuseit.model.DocumentList;
import net.uprin.mayiuseit.util.CategorytoString;
import net.uprin.mayiuseit.activity.DocumentActivity;
import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.util.RatingDialog;

import java.util.List;

/**
 * Created by CJS on 2017-11-08.
 */

public class DocumentListsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int TYPE_LOAD = 1;

    static Context context;
    private static final String TAG = DocumentActivity.class.getSimpleName();

    public final int TYPE_DOCUMENTS = 0;
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
        TextView rated_count,document_rated_textview;
        ImageView imageView, document_rated_imageview;
        LinearLayout document_rating_btn,document_list_share_btn;
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
            document_rated_imageview = (ImageView) v.findViewById(R.id.document_rated_imageview);
            document_rating_btn = (LinearLayout) v.findViewById(R.id.document_rating_btn);
            document_list_share_btn = (LinearLayout) v.findViewById(R.id.document_list_share_btn);
            document_rated_textview = (TextView) v.findViewById(R.id.document_rated_textview);
        }

        void bindData(final DocumentList documentLists){
            category_id.setText(new CategorytoString().intToString(documentLists.getCategory_id()));
            reason.setText(documentLists.getReason());
            Title.setText(documentLists.getTitle());
            company.setText(documentLists.getCompany());
            original_from.setText(documentLists.getOriginal_from());
            rgsde.setText(documentLists.getRgsde());
            readed_count.setText(""+documentLists.getReaded_count());
            rated_count.setText(String.format("%.1f",documentLists.getRated_count()));
            // Glide.with(context).load(R.drawable.visit_background).into(visit);

            if(documentLists.getRate()!=0f){
                document_rated_textview.setText("내 점수 : " + documentLists.getRate());
                document_rated_textview.setTextColor(ContextCompat.getColor(context, R.color .colorAccent));
                document_rated_imageview.setColorFilter(ContextCompat.getColor(context, R.color .colorAccent));
            }

            Glide.with(context).load(documentLists.getImg_srl())
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
                    intent.putExtra("document_srl", documentLists.getDocument_srl());
                    context.startActivity(intent);
                }
            });

            document_rating_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RatingDialog dialog = new RatingDialog(context,documentLists);
                    dialog.show();
                    dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            if(documentLists.getRate()!=0f){
                                document_rated_textview.setText("내 점수 : " + documentLists.getRate());
                                document_rated_textview.setTextColor(ContextCompat.getColor(context, R.color .colorAccent));
                                document_rated_imageview.setColorFilter(ContextCompat.getColor(context, R.color .colorAccent));
                            } else {
                                document_rated_textview.setText("별점 남기기");
                                document_rated_textview.setTextColor(ContextCompat.getColor(context, R.color .colorGreyLight));
                                document_rated_imageview.setColorFilter(ContextCompat.getColor(context, R.color .colorGreyLight));
                            }
                        }
                    });
                }
            });

            document_list_share_btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                    // Set default text message
                    // 카톡, 이메일, MMS 다 이걸로 설정 가능
                    intent.setType("text/plain");
                    String subject = "리콜정보 안내";
                    String text = "리콜정보 : " + documentLists.getTitle() + "\n 리콜사유 : " + documentLists.getReason() + "\n 등록일자 : " + documentLists.getRgsde() + "\n" + documentLists.getImg_srl();
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

    public void setLoadMoreListener(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }
}