package net.uprin.mayiuseit.login;

import com.google.gson.annotations.SerializedName;

/**
 * Created by uPrin on 2017. 11. 1..
 */

public class JoinResponse {
    @SerializedName("state")
    private int state;
    @SerializedName("description")
    private String description;

    public void setState(int state) {
        this.state = state;
    }

    public JoinResponse(int state, String description) {
        this.state = state;
        this.description = description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public String getDescription() {
        return description;
    }
}
