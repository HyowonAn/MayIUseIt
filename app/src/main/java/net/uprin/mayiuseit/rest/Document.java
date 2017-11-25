package net.uprin.mayiuseit.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJS on 2017-11-20.
 */

public class Document {

    @SerializedName("document_slr")
    private int document_slr;
    @SerializedName("category_id")
    private int category_id;
    @SerializedName("original_slr")
    private int original_slr;
    @SerializedName("title")
    private String title;
    @SerializedName("reason")
    private String reason;
    @SerializedName("detail")
    private String detail;
    @SerializedName("company")
    private String company;
    @SerializedName("company_slr")
    private String company_slr;
    @SerializedName("certification_id")
    private String certification_id;
    @SerializedName("img_slr")
    private String img_slr;
    @SerializedName("company_contact")
    private String company_contact;
    @SerializedName("original_from")
    private String original_from;
    @SerializedName("original_url ")
    private String original_url;
    @SerializedName("rgsde")
    private String rgsde;
    @SerializedName("readed_count")
    private int readed_count;
    @SerializedName("rated_count")
    private int rated_count;

    public Document(int document_slr, int category_id, int original_slr, String title, String reason,String detail, String company, String company_slr, String certification_id, String img_slr, String company_contact, String original_from, String original_url, String rgsde, int readed_count, int rated_count) {
        this.document_slr = document_slr;
        this.category_id = category_id;
        this.original_slr = original_slr;
        this.title = title;
        this.reason = reason;
        this.detail = detail;
        this.company = company;
        this.company_slr = company_slr;
        this.certification_id = certification_id;
        this.img_slr = img_slr;
        this.company_contact = company_contact;
        this.original_from = original_from;
        this.original_url = original_url;
        this.rgsde = rgsde;
        this.readed_count = readed_count;
        this.rated_count = rated_count;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public int getOriginal_slr() {
        return original_slr;
    }

    public void setOriginal_slr(int original_slr) {
        this.original_slr = original_slr;
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

    public String getCompany_slr() {
        return company_slr;
    }

    public void setCompany_slr(String company_slr) {
        this.company_slr = company_slr;
    }

    public String getCertification_id() {
        return certification_id;
    }

    public void setCertification_id(String certification_id) {
        this.certification_id = certification_id;
    }

    public String getImg_slr() {
        return img_slr;
    }

    public void setImg_slr(String img_slr) {
        this.img_slr = img_slr;
    }

    public String getCompany_contact() {
        return company_contact;
    }

    public void setCompany_contact(String company_contact) {
        this.company_contact = company_contact;
    }

    public String getOriginal_from() {
        return original_from;
    }

    public void setOriginal_from(String original_from) {
        this.original_from = original_from;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
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