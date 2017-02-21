package com.metabolic_app.server.servlets;

import com.metabolic_app.data.Clinician;
import com.metabolic_app.data.accessObject.ClinicianAccessObject;
import com.metabolic_app.data.accessObject.DataAccessObject;
import com.metabolic_app.security.SecureDigester;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by root on 14/02/17.
 */
public class LoginServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(String.valueOf(LoginServlet.class));
    private RequestDispatcher loginjsp = null;

    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        loginjsp = context.getRequestDispatcher("/WEB-INF/jsp/login/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String username = request.getParameter("username");
        ClinicianAccessObject DAO = new ClinicianAccessObject();
        System.out.println(DataAccessObject.getDataSource());
        Clinician clinician = DAO.findByUsername(username);
        System.out.println("2.");
        if(clinician == null) {
            logger.log(Level.INFO,"Recorded Failed Login Attempt.");
            request.setAttribute("message", "Authentication Failed - Error has been recorded");
            try {
                loginjsp.forward(request, response);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Couldn't redirect to login page: " + e);
            }
            return;
        }

        String password = request.getParameter("password");

        if(password == null) {
            logger.log(Level.INFO, "Recorded Failed Login Attempt.");
            request.setAttribute("message", "Authentication Failed - No password Supplied");
            try {
                loginjsp.forward(request, response);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Couldn't redirect to login page: " + e);
            }
            return;
        }

        String passwordDigest = SecureDigester.digest(password);
        if(!clinician.getPassword().equals(passwordDigest)) {
            logger.log(Level.INFO, "Recorded Failed Login Attempt.");
            request.setAttribute("message", "Authentication Failed - Error has been recorded");
            try {
                loginjsp.forward(request, response);
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Couldn't redirect to login page: " + e);
            }
            return;
        }

        HttpSession session = request.getSession();
        Long userId = clinician.getId();
        session.setAttribute("userId", userId);
        logger.log(Level.INFO, "Recorded Successful Authentication");
        String url = "home";
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            logger.log(Level.SEVERE,"Couldn't redirect on successful login: " + e);
        }


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        try {
            loginjsp.forward(req, resp);
        } catch (Exception e) {
            logger.severe("Couldn't redirect to login page: " + e);
        }
    }

}
