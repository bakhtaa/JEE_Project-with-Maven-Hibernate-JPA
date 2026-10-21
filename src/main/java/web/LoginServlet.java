
package web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.GestionUtilisateurImplJpa;
import dao.IGestionUtilisateur;
import model.Utilisateur;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    private IGestionUtilisateur gestionUtilisateur;

    @Override
    public void init() throws ServletException {
        gestionUtilisateur = new GestionUtilisateurImplJpa();
    }

    // Afficher le formulaire de connexion
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.getRequestDispatcher("/login.jsp")
               .forward(request, response);
    }

    // Traiter la connexion
    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        Utilisateur utilisateur =
            gestionUtilisateur.authenticate(username, password);

        if (utilisateur != null) {

            // Créer une session pour l'utilisateur connecté
            HttpSession session = request.getSession(true);

            session.setAttribute("utilisateur", utilisateur);
            session.setAttribute("role", utilisateur.getRole());

            // Rediriger vers l'accueil
            response.sendRedirect(
                request.getContextPath() + "/"
            );

        } else {

            // Identifiants incorrects
            request.setAttribute(
                "erreur",
                "Nom d'utilisateur ou mot de passe incorrect."
            );

            request.getRequestDispatcher("/login.jsp")
                   .forward(request, response);
        }
    }
}
