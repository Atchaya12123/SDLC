package daos.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import utils.JDBC;

public class BaseDaoImpl {

    // Method to get a database connection
    protected static Connection getConnection() throws SQLException {
        return JDBC.getConnection();
    }

    // Method to close resources
    protected static void closeResources(ResultSet rs, PreparedStatement stmt, Connection con) {
        try {
            if (rs != null) rs.close();
        } catch (SQLException e) { e.printStackTrace(); }

        try {
            if (stmt != null) stmt.close();
        } catch (SQLException e) { e.printStackTrace(); }

        try {
            if (con != null) con.close();
        } catch (SQLException e) { e.printStackTrace(); }
    }

    // Method to commit the transaction
    protected static void commitTransaction(Connection con) {
        try {
            con.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to rollback the transaction
    protected static void rollbackTransaction(Connection con) {
        try {
            if (con != null) con.rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
