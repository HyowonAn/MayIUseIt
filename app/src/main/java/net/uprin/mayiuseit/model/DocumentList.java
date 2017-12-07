package net.uprin.mayiuseit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJS on 2017-11-08.
 */

public class DocumentList {

    @SerializedName("document_srl")
    private int document_srl;
    @SerializedName("category_id")
    private int category_id;
    @SerializedName("title")
    private String title;
    @SerializedName("reason")
    private String reason;
    @SerializedName("company")
    private String company;
    @SerializedName("img_srl")
    private String img_srl;
    @SerializedName("original_from")
    private String original_from;
    @SerializedName("rgsde")
    private String rgsde;
    @SerializedName("readed_count")
    private int readed_count;
    @SerializedName("rated_count")
    private float rated_count;

    public DocumentList(int category_id) {
        this.category_id=category_id;
    }

    public int getDocument_srl() {
        return document_srl;
    }

    public void setDocument_srl(int document_srl) {
        this.document_srl = document_srl;
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

    public String getImg_srl() {
        return img_srl;
    }

    public void setImg_srl(String img_srl) {
        this.img_srl = img_srl;
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

    public float getRated_count() {
        return rated_count;
    }

    public void setRated_count(float rated_count) {
        this.rated_count = rated_count;
    }
}