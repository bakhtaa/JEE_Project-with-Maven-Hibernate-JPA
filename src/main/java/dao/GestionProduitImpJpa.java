package dao;


import java.util.List;
import model.Produit ;

import javax.persistence.*;


public class GestionProduitImpJpa implements IGestionProduit {

    private EntityManagerFactory emf =
        Persistence.createEntityManagerFactory("MonUniteDePersistence");
    private EntityManager em = emf.createEntityManager();

    // ---- CREATE ----
    @Override
    public void addProduit(Produit p) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(p); // INSERT INTO produit ...
            tx.commit();
        } catch (Exception e) {
            tx.rollback(); // Annulation en cas d'erreur
            e.printStackTrace();
        }
    }

    // ---- READ (tous les produits) ----
    @Override
    public List<Produit> getAllProduits() {
        // JPQL : langage de requete oriente objet (comme SQL mais sur les entites)
        return em.createQuery("SELECT p FROM Produit p  LEFT JOIN FETCH p.categorie").getResultList();
    }

    // ---- READ (par id) ----
    @Override
    public Produit getProductById(long id) {
        return em.find(Produit.class, id); // SELECT * WHERE id = ?
    }

    // ---- READ (recherche par nom) ----
    @Override
    public List<Produit> searchProduits(String motCle) {
        return em.createQuery(
                "SELECT p FROM Produit p WHERE p.nom LIKE :mc")
                .setParameter("mc", "%" + motCle + "%")
                .getResultList();
    }

    // ---- UPDATE ----
    @Override
    public void updateProduit(Produit p) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(p); // UPDATE produit SET ...
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }

    // ---- DELETE ----
    @Override
    public void deleteProduit(long id) {
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Produit p = em.find(Produit.class, id); 
            //SELECT * FROM Produit WHERE id = 2;
            if (p != null) {
                em.remove(p); // DELETE FROM produit WHERE id = ?
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
    }
}