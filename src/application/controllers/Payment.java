package application.controllers;

public class Payment {
private int numDiagnostique;
private String acte;
public int getNumDiagnostique() {
	return numDiagnostique;
}
public void setNumDiagnostique(int numDiagnostique) {
	this.numDiagnostique = numDiagnostique;
}
public String getActe() {
	return acte;
}
public void setActe(String acte) {
	this.acte = acte;
}
public Payment(int numDiagnostique, String acte) {
	
	this.numDiagnostique = numDiagnostique;
	this.acte = acte;
}

}
