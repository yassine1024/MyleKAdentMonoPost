package application;

public class Benefit {
	
	private double benefit;
	private String date;
	public double getBenefit() {
		return benefit;
	}
	public void setBenefit(double benefit) {
		this.benefit = benefit;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public Benefit(double benefit, String date) {
		super();
		this.benefit = benefit;
		this.date = date;
	}
	
	

}
