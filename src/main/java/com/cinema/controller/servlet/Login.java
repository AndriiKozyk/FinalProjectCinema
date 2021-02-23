package com.cinema.controller.servlet;

import com.cinema.model.dao.UserDAO;
import com.cinema.model.entity.user.Role;
import com.cinema.model.entity.user.User;
import com.cinema.model.entity.user.UserNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/login.jsp");
        requestDispatcher.forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        User user = null;
        String errorMessage = null;

        String active;

        try {
            UserDAO userDAO = new UserDAO();
            user = userDAO.getUser(login);
            if (user.getPassword().equals(password)) {
                active = "active";
                req.getServletContext().setAttribute("active", active);
                HttpSession userSession = req.getSession();
                userDAO.getUserDetails(user);
                userSession.setAttribute("user", user);
                if (Role.ADMIN.equals(user.getRole())) {
                    resp.sendRedirect("/timetableAdmin");
                    return;
                }
                resp.sendRedirect("/cinema");
                return;
            } else {
                errorMessage = "Incorrect password";
            }
        } catch (UserNotFoundException e) {
            errorMessage = "Incorrect login";
        }
        req.setAttribute("errorMessage", errorMessage);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/cinema/login.jsp");
        requestDispatcher.forward(req, resp);
    }
}
