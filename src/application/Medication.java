package application;

public class Medication {
private int medication_id;
private String nom;
public int getMedication_id() {
	return medication_id;
}
public void setMedication_id(int medication_id) {
	this.medication_id = medication_id;
}
public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public Medication(int medication_id, String nom) {
	super();
	this.medication_id = medication_id;
	this.nom = nom;
}

}
