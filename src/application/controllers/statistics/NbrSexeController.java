package application.controllers.statistics;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.ResourceBundle;

import application.SQLiteJDBC;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.StackPane;

public class NbrSexeController implements Initializable {

	@FXML
	private CategoryAxis x;

	@FXML
	private NumberAxis y;

	@FXML
	private StackPane root;

	@FXML
	private BarChart<String, Number> sexeBarChart;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		try {
			this.initializeBarChart();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		XYChart.Series set1 = new XYChart.Series<>();

		
		set1.getData().add(new XYChart.Data("Féminin", this.sexeNumberHashTable.get("Féminin")));
		set1.getData().add(new XYChart.Data("Masculin", this.sexeNumberHashTable.get("Masculin")));

		sexeBarChart.getData().addAll(set1);
		// TODO Auto-generated method stub
		/*
		 * ColorAdjust color= new ColorAdjust(); color.setBrightness(+1.0);
		 * root.setEffect(color);
		 */

	}

	private Hashtable<String, Integer> sexeNumberHashTable;

	private void initializeBarChart() throws SQLException {

		sexeNumberHashTable = new Hashtable<String, Integer>();

		Statement stm = new SQLiteJDBC().getConnection().createStatement();
		ResultSet rs = null;

		String request = "SELECT sexe,COUNT(malade_id) FROM malades GROUP BY sexe";

		rs = stm.executeQuery(request);

		while (rs.next()) {
			System.out.println(rs.getString(1) + " : " + rs.getInt(2));
			if(rs.getString(1)!= null)
			this.sexeNumberHashTable.put(rs.getString(1), rs.getInt(2));
		}

	}

}
