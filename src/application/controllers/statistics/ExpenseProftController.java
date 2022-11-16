package application.controllers.statistics;


import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXComboBox;

import application.Benefit;
import application.Expense;
import application.SQLiteJDBC;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.scene.layout.StackPane;

public class ExpenseProftController implements Initializable {

	@FXML
	private LineChart<String, Float> expenseProfitChart;

	CategoryAxis xAxis;
	NumberAxis yAxis;

	
	// We'll use these two arrays to draw the linechart daily
	private ArrayList<Benefit> allBenefits;
	private ArrayList<Expense> allExpenses;

	// we'll use these two arrays to draw the linechart monthly
	private ArrayList<Benefit> allBenefitsMonthly;
	private ArrayList<Expense> allExpensesMonthly;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		this.allBenefits = new ArrayList<Benefit>();
		this.allExpenses = new ArrayList<Expense>();

		// first initalize benefit and expense arrayList
		this.intializeBenefit();
		this.intializeExpense();

		// then initialize benefit monthly and expense monthly arrayList
		this.intializeExpenseMonthly();
		this.intializeBenefitMonthly();
		
		
		this.initilizeFilterComboBox();
		
		this.expenseProfitChart.getXAxis().setAnimated(false);
		this.drawLineChartDaily();
		//this.drawLineChartMonthly();
	}

	  @FXML
	    private JFXComboBox<String> filterComboBox;

	private void initilizeFilterComboBox() {
		// TODO Auto-generated method stub
		
		 // Weekdays
        String affichage[] =
                   { "Affichage par jour", "Affichage par mois" };
 
        // Initilizing the filterBox
       this.filterComboBox.setItems(FXCollections
               .observableArrayList(affichage));
                   
 
       this.filterComboBox.getSelectionModel().select(0);
		
	}
	
	 @FXML
	    void changeFilter(ActionEvent event) {
		 
		 String filterAffichage = this.filterComboBox.getSelectionModel().getSelectedItem();
		 
		 if(filterAffichage.equals("Affichage par jour")) {
			 this.drawLineChartDaily();
		 }else {
			 
			 this.drawLineChartMonthly();
		 }
	    
	    }

		 

	private void intializeBenefit() {
		// TODO Auto-generated method stub

		Statement stm = null;
		ResultSet rs = null;
		String request = "";
		double totaleBenefit = 0;
		int counter = 0;
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = "SELECT date,benefit as month FROM benefits ORDER BY date ASC;";
			rs = stm.executeQuery(request);

			XYChart.Series<String, Float> expenseProfitChartSeries2 = new XYChart.Series<String, Float>();
			while (rs.next()) {

				this.allBenefits.add(new Benefit(rs.getFloat(2), rs.getString(1)));
				// expenseProfitChartSeries2.getData().add(new
				// XYChart.Data(rs.getString(1),rs.getFloat(2)));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void intializeBenefitMonthly() {
		// TODO Auto-generated method stub

		this.allBenefitsMonthly= new ArrayList<>();
		Statement stm = null;
		ResultSet rs = null;
		String request = "";
		double totaleBenefit = 0;
		int counter = 0;
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = "SELECT * FROM profit_sum_month ;";
			rs = stm.executeQuery(request);

		
			while (rs.next()) {

				this.allBenefitsMonthly.add(new Benefit(rs.getFloat(2), rs.getString(1)));
				System.out.println("Expense monthly " + rs.getFloat(2) + rs.getString(1));
				// expenseProfitChartSeries2.getData().add(new
				// XYChart.Data(rs.getString(1),rs.getFloat(2)));

			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void intializeExpense() {
		// TODO Auto-generated method stub

		Statement stm = null;
		ResultSet rs = null;
		String request = "";
		double totaleBenefit = 0;
		int counter = 0;
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = " SELECT * FROM expenses_sum  ; ";
			rs = stm.executeQuery(request);

			XYChart.Series<String, Float> expenseProfitChartSeries2 = new XYChart.Series<String, Float>();
			while (rs.next()) {
				// expenseProfitChartSeries2.getData().add(new
				// XYChart.Data(rs.getString(1),rs.getFloat(2)));
				// System.out.println(rs.getString(1)+": "+rs.getFloat(2));
				this.allExpenses.add(new Expense(rs.getFloat(2), rs.getString(1)));
			}
			// this.expenseProfitChart.getData().add(expenseProfitChartSeries2);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void intializeExpenseMonthly() {
		// TODO Auto-generated method stub

		this.allExpensesMonthly = new ArrayList<Expense>();
		Statement stm = null;
		ResultSet rs = null;
		String request = "";
		double totaleBenefit = 0;
		int counter = 0;
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = " SELECT * FROM expenses_sum_month; ";
			rs = stm.executeQuery(request);

			while (rs.next()) {

				this.allExpensesMonthly.add(new Expense(rs.getFloat(2), rs.getString(1)));
				System.out.println("Expenses monthly: " + rs.getFloat(2) + "  ====>  " + rs.getString(1));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void drawLineChartDaily() {

		//this.expenseProfitChart.getXAxis().setAutoRanging(false);
		this.expenseProfitChart.getData().clear();
		//this.expenseProfitChart.getData().remove(0, this.expenseProfitChart.getData().size());
		
		// Profit's data
		XYChart.Series<String, Float> profitChartSeries = new XYChart.Series<String, Float>();
		// Expense's data
		XYChart.Series<String, Float> expenseChartSeries = new XYChart.Series<String, Float>();

		// indicator of each of arrays
		int profitIterator = 0, expenseIterator = 0;

		// begin put data
		while (profitIterator < this.allBenefits.size() && expenseIterator < this.allExpenses.size()) {
			// case 1 if benefit's date before expense's date
			int profitVSexpense = this.allBenefits.get(profitIterator).getDate()
					.compareTo(this.allExpenses.get(expenseIterator).getDate());
			if (profitVSexpense < 0) {

				profitChartSeries.getData().add(new XYChart.Data(this.allBenefits.get(profitIterator).getDate(),
						this.allBenefits.get(profitIterator).getBenefit()));
				expenseChartSeries.getData().add(new XYChart.Data(this.allBenefits.get(profitIterator).getDate(), 0));

				profitIterator++;
				// case 2 if expense's data before profit's date
			} else if (profitVSexpense > 0) {

				expenseChartSeries.getData().add(new XYChart.Data(this.allExpenses.get(expenseIterator).getDate(),
						this.allExpenses.get(expenseIterator).getExpense()));
				profitChartSeries.getData().add(new XYChart.Data(this.allExpenses.get(expenseIterator).getDate(), 0));

				expenseIterator++;
				// case 3 if both dates are equals
			} else {

				profitChartSeries.getData().add(new XYChart.Data(this.allBenefits.get(profitIterator).getDate(),
						this.allBenefits.get(profitIterator).getBenefit()));
				expenseChartSeries.getData().add(new XYChart.Data(this.allExpenses.get(expenseIterator).getDate(),
						this.allExpenses.get(expenseIterator).getExpense()));

				profitIterator++;
				expenseIterator++;
			}

		}

		// check which list doesn't completed the iteration on it
		if (profitIterator < this.allBenefits.size()) {

			for (int i = profitIterator; i < this.allBenefits.size(); i++) {
				profitChartSeries.getData()
						.add(new XYChart.Data(this.allBenefits.get(i).getDate(), this.allBenefits.get(i).getBenefit()));
				expenseChartSeries.getData().add(new XYChart.Data(this.allBenefits.get(i).getDate(), 0));

			}

		} else if (expenseIterator < this.allExpenses.size()) {

			for (int i = expenseIterator; i < this.allExpenses.size(); i++) {

				expenseChartSeries.getData()
						.add(new XYChart.Data(this.allExpenses.get(i).getDate(), this.allExpenses.get(i).getExpense()));
				profitChartSeries.getData().add(new XYChart.Data(this.allExpenses.get(i).getDate(), 0));

			}
		}

		// Last we added series to lineChart

		profitChartSeries.setName("Bénéfices");
		expenseChartSeries.setName("Dépenses");

		this.expenseProfitChart.getData().add(profitChartSeries);
		this.expenseProfitChart.getData().add(expenseChartSeries);

	}

	
	private void drawLineChartMonthly() {

		
		//xAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(categories))); 
		//xAxis.setAutoRanging(false);
	
		//this.expenseProfitChart.getXAxis().setAutoRanging(false);
		this.expenseProfitChart.getData().clear();
		//this.expenseProfitChart.getData().remove(0, this.expenseProfitChart.getData().size());
		
		
		// Profit's data
		XYChart.Series<String, Float> profitChartSeriesMonth = new XYChart.Series<String, Float>();
		// Expense's data
		XYChart.Series<String, Float> expenseChartSeriesMonth = new XYChart.Series<String, Float>();

		// indicator of each of arrays
		int profitIterator = 0, expenseIterator = 0;

		// begin put data
		while (profitIterator < this.allBenefitsMonthly.size() && expenseIterator < this.allExpensesMonthly.size()) {
			// case 1 if benefit's date before expense's date
			int profitVSexpense = this.allBenefitsMonthly.get(profitIterator).getDate()
					.compareTo(this.allExpensesMonthly.get(expenseIterator).getDate());
			if (profitVSexpense < 0) {

				profitChartSeriesMonth.getData().add(new XYChart.Data(this.allBenefitsMonthly.get(profitIterator).getDate(),
						this.allBenefitsMonthly.get(profitIterator).getBenefit()));
				expenseChartSeriesMonth.getData().add(new XYChart.Data(this.allBenefitsMonthly.get(profitIterator).getDate(), 0));

				profitIterator++;
				// case 2 if expense's data before profit's date
			} else if (profitVSexpense > 0) {

				expenseChartSeriesMonth.getData().add(new XYChart.Data(this.allExpensesMonthly.get(expenseIterator).getDate(),
						this.allExpensesMonthly.get(expenseIterator).getExpense()));
				profitChartSeriesMonth.getData().add(new XYChart.Data(this.allExpensesMonthly.get(expenseIterator).getDate(), 0));

				expenseIterator++;
				// case 3 if both dates are equals
			} else {

				profitChartSeriesMonth.getData().add(new XYChart.Data(this.allBenefitsMonthly.get(profitIterator).getDate(),
						this.allBenefitsMonthly.get(profitIterator).getBenefit()));
				expenseChartSeriesMonth.getData().add(new XYChart.Data(this.allExpensesMonthly.get(expenseIterator).getDate(),
						this.allExpensesMonthly.get(expenseIterator).getExpense()));

				profitIterator++;
				expenseIterator++;
			}

		}

		// check which list doesn't completed the iteration on it
		if (profitIterator < this.allBenefitsMonthly.size()) {

			for (int i = profitIterator; i < this.allBenefitsMonthly.size(); i++) {
				profitChartSeriesMonth.getData()
						.add(new XYChart.Data(this.allBenefitsMonthly.get(i).getDate(), this.allBenefitsMonthly.get(i).getBenefit()));
				expenseChartSeriesMonth.getData().add(new XYChart.Data(this.allBenefitsMonthly.get(i).getDate(), 0));

			}

		} else if (expenseIterator < this.allExpensesMonthly.size()) {

			for (int i = expenseIterator; i < this.allExpensesMonthly.size(); i++) {

				expenseChartSeriesMonth.getData()
						.add(new XYChart.Data(this.allExpensesMonthly.get(i).getDate(), this.allExpensesMonthly.get(i).getExpense()));
				profitChartSeriesMonth.getData().add(new XYChart.Data(this.allExpensesMonthly.get(i).getDate(), 0));

			}
		}

		// Last we added series to lineChart

		profitChartSeriesMonth.setName("Bénéfices");
		expenseChartSeriesMonth.setName("Dépenses");

		this.expenseProfitChart.getData().add(profitChartSeriesMonth);
		this.expenseProfitChart.getData().add(expenseChartSeriesMonth);

	}

}
