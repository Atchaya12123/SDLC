package web;
import daos.impl.SchoolDaoImpl;
import daos.impl.TeacherDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "ViewTeacher", value = "/viewTeachers")
public class ViewTeachersServlet extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        String action = req.getParameter("action");

        if (action != null && action.equals("loadTable")) {
            int std = Integer.parseInt(req.getParameter("std"));
            List<User> users = TeacherDaoImpl.getAllTs(std,"-1");
            // Build HTML string for <option> elements
            String html = "<thead><tr> <th>Name</th> <th>Email</th> <th>Subject</th> <th>Action</th> </tr> </thead> <tbody>";
            for (User u : users) {
                html+= "<tr><td>"+ u.getFirstName()+" "+u.getLastName();
                html+= "<td>"+ u.getEmail()+"</td>";
                html+= "<td>"+ u.getPassword()+"</td>";
               //${pageContext.request.contextPath}/oneTeacher?email=<%=user.getEmail()%>&fname=<%=user.getFirstName()%>&lname=<%=user.getLastName()%>">view</a>
                html+= "<td><button <a href=\"${pageContext.request.contextPath}/oneTeacher?email="+u.getEmail()+
                        "&fname="+u.getFirstName()+"&lname="+u.getLastName()+"\">> view </a></button></td></tr>";
            }
            html+="</tbody>";

            PrintWriter out = res.getWriter();
            out.print(html);
            out.flush();
            return;
        }
        List<User> users = TeacherDaoImpl.getAllTs(-1,"-1");
        System.out.println(users);
        req.setAttribute("users",users);
        List<Integer> classes = SchoolDaoImpl.getClasses();
        req.setAttribute("stds",classes);
        req.getRequestDispatcher("ViewTeachers.jsp").forward(req,res);
    }
}
