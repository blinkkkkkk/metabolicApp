package com.metabolic_app.data;

import javax.naming.InvalidNameException;
import java.sql.Blob;

/**
 * Created by root on 15/02/17.
 */
public class InboxEntry {
    private Long id;
    private Long clinician_id;
    private Long patient_id;
    private EntryType entryType;
    private Blob image;
    private String name;

    public InboxEntry(String entrytype) throws InvalidNameException {
        EntryType type = EntryType.toEntry(entrytype);
        if(type == null)
        {
            throw new InvalidNameException("Invalid entry type passed to create inbox entry: " + type);
        }
        this.entryType = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClinician_id() {
        return clinician_id;
    }

    public void setClinician_id(Long clinician_id) {
        this.clinician_id = clinician_id;
    }

    public Long getPatient_id() {
        return patient_id;
    }

    public void setPatient_id(long patient_id) {
        this.patient_id = patient_id;
    }

    public EntryType getEntryType() {
        return entryType;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}

