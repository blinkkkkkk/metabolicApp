package com.metabolic_app.server.servlets;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by root on 14/02/17.
 */
public class MainPageServlet extends HttpServlet {
    private Logger logger = Logger.getLogger(String.valueOf(this.getClass()));
    private RequestDispatcher homepagejsp = null;

    public void init(ServletConfig config) {
        ServletContext context = config.getServletContext();
        homepagejsp = context.getRequestDispatcher("/WEB-INF/jsp/main_page.jsp");
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
        logger.info("Recorded Home page request");
        try {
            homepagejsp.forward(req, resp);
        } catch (Exception e) {
            logger.severe("Could not redirect to home: " + e);

            //TODO(Kiran): Add default error page.

        }
    }
}
