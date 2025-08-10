package web;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import daos.impl.SchoolDaoImpl;
//import daos.impl.TeacherDaoImpl;
import daos.impl.TeacherDaoImpl;
import daos.impl.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Teacher;
import model.User;


@WebServlet(name = "AddTeacher", value = "/addTeacher")
public class AddTCont extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getParameter("action");
        if(action != null && action.equals("loadSubs")){
            int std = Integer.parseInt(req.getParameter("std"));
            List<String> subs= SchoolDaoImpl.getStdSub(std);
            String html="";
            for(String sub: subs){
                html+="<option name="+sub+" value="+sub+">"+sub+"</option>";
            }
            PrintWriter out = res.getWriter();
            out.print(html);
            System.out.println("All subs sent");
            return;
        }
        System.out.println("got till teacher Dao impl");
        List<Integer> classes = SchoolDaoImpl.getClasses();
        req.setAttribute("stds",classes);
        System.out.println("got after teacher Dao impl"+classes);
        req.getRequestDispatcher("AddTeacher.jsp").forward(req,res);
    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        System.out.println("Atleast I got to post");
        User u = new User();
        Teacher t = new Teacher();
        System.out.println("fname: " + req.getParameter("fname"));
        System.out.println("lname: " + req.getParameter("lname"));
        System.out.println("mail: " + req.getParameter("mail"));
        System.out.println("std: " + req.getParameter("std"));
        System.out.println("sub: " + req.getParameter("sub"));

        // Check if form data is completely missing
        if (req.getParameterMap().isEmpty()) {
            System.out.println("No parameters received! Possible JavaScript or encoding issue.");
        }

        u.setFirstName(req.getParameter("fname"));
        u.setLastName(req.getParameter("lname"));
        u.setEmail(req.getParameter("mail"));
        u.setRoleId(1);
        u.setPassword(req.getParameter("password"));
        System.out.println(req.getParameter("fname")+" "+req.getParameter("sub") +" "+req.getParameter("std"));
       // if(req.getParameter("std") != null)
        t.setStdId((Integer.parseInt(req.getParameter("std"))));
        t.setSubjectId(req.getParameter("sub"));

        boolean result = TeacherDaoImpl.storeT(u,t);
        System.out.println("did it!!");
        String op = null;
        if(result){
           op="<h2>Teacher added successfully</h2>";

            System.out.println("finally");
        }
        PrintWriter out = res.getWriter();
        out.print(op);
    }
}
