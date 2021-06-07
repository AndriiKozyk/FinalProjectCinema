package com.cinema.model.dao;

import com.cinema.model.DBManager;
import com.cinema.model.entity.place.Type;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Properties;

import static org.junit.Assert.*;

public class TypeDAOTest {

    private static final String DB_URL_TEST = "jdbc:mysql://127.0.0.1:3307/cinemaTest?allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/cinema?allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static DBManager dbManager;
    private static Properties prop;

    @Test
    public void getTypeTest() {
        Type type = new TypeDAO().getType(1);
        assertEquals(type, Type.STANDARD);
    }

    @Test
    public void getPriceTest() {
        BigDecimal price = new TypeDAO().getPrice(false);
        Type expectedType = Type.STANDARD;
        expectedType.setPrice(new BigDecimal(10));
        assertEquals(price, Type.STANDARD.getPrice());
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
