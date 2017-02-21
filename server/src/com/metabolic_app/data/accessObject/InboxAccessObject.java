package com.metabolic_app.data.accessObject;

import com.metabolic_app.data.EntryType;
import com.metabolic_app.data.InboxEntry;

import javax.naming.InvalidNameException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 15/02/17.
 */
public class InboxAccessObject extends DataAccessObject{
    private InboxEntry read(ResultSet rs) throws SQLException, InvalidNameException {
        Long id = rs.getLong("id");
        String entrytype = rs.getString("entry_type");
        InboxEntry inboxEntry = new InboxEntry(entrytype);
        Long clinician_id = rs.getLong("clinician_id");
        Long patient_id = rs.getLong("patient_id");
        Blob image = null;
        if(inboxEntry.getEntryType() == EntryType.SUBMISSION) {
            image = rs.getBlob("image");
        }

        inboxEntry.setPatient_id(patient_id);
        inboxEntry.setId(id);
        inboxEntry.setClinician_id(clinician_id);
        inboxEntry.setImage(image);
        return inboxEntry;
    }

    public void deleteEntry(InboxEntry entry) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;

        try {
            connection = getConnection();
            String sqlQuery =  "delete from inbox where id=?";
            statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1,entry.getId());
            statement.executeUpdate();
        }finally {
            close(statement, connection);
        }
    }

    public boolean hasPendingRequest(Long patient_id) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            String sqlQuery =  "select * from inbox where patient_id=? and entry_type=request";
            statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, patient_id);
            rs = statement.executeQuery();
            if(!rs.next()) return false;
            return true;
        }finally {
            close(rs, statement, connection);
        }
    }

    public InboxEntry getPendingRequest(Long patient_id) throws SQLException, InvalidNameException {
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            String sqlQuery =  "select * from inbox where patient_id=? and entry_type=request";
            statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, patient_id);
            rs = statement.executeQuery();
            while(rs.next()) {
                try {
                    return read(rs);
                } catch (SQLException | InvalidNameException e) {
                    logger.severe("Error while parsing inbox results");
                }
            }
        } finally {
            close(rs, statement, connection);
        }
        return null;
    }

    public InboxEntry getRequest(Long entry_id) throws SQLException, InvalidNameException {
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet rs = null;
        try {
            connection = getConnection();
            String sqlQuery =  "select * from inbox where id=?";
            statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, entry_id);
            rs = statement.executeQuery();
            while(rs.next()) {
                try {
                    return read(rs);
                } catch (SQLException | InvalidNameException e) {
                    logger.severe("Error while parsing inbox results");
                }
            }
        } finally {
            close(rs, statement, connection);
        }
        return null;
    }


    public void createRequest(InboxEntry entry) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            String sqlQuery = "insert into inbox (clinician_id, patient_id, entry_type, image) values (?,?,?,?)";
            statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, entry.getClinician_id());
            statement.setLong(2, entry.getPatient_id());
            statement.setString(3, entry.getEntryType().toString());
            if (entry.getEntryType() == EntryType.SUBMISSION) {
                statement.setBlob(4, entry.getImage());
            } else {
                statement.setNull(4, Types.BLOB);
            }
            statement.executeUpdate();
        } finally {
            close(statement, connection);
        }
    }

    public List<InboxEntry> getClinicianInboxEntries(Long clinician_id) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;
        ResultSet rs = null;
        ArrayList<InboxEntry> inboxItems = new ArrayList<>();
        try {
            connection = getConnection();
            String sqlQuery =  "select * from inbox where clinician_id=?";
            statement = connection.prepareStatement(sqlQuery);
            statement.setLong(1, clinician_id);
            rs = statement.executeQuery();
            while(rs.next()) {
                try {
                    InboxEntry entry = read(rs);
                    inboxItems.add(entry);
                } catch (SQLException | InvalidNameException e) {
                    logger.severe("Error while parsing inbox results:" + e);
                }
            }
            return inboxItems;
        } finally {
            close(rs, statement, connection);
        }
    }

}
