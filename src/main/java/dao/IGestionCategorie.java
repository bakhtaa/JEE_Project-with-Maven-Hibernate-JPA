package dao;

import java.util.List;

import model.Categorie;

public interface IGestionCategorie {
//lister toutes les catégories
	List<Categorie> getAllCategories();
	
	//retrouver une catégorie par son identifiant
	Categorie getCategorieById(Long id);
	
	
	//ajouter une nouvelle catégorie
	void addCategorie(Categorie c);
	
	
	//modifier une catégorie existante
	void updateCategorie(Categorie c);
	
	
	//supprimer une catégorie par son identifiant
	void deleteCategorie(Long id);
	
	
}
