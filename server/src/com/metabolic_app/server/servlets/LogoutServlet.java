package com.metabolic_app.server.servlets;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by root on 14/02/17.
 */
public class LogoutServlet extends HttpServlet{
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));

    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        session.invalidate();
        String url = "login";
        try {
            response.sendRedirect(url);
        } catch (Exception e) {
            logger.severe("Couldn't redirect to login: "+  e);

            //TODO(Kiran): implement Default error page

        }
    }
}
