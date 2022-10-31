package application.controllers.statistics;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import application.SQLiteJDBC;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;

public class ageStatisticController implements Initializable {

	@FXML
	private StackPane pieChartNbr;

	@FXML
	private Label caption;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		// piechart data
		PieChart.Data data[] = new PieChart.Data[5];

		// string and integer data
		

		/*
		 * String status[] = {"Correct Answer", "Wrong Answer", "Compilation Error",
		 * "Runtime Error", "Others" };
		 */
		int category1 = 0;
		int category2 = 0;
		int category3 = 0;
		int category4 = 0;
		int category5 = 0;

		Statement stm = null;
		ResultSet rs = null;

		String request = "SELECT age FROM malades";
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			rs = stm.executeQuery(request);

			while (rs.next()) {

				System.out.println(rs.getInt(1));
				if (rs.getInt(1) >= 0 && rs.getInt(1) < 15) {
					category1++;
				} else if (rs.getInt(1) >= 15 && rs.getInt(1) < 30) {
					category2++;
				} else if (rs.getInt(1) >= 30 && rs.getInt(1) < 45) {
					category3++;
				} else if (rs.getInt(1) >= 45 && rs.getInt(1) < 60) {
					category4++;
				} else if (rs.getInt(1) >= 60) {
					category5++;
				}
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			System.out.println(e.getMessage());

			e.printStackTrace();
		}

		String status[] = { "[0-15["+category1, "[15-30["+category2, "[30-45["+category3, "[45-60["+category4, "[60-Infénie["+category5 };
		 int values[] = {category1, category2, category3, category4, category5};
		//int values[] = { 20, 30, 10, 4, 2 };
		for (int i = 0; i < 5; i++) {
			data[i] = new PieChart.Data(status[i], values[i]);
		}

		PieChart pieChart = new PieChart(FXCollections.observableArrayList(data));
		pieChart.setTitle("Nbr patient par age");
		pieChart.setStyle("-fx-cursor: hand");
		
		
        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font: 24 arial;");

        for ( PieChart.Data data2 : pieChart.getData()) {
        	data2.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                e -> {
                    double total = 0;
                    for (PieChart.Data d : pieChart.getData()) {
                        total += d.getPieValue();
                    }
                    caption.setLayoutX(e.getSceneX());
                    caption.setLayoutY(e.getSceneY());
                    caption.toFront();
                   // caption.setTranslateX(e.getSceneX());
                    //caption.setTranslateY(e.getSceneY());
                    String text = String.format("%.1f%%", 100*data2.getPieValue()/total) ;
                    caption.setText(text);
                 }
                );
        }

		
		

		this.pieChartNbr.getChildren().add(pieChart);
		// create a pie chart

	}

}
