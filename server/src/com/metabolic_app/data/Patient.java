package com.metabolic_app.data;

import java.math.BigInteger;

/**
 * Created by root on 14/02/17.
 */
public class Patient extends User {
    private String DoB;
    private Long NHS_No;
    private Long clinician_id;
    private String date_created;
    private String first_name;
    private String last_name;

    public String getDoB() {
        return DoB;
    }

    public void setDoB(String doB) {
        DoB = doB;
    }

    public Long getNHS_No() {
        return NHS_No;
    }

    public void setNHS_No(Long NHS_No) {
        this.NHS_No = NHS_No;
    }

    public Long getClinician_id() {
        return clinician_id;
    }

    public void setClinician_id(Long clinician_id) {
        this.clinician_id = clinician_id;
    }

    public String getDate_created() {
        return date_created;
    }

    public void setDate_created(String date_created) {
        this.date_created = date_created;
    }

    public String getFirst_name() {return first_name;}

    public void setFirst_name(String first_name) {this.first_name = first_name;}

    public String getLast_name() {return last_name;}

    public void setLast_name(String last_name) {this.last_name = last_name;}
}
