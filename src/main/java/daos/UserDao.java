package daos;

import utils.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    String checkPass(String email){
    String sql = "select password from user_info where email=?";
    String pass = null;
        try (Connection con = JDBC.getConnection();
             PreparedStatement pstmt = con.prepareStatement("SELECT * FROM user_info WHERE user_id = ? AND password = ?")) {
             pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
               pass  = rs.getString("password");
            }
        rs.close();
        } catch (SQLException e) {
            return "error";
        }

        return pass;
    }
}
