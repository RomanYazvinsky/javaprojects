package com.senla.hotel.web.filters;

import com.senla.hotel.constants.Constants;
import utilities.web.TokenManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static com.senla.hotel.constants.Constants.BAD_TOKEN;

@WebFilter(urlPatterns = {"/client", "/clients", "/order", "/orders", "/service", "/services", "/room", "/rooms", "/record", "/records", "/logout"}, filterName = "LoginFilter")
public class LoginFilter implements Filter {
    private FilterConfig filterConfig;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest) servletRequest).getHeader(Constants.AUTHORIZATION);
        if (TokenManager.getInstance().check(token)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            servletResponse.getWriter().write(BAD_TOKEN);
        }
    }

    @Override
    public void destroy() {

    }
}
