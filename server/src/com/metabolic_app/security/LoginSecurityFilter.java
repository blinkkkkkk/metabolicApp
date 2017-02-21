package com.metabolic_app.security;

import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by root on 14/02/17.
 */
public class LoginSecurityFilter implements Filter {
    private Logger logger = Logger.getLogger("Security-Filter");
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpServletRequest request = (HttpServletRequest) req;
        String servletPath = request.getServletPath();

        // No authorization is required to go the login or create account page
        if(servletPath.equals("/login") || servletPath.equals("/createaccount")) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if(userId != null) {
            chain.doFilter(request, response);
            return;
        }
        response.sendRedirect("/login");
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
