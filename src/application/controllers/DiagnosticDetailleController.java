package application.controllers;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import application.SQLiteJDBC;
import application.controllers.login.ActivationController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class DiagnosticDetailleController implements Initializable {

	@FXML
	private TableView<DiagnostiqueDetaille> tableDiagnostiqueDetaille;

	@FXML
	private TextField tActe;

	@FXML
	private TextField tPaye;

	@FXML
	private StackPane stackPane;

	float payeCumule = 0;
	private static Diagnostique diagnostiqueSelected = null;
	private static Malade maladSelected = null;
	private ArrayList<DiagnostiqueDetaille> listDiagnostiqueDetaille;

	private void refreshList() {
		Statement stm = null;
		ResultSet rs = null;

		this.payeCumule = 0;
		this.listDiagnostiqueDetaille.clear();
		try {
			System.out.println(diagnostiqueSelected.getId() + " HHHHHHHHHHHHHHHHH");
			stm = new SQLiteJDBC().getConnection().createStatement();
			String request = "SELECT * FROM diagnostics_detaille WHERE diagnostic_id = '" + diagnostiqueSelected.getId()
					+ "' ";
			rs = stm.executeQuery(request);

			while (rs.next()) {
				payeCumule += rs.getFloat("paye");
				listDiagnostiqueDetaille.add(new DiagnostiqueDetaille(rs.getString("date_payement"),
						rs.getString("acte"), rs.getFloat("paye"), diagnostiqueSelected.getDevis() - payeCumule,
						rs.getInt("diagnostic_detaille_id")));

			}
			tableDiagnostiqueDetaille.setItems(FXCollections.observableArrayList(listDiagnostiqueDetaille));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@FXML
	void ajouterDiagnostiqueDetaille(MouseEvent event) {

		if (diagnostiqueSelected.getDevis() - payeCumule - Float.parseFloat(tPaye.getText()) < 0) {
			/*
			 * Notifications notification = Notifications.create().title("Echec")
			 * .text("Le reste est egale 0").graphic(null).hideAfter(Duration.seconds(2))
			 * .position(Pos.BOTTOM_CENTER).onAction(new EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 */
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Echec"));
			layout.setBody(new Text("Le reste est inférieur à 0"));

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

		if (!tPaye.getText().matches(".*\\d.*")) {
			/*
			 * Notifications notification = Notifications.create().title("Eche")
			 * .text("Le paye doit �tre un numero").graphic(null).hideAfter(Duration.
			 * seconds(2)) .position(Pos.BOTTOM_CENTER).onAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 */
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Echec"));
			layout.setBody(new Text("Le champ paye doit être un numero"));

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

		if (!tActe.getText().equals("") && !tPaye.getText().equals("")) {

			String pattern = "yyyy-MM-dd";
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
			String date = simpleDateFormat.format(new Date());
			// System.out.println(date);

			/*
			 * this.payeCumule += Float.parseFloat(tPaye.getText());
			 * listDiagnostiqueDetaille.add(new DiagnostiqueDetaille(date, tActe.getText(),
			 * Float.parseFloat(tPaye.getText()), diagnostiqueSelected.getDevis() -
			 * this.payeCumule));
			 * tableDiagnostiqueDetaille.setItems(FXCollections.observableArrayList(
			 * listDiagnostiqueDetaille));
			 */
			Statement stm = null;
			ResultSet rs = null;
			try {
				stm = new SQLiteJDBC().getConnection().createStatement();
				String request = "INSERT " + " INTO diagnostics_detaille(diagnostic_id,acte,paye,date_payement) "
						+ "VALUES('" + diagnostiqueSelected.getId() + "','" + tActe.getText() + "'," + "'"
						+ Float.parseFloat(tPaye.getText()) + "','" + date + "' );";
				stm.execute(request);
				refreshList();

				request = "UPDATE historic_malades SET paye = paye + '" + Float.parseFloat(tPaye.getText()) + "' "
						+ "WHERE historic_malade_id='" + maladSelected.getHmID() + "';";
				stm.execute(request);

				request = "SELECT benefit FROM benefits WHERE date='" + date + "' ;";
				rs = stm.executeQuery(request);
				double benefit = 0;
				while (rs.next()) {
					benefit = rs.getDouble(1);
				}
				rs.close();

				request = "INSERT OR REPLACE INTO benefits (date, benefit) VALUES('" + date + "', '"
						+ (benefit + Float.parseFloat(tPaye.getText())) + "')";
				stm.execute(request);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					stm.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		tActe.setText("");
		tPaye.setText("");

	}

	@FXML
	void changerPositionActe(KeyEvent event) {

		switch (event.getCode()) {
		case RIGHT:
			// System.out.println("Down");
			tPaye.requestFocus();
			break;

		}

	}

	@FXML
	void changerPositionPaye(KeyEvent event) {
		switch (event.getCode()) {
		case LEFT:
			// System.out.println("Down");
			tActe.requestFocus();
			break;

		case ENTER:
			ajouterDiagnostiqueDetaille(null);
			break;
		}
	}

	@FXML
	void supprimerDiagnostiqueDetaille(MouseEvent event) {

		Statement stm = null;

		DiagnostiqueDetaille dd = this.tableDiagnostiqueDetaille.getSelectionModel().getSelectedItem();

		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			String request = "DELETE FROM diagnostics_detaille WHERE diagnostic_detaille_id ='" + dd.getId() + "' ";
			stm.execute(request);
			refreshList();

			request = "UPDATE historic_malades SET paye = paye - '" + dd.getPaye() + "' " + "WHERE historic_malade_id='"
					+ maladSelected.getHmID() + "'";
			stm.execute(request);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		// initialiser tableau diagnostique
		TableColumn<DiagnostiqueDetaille, String> date = new TableColumn<DiagnostiqueDetaille, String>("Date");
		TableColumn<DiagnostiqueDetaille, String> acte = new TableColumn<DiagnostiqueDetaille, String>("Acte");
		TableColumn<DiagnostiqueDetaille, Number> paye = new TableColumn<DiagnostiqueDetaille, Number>("Paye");
		TableColumn<DiagnostiqueDetaille, Number> reste = new TableColumn<DiagnostiqueDetaille, Number>("Reste");

		// width of column of table
		date.prefWidthProperty().bind(tableDiagnostiqueDetaille.widthProperty().multiply(0.3));
		acte.prefWidthProperty().bind(tableDiagnostiqueDetaille.widthProperty().multiply(0.4));
		paye.prefWidthProperty().bind(tableDiagnostiqueDetaille.widthProperty().multiply(0.15));
		reste.prefWidthProperty().bind(tableDiagnostiqueDetaille.widthProperty().multiply(0.15));

		date.setResizable(false);
		acte.setResizable(false);
		paye.setResizable(false);
		reste.setResizable(false);

		date.setCellValueFactory(new PropertyValueFactory("date"));
		acte.setCellValueFactory(new PropertyValueFactory("acte"));
		paye.setCellValueFactory(new PropertyValueFactory("paye"));
		reste.setCellValueFactory(new PropertyValueFactory("reste"));

		// ajouter les colones et leur row au tableaux
		tableDiagnostiqueDetaille.getColumns().addAll(date, acte, paye, reste);
		System.out.println(diagnostiqueSelected.getId());

		listDiagnostiqueDetaille = new ArrayList<DiagnostiqueDetaille>();

		refreshList();
		this.setLightDarkMode();
	}

	public void setLightDarkMode() {

		if (!ActivationController.getMode()) {
			this.stackPane.getStylesheets().clear();

			this.stackPane.getStylesheets().add("/css/light_mode/tableView.css");

			this.stackPane.getStylesheets().add("/css/light_mode/styles.css");
		}
	}

	public static void setDiagnostique(Diagnostique diagno, Malade malade) {
		diagnostiqueSelected = diagno;
		maladSelected = malade;
	}

	@FXML
	void closeDetaille(KeyEvent event) {
		if (event.getCode() == KeyCode.ESCAPE) {
			System.out.println("escape");
		}
	}

}
