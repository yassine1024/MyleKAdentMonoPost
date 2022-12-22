package application.controllers.expense;

import java.net.URL;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.NumberValidator;
import com.jfoenix.validation.RequiredFieldValidator;

import application.SQLiteJDBC;
import application.controllers.login.ActivationController;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class ExpenseController implements Initializable {

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		// Validate the montant if it's number
		NumberValidator validatorExpense = new NumberValidator();

		this.expense.getValidators().add(validatorExpense);
		validatorExpense.setMessage("Ce champ doit être un numéro");

		this.expense.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {
					expense.validate();
				}
			}
		});

		// Set Light/Dark mode as user chosed
		this.setLightDarkMode();

	}

	public void setLightDarkMode() {

		if (!ActivationController.getMode()) {
			this.stackPane.getStylesheets().clear();

			this.stackPane.getStylesheets().add("/css/light_mode/buttonSyle.css");
			this.stackPane.getStylesheets().add("/css/light_mode/styles.css");
			this.stackPane.getStylesheets().add("/css/light_mode/styles2.css");
		}
	}

	@FXML
	private Button btnSave;

	@FXML
	private JFXTextField expense;

	@FXML
	private JFXTextArea reasonExpense;

	@FXML
	private StackPane stackPane;

	@FXML
	void addExpense(ActionEvent event) {

		// Get local date
		LocalDate myDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		if (!this.expense.validate()) {
			this.expense.setText("");
			return;
		} else {

			PreparedStatement stmt = null;
			String request = "INSERT INTO expenses(expense, date_heur, motif)" + " VALUES(?,?,?); ";

			try {

				stmt = new SQLiteJDBC().getConnection().prepareStatement(request);

				stmt.setFloat(1, Float.parseFloat(this.expense.getText()));
				stmt.setString(2, myFormat.format(myDate).toString());
				stmt.setString(3, this.reasonExpense.getText());

				stmt.executeUpdate();
				stmt.close();

				Stage stage = (Stage) this.btnSave.getScene().getWindow();
				stage.close();

			} catch (Exception e) {
				// TODO: handle exception
			}
		}

	}

	@FXML
	void changePositionExpense(KeyEvent event) {

	}

}
