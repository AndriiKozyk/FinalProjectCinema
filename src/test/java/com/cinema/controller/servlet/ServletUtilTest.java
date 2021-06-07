package com.cinema.controller.servlet;

import org.junit.Test;

import static org.junit.Assert.*;

public class ServletUtilTest {

    @Test
    public void getAmountUserSuggestionsTest() {
        int amountUserSuggestions = ServletUtil.getAmountUserSuggestions();
        assertTrue(amountUserSuggestions >= 0);
    }

    @Test
    public void getAmountVotedFilmsTest() {
        int amountVotedFilms = ServletUtil.getAmountVotedFilms();
        assertTrue(amountVotedFilms >= 0);
    }

}
