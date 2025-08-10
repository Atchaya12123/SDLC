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

public class SchoolDaoImpl extends BaseDaoImpl{

    public static boolean checkAddStdSub(int std, String sub){
        String sql;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            sql = "select * from std_sub where std=? and sub=?";
            stmt = con.prepareStatement(sql);
            stmt.setInt(1, std);
            stmt.setString(2, sub);
            rs = stmt.executeQuery();
            if (rs.next()) return true; //Std and Sub already available TRUE

            else{
                //CHECK IN CLASSES FOR STD
                sql = "select count(std) as cnt from classes where std=?";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, std);
                rs = stmt.executeQuery();
                if(rs.next()){
                    int cnt = rs.getInt("cnt");
                    if(cnt == 0){
                        sql = "insert into classes (std) values(?)";
                        stmt = con.prepareStatement(sql);
                        stmt.setInt(1, std);
                        int rows = stmt.executeUpdate();
                        System.out.println("Std_Sub Rows affected:"+rows);
                    }
                }
                //UPDATE IN STD_SUB
                sql = "insert into std_sub (std, sub) values(?,?)";
                stmt = con.prepareStatement(sql);
                stmt.setInt(1, std);
                stmt.setString(2, sub);
                int rows = stmt.executeUpdate();
                System.out.println("Std_Sub Rows affected:"+rows);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, con);
        }
        return false;
    }
    public static List<String> searchSubs(String query){
        String sql;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> subs = new ArrayList<>();
        try {
            con = getConnection();
            sql = "select distinct sub from std_sub where sub like ? LIMIT 10";
            stmt = con.prepareStatement(sql);
            stmt.setString(1, query+"%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                subs.add(rs.getString("sub"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, con);
        }
        return subs;
    }
    public static List<String> getSubjects(){
        String sql = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<String> subs = new ArrayList<>();
        try {
            con = getConnection();
            sql = "select distinct sub from std_sub";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                subs.add(rs.getString("sub"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, con);
        }
        return subs;
    }

    public static List<Integer> getClasses() {
        String sql = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Integer> classes = new ArrayList<>();
        try {
            con = getConnection();
            sql = "select * from Classes";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
               classes.add(rs.getInt("std"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, con);
        }
        return classes;
    }

    public static List<String> getStdSub(int stdid) {
        String sql = null;
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<String> subs = new ArrayList<>();
        try {
            con = getConnection();
            sql = "select * from std_sub where std=?";
            ps = con.prepareStatement(sql);
            ps.setInt(1,stdid);
            rs = ps.executeQuery();

            while (rs.next()) {
                subs.add(rs.getString("sub"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, ps, con);
        }
        return subs;
    }
}