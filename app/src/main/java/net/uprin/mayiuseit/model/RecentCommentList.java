package net.uprin.mayiuseit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJS on 2017-12-10.
 */

public class RecentCommentList {

    @SerializedName("document_srl")
    private int document_srl;
    @SerializedName("comment_srl")
    private int comment_srl;
    @SerializedName("category_id")
    private int category_id;
    @SerializedName("title")
    private String title;
    @SerializedName("member_srl")
    private int member_srl;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("rgsde")
    private String rgsde;
    @SerializedName("img_srl")
    private String img_srl;
    @SerializedName("content")
    private String content;
    @SerializedName("rate")
    private float rate;
    @SerializedName("reason")
    private String reason;


    private boolean isLoad=false;
    private boolean visibility=false;

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public RecentCommentList(boolean isLoad) {
        this.isLoad = isLoad;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public int getDocument_srl() {
        return document_srl;
    }

    public void setDocument_srl(int document_srl) {
        this.document_srl = document_srl;
    }

    public int getComment_srl() {
        return comment_srl;
    }

    public void setComment_srl(int comment_srl) {
        this.comment_srl = comment_srl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getMember_srl() {
        return member_srl;
    }

    public void setMember_srl(int member_srl) {
        this.member_srl = member_srl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRgsde() {
        return rgsde;
    }

    public void setRgsde(String rgsde) {
        this.rgsde = rgsde;
    }

    public String getImg_srl() {
        return img_srl;
    }

    public void setImg_srl(String img_srl) {
        this.img_srl = img_srl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
