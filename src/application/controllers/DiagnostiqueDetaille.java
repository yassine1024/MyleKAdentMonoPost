package application.controllers;

public class DiagnostiqueDetaille {

	private String date;
	private String acte;
	private float paye;
	private float reste;
	private int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getActe() {
		return acte;
	}

	public void setActe(String acte) {
		this.acte = acte;
	}

	public float getPaye() {
		return paye;
	}

	public void setPaye(float paye) {
		this.paye = paye;
	}

	public float getReste() {
		return reste;
	}

	public void setReste(float reste) {
		this.reste = reste;
	}

	public DiagnostiqueDetaille(String date, String acte, float paye, float reste) {
		super();
		this.date = date;
		this.acte = acte;
		this.paye = paye;
		this.reste = reste;
	}

	public DiagnostiqueDetaille(String date, String acte, float paye, float reste, int id) {
		super();
		this.date = date;
		this.acte = acte;
		this.paye = paye;
		this.reste = reste;
		this.id = id;
	}

}
