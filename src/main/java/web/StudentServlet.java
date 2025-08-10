package web;
import java.io.*;
import java.util.List;

import com.google.gson.Gson;
import daos.impl.SchoolDaoImpl;
import daos.impl.StudentDaoImpl;
import daos.impl.TeacherDaoImpl;
import daos.impl.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.Teacher;
import model.User;


@WebServlet(name = "StudentServlet", value = "/studServlet")


public class StudentServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
       if(StudentDaoImpl.generateRoll(-1)) System.out.println("Roll Generated");
       List<List<Object>> s_info= StudentDaoImpl.getStudInfo();
//       for(Object obj: s_info){
//
//       }

        System.out.println(s_info);
        req.setAttribute("studs",s_info);
        List<Integer> classes = SchoolDaoImpl.getClasses();
        req.setAttribute("stds",classes);
        try {
            req.getRequestDispatcher("Student.jsp").forward(req,res);
        } catch (ServletException e) {
            e.printStackTrace();
        }

    }
}