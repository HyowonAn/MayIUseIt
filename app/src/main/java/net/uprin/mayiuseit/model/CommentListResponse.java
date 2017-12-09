package net.uprin.mayiuseit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CJS on 2017-11-08.
 */

public class CommentListResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<CommentList> results;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("total_pages")
    private int totalPages;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public List<CommentList> getResults() {
        return results;
    }

    public void setResults(List<CommentList> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
