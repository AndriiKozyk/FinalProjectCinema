package com.cinema.model.dao;

import com.cinema.model.DBManager;
import com.cinema.model.entity.film.Genre;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class GenreDAOTest {

    private static final String DB_URL_TEST = "jdbc:mysql://127.0.0.1:3307/cinemaTest?allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/cinema?allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static DBManager dbManager;
    private static Properties prop;

    @Test
    public void getGenreTest() {
        Genre genre = new Genre();
        genre.setGenreEN("TestGenre1");
        GenreDAO genreDAO = new GenreDAO();
        genreDAO.insertGenre(genre);
        Genre actual = genreDAO.getGenre(8);
        assertEquals(genre.getGenreEN(), actual.getGenreEN());
    }

    @BeforeClass
    public static void beforeTest() throws IOException {

        prop = new Properties();
        prop.load(new FileInputStream("src/main/resources/application.properties"));
        prop.setProperty("url", DB_URL_TEST);
        prop.store(new FileOutputStream("src/main/resources/application.properties"), null);

        dbManager = DBManager.getInstance();
    }

    @AfterClass
    public static void afterTest() throws IOException {
        prop.setProperty("url", DB_URL);
        prop.store(new FileOutputStream("src/main/resources/application.properties"), null);
    }

}
