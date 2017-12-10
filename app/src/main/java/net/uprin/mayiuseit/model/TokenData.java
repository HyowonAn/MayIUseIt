package net.uprin.mayiuseit.model;

/**
 * Created by uPrin on 2017. 12. 3..
 */

public class TokenData {
    private String member_srl="";
    private String email_address="";
    private String nickname="";
    private String isAdmin;
    private String profile_image="";
    public String getMember_srl() {
        return member_srl;
    }

    public void setMember_srl(String member_srl) {
        this.member_srl = member_srl;
    }

    public String getEmail_address() {
        if (email_address==null){
            email_address="이메일이 없습니다";
        }
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(String isAdmin) {
        this.isAdmin = isAdmin;
    }

    public  boolean isAdmin (){
        if (isAdmin.equals("Y")){
            return true;
        } else {
            return false;
        }
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }
}
