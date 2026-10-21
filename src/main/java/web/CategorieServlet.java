
package web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GestionCategorieImpJpa;
import dao.IGestionCategorie;
import model.Categorie;

@WebServlet(urlPatterns = {"/categories","/categorieForm", "/ajouterProduit"})
public class CategorieServlet extends HttpServlet {

    private IGestionCategorie gestionCategorie;

    @Override
    public void init() throws ServletException {
        gestionCategorie = new GestionCategorieImpJpa();
    }

    // Vérification du rôle ADMIN
    private boolean isAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession(false);

        return session != null
            && "ADMIN".equals(session.getAttribute("role"));
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        String chemin = request.getServletPath();
        String action = request.getParameter("action");

        // 1. Afficher le formulaire d'ajout d'un produit
        if ("/ajouterProduit".equals(chemin)) {

            List<Categorie> categories =
                    gestionCategorie.getAllCategories();

            request.setAttribute("categories", categories);
            System.out.println("Nombre de catégories : "
                    + (categories == null ? "null" : categories.size()));
            
            System.out.println(">>> Route ajouterProduit atteinte");
            System.out.println(">>> Nombre de catégories : "
                + (categories == null ? "null" : categories.size()));

            if (categories != null) {
                for (Categorie c : categories) {
                    System.out.println(c.getId() + " - " + c.getNom());
                }
            }


            if (categories != null) {
                for (Categorie c : categories) {
                    System.out.println(c.getId() + " - " + c.getNom());
                }
            }
            request.getRequestDispatcher("/ajouterProduit.jsp")
                   .forward(request, response);
            return;
        }

        // 2. Gestion des catégories
        if ("/categories".equals(chemin)) {

            if (action == null || action.equals("list")) {

                List<Categorie> categories =
                        gestionCategorie.getAllCategories();

                request.setAttribute("categories", categories);

                request.getRequestDispatcher("/categories.jsp")
                       .forward(request, response);
                return;

            } else if (action.equals("form")) {

                request.getRequestDispatcher("/categorieForm.jsp")
                       .forward(request, response);
                return;

            } else if (action.equals("edit")) {

                Long id = Long.valueOf(request.getParameter("id"));

                Categorie categorie =
                        gestionCategorie.getCategorieById(id);

                if (categorie == null) {
                    response.sendError(
                            HttpServletResponse.SC_NOT_FOUND);
                    return;
                }

                request.setAttribute("categorie", categorie);

                request.getRequestDispatcher("/categorieForm.jsp")
                       .forward(request, response);
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_BAD_REQUEST);
    }
    

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        if (!isAdmin(request)) {
            response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return;
        }

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        if ("add".equals(action)) {

            Categorie categorie = new Categorie();
            categorie.setNom(request.getParameter("nom"));

            gestionCategorie.addCategorie(categorie);

        } else if ("update".equals(action)) {

            Long id = Long.valueOf(request.getParameter("id"));

            Categorie categorie =
                gestionCategorie.getCategorieById(id);

            if (categorie == null) {
                response.sendError(
                    HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            categorie.setNom(request.getParameter("nom"));

            gestionCategorie.updateCategorie(categorie);

        } else if ("delete".equals(action)) {

            Long id = Long.valueOf(request.getParameter("id"));

            gestionCategorie.deleteCategorie(id);

        } else {
            response.sendError(
                HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        // Redirection après traitement pour éviter
        // de répéter l'opération lors d'un rafraîchissement.
        response.sendRedirect(
            request.getContextPath() + "/categories");
    }
}
