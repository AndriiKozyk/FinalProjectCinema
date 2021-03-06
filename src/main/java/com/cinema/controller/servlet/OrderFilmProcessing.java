package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmToOrderDAO;
import com.cinema.model.entity.filmToOrder.FilmToOrder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;

import static com.cinema.controller.servlet.Constants.*;


public class OrderFilmProcessing extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int filmId = Integer.parseInt(req.getParameter(NAME));

        req.setAttribute(USER_SUGGESTIONS, ServletUtil.getAmountUserSuggestions());
        req.setAttribute(VOTED_FILMS, ServletUtil.getAmountVotedFilms());
        req.setAttribute(FILM, ServletUtil.getFilmToOrder(filmId));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/filmToOrderProcessing.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();
        String action = req.getParameter("action");

        if (action.equals("Reject movie")) {
            filmToOrderDAO.updateOrderFilmStatus(Integer.parseInt(req.getParameter(ID)), "rejected");
            resp.sendRedirect("/suggestionsList?name=suggestions");
        } else if (action.equals("Accept movie")) {

            FilmToOrder film = new FilmToOrder();
            film.setId(Integer.parseInt(req.getParameter(ID)));
            film.setNameEN(req.getParameter("nameEN"));
            String trailer = buildTrailerLink(req.getParameter("trailer"));
            film.setTrailer(trailer);
            film.setRequiredVote(Integer.parseInt(req.getParameter("votes")));
            Part part = req.getPart("poster");
            InputStream inputStream = part.getInputStream();
            film.setPosterInput(inputStream);
            filmToOrderDAO.updateOrderFilm(film);

            resp.sendRedirect("/suggestionsList?name=suggestions");
        }

    }

    private String buildTrailerLink(String link) {
        StringBuilder sb = new StringBuilder();
        String code = link.substring(32);
        sb.append("https://www.youtube.com/embed/").append(code).append("?autoplay=1");
        return sb.toString();
    }

}
