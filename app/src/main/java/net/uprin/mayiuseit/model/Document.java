package net.uprin.mayiuseit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJS on 2017-11-20.
 */

public class Document {

    @SerializedName("document_srl")
    private int document_srl;
    @SerializedName("category_id")
    private int category_id;
    @SerializedName("original_srl")
    private int original_srl;
    @SerializedName("title")
    private String title;
    @SerializedName("reason")
    private String reason;
    @SerializedName("detail")
    private String detail;
    @SerializedName("company")
    private String company;
    @SerializedName("company_srl")
    private String company_srl;
    @SerializedName("certification_id")
    private String certification_id;
    @SerializedName("img_srl")
    private String img_srl;
    @SerializedName("company_contact")
    private String company_contact;
    @SerializedName("original_from")
    private String original_from;
    @SerializedName("original_url")
    private String original_url;
    @SerializedName("rgsde")
    private String rgsde;
    @SerializedName("readed_count")
    private int readed_count;
    @SerializedName("rated_count")
    private float rated_count;

    @SerializedName("commented_count")
    private int commented_count;

    public int getCommented_count() {
        return commented_count;
    }

    public void setCommented_count(int commented_count) {
        this.commented_count = commented_count;
    }

    public Document(int document_srl, int category_id, int original_srl, String title, String reason, String detail, String company, String company_srl, String certification_id, String img_srl, String company_contact, String original_from, String original_url, String rgsde, int readed_count, float rated_count, int commented_count) {
        this.document_srl = document_srl;
        this.category_id = category_id;
        this.original_srl = original_srl;
        this.title = title;
        this.reason = reason;
        this.detail = detail;
        this.company = company;
        this.company_srl = company_srl;
        this.certification_id = certification_id;
        this.img_srl = img_srl;
        this.company_contact = company_contact;
        this.original_from = original_from;
        this.original_url = original_url;
        this.rgsde = rgsde;
        this.readed_count = readed_count;
        this.rated_count = rated_count;
        this.commented_count = commented_count;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public int getOriginal_srl() {
        return original_srl;
    }

    public void setOriginal_srl(int original_srl) {
        this.original_srl = original_srl;
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

    public String getCompany_srl() {
        return company_srl;
    }

    public void setCompany_srl(String company_srl) {
        this.company_srl = company_srl;
    }

    public String getCertification_id() {
        return certification_id;
    }

    public void setCertification_id(String certification_id) {
        this.certification_id = certification_id;
    }

    public String getImg_srl() {
        return img_srl;
    }

    public void setImg_srl(String img_srl) {
        this.img_srl = img_srl;
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

    public float getRated_count() {
        return rated_count;
    }

    public void setRated_count(float rated_count) {
        this.rated_count = rated_count;
    }

}