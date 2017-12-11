package net.uprin.mayiuseit.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by uPrin on 2017. 12. 11..
 */

public class SubscribeListConfigResponse {

    public SubscribeListConfigResponse(Boolean foods, Boolean machines, Boolean cosmetics, Boolean waters, Boolean livestocks, Boolean aborads, Boolean medicals, Boolean vehicles,Boolean medicines, String rgsde) {
        this.foods = foods;
        this.machines = machines;
        this.cosmetics = cosmetics;
        this.waters = waters;
        this.livestocks = livestocks;
        this.aborads = aborads;
        this.medicals = medicals;
        this.vehicles = vehicles;
        this.medicines =medicines;
        this.rgsde = rgsde;
    }

    @SerializedName("foods")
    private Boolean foods;
    @SerializedName("machines")
    private Boolean machines;
    @SerializedName("cosmetics")
    private Boolean cosmetics;
    @SerializedName("waters")
    private Boolean waters;
    @SerializedName("livestocks")
    private Boolean livestocks;
    @SerializedName("aborads")
    private Boolean aborads;
    @SerializedName("medicals")
    private Boolean medicals;
    @SerializedName("vehicles")
    private Boolean vehicles;
    @SerializedName("medicines")
    private Boolean medicines;
    @SerializedName("rgsde")
    private String rgsde;

    public Boolean getMedicines() {
        return medicines;
    }

    public void setMedicines(Boolean medicines) {
        this.medicines = medicines;
    }

    public Boolean getFoods() {
        return foods;
    }

    public void setFoods(Boolean foods) {
        this.foods = foods;
    }

    public Boolean getMachines() {
        return machines;
    }

    public void setMachines(Boolean machines) {
        this.machines = machines;
    }

    public Boolean getCosmetics() {
        return cosmetics;
    }

    public void setCosmetics(Boolean cosmetics) {
        this.cosmetics = cosmetics;
    }

    public Boolean getWaters() {
        return waters;
    }

    public void setWaters(Boolean waters) {
        this.waters = waters;
    }

    public Boolean getLivestocks() {
        return livestocks;
    }

    public void setLivestocks(Boolean livestocks) {
        this.livestocks = livestocks;
    }

    public Boolean getAborads() {
        return aborads;
    }

    public void setAborads(Boolean aborads) {
        this.aborads = aborads;
    }

    public Boolean getMedicals() {
        return medicals;
    }

    public void setMedicals(Boolean medicals) {
        this.medicals = medicals;
    }

    public Boolean getVehicles() {
        return vehicles;
    }

    public void setVehicles(Boolean vehicles) {
        this.vehicles = vehicles;
    }

    public String getRgsde() {
        return rgsde;
    }

    public void setRgsde(String rgsde) {
        this.rgsde = rgsde;
    }
}
