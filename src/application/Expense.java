package application;

public class Expense {

	private double expense;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public double getExpense() {
		return expense;
	}
	public void setExpense(double expense) {
		this.expense = expense;
	}
	public Expense(double expense, String date) {
		super();
		this.expense = expense;
		this.date = date;
	}
	private String date;
}
