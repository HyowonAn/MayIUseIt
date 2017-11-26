package net.uprin.mayiuseit.rest;

import com.google.gson.annotations.SerializedName;

/**
 * Created by CJS on 2017-11-26.
 */

public class NoticeList {

    @SerializedName("notice_srl")
    private int notice_srl;
    @SerializedName("title")
    private String title;
    @SerializedName("content")
    private String content;
    @SerializedName("rgsde")
    private String rgsde;
    @SerializedName("nickname")
    private  String nickname;


    private boolean isLoad=false;

    public NoticeList(boolean isLoad) {
        this.isLoad = isLoad;
    }

    public boolean isLoad() {
        return isLoad;
    }

    public void setLoad(boolean load) {
        isLoad = load;
    }

    public int getNotice_srl() {
        return notice_srl;
    }

    public void setNotice_srl(int notice_srl) {
        this.notice_srl = notice_srl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRgsde() {
        return rgsde;
    }

    public void setRgsde(String rgsde) {
        this.rgsde = rgsde;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
