package net.uprin.mayiuseit.login;

/**
 * Created by uPrin on 2017. 11. 1..
 */

public class JoinRequest {
    private String email;
    private String password;
    private String uprinkey;

    public JoinRequest(String email, String password, String uprinkey) {
        this.email = email;
        this.password = password;
        this.uprinkey = uprinkey;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUprinkey() {
        return uprinkey;
    }

    public void setUprinkey(String uprinkey) {
        this.uprinkey = uprinkey;
    }
}
