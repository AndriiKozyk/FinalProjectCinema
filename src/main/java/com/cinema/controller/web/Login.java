package com.cinema.controller.web;

import com.cinema.model.dao.UserDAO;
import com.cinema.model.entity.user.User;
import com.cinema.model.entity.user.UserNotFoundException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        try {
            user = new UserDAO().getUser(login);
            if (user.getPassword().equals(password)) {
                resp.sendRedirect("/cinema");
            } else {
                System.out.println("PASSWORD");
                String errorMessage = "Incorrect password";
                req.setAttribute("errorMessage", errorMessage);
                RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/login.jsp");
                requestDispatcher.forward(req, resp);
            }
        } catch (UserNotFoundException e) {
            System.out.println("LOGIN");
            String errorMessage = "Incorrect login";
            req.setAttribute("errorMessage", errorMessage);
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/login.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}
