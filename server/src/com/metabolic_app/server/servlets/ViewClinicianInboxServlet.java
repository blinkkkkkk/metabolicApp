package com.metabolic_app.server.servlets;

import com.metabolic_app.data.InboxEntry;
import com.metabolic_app.data.Patient;
import com.metabolic_app.data.TestResult;
import com.metabolic_app.data.accessObject.InboxAccessObject;
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
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by root on 15/02/17.
 */
public class ViewClinicianInboxServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    private RequestDispatcher view_inbox_jsp = null;

    public void  init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        view_inbox_jsp = context.getRequestDispatcher("/WEB-INF/jsp/view_personal_inbox.jsp");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long clinician_id = (Long) req.getSession().getAttribute("userId");
        List<InboxEntry> inboxEntries;
         try {
             inboxEntries = new InboxAccessObject().getClinicianInboxEntries(clinician_id);
         } catch (SQLException e) {
             logger.severe("Error fetching inbox entries for clinician");
             resp.sendRedirect("/home");
             return;
         }

         for(InboxEntry entry : inboxEntries) {
             Long patient_id = entry.getPatient_id();
             Patient patient = null;
             try {
                 patient = new PatientAccessObject().find(patient_id);
                 entry.setName(patient.getFirst_name() + ", " +  patient.getLast_name());
             } catch (SQLException e) {
                logger.severe("Error fetching patient from entry result:" + e);
             }
         }

        req.setAttribute("inboxEntries", inboxEntries);
        view_inbox_jsp.forward(req, resp);
        return;
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
