package com.metabolic_app.server.servlets;

import com.metabolic_app.data.Patient;
import com.metabolic_app.data.TestResult;
import com.metabolic_app.data.TestType;
import com.metabolic_app.data.accessObject.PatientAccessObject;
import com.metabolic_app.data.accessObject.TestAccessObject;
import com.metabolic_app.security.validation.TestResultValidator;
import jdk.nashorn.internal.ir.RuntimeNode;

import javax.naming.InvalidNameException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by root on 15/02/17.
 */
public class AddTestResultServlet extends HttpServlet{
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    private RequestDispatcher add_test_jsp = null;
    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        add_test_jsp = context.getRequestDispatcher("/WEB-INF/jsp/add_test_result.jsp");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long patient_id = Long.valueOf(req.getParameter("id"));
        Patient patient;
        try {
            patient = new PatientAccessObject().find(patient_id);
        } catch (SQLException e) {
            logger.severe("Could not retrieve patient from request to insert test results: " + e);
            resp.sendRedirect("/home");
            return;
        }
        req.setAttribute("patient", patient);
        add_test_jsp.forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long patient_id = Long.valueOf(req.getParameter("id"));
        Patient patient;
        try {
            patient = new PatientAccessObject().find(patient_id);
        } catch (SQLException e) {
            logger.severe("Could not retrieve patient from request to insert test results: " + e);
            resp.sendRedirect("/home");
            return;
        }
        req.setAttribute("patient", patient);
        Long clinician_id = (Long) req.getSession().getAttribute("userId");
        String test_type = req.getParameter("domain");
        Long glucose_level = null;
        if(req.getParameter("glucose_level") != null) glucose_level = Long.valueOf(req.getParameter("glucose_level"));
        Long ECG = null;
        if (req.getParameter("ECG") != null) ECG = Long.valueOf(req.getParameter("ECG"));
        Long BMI = null;
        if(req.getParameter("BMI")!=null) BMI = Long.valueOf(req.getParameter("BMI"));
        Long HbA1c = null;
        if(req.getParameter("HbA1c") != null) HbA1c = Long.valueOf(req.getParameter("HbA1c"));
        Long liver = null;
        if(req.getParameter("liver") != null) liver = Long.valueOf(req.getParameter("liver"));
        String message = req.getParameter("message");
        Date next_test = null;
        //if(req.getParameter("next_test")!= null) next_test = Date.valueOf(req.getParameter("next_test"));
        TestResult testResult = null;
        try {
            testResult = new TestResult(test_type);
        } catch (InvalidNameException e) {
            logger.warning("Recorded Invalid Create Test attempt: " + e);
            req.setAttribute("err_message", "Invalid type.");
            add_test_jsp.forward(req, resp);
            return;
        }
        if(glucose_level != null) testResult.setGlucose_level(glucose_level);
        if(ECG != null) testResult.setECG(ECG);
        if(BMI != null)testResult.setBMI(BMI);
        if(HbA1c != null) testResult.setHbA1c(HbA1c);
        if(liver != null) testResult.setLiver(liver);
        if(message != null) testResult.setMessage(message);
        if(next_test != null) testResult.setNext_test(next_test);
        testResult.setClinician_id(clinician_id);
        testResult.setPatient_id(patient_id);

        if(!TestResultValidator.validate(testResult)){
            logger.info("Invalid Test result data sumbitted to insert test");
            req.setAttribute("err_message", "Invalid data entered");
            add_test_jsp.forward(req, resp);
            return;
        }
        try {
            new TestAccessObject().create(testResult);
        } catch (Exception e) {
            logger.severe("Could not create account for clinician in database: " + e);
            req.setAttribute("err_message", "Unknown error occured, please contact sysadmin");
            add_test_jsp.forward(req, resp);
            return;
        }
        req.setAttribute("err_message", "Successfully added tests.");
        add_test_jsp.forward(req, resp);
        return;

    }
}
