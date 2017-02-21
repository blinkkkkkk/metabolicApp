package com.metabolic_app.server.servlets;

import com.metabolic_app.data.Clinician;
import com.metabolic_app.data.Patient;
import com.metabolic_app.data.accessObject.ClinicianAccessObject;
import com.metabolic_app.data.accessObject.PatientAccessObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by root on 14/02/17.
 */
public class GetAuthorizedPatientsServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    private RequestDispatcher authorized_patients_jsp = null;

    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        authorized_patients_jsp = context.getRequestDispatcher("/WEB-INF/jsp/get_authorized_patients.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Long id = Long.valueOf((Long) session.getAttribute("userId"));
        try {
            Clinician clinician = new ClinicianAccessObject().find(id);
            List<Patient> authorizedPatients = new PatientAccessObject().getAuthorizedPatients(id);
            req.setAttribute("clinician", clinician);
            req.setAttribute("patients", authorizedPatients);
            authorized_patients_jsp.forward(req, resp);
        } catch (Exception e) {
            logger.severe("Error when retrieving clinicians for authorized pages: "+ e);
            resp.sendRedirect("home");
            return;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
