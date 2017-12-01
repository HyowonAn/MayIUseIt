package net.uprin.mayiuseit.rest;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CJS on 2017-12-02.
 */

public class SearchListResponse {

    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<SearchList> results;
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

    public List<SearchList> getResults() {
        return results;
    }

    public void setResults(List<SearchList> results) {
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
