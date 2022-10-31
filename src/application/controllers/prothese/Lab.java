package application.controllers.prothese;

public class Lab {

	public Lab(int labId, String nom, String addresse, String phone) {
		super();
		this.labId = labId;
		this.nom = nom;
		this.addresse = addresse;
		this.phone = phone;
	}
	public int getLabId() {
		return labId;
	}
	public void setLabId(int labId) {
		this.labId = labId;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAddresse() {
		return addresse;
	}
	public void setAddresse(String addresse) {
		this.addresse = addresse;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	private int labId;
	private String nom;
	private String addresse;
	private String phone;
	
	
}
