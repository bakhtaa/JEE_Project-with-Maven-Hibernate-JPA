package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity

public class Categorie {
	 @Id @GeneratedValue ( strategy = GenerationType . IDENTITY )
	 private long id;
private String nom;

@OneToMany(
        mappedBy = "categorie",
        cascade = CascadeType.PERSIST,
        fetch = FetchType.LAZY
    )
private List<Produit> produits = new ArrayList<>();

public long getId() {
	return id;
}
public void setId(long id) {
	this.id = id;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}

}