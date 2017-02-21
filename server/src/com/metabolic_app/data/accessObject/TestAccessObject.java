package com.metabolic_app.data.accessObject;

import com.metabolic_app.data.TestResult;
import com.metabolic_app.data.TestType;

import javax.naming.InvalidNameException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15/02/17.
 */
public class TestAccessObject extends DataAccessObject {
    protected TestResult read(ResultSet rs) throws SQLException, InvalidNameException {
        TestResult testResult;
        String type = rs.getString("test_type");
        testResult = new TestResult(type);
        switch(testResult.getType()) {
            case glucose_test:
                String glucose_test = rs.getString("glucose_level");
                testResult.setGlucose_level(Long.parseLong(glucose_test));
                break;
            case ECG:
                String ECG_test = rs.getString("ECG");
                testResult.setECG(Long.parseLong(ECG_test));
                break;
            case BMI:
                String BMI_test = rs.getString("BMI_test");
                testResult.setBMI(Long.parseLong(BMI_test));
                break;
            case HbA1c:
                String HbA1c_test = rs.getString("HbA1c");
                testResult.setHbA1c(Long.parseLong(HbA1c_test));
                break;
            case liver:
                String liver_test = rs.getString("liver");
                testResult.setLiver(Long.parseLong(liver_test));
                break;
        }
        String message = rs.getString("message");
        testResult.setMessage(message);
        Date next_test = rs.getDate("next_test");
        testResult.setNext_test(next_test);
        Timestamp date_created = rs.getTimestamp("date_created");
        testResult.setDate_created(date_created);
        Long clinician_id = rs.getLong("clinician_id");
        Long patient_id = rs.getLong("patient_id");
        testResult.setPatient_id(patient_id);
        testResult.setClinician_id(clinician_id);
        return testResult;
    }

    public void create(TestResult testResult) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "insert into test_result (test_type, glucose_level," +
                    " ECG, BMI, HbA1c, liver, clinician_id, patient_id, next_test," +
                    " message) values (?,?,?,?,?,?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1,testResult.getTableName());
            switch (testResult.getType()) {
                case glucose_test:
                    statement.setLong(2, testResult.getGlucose_level());
                    statement.setNull(3, Types.INTEGER);
                    statement.setNull(4, Types.INTEGER);
                    statement.setNull(5, Types.INTEGER);
                    statement.setNull(6, Types.INTEGER);
                    break;
                case ECG:
                    statement.setNull(2, Types.INTEGER);
                    statement.setLong(3, testResult.getECG());
                    statement.setNull(4, Types.INTEGER);
                    statement.setNull(5, Types.INTEGER);
                    statement.setNull(6, Types.INTEGER);
                    break;
                case BMI:
                    statement.setNull(2, Types.INTEGER);
                    statement.setNull(3, Types.INTEGER);
                    statement.setLong(4, testResult.getBMI());
                    statement.setNull(5, Types.INTEGER);
                    statement.setNull(6, Types.INTEGER);
                    break;
                case HbA1c:
                    statement.setNull(2, Types.INTEGER);
                    statement.setNull(3, Types.INTEGER);
                    statement.setNull(4, Types.INTEGER);
                    statement.setLong(5, testResult.getHbA1c());
                    statement.setNull(6, Types.INTEGER);
                    break;
                case liver:
                    statement.setNull(2, Types.INTEGER);
                    statement.setNull(3, Types.INTEGER);
                    statement.setNull(4, Types.INTEGER);
                    statement.setNull(5, Types.INTEGER);
                    statement.setLong(6, testResult.getHbA1c());
                    break;
            }
            statement.setLong(7, testResult.getClinician_id());
            statement.setLong(8, testResult.getPatient_id());
            if(testResult.getNext_test() != null)
                statement.setDate(9, testResult.getNext_test());
            else statement.setNull(9, Types.DATE);
            statement.setString(10, testResult.getMessage());
            statement.executeUpdate();
        } finally {
            close(statement, connection);
        }
    }

    public List<TestResult> getTestResults(TestType type, Long patient_id) throws SQLException {
        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        ArrayList<TestResult> test_results = new ArrayList<>();
        try {
            connection = getConnection();
            String sql = "select * from test_result where patient_id=? and test_type=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,patient_id);
            statement.setString(2, type.toString());
            rs = statement.executeQuery();
            while(rs.next()) {
                TestResult result = null;
                try {
                    result = read(rs);
                    test_results.add(result);
                } catch (InvalidNameException e) {
                    logger.warning("Could not retrieve result set with exception: " + e);
                }
            }
            return test_results;
        } finally {
            close(rs, statement, connection);
        }
    }
}
