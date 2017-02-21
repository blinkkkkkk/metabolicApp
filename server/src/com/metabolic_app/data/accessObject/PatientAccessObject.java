package com.metabolic_app.data.accessObject;

import com.metabolic_app.data.Clinician;
import com.metabolic_app.data.Patient;
import com.metabolic_app.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 14/02/17.
 */
public class PatientAccessObject extends UserAccessObject{
    @Override
    protected Patient read(ResultSet rs) throws SQLException {
        Patient patient = new Patient();
        String username = rs.getString("username");
        String password = rs.getString("password_hash");
        Long id = rs.getLong("id");
        Long clinician_id = rs.getLong("clinician_id");
        String DoB = rs.getString("DoB");
        Long NHS_no = rs.getLong("NHS_no");
        String first_name = rs.getString("first_name");
        String last_name = rs.getString("last_name");

        patient.setUsername(username);
        patient.setPassword(password);
        patient.setId(id);
        patient.setClinician_id(clinician_id);
        patient.setDoB(DoB);
        patient.setNHS_No(NHS_no);
        patient.setFirst_name(first_name);
        patient.setLast_name(last_name);

        return patient;
    }

    @Override
    public void update(User user) throws SQLException {
        assert user instanceof Patient;
        update(user, "patient");
    }

    @Override
    public void create(User user) throws SQLException {
        assert user instanceof Patient;
        Patient patient = (Patient) user;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "insert into clinician (username, first_name, last_name, password_hash, DoB, NHS_no) values (?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, patient.getUsername());
            statement.setString(2, patient.getFirst_name());
            statement.setString(3,patient.getLast_name());
            statement.setString(4, patient.getPassword());
            statement.setString(5,patient.getDoB());
            statement.setLong(6, patient.getNHS_No());
            statement.executeUpdate();
        } finally {
            close(statement, connection);
        }

    }

    @Override
    public void delete(User user) throws SQLException {
        delete(user, "patient");
    }

    @Override
    public Patient find(Long id) throws SQLException {
        return (Patient) find(id, "patient");
    }

    public List<Patient> getAuthorizedPatients(Long id) throws SQLException {
        ArrayList<Patient> authorized_patient = new ArrayList<>();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            String sql = "select * from patient where clinician_id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id);
            rs = statement.executeQuery();
            while(rs.next()) {
                Patient patient = read(rs);
                authorized_patient.add(patient);
            }
            return authorized_patient;
        } finally {
            close(rs, statement, connection);
        }
    }

    public boolean checkAuthorized(Long clinician_id, Long patient_id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            String sql = "select count(*) from patient where id=? and clinician_id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,patient_id);
            statement.setLong(2,clinician_id);
            rs = statement.executeQuery();
            rs.next();
            Long count = rs.getLong(1);
            assert count == 1 || count == 0;
            if(count == 1) return true;
            return false;
        } finally {
            close(rs, statement, connection);
        }
    }


    public boolean hasClinician(Long patient_id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            String sql = "select * from patient where id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1, patient_id);
            rs = statement.executeQuery();
            while(rs.next()) {
                rs.getLong("clinician_id");
                return !rs.wasNull();
            }
        } finally {
            close(rs, statement, connection);
        }
        return false;
    }


    public void updatePatientClinician(Long clinician_id, Long patient_id) throws SQLException {
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            String sql = "update patient set clinician_id=? where id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,clinician_id);
            statement.setLong(2, patient_id);
            statement.executeUpdate();
        } finally {
            close(statement, connection);
        }
    }


    @Override
    public Patient findByUsername(String username) throws SQLException {
        return (Patient) findByUsername(username, "patient");
    }
}
