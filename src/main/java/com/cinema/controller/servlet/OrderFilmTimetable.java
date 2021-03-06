package com.cinema.controller.servlet;

import com.cinema.model.dao.FilmToOrderDAO;
import com.cinema.model.entity.filmToOrder.FilmStatus;
import com.cinema.model.entity.filmToOrder.FilmToOrder;
import com.cinema.model.entity.user.Role;
import com.cinema.model.entity.user.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.cinema.controller.servlet.Constants.*;

public class OrderFilmTimetable extends HttpServlet {

    private static String sortBy = SORT_VOTING;

    private int currentPage = 1;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String sort = req.getParameter(NAME);
        if (sort != null) {
            if (!sort.equals("")) {
                sortBy = req.getParameter(NAME);
            }
        }

        FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();

        User user = null;
        if (req.getSession(false) != null) {
            user = (User) req.getSession(false).getAttribute(USER);
            req.setAttribute(USER, user);
        }

        List<FilmToOrder> films = sorting(filmToOrderDAO, user);

        boolean empty = films.isEmpty();

        films = pagination(req, films);

        boolean shortForm;
        shortForm = sortBy.equals(SORT_SUGGESTIONS) || sortBy.equals(SORT_USER_SUGGESTIONS);

        if (shortForm || sortBy.equals(SORT_MOVIES)) {
            Map<FilmToOrder, String> userVotesMap = new LinkedHashMap<>();
            for (FilmToOrder film : films) {
                String status = filmToOrderDAO.getStatusByFilm(film.getId());
                userVotesMap.put(film, status);
            }
            req.setAttribute("filmsMap", userVotesMap);
        } else {
            Map<FilmToOrder, Boolean> userVotesMap = new LinkedHashMap<>();
            boolean userVote = true;
            if (user != null) {
                if (user.getRole().equals(Role.USER)) {
                    int userId = user.getId();
                    for (FilmToOrder film : films) {
                        userVote = filmToOrderDAO.isUserVote(userId, film.getId());
                        userVotesMap.put(film, userVote);
                    }
                }
            } else {
                for (FilmToOrder film : films) {
                    userVotesMap.put(film, userVote);
                }
            }
            req.setAttribute("filmsMap", userVotesMap);
        }

        req.setAttribute(USER_SUGGESTIONS, ServletUtil.getAmountUserSuggestions());
        req.setAttribute(VOTED_FILMS, ServletUtil.getAmountVotedFilms());
        req.setAttribute("sortBy", sortBy);
        req.setAttribute("isEmpty", empty);
        req.setAttribute("shortForm", shortForm);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("WEB-INF/cinema/filmToOrderTimetable.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = ((User) req.getSession(false).getAttribute(USER)).getId();
        int filmId = Integer.parseInt(req.getParameter(ID));

        FilmToOrderDAO filmToOrderDAO = new FilmToOrderDAO();
        filmToOrderDAO.insertUserVote(userId, filmId);

        doGet(req, resp);
    }

    private List<FilmToOrder> sorting(FilmToOrderDAO filmToOrderDAO, User user) {
        List<FilmToOrder> films;

        if (user == null) {
            sortBy = SORT_VOTING;
        } else {
            if (user.getRole().equals(Role.ADMIN) && sortBy.equals(SORT_VOTING)) {
                sortBy = SORT_MOVIES;
            }
        }

        switch (sortBy) {

            case SORT_SUGGESTIONS:
                films = filmToOrderDAO.selectOrderFilmsByStatus(FilmStatus.SUGGESTION.toString().toLowerCase());
                break;

            case SORT_MOVIES:
                films = filmToOrderDAO.selectOrderFilmsByStatusReady();
                break;

            case SORT_USER_VOTES:
                films = filmToOrderDAO.selectOrderFilmsByUserVote(user.getId());
                break;

            case SORT_USER_SUGGESTIONS:
                films = filmToOrderDAO.selectOrderFilmsByUserSuggestion(user.getId());
                break;

            default:
                films = filmToOrderDAO.selectOrderFilmsByStatus(FilmStatus.VOTING.toString().toLowerCase());
                break;
        }
        return films;
    }

    private List<FilmToOrder> pagination(HttpServletRequest req, List<FilmToOrder> films) {
        int filmAmount = films.size();
        int pageAmount = (int) Math.ceil((double) filmAmount / FILMS_LIMIT);

        Integer[] pages = new Integer[pageAmount];
        for (int i = 0; i < pageAmount; ++i) {
            pages[i] = i + 1;
        }

        if (req.getParameter("page") != null) {
            currentPage = Integer.parseInt(req.getParameter("page"));
            if (currentPage > pageAmount || currentPage < 1) {
                currentPage = 1;
            }
        }

        int offset = (currentPage - 1) * FILMS_LIMIT;
        int lastFilm = currentPage * FILMS_LIMIT;

        films = films.stream().skip(offset).limit(lastFilm).collect(Collectors.toList());

        req.setAttribute(CURRENT_PAGE, currentPage);
        req.setAttribute(PAGES, pages);
        req.setAttribute(FIRST_PAGE, CONST_ONE);
        req.setAttribute(LAST_PAGE, pageAmount);

        return films;
    }

}
