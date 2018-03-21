package com.senla.hotel.web.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

import static com.senla.hotel.constants.Constants.BAD_LOGIN_PASSWORD;
import static com.senla.hotel.constants.Constants.PASSWORD;
import static com.senla.hotel.constants.Constants.USERNAME;

@WebFilter(urlPatterns = {"/register", "/login"}, filterName = "/userfilter")
public class UsernameFilter implements Filter {

    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String username = servletRequest.getParameter(USERNAME);
        String password = servletRequest.getParameter(PASSWORD);
        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            servletResponse.getWriter().println(BAD_LOGIN_PASSWORD);
            return;
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
