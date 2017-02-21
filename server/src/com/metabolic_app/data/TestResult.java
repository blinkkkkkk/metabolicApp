package com.metabolic_app.data;

import javax.naming.InvalidNameException;
import java.sql.Date;
import java.sql.Timestamp;

/**
 * Created by root on 15/02/17.
 */
public class TestResult {

    private TestType type;
    private long glucose_level, ECG, BMI, HbA1c, liver;
    private String message;
    private Date next_test;
    java.sql.Timestamp date_created;
    private long clinician_id, patient_id;

    public TestResult(String test_type) throws InvalidNameException {
        TestType new_type = TestType.toTest(test_type);
        if(new_type != null){
            this.type = new_type;
        }
        else {
            throw new InvalidNameException("Type not found");
        }
        return;
    }

    public String getTableName() {
        return this.type.toString();
    }

    public TestType getType() {
        return this.type;
    }


    public long getGlucose_level() {
        return glucose_level;
    }

    public void setGlucose_level(long glucose_level) {
        this.glucose_level = glucose_level;
    }

    public long getECG() {
        return ECG;
    }

    public void setECG(long ECG) {
        this.ECG = ECG;
    }

    public long getBMI() {
        return BMI;
    }

    public void setBMI(long BMI) {
        this.BMI = BMI;
    }

    public long getHbA1c() {
        return HbA1c;
    }

    public void setHbA1c(long hbA1c) {
        HbA1c = hbA1c;
    }

    public long getLiver() {
        return liver;
    }

    public void setLiver(long liver) {
        this.liver = liver;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getNext_test() {
        return next_test;
    }

    public void setNext_test(Date next_test) {
        this.next_test = next_test;
    }
    public Timestamp getDate_created() {
        return date_created;
    }

    public void setDate_created(Timestamp date_created) {
        this.date_created = date_created;
    }

    public long getClinician_id() {
        return clinician_id;
    }

    public void setClinician_id(long clinician_id) {
        this.clinician_id = clinician_id;
    }

    public long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }


}
