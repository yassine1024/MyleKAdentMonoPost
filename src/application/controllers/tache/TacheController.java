package application.controllers.tache;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.SQLiteJDBC;
import application.controllers.login.ActivationController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class TacheController implements Initializable {

	private int user_id;
	private boolean success;
	
	@FXML
    private AnchorPane stackPane;


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public int getUser_id() {
		return user_id;
	}

	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	public TacheController(int user_id) {
		super();
		this.user_id = user_id;
		this.success = false;
	}

	@FXML
	private JFXTextField tache;

	@FXML
	private Button addButton;

	
	
	
	
	@FXML
    void addTache(ActionEvent event) {


		LocalDate myDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		PreparedStatement stmt = null;
		String task = this.tache.getText();
		String request = "INSERT INTO tasks(user_id,date_heur,task) VALUES(?,?,?)";
		try {
			stmt = new SQLiteJDBC().getConnection().prepareStatement(request);

			stmt.setInt(1, this.user_id);
			stmt.setString(2, myDate.format(myFormat));
			stmt.setString(3, task);
			stmt.executeUpdate();
			this.success = true;
			Stage stage = (Stage) addButton.getScene().getWindow();
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

		this.setLightDarkMode();
		
	}
public void setLightDarkMode() {
		
		if(!ActivationController.getMode()) {
			this.stackPane.getStylesheets().clear();
			
			this.stackPane.getStylesheets().add("/css/light_mode/buttonSyle.css");
			
			this.stackPane.getStylesheets().add("/css/light_mode/styles.css");
		}
	}
}
