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

public class DocumentsAdater extends RecyclerView.Adapter<DocumentsAdater.DocumentViewHolder> {

    private List<DocumentList> documentLists;
    private int rowLayout;
    private Context context;


    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
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

        public DocumentViewHolder(View v) {
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
    }

    public DocumentsAdater(List<DocumentList> documentLists, int rowLayout, Context context) {
        this.documentLists = documentLists;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new DocumentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DocumentViewHolder holder, final int position) {
        holder.category_id.setText(""+documentLists.get(position).getCategory_id());
        holder.reason.setText(documentLists.get(position).getReason());
        holder.Title.setText(documentLists.get(position).getTitle());
        holder.company.setText(documentLists.get(position).getCompany());
        holder.original_from.setText(documentLists.get(position).getOriginal_from());
        holder.rgsde.setText(documentLists.get(position).getRgsde());
        holder.readed_count.setText(""+documentLists.get(position).getReaded_count());
        holder.rated_count.setText(String.format("%.1f",documentLists.get(position).getRated_count()));
       // Glide.with(context).load(R.drawable.visit_background).into(visit);

        Glide.with(context).load(documentLists.get(position).getImg_slr()).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return documentLists.size();
    }
}
