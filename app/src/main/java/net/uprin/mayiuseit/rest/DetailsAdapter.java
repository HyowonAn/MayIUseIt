//package net.uprin.mayiuseit.rest;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.LinearLayout;
//import android.widget.TextView;
//
//import net.uprin.mayiuseit.R;
//
//import java.util.List;
//
///**
// * Created by CJS on 2017-11-21.
// */
//
//public class DetailsAdapter extends RecyclerView.Adapter<DetailsAdapter.DocumentViewHolder> {
//
//
//    private List<Document> documents;
//    private int rowLayout;
//    private Context context;
//
//
//    public static class DocumentViewHolder extends RecyclerView.ViewHolder {
//        LinearLayout detailsLayout;
//        TextView document_slr;
//        TextView category_id;
//        TextView original_slr;
//        TextView Title;
//        TextView reason;
//        TextView company;
//        TextView company_slr;
//        TextView certification_id;
//        TextView img_slr;
//        TextView company_contact;
//        TextView original_from;
//        TextView original_url;
//        TextView rgsde;
//        TextView readed_count;
//        TextView rated_count;
//
//        public DocumentViewHolder(View v) {
//            super(v);
//            detailsLayout = (LinearLayout) v.findViewById(R.id.details_layout);
//            document_slr = (TextView) v.findViewById(R.id.detail_document_slr);
//            category_id = (TextView) v.findViewById(R.id.detail_category_id);
//            original_slr = (TextView) v.findViewById(R.id.detail_original_slr);
//            reason = (TextView) v.findViewById(R.id.detail_reason);
//            Title = (TextView) v.findViewById(R.id.detail_title);
//            company = (TextView) v.findViewById(R.id.detail_company);
//            company_slr = (TextView) v.findViewById(R.id.detail_company_slr);
//            certification_id = (TextView) v.findViewById(R.id.detail_certification_id);
//            img_slr = (TextView) v.findViewById(R.id.detail_img_slr);
//            company_contact = (TextView) v.findViewById(R.id.detail_company_contact);
//            original_from = (TextView) v.findViewById(R.id.detail_original_from);
//            original_url = (TextView) v.findViewById(R.id.detail_original_url);
//            rgsde = (TextView) v.findViewById(R.id.detail_rgsde);
//            readed_count = (TextView) v.findViewById(R.id.detail_readed_count);
//            rated_count = (TextView) v.findViewById(R.id.detail_rated_count);
//        }
//    }
//
//    public DetailsAdapter(List<Document> documents, int rowLayout, Context context) {
//        this.documents = documents;
//        this.rowLayout = rowLayout;
//        this.context = context;
//    }
//
//    @Override
//    public DocumentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
//        return new DocumentViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(DocumentViewHolder holder, final int position) {
//        holder.document_slr.setText(""+ documents.get(position).getDocument_slr());
//        holder.category_id.setText(""+ documents.get(position).getCategory_id());
//        holder.reason.setText(documents.get(position).getReason());
//        holder.Title.setText(documents.get(position).getTitle());
//        holder.company.setText(documents.get(position).getCompany());
//        holder.img_slr.setText(documents.get(position).getImg_slr());
//        holder.original_from.setText(documents.get(position).getOriginal_from());
//        holder.rgsde.setText(documents.get(position).getRgsde());
//        holder.readed_count.setText(""+ documents.get(position).getReaded_count());
//        holder.rated_count.setText(""+ documents.get(position).getRated_count());
//        holder.original_slr.setText(""+ documents.get(position).getRated_count());
//        holder.company_slr.setText(""+ documents.get(position).getRated_count());
//        holder.certification_id.setText(""+ documents.get(position).getRated_count());
//        holder.company_contact.setText(""+ documents.get(position).getRated_count());
//        holder.original_url.setText(""+ documents.get(position).getRated_count());
//    }
//
//    @Override
//    public int getItemCount() {
//        return documents.size();
//    }
//}