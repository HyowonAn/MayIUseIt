package net.uprin.mayiuseit.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by uPrin on 2017. 12. 7..
 */

public class Rate {

    @SerializedName("rate")
    private float rate;
    @SerializedName("rgsde")
    private String rgsde;


    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getRgsde() {
        return rgsde;
    }

    public void setRgsde(String rgsde) {
        this.rgsde = rgsde;
    }
}
