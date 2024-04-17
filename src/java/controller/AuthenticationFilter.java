package controller;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "AuthenticationFilter", urlPatterns = {"/*"})
public class AuthenticationFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String requestURI = httpRequest.getRequestURI();
        if (requestURI.endsWith("Login") || requestURI.endsWith("SignUp")) {
            chain.doFilter(request, response);
            return;
        }
        if (requestURI.contains("/css/") ||requestURI.contains("/fonts/") ||requestURI.contains("/images/") ||requestURI.contains("/js/") || requestURI.contains("/AUCA BG.jpg") || requestURI.contains("/AUCA logo.jpg") || requestURI.contains("login.html") || requestURI.contains("signUp.html")
                || requestURI.endsWith("/login") || requestURI.contains("/register") || requestURI.endsWith("/logout")) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = httpRequest.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            Object userObj = session.getAttribute("user");

            if (userObj instanceof Model.User) {
                Model.User user = (Model.User) userObj;
                String role = user.getStatus().toString();
                if (requestURI.endsWith("index.html") || requestURI.endsWith("/logout")) {
                    chain.doFilter(request, response);
                    return;
                } else {
                    // Check access based on role
                    switch (role) {
                        case "ADMIN":
                            chain.doFilter(request, response);
                            return;
                        
                        case "TEACHER":
                            if (requestURI.endsWith("serviceCharter.html") || requestURI.endsWith("aucaJournal.html")) {
                                chain.doFilter(request, response);
                                return;
                            } else {
                                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.html");
                                return;
                            }
                        case "STUDENT":
                            if (requestURI.endsWith("academicCalender.html") || requestURI.endsWith("apply.html")) {
                                chain.doFilter(request, response);
                                return;
                            } else {
                                httpResponse.sendRedirect(httpRequest.getContextPath() + "/index.html");
                                return;
                            }
                    }
                }
            }
        }
        httpResponse.sendRedirect(httpRequest.getContextPath() + "/login.html");
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }
}
