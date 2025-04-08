package servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Prevision;

public class EtatServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if (session.getAttribute("login") != null) {
                List<Prevision> previsions =  Prevision.findAll(Prevision.class);
                String dateDebut;
                String dateFin;
                if( request.getParameter("dateDebut") != null|| request.getParameter("dateFin") != null ){
                    dateDebut = request.getParameter("dateDebut");
                    dateFin = request.getParameter("dateFin") ;
                    previsions = Prevision.findByDate(dateDebut, dateFin);
                }

                request.setAttribute("previsions", previsions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/etat.jsp");
                dispatcher.forward(request, response);
            } else {
                response.sendRedirect("login");
            }
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
    }

    
}
