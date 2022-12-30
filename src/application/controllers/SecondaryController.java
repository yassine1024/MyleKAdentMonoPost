package application.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import application.SQLiteJDBC;
import application.controllers.login.ActivationController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class SecondaryController implements Initializable {

	@FXML
	private JFXTextField orderMedicament;

	@FXML
	private JFXTextField orderType;

	

	@FXML
	private JFXTextField OrderNumber;

	@FXML
	private JFXTextArea orderHow;
	
	@FXML
	private JFXTextField quantity;

	private ArrayList<String> medications;
	private void autoCompleteOrderMedication() {
		medications=new ArrayList<String>();
		
		SQLiteJDBC sl = new SQLiteJDBC();
		Statement stm = null;
		ResultSet rs = null;

		try {
			stm = sl.getConnection().createStatement();
			String request = "SELECT DISTINCT nom FROM medications";
			rs = stm.executeQuery(request);
			while (rs.next()) {

				medications.add(rs.getString("nom"));
				// auto.add(new Medication(Integer.parseInt(rs.getString("medication_id")),
				// rs.getString("nom")));
			}
			}catch (Exception e) {
				// TODO: handle exception
			}finally {
				try {
					rs.close();
					stm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			TextFields.bindAutoCompletion(orderMedicament, medications);

	}
	
	
	@FXML
	private VBox stackPane;
	
public void setLightDarkMode() {
		
		if(!ActivationController.getMode()) {
			this.stackPane.getStylesheets().clear();
			
			this.stackPane.getStylesheets().add("/css/light_mode/buttonSyle.css");
		
			this.stackPane.getStylesheets().add("/css/light_mode/styles.css");
		}
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		this.setLightDarkMode();

	this.autoCompleteOrderMedication();
	}

	public TextField getOrderMedicament() {
		return orderMedicament;
	}

	public void setOrderMedicament(JFXTextField orderMedicament) {
		this.orderMedicament = orderMedicament;
	}

	public JFXTextField getOrderType() {
		return orderType;
	}

	public void setOrderType(JFXTextField orderType) {
		this.orderType = orderType;
	}

	

	public JFXTextField getOrderNumber() {
		return OrderNumber;
	}

	public void setOrderNumber(JFXTextField orderNumber) {
		OrderNumber = orderNumber;
	}

	/*public JFXTextField getOrderButtleDay() {
		return OrderButtleDay;
	}

	public void setOrderButtleDay(JFXTextField orderButtleDay) {
		OrderButtleDay = orderButtleDay;
	}
*/
	public TextArea getOrderHow() {
		return orderHow;
	}

	public void setOrderHow(JFXTextArea orderHow) {
		this.orderHow = orderHow;
	}

	public JFXTextField getQuantity() {
		return quantity;
	}

	public void setQuantity(JFXTextField quantity) {
		this.quantity = quantity;
	}

}
