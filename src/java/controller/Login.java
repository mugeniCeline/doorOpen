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
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet{
   private static final int SESSION_TIMEOUT = 60;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String selectedRole = request.getParameter("role");

        UserDao userDao = new UserDao();
        User existingUser = userDao.findByEmail(new User(null, email, null, null));

        if (existingUser != null && existingUser.getPassword().equals(password)) {
            HttpSession session = request.getSession();
            session.setMaxInactiveInterval(SESSION_TIMEOUT);
            session.setAttribute("user", existingUser);

            Role userRole = existingUser.getStatus();
            if (selectedRole.equals(userRole.toString())) {
                if (userRole == Role.ADMIN) {
                    response.sendRedirect("index.html");
                } else if (userRole == Role.STUDENT) {
                    response.sendRedirect("index.html");
                } else if (userRole == Role.TEACHER) {
                    response.sendRedirect("index.html");
                }
            } else {
                response.sendRedirect("login.html?error=invalid_role");
            }
        } else {
            response.sendRedirect("login.html");
        }
    }

}
