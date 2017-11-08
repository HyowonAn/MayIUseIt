package net.uprin.mayiuseit.rest;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by CJS on 2017-11-08.
 */

public class DocumentList {

    @SerializedName("document_slr")
    private int document_slr;
    @SerializedName("category_id")
    private int category_id;
    @SerializedName("title")
    private String title;
    @SerializedName("reason")
    private String reason;
    @SerializedName("company")
    private String company;
    @SerializedName("img_slr")
    private String img_slr;
    @SerializedName("original_from")
    private String original_from;
    @SerializedName("rgsde")
    private String rgsde;
    @SerializedName("readed_count")
    private int readed_count;
    @SerializedName("rated_count")
    private int rated_count;

    public DocumentList(int document_slr, int category_id, String title, String reason, String company, String img_slr, String original_from, String rgsde, int readed_count, int rated_count) {
        this.document_slr = document_slr;
        this.category_id = category_id;
        this.title = title;
        this.reason = reason;
        this.company = company;
        this.img_slr = img_slr;
        this.original_from = original_from;
        this.rgsde = rgsde;
        this.readed_count = readed_count;
        this.rated_count = rated_count;
    }

    public int getDocument_slr() {
        return document_slr;
    }

    public void setDocument_slr(int document_slr) {
        this.document_slr = document_slr;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getImg_slr() {
        return img_slr;
    }

    public void setImg_slr(String img_slr) {
        this.img_slr = img_slr;
    }

    public String getOriginal_from() {
        return original_from;
    }

    public void setOriginal_from(String original_from) {
        this.original_from = original_from;
    }

    public String getRgsde() {
        return rgsde;
    }

    public void setRgsde(String rgsde) {
        this.rgsde = rgsde;
    }

    public int getReaded_count() {
        return readed_count;
    }

    public void setReaded_count(int readed_count) {
        this.readed_count = readed_count;
    }

    public int getRated_count() {
        return rated_count;
    }

    public void setRated_count(int rated_count) {
        this.rated_count = rated_count;
    }
}