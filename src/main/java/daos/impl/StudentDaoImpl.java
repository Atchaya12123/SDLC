package daos.impl;

import model.Teacher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//Genera
public class StudentDaoImpl extends BaseDaoImpl{
    public static List<List<Object>> getStudInfo(){
        List<List<Object>> stud_info = new ArrayList<>();
        String sql;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            con = getConnection();
            sql = "select row_number() over (order by b.std_id, concat(fname, lname)) as slno, a.fname,a.lname,b.school_id from user_info a join"+
                    " student_info b on a.user_id = b.user_id group by b.std_id,a.fname,a.lname, b.school_id";
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();

            while (rs.next()) {
                ArrayList<Object> stud= new ArrayList<>();
                stud.add(rs.getInt("slno"));
                stud.add(rs.getString("fname"));
                stud.add(rs.getString("lname"));
                stud.add(rs.getString("school_id"));
                stud_info.add(stud);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeResources(rs, stmt, con);
        }
        return stud_info;
    }
    public static boolean generateRoll(int std){
        String sql = null;
        Connection con = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            if(std == -1)  sql = "SELECT a.user_id, a.fname,a.lname,b.std_id FROM user_info a JOIN " +
                    "student_info b ON a.user_id = b.user_id ORDER BY b.std_id, concat(a.fname,a.lname)";
            con = getConnection();
            stmt = con.prepareStatement(sql);
            rs = stmt.executeQuery();
            int roll=1;
            PreparedStatement ps=null;
            while(rs.next()){
                if(std != rs.getInt("std_id")) {
                    std = rs.getInt("std_id");
                    roll=1;
                }
                String stdid = String.valueOf(std);
                if(stdid.length()<3){
                    stdid = "0"+stdid;
                }
                String sid = stdid+rs.getString("fname")+rs.getString("lname").toUpperCase().charAt(0);
                String r = String.valueOf(roll++);
                while(r.length()<3){
                    r = "0"+r;
                }
                sid += r;
                sql = "update student_info set school_id=? where user_id=?";
                ps = con.prepareStatement(sql);
                ps.setString(1,sid);
                ps.setInt(2,rs.getInt("user_id"));
                ps.addBatch();
            }
            ps.executeBatch();
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            closeResources(rs, stmt, con); // Inherited method
        }
        return true;
    }
}
