
package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import model.Utilisateur;

public class GestionUtilisateurImplJpa implements IGestionUtilisateur {

    private EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("MonUniteDePersistence");

    @Override
    public Utilisateur authenticate(String username, String password) {

        EntityManager em = emf.createEntityManager();

        try {
            TypedQuery<Utilisateur> query = em.createQuery(
                "SELECT u FROM Utilisateur u " +
                "WHERE u.username = :username " +
                "AND u.password = :password",
                Utilisateur.class
            );

            query.setParameter("username", username);
            query.setParameter("password", password);

            return query.getResultList()
                        .stream()
                        .findFirst()
                        .orElse(null);

        } finally {
            em.close();
        }
    }
}
