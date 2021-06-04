package com.cinema.controller.servlet;

import com.cinema.model.dao.UserDAO;
import com.cinema.model.encryption.CryptPassword;
import com.cinema.model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class Login extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/login.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        User user;
        String errorMessage = null;
        String active;

        try {
            UserDAO userDAO = new UserDAO();
            user = userDAO.getUser(login);
            try {
                if (CryptPassword.check(req.getParameter("password"), user.getPassword())) {
                    active = "active";
                    req.getServletContext().setAttribute("active", active);
                    HttpSession userSession = req.getSession();
                    userSession.setAttribute("user", user);
                    resp.sendRedirect("/cinema");
                    return;
                } else {
                    errorMessage = "Incorrect password";
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            errorMessage = "Incorrect login";
        }
        req.setAttribute("errorMessage", errorMessage);
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/WEB-INF/cinema/login.jsp");
        requestDispatcher.forward(req, resp);
    }

}
