package net.uprin.mayiuseit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by CJS on 2017-11-21.
 */

public class DocumentResponse {

    @SerializedName("state")
    private int state;
    @SerializedName("description")
    private String description;
    @SerializedName("results")
    public List<Document> results;

    public DocumentResponse(int state, String description, List<Document> results) {
        this.state = state;
        this.description = description;
        this.results = results;
    }

    public List<Document> getResults() {
        return results;
    }

    public void setResults(List<Document> results) {
        this.results = results;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
