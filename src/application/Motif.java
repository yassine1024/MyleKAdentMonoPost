package application;

public class Motif {

	private int motif_id;
	private String nom;
	public Motif(int motif_id, String nom) {
		super();
		this.motif_id = motif_id;
		this.nom = nom;
	}
	public int getMotif_id() {
		return motif_id;
	}
	public void setMotif_id(int motif_id) {
		this.motif_id = motif_id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
}
