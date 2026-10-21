package dao;

import model.Utilisateur;

public interface IGestionUtilisateur {
	
	//une méthode d’authentification: Retourne l’objet Utilisateur si les credentials sont corrects, null sinon
	Utilisateur authenticate(String username, String password);
}
