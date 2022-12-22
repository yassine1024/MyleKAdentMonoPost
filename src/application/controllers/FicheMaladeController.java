package application.controllers;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Collection;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Stream;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.TextFields;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXChipView;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXSpinner;


import animatefx.animation.BounceIn;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeInLeftBig;
import animatefx.animation.FadeOut;
import application.Medication;
import application.SQLiteJDBC;
import application.controllers.carrousell.SampleController;
import application.controllers.carrousell.radiosMasanoryController;
import application.controllers.login.ActivationController;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.util.Duration;

public class FicheMaladeController implements Initializable {
	@FXML
	private Button btnEnregistrer;

	@FXML
	private ImageView btnFermer;

	@FXML
	private TextField tDiagnostique;

	@FXML
	private TextField tDevis;

	@FXML
	private TextField tTraitement;
	@FXML
	private Label nomComplet;

	@FXML
	private Label age;

	@FXML
	private Label phone;

	@FXML
	private Label adresse;

	@FXML
	private Label sexe;

	@FXML
	private Label fonction;

	@FXML
	private TextField medicationCourant;
	@FXML
	private VBox contentMedication;

	@FXML
	private TableView<Diagnostique> tableDiagnostique;

	@FXML
	private TextField motif;

	@FXML
	private TextField etatGeneret;

	@FXML
	private TextArea medication;
	@FXML
	private TextField tPDiagnostiques;

	@FXML
	private TextField tActe;

	@FXML
	private AnchorPane anchorConsult;
	@FXML
	private Text totale;
	@FXML
	private StackPane stackPane;

	@FXML
	private Button redactionButton;
//pour connaitre le totale de diagnostique
	private float totaleFicheMalade = 0;
	// ScrollPane scroller = new ScrollPane(contentMedication);
	// les attribues
	public static Malade malade;
	private ArrayList<Diagnostique> listDiagnostique;
	private ArrayList<Payment> listPayment;
	private ArrayList<Medication> auto = new ArrayList<Medication>();
	private ArrayList<String> motifs = new ArrayList<String>();
	private ArrayList<String> etatGenerets = new ArrayList<String>();
	private ArrayList<String> medications = new ArrayList<String>();
	private boolean ifAddDiagnostique = false;
	private boolean ifAddPayment = false;
	private int numeroOrdre = 1;
	private SQLiteJDBC sl;
	private ArrayList<String> newMedcine = new ArrayList<String>();
	private int existDejaDoncIdConsultation = 0;

	private boolean ifSaved = false;
	private int etatId = 0, motifId = 0;

	// table of hachage
	Hashtable<String, Integer> comportMedicationId = new Hashtable<String, Integer>();

	public static int switcher;

	public static int getSwitcher() {
		return switcher;
	}

	public static void selectionerMalade(Malade malade1) {
		// TODO Auto-generated constructor stub
		malade = malade1;
	}

