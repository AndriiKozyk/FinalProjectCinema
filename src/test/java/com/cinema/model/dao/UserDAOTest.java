package com.cinema.model.dao;

import com.cinema.model.DBManager;
import com.cinema.model.entity.user.Role;
import com.cinema.model.entity.user.User;
import com.cinema.model.entity.user.UserDetails;
import org.junit.*;

import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

import static org.junit.Assert.*;

public class UserDAOTest {

    private static final String DB_URL_TEST = "jdbc:mysql://127.0.0.1:3307/cinemaTest?allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3307/cinema?allowPublicKeyRetrieval=true&useUnicode=true&serverTimezone=UTC&useSSL=false";
    private static DBManager dbManager;
    private static Properties prop;

    @Test
    public void getUserTest() {
        User user = new User();
        User userTest = null;
        UserDetails userDetails = new UserDetails();
        user.setLogin("test");
        user.setPassword("test");
        user.setRole(Role.USER);
        userDetails.setFirstNameEN("testUser");
        userDetails.setLastNameEN("testLast");
        userDetails.setEmail("test@test.com");
        userDetails.setPhone("123456789");
        user.setDetails(userDetails);
        UserDAO userDAO = new UserDAO();
        userDAO.insertUser(user);
        try {
            userTest = userDAO.getUser("test");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        assertEquals(user.getDetails().getFirstNameEN(), userTest.getDetails().getFirstNameEN());
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
