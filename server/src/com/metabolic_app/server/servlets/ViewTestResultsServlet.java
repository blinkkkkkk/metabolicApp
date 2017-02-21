package com.metabolic_app.server.servlets;

import com.metabolic_app.data.*;
import com.metabolic_app.data.accessObject.InboxAccessObject;
import com.metabolic_app.data.accessObject.PatientAccessObject;
import com.metabolic_app.data.accessObject.TestAccessObject;

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
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by root on 15/02/17.
 */
public class ViewTestResultsServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    private RequestDispatcher selection_page_jsp = null, test_result_jsp = null;

    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        selection_page_jsp = context.getRequestDispatcher("/WEB-INF/jsp/select_patient_domain.jsp");
        test_result_jsp = context.getRequestDispatcher("/WEB-INF/jsp/view_test_results.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long patient_id = Long.valueOf(req.getParameter("id"));
        TestType test_type = TestType.toTest(req.getParameter("domain"));
        System.out.println(req.getParameter("domain"));
        Patient patient;
        try {
            patient = new PatientAccessObject().find(patient_id);
        } catch (SQLException e) {
            logger.severe("Could not retrieve patient from id:" + e);
            resp.sendRedirect("/home");
            return;
        }

        if(test_type == null) {
            // Means an invalid choice was made.
            selection_page_jsp.forward(req, resp);
            return;
        }
        List<TestResult> testResults;
        try {
            testResults = new TestAccessObject().getTestResults(test_type, patient_id);
        } catch (SQLException e) {
            logger.severe("Recorded error when attempting to view test results: " + e);
            resp.sendRedirect("/home");
            return;
        }
        req.setAttribute("testResults", testResults);
        req.setAttribute("domain", test_type.toString());
        req.setAttribute("patient", patient);
        test_result_jsp.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
