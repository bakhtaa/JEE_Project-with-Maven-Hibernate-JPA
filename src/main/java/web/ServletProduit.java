package web;

import dao.GestionProduitImpJpa;
import dao.IGestionProduit;
import model.Produit;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/app.php")
public class ServletProduit extends HttpServlet {
    private IGestionProduit gestion;

    @Override
    public void init() throws ServletException {
    	gestion = new GestionProduitImpJpa () ;
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response)
            throws ServletException, IOException {
        // Recuperation des donnees via le DAO
        List<Produit> liste = gestion.getAllProduits();

        // Stockage dans le scope request
        request.setAttribute("listep", liste);

        // Transfert vers la vue JSP
        request.getRequestDispatcher("vue.jsp").forward(request, response);
    }
}