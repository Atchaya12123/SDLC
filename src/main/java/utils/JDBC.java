package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class JDBC {
    private static final String URL = "jdbc:mysql://localhost:3306/schoolrecords";
    private static final String USER = "root";
    private static final String PASSWORD = "root";
    // Static block to load driver once
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Database driver not found!", e);
        }
        System.out.println("Connected");
    }
    // Get a database connection
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    }
