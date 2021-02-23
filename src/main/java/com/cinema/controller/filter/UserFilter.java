package com.cinema.controller.filter;

import com.cinema.model.entity.user.Role;
import com.cinema.model.entity.user.User;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class UserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpSession session = ((HttpServletRequest) req).getSession(false);
        if (!"active".equals(req.getServletContext().getAttribute("active"))) {
            ((HttpServletResponse) resp).sendRedirect("/cinema");
            return;
        }
        User user = (User) session.getAttribute("user");
        if (!Role.USER.equals(user.getRole())) {
            ((HttpServletResponse) resp).sendRedirect("/cinema");
            return;
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
