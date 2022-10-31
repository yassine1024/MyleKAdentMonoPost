package application;

public class Agenda {
	
	private String hour;
	private String fullName;
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public Agenda(String hour, String fullName) {
		super();
		this.hour = hour;
		this.fullName = fullName;
	}
	
	
	

}
