package application;

public class EtatGeneret {
private int etatGeneret_id;
private String nom;
public EtatGeneret(int etatGeneret_id, String nom) {
	super();
	this.etatGeneret_id = etatGeneret_id;
	this.nom = nom;
}
public int getEtatGeneret_id() {
	return etatGeneret_id;
}
public void setEtatGeneret_id(int etatGeneret_id) {
	this.etatGeneret_id = etatGeneret_id;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}

}
