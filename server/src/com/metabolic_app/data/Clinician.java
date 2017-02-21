package com.metabolic_app.data;

/**
 * Created by root on 14/02/17.
 */
public class Clinician extends User {
    private String GMC_no;
    private String practice_no;
    private String full_name;

    public String getGMC_no() {
        return GMC_no;
    }

    public void setGMC_no(String GMC_no) {
        this.GMC_no = GMC_no;
    }

    public String getPractice_no() {
        return practice_no;
    }

    public void setPractice_no(String practice_no) {
        this.practice_no = practice_no;
    }

    public String getFull_name() {return full_name;}

    public void setFull_name(String fullname) {this.full_name = fullname;}
}
