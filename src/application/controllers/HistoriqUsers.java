package application.controllers;

public class HistoriqUsers {

	private int id;
	private String date;
	private String user;
	private String action;
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
	public String getUser() {
		return user;
	}
	public void setUser(String user) {
		this.user = user;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public HistoriqUsers(int id, String date, String user, String action) {
		super();
		this.id = id;
		this.date = date;
		this.user = user;
		this.action = action;
	}
	
	
	
}
