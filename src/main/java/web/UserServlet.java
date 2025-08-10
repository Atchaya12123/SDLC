package web;

import java.io.*;

import daos.impl.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.User;


@WebServlet(name = "helloServlet", value = "/userServlet")
public class UserServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
        res.setContentType("text/html");

    }

    public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        UserDaoImpl udi = new UserDaoImpl();
        String uname = req.getParameter("mail");
        String pass = req.getParameter("password");
        User u = udi.getUser(uname,pass);
        System.out.println("hello");
        if( u != null){
            req.getSession().setAttribute("user", u);
           if(u.getRoleId() == 0) {
               try {
                   System.out.println("What is the matter??");
                   //System.out.println("okk: "+u.getFirstName()+" "+u.getLastName()+" "+u);
                   req.getRequestDispatcher("PrincipalPage.jsp").forward(req,res);
               } catch (ServletException e) {
                   System.out.println("Servlet Error Occured: "+e);
                   req.setAttribute("mesg","Servlet not responding");
                   req.getRequestDispatcher("error.jsp").forward(req,res);
               }
           }
        }
    }
}