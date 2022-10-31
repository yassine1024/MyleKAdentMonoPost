package application.controllers.prothese;

public class LabDetaille {
	
	private int labDetailleId;
	private String date;
	private String type;
	private int qte;
	private float prixUnitaire;
	private float prixGlobal;
	private int labId;
	private int ordre;
	public int getOrdre() {
		return ordre;
	}
	public void setOrdre(int ordre) {
		this.ordre = ordre;
	}
	public int getLabDetailleId() {
		return labDetailleId;
	}
	public void setLabDetailleId(int labDetailleId) {
		this.labDetailleId = labDetailleId;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getQte() {
		return qte;
	}
	public void setQte(int qte) {
		this.qte = qte;
	}
	public float getPrixUnitaire() {
		return prixUnitaire;
	}
	public void setPrixUnitaire(float prixUnitaire) {
		this.prixUnitaire = prixUnitaire;
	}
	public int getLabId() {
		return labId;
	}
	public void setLabId(int labId) {
		this.labId = labId;
	}
	public LabDetaille(int labDetailleId, String date, String type, int qte, float prixUnitaire, int labId) {
		super();
		this.labDetailleId = labDetailleId;
		this.date = date;
		this.type = type;
		this.qte = qte;
		this.prixUnitaire = prixUnitaire;
		this.labId = labId;
	}
	public LabDetaille(int labDetailleId, String date, String type, int qte, float prixUnitaire, float prixGlobal,
			int labId, int ordre) {
		super();
		this.labDetailleId = labDetailleId;
		this.date = date;
		this.type = type;
		this.qte = qte;
		this.prixUnitaire = prixUnitaire;
		this.prixGlobal = prixGlobal;
		this.labId = labId;
		this.ordre = ordre;
	}
	public LabDetaille(int labDetailleId, String date, String type, int qte, float prixUnitaire, float prixGlobal,
			int labId) {
		super();
		this.labDetailleId = labDetailleId;
		this.date = date;
		this.type = type;
		this.qte = qte;
		this.prixUnitaire = prixUnitaire;
		this.prixGlobal = prixGlobal;
		this.labId = labId;
	}
	public float getPrixGlobal() {
		return prixGlobal;
	}
	public void setPrixGlobal(float prixGlobal) {
		this.prixGlobal = prixGlobal;
	}
	
	
	
	

}
