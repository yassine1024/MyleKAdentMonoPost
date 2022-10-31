package application.controllers.appointment;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTimePicker;
import com.jfoenix.validation.RequiredFieldValidator;

import application.SQLiteJDBC;
import application.controllers.HomeController;
import application.controllers.login.ActivationController;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AppointmentController implements Initializable {

	@FXML
	private Button btnSave;

	@FXML
	private JFXTextField name;

	@FXML
	private JFXTextField phone;

	@FXML
	private JFXDatePicker date;

	@FXML
	private JFXTimePicker hour;

	@FXML
	private StackPane stackPane;

	private SQLiteJDBC sl;
	public static int switcher = 0;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		//Initialize patients list in appointement
		TextFields.bindAutoCompletion(this.name, ActivationController.patientListComplete);
		
		Tooltip ttDetailed = new Tooltip();
		ttDetailed.setText("Pour enregistrer R.V.");
		ttDetailed.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		btnSave.setTooltip(ttDetailed);
		
		// TODO Auto-generated method stub
		sl = new SQLiteJDBC();

		RequiredFieldValidator validatorName = new RequiredFieldValidator();

		this.name.getValidators().add(validatorName);
		validatorName.setMessage("Le nom  est obligatoire");

		this.name.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {
					name.validate();
				}
			}
		});

		RequiredFieldValidator validatorPhone = new RequiredFieldValidator();

		this.phone.getValidators().add(validatorPhone);
		validatorPhone.setMessage("Le t�l�phone  est obligatoire");

		this.phone.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {
					phone.validate();
				}
			}
		});

		RequiredFieldValidator validatorDate = new RequiredFieldValidator();

		this.date.getValidators().add(validatorDate);
		validatorDate.setMessage("La date  est obligatoire");

		this.date.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {

					date.validate();
				} else {
					/*
					 * if(date.getValue().toString()=="2020-12-05") {
					 * 
					 * validatorDate.setMessage("Cette Date"); //date.validate(); }
					 */
				}
			}
		});

		RequiredFieldValidator validatorHour = new RequiredFieldValidator();

		this.hour.getValidators().add(validatorHour);
		validatorHour.setMessage("L'heure  est obligatoire");

		this.hour.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {
					hour.validate();
				}
			}
		});

	}

	@FXML
	void addAppointment(ActionEvent event) {

		System.out.println("validate date " + this.date.validate());
		System.out.println("validate phone " + this.phone.validate());
		System.out.println("validate hour " + this.hour.validate());

		if (!this.name.validate() || !this.phone.validate() || !this.date.validate() || !this.hour.validate()) {
			return;
		} else if (ActivationController.hashTableAppointment
				.containsKey(this.date.getValue().toString() + " " + this.hour.getValue().toString())) {
			String dateS = this.date.getValue().toString();
			String hourS = this.hour.getValue().toString();
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Duplication des données"));
			layout.setBody(new Text("Le rendez-vous dans" + dateS + " à " + hourS + " de monsieur(madame) "
					+ ActivationController.hashTableAppointment.get(dateS + " " + hourS) + " existe déja"));

			JFXAlert dialog = new JFXAlert();

			dialog.setContent(layout);
			JFXButton ok = new JFXButton("OK");
			ok.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					dialog.close();

				}
			});

			layout.setActions(ok);
			dialog.show();

		}

		else {

			PreparedStatement stmt = null;
			String request = "INSERT INTO appointment(nom, phone, appointment_date, appointment_hour)"
					+ " VALUES(?,?,?,?); ";

			try {
				stmt = sl.getConnectionAppointment().prepareStatement(request);

				stmt.setString(1, this.name.getText());
				stmt.setString(2, this.phone.getText());
				stmt.setString(3, this.date.getValue().toString());
				stmt.setString(4, this.hour.getValue().toString());
				stmt.executeUpdate();

				/*
				 * JFXDialogLayout layout = new JFXDialogLayout(); layout.setHeading(new
				 * Text("Ajout d'un rendez-vous")); layout.setBody(new
				 * Text("L'ajout du rendez a été accompli avec succés"));
				 * 
				 * JFXDialog dialog = new JFXDialog(stackPane, layout,
				 * JFXDialog.DialogTransition.CENTER);
				 * 
				 * JFXButton ok = new JFXButton("OK"); ok.setOnAction(new
				 * EventHandler<ActionEvent>() {
				 * 
				 * @Override public void handle(ActionEvent event) { dialog.close();
				 * 
				 * } });
				 * 
				 * layout.setActions(ok); dialog.show();
				 */

				System.out.println("Added in hour " + this.hour.getValue().toString());

				switcher = 1;
				Stage stage = (Stage) stackPane.getScene().getWindow();

				stage.close();

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	public static int getSwitcher() {
		return switcher;
	}

	public static void setSwitcher(int switcher) {
		AppointmentController.switcher = switcher;
	}

	@FXML
	void changePositionFullName(KeyEvent event) {

		switch (event.getCode()) {
		case DOWN:
			this.phone.requestFocus();
			break;

		}

	}

	@FXML
	void changePositionPhone(KeyEvent event) {

		switch (event.getCode()) {
		case DOWN:
			this.phone.requestFocus();
			break;
		case UP:
			this.phone.requestFocus();
			break;

		}

	}

}
