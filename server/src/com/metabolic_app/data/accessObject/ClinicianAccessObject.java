package com.metabolic_app.data.accessObject;

import com.metabolic_app.data.Clinician;
import com.metabolic_app.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;

/**
 * Created by Kiran Gopinathan on 14/02/17.
 * Class to handle clinician login requests via the website.
 */
public class ClinicianAccessObject extends UserAccessObject{
    private static ClinicianAccessObject instance = new ClinicianAccessObject();

    public static ClinicianAccessObject getInstance() {return instance;}

    @Override
    protected Clinician read(ResultSet rs) throws SQLException {
        Clinician clinician = new Clinician();
        String username = rs.getString("username");
        String password = rs.getString("password_hash");
        Long id = rs.getLong("id");
        String GMC_no = rs.getString("GMC_no");
        String practice_no = rs.getString("practice_no");
        String full_name = rs.getString("full_name");

        clinician.setUsername(username);
        clinician.setPassword(password);
        clinician.setId(id);
        clinician.setGMC_no(GMC_no);
        clinician.setPractice_no(practice_no);
        clinician.setFull_name(full_name);

        return clinician;
    }

    @Override
    public void create(User user) throws SQLException {
        assert user instanceof Clinician;
        Clinician clinician = (Clinician) user;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "insert into clinician (username, full_name, password_hash, GMC_no, practice_no) values (?,?,?,?,?)";
            statement = connection.prepareStatement(sql);
            statement.setString(1, clinician.getUsername());
            statement.setString(2, clinician.getFull_name());
            statement.setString(3, clinician.getPassword());
            statement.setString(4,clinician.getGMC_no());
            statement.setString(5, clinician.getPractice_no());
            statement.executeUpdate();
        } finally {
            close(statement, connection);
        }

    }

    @Override
    public void delete(User user) throws SQLException {
        assert user instanceof Clinician;
        delete(user, "clinician");
    }

    @Override
    public Clinician find(Long id) throws SQLException {
        return (Clinician) find(id, "clinician");
    }

    @Override
    public Clinician findByUsername(String username){
        if(username == null) return null;
        try {
            return (Clinician) findByUsername(username, "clinician");
        } catch (SQLException e) {
            logger.log(Level.WARNING,"SQL Error on FindbyUsername" + e);
            return null;
        }
    }



    @Override
    public void update(User user) throws SQLException {
        assert user instanceof Clinician;
        update(user, "clinician");
    }

}
