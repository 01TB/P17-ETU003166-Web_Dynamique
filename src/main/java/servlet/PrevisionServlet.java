package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Prevision;
import connection.MySQLConnection;



public class PrevisionServlet extends HttpServlet {


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        if(session.getAttribute("login")!=null){
            RequestDispatcher dispatcher = request.getRequestDispatcher("pages/form-prevision.jsp");
            dispatcher.forward(request, response);
        }
        else{
            response.sendRedirect("login");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String libelle = request.getParameter("libelle");
        Double montant = Double.parseDouble(request.getParameter("montant"));
        Date date = Date.valueOf(request.getParameter("date"));
        Connection connection = null;
        try {
            connection = MySQLConnection.getConnection();
            Prevision prevision = new Prevision(libelle, montant,date);
            prevision.save();
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
        finally{
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("prevision");
    }
}
