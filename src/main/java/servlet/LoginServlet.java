package servlet;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


public class LoginServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try  {
          RequestDispatcher dispatcher = request.getRequestDispatcher("pages/connection.jsp");
          dispatcher.forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        if (name.equals("admin") && password.equals("admin")) {
            HttpSession session = request.getSession();
            session.setAttribute("login", 1);
            response.sendRedirect("etat");
        }
        else{
            response.sendRedirect("login");
        }
    }
}
