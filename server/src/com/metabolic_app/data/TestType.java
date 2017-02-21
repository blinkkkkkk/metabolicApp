package com.metabolic_app.data;

/**
 * Created by root on 15/02/17.
 */
public enum TestType {
    glucose_test("glucose_test"), ECG("ECG"), BMI("BMI"), HbA1c("HbA1c"), liver("liver");
    private final String table_name;

    private TestType(String table_name) {
        this.table_name = table_name;
    }

    public String toString() {
        return this.table_name;
    }

    public static TestType toTest(String s) {
        for (TestType t : TestType.values()) {
            System.out.println("Comparing " + t.toString() + " with " + s);
            if(t.toString().equals(s)) return t;
        }
        return null;
    }
}
