package com.metabolic_app.data.accessObject;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by root on 13/02/17.
 */
public class DataAccessObject {
    private static DataSource dataSource;
    protected static Logger logger = Logger.getLogger(String.valueOf(DataAccessObject.class));

    public static void main(String[] args) {logger.log(Level.INFO,"Server Logging Initialized");}

    // A lock to facilitate concurrent requests
    private static Object idLock = new Object();

    public static void setDataSource(DataSource dataSource) {DataAccessObject.dataSource = dataSource;}

    public static DataSource getDataSource() {return dataSource;}

    protected static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.toString());
        }
        return null;
    }

    protected static void close(Statement statement, Connection connection) {close(null, statement, connection);}

    protected static void close(ResultSet rs, Statement statement, Connection connection) {
        try {
            if(rs != null) rs.close();
            if(statement != null) statement.close();
            if(connection != null) connection.close();
        } catch (SQLException e) {
            logger.log(Level.WARNING, e.toString());
        }
    }

}
