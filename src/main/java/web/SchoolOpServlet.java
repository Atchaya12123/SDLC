package web;
import java.io.*;
import java.util.List;

import com.google.gson.Gson;
import daos.impl.SchoolDaoImpl;
import daos.impl.TeacherDaoImpl;
import daos.impl.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Teacher;
import model.User;


@WebServlet(name = "SchoolServlet", value = "/sclServlet")


public class SchoolOpServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        String query = req.getParameter("query");
        List<String> subs;
        if(query != null){
          subs = SchoolDaoImpl.searchSubs(query);
            res.setContentType("application/json");
            PrintWriter out = res.getWriter();
            out.write(new Gson().toJson(subs));
            out.flush();  // Ensure response is sent immediately
          return;
        }
        List<Integer> classes = SchoolDaoImpl.getClasses();
        subs = SchoolDaoImpl.getSubjects();
        req.setAttribute("stds",classes);
        req.setAttribute("subs",subs);
        try {
            req.getRequestDispatcher("StdSub.jsp").forward(req,res);
        } catch (ServletException e) {
            System.out.println("Request dispatcher error");
        }
    }
    //POSTTTTTTTTTT
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String result = null;
        int std = Integer.parseInt(req.getParameter("std"));
        String sub = req.getParameter("sub");
        boolean check = SchoolDaoImpl.checkAddStdSub(std, sub);
        if (!check) { //IF NOT ALREADY PRESENT
            result = "Std and Sub added Successfully";
            PrintWriter out = res.getWriter();
            out.print(result);
        }
        else{
            result = "Std and Sub already available!";
            PrintWriter out = res.getWriter();
            out.print(result);
        }
    }
}
