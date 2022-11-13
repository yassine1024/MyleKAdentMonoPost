package application.controllers.statistics;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import application.Benefit;
import application.Expense;
import application.SQLiteJDBC;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class ExpenseProftController implements Initializable {

	@FXML
	private LineChart<String, Float> expenseProfitChart;

	// We'll use these two arrays to draw the linechart
	private ArrayList<Benefit> allBenefits;
	private ArrayList<Expense> allExpenses;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		this.allBenefits = new ArrayList<Benefit>();
		this.allExpenses = new ArrayList<Expense>();

		this.intializeBenefit();
		this.intializeExpense();

		this.drawLineChartDaily();
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
			request = " SELECT date,benefit FROM benefits ORDER BY date ASC ; ";
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

	private void drawLineChartDaily() {

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
	

}
