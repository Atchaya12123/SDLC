package daos.impl;

import model.Teacher;
import model.User;
import utils.JDBC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeacherDaoImpl extends BaseDaoImpl{

    public static boolean removeTeacher(Teacher t){
        String sql = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int uid = 0;
        try {
            sql = "delete from teacher_info where user_id=? and std_id=? and sub_id=?";
            con = getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, t.getUserId());
            stmt.setInt(2, t.getStdId());
            stmt.setString(3, t.getSubjectId());
            stmt.executeUpdate();

            sql = "select * from teacher_info where user_id=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,uid);
            rs = stmt.executeQuery();
            if(!rs.next()){
                sql = "delete from user_info where user_id=?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1,uid);
                stmt.executeUpdate();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(rs, stmt, con); // Inherited method
        }
        return true;
    }
    public static boolean setTeacherSub(Teacher t, String email){
        String sql = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int uid = 0;
        try {
            sql = "select user_id from user_info where email=?";
            con = getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if(rs.next())  uid = rs.getInt("user_id");

            sql = "insert into teacher_info (user_id, std_id, sub_id) values(?,?,?)";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1,uid);
            stmt.setInt(2,t.getStdId());
            stmt.setString(3,t.getSubjectId());
            stmt.executeUpdate();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(rs, stmt, con); // Inherited method
        }
        return true;
    }
    public static List<String> getTeacherSubs(String email, int std){
        List<String> tsubs = new ArrayList<>();
        String sql = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int uid = 0;
        sql = "select sub_id from user_info a cross join teacher_info b on a.user_id = b.user_id where a.email=? and b.std_id=?";
        try {
            con = getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setInt(2,std);
            rs = stmt.executeQuery();
            while(rs.next()){
                String sub = rs.getString("sub_id");
                tsubs.add(sub);
            }

        }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, con); // Inherited method
        }
        return tsubs;
    }
    public static List<Teacher> getTeacherInfo(String email){
        List<Teacher> teachers = new ArrayList<>();
        String sql = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        int uid = 0;
        sql = "select user_id from user_info where email=?";
        try {
            con = getConnection();
            stmt = con.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();

            if(rs.next()) uid = rs.getInt("user_id");
            sql = "select * from teacher_info where user_id = ?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, uid);
            rs = stmt.executeQuery();
            while(rs.next()){
                Teacher t = new Teacher();
                t.setUserId(rs.getInt("user_id"));
                t.setStdId(rs.getInt("std_id"));
                t.setSubjectId(rs.getString("sub_id"));
                t.setGrade(rs.getString("grade"));
                teachers.add(t);
            }

    }catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, con); // Inherited method
        }
        return teachers;
    }
    public static List<User> getAllTs(int std, String sub) {
        List<User> users = new ArrayList<>();
        String sql = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        User user = null;
        if(std == -1 && sub.equals("-1")) sql = "SELECT * FROM user_info WHERE role_id = ?";
        if(std != -1 && sub.equals("-1")) sql = "SELECT DISTINCT a.user_id, a.fname, a.lname, a.email, b.sub_id FROM user_info a join teacher_info b on a.user_id = b.user_id WHERE role_id = ? and std_id=?";
        try {
            con = getConnection();

            stmt = con.prepareStatement(sql);
            if(std != -1 && sub.equals("-1")){
                stmt.setInt(1, 1);
                stmt.setInt(2, std);

                rs = stmt.executeQuery();

                while (rs.next()) {
                    user = new User();
                    user.setFirstName(rs.getString("fname"));
                    user.setLastName(rs.getString("lname"));
                    user.setEmail(rs.getString("email")); // Assuming password is stored securely (hashed)
                    user.setPassword(rs.getString("sub_id"));
                    users.add(user);
                }
            }
//Conditinally returning diff users
            if(std == -1 && sub.equals("-1")){
            stmt.setInt(1, 1);

            rs = stmt.executeQuery();

            while (rs.next()) {
                user = new User();
                user.setFirstName(rs.getString("fname"));
                user.setLastName(rs.getString("lname"));
                user.setEmail(rs.getString("email")); // Assuming password is stored securely (hashed)
                users.add(user);
            }}
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, con); // Inherited method
        }
        return users;
    }
    public static boolean storeT(User u, Teacher t){
        String sql = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            con = getConnection();
            sql = "insert into user_info ( role_id, password, email, fname, lname) values(?,?,?,?,?)";
            ps = con.prepareStatement(sql);
            ps.setInt(1, u.getRoleId());
            ps.setString(2,u.getPassword());
            ps.setString(3,u.getEmail());
            ps.setString(4,u.getFirstName());
            ps.setString(5, u.getLastName());
            ps.executeUpdate();

            sql = "select user_id from user_info order by user_id desc limit 1";
            int uid = 0;
            ps=con.prepareStatement(sql);
            rs = ps.executeQuery();
            if(rs.next()) uid = rs.getInt("user_id");

            sql = "insert into teacher_info (user_id, std_id, sub_id) values(?,?,?)";
            ps=con.prepareStatement(sql);
            ps.setInt(1,uid);
            ps.setInt(2,t.getStdId());
            ps.setString(3,t.getSubjectId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con); // Inherited method
        }
        return true;
    }
}

