package web;

import dao.GestionCategorieImpJpa;
import dao.GestionProduitImpJpa;
import dao.IGestionCategorie;
import dao.IGestionProduit;
import model.Categorie;
import model.Produit;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(urlPatterns = {
	    "/", "/accueil", "/search", "/delete", "/update", "/save"
	})
	public class FirstServlet extends HttpServlet {
	    private IGestionProduit gestion;
	    private IGestionCategorie gestionCategorie;

	    @Override
	    public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	        gestion = new GestionProduitImpJpa ();
	        gestionCategorie = new GestionCategorieImpJpa();

	    }

	    @Override
	    protected void doGet(HttpServletRequest request,
	                         HttpServletResponse response)
	            throws ServletException, IOException {

	        String path = request.getServletPath();

	        if (path.equals("/search")) {
	            String mc = request.getParameter("mc");
	            if (mc == null) mc = "";

	            List<Produit> products = gestion.searchProduits(mc);
	            request.setAttribute("liste", products);
	            System.out.println(request.getAttribute("liste"));
	            request.setAttribute("mc", mc);
	        } 
	        else if (path.equals("/delete")) {
	            long id = Long.parseLong(request.getParameter("id"));
	            gestion.deleteProduit(id);
	            response.sendRedirect("accueil");
	            return;
	        }
	        else if (path.equals("/update")) {
	            long id = Long.parseLong(request.getParameter("id"));
	            Produit p = gestion.getProductById(id);
	            request.setAttribute("produit", p);
	            request.getRequestDispatcher("ajouterProduit.jsp")
	                   .forward(request, response);
	            return;
	        }
	        else {
	            List<Produit> products = gestion.getAllProduits();
	            request.setAttribute("liste", products);
	            System.out.println(request.getAttribute("liste"));
	            request.setAttribute("mc", "");
	        }

	        request.getRequestDispatcher("afficher_produits.jsp")
	               .forward(request, response);
	    }
	    
	    
	    
	    @Override
	    protected void doPost(HttpServletRequest request,
	                          HttpServletResponse response)
	            throws ServletException, IOException {

	        if (request.getServletPath().equals("/save")) {
	            long id = Long.parseLong(request.getParameter("id"));
	            String nom = request.getParameter("nom");
	            double prix = Double.parseDouble(request.getParameter("prix"));
	            int quantite = Integer.parseInt(request.getParameter("quantite"));
	            String categorieIdParam = request.getParameter("categorieId");

	            long categorieId = Long.parseLong(categorieIdParam);

	            Categorie categorie = gestionCategorie.getCategorieById(categorieId);

	            Produit p = new Produit(id, nom, prix, quantite,categorie);

	            if (id == 0) {
	                gestion.addProduit(p); // INSERT
	            } else {
	                gestion.updateProduit(p); // UPDATE
	            }
	            response.sendRedirect("accueil");
	        }
	    }
	    
	    
	}