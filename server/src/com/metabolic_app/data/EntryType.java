package com.metabolic_app.data;

/**
 * Created by root on 15/02/17.
 */
public enum EntryType {
    REQUEST("request"), SUBMISSION("submission");
    private String table_value;

    private EntryType(String table_value) {
        this.table_value = table_value;
    }

    public String toString() {return this.table_value;}

    public static EntryType toEntry(String type) {
        for(EntryType t : EntryType.values()) {
            if(t.toString().equals(type)) return t;
        }
        return null;
    }
}
