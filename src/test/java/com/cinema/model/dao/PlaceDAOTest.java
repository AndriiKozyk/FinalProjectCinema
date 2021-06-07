package com.cinema.model.dao;

import com.cinema.model.DBManager;
import com.cinema.model.entity.place.Place;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.*;

public class PlaceDAOTest {

    private static final String DB_URL_TEST = "jdbc:mysql://127.0.0.1:3307/cinemaTest?allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/cinema?allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static DBManager dbManager;
    private static Properties prop;

    @Test
    public void getPlaceTest() {
        PlaceDAO placeDAO = new PlaceDAO();
        Place expected = placeDAO.getPlace(1);
        Place place = placeDAO.selectPlaces().get(0);
        assertEquals(expected.getNumber(), place.getNumber());
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
