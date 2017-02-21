package com.metabolic_app.security;

import com.metabolic_app.data.Patient;
import com.metabolic_app.data.accessObject.PatientAccessObject;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by root on 15/02/17.
 */
public class ClinicianAuthorizedFilter implements Filter {
    private Logger logger = Logger.getLogger("Authority-Filter");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        Long patient_id = Long.valueOf(request.getParameter("id"));
        Long clinician_id = (Long) request.getSession().getAttribute("userId");

        boolean authorized;
        try {
            authorized = new PatientAccessObject().checkAuthorized(clinician_id, patient_id);
        } catch (SQLException e) {
            logger.severe("Could not check authority of clinician and patient-view-request, defaulting to unauthorized: " + e);
            authorized = false;
        }
        //Should never happen, defensive programming
        if(!authorized) {
            response.sendRedirect("/unauthorized-request");
            return;
        }

        chain.doFilter(request, response);
        return;
    }

    @Override
    public void destroy() {

    }
}
