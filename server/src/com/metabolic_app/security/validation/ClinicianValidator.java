package com.metabolic_app.security.validation;

import com.metabolic_app.data.Clinician;

/**
 * Created by root on 14/02/17.
 */
public class ClinicianValidator {
    public static boolean validate(Clinician clinician) {
        //TODO(Kiran): Add Extra validation code for inputs.
        if(clinician.getFull_name() == null) return false;
        if(clinician.getPassword() == null) return false;
        if(clinician.getGMC_no() == null) return false;
        if(clinician.getPractice_no() == null) return false;
        if(clinician.getUsername() == null) return false;

        return true;
    }
}
