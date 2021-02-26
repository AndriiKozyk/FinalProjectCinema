package com.cinema.controller.servlet;

import com.cinema.model.dao.UserDAO;
import com.cinema.model.encryption.CryptPassword;
import com.cinema.model.entity.user.Role;
import com.cinema.model.entity.user.User;
import com.cinema.model.entity.user.UserDetails;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Registration extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/registration.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = new User();
        UserDetails details = new UserDetails();
        user.setLogin(req.getParameter("login"));
        try {
            String cryptPassword = CryptPassword.getSaltedHash(req.getParameter("password"));
            user.setPassword(cryptPassword);
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setRole(Role.USER);
        details.setFirstNameEN(req.getParameter("firstNameEN"));
        details.setLastNameEN(req.getParameter("lastNameEN"));
        details.setFirstNameUA(req.getParameter("firstNameUA"));
        details.setLastNameUA(req.getParameter("lastNameUA"));
        details.setEmail(req.getParameter("email"));
        details.setPhone(req.getParameter("phone"));
        user.setDetails(details);
        new UserDAO().insertUser(user);
        resp.sendRedirect("/login");
    }
}
