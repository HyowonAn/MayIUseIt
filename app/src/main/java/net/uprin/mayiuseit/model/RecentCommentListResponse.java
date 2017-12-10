package net.uprin.mayiuseit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CJS on 2017-12-10.
 */

public class RecentCommentListResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<RecentCommentList> results;
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

    public List<RecentCommentList> getResults() {
        return results;
    }

    public void setResults(List<RecentCommentList> results) {
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
