package controller;

import Model.Role;
import Model.User;
import dao.UserDao;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SignUp")

public class SignUp extends HttpServlet{
    private static final long serialVersionUID = 1L;
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        Role role = Role.valueOf(request.getParameter("role"));
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(password);
        newUser.setStatus(role);
        UserDao userDao = new UserDao();
        if (userDao.findByEmail(newUser) != null) {
            request.setAttribute("errorMessage", "User with this email already exists!");
            request.getRequestDispatcher("signup.html").forward(request, response);
        } else {
            User createdUser = userDao.createUser(newUser);
            if (createdUser != null) {
                response.sendRedirect("login.html");
            } else {
                request.setAttribute("errorMessage", "Failed to create user. Please try again later.");
                request.getRequestDispatcher("signup.html").forward(request, response);
            }
        }
    }
}
