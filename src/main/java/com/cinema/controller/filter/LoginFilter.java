package com.cinema.controller.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        if ("active".equals(req.getServletContext().getAttribute("active"))) {
            ((HttpServletResponse) resp).sendRedirect("/cinema");
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

}
