package application.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import application.DataBaseConnection;
import application.SQLiteJDBC;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class AddPatientController implements Initializable {
	@FXML
	private TextField nom;

	@FXML
	private ImageView btnFermer;

	@FXML
	private TextField prenom;

	@FXML
	private TextField age;

	@FXML
	private TextField adresse;

	@FXML
	private TextField phone;

	@FXML
	private TextField fonction;

	@FXML
	private ComboBox<String> sexe;
	@FXML
	private Button btnAjouter;

	@FXML
	private StackPane stackPane;

	private SQLiteJDBC sl;
	private int switcher;
	private int userId;
	private Malade updateData;

	public void initialize(URL arg0, ResourceBundle arg1) {

		Tooltip ttDetailed = new Tooltip();
		ttDetailed.setText("Pour ajouter le patient.");
		ttDetailed.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		btnAjouter.setTooltip(ttDetailed);

		// TODO Auto-generated method stub
		sl = new SQLiteJDBC();
		ObservableList<String> data = FXCollections.observableArrayList("Masculin", "Féminin");
		sexe.setItems(data);
		switcher = -1;

		if (this.updateData != null) {

			this.nom.setText(this.updateData.getNom());
			this.prenom.setText(this.updateData.getPrenom());
			this.age.setText(this.updateData.getAge() + "");
			this.adresse.setText(this.updateData.getAdresse());
			this.phone.setText(this.updateData.getPhone());
			this.fonction.setText(this.updateData.getProfession());
			//this.sexe.setValue(this.updateData.getSexe());
			if(this.updateData.getSexe().equals("Masculin")) {
				setSelectCheckBoxMale(null);
			}else {
				setSelectCheckBoxFemale(null);
			}
		}

	}

	public AddPatientController(int userId) {
		// TODO Auto-generated constructor stub
		this.userId = userId;
		this.updateData = null;
	}

	public AddPatientController(Malade malade) {

		this.updateData = malade;

	}

	@FXML
	void ajouter(MouseEvent event) {
//rï¿½cupï¿½rer la date 
		LocalDateTime myDate = LocalDateTime.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// System.out.println("selected combobox " + s);
		// DataBaseConnection db = new DataBaseConnection();
		// Statement stm = null;
		// ResultSet rs = null;
		String nomS = "", prenomS = "", sexeS = "", adresseS = "", phoneS = "", fonctionS = "", payeS = "", ageS = "0";
		String s = "";
		if (!nom.getText().equals("")) {
			nomS = nom.getText();
		}
		if (!prenom.getText().equals("")) {
			prenomS = prenom.getText();
		}
		if (!age.getText().equals("")) {
			ageS = age.getText();
			if (!age.getText().matches(".*\\d.*")) {
				/*
				 * Notifications notification =
				 * Notifications.create().title("Eche").text("L'age doit ï¿½tre un numero")
				 * .graphic(null).hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_CENTER)
				 * .onAction(new EventHandler<ActionEvent>() {
				 * 
				 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
				 * System.out.println("notification"); } }); notification.show();5566
				 */
				JFXDialogLayout layout = new JFXDialogLayout();
				layout.setHeading(new Text("Eche"));
				layout.setBody(new Text("L'age doit être un numero"));

				JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);

				JFXButton ok = new JFXButton("Fermer");
				ok.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						dialog.close();

					}
				});

				layout.setActions(ok);
				dialog.show();
				return;

			}
		}

		s = this.sexeFromCheckBox;

		if (!adresse.getText().equals("")) {
			adresseS = adresse.getText();
		}
		if (!phone.getText().equals("")) {
			phoneS = phone.getText();
		}
		if (!fonction.getText().equals("")) {
			fonctionS = fonction.getText();
		}

		try {
			System.out.println("atik");

			String request = "";
			if (this.updateData == null) {
				request = "INSERT INTO malades(nom, prenom, age, sexe, adresse, telephone, fonction,date_creation)"
						+ " VALUES(?,?,?,?,?,?,?,?); ";
			} else {
				request = "UPDATE  malades SET nom=?,prenom=?,age=?,sexe=?,adresse=?,telephone=?,fonction=?,date_creation=? "
						+ "WHERE malade_id=?";
			}
			PreparedStatement stmt = sl.getConnection().prepareStatement(request);

			stmt.setString(1, nomS);
			stmt.setString(2, prenomS);
			stmt.setInt(3, Integer.parseInt(ageS));
			stmt.setString(4, s);
			stmt.setString(5, adresseS);
			stmt.setString(6, phoneS);
			stmt.setString(7, fonctionS);
			stmt.setString(8, myDate.format(myFormat));
			if (this.updateData != null) {
				stmt.setInt(9, this.updateData.getId());
			}
			stmt.executeUpdate();

			System.out.println("...........................................");

			/*
			 * String request =
			 * "INSERT INTO malades(nom, prenom, age, sexe, adresse, telephone, fonction,date_creation)"
			 * + " VALUES('" + nomS + "','" + prenomS + "'," + Integer.parseInt(ageS) + ",'"
			 * + s + "','" + adresseS + "','" + phoneS + "','" + fonctionS + "','" +
			 * myDate.format(myFormat) + "'); "; stm.execute(request);
			 */
			if (this.updateData == null) {
				int idMalade = stmt.getGeneratedKeys().getInt(1);

				this.switcher = idMalade;

				request = "INSERT INTO historic_users(user_id,date_heur,action) " + "VALUES(?,?,?);";
				stmt = sl.getConnection().prepareStatement(request);
				stmt.setInt(1, userId);
				stmt.setString(2, myDate.format(myFormat));
				stmt.setString(3, "Ajouter un patient");
				stmt.executeUpdate();
				/*
				 * request="INSERT INTO historic_users(user_id,date_heur,action) " +
				 * "VALUES('"+userId+"', '"+myDate.format(myFormat)+"','Ajouter un patient')";
				 * stm.execute(request);
				 */
				// int idMalade = stm.getGeneratedKeys().getInt(1);
				System.out.println("the last id for patient is " + idMalade);
				request = "INSERT INTO historic_malades(malade_id,date_arriver,paye,user_id) " + "VALUES(?,?,?,?)";
				stmt = sl.getConnection().prepareStatement(request);
				stmt.setInt(1, idMalade);
				stmt.setString(2, myDate.format(myFormat));
				stmt.setFloat(3, 0);
				stmt.setInt(4, this.userId);
				/*
				 * request = "INSERT INTO historic_malades(malade_id,date_arriver,paye) " +
				 * "VALUES('" + idMalade + "','" + myDate.format(myFormat) + "',0)";
				 * 
				 * 
				 * stm.execute(request);
				 */
				stmt.executeUpdate();
			}
			stmt.close();
			nom.setText("");
			prenom.setText("");
			age.setText("");
			adresse.setText("");
			fonction.setText("");
			phone.setText("");
//			HomeController hc=new HomeController();
////				hc.addPost(new Malade("hamouche", "Karim", "kdjfdk", "02555666", "homme", 35));
//				hc.addPost();

			// update

			// insert
			// this.switcher = 1;

			btnAjouter.getScene().getWindow().hide();

		} catch (SQLException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		}
	}

	public boolean numbPatient() throws SQLException {

		Statement stm = null;
		ResultSet rs = null;

		String request = "SELECT COUNT(*)  FROM malades";
		rs = new SQLiteJDBC().getConnection().createStatement().executeQuery(request);

		int size = 0;

		if (rs != null)

		{

			// moves cursor to the last row

			size = rs.getInt(1); // get row id

		}
		System.out.println("AddPatientController " + size);
		rs.close();

		if (size >= 50) {

			return true;
		}
		return false;

	}

	public int getSwitcher() {
		return this.switcher;
	}

	@FXML
	void fermer(MouseEvent event) {
		Stage stage = (Stage) btnFermer.getScene().getWindow();
		stage.close();
	}

	@FXML
	void changerPositionActe(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:
			System.out.println("Down");
			break;
		case UP:
			fonction.requestFocus();
			break;
		case ENTER:
			ajouter(null);

		}

	}

	@FXML
	void changerPositionAdresse(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:
			phone.requestFocus();
			break;
		case UP:
			age.requestFocus();
			break;
		case ENTER:
			ajouter(null);

		}
	}

	@FXML
	void changerPositionAge(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:
			adresse.requestFocus();
			break;
		case UP:
			prenom.requestFocus();
			break;
		case ENTER:
			ajouter(null);

		}
	}

	@FXML
	void changerPositionFonction(KeyEvent event) {
		switch (event.getCode()) {

		case UP:
			phone.requestFocus();
			break;
		case ENTER:
			ajouter(null);
			break;

		}
	}

	@FXML
	void changerPositionNom(KeyEvent event) {

		switch (event.getCode()) {
		case DOWN:
			prenom.requestFocus();
			break;
		case UP:
			System.out.println("up");
			break;
		case ENTER:
			ajouter(null);

		}

	}

	@FXML
	void changerPositionPrenom(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:
			age.requestFocus();
			break;
		case UP:
			nom.requestFocus();
			break;
		case ENTER:
			ajouter(null);

		}
	}

	@FXML
	void changerPositionTelephone(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:
			fonction.requestFocus();
			break;
		case UP:
			adresse.requestFocus();
			break;
		case ENTER:
			ajouter(null);

		}
	}

	@FXML
	JFXCheckBox checkBoxMale;
	@FXML
	JFXCheckBox checkBoxFemale;
	private String sexeFromCheckBox="Masculin";

	@FXML
	public void setSelectCheckBoxMale(ActionEvent event) {
		
		this.checkBoxMale.setSelected(true);
			this.checkBoxFemale.setSelected(false);
			this.sexeFromCheckBox="Masculin";
		
		
		this.adresse.requestFocus();
	}
	
	@FXML
	public void setSelectCheckBoxFemale(ActionEvent event) {
		this.checkBoxFemale.setSelected(true);
		this.checkBoxMale.setSelected(false);
		this.sexeFromCheckBox="Féminin";
		this.adresse.requestFocus();
	}

}
