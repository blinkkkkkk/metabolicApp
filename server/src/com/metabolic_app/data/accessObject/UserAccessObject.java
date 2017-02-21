package com.metabolic_app.data.accessObject;

import com.metabolic_app.data.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by root on 14/02/17.
 */
public abstract class UserAccessObject extends DataAccessObject {

    protected void update(User user, String table) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "update " + table + " set password=? where id=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, user.getPassword());
            statement.setLong(2, user.getId());
            statement.executeUpdate();
        } finally {
            close(statement, connection);
        }
    }

    protected User findByUsername(String username, String table) throws SQLException {
        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "select * from " + table + " where username=?";
            statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            rs = statement.executeQuery();
            if (!rs.next()) {return null;}
            return read(rs);
        } finally {
            close(rs, statement, connection);
        }
    }

    protected void delete(User user, String table) throws SQLException {
        PreparedStatement statement = null;
        Connection connection = null;
        Long UserId = user.getId();
        logger.info("Unused Delete method called.");
        try {
            connection = getConnection();
            String sql = "delete from " + table + "where id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,UserId);
            statement.executeUpdate();
        } finally {
            close(statement, connection);
        }
    }

    protected User find(Long id, String table) throws SQLException {
        ResultSet rs = null;
        PreparedStatement statement = null;
        Connection connection = null;
        try {
            connection = getConnection();
            String sql = "select * from " + table + " where id=?";
            statement = connection.prepareStatement(sql);
            statement.setLong(1,id.longValue());
            rs = statement.executeQuery();
            if(!rs.next()) {return null;}
            return read(rs);
        } finally {
            close(rs, statement, connection);
        }
    }



    protected abstract User read(ResultSet rs) throws SQLException;
    public abstract void update(User user) throws SQLException;
    public abstract void create(User user) throws SQLException;
    public abstract void delete(User user) throws SQLException;
    public abstract User find(Long id) throws SQLException;
    public abstract User findByUsername(String username) throws SQLException;

}
