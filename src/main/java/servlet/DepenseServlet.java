package servlet;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import models.Depense;
import models.Prevision;

public class DepenseServlet extends HttpServlet {


    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            HttpSession session = request.getSession();
            if(session.getAttribute("login")!=null){
                List<Prevision> previsions = Prevision.findAll(Prevision.class);
                request.setAttribute("previsions", previsions);
                RequestDispatcher dispatcher = request.getRequestDispatcher("pages/form-depense.jsp");
                dispatcher.forward(request, response);
            }
            else{
                response.sendRedirect("login");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new ServletException(e.getMessage());
        }
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 
        try {
            
            Integer id_prevision = Integer.parseInt(request.getParameter("prevision"));
            Double montant = Double.parseDouble(request.getParameter("montant"));
            Prevision prevision = Prevision.findById( Prevision.class, id_prevision);
            if(prevision.getReste()<montant){
                throw new ServletException("Le montant de depense ne doit pas depasser la prevision");
            }
            Depense depense = new Depense(id_prevision, montant);
            depense.save();
            
        } catch (Exception e) {
            throw new ServletException(e.getMessage());
        }
        
        response.sendRedirect("depense");
    } 
}
