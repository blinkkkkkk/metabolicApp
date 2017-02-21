package com.metabolic_app.server.servlets;

import com.metabolic_app.data.Clinician;
import com.metabolic_app.data.accessObject.ClinicianAccessObject;
import com.metabolic_app.security.SecureDigester;
import com.metabolic_app.security.validation.ClinicianValidator;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.logging.Logger;

/**
 * Created by root on 14/02/17.
 */
public class CreateAccountServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    private RequestDispatcher create_account_jsp = null;

    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        create_account_jsp = context.getRequestDispatcher("/WEB-INF/jsp/login/create_account.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        try {
            create_account_jsp.forward(req, resp);
        } catch (Exception e) {
            logger.severe("Could not redirect to create account page: " + e);

            //TODO(Kiran): Add default error message code

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String full_name = (String) req.getParameter("full_name");
        String practice_no = (String) req.getParameter("practice_no");
        String GMC_no = (String) req.getParameter("GMC_no");
        String email = (String) req.getParameter("email");
        String password = (String) req.getParameter("password");
        String passwordHash = SecureDigester.digest(password);
        Clinician clinician = new Clinician();
        clinician.setFull_name(full_name);
        clinician.setPractice_no(practice_no);
        clinician.setGMC_no(GMC_no);
        clinician.setUsername(email);
        clinician.setPassword(passwordHash);

        // If Potential SQL Injection Detected
        if(!ClinicianValidator.validate(clinician)) {
            logger.info("Invalid Clinician Data submitted to create account");
            req.setAttribute("message","Invalid data entered.");
            create_account_jsp.forward(req, resp);
            return;
        }

        try {
            new ClinicianAccessObject().create(clinician);
        } catch (Exception e) {
            logger.severe("Could not create account for clinician in database: " + e);
            req.setAttribute("message", "Unknown error occured, please contact sysadmin");
            create_account_jsp.forward(req, resp);
            return;
        }

        resp.sendRedirect("login");
        return;
    }
}
