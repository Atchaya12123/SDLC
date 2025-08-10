package web;

import java.io.*;

import daos.impl.UserDaoImpl;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import model.User;


@WebServlet(name = "LogoutServlet", value = "/logoutServlet")
public class LogoutServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        req.getSession(false).removeAttribute("user");
        req.getRequestDispatcher("/login.jsp").forward(req,res);

    }}
