package com.metabolic_app.server;

import com.metabolic_app.data.accessObject.DataAccessObject;

import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by root on 14/02/17.
 */
public class Init implements ServletContextListener {
    private Logger logger = Logger.getLogger(String.valueOf(ServletContextListener.class));
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("INITIALIZING");
        logger.info("INITIALIZING");
        logger.log(Level.INFO,"Initializing Script Run");
        java.util.logging.Logger.getAnonymousLogger().log(Level.INFO, "Initialized - NON MAIN LOGGER");
        ServletContext servletContext = servletContextEvent.getServletContext();
        try {
            setDataSource(servletContext);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Could not initialize Data source");
            throw new RuntimeException(e);
        }
        logger.log(Level.SEVERE,"Initialization Success");
    }

    private void setDataSource(ServletContext servletContext) throws Exception {
        InitialContext initialContext = new InitialContext();
        servletContext.log("Retrieving DataSource");
        Context compContext = (Context) initialContext.lookup("java:/comp/env");
        javax.sql.DataSource dataSource = (javax.sql.DataSource) compContext.lookup("datasource");
        System.out.println("" + dataSource);
        DataAccessObject.setDataSource((javax.sql.DataSource) dataSource);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}
