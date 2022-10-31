package application.controllers;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.SQLiteJDBC;
import application.controllers.login.ActivationController;
import javafx.scene.control.ComboBox;

public class Malade {
	private int id;
public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
private String nom;
private String prenom;
private String adresse;
private String phone;
private String sexe;
private int age;
private String profession;
private String acte;
private float paye;
private String dateArriver;
private int hmID;
private int user;


public int getUser() {
	return user;
}
public void setUser(int user) {
	this.user = user;
}
public int getHmID() {
	return hmID;
}
public void setHmID(int hmID) {
	this.hmID = hmID;
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
public String getDateArriver() {
	return dateArriver;
}
public void setDateArriver(String dateArriver) {
	this.dateArriver = dateArriver;
}
private ComboBox<String> listConsultation;

public String getNom() {
	return nom;
}
public void setNom(String nom) {
	this.nom = nom;
}
public String getPrenom() {
	return prenom;
}
public void setPrenom(String prenom) {
	this.prenom = prenom;
}
public String getAdresse() {
	return adresse;
}
public void setAdresse(String adresse) {
	this.adresse = adresse;
}
public String getPhone() {
	return phone;
}
public void setPhone(String phone) {
	this.phone = phone;
}
public String getSexe() {
	return sexe;
}
public void setSexe(String sexe) {
	this.sexe = sexe;
}
public int getAge() {
	return age;
}
public Malade(int id,String nom, String prenom, String adresse, String phone, String sexe, int age,String profession,ComboBox<String> listConsultation,float paye,String dateArriver,int hmID,int user) {
	this.id=id;
	this.nom = nom;
	this.prenom = prenom;
	this.adresse = adresse;
	this.phone = phone;
	this.sexe = sexe;
	this.age = age;
	this.profession=profession;
	this.listConsultation=listConsultation;
	//this.acte=acte;
	this.paye=paye;
	this.dateArriver=dateArriver;
	this.hmID=hmID;
	this.user = user;
	
	this.initialiseActe();
}
public void initialiseActe() {
	// TODO Auto-generated method stub
	this.acte="";
	Statement stm = null;
	ResultSet rs = null;
	try {
		stm = new SQLiteJDBC().getConnection().createStatement();
		String request = "SELECT D.diagnostic_id FROM diagnostics D, consultations C "
				+ " WHERE D.consultation_id=C.consultation_id "
				+ " AND C.malade_id='"+this.id+"' ";
						//+ " AND C.date_consultation='"+this.dateArriver+"';";

		rs = stm.executeQuery(request);
	
		while(rs.next()) {
			
			Statement stm2 = null;
			ResultSet rs2 = null;
			stm2=new SQLiteJDBC().getConnection().createStatement();
			String request2 = "SELECT DD.acte FROM diagnostics D, diagnostics_detaille DD "
					+ " WHERE D.diagnostic_id=DD.diagnostic_id "
					+ " AND D.diagnostic_id='"+rs.getInt(1)+"' "
							+ " AND DD.date_payement='"+ActivationController.dateListeString+"';";
			rs2=stm2.executeQuery(request2);
			
			while(rs2.next()) {
				System.out.println("soussem");
				this.acte += rs2.getString(1)+", ";
			}
		}
	
	}catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} finally {

		try {
			rs.close();
			stm.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	System.out.println(this.acte);
}
public String getProfession() {
	return profession;
}
public void setProfession(String profession) {
	this.profession = profession;
}
public ComboBox<String> getListConsultation() {
	return listConsultation;
}
public void setListConsultation(ComboBox<String> listConsultation) {
	this.listConsultation = listConsultation;
}
public void setAge(int age) {
	this.age = age;
}


}
