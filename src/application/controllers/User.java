package application.controllers;

public class User {
	
	private int id;
	private String nomFR;
	private String nomAR;
	private String specialtyFR;
	private String specialtyAR;
	private String address;
	private String municipality;
	private String town;
	private String password;
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNomFR() {
		return nomFR;
	}
	public void setNomFR(String nomFR) {
		this.nomFR = nomFR;
	}
	public String getMunicipality() {
		return municipality;
	}
	public void setMunicipality(String municipality) {
		this.municipality = municipality;
	}
	public String getTown() {
		return town;
	}
	public void setTown(String town) {
		this.town = town;
	}
	private String phoneNum;
	private String mail;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return nomFR;
	}
	public void setNom(String nom) {
		this.nomFR = nom;
	}
	public String getNomAR() {
		return nomAR;
	}
	public void setNomAR(String nomAR) {
		this.nomAR = nomAR;
	}
	public String getSpecialtyFR() {
		return specialtyFR;
	}
	public void setSpecialtyFR(String specialtyFR) {
		this.specialtyFR = specialtyFR;
	}
	public String getSpecialtyAR() {
		return specialtyAR;
	}
	public void setSpecialtyAR(String specialtyAR) {
		this.specialtyAR = specialtyAR;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getMail() {
		return mail;
	}
	public User(int id, String nomFR, String nomAR, String specialtyFR, String specialtyAR, String address,
			String phoneNum, String mail) {
		super();
		this.id = id;
		this.nomFR = nomFR;
		this.nomAR = nomAR;
		this.specialtyFR = specialtyFR;
		this.specialtyAR = specialtyAR;
		this.address = address;
		this.phoneNum = phoneNum;
		this.mail = mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	
	
	

}
