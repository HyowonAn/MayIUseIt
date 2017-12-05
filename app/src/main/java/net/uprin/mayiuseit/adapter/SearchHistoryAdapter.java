package net.uprin.mayiuseit.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import net.uprin.mayiuseit.R;
import net.uprin.mayiuseit.model.History;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJS on 2017-12-03.
 */

public class SearchHistoryAdapter extends RecyclerView.Adapter<SearchHistoryAdapter.SearchViewHolder> {

    ArrayList<String> histories;
    private int rowLayout;
    private Context context;

    public static class SearchViewHolder extends RecyclerView.ViewHolder {
        LinearLayout historyLayout;
        TextView history_keyword;


        public SearchViewHolder(View v) {
            super(v);
            historyLayout = (LinearLayout) v.findViewById(R.id.history_recycler_view);
            history_keyword = (TextView) v.findViewById(R.id.history_keyword);
        }
    }

    public SearchHistoryAdapter(ArrayList<String> histories, int rowLayout, Context context) {
        this.histories = histories;
        this.rowLayout = rowLayout;
        this.context = context;
    }

    @Override
    public SearchHistoryAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent,
                                                                    int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new SearchViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {
        holder.history_keyword.setText(histories.get(position));
    }

    @Override
    public int getItemCount() {

        return histories.size();
    }

}
