
package dao;

import java.util.List;

import javax.persistence.*;
import model.Categorie;

public class GestionCategorieImpJpa implements IGestionCategorie {

    private EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("MonUniteDePersistence");

    private EntityManager em = emf.createEntityManager();

    // ---- READ (toutes les catégories) ----
    @Override
    public List<Categorie> getAllCategories() {
        return em.createQuery(
            "SELECT c FROM Categorie c", Categorie.class
        ).getResultList();
    }

    // ---- READ (par id) ----
    @Override
    public Categorie getCategorieById(Long id) {
        return em.find(Categorie.class, id);
    }

    // ---- CREATE ----
    @Override
    public void addCategorie(Categorie c) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(c);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    // ---- UPDATE ----
    @Override
    public void updateCategorie(Categorie c) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(c);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }

    // ---- DELETE ----
    @Override
    public void deleteCategorie(Long id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            Categorie c = em.find(Categorie.class, id);

            if (c != null) {
                em.remove(c);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        }
    }
}