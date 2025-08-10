package web;
import daos.impl.SchoolDaoImpl;
import daos.impl.TeacherDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Teacher;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@WebServlet(name = "OneTeacher", value = "/oneTeacher")
public class OneTeacherServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getParameter("action");
        if(action != null && action.equals("loadSubs")){
            System.out.println("OnChange is working");
            int std = Integer.parseInt(req.getParameter("std"));
            String email = req.getParameter("email");
            List<String> subs = SchoolDaoImpl.getStdSub(std);
            List<String> tsubs = TeacherDaoImpl.getTeacherSubs(email,std);
            String html = "";
            Iterator<String> iterator = subs.iterator();
            while (iterator.hasNext()) {
                String sub = iterator.next();
                if (tsubs.contains(sub)) {
                    iterator.remove(); // Correct way to remove items safely
                }
            }
//            for(String sub: subs){
//                if(tsubs.contains(sub))   subs.remove(sub);
//            }
            for(String sub: subs){
                html+="<option name="+sub+" value="+sub+">"+sub+"</option>";
            }
            PrintWriter out = res.getWriter();
            System.out.println("Av T subs sent"+tsubs+"AllSubs:"+subs);
            out.print(html);
            return;
        }
        String fname = req.getParameter("fname");
        String lname = req.getParameter("lname");
        String email = req.getParameter("email");
        List<Teacher> teachers = TeacherDaoImpl.getTeacherInfo(email); //stdid subid grade

        String html = "<h2 id=\"title\">"+fname+"    "+lname+"   "+email+"</h2><br><br>";
        List<Integer> stds = new ArrayList<>();
        for(Teacher t : teachers){
            if(!stds.contains(t.getStdId())){
                if(!stds.isEmpty()) html+="</tbody></table>";
                stds.add(t.getStdId());
                html += "<br><h3>Class "+t.getStdId()+"</h3> <table><thead><tr> <th>Sub</th> <th>Grade</th> <th>Action</th></tr></thead> <tbody>";
            }
            //<button onclick=\"view("+u.getEmail()+","+u.getFirstName()+","+u.getLastName()+")\">view</button>
            html += "<tr id=\""+t.getSubjectId()+t.getStdId()+"\"><td>"+t.getSubjectId()+"</td><td>"+t.getGrade()+"</td><td>" +
                    "<button onclick=\"remove("+t.getUserId()+","+t.getStdId()+","+"'"+t.getSubjectId()+"'"+")\">remove</button></td> </tr>";
        }
        html += "</tbody></table><br><br>";

        List<Integer> classes = SchoolDaoImpl.getClasses();
        System.out.println(html);
        req.setAttribute("html",html);
        req.setAttribute("stds",classes);
        req.getRequestDispatcher("/OneTeacher.jsp").forward(req,res);
    }
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String result = null;
        Teacher t = new Teacher();
        int std = Integer.parseInt(req.getParameter("std"));
        t.setStdId(std);
        String sub = req.getParameter("sub");
        t.setSubjectId(sub);
        String action = req.getParameter("action");

        //CONDITIONAL RENDERING
        if(action != null && action.equals("remove")){
            int uid = Integer.parseInt(req.getParameter("tid"));
            t.setUserId(uid);
            boolean removed = TeacherDaoImpl.removeTeacher(t);
            if(removed){
                result = "Teacher Removed";
                System.out.println(result);
            }
            PrintWriter out = res.getWriter();
            out.print(result); return;
        }
        if(action != null && action.equals("add")){
            String email = req.getParameter("email");
            boolean added = TeacherDaoImpl.setTeacherSub(t,email);
            if(added){
                result = "Teacher info updated";
                System.out.println(result);
            }
        }
        PrintWriter out = res.getWriter();
        out.print(result);
    }
}

