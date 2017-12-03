package net.uprin.mayiuseit.model;

import java.util.List;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJS on 2017-11-08.
 */

public class DocumentListResponse {
    @SerializedName("page")
    private int page;
    @SerializedName("results")
    private List<DocumentList> results;
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

    public List<DocumentList> getResults() {
        return results;
    }

    public void setResults(List<DocumentList> results) {
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
