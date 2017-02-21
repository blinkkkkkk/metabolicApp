package com.metabolic_app.server.servlets;

import com.metabolic_app.data.EntryType;
import com.metabolic_app.data.InboxEntry;
import com.metabolic_app.data.Patient;
import com.metabolic_app.data.accessObject.InboxAccessObject;
import com.metabolic_app.data.accessObject.PatientAccessObject;

import javax.naming.InvalidNameException;
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
public class ViewEntryServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    public RequestDispatcher view_entry_jsp = null;

    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        view_entry_jsp = context.getRequestDispatcher("/WEB-INF/jsp/view_inbox_entry.jsp");
            }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long patient_id = Long.valueOf(req.getParameter("id"));
        Long entry_id = Long.valueOf(req.getParameter("entry_id"));

        Patient patient = null;
        try {
            patient = new PatientAccessObject().find(patient_id);
        } catch(SQLException e) {
            logger.severe("Could not retrieve patient from inbox entry, redirecting to home: " + e);
            resp.sendRedirect("/home");
            return;
        }

        InboxEntry inboxEntry = null;
        try {
            inboxEntry = new InboxAccessObject().getRequest(entry_id);
        } catch (InvalidNameException | SQLException e) {
            logger.severe("Could not get inbox entry, redirecting to home:" + e);
        }
        if(inboxEntry.getEntryType() != EntryType.REQUEST) {
            if(req.getContextPath().equals("/view-inbox-request")) {
                resp.sendRedirect("/unauthorized-access");
                return;
            }
        }


        req.setAttribute("patient", patient);
        req.setAttribute("entry", inboxEntry);
        view_entry_jsp.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String selection = req.getParameter("selection");
        Long clinician_id = (Long) req.getSession().getAttribute("userId");
        Long patient_id = Long.valueOf(req.getParameter("id"));
        Long entry_id = Long.valueOf(req.getParameter("entry_id"));
        if(selection.equals("accept")) {
            InboxEntry entry = null;
            try {
                InboxAccessObject datasource = new InboxAccessObject();
                entry = datasource.getRequest(entry_id);

                if(entry.getEntryType() != EntryType.REQUEST) {
                    if(req.getContextPath().equals("/view-inbox-request")) {
                        resp.sendRedirect("/unauthorized-access");
                        return;
                    }
                }

                if(entry.getEntryType() == EntryType.SUBMISSION) {
                    resp.sendRedirect("/viewinbox");
                    return;
                }
                if(!entry.getClinician_id().equals(clinician_id)) {
                    logger.warning("Retrieved entry does not match up with clinician making the query.");
                    resp.sendRedirect("/viewinbox");
                    return;
                }
                if(!entry.getPatient_id().equals(patient_id)) {
                    logger.warning("Retrieved entry does not match up with clinician making the query.");
                    resp.sendRedirect("/viewinbox");
                    return;
                }
                new PatientAccessObject().updatePatientClinician(clinician_id,patient_id);
                datasource.deleteEntry(entry);


            } catch (InvalidNameException | SQLException e) {
                logger.severe("Unknown error occurred when accepting entry:" + e);
                resp.sendRedirect("/viewinbox");
                return;
            }
        }
        else if (selection.equals("reject") || selection.equals("dismiss")) {
            InboxAccessObject datasource = new InboxAccessObject();
            InboxEntry entry = null;
            try {
                entry = datasource.getRequest(entry_id);

                if(!entry.getClinician_id().equals(clinician_id)) {
                    logger.warning("Retrieved entry does not match up with clinician making the query.");
                    resp.sendRedirect("/viewinbox");
                    return;
                }
                if(!entry.getPatient_id().equals(patient_id)) {
                    logger.warning("Retrieved entry does not match up with clinician making the query.");
                    resp.sendRedirect("/viewinbox");
                    return;
                }
                datasource.deleteEntry(entry);

            } catch (SQLException | InvalidNameException e) {
                logger.severe("Unknown error occurred when rejecting entry:" + e);
                resp.sendRedirect("/viewinbox");
                return;
            }
        }
        else {
            logger.warning("Unknown response recieved from client, redirecting back to inbox with no changes: " + selection);
            resp.sendRedirect("/viewinbox");
            return;
        }
        resp.sendRedirect("/viewinbox");
        return;
    }
}

