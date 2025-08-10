package daos.impl;

import daos.UserDao;
import model.User;
import utils.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class UserDaoImpl implements UserDao {
      public List<User> getTeacherUser(int std, String sub){
          String sql = "select * from user_info where rode_id=?";
          User user = null;
          Connection con = null;
          PreparedStatement stmt = null;
          ResultSet rs = null;
          return null;
      }
    @Override
    public User getUser(String uname, String pass) {
        //System.out.println("here: "+uname+" "+pass);
        User u = null;
        String sql = "select * from user_info where email=? and password=?";

        try (Connection con = JDBC.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, uname);
            pstmt.setString(2, pass);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {  // Use if instead of while
                u = new User();  // Initialize only when data is found
                u.setUserId(rs.getInt("user_id")); // Fixed column name
                u.setRoleId(rs.getInt("role_id"));
                u.setPassword(rs.getString("password"));
                u.setEmail(rs.getString("email"));
                u.setFirstName(rs.getString("fname"));
                u.setLastName(rs.getString("lname"));
                u.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime()); // Fixed conversion
            }
            rs.close();
        } catch (SQLException e) {
            return null;
        }

        return u;
    }

}
