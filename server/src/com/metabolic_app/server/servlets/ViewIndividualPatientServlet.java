package com.metabolic_app.server.servlets;

import com.metabolic_app.data.Patient;
import com.metabolic_app.data.accessObject.PatientAccessObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by root on 15/02/17.
 */
public class ViewIndividualPatientServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    public RequestDispatcher view_individual_jsp = null;
    public RequestDispatcher select_domain_jsp = null;

    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        view_individual_jsp = context.getRequestDispatcher("/WEB-INF/jsp/view_patient_options.jsp");
        select_domain_jsp = context.getRequestDispatcher("/WEB-INF/jsp/select_patient_domain.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Long patient_id = Long.valueOf(req.getParameter("id"));
        Long clinician_id = (Long) req.getSession().getAttribute("userId");

        Patient patient;
        try {
            patient = new PatientAccessObject().find(patient_id);
        } catch (SQLException e) {
            logger.severe("Could not retrieve patient from request, redirecting to home: " + e);
            resp.sendRedirect("/home");
            return;
        }

        req.setAttribute("patient", patient);
        view_individual_jsp.forward(req, resp);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        select_domain_jsp.forward(req, resp);
    }
}
