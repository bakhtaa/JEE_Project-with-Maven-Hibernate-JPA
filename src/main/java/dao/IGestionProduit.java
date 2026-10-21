package dao;

import model.Produit;
import java.util.List;

public interface IGestionProduit  {
    // Ajouter un produit
    void addProduit(Produit p);

    // Supprimer un produit par ID
    void deleteProduit(long id);

    // Mettre à jour un produit
    void  updateProduit(Produit p);

    // Récupérer tous les produits
    List<Produit> getAllProduits();

    // Rechercher des produits par mot-cle
    List<Produit> searchProduits(String mc);

    // Recuperer un produit par ID
    Produit getProductById(long id);
    
    
    
    
    
    
}