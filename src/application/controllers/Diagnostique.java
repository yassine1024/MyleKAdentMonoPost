package application.controllers;

public class Diagnostique {
private int ordre;
private String diagnostique;
private String traitement ;
private float devis;
private int id;
public int getOrdre() {
	return ordre;
}
public void setOrdre(int ordre) {
	this.ordre = ordre;
}
public String getDiagnostique() {
	return diagnostique;
}
public void setDiagnostique(String diagnostique) {
	this.diagnostique = diagnostique;
}
public String getTraitement() {
	return traitement;
}
public void setTraitement(String traitement) {
	this.traitement = traitement;
}
public float getDevis() {
	return devis;
}
public void setDevis(float devis) {
	this.devis = devis;
}
public Diagnostique(int ordre, String diagnostique, String traitement, float devis) {
	
	this.ordre = ordre;
	this.diagnostique = diagnostique;
	this.traitement = traitement;
	this.devis = devis;
}
public Diagnostique(int ordre, String diagnostique, String traitement, float devis,int id) {
	
	this.ordre = ordre;
	this.diagnostique = diagnostique;
	this.traitement = traitement;
	this.devis = devis;
	this.id=id;
}
public int getId() {
	return id;
}
public void setId(int id) {
	this.id = id;
}



}