	@FXML
	private JFXSpinner spinerTiming;
	@FXML
	void displayRadios2(MouseEvent event) {

		
		Stage primaryStage = new Stage();
		// primaryStage.setFullScreen(true);
		// primaryStage.initStyle(StageStyle.UNDECORATED);
		radiosMasanoryController controller = new radiosMasanoryController(radioList);
		FXMLLoader root;
		Parent parent;

		try {
			root = new FXMLLoader(getClass().getResource("/application/views/carrousell/radiosMasanory.fxml"));

			root.setController(controller);
			parent = root.load();
			Scene scene = new Scene(parent);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent ke) {
					// TODO Auto-generated method stub
					if (ke.getCode() == KeyCode.ESCAPE) {
						primaryStage.close();
					}
				}
			});

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {

		}
		
		

	}

	@FXML
	void displayRadios(MouseEvent event) {

		// show the carroussell of radio

		Stage primaryStage = new Stage();
		primaryStage.setFullScreen(true);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		SampleController controller = new SampleController(radioList);
		FXMLLoader root;
		Parent parent;

		try {
			root = new FXMLLoader(getClass().getResource("/application/views/carrousell/sample.fxml"));

			root.setController(controller);
			parent = root.load();
			Scene scene = new Scene(parent);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent ke) {
					// TODO Auto-generated method stub
					if (ke.getCode() == KeyCode.ESCAPE) {
						primaryStage.close();
					}
				}
			});

			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {

		}
	}

	@FXML
	void addRadio(MouseEvent event) throws IOException {

		spinerTiming.setVisible(true);
		/*
		 * PreparedStatement stmt = null;
		 * 
		 * String request =
		 * "INSERT INTO diagnostics(consultation_id,diagnostique,traitement,devis,num_diagnostique) "
		 * + "VALUES(?,?,?,?,?)";
		 * 
		 * stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
		 * stmt.setInt(1, existDejaDoncIdConsultation); stmt.setString(2,
		 * tDiagnostique.getText()); stmt.setString(3, tTraitement.getText());
		 * stmt.setFloat(4, Float.parseFloat(tDevis.getText())); stmt.setInt(5,
		 * numeroOrdre); stmt.executeUpdate();
		 * 
		 */
		// Creating a File chooser
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Multiple Files");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));
		List<File> selectedFiles = fileChooser.showOpenMultipleDialog((Stage) this.stackPane.getScene().getWindow());

		if (selectedFiles != null) {

			PreparedStatement stmt = null;

			String request;
			// System.out.println("PDF Files selected [" + selectedFiles.size() + "]: " +
			// selectedFiles.get(0).getName() + "..");

			String string = "assets\\sqlite\\data\\radios\\" + this.malade.getId() + "__" + this.malade.getNom() + "_"
					+ this.malade.getPrenom();

			File theDir = new File(string);
			if (!theDir.exists()) {
				System.out.println("creating directory: " + theDir.getName());
				boolean result = false;

				try {
					theDir.mkdir();
					result = true;
				} catch (SecurityException se) {
					// handle it
				}
				if (result) {
					System.out.println("DIR created");
				}
			}

			for (File file : selectedFiles) {

				// System.out.println(file.getAbsolutePath());
				
				//PathSource , PathTarget

				Files.copy(Paths.get(file.getAbsolutePath()), Paths.get(string + "\\" + file.getName()),
						StandardCopyOption.REPLACE_EXISTING);
				

				System.out.println(file.getName() + " file copied");
				request = "INSERT INTO radios(malade_id,file_name,file_path) " + "VALUES(?,?,?)";

				try {
					stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
					stmt.setInt(1, this.malade.getId());
					stmt.setString(2, file.getName());
					stmt.setString(3, string);
					stmt.executeUpdate();
					System.out.println(file.getName() + " file inserted in DB");
					//to update the list of radios after we add some images to it (radios)
					this.radioList.add(string + "\\" + file.getName());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		} else {
			System.out.println("PDF file selection cancelled.");
		}
		
		spinerTiming.setVisible(false);

	}

	private boolean ifSelectedItem() {
		Diagnostique selectedDiagnostique = tableDiagnostique.getSelectionModel().getSelectedItem();
		if (selectedDiagnostique != null) {
			return true;
		}
		/*
		 * Notifications notification =
		 * Notifications.create().title("Aucun diagnostic sï¿½lï¿½ctionner")
		 * .text("Il faut sï¿½lï¿½ctionner un diagnostic").graphic(null).hideAfter(Duration.
		 * seconds(2)) .position(Pos.BOTTOM_CENTER).onAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
		 * System.out.println("notification"); } }); notification.show();
		 */
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Text("Aucun diagnostic séléctionner"));
		layout.setBody(new Text("Il faut séléctionner un diagnostic"));

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

		return false;
	}

	@FXML
	void doubleClickDiagnostique(MouseEvent event) {

		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {

				if (ifSaved || this.existDejaDoncIdConsultation != 0) {
					System.out.println("Double clicked");
					if (ifSelectedItem()) {
						Diagnostique selectedDiagnostique = tableDiagnostique.getSelectionModel().getSelectedItem();
						Stage primaryStage = new Stage();
						primaryStage.initStyle(StageStyle.UNDECORATED);
						Parent root;

						try {
							DiagnosticDetailleController.setDiagnostique(selectedDiagnostique, malade);
							root = FXMLLoader
									.load(getClass().getResource("/application/views/DiagnosticDetaille.fxml"));

							Scene scene = new Scene(root);
							scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

								@Override
								public void handle(KeyEvent ke) {
									// TODO Auto-generated method stub
									if (ke.getCode() == KeyCode.ESCAPE) {
										primaryStage.close();
									}
								}
							});
							scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

								@Override
								public void handle(KeyEvent ke) {
									// TODO Auto-generated method stub
									if (ke.getCode() == KeyCode.ESCAPE) {
										primaryStage.close();
									}
								}
							});

							// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
							primaryStage.setScene(scene);
							primaryStage.show();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					JFXDialogLayout layout = new JFXDialogLayout();
					layout.setHeading(new Text("Echec"));
					layout.setBody(new Text("Il faut sauvegarder la fiche"));

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
				}
			} // closed

		}

	}

	@FXML
	ScrollPane scrollPaneMedication;

	private void insertMedicationsIfnotExist() throws SQLException {

		ResultSet rs = null;
		PreparedStatement stmt = null;

		// stm = new SQLiteJDBC().getConnection().createStatement();
		// s
		// Statement stm = null;

		Hashtable<String, Integer> newHashTableChip = new Hashtable<String, Integer>();
		for (String medicationChip : this.chipView.getChips()) {
			newHashTableChip.put(medicationChip, 1);
		}

		for (String medicationChip : this.chipView.getChips()) {
			boolean medcinesExist = false;
			for (int i = 0; i < medications.size(); i++) {
				if (medicationChip.equals(medications.get(i))) {

					medcinesExist = true;
					break;
				}
			}
			if (!medcinesExist) {

				String request = "INSERT INTO medications(nom) VALUES (?)";

				stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
				stmt.setString(1, medicationChip);
				stmt.executeUpdate();
				/*
				 * String request = "INSERT INTO medications(nom) VALUES ('" + label.getText() +
				 * "')"; stm.execute(request);
				 */
				stmt.close();
			}

			if (!this.comportMedicationId.containsKey(medicationChip) && newHashTableChip.containsKey(medicationChip)) {

				System.out.println("meddddddddddddddddddd.....   " + medicationChip);
				String request = "SELECT medication_id FROM medications WHERE nom=?";
				stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
				stmt.setString(1, medicationChip);
				// String request = "select medication_id from medications where nom='" +
				// label.getText() + "'";
				rs = stmt.executeQuery();
				System.out.println("MedId===== " + rs.getInt(1));
				// rs.getInt("medication_id");
				// stmt.close();
				int medIDRs = rs.getInt("medication_id");

				request = "INSERT INTO comporte_medications(consultation_id,medication_id)" + " VALUES (?,?)";
				stmt.close();
				stmt = new SQLiteJDBC().getConnection().prepareStatement(request);

				stmt.setInt(1, existDejaDoncIdConsultation);

				stmt.setInt(2, medIDRs);
				System.out.println("Insert into table comporte_medications with success");

				stmt.executeUpdate();
				// Hna

				stmt.close();
			}
		}

		for (String medication : this.medications) {

			if (this.comportMedicationId.containsKey(medication) && !newHashTableChip.containsKey(medication)) {

				String request = "DELETE FROM comporte_medications  WHERE consultation_id=? AND medication_id=?";
				stmt = new SQLiteJDBC().getConnection().prepareStatement(request);

				stmt.setInt(1, existDejaDoncIdConsultation);

				stmt.setInt(2, this.comportMedicationId.get(medication));

				System.out.println("Delete from comporte_medications with success");

				stmt.executeUpdate();
				// Hna

				stmt.close();
			}

		}
	}

	@FXML
	void addMedicationInList(ActionEvent event) {

		final Random rng = new Random();

		// scroller.setFitToWidth(true);

		AnchorPane anchorPane = new AnchorPane();
		String style = String.format("-fx-background: rgb(%d, %d, %d);" + "-fx-background-color: -fx-background;", 255,
				197, 58);
		anchorPane.setStyle(style);

		Label label = new Label(medicationCourant.getText());
		AnchorPane.setLeftAnchor(label, 5.0);
		AnchorPane.setTopAnchor(label, 5.0);
		Button button = new Button("Supp");
		// Statement stm=null;

		if (existDejaDoncIdConsultation == 0) {
			button.setOnAction(evt -> contentMedication.getChildren().remove(anchorPane));
		} else {
			ResultSet rs = null;
			PreparedStatement stmt = null;
			try {

				// stm = new SQLiteJDBC().getConnection().createStatement();
				// s
				// Statement stm = null;
				boolean medcinesExist = false;
				for (int i = 0; i < medications.size(); i++) {
					if (label.getText().equals(medications.get(i))) {

						medcinesExist = true;
						break;
					}
				}
				if (!medcinesExist) {
					System.out.println("jj entrer");
					String request = "INSERT INTO medications(nom) VALUES (?)";
					stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
					stmt.setString(1, label.getText());
					stmt.executeUpdate();
					/*
					 * String request = "INSERT INTO medications(nom) VALUES ('" + label.getText() +
					 * "')"; stm.execute(request);
					 */
					stmt.close();
				}
				String request = "SELECT medication_id FROM medications WHERE nom=?";
				stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
				stmt.setString(1, label.getText());
				// String request = "select medication_id from medications where nom='" +
				// label.getText() + "'";
				rs = stmt.executeQuery();
				System.out.println("MedId===== " + rs.getInt(1));
				// rs.getInt("medication_id");
				// stmt.close();
				int medIDRs = rs.getInt("medication_id");
				request = "INSERT INTO comporte_medications(consultation_id,medication_id)" + " VALUES (?,?)";
				stmt.close();
				stmt = new SQLiteJDBC().getConnection().prepareStatement(request);

				stmt.setInt(1, existDejaDoncIdConsultation);

				stmt.setInt(2, medIDRs);
				System.out.println("Insert into table comporte_medications with success");

				stmt.executeUpdate();
				// Hna

				stmt.close();
				/*
				 * request = "INSERT INTO comporte_medications(consultation_id,medication_id)" +
				 * "VALUES ('" + existDejaDoncIdConsultation + "','" +
				 * rs.getInt("medication_id") + "')"; stm.execute(request);
				 */

				button.setOnAction(

						evt -> {
							PreparedStatement stmt2 = null;
							// System.out.println("existe "existDejaDoncIdConsultation);
							if (existDejaDoncIdConsultation != 0) {
								try {
									String medicationLable = ((Label) anchorPane.getChildren().get(0)).getText();
									String request2 = "DELETE FROM comporte_medications WHERE " + "medication_id =? "
											+ "AND consultation_id =?";

									stmt2 = new SQLiteJDBC().getConnection().prepareStatement(request2);
									System.out.println(
											"id medication Comporte " + comportMedicationId.get(medicationLable));
									// comportMedicationId
									/*
									 * String request2 = "DELETE FROM comporte_medications WHERE " +
									 * "medication_id = '" + comportMedicationId.get(medicationLable) + "' " +
									 * "AND consultation_id = '" + existDejaDoncIdConsultation + "'";
									 * stm2.execute(request2);
									 */
									stmt2.setInt(1, comportMedicationId.get(medicationLable));
									stmt2.setInt(2, existDejaDoncIdConsultation);
									stmt2.executeUpdate();
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} finally {
									try {
										stmt2.close();
									} catch (SQLException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}
							System.out.println(((Label) anchorPane.getChildren().get(0)).getText());

							contentMedication.getChildren().remove(anchorPane);
						}

				);
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				System.out.println("GGGGGGGGRRRRAAAANNNNNDDDDD ERRRRREEEEEEEUUUURRRRR");
			} finally {
				try {
					stmt.close();
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		AnchorPane.setRightAnchor(button, 5.0);
		AnchorPane.setTopAnchor(button, 5.0);
		AnchorPane.setBottomAnchor(button, 5.0);
		anchorPane.getChildren().addAll(label, button);
		contentMedication.getChildren().add(anchorPane);
		newMedcine.add(medicationCourant.getText());
		medicationCourant.setText("");

//System.out.println(((Label) contentMedication.getChildren().get(0)).getText());
	}

	public static void setSwitcher(int sw) {
		switcher = sw;
	}

	@FXML
	private Button plusButton;

	@FXML
	private Button listButton;

	@FXML
	private JFXListView<Label> listOrderOfPatient;

	private String fileOrders;

	private void initializeOrdersForPatient() {
		Statement stm = null;
		ResultSet rs = null;

		String request = "SELECT * FROM orders O, malades M  WHERE O.malade_id=M.malade_id AND O.malade_id='"
				+ this.malade.getId() + "'";
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			rs = stm.executeQuery(request);

			while (rs.next()) {
				// System.out.println(rs.getString("file_name"));

				Label label = new Label(rs.getString("file_name"));

				File file = new File("assets//file.png");
				ImageView radio = new ImageView(file.toURI().toString());
				radio.setFitWidth(50);
				radio.setFitHeight(40);
				radio.setPreserveRatio(true);
				label.setGraphic(radio);

				fileOrders = rs.getString("file_path");
				label.setOnMousePressed(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						// TODO Auto-generated method stub
						if (event.getClickCount() == 2) {

							try {
								File myFile = null;

								myFile = new File(fileOrders);
								Desktop.getDesktop().open(myFile);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
				});
				listOrderOfPatient.getItems().add(label);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initialize(URL arg0, ResourceBundle arg1) {
		
		

		this.initializeOrdersForPatient();

		Tooltip ttSave = new Tooltip();
		ttSave.setText("Enregistrer Vos données.");
		ttSave.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttplus = new Tooltip();
		ttplus.setText("Ajouter un ou+ radio(s).");
		ttplus.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttList = new Tooltip();
		ttList.setText("Afficher les radios.");
		ttList.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		this.btnEnregistrer.setTooltip(ttSave);
		this.plusButton.setTooltip(ttplus);
		this.listButton.setTooltip(ttList);

		// initialiser la fiche information malade

		nomComplet.setText(malade.getNom() + " " + malade.getPrenom());
		age.setText(malade.getAge() + "");
		phone.setText(malade.getPhone());
		adresse.setText(malade.getAdresse());
		sexe.setText(malade.getSexe());
		fonction.setText(malade.getProfession());

		// initialiser tableau diagnostique
		TableColumn<Diagnostique, Number> ordre = new TableColumn<Diagnostique, Number>("N°");
		TableColumn<Diagnostique, String> diagnostique = new TableColumn<Diagnostique, String>("Diagnostique");
		TableColumn<Diagnostique, String> traitement = new TableColumn<Diagnostique, String>("Traitement");
		TableColumn<Diagnostique, Number> devis = new TableColumn<Diagnostique, Number>("Devis");
//width of column of table
		ordre.prefWidthProperty().bind(tableDiagnostique.widthProperty().multiply(0.1));
		diagnostique.prefWidthProperty().bind(tableDiagnostique.widthProperty().multiply(0.4));
		traitement.prefWidthProperty().bind(tableDiagnostique.widthProperty().multiply(0.4));
		devis.prefWidthProperty().bind(tableDiagnostique.widthProperty().multiply(0.2));

		ordre.setResizable(false);
		diagnostique.setResizable(false);
		traitement.setResizable(false);
		devis.setResizable(false);

		ordre.setCellValueFactory(new PropertyValueFactory("ordre"));
		diagnostique.setCellValueFactory(new PropertyValueFactory("diagnostique"));
		traitement.setCellValueFactory(new PropertyValueFactory("traitement"));
		devis.setCellValueFactory(new PropertyValueFactory("devis"));
//		initialiser tableau payment 
		TableColumn<Payment, Number> numDiagnostique = new TableColumn<Payment, Number>("N°");
		TableColumn<Payment, String> acte = new TableColumn<Payment, String>("acte");
//set width of table payment
		numDiagnostique.prefWidthProperty().bind(tableDiagnostique.widthProperty().multiply(0.1));
		acte.prefWidthProperty().bind(tableDiagnostique.widthProperty().multiply(0.9));

		numDiagnostique.setResizable(false);
		acte.setResizable(false);

		numDiagnostique.setCellValueFactory(new PropertyValueFactory("numDiagnostique"));
		acte.setCellValueFactory(new PropertyValueFactory("acte"));

		listDiagnostique = new ArrayList<Diagnostique>();
		listPayment = new ArrayList<Payment>();
		// ajouter les colones et leur row au tableaux
		tableDiagnostique.getColumns().addAll(ordre, diagnostique, traitement, devis);
		// tablePayment.getColumns().addAll(numDiagnostique, acte);

		/*
		 * contentMedication = new VBox(5); contentMedication.setLayoutX(88);
		 * contentMedication.setLayoutY(242); contentMedication.setPrefWidth(377);
		 * contentMedication.setPrefHeight(269); ScrollPane scroller = new
		 * ScrollPane(contentMedication); scroller.setFitToWidth(true);
		 */
		anchorConsult.getChildren().add(contentMedication);
		// TODO Auto-generated method stub

		// initialiser le auto travers BD
		// DataBaseConnection db = new DataBaseConnection();
		sl = new SQLiteJDBC();
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
			TextFields.bindAutoCompletion(medicationCourant, medications);

			// requette pour les motifs
			request = "SELECT DISTINCT nom FROM motifs";
			rs = stm.executeQuery(request);
			while (rs.next()) {
				motifs.add(rs.getString("nom"));
			}
			// INitialiser autoComplete motif
			TextFields.bindAutoCompletion(motif, motifs);

			// requette pour les etats generets
			request = "SELECT DISTINCT nom FROM etat_generets";
			rs = stm.executeQuery(request);
			while (rs.next()) {
				etatGenerets.add(rs.getString(1));
			}
			TextFields.bindAutoCompletion(etatGeneret, etatGenerets);

			// initialize the all information if does it exists
			// 1) get the etat and motif
			System.out.println("malade id =" + this.malade.getId());
			request = "SELECT e.nom nomE,e.etat_generet_id eId,m.motif_id mId,m.nom nomM,c.consultation_id  FROM  consultations c,etat_generets e,motifs m"
					+ " WHERE  e.etat_generet_id =  c.etat_generet_id  " + " AND m.motif_id=c.motif_id"
					+ " AND malade_id = " + this.malade.getId() + "; ";

			rs = stm.executeQuery(request);
			int consultationID = 0;
			// System.out.println("motif: " + rs.getString("nomM") + " etat: " +
			// rs.getString("nomE"));
			if (rs.next()) {
				motif.setText(rs.getString("nomM"));
				etatId = rs.getInt("eId");
				motifId = rs.getInt("mId");

				etatGeneret.setText(rs.getString("nomE"));
				consultationID = rs.getInt("consultation_id");
				existDejaDoncIdConsultation = consultationID;
			}

			// 2) insert madications courently if exists

			final Random rng = new Random();

			// scroller.setFitToWidth(true);

			request = "SELECT m.nom med,cm.medication_id med_id  from  comporte_medications cm,medications m "
					+ "WHERE cm.medication_id = m.medication_id AND cm.consultation_id ='" + consultationID + "' ;";
			rs = stm.executeQuery(request);
			while (rs.next()) {
				AnchorPane anchorPane = new AnchorPane();
				String style = String.format(
						"-fx-background: rgb(%d, %d, %d);" + "-fx-background-color: -fx-background;", 255, 197, 58);
				anchorPane.setStyle(style);

				Label label = new Label(rs.getString("med"));
				// mapping
				comportMedicationId.put(rs.getString("med"), rs.getInt("med_id"));

				AnchorPane.setLeftAnchor(label, 5.0);
				AnchorPane.setTopAnchor(label, 5.0);
				Button button = new Button("Supp");

				button.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						// System.out.println("Hello World!");
						Statement stm = null;
						// System.out.println("existe "existDejaDoncIdConsultation);
						if (existDejaDoncIdConsultation != 0) {
							try {
								String medicationLable = ((Label) anchorPane.getChildren().get(0)).getText();
								stm = new SQLiteJDBC().getConnection().createStatement();
								System.out
										.println("id medication Comporte " + comportMedicationId.get(medicationLable));
								// comportMedicationId
								String request = "DELETE FROM comporte_medications WHERE " + "medication_id = '"
										+ comportMedicationId.get(medicationLable) + "' " + "AND consultation_id = '"
										+ existDejaDoncIdConsultation + "'";
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
						System.out.println(((Label) anchorPane.getChildren().get(0)).getText());

						contentMedication.getChildren().remove(anchorPane);

					}
				});

				/*
				 * button.setOnAction(
				 * 
				 * evt -> { Statement stm = null;
				 * System.out.println("existe "existDejaDoncIdConsultation); if
				 * (existDejaDoncIdConsultation != 0) { try { String medicationLable = ((Label)
				 * anchorPane.getChildren().get(0)).getText(); stm = new
				 * SQLiteJDBC().getConnection().createStatement();
				 * System.out.println("id medication Comporte "+comportMedicationId.get(
				 * medicationLable)); // comportMedicationId String request =
				 * "DELETE FROM comporte_medications WHERE " + "medication_id = '" +
				 * comportMedicationId.get(medicationLable) + "' " + "AND consultation_id = '" +
				 * existDejaDoncIdConsultation + "'"; stm.execute(request); } catch
				 * (SQLException e) { // TODO Auto-generated catch block e.printStackTrace(); }
				 * finally { try { stm.close(); } catch (SQLException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); } } }
				 * System.out.println(((Label) anchorPane.getChildren().get(0)).getText());
				 * 
				 * contentMedication.getChildren().remove(anchorPane); }
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * 
				 * );
				 */
				AnchorPane.setRightAnchor(button, 5.0);
				AnchorPane.setTopAnchor(button, 5.0);
				AnchorPane.setBottomAnchor(button, 5.0);
				anchorPane.getChildren().addAll(label, button);
				contentMedication.getChildren().add(anchorPane);
				// newMedcine.add(medicationCourant.getText());
				medicationCourant.setText("");
			}

			// 3) insert in view table diagnostique
			request = "SELECT d.diagnostic_id did, d.diagnostique diagno,d.traitement trait,d.devis devise,d.num_diagnostique num FROM "
					+ "diagnostics d " + "WHERE d.consultation_id = '" + consultationID + "'";

			rs = stm.executeQuery(request);
			// listDiagnostique = new ArrayList<Diagnostique>();
			this.totaleFicheMalade = 0;
			while (rs.next()) {
				System.out.println("diagno " + rs.getString("diagno") + " " + rs.getString("trait") + ""
						+ rs.getFloat("devise") + " " + rs.getInt("num"));
				listDiagnostique.add(new Diagnostique(rs.getInt("num"), rs.getString("diagno"), rs.getString("trait"),
						rs.getFloat("devise"), rs.getInt("did")));

				// totale diagnostique
				this.totaleFicheMalade += rs.getFloat("devise");
				// tDiagnostique.setText("");
				// tDevis.setText("");
				// tTraitement.setText("");
				numeroOrdre++;
			}
			this.totale.setText("Totale: " + this.totaleFicheMalade);
			tableDiagnostique.setItems(FXCollections.observableArrayList(listDiagnostique));

			initializeRadios(rs, stm);

			initializeChipViewOfMedecine();
			
			
			//Set Light/Dark mode as user chosed
			this.setLightDarkMode();
			
			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
			System.out.println("error : " + e.getMessage());
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
	
public void setLightDarkMode() {
		
		if(!ActivationController.getMode()) {
			this.stackPane.getStylesheets().clear();
			
			this.stackPane.getStylesheets().add("/css/light_mode/buttonSyle.css");
			this.stackPane.getStylesheets().add("/css/light_mode/tableView.css");
			this.stackPane.getStylesheets().add("/css/light_mode/styles.css");
			this.stackPane.getStylesheets().add("/css/light_mode/styles2.css");
		}
	}

	private BounceIn animationForSavingData ;
	@FXML
	private JFXButton jfxButtonSave;
	@FXML
	private StackPane medicineStackPane;

	private JFXChipView<String> chipView;

	private void initializeChipViewOfMedecine() {

		chipView = new JFXChipView<>();

		// chipView.getChips().addAll(this.medications);
		chipView.getChips().addAll(Collections.list(comportMedicationId.keys()));

		// chipView.getSuggestions().addAll("HELLO", "TROLL", "WFEWEF", "WEF");
		chipView.getSuggestions().addAll(this.medications);
		chipView.setPromptText(
				"Entrer vos medicaments ici(pour chaque médicament click ok pour ajouter dans la liste)");
		// chipView.setStyle("-fx-background-color: WHITE;");
		medicineStackPane.setStyle("-fx-background-color:GRAY;");

		medicineStackPane.getChildren().add(chipView);

	}

	private LinkedList<String> radioList;

	private void initializeRadios(ResultSet rs, Statement stm) throws SQLException {

		String request = "SELECT * FROM radios WHERE malade_id = '" + this.malade.getId() + "'";
		radioList = new LinkedList<String>();
		rs = stm.executeQuery(request);

		while (rs.next()) {

			// System.out.println(rs.getString("file_path"));
			radioList.add(rs.getString("file_path") + "\\" + rs.getString("file_name"));
		}

	}

	// refresh the diagnostic's table

	private void refreshDiagnostics(int consultationID) throws SQLException {

		
		// 3) insert in view table diagnostique
		this.listDiagnostique.clear();
		Statement stm = new SQLiteJDBC().getConnection().createStatement();
		String request = "SELECT d.diagnostic_id did, d.diagnostique diagno,d.traitement trait,d.devis devise,d.num_diagnostique num FROM "
				+ "diagnostics d " + "WHERE d.consultation_id = '" + consultationID + "'";

		ResultSet rs = stm.executeQuery(request);
		// listDiagnostique = new ArrayList<Diagnostique>();
		this.totaleFicheMalade = 0;
		while (rs.next()) {
			System.out.println("diagno " + rs.getString("diagno") + " " + rs.getString("trait") + ""
					+ rs.getFloat("devise") + " " + rs.getInt("num"));
			listDiagnostique.add(new Diagnostique(rs.getInt("num"), rs.getString("diagno"), rs.getString("trait"),
					rs.getFloat("devise"), rs.getInt("did")));

			// totale diagnostique
			this.totaleFicheMalade += rs.getFloat("devise");
			// tDiagnostique.setText("");
			// tDevis.setText("");
			// tTraitement.setText("");
			numeroOrdre++;
		}
		this.totale.setText("Totale: " + this.totaleFicheMalade);
		tableDiagnostique.setItems(FXCollections.observableArrayList(listDiagnostique));

	}

	@FXML
	void ajouterDiagnostique(MouseEvent event) {
		this.animationForSavingData= new BounceIn(this.jfxButtonSave);
		this.animationForSavingData.setCycleCount(100000).play();

		if (!tDevis.getText().matches(".*\\d.*")) {
			/*
			 * Notifications notification =
			 * Notifications.create().title("Eche").text("Le devis doit ï¿½tre un numero")
			 * .graphic(null).hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_CENTER)
			 * .onAction(new EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 */
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Echec"));
			layout.setBody(new Text("Le devis doit être un numéro"));

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

		if (!tDiagnostique.getText().equals("") && !tDevis.getText().equals("") && !tTraitement.getText().equals("")) {
			listDiagnostique.add(new Diagnostique(numeroOrdre, tDiagnostique.getText(), tTraitement.getText(),
					Float.parseFloat(tDevis.getText())));

			this.totaleFicheMalade += Float.parseFloat(tDevis.getText());
			totale.setText("Totale: " + this.totaleFicheMalade);
			tableDiagnostique.setItems(FXCollections.observableArrayList(listDiagnostique));

			if (existDejaDoncIdConsultation != 0) {
				PreparedStatement stmt = null;
				try {
					String request = "INSERT INTO diagnostics(consultation_id,diagnostique,traitement,devis,num_diagnostique) "
							+ "VALUES(?,?,?,?,?)";

					stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
					stmt.setInt(1, existDejaDoncIdConsultation);
					stmt.setString(2, tDiagnostique.getText());
					stmt.setString(3, tTraitement.getText());
					stmt.setFloat(4, Float.parseFloat(tDevis.getText()));
					stmt.setInt(5, numeroOrdre);
					stmt.executeUpdate();
					/*
					 * String request =
					 * "INSERT INTO diagnostics(consultation_id,diagnostique,traitement,devis,num_diagnostique) "
					 * + "VALUES('" + existDejaDoncIdConsultation + "','" + tDiagnostique.getText()
					 * + "'," + "'" + tTraitement.getText() + "','" +
					 * Float.parseFloat(tDevis.getText()) + "'," + "'" + numeroOrdre + "')";
					 * stm.execute(request);
					 */
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

			tDiagnostique.setText("");
			tDevis.setText("");
			tTraitement.setText("");
			numeroOrdre++;
		}

	}

	@FXML
	void fermerFicherMalade(MouseEvent event) {
		Stage stage = (Stage) btnFermer.getScene().getWindow();
		stage.close();

	}

	@FXML
	void enregisterFicherMalade(MouseEvent event) {
		
		this.animationForSavingData.stop();

		int consultationId = 0;

		System.out.println("enregistrerFM********************");

		// block events for this btn
		EventHandler<MouseEvent> handler = MouseEvent::consume;
		btnEnregistrer.addEventFilter(MouseEvent.ANY, handler);

		if (existDejaDoncIdConsultation == 0) {

			// 1)rï¿½cupï¿½rer les donnï¿½es
			// a)rï¿½cupï¿½rer les donnï¿½es concernant consult
			String mtf = motif.getText();
			String eG = etatGeneret.getText();
			// String med = medicationCourant.getText();

			// b)rï¿½cupï¿½rer les donnï¿½es concernant diagnosique;
			ObservableList<Diagnostique> olDiagnostique = tableDiagnostique.getItems();

			// c) rï¿½cupï¿½rer les donnï¿½es concernant payement
			// ObservableList<Payment> olPayment = tablePayment.getItems();

			// ************fin ï¿½tape 1 rï¿½cupï¿½ration des donnï¿½es********************///

			// **************2) enregistrer les donnï¿½s dans BD**********************///

			// a) connecter a BD
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				// DataBaseConnection db = new DataBaseConnection();

				// b) insï¿½rer dans tableau motif
				boolean motifExist = false;
				for (int i = 0; i < motifs.size(); i++) {
					if (mtf.equals(motifs.get(i))) {

						motifExist = true;
						break;
					}
				}
				if (!motifExist) {
					String request = "INSERT INTO motifs(nom) VALUES (?)";
					stmt = sl.getConnection().prepareStatement(request);
					stmt.setString(1, mtf);
					/*
					 * String request = "INSERT INTO motifs(nom) VALUES ('" + mtf + "')";
					 * 
					 * stm.execute(request);
					 */
					stmt.executeUpdate();
					stmt.close();

				}

				// insï¿½rer dans le tableau etat generet
				boolean etatGExist = false;
				for (int i = 0; i < etatGenerets.size(); i++) {
					if (eG.equals(etatGenerets.get(i))) {

						etatGExist = true;
						break;
					}
				}
				if (!etatGExist) {

					String request = "INSERT INTO etat_generets(nom) VALUES (?)";
					stmt = sl.getConnection().prepareStatement(request);
					stmt.setString(1, eG);
					stmt.executeUpdate();
					stmt.close();
					/*
					 * String request = "INSERT INTO etat_generets(nom) VALUES ('" + eG + "')";
					 * stm.execute(request);
					 */
					// stm.close();
				}
				// insï¿½rer dans le tableau mï¿½dication *******************************
				// String

				// for (int k = 0; k < newMedcine.size(); k++) {
				for (String medicationChip : this.chipView.getChips()) {
					boolean medcinesExist = false;
					for (int i = 0; i < medications.size(); i++) {
						if (medicationChip.equals(medications.get(i))) {

							medcinesExist = true;
							break;
						}
					}
					if (!medcinesExist) {

						String request = "INSERT INTO medications(nom) VALUES (?)";
						stmt = sl.getConnection().prepareStatement(request);
						stmt.setString(1, medicationChip);
						stmt.executeUpdate();
						stmt.close();
						/*
						 * String request = "INSERT INTO medications(nom) VALUES ('" + newMedcine.get(k)
						 * + "')"; stm.execute(request);
						 */
						// stm.close();
					}
				}
				// end insert into table medcin $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
				/*
				 * String tabMedicaments[] = med.split(","); int tabMedicamentId[] = new
				 * int[tabMedicaments.length];
				 * 
				 * for (int a = 0; a < tabMedicaments.length; a++) { //
				 * System.out.println("medicament :" + tabMedicaments[a]); boolean medicament =
				 * false; for (int i = 0; i < auto.size(); i++) { if
				 * (tabMedicaments[a].equalsIgnoreCase(auto.get(i).getNom())) {
				 * 
				 * medicament = true; break; } } if (!medicament) {
				 * 
				 * String request = "INSERT INTO medications(nom) VALUES ('" + tabMedicaments[a]
				 * + "')"; stm.execute(request);
				 * 
				 * }
				 * 
				 * } // stm.close();
				 */
				// rï¿½cupï¿½rer les donnï¿½es des trois champs
				// rï¿½cupï¿½rer de champ etat gï¿½nï¿½ret
				String request = "select etat_generet_id from etat_generets where nom=?";
				stmt = sl.getConnection().prepareStatement(request);
				stmt.setString(1, eG);
				// String request = "select etat_generet_id from etat_generets where nom='" + eG
				// + "'";
				System.out.println("teste           dkkfjd dkjf dkfj");
				rs = stmt.executeQuery();
				int etat_id = rs.getInt("etat_generet_id");
				stmt.close();
				System.out.println("etat id " + etat_id);
				// 1rï¿½cupï¿½rer de champ motif
				// System.out.println("motif: " + mtf);
				request = "select motif_id from motifs where nom=?";
				stmt = sl.getConnection().prepareStatement(request);
				stmt.setString(1, mtf);
				// request = "select motif_id from motifs where nom='" + mtf + "'";
				rs = stmt.executeQuery();
				// System.out.println(rs.getInt("motif_id"));
				int motif_id = rs.getInt("motif_id");
				System.out.println("motif id " + motif_id);
				stmt.close();
				// rs.close();

				// rï¿½cupï¿½rer de champ mï¿½dication
				int indice = 0;
				int[] tabMedicamentId = new int[this.chipView.getChips().size()];
				for (String medicament : this.chipView.getChips()) {
					request = "select medication_id from medications where nom=?";
					stmt = sl.getConnection().prepareStatement(request);
					stmt.setString(1, medicament);
					// request = "select medication_id from medications where nom='" + medicament +
					// "'";
					rs = stmt.executeQuery();
					tabMedicamentId[indice] = rs.getInt("medication_id");
					indice++;
					stmt.close();
				}

				// ajouter une consultation
				System.out.println("Medications are : " + Arrays.toString(tabMedicamentId));
				// System.out.println("etat_generet_id " + etat_id + " motif_id" + motif_id);
				/*
				 * request =
				 * "insert into consultation(malade_id, etat_generet_id, date_consultation,motif_id)"
				 * + "values('" + 1 + "','" + etat_id + "','25/06/1993','" + motif_id + "')";
				 * stm.execute(request);
				 * 
				 * Notifications notification = Notifications.create().title("la fiche malade")
				 * .text("la fiche malade a ï¿½tï¿½ enregistrer").graphic(null).hideAfter(Duration.
				 * seconds(2)) .position(Pos.BOTTOM_CENTER).onAction(new
				 * EventHandler<ActionEvent>() {
				 * 
				 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
				 * System.out.println("notification"); } }); notification.show();
				 */

				// *****************in this window we are going to add all
				// data************************************
				String pattern = "yyyy-MM-dd";
				SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
				String date = simpleDateFormat.format(new Date());
				// System.out.println(date);
				int maladId = malade.getId();
				request = "INSERT INTO consultations(malade_id,etat_generet_id,motif_id,date_consultation)"
						+ "VALUES (?,?,?,?)";

				stmt = sl.getConnection().prepareStatement(request);
				stmt.setInt(1, maladId);
				stmt.setInt(2, etat_id);
				stmt.setInt(3, motif_id);
				stmt.setString(4, date);
				stmt.executeUpdate();
				stmt.close();
				/*
				 * request =
				 * "INSERT INTO consultations(malade_id,etat_generet_id,motif_id,date_consultation)"
				 * + "VALUES ('" + maladId + "','" + etat_id + "','" + motif_id + "','" + date +
				 * "')"; stm.execute(request);
				 */
				System.out.println("Insert into table consultations with success");

				// selected the last id from consultaions table
				request = "SELECT consultation_id FROM consultations ORDER BY consultation_id DESC LIMIT 1;";
				stmt = sl.getConnection().prepareStatement(request);
				// request = "SELECT consultation_id FROM consultations ORDER BY consultation_id
				// DESC LIMIT 1;";
				rs = stmt.executeQuery();
				consultationId = rs.getInt("consultation_id");
				stmt.close();

				for (Diagnostique diagnostic : listDiagnostique) {
					request = "INSERT INTO diagnostics(consultation_id,diagnostique,traitement,devis,num_diagnostique)"
							+ "VALUES (?,?,?,?,?)";
					stmt = sl.getConnection().prepareStatement(request);
					stmt.setInt(1, consultationId);
					stmt.setString(2, diagnostic.getDiagnostique());
					stmt.setString(3, diagnostic.getTraitement());
					stmt.setFloat(4, diagnostic.getDevis());
					stmt.setInt(5, diagnostic.getOrdre());
					stmt.executeUpdate();
					/*
					 * request =
					 * "INSERT INTO diagnostics(consultation_id,diagnostique,traitement,devis,num_diagnostique)"
					 * + "VALUES ('" + consultationId + "','" + diagnostic.getDiagnostique() + "','"
					 * + diagnostic.getTraitement() + "','" + diagnostic.getDevis() + "','" +
					 * diagnostic.getOrdre() + "')"; stm.execute(request);
					 */
					stmt.close();
				}

				System.out.println("Insert into table diagnostics with success");
				for (int medication : tabMedicamentId) {
					request = "INSERT INTO comporte_medications(consultation_id,medication_id)" + "VALUES (?,?)";
					stmt = sl.getConnection().prepareStatement(request);
					stmt.setInt(1, consultationId);
					stmt.setInt(2, medication);
					stmt.executeUpdate();
					/*
					 * request = "INSERT INTO comporte_medications(consultation_id,medication_id)" +
					 * "VALUES ('" + consultationId + "','" + medication + "')";
					 * stm.execute(request);
					 */
					stmt.close();
				}

				System.out.println("Insert into table comporte_medications with success");

				// #################end of
				// addition###################################################################

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("erreur " + e.getMessage());
			} finally {

				try {
					rs.close();
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} else {
			consultationId = this.existDejaDoncIdConsultation;
			try {
				this.insertMedicationsIfnotExist();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		/*
		 * Stage stage = (Stage) btnEnregistrer.getScene().getWindow(); stage.close();
		 */
		// show the message of saving the patient-file
		/*
		 * Notifications notification = Notifications.create().title("Succes")
		 * .text("L'affiche a ï¿½tait enregistrï¿½ avec succï¿½").graphic(null).hideAfter(
		 * Duration.seconds(4)) .position(Pos.BOTTOM_CENTER).onAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
		 * System.out.println("notification"); } }); notification.show();
		 */
		/*
		 * JFXDialogLayout layout=new JFXDialogLayout(); layout.setHeading(new
		 * Text("SuccÃ©s")); layout.setBody(new
		 * Text("L'affiche a Ã©tait enregistrÃ© avec succÃ©s"));
		 * 
		 * JFXDialog dialog=new JFXDialog(stackPane, layout,
		 * JFXDialog.DialogTransition.CENTER);
		 * 
		 * JFXButton ok= new JFXButton("Fermer"); ok.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { dialog.close();
		 * 
		 * } });
		 * 
		 * layout.setActions(ok); dialog.show();
		 */
		switcher = 1;
		// enable button events
		btnEnregistrer.removeEventFilter(MouseEvent.ANY, handler);

		Stage sb = (Stage) btnEnregistrer.getScene().getWindow();
		// sb.close();

		try {
			this.refreshDiagnostics(consultationId);

			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Succés"));
			layout.setBody(new Text("L'affiche a été enregistrée avec succés"));

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
			
			FadeTransition ft = new FadeTransition(Duration.millis(1250), dialog);

			ft.setToValue(0);
			// ft.setAutoReverse(true);
			ft.setOnFinished(new EventHandler<ActionEvent>() {
				@Override
				public void handle(final ActionEvent actionEvent) {
					dialog.close();

					

				}
			});
			ft.play();
			
			this.ifSaved = true;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// switch scenes

	@FXML
	void openRedaction(MouseEvent event) {

		((Stage) ((AnchorPane) event.getSource()).getScene().getWindow()).setIconified(true);

		Stream<Window> open = Stage.getWindows().stream().filter(Window::isShowing);

		Stream<Integer> stream = Stream.of(new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 });
		stream.forEach(p -> System.out.println(p));
		open.forEach(ev -> {
			System.out.println(ev.getHeight());
			if (ev.getHeight() == 680.0) {
				
				FXMLLoader loader = (FXMLLoader) ev.getScene().getUserData();
				ActivationController homeController = (ActivationController) loader.getController();

				homeController.setOpenRedactionFromFicheMalade(malade);
				homeController.afficherRedaction(null);
			}
		}

		);

	}

	@FXML
	void supprimerDiagnostique(MouseEvent event) {
		
		this.animationForSavingData= new BounceIn(this.jfxButtonSave);
		this.animationForSavingData.setCycleCount(100000).play();
		
		Statement stm = null;
		ResultSet rs = null;
		Diagnostique supprimer = tableDiagnostique.getSelectionModel().getSelectedItem();
		// check if item different of null
		if (ifSelectedItem()) {
			System.out.println(supprimer.getOrdre());
			if (existDejaDoncIdConsultation != 0) {
				try {
					stm = new SQLiteJDBC().getConnection().createStatement();
					// delete the diagnostic father
					String request = "SELECT SUM(paye) sum_paye FROM diagnostics_detaille WHERE diagnostic_id = '"
							+ supprimer.getId() + "';";
					rs = stm.executeQuery(request);
					System.out.println("la somme de payement est= " + rs.getFloat(1));
					// return;
					// mince the payement in this day
					request = "UPDATE historic_malades SET paye = paye - '" + rs.getFloat(1) + "' "
							+ "WHERE historic_malade_id='" + malade.getHmID() + "'";
					stm.execute(request);
					request = "DELETE  FROM diagnostics WHERE consultation_id = '" + existDejaDoncIdConsultation + "' "
							+ "AND num_diagnostique = '" + supprimer.getOrdre() + "'";
					stm.execute(request);
					System.out.println("delete father diagno");
					// delete the details diagnostic children
					request = "DELETE  FROM diagnostics_detaille WHERE diagnostic_id = '" + supprimer.getId() + "';";
					stm.execute(request);
					System.out.println("delete children digno_details");

					tableDiagnostique.getItems().remove(supprimer);
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
					// rs.close();
				}
			}
		}
	}

	@FXML
	void supprimerPayment(MouseEvent event) {

	}

	@FXML
	void getMedication(KeyEvent event) {
		switch (event.getCode()) {
		// en cas de suppression
		case BACK_SPACE:
			// System.out.println("atik");

			break;
//		case ENTER:
//			// System.out.println("entrer");
//			medication.setText(medication.getText());
//			break;

		default:
			EventQueue.invokeLater(new Runnable() {

				public void run() {
					// TODO Auto-generated method stub
					// retourner le dernier champ
					String medications[] = medication.getText().split(",");
					System.out.println("mot " + medications.length);
					System.out.println("text " + medication.getText().length());
					autoComplete(medications[medications.length - 1], medication.getText().length());
				}
			});
		}
	}

	private void autoComplete(String txt, int taille) {
		String complete = "";
		int start = taille;
		int last = taille;

		for (int a = 0; a < auto.size(); a++) {
			if (auto.get(a).getNom().toString().startsWith(txt)) {
				complete = auto.get(a).getNom().toString().substring(txt.length());
				last = complete.length() + taille;
				break;
			}
		}
		if (last > start) {
			medication.setText(medication.getText().substring(0, taille) + complete);
			medication.selectRange(start, last);
			// input.positionCaret(last);
			// input.move

		}
	}

	@FXML
	void getMotif(KeyEvent event) {
		switch (event.getCode()) {

		case DOWN:
			etatGeneret.requestFocus();
			break;
		case ENTER:
			if (existDejaDoncIdConsultation != 0) {
				// Statement stm = null;
				ResultSet rs = null;
				PreparedStatement stmt = null;
				try {
					// stm = new SQLiteJDBC().getConnection().createStatement();

					String mtf = motif.getText();
					boolean motifExist = false;
					for (int i = 0; i < motifs.size(); i++) {
						if (mtf.equals(motifs.get(i))) {

							motifExist = true;
							break;
						}
					}

					if (!motifExist) {

						String request = "INSERT INTO motifs(nom) VALUES (?)";

						stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
						stmt.setString(1, mtf);
						stmt.executeUpdate();
						// String request = "INSERT INTO motifs(nom) VALUES ('" + mtf + "')";
						// stm.execute(request);
						request = "SELECT motif_id FROM motifs WHERE nom = ?";

						stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
						stmt.setString(1, mtf);

						// request = "SELECT motif_id FROM motifs WHERE nom = '" + mtf + "'";
						rs = stmt.executeQuery();

						this.motifId = rs.getInt("motif_id");

					}
					stmt.close();
					String request = "UPDATE consultations SET motif_id= ? " + "WHERE consultation_id=?";

					stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
					stmt.setInt(1, this.motifId);
					stmt.setInt(2, existDejaDoncIdConsultation);

					stmt.executeUpdate();
					stmt.close();
					/*
					 * String request = "UPDATE consultations SET motif_id='" + this.motifId + "' "
					 * + "WHERE consultation_id='" + existDejaDoncIdConsultation + "'";
					 * stm.execute(request);
					 */
					motif.selectRange(0, mtf.length());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						rs.close();
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}
			break;

		}
	}

	@FXML
	void changerPositionEtatG(KeyEvent event) {
		switch (event.getCode()) {
		case UP:
			// System.out.println("Down");
			motif.requestFocus();
			break;
		case DOWN:
			medicationCourant.requestFocus();
			break;
		case ENTER:
			if (existDejaDoncIdConsultation != 0) {
				PreparedStatement stmt = null;
				ResultSet rs = null;
				try {
					// stmt = new SQLiteJDBC().getConnection().createStatement();
					String eG = etatGeneret.getText();
					boolean etatGExist = false;
					for (int i = 0; i < etatGenerets.size(); i++) {
						if (eG.equals(etatGenerets.get(i))) {

							etatGExist = true;
							break;
						}
					}
					if (!etatGExist) {

						String request = "INSERT INTO etat_generets(nom) VALUES (?)";
						stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
						stmt.setString(1, eG);
						stmt.executeUpdate();
						stmt.close();
						/*
						 * String request = "INSERT INTO etat_generets(nom) VALUES ('" + eG + "')";
						 * stm.execute(request);
						 */
						request = "SELECT etat_generet_id FROM etat_generets WHERE nom = ?";
						stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
						stmt.setString(1, eG);
						// request = "SELECT etat_generet_id FROM etat_generets WHERE nom = '" + eG +
						// "'";
						rs = stmt.executeQuery();

						this.etatId = rs.getInt("etat_generet_id");

						stmt.close();
					}

					String request = "UPDATE consultations SET etat_generet_id=? " + "WHERE consultation_id=?";
					stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
					stmt.setInt(1, this.etatId);
					stmt.setInt(2, existDejaDoncIdConsultation);
					stmt.executeUpdate();
					stmt.close();
					/*
					 * String request = "UPDATE consultations SET etat_generet_id='" + this.etatId +
					 * "' " + "WHERE consultation_id='" + existDejaDoncIdConsultation + "'";
					 * stm.execute(request);
					 */
					etatGeneret.selectRange(0, eG.length());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					try {
						rs.close();
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

			break;

		}
	}

	@FXML
	void changerPositionMedication(KeyEvent event) {
		switch (event.getCode()) {
		case UP:
			// System.out.println("Down");
			etatGeneret.requestFocus();
			break;
		case DOWN:
			tDiagnostique.requestFocus();
			break;
		case ENTER:
			addMedicationInList(null);
			break;

		}
	}

	@FXML
	void changerPositionDiagnostique(KeyEvent event) {

		System.out.println("Debut..................");

		// EventHandler<KeyEvent> handler = KeyEvent::consume;

		// block events
		// tDiagnostique.addEventFilter(KeyEvent.ANY, handler);
		switch (event.getCode()) {
		case RIGHT:
			// System.out.println("Down");
			tTraitement.requestFocus();
			break;

		}
		System.out.println("Fin..................");
	}

	@FXML
	void changerPositionTraitement(KeyEvent event) {
		switch (event.getCode()) {
		case RIGHT:
			// System.out.println("Down");
			tDevis.requestFocus();
			break;
		case LEFT:
			tDiagnostique.requestFocus();
			break;

		}

	}

	@FXML
	void changerPositionDevis(KeyEvent event) {
		switch (event.getCode()) {
		case LEFT:
			// System.out.println("Down");
			tTraitement.requestFocus();
			break;
		case ENTER:
			// addMedicationInList(null);
			ajouterDiagnostique(null);
			tDiagnostique.requestFocus();
			break;

		}
	}
	
	@FXML
    private AnchorPane addRadioAnchor;
    @FXML
    void addRadioEntered(MouseEvent event) {

    	 //creating a 2D scale   
        Scale scale = new Scale();  
          
        // setting the X-y factors for the scale   
        scale.setX(1.05);  
        scale.setY(1.05);  
          
        //setting the pivot points along which the scaling is done  
       // scale.setPivotX(550);  
        //scale.setPivotY(200);  
        this.addRadioAnchor.getTransforms().add(scale);
        
    }
 
    @FXML
    void addRadioExited(MouseEvent event) {
    	this.addRadioAnchor.getTransforms().clear();
    }

    
    
    
    
    
    @FXML
    private AnchorPane showRadiosAnchor;
    @FXML
    void showRadiosEntred(MouseEvent event) {

    	 //creating a 2D scale   
        Scale scale = new Scale();  
          
        // setting the X-y factors for the scale   
        scale.setX(1.05);  
        scale.setY(1.05);  
          
        //setting the pivot points along which the scaling is done  
       // scale.setPivotX(550);  
        //scale.setPivotY(200);  
        this.showRadiosAnchor.getTransforms().add(scale);
        
    }
 
    @FXML
    void showRadiosExited(MouseEvent event) {
    	this.showRadiosAnchor.getTransforms().clear();
    }

    
    
    
    
    
    @FXML
    private AnchorPane redactionAnchor;
    @FXML
    void redactionEntered(MouseEvent event) {

    	 //creating a 2D scale   
        Scale scale = new Scale();  
          
        // setting the X-y factors for the scale   
        scale.setX(1.05);  
        scale.setY(1.05);  
          
        //setting the pivot points along which the scaling is done  
       // scale.setPivotX(550);  
        //scale.setPivotY(200);  
        this.redactionAnchor.getTransforms().add(scale);
        
    }
 
    @FXML
    void redactionExited(MouseEvent event) {
    	this.redactionAnchor.getTransforms().clear();
    }

    @FXML
    void captureRadios(ActionEvent event) {
    	Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		
		FXMLLoader root;
		Parent parent;

		try {

			root = new FXMLLoader(getClass().getResource("/application/views/radioCapture.fxml"));

			parent = root.load();
			Scene scene = new Scene(parent);

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent ke) {
					// TODO Auto-generated method stub
					if (ke.getCode() == KeyCode.ESCAPE) {
						primaryStage.close();
					}
				}
			});
			// application.controllers.AddPatientController
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			// TODO: handle exception
		}
    }
	
}
