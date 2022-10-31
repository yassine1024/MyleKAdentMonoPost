package application;

import com.jfoenix.controls.JFXCheckBox;

public class FicheNavette {
	
	private int id;
	private String fullName;
	private String color;
	private String date;
	private float quantity;
	private String remarque;
	private String type;
	private double price;
	private double totalePrice;
	private JFXCheckBox checkBox;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public FicheNavette(int id, String fullName, String color, String date, float quantity, String remarque, String type, double price) {
		super();
		this.id = id;
		this.fullName = fullName;
		this.color = color;
		this.date = date;
		this.quantity = quantity;
		this.remarque = remarque;
		this.type = type;
		this.price=price;
		this.totalePrice= this.price*quantity;
		this.checkBox = new JFXCheckBox();
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getTotalePrice() {
		return totalePrice;
	}
	public void setTotalePrice(double totalePrice) {
		this.totalePrice = totalePrice;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public float getQuantity() {
		return quantity;
	}
	public void setQuantity(float quantity) {
		this.quantity = quantity;
	}
	public String getRemarque() {
		return remarque;
	}
	public void setRemarque(String remarque) {
		this.remarque = remarque;
	}
	public JFXCheckBox getCheckBox() {
		return checkBox;
	}
	public void setCheckBox(JFXCheckBox checkBox) {
		this.checkBox = checkBox;
	}
	
	
	

}
