package application.controllers.login;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import com.itextpdf.text.List;

import java.util.Random;
import java.util.ResourceBundle;

import org.controlsfx.control.Notifications;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.itextpdf.text.Anchor;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleButton;
import com.jfoenix.validation.RequiredFieldValidator;

import animatefx.animation.BounceIn;
import animatefx.animation.BounceInRight;
import animatefx.animation.FadeOut;
import animatefx.animation.FadeOutRight;
import animatefx.animation.Flash;
import animatefx.animation.SlideInRight;
import animatefx.animation.ZoomIn;
import application.Appointment;
import application.DataBaseConnection;
import application.DiskUtils;
import application.Main;
import application.MyQr;
import application.SQLiteJDBC;
import application.controllers.AddPatientController;
import application.controllers.FicheMaladeController;
import application.controllers.HistoriqUsers;
import application.controllers.Malade;
import application.controllers.appointment.AppointmentController;
import application.controllers.prothese.AddLaboController;
import application.controllers.prothese.LabDetaille;
import application.controllers.prothese.RecordShuttleController;
import application.controllers.tache.TacheController;
import application.cryptage.Cryptage;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.Font;

import javafx.scene.text.Text;
import javafx.scene.transform.Scale;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class ActivationController implements Initializable {

	@FXML
	private StackPane stackPane;
	@FXML
	private JFXTextField serial;

	@FXML
	private JFXToggleButton saveAuthToggleButton;

	@FXML
	private AnchorPane homeButton;
	@FXML
	private AnchorPane redactionButton;
	@FXML
	private AnchorPane statisticsButton;

	/*
	 * @FXML private StackPane parentStackPane;
	 */
	@FXML
	private TextField tRecherche;
	@FXML
	private AnchorPane navigation;
	@FXML
	private AnchorPane redaction;

	@FXML
	private Text userTitle;
	@FXML
	private ImageView maladeView;

	@FXML
	private ImageView redactionView;
	@FXML
	private JFXTextField jfxTPatientRedaction;
	@FXML
	private ImageView statistiqueView;

	@FXML
	private ImageView depenseView;

	@FXML
	private ImageView protheseView;

	@FXML
	private ImageView agendaView;

	@FXML
	private AnchorPane agenda;

	@FXML
	private AnchorPane depense;
	@FXML
	private ImageView historiqueView;
	@FXML
	private AnchorPane historique;
//attribut prothese
	// graphical
	@FXML
	private JFXComboBox<String> cbLab;

	@FXML
	private TableView<LabDetaille> tableLabInfo;

	@FXML
	private DatePicker dateDebutLab;

	@FXML
	private DatePicker dateFinLab;

	@FXML
	private JFXTextField tType;

	@FXML
	private JFXTextField tQte;

	@FXML
	private JFXTextField tPrixUnitaire;
//terminal
	// pour ordonner le tableau de chaque labo
	private int orderOfLabDetaille = 0;
	// array de tableau tableLabInfo
	private ArrayList<LabDetaille> labDetailleArray;
	// array de comboBox tableLabInfo
	private ArrayList<String> labDetailleCombBoxArray;
	private Hashtable<String, Number> labDetailleHashTable;
	// fin prothese

	@FXML
	private AnchorPane statistique;

	@FXML
	private AnchorPane malade;

	@FXML
	private AnchorPane prothese;
	@FXML
	public JFXDatePicker dateListe;
	public static String dateListeString;
	@FXML
	private ImageView closeButton;
	// table view
	@FXML
	public TableView<Malade> tabPatient;
	// la liste des tous les malades
	public ObservableList<Malade> allMalade;
//cette attribut pour détecter la double click
	private double lastTime;
	private boolean ifClicked = false;
	private Malade selectedMalade;
//sous menu
	ContextMenu cm;
	MenuItem menuAjouterDateNow;
	MenuItem menuTeste;
	private SQLiteJDBC sl;

	/*
	 * *********************Begin snapSound attributes
	 *******************************/
	/*
	 * String ssound = "assets//homeSound.mp3"; Media sound = new Media(new
	 * File(ssound).toURI().toString()); MediaPlayer mediaPlayer = new
	 * MediaPlayer(sound);
	 */

	// add construct for a TimerTask
	public Appointment appointment;
	public Hashtable<Integer, Boolean> appointmentTimerTask;

	public ActivationController(Appointment appointment) {

		this.appointment = appointment;

	}

	/*
	 * @Override public void run() {
	 * 
	 * System.out.println("Monsieur/Madame " + this.appointment.getFullName()); //
	 * JOptionPane.showMessageDialog(null, "Monsieur/Madame //
	 * "+this.appointment.getFullName()+" a un rendez-vou aujourd-hui Ã  //
	 * "+this.appointment.getHour());
	 * 
	 * // TODO Auto-generated method stub
	 * 
	 * /* JFXDialogLayout layout = new JFXDialogLayout(); layout.setHeading(new
	 * Text("Info")); layout.setBody( new
	 * Text("Monsieur/Madame "+this.appointment.getFullName()
	 * +" a un rendez-vou aujourd-hui Ã  "+this.appointment.getHour()));
	 * 
	 * 
	 * JFXDialog dialog = new JFXDialog(stackPane, layout,
	 * JFXDialog.DialogTransition.CENTER);
	 * 
	 * JFXButton ok = new JFXButton("Fermer"); ok.setOnAction(new
	 * EventHandler<ActionEvent>() {
	 * 
	 * @Override public void handle(ActionEvent event) { dialog.close();
	 * 
	 * } });
	 * 
	 * layout.setActions(ok); dialog.show();
	 * 
	 * 
	 */

//	}

	// public Timer timer = new Timer();

	/*
	 * public void fillAppointmentTimerTask() {
	 * 
	 * DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	 * 
	 * LocalDate myDate = LocalDate.now(); DateTimeFormatter myFormatHour =
	 * DateTimeFormatter.ofPattern("HH:mm"); // DateTimeFormatter myFormatDate =
	 * DateTimeFormatter.ofPattern("HH:mm"); //
	 * 
	 * //System.out.println("Hour::::: " + myDate.format(myFormat));
	 * 
	 * for (Appointment appointment : this.arrayListAppointment) {
	 * 
	 * if (appointment.getDate().equals(myDate(myFormatDate))) {
	 * 
	 * 
	 * appointmentTimerTask.put(myDate(myFormatDate)+" "+myDate(myFormatHour),
	 * appointment);
	 * 
	 * 
	 * } } }
	 */
	/**********************
	 * End snapSound attributes
	 **********************************/

//	    @FXML
//	    private TableColumn<?, ?> nomColumn;
//
//	    @FXML
//	    private TableColumn<?, ?> prenomColumn;
//
//	    @FXML
//	    private TableColumn<?, ?> ageColumn;
//
//	    @FXML
//	    private TableColumn<?, ?> sexeColumn;
//
//	    @FXML
//	    private TableColumn<?, ?> adresseColumn;
//
//	    @FXML
//	    private TableColumn<?, ?> phoneColumn;
//
//	    @FXML
//	    private TableColumn<?, ?> fonctionColumn;

	static public int userId;

	public ActivationController() {
	};

	public ActivationController(String userTitle, int userId) {
		this.userTitleText = userTitle;
		this.userId = userId;

		System.out.println(
				"lllllllllllloooggggggggggggggggggggggooooooooooooooooo" + this.userTitleText + " ID: " + this.userId);
	}

	public static String logoName;
	private ArrayList<HistoriqUsers> historiqUsersArray = new ArrayList<HistoriqUsers>();

	// the logo of the cabinet
	@FXML
	private Label labeLogo;

	public static void initLogo(String logo) {
		// TODO Auto-generated constructor stub
		System.out.println("logoooooooooooooooo " + logo);
		logoName = logo;

	}

	@FXML
	private VBox tasksList;

	@FXML
	void afficherTaches(MouseEvent event) {

		// this.tasksList.setVisible(true);
		tacheScrollPane.setVisible(true);

	}

	@FXML
	void hideTaches(MouseEvent event) {

		tacheScrollPane.setVisible(false);

	}

	@FXML
	Button tacheMenu;

	public void createNTask() {

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		TacheController controller = new TacheController(this.userId);
		FXMLLoader root;
		Parent parent;

		try {

			// this.tacheScrollPane.setVisible(false);
			System.out.println("atik");
			root = new FXMLLoader(getClass().getResource("/application/views/taches/taches.fxml"));

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

			// application.controllers.AddPatientController
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.showAndWait();

			if (controller.isSuccess()) {

				/*
				 * Notifications notification =
				 * Notifications.create().title("Ajout d'une tÃ¢che")
				 * .text("Une tÃ¢che a été ajoutée avec succÃ¨s, nous espérons que vous la terminerez bientÃ´t."
				 * ).graphic(null).hideAfter(Duration.seconds(4))
				 * .position(Pos.BOTTOM_CENTER).onAction(new EventHandler<ActionEvent>() {
				 * 
				 * public void handle(ActionEvent arg0) { // TO DO Auto-generated method stub
				 * System.out.println("notification"); } }); notification.show();
				 */
				JFXDialogLayout layout = new JFXDialogLayout();
				layout.setHeading(new Text("Ajout d'une nouvelle tâche"));
				layout.setBody(
						new Text("Une tâche a été ajoutée avec succées, nous espérons que vous la terminerez bientôt"));

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

				// tasksList.getChildren().clear();
				System.out.println("Kemmmoune");
				refreshListTasks();
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	private void refreshListTasks() {
		Statement stm = null;
		ResultSet rs = null;
		String request = "";
		ScrollPane toor;

		String style;
		String styleDetetB;

		vboxTasksSlider.getChildren().clear();
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = "SELECT * FROM tasks";
			rs = stm.executeQuery(request);

			int cmpt = 1;

			while (rs.next()) {

				// toor =
				// FXMLLoader.load(getClass().getResource("../views/taches/taches.fxml"));

				// add 32/08/2021

				AnchorPane root = null;
				System.out.println("ddddddddddddddddddddd");
				try {

					root = FXMLLoader.load(getClass().getResource("/application/views/taches/tasks.fxml"));
					AnchorPane fff = root;
					fff.setPrefWidth(339);
					fff.setPrefHeight(135);
					((Label) fff.getChildren().get(0)).setText("Tâche " + cmpt);

					((Label) fff.getChildren().get(1)).setText(rs.getString("task"));

					vboxTasksSlider.getChildren().add(fff);
					int taskId = rs.getInt(1);

					// addListner to button of delete
					((JFXButton) fff.getChildren().get(2)).setOnAction(evt -> {
						PreparedStatement stmt2 = null;
						// System.out.println("existe "existDejaDoncIdConsultation);

						try {

							String request2 = "DELETE FROM tasks WHERE task_id =? ";

							stmt2 = new SQLiteJDBC().getConnection().prepareStatement(request2);

							stmt2.setInt(1, taskId);
							stmt2.executeUpdate();
							vboxTasksSlider.getChildren().remove(fff);
							refreshListTasks();
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

					});

					cmpt++;

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// End add 32/08/2021
			}
			// tacheMenu.setText(cmpt - 1 + " TÃ¢ches");
			tasksLabel.setText(cmpt - 1 + "");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void afficherStatistique(ActionEvent event) {
		/*
		 * if (tacheScrollPane.isVisible()) { // tasksList.setVisible(false);
		 * this.tacheScrollPane.setVisible(false); } else { //
		 * tasksList.setVisible(true); this.tacheScrollPane.setVisible(true); }
		 */
		depense.setVisible(true);
		statistique.setVisible(false);
		historique.setVisible(false);
		malade.setVisible(false);
		prothese1.setVisible(false);
		redaction.setVisible(false);

	}

	@FXML
	private AnchorPane prothese1;
	@FXML
	private ScrollPane tacheScrollPane;

	// for detect age of Patient
	private Hashtable<String, Integer> hashTableDetectAgePatient;

	@FXML
	private Button detailed;
	@FXML
	private Button delete;
	@FXML
	private Button addToday;

	@FXML
	private Button addPatient;
	@FXML
	private Button buttonUpdatePatient;

	@FXML
	private Button btnAddMedecine;

	@FXML
	private Button btnPrintOrder;
	@FXML
	private Button btnRefreshOrder;

	@FXML
	private Button btnPrintJustification;
	@FXML
	private Button btnStopJobOrder;
	@FXML
	private Button btnPrintBell;

	@FXML
	private Button buttonPrintFreeRedaction;
	@FXML
	private Button buttonRefreshFreeRedaction;
	@FXML
	private ScrollPane scrollPaneParameter;
	private Hashtable<String, Integer> hashTableDetectIDPatient;
	private String dateOfToday;

	public void initialize(URL arg0, ResourceBundle arg1) {

		// check If the user is logged Then Limit access to App
		if (this.userId != 1) {

			this.comboBoxSelectUser.setVisible(false);
			saveAuthToggleButton.setVisible(false);

		}

		// scrollPaneParameter.setFitToWidth(true);
		// start the logo of kachi and yassine's company
		// loatSplashScreen();

		Tooltip ttDetailed = new Tooltip();
		ttDetailed.setText("Pour plus détailles sur le patient.");
		ttDetailed.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttDelete = new Tooltip();
		ttDelete.setText("Pour supprimer le patient.");
		ttDelete.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttAddToday = new Tooltip();
		ttAddToday.setText("Pour ajouter le patient à  la date d'aujourdhui.");
		ttAddToday.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttAddPatient = new Tooltip();
		ttAddPatient.setText("Pour ajouter un nouveau patient.");
		ttAddPatient.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttUpdatePatientData = new Tooltip();
		ttUpdatePatientData.setText("Modifier les informations du patient.");
		ttUpdatePatientData
				.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttAddMedecine = new Tooltip();
		ttAddMedecine.setText("Ajouter un médicament.");
		ttAddMedecine.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttAddPrint = new Tooltip();
		ttAddPrint.setText("Imprimer.");
		ttAddPrint.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttAddRefreshOrder = new Tooltip();
		ttAddRefreshOrder.setText("Actucaliser.");
		ttAddRefreshOrder
				.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		Tooltip ttPrintBell = new Tooltip();
		ttPrintBell.setText("Imprimer la facture.");
		ttPrintBell.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

		detailed.setTooltip(ttDetailed);
		delete.setTooltip(ttDelete);
		addToday.setTooltip(ttAddToday);
		// addPatient.setTooltip(ttAddPatient);
		buttonUpdatePatient.setTooltip(ttUpdatePatientData);
		btnAddMedecine.setTooltip(ttAddMedecine);
		btnPrintOrder.setTooltip(ttAddPrint);
		btnRefreshOrder.setTooltip(ttAddRefreshOrder);
		btnPrintJustification.setTooltip(ttAddPrint);
		btnStopJobOrder.setTooltip(ttAddPrint);
		// btnPrintBell.setTooltip(ttPrintBell);
		buttonPrintFreeRedaction.setTooltip(ttAddPrint);
		buttonRefreshFreeRedaction.setTooltip(ttAddRefreshOrder);

		// this.vBoxPrincipale.getChildren().add(toor);

		// initialize the hashTable of medcine
		try {
			this.inithashTableInsertMedecineIfNotExist();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// this.labeLogo.setText(this.userTitleText);
		sl = new SQLiteJDBC();
		// TODO Auto-generated method stub
		// prothese.setVisible(false);
		// malade.setVisible(false);
//add validator number to textfield

		// force the field to be numeric only

		/*
		 * tQte.textProperty().addListener(new ChangeListener<String>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends String> observable,
		 * String oldValue, String newValue) { if (!newValue.matches("\\d*")) {
		 * tQte.setText(newValue.replaceAll("[^\\d]", "")); } } });
		 * tPrixUnitaire.textProperty().addListener(new ChangeListener<String>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends String> observable,
		 * String oldValue, String newValue) { if (!newValue.matches("\\d*\\.\\d*")) {
		 * tPrixUnitaire.setText(newValue.replaceAll("[^\\d\\.^\\d]", "")); } } });
		 */
		// initialser la date d'aujourdhui

		LocalDate myDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		dateListe.setValue(myDate);
		dateListeString = myFormat.format(myDate).toString();

		// store the date of today for using in synchronized the date picker
		this.dateOfToday = myFormat.format(myDate).toString();
		// sous menu
		cm = new ContextMenu();
		menuAjouterDateNow = new MenuItem("Ajouter à la liste d'aujourdhui");
		menuTeste = new MenuItem("Supprimer");

		// add a event to subMenu
		cm.setOnAction(new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub

				if (((MenuItem) event.getTarget()).getText() == "Supprimer") {
					supprimerMalade();
				} else if (((MenuItem) event.getTarget()).getText() == "Ajouter à la liste d'aujourdhui") {
					ajouterDateAujourdhui();
				}

			}

		});

		cm.getItems().addAll(menuAjouterDateNow, menuTeste);

		TableColumn<Malade, String> fullName = new TableColumn<Malade, String>("full name");
		TableColumn<Malade, String> lastName = new TableColumn<Malade, String>("nom");
		TableColumn<Malade, String> firstName = new TableColumn<Malade, String>("prénom");
		fullName.getColumns().addAll(lastName, firstName);

		TableColumn<Malade, String> ageColumn = new TableColumn<Malade, String>("age");
		TableColumn<Malade, String> sexeColumn = new TableColumn<Malade, String>("sexe");
		TableColumn<Malade, String> adresseColumn = new TableColumn<Malade, String>("adresse");
		TableColumn<Malade, String> phoneColumn = new TableColumn<Malade, String>("phone");
		TableColumn<Malade, String> fonctionColumn = new TableColumn<Malade, String>("fonction");
		TableColumn<Malade, String> acteColumn = new TableColumn<Malade, String>("acte");
		TableColumn<Malade, Number> payeColumn = new TableColumn<Malade, Number>("payé");
		TableColumn<Malade, Number> dateColumn = new TableColumn<Malade, Number>("Date");
		// chaque colone a la liste des consultation
		TableColumn<Malade, ComboBox<String>> consultation = new TableColumn<Malade, ComboBox<String>>("consultation");
		// ajouter sub column

		lastName.setCellValueFactory(new PropertyValueFactory("nom"));
		firstName.setCellValueFactory(new PropertyValueFactory("prenom"));
		adresseColumn.setCellValueFactory(new PropertyValueFactory("adresse"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory("phone"));
		sexeColumn.setCellValueFactory(new PropertyValueFactory("sexe"));
		ageColumn.setCellValueFactory(new PropertyValueFactory("age"));
		fonctionColumn.setCellValueFactory(new PropertyValueFactory("profession"));
		consultation.setCellValueFactory(new PropertyValueFactory("listConsultation"));
		payeColumn.setCellValueFactory(new PropertyValueFactory("paye"));
		acteColumn.setCellValueFactory(new PropertyValueFactory("acte"));

		dateColumn.setCellValueFactory(new PropertyValueFactory("dateArriver"));
		// the set width of column
		lastName.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.25));
		firstName.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.25));
		adresseColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.35));
		phoneColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.2));
		sexeColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.05));
		ageColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.1));
		fonctionColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.15));
		consultation.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.1));
		payeColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.2));
		acteColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.5));
		dateColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.2));

		lastName.setResizable(false);
		firstName.setResizable(false);
		adresseColumn.setResizable(false);
		phoneColumn.setResizable(false);
		sexeColumn.setResizable(false);
		ageColumn.setResizable(false);
		fonctionColumn.setResizable(false);
		consultation.setResizable(false);
		payeColumn.setResizable(false);
		acteColumn.setResizable(false);
		dateColumn.setResizable(false);
		// set sort type for last name
		lastName.setSortType(TableColumn.SortType.DESCENDING);

		// remplier le tableau des malades de maniÃ¨re cacher
		allMalade = fillTable();
		// ObservableList<Malade> list2=FXCollections.observableArrayList(new
		// Malade("ati", "yassine", "cctiti", "25555", "homme", 25));

		// remplir le tableau de maniÃ¨re réelle
		ObservableList<Malade> arrayRecherche = allMalade;
		ArrayList<Malade> arrayR = new ArrayList<Malade>();

		String dateSelected = dateListe.getValue().toString();
		float sumPaye = 0;

		// This where we initailze combobox for users and is shown only for Admin
		this.initializeComboBoxSelelctUser();

		String[] userSplit = this.comboBoxSelectUser.getValue().split("_");
		int userIdAfterParse = Integer.parseInt(userSplit[0]);
		for (Malade malade : arrayRecherche) {
			if (malade.getDateArriver().equals(dateSelected) && malade.getUser() == userIdAfterParse) {
				arrayR.add(malade);
				sumPaye += malade.getPaye();

			}
		}
		tabPatient.setItems(FXCollections.observableArrayList(arrayR));
		// tabPatient.setItems(allMalade);
		tfTotale.setText("TOTALE: " + sumPaye);
		tabPatient.getColumns().addAll(fullName, ageColumn, acteColumn, payeColumn, dateColumn);
		// ObservableList<String> list = FXCollections.observableArrayList("Masculin",
		// "Féminin");
		// tabPatient.setItems(list);

		/*
		 * Platform.runLater(() -> {
		 * 
		 * 
		 * 
		 * }
		 * 
		 * );
		 */

		// System.out.println("user from home "+this.userTitleText);
		// this.userTitle.setText(this.userTitleText);

		// initialize the historique users page

		TableColumn<HistoriqUsers, Number> idColumn = new TableColumn<HistoriqUsers, Number>("id");
		TableColumn<HistoriqUsers, String> dateColumn2 = new TableColumn<HistoriqUsers, String>("Date");
		TableColumn<HistoriqUsers, String> userColumn = new TableColumn<HistoriqUsers, String>("Utilisateur");
		TableColumn<HistoriqUsers, String> actionColumn = new TableColumn<HistoriqUsers, String>("Action");

		idColumn.setCellValueFactory(new PropertyValueFactory("id"));
		dateColumn2.setCellValueFactory(new PropertyValueFactory("date"));
		userColumn.setCellValueFactory(new PropertyValueFactory("user"));
		actionColumn.setCellValueFactory(new PropertyValueFactory("action"));

		// the set width of column
		// idColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.1));
		dateColumn2.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.2));
		userColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.3));
		actionColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.5));
		dateColumn2.setResizable(false);
		userColumn.setResizable(false);
		actionColumn.setResizable(false);

		// tabPatient.setItems(FXCollections.observableArrayList(arrayR));
		// tabPatient.setItems(allMalade);

		/*
		 * tableHistorique.getColumns().addAll(dateColumn, userColumn, actionColumn);
		 * 
		 * // if(activeFilter.isSelected()) {
		 * 
		 * dateSearch.setDisable(true); userSearch.setDisable(true);
		 * actionSearch.setDisable(true); // }
		 */
		Statement stm = null;
		ResultSet rs = null;
		String request = null;

		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = "SELECT * FROM users";
			rs = stm.executeQuery(request);
			ArrayList<String> userSearch = new ArrayList<String>();
			searchUserHashTable = new Hashtable<String, Number>();
			while (rs.next()) {
				System.out.println("Ø§Ù„Ø¥Ø³Ù…: " + rs.getString(3) + " Ø§Ù„Ù„Ù‚Ø¨ " + rs.getString(5));
				searchUserHashTable.put(rs.getString(2), rs.getInt(1));
				userSearch.add(rs.getString(2));
			}
			// this.userSearch.setItems(FXCollections.observableArrayList(userSearch));

			ArrayList<String> actionArray = new ArrayList<String>();
			// actionArray.addAll("","","","");
			actionArray.add("Connexion");
			actionArray.add("Déconnexion");
			actionArray.add("Ajouter un patient");

			// this.actionSearch.setItems(FXCollections.observableArrayList(actionArray));
			// request=""

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

		// remplireHistoriqUsers();

		// end intialize the historique users page

		// Prothese initialization
		// initialize the prothese page

		TableColumn<LabDetaille, Number> ordreLabColumn = new TableColumn<LabDetaille, Number>("NÂ°=");
		TableColumn<LabDetaille, String> dateLabColumn = new TableColumn<LabDetaille, String>("Date");
		TableColumn<LabDetaille, String> typeLabColumn = new TableColumn<LabDetaille, String>("Type");
		TableColumn<LabDetaille, Number> prixUniLabColumn = new TableColumn<LabDetaille, Number>("Prix unitaire");
		TableColumn<LabDetaille, Number> prixGlobalLabColumn = new TableColumn<LabDetaille, Number>("Prix globale");
		TableColumn<LabDetaille, Number> qteLabColumn = new TableColumn<LabDetaille, Number>("Qte");

		ordreLabColumn.setCellValueFactory(new PropertyValueFactory("ordre"));
		dateLabColumn.setCellValueFactory(new PropertyValueFactory("date"));
		typeLabColumn.setCellValueFactory(new PropertyValueFactory("type"));
		prixUniLabColumn.setCellValueFactory(new PropertyValueFactory("prixUnitaire"));
		prixGlobalLabColumn.setCellValueFactory(new PropertyValueFactory("prixGlobal"));
		qteLabColumn.setCellValueFactory(new PropertyValueFactory("qte"));

		// the set width of column
		// idColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.1));
		ordreLabColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.05));
		dateLabColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.15));
		typeLabColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.15));
		prixUniLabColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.2));
		prixGlobalLabColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.2));
		qteLabColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.15));

		ordreLabColumn.setResizable(false);
		dateLabColumn.setResizable(false);
		typeLabColumn.setResizable(false);
		prixUniLabColumn.setResizable(false);
		prixGlobalLabColumn.setResizable(false);
		qteLabColumn.setResizable(false);

		/*
		 * tableLabInfo.getColumns().addAll(ordreLabColumn, dateLabColumn,
		 * typeLabColumn, prixUniLabColumn, qteLabColumn, prixGlobalLabColumn);
		 */

		// remplire la l'array tableau labinfo
		labDetailleArray = new ArrayList<LabDetaille>();
		this.InitializArrayTableInfo();
		// remplire le combobox de labinfo
		labDetailleCombBoxArray = new ArrayList<String>();
		labDetailleHashTable = new Hashtable<String, Number>();
		InitilizeComboBoxAndHashTableOfTableInfo();
		this.cbLab.setItems(FXCollections.observableArrayList(this.labDetailleCombBoxArray));

		// insert the autocomplete list in redaction's view
		InitializePatientList();
		TextFields.bindAutoCompletion(this.tfPatientStopJob1, this.patientListComplete);
		TextFields.bindAutoCompletion(this.tfPatientJustification1, this.patientListComplete);
		TextFields.bindAutoCompletion(jfxTPatientRedaction, this.patientListComplete);
		// this.initializeValidatorjfxTPatientRedaction();
		// initialize date of stopingJob
		// LocalDateTime myDate1 = LocalDateTime.now();
		// tDateStopingJob.setText(myDate.getDayOfMonth() + "/" + myDate.getMonthValue()
		// + "/" + myDate.getYear());

		this.hashTableDetectAgePatient = new Hashtable<String, Integer>();
		this.hashTableDetectIDPatient = new Hashtable<String, Integer>();
		// Detect Age Of Patient
		for (Malade malade : this.fillTable()) {
			System.out.println(malade.getNom() + " " + malade.getPrenom());
			this.hashTableDetectAgePatient.put(malade.getNom() + " " + malade.getPrenom(), malade.getAge());
			this.hashTableDetectIDPatient.put(malade.getNom() + " " + malade.getPrenom(), malade.getId());

		}

		// addListner to change for type of Lab
		/*
		 * tType.textProperty().addListener(new ChangeListener<String>() {
		 * 
		 * @Override public void changed(ObservableValue<? extends String> observable,
		 * String oldValue, String newValue) {
		 * 
		 * // System.out.println(" Text Changed to " + newValue + ")\n");
		 * 
		 * if (typeLabHashTable.containsKey(newValue)) {
		 * tPrixUnitaire.setText(typeLabHashTable.get(newValue) + "");
		 * tQte.requestFocus(); } } });
		 */
		this.typePriceBise = new ArrayList<String>();

		// intialize tasks
		refreshListTasks();

		// intialize Paremeter AnchorPane
		ArrayList<String> townArray = new ArrayList<String>();
		townArray.add("BLIDA");
		townArray.add("ALGER");
		townArray.add("TIPAZA");
		townArray.add("ORAN");
		townArray.add("CONSTANTINE");
		townArray.add("ANNABA");

		this.town.setItems(FXCollections.observableArrayList(townArray));
		refreshParameters();

		// intlize the Appointemnt1122
		this.dLAppointment.setValue(myDate);
		this.initializeTabAppointment();
		this.refreshTabAppointment();
		this.selectDateAppointment(null);

		// for Animate the Appointment
		Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(55), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				System.out.println("this is called every 55 seconds on UI thread");

				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
				LocalDateTime now = LocalDateTime.now();

				// System.out.println("Hour::::: " + myDate.format(myFormat));

				if (hashTableAppointment.containsKey(dtf.format(now))) {

					JFXDialogLayout layout = new JFXDialogLayout();
					layout.setHeading(new Text("Rendez-Vous"));
					layout.setBody(new Text(
							"Monsieur/Madame " + hashTableAppointment.get(dtf.format(now)) + " a un rendez-vous"));
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

					/*
					 * String ssound = "assets//appointment2.mp3"; Media sound = new Media(new
					 * File(ssound).toURI().toString()); MediaPlayer mediaPlayer = new
					 * MediaPlayer(sound); mediaPlayer.play();
					 */

				}
			}
		}));
		fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
		fiveSecondsWonder.play();

		// for synchronized datePicker to date system
		Timeline fiveMinutesWonder = new Timeline(new KeyFrame(Duration.seconds(300), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				LocalDate myDate = LocalDate.now();
				DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				System.out.println("Every 5 Minutes..." + dateListe.getValue().toString());
				if (!myFormat.format(myDate).toString().equals(dateOfToday)) {

					System.out.println("The two dates aren't equally, So changes them.");
					dateListe.setValue(myDate);
					dateOfToday = myFormat.format(myDate).toString();
				}

			}

		}));
		fiveMinutesWonder.setCycleCount(Timeline.INDEFINITE);
		fiveMinutesWonder.play();

		// add Timer to Home contrller for Appointment
		/*
		 * try { appointmentTimerTask = new Hashtable<Integer, Boolean>();
		 * this.fillAppointmentTimerTask(); } catch (ParseException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */

		// this.tabAppointment.setItems(FXCollections.observableArrayList(this.arrayListAppointment));

		this.initializeValidatorjfxTPatientRedaction();

	}

	private void initializeValidatorjfxTPatientRedaction() {
		// TODO Auto-generated method stub

		// validate type
		RequiredFieldValidator validatorType = new RequiredFieldValidator();

		this.jfxTPatientRedaction.getValidators().add(validatorType);
		this.tfPatientJustification1.getValidators().add(validatorType);
		this.tfPatientStopJob1.getValidators().add(validatorType);

		validatorType.setMessage("Ce patient n'éxiste pas, il faut ajouter dabord");

		this.jfxTPatientRedaction.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {// when focus lost
					if (!hashTableDetectAgePatient.containsKey(jfxTPatientRedaction.getText())) {// !textField.getText().matches("[1-5]\\.[0-9]|6\\.0")
						jfxTPatientRedaction.setText("");

					}
					jfxTPatientRedaction.validate();

				}
			}
		});

		this.tfPatientJustification1.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {// when focus lost
					if (!hashTableDetectAgePatient.containsKey(tfPatientJustification1.getText())) {// !textField.getText().matches("[1-5]\\.[0-9]|6\\.0")
						tfPatientJustification1.setText("");

					}
					tfPatientJustification1.validate();

				}
			}
		});

		this.tfPatientStopJob1.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {// when focus lost
					if (!hashTableDetectAgePatient.containsKey(tfPatientStopJob1.getText())) {// !textField.getText().matches("[1-5]\\.[0-9]|6\\.0")
						tfPatientStopJob1.setText("");

					}
					tfPatientStopJob1.validate();

				}
			}
		});

	}

	// The old Password in Parameter
	private String oldPasswordS;

	private void refreshParameters() {
		// .555

		Statement stm = null;
		ResultSet rs = null;
		this.typePriceBise.clear();
		// this.typePriceBise=new ArrayList<String>();
		// ArrayList<String> typePriceBise=null;
		String request = "SELECT * FROM users  WHERE  user_id='" + this.userId + "' ";

		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			rs = stm.executeQuery(request);

			while (rs.next()) {
				System.out.println("Barhouma");
				String name = rs.getString(2);
				String nameAR = rs.getString(3);
				String spec = rs.getString(4);
				String specAR = rs.getString(5);
				String address = rs.getString(6);
				String munisipality = rs.getString(7);

				String town = rs.getString(8);
				String phone = rs.getString(9);
				String mail = rs.getString(10);
				this.oldPasswordS = rs.getString(11);

				this.nameL.setText(name);
				this.nameAR.setText(nameAR);
				this.specialityL.setText(spec);
				this.specialityAR.setText(specAR);
				this.address.setText(address);
				this.municipality.setText(munisipality);

				System.out.println("Town   " + town);
				if (town != null) {
					this.town.setValue(town);
				}
				this.phone.setText(phone);
				this.mail.setText(mail);

				this.saveAuthToggleButton.setSelected(rs.getBoolean(12));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ArrayList<String> typePriceBise;

	// private ArrayList<String> typePriceLab;
	private void settypeLabHashTable() {

		Statement stm = null;
		ResultSet rs = null;
		this.typePriceBise.clear();
		// this.typePriceBise=new ArrayList<String>();
		// ArrayList<String> typePriceBise=null;
		String request = "SELECT DISTINCT type,prix FROM labos_data  WHERE  labo_id='"
				+ this.labDetailleHashTable.get(this.cbLab.getSelectionModel().getSelectedItem()) + "' ";
		try {

			stm = new SQLiteJDBC().getConnectionProthese().createStatement();
			rs = stm.executeQuery(request);

			while (rs.next()) {

				this.typeLabHashTable.put(rs.getString(1), rs.getFloat(2));
				typePriceBise.add(rs.getString(1) + "");
			}

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

		// String request = "select medication_id from medications where nom='" +
		// label.getText() + "'";

	}

	// the list of patient for autocomplete in redaction
	public static ArrayList<String> patientListComplete = new ArrayList<String>(); // 044260552

	public void InitializePatientList() {

		this.patientListComplete.clear();
		Statement stm = null;
		ResultSet rs = null;
		String request = null;

		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = "SELECT nom,prenom FROM malades";
			rs = stm.executeQuery(request);
			while (rs.next()) {

				this.patientListComplete.add(rs.getString(1) + " " + rs.getString(2));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void refreshRedactionByPatientList() {

		this.InitializePatientList();

		TextFields.bindAutoCompletion(this.tfPatientStopJob1, this.patientListComplete);
		TextFields.bindAutoCompletion(this.tfPatientJustification1, this.patientListComplete);
		TextFields.bindAutoCompletion(jfxTPatientRedaction, this.patientListComplete);

	}

	public void InitilizeComboBoxAndHashTableOfTableInfo() {

		Statement stm = null;
		ResultSet rs = null;
		String request = null;

		this.labDetailleCombBoxArray.clear();
		labDetailleHashTable.clear();
		try {
			stm = new SQLiteJDBC().getConnectionProthese().createStatement();
			request = "SELECT * FROM labos";
			rs = stm.executeQuery(request);
			while (rs.next()) {

				labDetailleCombBoxArray.add(rs.getString(2));
				labDetailleHashTable.put(rs.getString(2), rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void InitializArrayTableInfo() {

		Statement stm = null;
		ResultSet rs = null;
		String request = null;
		labDetailleArray.clear();

		try {
			stm = new SQLiteJDBC().getConnectionProthese().createStatement();
			request = "SELECT lb.labos_data_id ldi,lb.date dt,lb.type typo,lb.qte q,lb.prix price,lb.labo_id li "
					+ "FROM labos_data lb,labos l " + "WHERE l.labo_id=lb.labo_id";
			rs = stm.executeQuery(request);
			while (rs.next()) {
				labDetailleArray.add(new LabDetaille(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4),
						rs.getFloat(5), rs.getFloat(5) * rs.getInt(4), rs.getInt(6)));
			}
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

	Hashtable<String, Number> searchUserHashTable;
	Hashtable<String, Number> typeLabHashTable;

	public void remplireHistoriqUsers() {

		historiqUsersArray.clear();

		String request = null;
		Statement stm = null;
		ResultSet rs = null;

		try {
			stm = new SQLiteJDBC().getConnection().createStatement();

			request = "SELECT hu.historic_users_id hid,hu.user_id hui,hu.date_heur dh,hu.action ah,u.nom_fr nu FROM historic_users hu,users u "
					+ "WHERE hui=u.user_id ";
			rs = stm.executeQuery(request);

			while (rs.next()) {
				historiqUsersArray
						.add(new HistoriqUsers(rs.getInt(1), rs.getString(3), rs.getString(5), rs.getString(4)));
			}
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

		// tableHistorique.setItems(FXCollections.observableArrayList(this.historiqUsersArray));

		// tabPatient.setItems(FXCollections.observableArrayList(arrayR));

	}

	@FXML
	void clickOK(KeyEvent event) {

		this.selectionnerPatientDoubleClick(null);
		/*
		 * switch (event.getCode()) { case ENTER: selectedMalade =
		 * tabPatient.getSelectionModel().getSelectedItem(); // ifClicked = false; if
		 * (selectedMalade == null) {
		 * 
		 * JFXDialogLayout layout = new JFXDialogLayout(); layout.setHeading(new
		 * Text("Aucun malade séléctionner")); layout.setBody(new
		 * Text("Il faut séléctionner un malade"));
		 * 
		 * JFXDialog dialog = new JFXDialog(stackPane, layout,
		 * JFXDialog.DialogTransition.CENTER);
		 * 
		 * JFXButton ok = new JFXButton("Fermer"); ok.setOnAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * @Override public void handle(ActionEvent event) { dialog.close();
		 * 
		 * } });
		 * 
		 * layout.setActions(ok); dialog.show(); } else { // redirect au page
		 * consultation Stage primaryStage = new Stage();
		 * primaryStage.initStyle(StageStyle.UNDECORATED); Parent root;
		 * 
		 * try { FicheMaladeController.selectionerMalade(selectedMalade); root =
		 * FXMLLoader.load(getClass().getResource("/application/views/FicheMalade.fxml")
		 * );
		 * 
		 * Scene scene = new Scene(root); scene.setOnKeyPressed(new
		 * EventHandler<KeyEvent>() {
		 * 
		 * @Override public void handle(KeyEvent ke) { // TODO Auto-generated method
		 * stub if (ke.getCode() == KeyCode.ESCAPE) {
		 * FicheMaladeController.setSwitcher(0); primaryStage.close(); } } });
		 * scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
		 * 
		 * @Override public void handle(KeyEvent ke) { // TODO Auto-generated method
		 * stub if (ke.getCode() == KeyCode.ESCAPE) { primaryStage.close(); } } });
		 * 
		 * // scene.getStylesheets().add(getClass().getResource("application.css").
		 * toExternalForm()); primaryStage.setScene(scene); primaryStage.showAndWait();
		 * 
		 * 
		 * refreshList(); } catch (IOException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); } } break;
		 * 
		 * default: break; }
		 */
	}

	@FXML
	void addPatient(MouseEvent event) {
		selectedMalade = tabPatient.getSelectionModel().getSelectedItem();
		if (selectedMalade == null) {
			/*
			 * Notifications notification =
			 * Notifications.create().title("Aucun malade séléctionner")
			 * .text("Il faut séléctionner un malade").graphic(null).hideAfter(Duration.
			 * seconds(2)) .position(Pos.BOTTOM_CENTER).onAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 */
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Aucun malade séléctionner"));
			layout.setBody(new Text("Il faut séléctionner un malade"));

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

		} else {

			ajouterDateAujourdhui();
		}

	}

	@FXML
	void getMaladeFile(MouseEvent event) {
		selectedMalade = tabPatient.getSelectionModel().getSelectedItem();
		if (selectedMalade == null) {
			/*
			 * Notifications notification =
			 * Notifications.create().title("Aucun malade séléctionner")
			 * .text("Il faut séléctionner un malade").graphic(null).hideAfter(Duration.
			 * seconds(2)) .position(Pos.BOTTOM_CENTER).onAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 */
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Aucun malade séléctionner"));
			layout.setBody(new Text("Il faut séléctionner un malade"));

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

		} else {

			Stage primaryStage = new Stage();
			primaryStage.initStyle(StageStyle.UNDECORATED);
			Parent root;

			try {
				FicheMaladeController.selectionerMalade(selectedMalade);
				root = FXMLLoader.load(getClass().getResource("/application/views/FicheMalade.fxml"));

				Scene scene = new Scene(root);

				scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

					@Override
					public void handle(KeyEvent ke) {
						// TODO Auto-generated method stub
						if (ke.getCode() == KeyCode.ESCAPE) {
							FicheMaladeController.setSwitcher(0);
							primaryStage.close();
						}
					}
				});

				// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.showAndWait();
				/*
				 * if (FicheMaladeController.getSwitcher() == 1) {
				 * 
				 * JFXDialogLayout layout = new JFXDialogLayout(); layout.setHeading(new
				 * Text("Succés")); layout.setBody(new
				 * Text("L'affiche a était enregistré avec succés"));
				 * 
				 * JFXDialog dialog = new JFXDialog(stackPane, layout,
				 * JFXDialog.DialogTransition.CENTER);
				 * 
				 * JFXButton ok = new JFXButton("Fermer"); ok.setOnAction(new
				 * EventHandler<ActionEvent>() {
				 * 
				 * @Override public void handle(ActionEvent event) { dialog.close();
				 * 
				 * } });
				 * 
				 * layout.setActions(ok); dialog.show(); }
				 */

				refreshList();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@FXML
	void deletePatient(MouseEvent event) {
		selectedMalade = tabPatient.getSelectionModel().getSelectedItem();
		if (selectedMalade == null) {
			/*
			 * Notifications notification =
			 * Notifications.create().title("Aucun malade séléctionner")
			 * .text("Il faut séléctionner un malade").graphic(null).hideAfter(Duration.
			 * seconds(2)) .position(Pos.BOTTOM_CENTER).onAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 */
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Aucun malade séléctionner"));
			layout.setBody(new Text("Il faut séléctionner un malade"));

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

		} else {
			supprimerMalade();
		}
	}

	protected void supprimerMalade() {
		// TODO Auto-generated method stub
		Malade m = tabPatient.getSelectionModel().getSelectedItem();

		// DataBaseConnection db = new DataBaseConnection();
		if (m != null) {
			try {
				Statement stm = sl.getConnection().createStatement();
				String request = "delete from historic_malades where historic_malade_id='" + m.getHmID() + "'";
				stm.execute(request);

				allMalade.remove(m);
				refreshList();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	protected void ajouterDateAujourdhui() {
		// TODO Auto-generated method stub
		LocalDate myDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		Malade m = tabPatient.getSelectionModel().getSelectedItem();
		boolean ifExistDejaToday = false;
		for (Malade validateMalade : tabPatient.getItems()) {
			if (validateMalade.getDateArriver().equals(myFormat.format(myDate).toString())) {
				if (validateMalade.getId() == m.getId()) {
					ifExistDejaToday = true;
					break;
				}
			}

		}
		if (ifExistDejaToday) {
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Echec"));
			layout.setBody(new Text("Ce Malde existe déja dans la date d'aujourd'hui"));

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

		} else {
			if (m != null) {
				// DataBaseConnection db = new DataBaseConnection();
				Statement stm = null;
				try {

					System.out.println(dateListe.getValue().toString());
					stm = sl.getConnection().createStatement();
					;
					String requette = "insert into historic_malades(malade_id, date_arriver, paye,user_id)" + "Values('"
							+ m.getId() + "','" + myDate.format(myFormat) + "',0,'" + this.userId + "')";
					stm.execute(requette);
					// stm.cancel();
					// ajouter le patient dans la liste d'aujourdhui
					allMalade.add(new Malade(m.getId(), m.getNom(), m.getPrenom(), m.getAdresse(), m.getPhone(),
							m.getSexe(), m.getAge(), m.getProfession(), null, m.getPaye(), myDate.format(myFormat),
							m.getHmID(), m.getUser()));
					dateListe.setValue(myDate);

					refreshList();
					Notifications notification = Notifications.create().title("Ajout de nouveau patient")
							.text("le patient Ã  été enrgistrer").graphic(null).hideAfter(Duration.seconds(2))
							.position(Pos.BOTTOM_CENTER);

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {

					try {
						// rs.close();
						stm.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		//
	}

	private String userTitleText;

	public void setUserTitle(String userTitle) {
		this.userTitleText = userTitle;
	}

	@FXML
	void selectionnerPatientDoubleClick(MouseEvent event) {
		if (event.getButton().equals(MouseButton.PRIMARY)) {
			if (event.getClickCount() == 2) {
				System.out.println("Double clicked");
				selectedMalade = tabPatient.getSelectionModel().getSelectedItem();
				if (selectedMalade == null) {
					/*
					 * Notifications notification =
					 * Notifications.create().title("Aucun malade séléctionner")
					 * .text("Il faut séléctionner un malade").graphic(null).hideAfter(Duration.
					 * seconds(2)) .position(Pos.BOTTOM_CENTER).onAction(new
					 * EventHandler<ActionEvent>() {
					 * 
					 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
					 * System.out.println("notification"); } }); notification.show();
					 */
					JFXDialogLayout layout = new JFXDialogLayout();
					layout.setHeading(new Text("Aucun malade séléctionner"));
					layout.setBody(new Text("Il faut séléctionner un malade"));

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

				} else {
					DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
					LocalDateTime now = LocalDateTime.now();
					if (selectedMalade.getDateArriver().equals(dtf.format(now))) {
						Stage primaryStage = new Stage();
						primaryStage.initStyle(StageStyle.UNDECORATED);
						Parent root;

						try {
							FicheMaladeController.selectionerMalade(selectedMalade);
							root = FXMLLoader.load(getClass().getResource("/application/views/FicheMalade.fxml"));

							Scene scene = new Scene(root);

							// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
							primaryStage.setScene(scene);

							scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

								@Override
								public void handle(KeyEvent ke) {
									// TODO Auto-generated method stub
									if (ke.getCode() == KeyCode.ESCAPE) {
										FicheMaladeController.setSwitcher(0);
										primaryStage.close();
									}
								}
							});

							primaryStage.showAndWait();
							/*
							 * if (FicheMaladeController.getSwitcher() == 1) {
							 * 
							 * JFXDialogLayout layout = new JFXDialogLayout(); layout.setHeading(new
							 * Text("Succés")); layout.setBody(new
							 * Text("L'affiche a était enregistré avec succés"));
							 * 
							 * JFXDialog dialog = new JFXDialog(stackPane, layout,
							 * JFXDialog.DialogTransition.CENTER);
							 * 
							 * JFXButton ok = new JFXButton("Fermer"); ok.setOnAction(new
							 * EventHandler<ActionEvent>() {
							 * 
							 * @Override public void handle(ActionEvent event) { dialog.close();
							 * 
							 * } });
							 * 
							 * layout.setActions(ok); dialog.show(); }
							 */

							refreshList();
						} catch (

						IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					} else {

						JFXDialogLayout layout = new JFXDialogLayout();
						layout.setHeading(new Text("Echec"));
						layout.setBody(new Text("Il faut ajouter ce patient à la date d'aujourdhui"));

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

				}
			}
		} else {
			cm.show(tabPatient, event.getScreenX(), event.getScreenY());
		}

		/*
		 * if (event.getButton() == MouseButton.SECONDARY) {
		 * System.out.println("right"); cm.show(tabPatient, event.getScreenX(),
		 * event.getScreenY()); } if (!ifClicked) { ifClicked = true; lastTime =
		 * System.currentTimeMillis(); selectedMalade =
		 * tabPatient.getSelectionModel().getSelectedItem();
		 * System.out.println(lastTime); } else { if (selectedMalade ==
		 * tabPatient.getSelectionModel().getSelectedItem()) { if
		 * (System.currentTimeMillis() - lastTime < 200.0) {
		 * 
		 * // ifClicked = false; // redirect au page consultation Stage primaryStage =
		 * new Stage(); primaryStage.initStyle(StageStyle.UNDECORATED); Parent root;
		 * 
		 * try { FicheMaladeController.selectionerMalade(selectedMalade); root =
		 * FXMLLoader.load(getClass().getResource("../views/FicheMalade.fxml"));
		 * 
		 * Scene scene = new Scene(root);
		 * 
		 * // scene.getStylesheets().add(getClass().getResource("application.css").
		 * toExternalForm()); primaryStage.setScene(scene); primaryStage.show(); } catch
		 * (IOException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * } else { lastTime = System.currentTimeMillis(); } } else { lastTime =
		 * System.currentTimeMillis(); } }
		 */
	}

	private ObservableList<Malade> fillTable() {
//pour la consultation 

		ObservableList<String> data = FXCollections.observableArrayList("Masculin", "Féminin");

		// DataBaseConnection db = new DataBaseConnection();
		ArrayList<Malade> al = new ArrayList<Malade>();

		Statement stm = null;
		ResultSet rs = null;
		try {
			stm = sl.getConnection().createStatement();
			String request = "select hm.historic_malade_id hm_id, m.malade_id malade_id1,m.nom nom1,m.prenom prenom1"
					+ " , m.age age1, m.sexe sexe1, m.adresse adresse1, m.telephone telephone1,"
					+ " m.fonction fonction1,hm.date_arriver date_arriver1,hm.paye paye1,hm.user_id user"
					+ " from malades m, historic_malades hm" + " WHERE hm.malade_id = m.malade_id ";

			rs = stm.executeQuery(request);

			while (rs.next()) {
				ComboBox<String> sexe = new ComboBox<String>();
				sexe.setItems(data);
				al.add(new Malade(rs.getInt("malade_id1"), rs.getString("nom1"), rs.getString("prenom1"),
						rs.getString("adresse1"), rs.getString("telephone1"), rs.getString("sexe1"),
						Integer.parseInt(rs.getString("age1")), rs.getString("fonction1"), sexe,
						Float.parseFloat(rs.getString("paye1")), rs.getString("date_arriver1"), rs.getInt("hm_id"),
						rs.getInt("user")));

			}

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

		return FXCollections.observableArrayList(al);
	}

	@FXML
	public void malade(MouseEvent event) {
		malade.setVisible(true);
		historique.setVisible(false);

		prothese1.setVisible(false);
		redaction.setVisible(false);
		parameter.setVisible(false);
		depense.setVisible(false);

		/*
		 * Scene scene=maladeView.getScene();
		 * malade.translateYProperty().set(scene.getHeight());
		 * 
		 * Timeline tl=new Timeline(); KeyValue kv=new
		 * KeyValue(malade.translateYProperty(), 0, Interpolator.EASE_IN); KeyFrame
		 * kf=new KeyFrame(Duration.seconds(1), kv); tl.getKeyFrames().add(kf);
		 * tl.setOnFinished(event1 ->{ parentStackPane.getChildren().remove(malade); });
		 * tl.play();
		 */
	}

	@FXML
	private AnchorPane parameter;

	@FXML
	public void afficherHistorique(MouseEvent event) {

		// historique.setVisible(true);
		parameter.setVisible(true);
		malade.setVisible(false);
		prothese1.setVisible(false);
		redaction.setVisible(false);
		statistique.setVisible(false);
		agenda.setVisible(false);
		depense.setVisible(false);

	}

	@FXML
	public void afficherAgenda(MouseEvent event) {

		// historique.setVisible(true);
		agenda.setVisible(true);
		parameter.setVisible(false);
		malade.setVisible(false);
		prothese1.setVisible(false);
		redaction.setVisible(false);
		statistique.setVisible(false);
		depense.setVisible(false);

	}

	@FXML
	private ImageView redactionView2;

	private Malade openRedactionFromFicheMalade = null;

	public void setOpenRedactionFromFicheMalade(Malade openRedactionFromFicheMalade) {
		this.openRedactionFromFicheMalade = openRedactionFromFicheMalade;
	}

	@FXML
	public void afficherRedaction(MouseEvent event) {

		if (openRedactionFromFicheMalade != null) {
			this.jfxTPatientRedaction
					.setText(openRedactionFromFicheMalade.getNom() + " " + openRedactionFromFicheMalade.getPrenom());
		}

		this.showRedaction(null);
		/*
		 * redaction.setVisible(true); malade.setVisible(false);
		 * prothese1.setVisible(false); historique.setVisible(false);
		 * statistique.setVisible(false); parameter.setVisible(false);
		 * depense.setVisible(false);
		 */
		/*
		 * Scene scene=redactionView2.getScene();
		 * redaction.translateYProperty().set(scene.getHeight());
		 * 
		 * Timeline tl=new Timeline(); KeyValue kv=new
		 * KeyValue(malade.translateYProperty(), 0, Interpolator.EASE_IN); KeyFrame
		 * kf=new KeyFrame(Duration.seconds(1), kv);
		 */
		// tl.getKeyFrames().add(kf);
		/*
		 * tl.setOnFinished(event1 ->{ parentStackPane.getChildren().remove(redaction);
		 * });
		 */
		/*
		 * tl.play();
		 */

	}

	@FXML
	void activerDesactiverFilter(ActionEvent event) {

		if (activeFilter.isSelected()) {
			System.out.println("selected");
			dateSearch.setDisable(true);
			userSearch.setDisable(true);
			actionSearch.setDisable(true);
			this.tableHistorique.setItems(FXCollections.observableArrayList(this.historiqUsersArray));
		} else {
			System.out.println("Unselected");
			dateSearch.setDisable(false);
			userSearch.setDisable(false);
			actionSearch.setDisable(false);
		}
	}

	@FXML
	public void afficherProthese(MouseEvent event) {
		prothese1.setVisible(true);
		historique.setVisible(false);
		malade.setVisible(false);
		redaction.setVisible(false);
		statistique.setVisible(false);
		depense.setVisible(true);

	}

	// attribute uses for define the dialoge

	@FXML
	public void ajouterMalade(MouseEvent event) {
		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		AddPatientController controller = new AddPatientController(this.userId);
		FXMLLoader root;
		Parent parent;

		try {
			root = new FXMLLoader(getClass().getResource("/application/views/addPatient.fxml"));
			System.out.println("atik");
			root.setController(controller);
			parent = root.load();
			Scene scene = new Scene(parent);

			// Add listner for close the window

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					if (ke.getCode() == KeyCode.ESCAPE) {
						primaryStage.close();
					}

					if (new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_ANY).match(ke)) {
						System.out.println("Work for me");
						controller.setSelectCheckBoxMale(null);
					}
					if (new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_ANY).match(ke)) {
						System.out.println("Work for me");
						controller.setSelectCheckBoxFemale(null);
					}
				}
			});
			///////////////// End//////////////

//application.controllers.AddPatientController
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.showAndWait();
			// System.out.println(controller.getSwitcher());
			// primaryStage.show
			// System.out.println("atikffjjjjjjjjjjffjjfjff");
			if (controller.getSwitcher() != -1) {

				if (Main.ifStillFree) {
					if (controller.numbPatient()) {

						Main.setIfStillFree(false);

						Stage stage = (Stage) homeAnchor.getScene().getWindow();
						stage.close();
						return;

					}
				}
				refreshList();
				remplireHistoriqUsers();
				this.refreshRedactionByPatientList();

				// open patinet fiche directly
				this.openPatientListDirectly(controller.getSwitcher());

			}
			// primaryStage.show();
		} catch (IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void openPatientListDirectly(int id) {

		Malade m1 = null;

		for (Malade malade : this.fillTable()) {
			if (malade.getId() == id) {
				m1 = malade;
				break;
			}
		}

		// redirect au page consultation
		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		Parent root;

		try {
			FicheMaladeController.selectionerMalade(m1);
			root = FXMLLoader.load(getClass().getResource("/application/views/FicheMalade.fxml"));

			Scene scene = new Scene(root);
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent ke) {
					// TODO Auto-generated method stub
					if (ke.getCode() == KeyCode.ESCAPE) {
						FicheMaladeController.setSwitcher(0);
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

			primaryStage.setScene(scene);
			primaryStage.showAndWait();

			refreshList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@FXML
	private Label tfTotale;

	private void refreshList() {
		// TODO Auto-generated method stub

		// il faut changer cette méthode de refresh car il faut ajouter seulement le
		// nouveau malade
		allMalade = fillTable();
		float sumPaye = 0;
		ObservableList<Malade> arrayRecherche = allMalade;
		ArrayList<Malade> arrayR = new ArrayList<Malade>();

		String dateSelected = dateListe.getValue().toString();

		// 00 ==> 11
		String[] userSplit = this.comboBoxSelectUser.getValue().split("_");
		int userIdAfterParse = Integer.parseInt(userSplit[0]);
		for (Malade malade : arrayRecherche) {
			if (malade.getDateArriver().equals(dateSelected) && malade.getUser() == userIdAfterParse) {
				arrayR.add(malade);
				System.out.println("true");
				sumPaye += malade.getPaye();
			}
			if (!this.hashTableDetectAgePatient.containsKey(malade.getNom() + " " + malade.getPrenom())) {
				this.hashTableDetectAgePatient.put(malade.getNom() + " " + malade.getPrenom(), malade.getAge());
				this.hashTableDetectIDPatient.put(malade.getNom() + " " + malade.getPrenom(), malade.getAge());

			}
		}
		tabPatient.setItems(FXCollections.observableArrayList(arrayR));
		tfTotale.setText("TOTALE:  " + sumPaye);
		// ObservableList<Malade> list2=FXCollections.observableArrayList(new
		// Malade("ati", "yassine", "cctiti", "25555", "homme", 25));
		// tabPatient.setItems(allMalade);
	}

	@FXML
	void ajouterNouvelleConsultation(MouseEvent event) {
		Malade malade = tabPatient.getSelectionModel().getSelectedItem();
		Stage primaryStage = new Stage();
		Parent root;

		try {
			FicheMaladeController.selectionerMalade(malade);
			root = FXMLLoader.load(getClass().getResource("/application/views/FicheMalade.fxml"));

			Scene scene = new Scene(root);

			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.showAndWait();
			/*
			 * if (FicheMaladeController.getSwitcher() == 1) {
			 * 
			 * JFXDialogLayout layout = new JFXDialogLayout(); layout.setHeading(new
			 * Text("Succés")); layout.setBody(new
			 * Text("L'affiche a était enregistré avec succés"));
			 * 
			 * JFXDialog dialog = new JFXDialog(stackPane, layout,
			 * JFXDialog.DialogTransition.CENTER);
			 * 
			 * JFXButton ok = new JFXButton("Fermer"); ok.setOnAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * @Override public void handle(ActionEvent event) { dialog.close();
			 * 
			 * } });
			 * 
			 * layout.setActions(ok); dialog.show(); }
			 */
			refreshList();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void rechercher(KeyEvent event) {

		// initialiser la liste au début par tout les malades
		ObservableList<Malade> arrayRecherche = allMalade;
		ArrayList<Malade> arrayR = new ArrayList<Malade>();

		// System.out.println(arrayRecherche.get(1).getNom());
		// séléctionner depuis la date d'aujourdhui
		if (tRecherche.getText().equals("")) {
			String dateSelected = dateListe.getValue().toString();

			for (Malade malade : arrayRecherche) {
				if (malade.getDateArriver().equals(dateSelected)) {
					arrayR.add(malade);
					System.out.println("true");
				}
			}
			tabPatient.setItems(FXCollections.observableArrayList(arrayR));

			// il fait la recherche
		} else {
			for (Malade malade : arrayRecherche) {
				if (malade.getNom().toUpperCase().contains(tRecherche.getText().toUpperCase())
						|| malade.getPrenom().toUpperCase().contains(tRecherche.getText().toUpperCase())) {
					arrayR.add(malade);
				}
			}
			tabPatient.setItems(FXCollections.observableArrayList(arrayR));
		}

	}

	@FXML
	void fermer(MouseEvent event) {

		String request = null;
		Statement stm = null;

		LocalDate myDate = LocalDate.now();
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		request = "INSERT INTO historic_users(user_id,date_heur,action) " + "VALUES('" + this.userId + "', '"
				+ myDate.format(myFormat) + "','Déconnexion')";
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			stm.execute(request);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		Stage stage = (Stage) closeButton.getScene().getWindow();
		stage.close();

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		Parent root;

		try {

			root = FXMLLoader.load(getClass().getResource("../views/login/Login.fxml"));

			Scene scene = new Scene(root);
			scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// renitialser le tableau
	public void addPost() {
		System.out.println("addPost");
//		 allMalade.add(mal);
//		 tabPatient.setItems(allMalade);
		allMalade.removeAll(allMalade);

	}

	@FXML
	void slelectionnerDate(ActionEvent event) {
		ObservableList<Malade> arrayRecherche = allMalade;
		ArrayList<Malade> arrayR = new ArrayList<Malade>();

		String dateSelected = dateListe.getValue().toString();
		dateListeString = dateSelected;
		float sumPaye = 0;
		String[] userSplit = this.comboBoxSelectUser.getValue().split("_");
		int userIdAfterParse = Integer.parseInt(userSplit[0]);
		for (Malade malade : arrayRecherche) {
			System.out.println("From select date Malade: " + malade.getUser() + " This user: " + userIdAfterParse);
			if (malade.getDateArriver().equals(dateSelected) && malade.getUser() == userIdAfterParse) {
				malade.initialiseActe();
				arrayR.add(malade);
				System.out.println("true");
				sumPaye += malade.getPaye();
			}
		}
		tabPatient.setItems(FXCollections.observableArrayList(arrayR));
		tfTotale.setText("TOTALE:  " + sumPaye);
	}

	@FXML
	void selectedDateHistoriqueUsers(ActionEvent event) {

		System.out.println("fffffffffffffffffffffffffffffffffffffffffffffffffffff");
		this.dateHistory = dateSearch.getValue().toString();
		System.out.println("action " + actionSearch.getSelectionModel().getSelectedItem());

		refreshListHistoriqueUser(this.dateHistory, this.userHistory, this.actionHistory);

		// System.out.println(dateFiltre);
	}

	@FXML
	void selectedUserHistoriqueUsers(ActionEvent event) {

		this.userHistory = userSearch.getSelectionModel().getSelectedItem();
		refreshListHistoriqueUser(this.dateHistory, this.userHistory, this.actionHistory);

	}

	@FXML
	void selectedActionHistoriqueUsers(ActionEvent event) {
		this.actionHistory = actionSearch.getSelectionModel().getSelectedItem();
		refreshListHistoriqueUser(this.dateHistory, this.userHistory, this.actionHistory);

	}

	private String dateHistory = "", actionHistory = "", userHistory = "";

	void refreshListHistoriqueUser(String date, String user, String action) {
		ArrayList<HistoriqUsers> arrayH = new ArrayList<HistoriqUsers>();

		for (HistoriqUsers historiqUsers : historiqUsersArray) {
			if (historiqUsers.getDate().contains(date) && historiqUsers.getAction().contains(action)
					&& historiqUsers.getUser().contains(user)) {

				arrayH.add(historiqUsers);

			}

		}

		tableHistorique.setItems(FXCollections.observableArrayList(arrayH));

	}

	// Historique Anchore pane
	@FXML
	private DatePicker dateSearch;

	@FXML
	private ComboBox<String> userSearch;

	@FXML
	private ComboBox<String> actionSearch;

	@FXML
	private CheckBox activeFilter;

	@FXML
	private TableView<HistoriqUsers> tableHistorique;

	// Prothese Anchore pane
	@FXML
	public void addProthese(MouseEvent event) {

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		AddLaboController controller = new AddLaboController();
		FXMLLoader root;
		Parent parent;

		try {
			root = new FXMLLoader(getClass().getResource("/application/views/prothese/AddLabo.fxml"));
			System.out.println("atik");
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
			// application.controllers.AddPatientController
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.showAndWait();
			// InitilizeComboBoxAndHashTableOfTableInfo();
			// this.cbLab.add

			if (controller.getSwitchController() == 1) {
				/*
				 * Notifications notification =
				 * Notifications.create().title("Ajout de nouveau labo")
				 * .text("le labo Ã  été enrgistrer").graphic(null).hideAfter(Duration.seconds(
				 * 2) ) .position(Pos.BOTTOM_CENTER).onAction(new EventHandler<ActionEvent>() {
				 * 
				 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
				 * System.out.println("notification"); } }); notification.show();
				 */
				JFXDialogLayout layout = new JFXDialogLayout();
				layout.setHeading(new Text("Ajout de nouveau labo"));
				layout.setBody(new Text("Le labo a été enrgistrer avec succés"));

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

				this.InitilizeComboBoxAndHashTableOfTableInfo();
				this.cbLab.setItems(FXCollections.observableArrayList(this.labDetailleCombBoxArray));
				// afficherNotifications("Ajout d'un labo", "L'ajout de labo est fait avec
				// success");

			}
		} catch (Exception e) {

		}

	}

	@FXML
	void ChangePositionPrixUnitaire(KeyEvent event) {

		switch (event.getCode()) {
		case LEFT:
			this.tQte.requestFocus();
			break;

		case ENTER:
			this.addInLabDetailleTable();
			break;
		}
	}

	private ArrayList<LabDetaille> reduceLabDetailleArray = new ArrayList<LabDetaille>();

	@FXML
	private void changeLaboSelected(ActionEvent event) {

		reduceLabDetailleArray.clear();// = new ArrayList<LabDetaille>();//reduceLabDetailleArray= new
										// ArrayList<LabDetaille>();
		// System.out.println(reduceLabDetailleArray.get(0));
		String selectedLab = this.cbLab.getSelectionModel().getSelectedItem();
		this.orderOfLabDetaille = 1;
		for (LabDetaille labDetaille : this.labDetailleArray) {
			if (labDetaille.getLabId() == (int) labDetailleHashTable.get(selectedLab)) {
				this.reduceLabDetailleArray.add(new LabDetaille(labDetaille.getLabDetailleId(), labDetaille.getDate(),
						labDetaille.getType(), labDetaille.getQte(), labDetaille.getPrixUnitaire(),
						labDetaille.getPrixGlobal(), labDetaille.getLabId(), this.orderOfLabDetaille));
				System.out.println("order Lab Detaille ++" + this.orderOfLabDetaille);
				this.orderOfLabDetaille += 1;

			}
		}

		// this.tableLabInfo.setItems(FXCollections.observableArrayList(this.reduceLabDetailleArray));

		// initaialize the labeHashTable
		typeLabHashTable = new Hashtable<String, Number>();
		// TextFields tfs=TextFields.createClearableTextField();
		// if(settypeLabHashTable()!=null) {
		// tType=TextFields.createClearableTextField();

		// TextFields.bindAutoCompletion(tType,getSkinnable().getSuggestionProvider()).dispose();
		this.settypeLabHashTable();

		/*
		 * if (k != 0) { // auto=TextFields.bindAutoCompletion(tType,this.typePriceBise
		 * ); auto.dispose(); } else { k++; }
		 */
		// auto = TextFields.bindAutoCompletion(tType, this.typePriceBise);
		// tType.dis

	}

	private int k = 0;
	AutoCompletionBinding auto;

	@FXML
	void changePositionQte(KeyEvent event) {

		switch (event.getCode()) {
		case RIGHT:
			this.tPrixUnitaire.requestFocus();

			break;
		case LEFT:
			this.tType.requestFocus();
			break;

		case ENTER:
			this.addInLabDetailleTable();
			break;

		}

	}

	public void addInLabDetailleTable() {

		Statement stm = null;
		String request = null;
		String content = "Il faut séléctioner un labo";
		try {
			if (this.cbLab.getSelectionModel().getSelectedItem() == null) {
				/*
				 * Notifications notification =
				 * Notifications.create().title("Echec").text(content).graphic(null)
				 * .hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_CENTER) .onAction(new
				 * EventHandler<ActionEvent>() {
				 * 
				 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
				 * System.out.println("notification"); } }); notification.show();
				 */
				JFXDialogLayout layout = new JFXDialogLayout();
				layout.setHeading(new Text("Echec"));
				layout.setBody(new Text(content));

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

			} else {
				LocalDateTime myDate = LocalDateTime.now();
				DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
				if (this.tPrixUnitaire.getText().equals("") || this.tQte.getText().equals("")) {
					content = "La quantité et le devis doivent Ãªtre des numéros.";
					/*
					 * Notifications notification =
					 * Notifications.create().title("Echec").text(content).graphic(null)
					 * .hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_CENTER) .onAction(new
					 * EventHandler<ActionEvent>() {
					 * 
					 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
					 * System.out.println("notification"); } }); notification.show();
					 */
					JFXDialogLayout layout = new JFXDialogLayout();
					layout.setHeading(new Text("Echec"));
					layout.setBody(new Text(content));

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

				} else {
					stm = new SQLiteJDBC().getConnectionProthese().createStatement();
					request = "INSERT INTO labos_data(type,prix,date,qte,labo_id) " + "VALUES('" + this.tType.getText()
							+ "','" + Float.parseFloat(this.tPrixUnitaire.getText()) + "' " + ",'"
							+ myDate.format(myFormat) + "', '" + Integer.parseInt(this.tQte.getText()) + "' ,'"
							+ this.labDetailleHashTable.get(this.cbLab.getSelectionModel().getSelectedItem()) + "');";

					stm.execute(request);
					this.tType.setText("");
					this.tPrixUnitaire.setText("");
					this.tQte.setText("");
					InitializArrayTableInfo();
					this.changeLaboSelected(null);

				}

			}
		} catch (

		SQLException e) {
			// TODO Auto-generated catch block
			// e.printStackTrace();
		}

	}

	@FXML
	void removeInLabo(MouseEvent event) {
		Statement stm = null;
		String request = null;
		LabDetaille labDetaille = this.tableLabInfo.getSelectionModel().getSelectedItem();

		try {
			stm = new SQLiteJDBC().getConnectionProthese().createStatement();
			request = "DELETE FROM labos_data WHERE labos_data_id='" + labDetaille.getLabDetailleId() + "'";
			stm.execute(request);
			InitializArrayTableInfo();
			this.changeLaboSelected(null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void changePositionType(KeyEvent event) {
		switch (event.getCode()) {
		case RIGHT:
			this.tQte.requestFocus();
			break;
		}
	}

	@FXML
	void facturer(MouseEvent event) throws DocumentException, IOException {
		String dateFin = dateFinLab.getValue().toString();
		String dateDebut = dateDebutLab.getValue().toString();
		if ((dateFin.compareTo(dateDebut)) >= 0) {
			this.reduceLabDetailleArray.clear();

			// get the id of lab selected
			int labID = (int) labDetailleHashTable.get(cbLab.getSelectionModel().getSelectedItem());

			for (LabDetaille labDetaille : labDetailleArray) {
				if (labDetaille.getDate().compareTo(dateDebut) >= 0 && labDetaille.getDate().compareTo(dateFin) <= 0
						&& labDetaille.getLabId() == labID) {
					this.reduceLabDetailleArray.add(labDetaille);
				}
			}
			this.tableLabInfo.setItems(FXCollections.observableArrayList(this.reduceLabDetailleArray));

			LocalDateTime myDate = LocalDateTime.now();
			// DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// File theDir = new File("C:\\Users\\LTO\\Desktop\\lib jar\\FARES");
			String tableTheDir[] = { "assets\\sqlite\\data\\facturation\\" + myDate.getYear(),
					"assets\\sqlite\\data\\facturation\\" + myDate.getYear() + "\\" + myDate.getMonthValue(),
					"assets\\sqlite\\data\\facturation\\" + myDate.getYear() + "\\" + myDate.getMonthValue() + "\\"
							+ myDate.getDayOfMonth() };

			// if the directory does not exist, create it
			// ********************begins of creation directories***************/
			for (String string : tableTheDir) {
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
			}
			// ##################"end of creating of directories##############

			// *******************begins of creating the PDF Document***********/

			String minuteSecondMiliSecond = myDate.getHour() + "" + myDate.getMinute() + "" + myDate.getSecond() + ""
					+ myDate.getNano();

			String file_name = "C:\\sqlite\\data\\facturation\\" + myDate.getYear() + "\\" + myDate.getMonthValue()
					+ "\\" + myDate.getDayOfMonth() + "\\" + minuteSecondMiliSecond + ".pdf";
			Document document = new Document();

			PdfWriter.getInstance(document, new FileOutputStream(file_name));

			document.open();
			/*
			 * Paragraph para1 = new Paragraph(
			 * "Ù�Ø§ØªÙ€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€ÙˆØ±Ø© Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù‡Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ø§ØªÙ€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù�Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€"
			 * );
			 * 
			 * document.add(para1);
			 * 
			 */

			Paragraph preface = new Paragraph();
			// We add one empty line ===> add jump
			// preface.setAlignment(Element.ALIGN_RIGHT);
			// preface.setAlignment(Element.ALIGN_CENTER);
			addEmptyLine(preface, 1);
			// Lets write a big header
			Paragraph p = new Paragraph("Facture", headerFont);
			p.setAlignment(Element.ALIGN_CENTER);
			Paragraph pNameLab = new Paragraph(cbLab.getSelectionModel().getSelectedItem()
					+ "                                                                                                   "
					+ dateDebut + "  jusqu'au  " + dateFin, redFont);
			Paragraph pNumBill = new Paragraph("facture N:09542", redFont);
			// Paragraph pDate = new Paragraph(dateDebut+" jusqu'au "+dateFin, redFont);

			p.setAlignment(Element.ALIGN_CENTER);

			pNameLab.setAlignment(Element.ALIGN_LEFT);
			pNumBill.setAlignment(Element.ALIGN_LEFT);
			// pDate.setAlignment(Element.ALIGN_RIGHT);
			Paragraph pPrincipale = new Paragraph();

			pPrincipale.add(pNameLab);
			pPrincipale.add(pNumBill);
			// pPrincipale.add(pDate);
			pPrincipale.setAlignment(Element.ALIGN_JUSTIFIED_ALL);

			// preface.add(pNameLab);

			//
			preface.add(pPrincipale);
			// preface.add(pNumBill);

			preface.add(p);
			// preface.add(pDate);
			// preface.addAll({pNameLab,pNumBill,p,pDate})
			// preface.add(p);

//Paragraph p2=new Paragraph( BaseFont.IDENTITY_H,"Ù�Ù€Ù€Ù€Ø§ØªÙ€Ù€Ù€Ù€Ù€Ù€ÙˆØ±", catFont);
			addEmptyLine(preface, 3);

			// addEmptyLine(preface, 1);

			PdfPTable table = new PdfPTable(5);
			PdfPCell cell1 = new PdfPCell(new Phrase("Date"));
			PdfPCell cell2 = new PdfPCell(new Phrase("Type"));
			PdfPCell cell3 = new PdfPCell(new Phrase("Prix_Unitaire"));
			PdfPCell cell4 = new PdfPCell(new Phrase("Quantité"));
			PdfPCell cell5 = new PdfPCell(new Phrase("Prix_Globale"));

			table.addCell(cell1);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.setHeaderRows(1);

			for (LabDetaille labDetaille : this.reduceLabDetailleArray) {

				if (labDetaille.getLabId() == labID && labDetaille.getDate().compareTo(dateDebut) >= 0
						&& labDetaille.getDate().compareTo(dateFin) <= 0) {

					table.addCell(labDetaille.getType());
					table.addCell(labDetaille.getPrixUnitaire() + "");
					table.addCell(labDetaille.getQte() + "");
					table.addCell(labDetaille.getPrixGlobal() + "");
				}
			}

			document.add(preface);
			document.add(table);
//413
			/*
			 * PdfPTable table = new PdfPTable(5); PdfPCell cell1 = new PdfPCell(new
			 * Phrase("Ø§Ù„Ø²Ù…Ø§Ù†")); PdfPCell cell2 = new PdfPCell(new
			 * Phrase("Ø§Ù„Ù†ÙˆØ¹")); PdfPCell cell3 = new PdfPCell(new
			 * Phrase("Ù…Ø¨Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù„Øº Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€ÙˆØ­Ù€Ù€Ù€Ù€Ù€Ø¯Ø©"));
			 * PdfPCell cell4 = new PdfPCell(new Phrase("Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€ÙƒÙ€Ù€Ù…ÙŠØ©"));
			 * PdfPCell cell5 = new PdfPCell(new
			 * Phrase("Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù…Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ø¨Ù€Ù€Ù€Ù€Ù€Ù„Øº Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€Ù€ÙƒÙ€Ù€Ù€Ù€Ù„Ù€Ù€Ù€Ù€Ù€ÙŠ"
			 * )); table.addCell(cell1); table.addCell(cell2); table.addCell(cell3);
			 * table.addCell(cell4); table.addCell(cell5); table.setHeaderRows(1); for
			 * (LabDetaille labDetaille : this.reduceLabDetailleArray) {
			 * table.addCell(labDetaille.getDate()); table.addCell(labDetaille.getType());
			 * table.addCell(labDetaille.getPrixUnitaire() + "");
			 * table.addCell(labDetaille.getQte() + "");
			 * table.addCell(labDetaille.getPrixGlobal() + ""); }
			 * 
			 * document.add(table);
			 */
			document.close();
			// System.out.println()

			// **********************Begin open the PDF
			// File*********************************
			File myFile = new File(file_name);
			Desktop.getDesktop().open(myFile);
			// ######################End of the open PDF
			// File#################################

			System.out.println("Finished");

			// ###################End of creating the PDF Document################
		} else {
			String content = "La date de début est supérieur de la date de fin!";
			/*
			 * Notifications notification =
			 * Notifications.create().title("Echec").text(content).graphic(null)
			 * .hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_CENTER) .onAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 */
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Echec"));
			layout.setBody(new Text(content));

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
	}

	@FXML
	void addInLabo(MouseEvent event) {

		this.addInLabDetailleTable();
		System.out.println("Add in labo");
	}

	public void afficherNotifications(String title, String content) {
		/*
		 * Notifications notification =
		 * Notifications.create().title(title).text(content).graphic(null)
		 * .hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_CENTER).onAction(new
		 * EventHandler<ActionEvent>() {
		 * 
		 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
		 * System.out.println("notification"); } }); notification.show();
		 */
		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Text(title));
		layout.setBody(new Text(content));

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

	// Redaction Exercise
	@FXML
	private VBox vBoxPrincipale;

	// font

	Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
	Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	Font ordinaryFont = new Font(Font.FontFamily.TIMES_ROMAN, 14);

	Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	// Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	/**************************
	 * Begin stopingJob
	 ***********************************/
	@FXML
	private JFXTextField tfPatientStopJob1;

	@FXML
	private JFXTextArea taCauses1;
	@FXML
	private JFXTextField tfPeriodePatientJob1;

	@FXML
	private Text tDateStopingJob;

	@FXML
	private TextArea jftTAFreeRedaction;

	@FXML
	private JFXTextField tfTitleFreeRedaction;

	@FXML
	void printFreeRedaction(ActionEvent event) throws DocumentException, IOException {

		BaseFont ArialBase = BaseFont.createFont("C:\\Windows\\Fonts\\arialbd.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font ArialFont = new Font(ArialBase, 15, Font.BOLD);
		Font ArialFontOrdinary = new Font(ArialBase, 14);

		/**********************
		 * begin generating the folders
		 ************************************/
		LocalDateTime myDate = LocalDateTime.now();
		// DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// File theDir = new File("C:\\Users\\LTO\\Desktop\\lib jar\\FARES");
		String tableTheDir[] = { "assets\\sqlite\\data\\arretTravail\\" + myDate.getYear(),
				"assets\\sqlite\\data\\arretTravail\\" + myDate.getYear() + "\\" + myDate.getMonthValue(),
				"assets\\sqlite\\data\\arretTravail\\" + myDate.getYear() + "\\" + myDate.getMonthValue() + "\\"
						+ myDate.getDayOfMonth() };

		// if the directory does not exist, create it
		// ********************begins of creation directories***************/
		for (String string : tableTheDir) {
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
		}
		// ##################"end of creating of directories##############

		// *******************begins of creating the PDF Document***********/

		String minuteSecondMiliSecond = myDate.getHour() + "" + myDate.getMinute() + "" + myDate.getSecond() + ""
				+ myDate.getNano();

		String file_name = "assets\\sqlite\\data\\arretTravail\\" + myDate.getYear() + "\\" + myDate.getMonthValue()
				+ "\\" + myDate.getDayOfMonth() + "\\" + minuteSecondMiliSecond + ".pdf";

		/**********************
		 * End generating the folders
		 ***************************************/

		String patientStopJob = this.tfPatientStopJob1.getText();
		String causes = this.taCauses1.getText();
		String periodePatientStopJob = this.tfPeriodePatientJob1.getText();

		/****************************
		 * Begin generating PDF Arret_de_travail
		 *************************/
		// String file_name = "C:\\Users\\LTO\\Desktop\\lib jar\\arretDeTravail.pdf";
		// create a new pdf document
		Document document = new Document();

		PdfWriter.getInstance(document, new FileOutputStream(file_name));
//open the pdf document
		document.open();

		Paragraph preface = new Paragraph();
		// We add one empty line ===> add jump
		// preface.setAlignment(Element.ALIGN_RIGHT);
		// preface.setAlignment(Element.ALIGN_CENTER);
		// addEmptyLine(preface, 1);
		// Lets write a big header8877
		Paragraph p = new Paragraph();
		p.setFont(headerFont);
		p.add(new Chunk(this.tfTitleFreeRedaction.getText()).setUnderline(0.9f, -2f));
		p.setAlignment(Element.ALIGN_CENTER);

		// String name1 = new String("Ø£Ø­Ù…Ø¯", "UTF-8");
		// Paragraph nameP = new Paragraph(name1, catFont);

		Paragraph cabinet = new Paragraph();
		cabinet.setFont(catFont);
		cabinet.add(new Chunk("CABINET DENTAIRE").setUnderline(0.9f, -2f));

		// Paragraph pDoctor = new Paragraph("Dr "+this.nameL.getText());
		// Paragraph jobFR = new Paragraph(this.specialityL.getText());
		// declare the table with 3 column
		PdfPTable pdfCell = new PdfPTable(3);
		pdfCell.setWidthPercentage(100);

		// first row

		PdfPCell cellNL = new PdfPCell(new Paragraph(this.nameL.getText(), subFont));
		// PdfPCell cellPhoto= new PdfPCell(new Paragraph("photo photo
		// photo",headerFont));
		PdfPCell cellNAR = new PdfPCell(new Paragraph("Ø§Ù„Ø­ÙƒÙŠÙ…Ù€(Ø©) " + this.nameAR.getText(), ArialFont));

		// seconde row
		PdfPCell cellSpL = new PdfPCell(new Paragraph(this.specialityL.getText(), ordinaryFont));
		PdfPCell cellSpAR = new PdfPCell(new Paragraph(this.specialityAR.getText(), ArialFontOrdinary));

		cellNAR.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		// cellNAR.ma
		// cellNAR.setBorder(Rectangle.NO_BORDER);

		cellSpAR.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
		// cellSpAR.setBorder(Rectangle.NO_BORDER);
		cellNAR.setHorizontalAlignment(Element.ALIGN_RIGHT);
		cellSpAR.setHorizontalAlignment(Element.ALIGN_RIGHT);

		Paragraph address = new Paragraph(
				this.address.getText() + "," + this.municipality.getText() + "-W." + this.town.getValue(),
				ordinaryFont);
		Paragraph phone = new Paragraph("Tél: " + this.phone.getText(), ordinaryFont);
		// Paragraph firstLastName = new Paragraph("Nom & Prénom: " +
		// this.tfPatientStopJob1.getText(), ordinaryFont);

		// Paragraph agePara = new Paragraph("Age: " +
		// this.hashTableDetectAgePatient.get(tfPatientStopJob1.getText()),
		// ordinaryFont);
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-YYYY");

		Paragraph dateOrder = new Paragraph(this.municipality.getText() + ", le: " + myDate.format(myFormat),
				ordinaryFont);

		/*
		 * cabinet.setAlignment(Element.ALIGN_LEFT); //
		 * pDoctor.setAlignment(Element.ALIGN_LEFT); //
		 * jobFR.setAlignment(Element.ALIGN_LEFT);
		 * address.setAlignment(Element.ALIGN_LEFT);
		 * phone.setAlignment(Element.ALIGN_LEFT);
		 * firstLastName.setAlignment(Element.ALIGN_LEFT);
		 */

		String IMG = "assets//teeth2.jpg";
		// first row
		// cellNL.setPaddingLeft(50f);
		cellNL.setBorder(Rectangle.NO_BORDER);
		cellNL.setPaddingTop(25f);
		pdfCell.addCell(cellNL);
		PdfPCell cellNull = new PdfPCell(new Paragraph(""));
		cellNull.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(cellNull);
		cellNAR.setPaddingLeft(30f);
		cellNAR.setPaddingTop(25f);
		cellNAR.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(cellNAR);
		// table.addCell(labDetaille.getDate());
		// seconde row
		cellSpL.setPaddingLeft(15f);
		// cellSpL.setPaddingTop(30f);
		cellSpL.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(cellSpL);

		Image img = Image.getInstance(IMG);
		img.scaleAbsolute(100f, 100f);
		// img.setWidthPercentage(25);
		PdfPCell imgCell = new PdfPCell(img);
		imgCell.setPaddingLeft(30f);
		imgCell.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(imgCell);
		cellSpAR.setPaddingLeft(50f);
		// cellSpAR.setPaddingTop(30f);
		cellSpAR.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(cellSpAR);
		// third row
		PdfPCell addressCell = new PdfPCell(address);
		addressCell.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(addressCell);
		pdfCell.addCell(cellNull);
		pdfCell.addCell(cellNull);
		// fourth row
		PdfPCell phoneCell = new PdfPCell(phone);
		phoneCell.setBorder(Rectangle.NO_BORDER);
		phoneCell.setPaddingLeft(20f);
		pdfCell.addCell(phoneCell);
		pdfCell.addCell(new PdfPCell(cellNull));
		PdfPCell dateOrderCell = new PdfPCell(dateOrder);
		dateOrderCell.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(dateOrderCell);
		// fifth row
		// PdfPCell firstLastNameCell = new PdfPCell(firstLastName);
		// firstLastNameCell.setBorder(Rectangle.NO_BORDER);
		// pdfCell.addCell(firstLastNameCell);
		pdfCell.addCell(cellNull);
		// PdfPCell ageCell = new PdfPCell(agePara);
		// ageCell.setBorder(Rectangle.NO_BORDER);
		// ageCell.setPaddingLeft(50f);
		// pdfCell.addCell(ageCell);
		/*
		 * PdfPTable pdfCell = new PdfPTable(1); PdfPCell cell = new PdfPCell(new
		 * Phrase("Ø¢Ø²Ù…Ø§ÙŠØ´", ArialFont));
		 * 
		 * cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL); pdfCell.addCell(cell);
		 */
		// pdfCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

		System.out.println(this.nameAR.getText());

		// cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

		Paragraph nameAR = new Paragraph("Ø§Ù„Ø­ÙƒÙŠÙ…Ù€(Ø©) " + this.nameAR.getText(), ArialFont);
		Paragraph specAR = new Paragraph(this.specialityAR.getText(), ArialFont);
		// LocalDateTime myDate = LocalDateTime.now();

		nameAR.setAlignment(Element.ALIGN_RIGHT);
		specAR.setAlignment(Element.ALIGN_RIGHT);
		dateOrder.setAlignment(Element.ALIGN_RIGHT);

		// Paragraph pDate = new Paragraph(dateDebut+" jusqu'au "+dateFin, redFont);

		/*
		 * p.setAlignment(Element.ALIGN_CENTER);
		 * 
		 * pNameLab.setAlignment(Element.ALIGN_LEFT);
		 * pNumBill.setAlignment(Element.ALIGN_LEFT); //
		 * pDate.setAlignment(Element.ALIGN_RIGHT);
		 *//*
			 * /
			 */
		Paragraph pPrincipale = new Paragraph();

		pPrincipale.add(cabinet);
		// pPrincipale.add(pDoctor);
		// pPrincipale.add(jobFR);
		pPrincipale.add(pdfCell);
		// pPrincipale.add(nameAR);
		// pPrincipale.add(specAR);
		// pPrincipale.add(address);
		// pPrincipale.add(phone);
		// pPrincipale.add(dateOrder);

		// preface.add(pNameLab);

		//
		preface.add(pPrincipale);
		// preface.add(pNumBill);
		addEmptyLine(preface, 1);
		addEmptyLine(preface, 1);
		addEmptyLine(preface, 1);

		p.setAlignment(Element.ALIGN_CENTER);
		preface.add(p);

		// addEmptyLine(preface, 1);
		// preface.add(new Paragraph("Monsieur/Madame: " + tfPatientStopJob1.getText(),
		// catFont));
		addEmptyLine(preface, 1);
		addEmptyLine(preface, 1);
		addEmptyLine(preface, 1);
		// Will create: Report generated by: _name, _date
		// create the paragraphe
		String stopJob = this.jftTAFreeRedaction.getText();
		preface.add(new Paragraph(stopJob, catFont));
		// preface.add(new Paragraph("Je soussigné DR .................... certifie
		// avoir pris", smallBold));
		// addEmptyLine(preface, 1);
		// preface.add(new Paragraph("en charge le patient sous-nommé le " +
		// patientStopJob, smallBold));
		// preface.add(new Paragraph("Pour " + causes, smallBold));
		/*
		 * preface.add(new Paragraph(
		 * "L'état du santé du patient justifie un arrÃªt de travail de 3 trois jours Ã  compter Ã  partir de ce jour mÃªme."
		 * , smallBold));
		 */

		addEmptyLine(preface, 2);

		// preface.add(new Paragraph("ArrÃªt de travail delivré Ã  l'intéressé pour
		// servir
		// et valoir ce que de droit.",
		// catFont));
		document.add(preface);
		// close the document

		// **********************Begin open the PDF
		// File*********************************
		File myFile = new File(file_name);
		try {
			Desktop.getDesktop().open(myFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ######################End of the open PDF
		// File#################################

		document.close();

		/****************************
		 * End generating PDF Arret_de_travail
		 ***************************/

	}

	@FXML
	void refreshFreeRedaction(ActionEvent event) {
		this.jftTAFreeRedaction.setText("");
	}

	@FXML
	void refreshOrdonnanceRedaction(ActionEvent event) {

		this.vBoxPrincipaleBise.getChildren().clear();
	}

	@FXML
	void printStopingJob(ActionEvent event) throws DocumentException, IOException {

		if (this.tfPatientStopJob1.validate()) {
			BaseFont ArialBase = BaseFont.createFont("C:\\Windows\\Fonts\\arialbd.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			Font ArialFont = new Font(ArialBase, 15, Font.BOLD);
			Font ArialFontOrdinary = new Font(ArialBase, 14);

			/**********************
			 * begin generating the folders
			 ************************************/
			LocalDateTime myDate = LocalDateTime.now();
			// DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// File theDir = new File("C:\\Users\\LTO\\Desktop\\lib jar\\FARES");
			String tableTheDir[] = { "assets\\sqlite\\data\\arretTravail\\" + myDate.getYear(),
					"assets\\sqlite\\data\\arretTravail\\" + myDate.getYear() + "\\" + myDate.getMonthValue(),
					"assets\\sqlite\\data\\arretTravail\\" + myDate.getYear() + "\\" + myDate.getMonthValue() + "\\"
							+ myDate.getDayOfMonth() };

			// if the directory does not exist, create it
			// ********************begins of creation directories***************/
			for (String string : tableTheDir) {
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
			}
			// ##################"end of creating of directories##############

			// *******************begins of creating the PDF Document***********/

			String minuteSecondMiliSecond = myDate.getHour() + "" + myDate.getMinute() + "" + myDate.getSecond() + ""
					+ myDate.getNano();

			String file_name = "assets\\sqlite\\data\\arretTravail\\" + myDate.getYear() + "\\" + myDate.getMonthValue()
					+ "\\" + myDate.getDayOfMonth() + "\\" + minuteSecondMiliSecond + ".pdf";

			/**********************
			 * End generating the folders
			 ***************************************/

			String patientStopJob = this.tfPatientStopJob1.getText();
			String causes = this.taCauses1.getText();
			String periodePatientStopJob = this.tfPeriodePatientJob1.getText();

			/****************************
			 * Begin generating PDF Arret_de_travail
			 *************************/
			// String file_name = "C:\\Users\\LTO\\Desktop\\lib jar\\arretDeTravail.pdf";
			// create a new pdf document
			Document document = new Document();

			PdfWriter.getInstance(document, new FileOutputStream(file_name));
//open the pdf document
			document.open();

			Paragraph preface = new Paragraph();
			// We add one empty line ===> add jump
			// preface.setAlignment(Element.ALIGN_RIGHT);
			// preface.setAlignment(Element.ALIGN_CENTER);
			// addEmptyLine(preface, 1);
			// Lets write a big header8877
			Paragraph p = new Paragraph();
			p.setFont(headerFont);
			p.add(new Chunk("ARRET DE TRAVAIL").setUnderline(0.9f, -2f));
			p.setAlignment(Element.ALIGN_CENTER);

			// String name1 = new String("Ø£Ø­Ù…Ø¯", "UTF-8");
			// Paragraph nameP = new Paragraph(name1, catFont);

			Paragraph cabinet = new Paragraph();
			cabinet.setFont(catFont);
			cabinet.add(new Chunk("CABINET DENTAIRE").setUnderline(0.9f, -2f));

			// Paragraph pDoctor = new Paragraph("Dr "+this.nameL.getText());
			// Paragraph jobFR = new Paragraph(this.specialityL.getText());
			// declare the table with 3 column
			PdfPTable pdfCell = new PdfPTable(3);
			pdfCell.setWidthPercentage(100);

			// first row

			PdfPCell cellNL = new PdfPCell(new Paragraph(this.nameL.getText(), subFont));
			// PdfPCell cellPhoto= new PdfPCell(new Paragraph("photo photo
			// photo",headerFont));
			PdfPCell cellNAR = new PdfPCell(new Paragraph("Ø§Ù„Ø­ÙƒÙŠÙ…Ù€(Ø©) " + this.nameAR.getText(), ArialFont));

			// seconde row
			PdfPCell cellSpL = new PdfPCell(new Paragraph(this.specialityL.getText(), ordinaryFont));
			PdfPCell cellSpAR = new PdfPCell(new Paragraph(this.specialityAR.getText(), ArialFontOrdinary));

			cellNAR.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			// cellNAR.ma
			// cellNAR.setBorder(Rectangle.NO_BORDER);

			cellSpAR.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			// cellSpAR.setBorder(Rectangle.NO_BORDER);
			cellNAR.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSpAR.setHorizontalAlignment(Element.ALIGN_RIGHT);

			Paragraph address = new Paragraph(
					this.address.getText() + "," + this.municipality.getText() + "-W." + this.town.getValue(),
					ordinaryFont);
			Paragraph phone = new Paragraph("Tél: " + this.phone.getText(), ordinaryFont);
			Paragraph firstLastName = new Paragraph("Nom & Prénom: " + this.tfPatientStopJob1.getText(), ordinaryFont);

			String age = "";
			if (this.hashTableDetectAgePatient.containsKey(tfPatientStopJob1.getText())) {
				age = this.hashTableDetectAgePatient.get(tfPatientStopJob1.getText()) + "";

			}

			Paragraph agePara = new Paragraph("Age: " + age, ordinaryFont);
			DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-YYYY");

			Paragraph dateOrder = new Paragraph(this.municipality.getText() + ", le: " + myDate.format(myFormat),
					ordinaryFont);

			/*
			 * cabinet.setAlignment(Element.ALIGN_LEFT); //
			 * pDoctor.setAlignment(Element.ALIGN_LEFT); //
			 * jobFR.setAlignment(Element.ALIGN_LEFT);
			 * address.setAlignment(Element.ALIGN_LEFT);
			 * phone.setAlignment(Element.ALIGN_LEFT);
			 * firstLastName.setAlignment(Element.ALIGN_LEFT);
			 */

			String IMG = "assets//teeth2.jpg";
			// first row
			// cellNL.setPaddingLeft(50f);
			cellNL.setBorder(Rectangle.NO_BORDER);
			cellNL.setPaddingTop(25f);
			pdfCell.addCell(cellNL);
			PdfPCell cellNull = new PdfPCell(new Paragraph(""));
			cellNull.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellNull);
			cellNAR.setPaddingLeft(30f);
			cellNAR.setPaddingTop(25f);
			cellNAR.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellNAR);
			// table.addCell(labDetaille.getDate());
			// seconde row
			cellSpL.setPaddingLeft(15f);
			// cellSpL.setPaddingTop(30f);
			cellSpL.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellSpL);

			Image img = Image.getInstance(IMG);
			img.scaleAbsolute(100f, 100f);
			// img.setWidthPercentage(25);
			PdfPCell imgCell = new PdfPCell(img);
			imgCell.setPaddingLeft(30f);
			imgCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(imgCell);
			cellSpAR.setPaddingLeft(50f);
			// cellSpAR.setPaddingTop(30f);
			cellSpAR.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellSpAR);
			// third row
			PdfPCell addressCell = new PdfPCell(address);
			addressCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(addressCell);
			pdfCell.addCell(cellNull);
			pdfCell.addCell(cellNull);
			// fourth row
			PdfPCell phoneCell = new PdfPCell(phone);
			phoneCell.setBorder(Rectangle.NO_BORDER);
			phoneCell.setPaddingLeft(20f);
			pdfCell.addCell(phoneCell);
			pdfCell.addCell(new PdfPCell(cellNull));
			PdfPCell dateOrderCell = new PdfPCell(dateOrder);
			dateOrderCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(dateOrderCell);
			// fifth row
			PdfPCell firstLastNameCell = new PdfPCell(firstLastName);
			firstLastNameCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(firstLastNameCell);
			pdfCell.addCell(cellNull);
			PdfPCell ageCell = new PdfPCell(agePara);
			ageCell.setBorder(Rectangle.NO_BORDER);
			ageCell.setPaddingLeft(50f);
			pdfCell.addCell(ageCell);
			/*
			 * PdfPTable pdfCell = new PdfPTable(1); PdfPCell cell = new PdfPCell(new
			 * Phrase("Ø¢Ø²Ù…Ø§ÙŠØ´", ArialFont));
			 * 
			 * cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL); pdfCell.addCell(cell);
			 */
			// pdfCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

			System.out.println(this.nameAR.getText());

			// cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

			Paragraph nameAR = new Paragraph("Ø§Ù„Ø­ÙƒÙŠÙ…Ù€(Ø©) " + this.nameAR.getText(), ArialFont);
			Paragraph specAR = new Paragraph(this.specialityAR.getText(), ArialFont);
			// LocalDateTime myDate = LocalDateTime.now();

			nameAR.setAlignment(Element.ALIGN_RIGHT);
			specAR.setAlignment(Element.ALIGN_RIGHT);
			dateOrder.setAlignment(Element.ALIGN_RIGHT);

			// Paragraph pDate = new Paragraph(dateDebut+" jusqu'au "+dateFin, redFont);

			/*
			 * p.setAlignment(Element.ALIGN_CENTER);
			 * 
			 * pNameLab.setAlignment(Element.ALIGN_LEFT);
			 * pNumBill.setAlignment(Element.ALIGN_LEFT); //
			 * pDate.setAlignment(Element.ALIGN_RIGHT);
			 *//*
				 * /
				 */
			Paragraph pPrincipale = new Paragraph();

			pPrincipale.add(cabinet);
			// pPrincipale.add(pDoctor);
			// pPrincipale.add(jobFR);
			pPrincipale.add(pdfCell);
			// pPrincipale.add(nameAR);
			// pPrincipale.add(specAR);
			// pPrincipale.add(address);
			// pPrincipale.add(phone);
			// pPrincipale.add(dateOrder);

			// preface.add(pNameLab);

			//
			preface.add(pPrincipale);
			// preface.add(pNumBill);
			addEmptyLine(preface, 1);
			addEmptyLine(preface, 1);
			addEmptyLine(preface, 1);
			p.setAlignment(Element.ALIGN_CENTER);
			preface.add(p);

			// addEmptyLine(preface, 1);
			// preface.add(new Paragraph("Monsieur/Madame: " + tfPatientStopJob1.getText(),
			// catFont));
			addEmptyLine(preface, 1);
			addEmptyLine(preface, 1);
			addEmptyLine(preface, 1);
			// Will create: Report generated by: _name, _date
			// create the paragraphe
			String stopJob = "Je soussigné DR " + this.nameL.getText() + " certifie avoir pris "
					+ "en charge le patient sus-nommé le " + myDate.getDayOfMonth() + "/" + myDate.getMonthValue() + "/"
					+ myDate.getYear() + " Pour " + taCauses1.getText()
					+ " L'état de santé du patient justifie un arrÃªt de travail de " + tfPeriodePatientJob1.getText()
					+ " Ã  compter Ã  partir de ce jour mÃªme.";
			preface.add(new Paragraph(stopJob, catFont));
			// preface.add(new Paragraph("Je soussigné DR .................... certifie
			// avoir pris", smallBold));
			// addEmptyLine(preface, 1);
			// preface.add(new Paragraph("en charge le patient sous-nommé le " +
			// patientStopJob, smallBold));
			// preface.add(new Paragraph("Pour " + causes, smallBold));
			/*
			 * preface.add(new Paragraph(
			 * "L'état du santé du patient justifie un arrÃªt de travail de 3 trois jours Ã  compter Ã  partir de ce jour mÃªme."
			 * , smallBold));
			 */

			addEmptyLine(preface, 2);

			preface.add(new Paragraph("ArrÃªt de travail delivré Ã  l'intéressé pour servir et valoir ce que de droit.",
					catFont));
			document.add(preface);
			// close the document

			// **********************Begin open the PDF
			// File*********************************
			File myFile = new File(file_name);
			try {
				Desktop.getDesktop().open(myFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ######################End of the open PDF
			// File#################################

			document.close();

			/****************************
			 * End generating PDF Arret_de_travail
			 ***************************/
		}
	}

	/**************************
	 * End stopingJob
	 *************************************/

	// ########################################## Begin generating Justification
	// ########################

	@FXML
	private JFXTextField tfPatientJustification1;
	@FXML
	private JFXTextArea taCausesJustification1;
	@FXML
	private Text tDateJustification;

	@FXML
	void printJustification(ActionEvent event) throws DocumentException, IOException {

		if (this.tfPatientJustification1.validate()) {
			BaseFont ArialBase = BaseFont.createFont("C:\\Windows\\Fonts\\arialbd.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);

			Font ArialFont = new Font(ArialBase, 15, Font.BOLD);
			Font ArialFontOrdinary = new Font(ArialBase, 14);

			/**********************
			 * begin generating the folders
			 ************************************/
			LocalDateTime myDate = LocalDateTime.now();
			// DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// File theDir = new File("C:\\Users\\LTO\\Desktop\\lib jar\\FARES");
			String tableTheDir[] = { "assets\\sqlite\\data\\justification\\" + myDate.getYear(),
					"assets\\sqlite\\data\\justification\\" + myDate.getYear() + "\\" + myDate.getMonthValue(),
					"assets\\sqlite\\data\\justification\\" + myDate.getYear() + "\\" + myDate.getMonthValue() + "\\"
							+ myDate.getDayOfMonth() };

			// if the directory does not exist, create it
			// ********************begins of creation directories***************/
			for (String string : tableTheDir) {
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
			}
			// ##################"end of creating of directories##############

			// *******************begins of creating the PDF Document***********/

			String minuteSecondMiliSecond = myDate.getHour() + "" + myDate.getMinute() + "" + myDate.getSecond() + ""
					+ myDate.getNano();

			String file_name = "assets\\sqlite\\data\\justification\\" + myDate.getYear() + "\\"
					+ myDate.getMonthValue() + "\\" + myDate.getDayOfMonth() + "\\" + minuteSecondMiliSecond + ".pdf";

			/**********************
			 * End generating the folders
			 ***************************************/

			String patientJustification = this.tfPatientJustification1.getText();
			String causes = this.taCausesJustification1.getText();

			/****************************
			 * Begin generating PDF Arret_de_travail
			 *************************/
			// String file_name = "C:\\Users\\LTO\\Desktop\\lib jar\\arretDeTravail.pdf";
			// create a new pdf document
			Document document = new Document();

			PdfWriter.getInstance(document, new FileOutputStream(file_name));
//open the pdf document
			document.open();

			Paragraph preface = new Paragraph();
// We add one empty line ===> add jump
// preface.setAlignment(Element.ALIGN_RIGHT);
// preface.setAlignment(Element.ALIGN_CENTER);
// addEmptyLine(preface, 1);
// Lets write a big header8877
			Paragraph p = new Paragraph();
			p.setFont(headerFont);
			p.add(new Chunk("JUSTIFICATION").setUnderline(0.9f, -2f));
			p.setAlignment(Element.ALIGN_CENTER);

// String name1 = new String("Ø£Ø­Ù…Ø¯", "UTF-8");
// Paragraph nameP = new Paragraph(name1, catFont);

			Paragraph cabinet = new Paragraph();
			cabinet.setFont(catFont);
			cabinet.add(new Chunk("CABINET DENTAIRE").setUnderline(0.9f, -2f));

// Paragraph pDoctor = new Paragraph("Dr "+this.nameL.getText());
// Paragraph jobFR = new Paragraph(this.specialityL.getText());
//declare the table with 3 column
			PdfPTable pdfCell = new PdfPTable(3);
			pdfCell.setWidthPercentage(100);

//first row

			PdfPCell cellNL = new PdfPCell(new Paragraph(this.nameL.getText(), subFont));
//PdfPCell cellPhoto= new PdfPCell(new Paragraph("photo photo photo",headerFont));
			PdfPCell cellNAR = new PdfPCell(new Paragraph("Ø§Ù„Ø­ÙƒÙŠÙ…Ù€(Ø©) " + this.nameAR.getText(), ArialFont));

//seconde row
			PdfPCell cellSpL = new PdfPCell(new Paragraph(this.specialityL.getText(), ordinaryFont));
			PdfPCell cellSpAR = new PdfPCell(new Paragraph(this.specialityAR.getText(), ArialFontOrdinary));

			cellNAR.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
//cellNAR.ma
//cellNAR.setBorder(Rectangle.NO_BORDER);

			cellSpAR.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
//cellSpAR.setBorder(Rectangle.NO_BORDER);
			cellNAR.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSpAR.setHorizontalAlignment(Element.ALIGN_RIGHT);

			Paragraph address = new Paragraph(
					this.address.getText() + "," + this.municipality.getText() + "-W." + this.town.getValue(),
					ordinaryFont);
			Paragraph phone = new Paragraph("Tél: " + this.phone.getText(), ordinaryFont);
			Paragraph firstLastName = new Paragraph("Nom & Prénom: " + this.tfPatientJustification1.getText(),
					ordinaryFont);

			String age = "";
			if (this.hashTableDetectAgePatient.containsKey(tfPatientJustification1.getText())) {
				age += this.hashTableDetectAgePatient.get(tfPatientJustification1.getText());
			}

			Paragraph agePara = new Paragraph("Age: " + age, ordinaryFont);
			DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-YYYY");

			Paragraph dateOrder = new Paragraph(this.municipality.getText() + ", le: " + myDate.format(myFormat),
					ordinaryFont);

			/*
			 * cabinet.setAlignment(Element.ALIGN_LEFT); //
			 * pDoctor.setAlignment(Element.ALIGN_LEFT); //
			 * jobFR.setAlignment(Element.ALIGN_LEFT);
			 * address.setAlignment(Element.ALIGN_LEFT);
			 * phone.setAlignment(Element.ALIGN_LEFT);
			 * firstLastName.setAlignment(Element.ALIGN_LEFT);
			 */

			String IMG = "assets//teeth2.jpg";
//first row
			// cellNL.setPaddingLeft(50f);
			cellNL.setBorder(Rectangle.NO_BORDER);
			cellNL.setPaddingTop(25f);
			pdfCell.addCell(cellNL);
			PdfPCell cellNull = new PdfPCell(new Paragraph(""));
			cellNull.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellNull);
			cellNAR.setPaddingLeft(30f);
			cellNAR.setPaddingTop(25f);
			cellNAR.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellNAR);
//table.addCell(labDetaille.getDate());
//seconde row
			cellSpL.setPaddingLeft(15f);
			// cellSpL.setPaddingTop(30f);
			cellSpL.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellSpL);

			Image img = Image.getInstance(IMG);
			img.scaleAbsolute(100f, 100f);
//img.setWidthPercentage(25);
			PdfPCell imgCell = new PdfPCell(img);
			imgCell.setPaddingLeft(30f);
			imgCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(imgCell);
			cellSpAR.setPaddingLeft(50f);
			// cellSpAR.setPaddingTop(30f);
			cellSpAR.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellSpAR);
//third row
			PdfPCell addressCell = new PdfPCell(address);
			addressCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(addressCell);
			pdfCell.addCell(cellNull);
			pdfCell.addCell(cellNull);
//fourth row
			PdfPCell phoneCell = new PdfPCell(phone);
			phoneCell.setBorder(Rectangle.NO_BORDER);
			phoneCell.setPaddingLeft(20f);
			pdfCell.addCell(phoneCell);
			pdfCell.addCell(new PdfPCell(cellNull));
			PdfPCell dateOrderCell = new PdfPCell(dateOrder);
			dateOrderCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(dateOrderCell);
//fifth row
			PdfPCell firstLastNameCell = new PdfPCell(firstLastName);
			firstLastNameCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(firstLastNameCell);
			pdfCell.addCell(cellNull);
			PdfPCell ageCell = new PdfPCell(agePara);
			ageCell.setBorder(Rectangle.NO_BORDER);
			ageCell.setPaddingLeft(50f);
			pdfCell.addCell(ageCell);
			/*
			 * PdfPTable pdfCell = new PdfPTable(1); PdfPCell cell = new PdfPCell(new
			 * Phrase("Ø¢Ø²Ù…Ø§ÙŠØ´", ArialFont));
			 * 
			 * cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL); pdfCell.addCell(cell);
			 */
// pdfCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

			System.out.println(this.nameAR.getText());

// cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

			Paragraph nameAR = new Paragraph("Ø§Ù„Ø­ÙƒÙŠÙ…Ù€(Ø©) " + this.nameAR.getText(), ArialFont);
			Paragraph specAR = new Paragraph(this.specialityAR.getText(), ArialFont);
// LocalDateTime myDate = LocalDateTime.now();

			nameAR.setAlignment(Element.ALIGN_RIGHT);
			specAR.setAlignment(Element.ALIGN_RIGHT);
			dateOrder.setAlignment(Element.ALIGN_RIGHT);

// Paragraph pDate = new Paragraph(dateDebut+" jusqu'au "+dateFin, redFont);

			/*
			 * p.setAlignment(Element.ALIGN_CENTER);
			 * 
			 * pNameLab.setAlignment(Element.ALIGN_LEFT);
			 * pNumBill.setAlignment(Element.ALIGN_LEFT); //
			 * pDate.setAlignment(Element.ALIGN_RIGHT);
			 *//*
				 * /
				 */
			Paragraph pPrincipale = new Paragraph();

			pPrincipale.add(cabinet);
// pPrincipale.add(pDoctor);
// pPrincipale.add(jobFR);
			pPrincipale.add(pdfCell);
// pPrincipale.add(nameAR);
// pPrincipale.add(specAR);
//pPrincipale.add(address);
//pPrincipale.add(phone);
//pPrincipale.add(dateOrder);

			// pPrincipale.add(firstLastName);

			// preface.add(pNameLab);

			//
			preface.add(pPrincipale);
			// preface.add(pNumBill);
			addEmptyLine(preface, 1);
			addEmptyLine(preface, 1);
			addEmptyLine(preface, 1);
			// Lets write a big header
			// Paragraph p=new Paragraph("Justification", headerFont);
			p.setAlignment(Element.ALIGN_CENTER);
			preface.add(p);

			addEmptyLine(preface, 5);
			// preface.add(new Paragraph("Monsieur/Madame: " +
			// tfPatientJustification1.getText(), catFont));
			// addEmptyLine(preface, 1);
			// Will create: Report generated by: _name, _date
			// create the paragraphe
			String justification = "Je soussigné DR " + this.nameL.getText() + " certifie avoir pris "
					+ "en charge le patient sus-nommé le " + myDate.getDayOfMonth() + "/" + myDate.getMonthValue() + "/"
					+ myDate.getYear() + " Pour " + taCausesJustification1.getText();
			preface.add(new Paragraph(justification, catFont));
			// preface.add(new Paragraph("Je soussigné DR .................... certifie
			// avoir pris", smallBold));
			// addEmptyLine(preface, 1);
			// preface.add(new Paragraph("en charge le patient sous-nommé le " +
			// patientStopJob, smallBold));
			// preface.add(new Paragraph("Pour " + causes, smallBold));
			/*
			 * preface.add(new Paragraph(
			 * "L'état du santé du patient justifie un arrÃªt de travail de 3 trois jours Ã  compter Ã  partir de ce jour mÃªme."
			 * , smallBold));
			 */

			addEmptyLine(preface, 2);

			preface.add(new Paragraph("Justification delivré Ã  l'intéressé pour servir et valoir ce que de droit.",
					catFont));
			document.add(preface);
			// close the document

			// **********************Begin open the PDF
			// File*********************************
			File myFile = new File(file_name);
			try {
				Desktop.getDesktop().open(myFile);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// ######################End of the open PDF
			// File#################################

			document.close();

			/****************************
			 * End generating PDF Arret_de_travail
			 ***************************/
		}
	}

	@FXML
	void printOrder(ActionEvent event) throws DocumentException, IOException, SQLException {

		if (this.jfxTPatientRedaction.validate()) {
			BaseFont ArialBase = BaseFont.createFont("C:\\Windows\\Fonts\\arialbd.ttf", BaseFont.IDENTITY_H,
					BaseFont.EMBEDDED);
			Font ArialFont = new Font(ArialBase, 15, Font.BOLD);
			Font ArialFontOrdinary = new Font(ArialBase, 14);

			// helper.parseXHtml(writer, document, htmlIn, in, Charset.forName("UTF-8"),
			// FontFactory.getFontImp());
			// Font arabFont = FontFactory.getFont("C:\\Windows\\Fonts\\arabtype.ttf");

			// ((FontFactory) arabFont).getFontImp().defaultEncoding = BaseFont.IDENTITY_H;

			/**********************
			 * begin generating the folders
			 ************************************/
			LocalDateTime myDate = LocalDateTime.now();
			// DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// File theDir = new File("C:\\Users\\LTO\\Desktop\\lib jar\\FARES");
			String tableTheDir[] = { "assets\\sqlite\\data\\ordonnance\\" + myDate.getYear(),
					"assets\\sqlite\\data\\ordonnance\\" + myDate.getYear() + "\\" + myDate.getMonthValue(),
					"assets\\sqlite\\data\\ordonnance\\" + myDate.getYear() + "\\" + myDate.getMonthValue() + "\\"
							+ myDate.getDayOfMonth() };

			// if the directory does not exist, create it
			// ********************begins of creation directories***************/
			for (String string : tableTheDir) {
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
			}
			// ##################"end of creating of directories##############
			/**********************
			 * End generating the folders
			 ***************************************/

			// *******************begins of creating the PDF Document***********/

			String minuteSecondMiliSecond = myDate.getHour() + "" + myDate.getMinute() + "" + myDate.getSecond() + ""
					+ myDate.getNano();

			String file_name = "assets\\sqlite\\data\\ordonnance\\" + myDate.getYear() + "\\" + myDate.getMonthValue()
					+ "\\" + myDate.getDayOfMonth() + "\\" + minuteSecondMiliSecond + ".pdf";

			/****************************
			 * Begin generating PDF Arret_de_travail
			 *************************/

			Document document = new Document();

			PdfWriter writer = null;
			try {
				writer = PdfWriter.getInstance(document, new FileOutputStream(file_name));
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
//open the pdf document
			document.open();

			Paragraph preface = new Paragraph();
			// We add one empty line ===> add jump
			// preface.setAlignment(Element.ALIGN_RIGHT);
			// preface.setAlignment(Element.ALIGN_CENTER);
			// addEmptyLine(preface, 1);
			// Lets write a big header8877
			Paragraph p = new Paragraph();
			p.setFont(headerFont);
			p.add(new Chunk("ORDONNANCE").setUnderline(0.9f, -2f));
			p.setAlignment(Element.ALIGN_CENTER);

			// String name1 = new String("Ø£Ø­Ù…Ø¯", "UTF-8");
			// Paragraph nameP = new Paragraph(name1, catFont);

			Paragraph cabinet = new Paragraph();
			cabinet.setFont(catFont);
			cabinet.add(new Chunk("CABINET DENTAIRE").setUnderline(0.9f, -2f));

			// Paragraph pDoctor = new Paragraph("Dr "+this.nameL.getText());
			// Paragraph jobFR = new Paragraph(this.specialityL.getText());
			// declare the table with 3 column
			PdfPTable pdfCell = new PdfPTable(3);
			pdfCell.setWidthPercentage(100);

			// first row

			System.out.println("aaaaaaaaaaaaaaaaaayyyyyyyyyyyyyyyyeeeeeeee     " + this.nameL.getText());
			PdfPCell cellNL = new PdfPCell(new Paragraph(this.nameL.getText(), subFont));
			// PdfPCell cellPhoto= new PdfPCell(new Paragraph("photo photo
			// photo",headerFont));
			PdfPCell cellNAR = new PdfPCell(new Paragraph("(ة)الحكيمـ " + this.nameAR.getText(), ArialFont));

			// seconde row
			PdfPCell cellSpL = new PdfPCell(new Paragraph(this.specialityL.getText(), ordinaryFont));
			PdfPCell cellSpAR = new PdfPCell(new Paragraph(this.specialityAR.getText(), ArialFontOrdinary));

			cellNAR.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			// cellNAR.ma
			// cellNAR.setBorder(Rectangle.NO_BORDER);

			cellSpAR.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);
			// cellSpAR.setBorder(Rectangle.NO_BORDER);
			cellNAR.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cellSpAR.setHorizontalAlignment(Element.ALIGN_RIGHT);

			Paragraph address = new Paragraph(
					this.address.getText() + "," + this.municipality.getText() + "-W." + this.town.getValue(),
					ordinaryFont);
			Paragraph phone = new Paragraph("Tél: " + this.phone.getText(), ordinaryFont);
			Paragraph firstLastName = new Paragraph("Nom & Prénom: " + this.jfxTPatientRedaction.getText(),
					ordinaryFont);

			String age = "";
			System.out.println(jfxTPatientRedaction.getText());
			System.out.println("Patient AGE : " + this.hashTableDetectAgePatient.get(jfxTPatientRedaction.getText()));
			if (this.hashTableDetectAgePatient.containsKey(jfxTPatientRedaction.getText())) {
				age = this.hashTableDetectAgePatient.get(jfxTPatientRedaction.getText()) + "";
			}
			Paragraph agePara = new Paragraph("Age: " + age, ordinaryFont);
			DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-YYYY");

			Paragraph dateOrder = new Paragraph(this.municipality.getText() + ", le: " + myDate.format(myFormat),
					ordinaryFont);

			/*
			 * cabinet.setAlignment(Element.ALIGN_LEFT); //
			 * pDoctor.setAlignment(Element.ALIGN_LEFT); //
			 * jobFR.setAlignment(Element.ALIGN_LEFT);
			 * address.setAlignment(Element.ALIGN_LEFT);
			 * phone.setAlignment(Element.ALIGN_LEFT);
			 * firstLastName.setAlignment(Element.ALIGN_LEFT);
			 */

			String IMG = "assets//teeth2.jpg";

			// first row
			// cellNL.setPaddingLeft(50f);
			cellNL.setBorder(Rectangle.NO_BORDER);
			cellNL.setPaddingTop(25f);
			pdfCell.addCell(cellNL);
			PdfPCell cellNull = new PdfPCell(new Paragraph(""));
			cellNull.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellNull);
			cellNAR.setPaddingLeft(30f);
			cellNAR.setPaddingTop(25f);
			cellNAR.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellNAR);
			// table.addCell(labDetaille.getDate());
			// seconde row
			cellSpL.setPaddingLeft(15f);
			// cellSpL.setPaddingTop(30f);
			cellSpL.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellSpL);

			Image img = Image.getInstance(IMG);
			img.scaleAbsolute(100f, 100f);
			// img.setWidthPercentage(25);
			PdfPCell imgCell = new PdfPCell(img);
			imgCell.setPaddingLeft(30f);
			imgCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(imgCell);
			cellSpAR.setPaddingLeft(50f);
			// cellSpAR.setPaddingTop(30f);
			cellSpAR.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(cellSpAR);
//third row
			PdfPCell addressCell = new PdfPCell(address);
			addressCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(addressCell);
			pdfCell.addCell(cellNull);
			pdfCell.addCell(cellNull);
			// fourth row
			PdfPCell phoneCell = new PdfPCell(phone);
			phoneCell.setBorder(Rectangle.NO_BORDER);
			phoneCell.setPaddingLeft(20f);
			pdfCell.addCell(phoneCell);
			pdfCell.addCell(new PdfPCell(cellNull));
			PdfPCell dateOrderCell = new PdfPCell(dateOrder);
			dateOrderCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(dateOrderCell);
			// fifth row
			PdfPCell firstLastNameCell = new PdfPCell(firstLastName);
			firstLastNameCell.setBorder(Rectangle.NO_BORDER);
			pdfCell.addCell(firstLastNameCell);
			pdfCell.addCell(cellNull);
			PdfPCell ageCell = new PdfPCell(agePara);
			ageCell.setBorder(Rectangle.NO_BORDER);
			ageCell.setPaddingLeft(50f);
			pdfCell.addCell(ageCell);
			/*
			 * PdfPTable pdfCell = new PdfPTable(1); PdfPCell cell = new PdfPCell(new
			 * Phrase("Ø¢Ø²Ù…Ø§ÙŠØ´", ArialFont));
			 * 
			 * cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL); pdfCell.addCell(cell);
			 */
			// pdfCell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

			System.out.println(this.nameAR.getText());

			// cell.setRunDirection(PdfWriter.RUN_DIRECTION_RTL);

			Paragraph nameAR = new Paragraph("(ة)الحكيمـ " + this.nameAR.getText(), ArialFont);
			Paragraph specAR = new Paragraph(this.specialityAR.getText(), ArialFont);
			// LocalDateTime myDate = LocalDateTime.now();

			nameAR.setAlignment(Element.ALIGN_RIGHT);
			specAR.setAlignment(Element.ALIGN_RIGHT);
			dateOrder.setAlignment(Element.ALIGN_RIGHT);

			// Paragraph pDate = new Paragraph(dateDebut+" jusqu'au "+dateFin, redFont);

			/*
			 * p.setAlignment(Element.ALIGN_CENTER);
			 * 
			 * pNameLab.setAlignment(Element.ALIGN_LEFT);
			 * pNumBill.setAlignment(Element.ALIGN_LEFT); //
			 * pDate.setAlignment(Element.ALIGN_RIGHT);
			 *//*
				 * /
				 */
			Paragraph pPrincipale = new Paragraph();

			pPrincipale.add(cabinet);
			// pPrincipale.add(pDoctor);
			// pPrincipale.add(jobFR);
			pPrincipale.add(pdfCell);
			// pPrincipale.add(nameAR);
			// pPrincipale.add(specAR);
			// pPrincipale.add(address);
			// pPrincipale.add(phone);
			// pPrincipale.add(dateOrder);
			// pPrincipale.add(firstLastName);

			// preface.add(pNameLab);

			//
			preface.add(pPrincipale);
			// preface.add(pNumBill);
			addEmptyLine(preface, 1);
			addEmptyLine(preface, 1);
			addEmptyLine(preface, 1);
			preface.add(p);
			addEmptyLine(preface, 1);
			addEmptyLine(preface, 1);
			// Chunk underline = new Chunk("Underline. farouke kj kjdfjfdj fjkfjfd");
			// underline.setUnderline(0.1f, -2f); //0.1 thick, -2 y-location
			// preface.add(underline);

			// Here we are interesting
			int compt = 1;
			System.out.println(vBoxPrincipaleBise.getChildren().size());
			for (int i = 1; i < vBoxPrincipaleBise.getChildren().size(); i += 2) {

				VBox vbox = (VBox) vBoxPrincipaleBise.getChildren().get(i);
				HBox hbox = (HBox) vbox.getChildren().get(0);
				String medecineOrder = ((TextField) hbox.getChildren().get(0)).getText();
				this.insertMedecineIfNotExist(medecineOrder);
				String quantity = ((JFXTextField) hbox.getChildren().get(1)).getText();
				String typeOrder = ((JFXTextField) hbox.getChildren().get(2)).getText();
				// String qspOrder = ((JFXTextField) hbox.getChildren().get(3)).getText();
				String numOrder = ((JFXTextField) hbox.getChildren().get(3)).getText();
				// String dayOrder=((JFXTextField) hbox.getChildren().get(4)).getText();

				String detailOrder = ((TextArea) vbox.getChildren().get(1)).getText();

				// Paragraph preface = new Paragraph();
				// We add one empty line ===> add jump
				// addEmptyLine(preface, 1);
				// Lets write a big header

				Paragraph pr = new Paragraph();
				pr.setFont(ordinaryFont);
				pr.add("" + compt + ")    ");
				compt++;
				pr.add(new Chunk(medecineOrder + " " + quantity).setUnderline(0.9f, -2f));
				pr.add(" (" + typeOrder + ")__________________________________" + numOrder);

				preface.add(pr);
				// preface.add(new Paragraph(medecineOrder+"("+typeOrder+")"+qspOrder+"
				// "+numOrder+" "+dayOrder+" "+detailOrder, catFont));

				Paragraph pc = new Paragraph(detailOrder);
				pc.setFont(ordinaryFont);
				pc.setIndentationLeft(40);
				// preface.add(pc);
				preface.add(pc);

				addEmptyLine(preface, 1);

				// generateQrCode
				try {
					MyQr.init(file_name, "assets\\QrCode.png");
				} catch (NotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (WriterException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				Image qRImage = Image.getInstance("assets\\QrCode.png");
				qRImage.scaleAbsolute(100f, 100f);

				qRImage.setAbsolutePosition(0f, 0f);
				document.add(qRImage);
				// img.setWidthPercentage(25);
				// PdfPCell imgCell = new PdfPCell(img);

				// public void onEndPage(PdfWriter writer, Document document) {
				// ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
				// new Phrase(byte1 + "", FontFactory.getFont(FontFactory.TIMES_ROMAN, 7,
				// Font.NORMAL)), 110, 30, 0);
				// ColumnText.showTextAligned(writer.getDirectContent(), Element.ALIGN_CENTER,
				// new Phrase("page " + document.getPageNumber()), 550, 30, 0);
				// }4455

				// Add in orders table

				PreparedStatement stmt = null;
				// myDate.getYear()
				String request = "INSERT INTO orders(malade_id,file_name,file_path) VALUES(?,?,?); ";
				stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
				System.out.println(this.jfxTPatientRedaction.getText());
				System.out.println(this.hashTableDetectIDPatient.containsKey(this.jfxTPatientRedaction.getText()));

				stmt.setInt(1, this.hashTableDetectIDPatient.get(this.jfxTPatientRedaction.getText()));
				stmt.setString(2,
						this.jfxTPatientRedaction.getText() + "__" + myDate.getYear() + "-" + myDate.getMonth());
				stmt.setString(3, file_name);
				stmt.execute();

				stmt.close();
				// **********************Begin open the PDF
				// File*********************************
				File myFile = new File(file_name);
				try {
					Desktop.getDesktop().open(myFile);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// ######################End of the open PDF
				// File#################################

			}
			try {
				document.add(preface);
			} catch (DocumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				document.close();
			}

			// close the document

			/****************************
			 * End generating PDF Arret_de_travail
			 ***************************/
		}
	}

	private Hashtable<String, Boolean> hashTableInsertMedecineIfNotExist;

	private void inithashTableInsertMedecineIfNotExist() throws SQLException {

		hashTableInsertMedecineIfNotExist = new Hashtable<String, Boolean>();
		Statement stm = null;
		ResultSet rs = null;
		stm = new SQLiteJDBC().getConnection().createStatement();

		String request = "SELECT DISTINCT nom FROM medications";
		rs = stm.executeQuery(request);
		while (rs.next()) {

			this.hashTableInsertMedecineIfNotExist.put(rs.getString("nom"), true);
			// auto.add(new Medication(Integer.parseInt(rs.getString("medication_id")),
			// rs.getString("nom")));
		}
	}

	private void insertMedecineIfNotExist(String medecine) throws SQLException {

		if (!this.hashTableInsertMedecineIfNotExist.containsKey(medecine)) {
			this.hashTableInsertMedecineIfNotExist.put(medecine, true);

			String request = "INSERT INTO medications(nom) VALUES (?)";
			PreparedStatement stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
			stmt.setString(1, medecine);
			stmt.executeUpdate();
			/*
			 * String request = "INSERT INTO medications(nom) VALUES ('" + label.getText() +
			 * "')"; stm.execute(request);
			 */
			stmt.close();
		}

	}

	// ########################################## End geneerating Justification
	// ############################
	@FXML
	void printView(ActionEvent event) {

		try {
			String file_name = "C:\\Users\\LTO\\Desktop\\lib jar\\yassine2.pdf";
			// create a new pdf document
			Document document = new Document();
			PdfWriter.getInstance(document, new FileOutputStream(file_name));
//open the pdf document
			document.open();

			/*
			 * Paragraph para1=new Paragraph("This is the first paragraph"); Paragraph
			 * para2=new Paragraph("This is the seconde paragraph"); Paragraph para3=new
			 * Paragraph("This is the thirst paragraph"); Paragraph para4=new
			 * Paragraph("This is the fourth paragraph"); Paragraph para5=new
			 * Paragraph("This is the fifth paragraph");
			 * 
			 * document.add(para1); document.add(para2); document.add(para3);
			 * document.add(para4); document.add(para5);
			 */
			// add table to pdf
			/*
			 * PdfPTable table=new PdfPTable(3); PdfPCell cell1=new PdfPCell(new
			 * Phrase("Headin1")); PdfPCell cell2=new PdfPCell(new Phrase("Headin2"));
			 * PdfPCell cell3=new PdfPCell(new Phrase("Headin3"));
			 * 
			 * table.addCell(cell1); table.addCell(cell2); table.addCell(cell3);
			 * table.setHeaderRows(1);
			 * 
			 * table.addCell("1.0"); table.addCell("1.2"); table.addCell("1.6");
			 * table.addCell("1.9"); table.addCell("2.4"); table.addCell("3.0");
			 * 
			 * document.add(table);
			 */

			/*
			 * ArrayList<Paragraph> arrayParagraphes = new ArrayList<Paragraph>(); //
			 * parcour the vbox all children for (int i = 2; i <
			 * vBoxPrincipale.getChildren().size(); i++) { VBox medicationToPrint = (VBox)
			 * vBoxPrincipale.getChildren().get(i);
			 * 
			 * // System.out.println(((Text) //
			 * medicationToPrint.getChildren().get(0)).getText());
			 * 
			 * HBox hbox1 = (HBox) medicationToPrint.getChildren().get(1); HBox hbox2 =
			 * (HBox) medicationToPrint.getChildren().get(2);
			 * 
			 * String tfMedPDF = ((TextField) hbox1.getChildren().get(0)).getText(); String
			 * t1MedPDF = ((Text) hbox1.getChildren().get(1)).getText(); String t2MedPDF =
			 * ((Text) hbox2.getChildren().get(0)).getText(); String t3MedPDF = ((Text)
			 * hbox2.getChildren().get(1)).getText();
			 * 
			 * arrayParagraphes.add(new Paragraph(tfMedPDF + " : " + t1MedPDF + " : " +
			 * t2MedPDF + " : " + t3MedPDF));
			 * 
			 * }
			 * 
			 * // document.addTitle("ÙˆØµÙ�Ø© Ø¯ÙˆØ§Ø¡"); // document.addHeader(arg0, arg1)
			 * //
			 * document.addSubject("Ø§Ù„Ø£Ø¯ÙˆÙŠØ© Ø§Ù„Ù…Ø³ØªØ¹Ù…Ù„Ø© Ù�ÙŠ Ø§Ù„Ø¹Ù„Ø§Ø¬ ");
			 * for (Paragraph paragraph : arrayParagraphes) { document.add(paragraph);
			 * 
			 * } document.close();
			 */

			/*
			 * addMetaData(document); addTitlePage(document); addContent(document);
			 */
			document.close();

			// System.out.println()

			System.out.println("Finished");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// iText allows to add metadata to the PDF which can be viewed in your Adobe
	// Reader
	// under File -> Properties
	private void addMetaData(Document document, String title) {
		document.addTitle(title);
		document.addSubject("Cabinet médicale");
		document.addKeywords("cabinet médicale,cabinet,pdf");
		document.addAuthor("Atik Yassine");
		document.addCreator("Atik Yassine");
	}

	private void addTitlePage(Document document) throws DocumentException {
		Paragraph preface = new Paragraph();
		// We add one empty line ===> add Ù‚Ù�Ø²Ø©
		addEmptyLine(preface, 1);
		// Lets write a big header
		preface.add(new Paragraph("Title of the document", catFont));

		addEmptyLine(preface, 1);
		// Will create: Report generated by: _name, _date
		preface.add(new Paragraph("Report generated by: " + System.getProperty("user.name") + ", " + new Date(), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
				smallBold));
		addEmptyLine(preface, 3);
		preface.add(new Paragraph("This document describes something which is very important ", smallBold));

		addEmptyLine(preface, 8);

		preface.add(new Paragraph(
				"This document is a preliminary version and not subject to your license agreement or any other agreement with vogella.com ;-).",
				redFont));

		document.add(preface);
		// Start a new page
		document.newPage();
	}

	private void addContent(Document document) throws DocumentException {
		Anchor anchor = new Anchor("First Chapter", catFont);
		anchor.setName("First Chapter");

		// Second parameter is the number of the chapter
		Chapter catPart = new Chapter(new Paragraph(anchor), 1);

		Paragraph subPara = new Paragraph("Subcategory 1", subFont);
		Section subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("Hello"));

		subPara = new Paragraph("Subcategory 2", subFont);
		subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("Paragraph 1"));
		subCatPart.add(new Paragraph("Paragraph 2"));
		subCatPart.add(new Paragraph("Paragraph 3"));

		// add a list
		createList(subCatPart);
		Paragraph paragraph = new Paragraph();
		addEmptyLine(paragraph, 5);
		subCatPart.add(paragraph);

		// add a table
		createTable(subCatPart);

		// now add all this to the document
		document.add(catPart);

		// Next section
		anchor = new Anchor("Second Chapter", catFont);
		anchor.setName("Second Chapter");

		// Second parameter is the number of the chapter
		catPart = new Chapter(new Paragraph(anchor), 1);

		subPara = new Paragraph("Subcategory", subFont);
		subCatPart = catPart.addSection(subPara);
		subCatPart.add(new Paragraph("This is a very important message"));

		// now add all this to the document
		document.add(catPart);

	}

	private void createTable(Section subCatPart) throws BadElementException {
		PdfPTable table = new PdfPTable(3);

		// t.setBorderColor(BaseColor.GRAY);
		// t.setPadding(4);
		// t.setSpacing(4);
		// t.setBorderWidth(1);

		PdfPCell c1 = new PdfPCell(new Phrase("Table Header 1"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 2"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase("Table Header 3"));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		table.addCell(c1);
		table.setHeaderRows(1);

		table.addCell("1.0");
		table.addCell("1.1");
		table.addCell("1.2");
		table.addCell("2.1");
		table.addCell("2.2");
		table.addCell("2.3");

		subCatPart.add(table);

	}

	private void createList(Section subCatPart) {
		List list = new List(true, false, 10);
		list.add(new ListItem("First point"));
		list.add(new ListItem("Second point"));
		list.add(new ListItem("Third point"));
		subCatPart.add(list);
	}

	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

	@FXML
	private VBox vBoxPrincipaleBise;

	@FXML
	void addMedication(ActionEvent event) {
//System.out.println("Pricipale");
		try {
			VBox toor = FXMLLoader.load(getClass().getResource("/application/views/Secondary.fxml"));
			JFXButton btn1 = new JFXButton("SUPPRIMR");
			btn1.setStyle("-fx-background-color: red");

			this.vBoxPrincipaleBise.getChildren().addAll(btn1, toor);
			new BounceIn(btn1).play();
			new BounceIn(toor).play();
			btn1.setOnAction(evt -> {

				this.vBoxPrincipaleBise.getChildren().remove(toor);
				this.vBoxPrincipaleBise.getChildren().remove(btn1);

			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void pressButton(KeyEvent event) {
		switch (event.getCode()) {
		case ADD:
			this.addMedication(null);
			break;

		default:
			break;
		}
	}

	@FXML
	public Button dec;

	// deconnect
	@FXML
	public void deconnect(ActionEvent event) {
		Stage stage = (Stage) dec.getScene().getWindow();
		stage.close();

		Stage primaryStage = new Stage();
		// primaryStage.initStyle(StageStyle.TRANSPARENT);
		Parent root;

		try {
			// HomeController homeController = new
			// HomeController(rs.getString("nom"),rs.getInt("user_id"));

			// FXMLLoader fxmlLoader = new
			// FXMLLoader(getClass().getResource("../../views/login/Login.fxml"));
			root = FXMLLoader.load(getClass().getResource("/application/views/login/Login.fxml"));
			// System.out.println("nom : "+rs.getString("nom")+" id :
			// "+rs.getInt("user_id"));
			/*
			 * request="INSERT INTO historic_users(user_id,date_heur,action) " +
			 * "VALUES('"+rs.getInt("user_id")+"', '"+myDate.format(myFormat)+
			 * "','Connexion')"; stm.execute(request);
			 */

			// root = (Parent) fxmlLoader.load();

			primaryStage.initStyle(StageStyle.TRANSPARENT);
			Scene scene = new Scene(root);
			scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ***********************Begin Redaction
	// Dashboard*******************************
	@FXML
	private Button btnOrdonnance;
	@FXML
	private Button btnJustification;
	@FXML
	private Button btnStopJob;
	@FXML
	private Button btnFreeRedaction;
	@FXML
	private Pane pnlStatus;
	@FXML
	private AnchorPane aPOrdonnance;
	@FXML
	private AnchorPane aPJustification;
	@FXML
	private AnchorPane apStopJob;
	@FXML
	private Label lblStatusMini;
	@FXML
	private Label lblStatus;

	@FXML
	private AnchorPane apFreeRedaction;

	@FXML
	void handleRedactionDashboard(ActionEvent event) {

		if (event.getSource() == btnOrdonnance) {
			// System.out.println("handleRedactionDashboard");
			// btnOrdonnance.setBackground(new Background(new
			// BackgroundFill(Color.rgb(63,43,99),CornerRadii.EMPTY,Insets.EMPTY)));
			aPOrdonnance.setVisible(true);
			aPJustification.setVisible(false);
			apStopJob.setVisible(false);
			apFreeRedaction.setVisible(false);
			lblStatusMini.setText("/Redaction/Ordonnance");
			lblStatus.setText("Ordonnance");

		} else if (event.getSource() == btnJustification) {
			aPJustification.setVisible(true);
			aPOrdonnance.setVisible(false);
			apStopJob.setVisible(false);
			apFreeRedaction.setVisible(false);
			lblStatusMini.setText("/Redaction/Justification");
			lblStatus.setText("Justification");
		} else if (event.getSource() == btnStopJob) {
			apStopJob.setVisible(true);
			aPOrdonnance.setVisible(false);
			aPJustification.setVisible(false);
			apFreeRedaction.setVisible(false);
			lblStatusMini.setText("/Redaction/ArrÃªt de travail");
			lblStatus.setText("ArrÃªt de travail");
		} else {
			apFreeRedaction.setVisible(true);
			apStopJob.setVisible(false);
			aPOrdonnance.setVisible(false);
			aPJustification.setVisible(false);
			lblStatusMini.setText("/Redaction/Rédaction libre");
			lblStatus.setText("Rédaction Libre");
		}
	}
	// ************************End Redaction
	// Dashboard*******************************

	@FXML
	void mouseEnteredTask(MouseEvent event) {
		System.out.println("entered tasks");
	}

	@FXML
	void showAgeStatistics(MouseEvent event) throws IOException {

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		// TacheController controller = new TacheController(this.userId);
		// FXMLLoader root;
		Parent root;

		root = FXMLLoader.load(getClass().getResource("../views/statistics/ageStatistic.fxml"));
		System.out.println("atik");
		// root.setController(controller);
		// parent = root.load();
		Scene scene = new Scene(root);
		// application.controllers.AddPatientController
		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.showAndWait();

	}

	// ########################################BEGIN PARAMETER
	// VIEW###################

	@FXML
	private JFXTextField nameL;

	@FXML
	private JFXTextField nameAR;

	@FXML
	private JFXTextField specialityL;

	@FXML
	private JFXTextField specialityAR;

	@FXML
	private JFXTextField address;

	@FXML
	private JFXTextField municipality;

	@FXML
	private JFXComboBox<String> town;

	@FXML
	private JFXTextField phone;

	@FXML
	private JFXTextField mail;

	@FXML
	private JFXPasswordField oldPassword;

	@FXML
	private JFXPasswordField passwordConfiramation;

	@FXML
	private JFXPasswordField newPassword;

	@FXML
	void saveParameters(ActionEvent event) throws Exception {

		// launch the request

		String name = this.nameL.getText();
		String nameAR = this.nameAR.getText();
		String spec = this.specialityL.getText();
		String specAR = this.specialityAR.getText();
		String address = this.address.getText();
		String munisipality = this.municipality.getText();
		String town = this.town.getSelectionModel().getSelectedItem();
		String phone = this.phone.getText();
		String mail = this.mail.getText();
		String oldPassword = this.oldPassword.getText();
		String newPassword = this.newPassword.getText();
		String confirmPassword = this.passwordConfiramation.getText();
		Boolean setSelected = saveAuthToggleButton.isSelected();

		if (!Cryptage.cryptageSha(oldPassword).equals(this.oldPasswordS)) {

			// The old password is incorrect
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Echec"));
			layout.setBody(new Text("Ton ancinen mot de passe est faux"));

			JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);

			JFXButton ok = new JFXButton("Annuler");
			ok.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					dialog.close();

				}
			});

			layout.setActions(ok);
			dialog.show();

		} else {
			// Check if newPassword and confirmPassword are equale
			if (!Cryptage.cryptageSha(newPassword).equals(Cryptage.cryptageSha(confirmPassword))) {
				// The old password is incorrect
				JFXDialogLayout layout = new JFXDialogLayout();
				layout.setHeading(new Text("Echec"));
				layout.setBody(new Text("Le nouveau mot de passe et confirme mot de passe sont pas les mÃªmes"));

				JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);

				JFXButton ok = new JFXButton("Annuler");
				ok.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						dialog.close();

					}
				});

				layout.setActions(ok);
				dialog.show();

			} else {
				// Update the Parameters
				ResultSet rs = null;
				PreparedStatement stm = null;
				String request;
				try {
					if (!newPassword.equals("")) {
						request = "UPDATE  users SET nom_fr=?,nom_ar=?,specialty=?,specialty_ar=?,address=?,municipality=?,town=?,phone=?,mail=?,password=?,if_auth=?,password_clear=? "
								+ "WHERE user_id=?";

						System.out.println("1");
						stm = sl.getConnection().prepareStatement(request);
						System.out.println("2");

						stm.setInt(13, this.userId);
						stm.setString(1, name);
						stm.setString(2, nameAR);
						stm.setString(3, spec);
						stm.setString(4, specAR);
						stm.setString(5, address);
						stm.setString(6, munisipality);
						stm.setString(7, this.town.getSelectionModel().getSelectedItem());
						stm.setString(8, phone);
						stm.setString(9, mail);
						stm.setString(10, Cryptage.cryptageSha(newPassword));
						stm.setBoolean(11, setSelected);
						stm.setString(12, newPassword);

						stm.executeUpdate();
					} else {

						request = "UPDATE  users SET nom_fr=?,nom_ar=?,specialty=?,specialty_ar=?,address=?,municipality=?,town=?,phone=?,mail=?,if_auth=? "
								+ "WHERE user_id=?";

						System.out.println("1");
						stm = sl.getConnection().prepareStatement(request);
						System.out.println("2");

						stm.setString(1, name);
						stm.setString(2, nameAR);
						stm.setString(3, spec);
						stm.setString(4, specAR);
						stm.setString(5, address);
						stm.setString(6, munisipality);
						stm.setString(7, this.town.getSelectionModel().getSelectedItem());
						stm.setString(8, phone);
						stm.setString(9, mail);
						stm.setBoolean(10, setSelected);
						stm.setInt(11, this.userId);

						stm.executeUpdate();
					}

					this.oldPassword.setText("");
					this.newPassword.setText("");
					this.passwordConfiramation.setText("");

					System.out.println("3");
					JFXDialogLayout layout = new JFXDialogLayout();
					layout.setHeading(new Text("Enregistrement des paramÃ¨tres"));
					layout.setBody(new Text("Vos paramÃ¨tres sont enregistrées avec succés"));

					JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.CENTER);

					JFXButton ok = new JFXButton("OK");
					ok.setOnAction(new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							dialog.close();

						}
					});

					layout.setActions(ok);
					dialog.show();

					// this.refreshParameters();

				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}
		// String password=rs.getString(11);

	}

	// ########################################END PARAMETER VIEW###################

	// #####################################BEGIN AGENDA VIEW#####################

	@FXML
	private JFXTextField tJFAppointment;

	@FXML
	private TableView<Appointment> tabAppointment;

	@FXML
	private JFXDatePicker dLAppointment;

	@FXML
	void filterAppointment(KeyEvent event) {

		// initialiser la liste au début par tout les malades
		ArrayList<Appointment> reduceAppointments = new ArrayList<Appointment>();

		// System.out.println(arrayRecherche.get(1).getNom());
		// séléctionner depuis la date d'aujourdhui
		if (tJFAppointment.getText().equals("")) {

			this.tabAppointment.setItems(FXCollections.observableArrayList(this.arrayListAppointment));

			// il fait la recherche
		} else {
			for (Appointment appointment : this.arrayListAppointment) {
				if (appointment.getFullName().contains(tJFAppointment.getText())) {
					reduceAppointments.add(appointment);
				}
			}
			this.tabAppointment.setItems(FXCollections.observableArrayList(reduceAppointments));
		}

	}

	@FXML
	void addAppointment(MouseEvent event) {

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		// AddPatientController controller = new AddPatientController(this.userId);
		FXMLLoader root;
		Parent parent;

		try {
			root = new FXMLLoader(getClass().getResource("/application/views/appointment/appointment.fxml"));
			System.out.println("atik");
			// root.setController(controller);
			parent = root.load();
			Scene scene = new Scene(parent);

			// Add listner for close the window

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					if (ke.getCode() == KeyCode.ESCAPE) {
						AppointmentController.setSwitcher(0);
						primaryStage.close();
					}
				}
			});
			///////////////// End//////////////

//application.controllers.AddPatientController
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.showAndWait();

			if (AppointmentController.switcher == 1) {
				JFXDialogLayout layout = new JFXDialogLayout();
				layout.setHeading(new Text("Ajout d'un rendez-vous"));
				layout.setBody(new Text("L'ajout du rendez a été accompli avec succés"));

				JFXDialog dialog = new JFXDialog(stackPane, layout, JFXDialog.DialogTransition.RIGHT);

				JFXButton ok = new JFXButton("OK");
				ok.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						dialog.close();

					}
				});

				layout.setActions(ok);
				dialog.show();

				this.refreshTabAppointment();
				this.selectDateAppointment(null);

				/*
				 * try { //this.fillAppointmentTimerTask(); } catch (ParseException e) { // TODO
				 * Auto-generated catch block e.printStackTrace(); }
				 */
			}
			// refreshList();

		}
		// primaryStage.show();
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// fill the tabAppointment
	private ArrayList<Appointment> arrayListAppointment = new ArrayList<Appointment>();
	public static Hashtable<String, String> hashTableAppointment;

	@FXML
	void selectDateAppointment(ActionEvent event) {
		// ObservableList<Malade> arrayRecherche = allMalade;
		ArrayList<Appointment> reduceAppointments = new ArrayList<Appointment>();

		String dateSelected = this.dLAppointment.getValue().toString();

		for (Appointment appointment : this.arrayListAppointment) {
			if (appointment.getDate().equals(dateSelected)) {
				reduceAppointments.add(appointment);

			}
		}
		this.tabAppointment.setItems(FXCollections.observableArrayList(reduceAppointments));

	}

	public void refreshTabAppointment() {

		this.arrayListAppointment.clear();
		hashTableAppointment = new Hashtable<String, String>();
		Statement stm = null;
		ResultSet rs = null;

		try {
			stm = new SQLiteJDBC().getConnectionAppointment().createStatement();
			String request = "SELECT * FROM appointment ORDER BY appointment_date,appointment_hour ASC";
			rs = stm.executeQuery(request);

			// int cmpt = 1;

			while (rs.next()) {
				Appointment ap = new Appointment(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
						rs.getString(5));
				arrayListAppointment.add(ap);
				hashTableAppointment.put(ap.getDate() + " " + ap.getHour(), ap.getFullName());
				tabAppointment.getColumns().clear();
				this.initializeTabAppointment();
				this.toggleButtonWeek.setSelected(false);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void initializeTabAppointment() {

		TableColumn<Appointment, String> dateAppColumn = new TableColumn<Appointment, String>("Date");
		TableColumn<Appointment, String> hourAppColumn = new TableColumn<Appointment, String>("Heure");
		TableColumn<Appointment, String> nameAppColumn = new TableColumn<Appointment, String>("Nom & Prénom");
		TableColumn<Appointment, String> phoneAppColumn = new TableColumn<Appointment, String>("Téléphone");

		dateAppColumn.setCellValueFactory(new PropertyValueFactory("date"));
		hourAppColumn.setCellValueFactory(new PropertyValueFactory("hour"));
		nameAppColumn.setCellValueFactory(new PropertyValueFactory("fullName"));
		phoneAppColumn.setCellValueFactory(new PropertyValueFactory("phone"));

		// the set width of column
		// idColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.1));
		dateAppColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.3));
		hourAppColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.2));
		nameAppColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.4));
		phoneAppColumn.prefWidthProperty().bind(tabPatient.widthProperty().multiply(0.3));

		dateAppColumn.setResizable(false);
		hourAppColumn.setResizable(false);
		nameAppColumn.setResizable(false);
		phoneAppColumn.setResizable(false);
		tabAppointment.getColumns().addAll(dateAppColumn, hourAppColumn, nameAppColumn, phoneAppColumn);
		// tabAppointment.getColumns().addAll(dateAppColumn, hourAppColumn,
		// nameAppColumn, phoneAppColumn);

	}

// #####################################BEGIN AGENDA VIEW#####################

	// *********************************BEGIN SPLASH SCREEN*******************

	private void loatSplashScreen() {

		try {
			StackPane pane = FXMLLoader.load(getClass().getResource("/application/views/splashScreen.fxml"));
			stackPane.getChildren().setAll(pane);

			// I w'll create two fade transition 1->inTrasition 2->outTrinsition
			// 1)fadeIN
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(3), pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			fadeIn.play();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// #####################################END SPLASH SCREEN#####################

	@FXML
	private void updatePatientData(MouseEvent event) {

		selectedMalade = tabPatient.getSelectionModel().getSelectedItem();
		if (selectedMalade == null) {

			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Aucun malade séléctionner"));
			layout.setBody(new Text("Il faut séléctionner un malade"));

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
		} else {
			System.out.println("Selected Malade: " + selectedMalade.getNom());
			Stage primaryStage = new Stage();
			primaryStage.initStyle(StageStyle.UNDECORATED);
			AddPatientController controller = new AddPatientController(this.selectedMalade);
			FXMLLoader root;
			Parent parent;

			try {
				root = new FXMLLoader(getClass().getResource("/application/views/addPatient.fxml"));

				root.setController(controller);
				parent = root.load();
				Scene scene = new Scene(parent);

				// Add listner for close the window

				scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
					@Override
					public void handle(KeyEvent ke) {
						if (ke.getCode() == KeyCode.ESCAPE) {
							primaryStage.close();
						}

						if (new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_ANY).match(ke)) {
							System.out.println("Work for me");
							controller.setSelectCheckBoxMale(null);
						}
						if (new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_ANY).match(ke)) {
							System.out.println("Work for me");
							controller.setSelectCheckBoxFemale(null);
						}
					}
				});
				///////////////// End//////////////

				// application.controllers.AddPatientController
				// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.showAndWait();
				// System.out.println("switcher "+FicheMaladeController.getSwitcher());
				if (controller.getSwitcher() == 1) {

					JFXDialogLayout layout = new JFXDialogLayout();
					layout.setHeading(new Text("Succés"));
					layout.setBody(new Text("L'affiche a était modifier avec succés"));

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
					refreshList();
				}

			} catch (Exception e) {
				// TODO: handle exception
			}

		}

	}
	//Open window that show us Expense/Profit graph
	@FXML
	public void displayExpenseProfit(MouseEvent event) {
		

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		FXMLLoader root;
		Parent parent;

		try {

			root = new FXMLLoader(getClass().getResource("/application/views/statistics/expenseProfit.fxml"));

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

	@FXML
	public void displayNbrPatient(MouseEvent event) {

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		String labID = this.cbLab.getSelectionModel().getSelectedItem();
		FXMLLoader root;
		Parent parent;

		try {

			root = new FXMLLoader(getClass().getResource("/application/views/statistics/ageStatistic.fxml"));

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

	@FXML
	public void openBenefit(MouseEvent event) {

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		String labID = this.cbLab.getSelectionModel().getSelectedItem();
		FXMLLoader root;
		Parent parent;

		try {

			root = new FXMLLoader(getClass().getResource("/application/views/statistics/benefit.fxml"));

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

	@FXML
	public void addRecordShuttle(MouseEvent event) {
		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		String labID = this.cbLab.getSelectionModel().getSelectedItem();

		try {
			RecordShuttleController controller = new RecordShuttleController(
					this.labDetailleHashTable.get(labID).intValue(), this.patientListComplete);
			FXMLLoader root;
			Parent parent;
			root = new FXMLLoader(getClass().getResource("/application/views/prothese/recordShuttle.fxml"));

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
			// application.controllers.AddPatientController
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch (Exception e) {
			// TODO: handle exception

			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("ECHEC"));
			layout.setBody(new Text("Il faut séléctionner le labo"));

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

	}

	@FXML
	public void filterRecordShuttle(MouseEvent event) {
		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		String labID = this.cbLab.getSelectionModel().getSelectedItem();
		FXMLLoader root;
		Parent parent;

		try {

			root = new FXMLLoader(getClass().getResource("/application/views/prothese/recordFilter.fxml"));

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

	@FXML
	public void comptabiliterProthese(MouseEvent event) {
		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		String labID = this.cbLab.getSelectionModel().getSelectedItem();
		FXMLLoader root;
		Parent parent;

		try {

			root = new FXMLLoader(getClass().getResource("/application/views/prothese/bill.fxml"));

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

	@FXML
	private JFXToggleButton toggleButtonWeek;

	Hashtable<String, String> daysInFrench = new Hashtable<String, String>();

	@FXML
	void visualiseCalendar(ActionEvent event) {

		if (this.toggleButtonWeek.isSelected()) {
			this.initialize7DaysTabelView();
		} else {
			tabAppointment.getColumns().clear();
			this.initializeTabAppointment();
		}
	}

	private void initialize7DaysTabelView() {
		daysInFrench.put("SATURDAY", "SAMEDI");
		daysInFrench.put("SUNDAY", "DIMANCHE");
		daysInFrench.put("MONDAY", "LUNDI");
		daysInFrench.put("TUESDAY", "MARDI");
		daysInFrench.put("WEDNESDAY", "MERCREDI");
		daysInFrench.put("THURSDAY", "JEUDI");
		daysInFrench.put("FRIDAY", "VENDREDI");

		System.out.println(this.toggleButtonWeek.isSelected());
		/*
		 * To remove all data:
		 * 
		 * eventsTableView.getItems().clear(); To remove all columns:
		 * 
		 * eventsTableView.getColumns().clear();
		 * 
		 */
		tabAppointment.getColumns().clear();

		// LocalDate today = LocalDate.now();
		// LocalDate tomorrow = today.plusDays(1);

		// In string format
		LocalDate todaye = LocalDate.now();
		String saturday = // (todaye.plusDays(1)).format(DateTimeFormatter.ISO_DATE)
				todaye.plusDays(1).getDayOfWeek().toString();
		String sunday = todaye.plusDays(2).getDayOfWeek().toString();// (todaye.plusDays(2)).format(DateTimeFormatter.ISO_DATE);
		String monday = todaye.plusDays(3).getDayOfWeek().toString();// (todaye.plusDays(3)).format(DateTimeFormatter.ISO_DATE);
		String tuesday = todaye.plusDays(4).getDayOfWeek().toString();// (todaye.plusDays(4)).format(DateTimeFormatter.ISO_DATE);
		String wendsday = todaye.plusDays(5).getDayOfWeek().toString();// (todaye.plusDays(5)).format(DateTimeFormatter.ISO_DATE);
		String thursday = todaye.plusDays(6).getDayOfWeek().toString();// (todaye.plusDays(6)).format(DateTimeFormatter.ISO_DATE);
		String friday = todaye.plusDays(7).getDayOfWeek().toString();// (todaye.plusDays(7)).format(DateTimeFormatter.ISO_DATE);

		// String after30 = (todaye.plusDays(30)).format(DateTimeFormatter.ISO_DATE);

		System.out.println(saturday);
		System.out.println(sunday);
		System.out.println(monday);
		System.out.println(tuesday);
		System.out.println(wendsday);
		System.out.println(thursday);
		System.out.println(friday);

		TableColumn<Appointment, String> dateSaturday = new TableColumn<Appointment, String>(
				daysInFrench.get(saturday));
		TableColumn<Appointment, String> lastNameSaturday = new TableColumn<Appointment, String>("Heure");
		TableColumn<Appointment, String> firstNameSaturday = new TableColumn<Appointment, String>("Nom");

		TableColumn<Appointment, String> dateSunday = new TableColumn<Appointment, String>(daysInFrench.get(sunday));
		TableColumn<Appointment, String> lastNameSunday = new TableColumn<Appointment, String>("Heure");
		TableColumn<Appointment, String> firstNameSunday = new TableColumn<Appointment, String>("Nom");

		TableColumn<Appointment, String> dateMonday = new TableColumn<Appointment, String>(daysInFrench.get(monday));
		TableColumn<Appointment, String> lastNameMonday = new TableColumn<Appointment, String>("Heure");
		TableColumn<Appointment, String> firstNameMonday = new TableColumn<Appointment, String>("Nom");

		TableColumn<Appointment, String> dateTuesday = new TableColumn<Appointment, String>(daysInFrench.get(tuesday));
		TableColumn<Appointment, String> lastNameTuesday = new TableColumn<Appointment, String>("Heure");
		TableColumn<Appointment, String> firstNameTuesday = new TableColumn<Appointment, String>("Nom");

		TableColumn<Appointment, String> dateWendsday = new TableColumn<Appointment, String>(
				daysInFrench.get(wendsday));
		TableColumn<Appointment, String> lastNameWendsday = new TableColumn<Appointment, String>("Heure");
		TableColumn<Appointment, String> firstNameWendsday = new TableColumn<Appointment, String>("Nom");

		TableColumn<Appointment, String> dateThursday = new TableColumn<Appointment, String>(
				daysInFrench.get(thursday));
		TableColumn<Appointment, String> lastNameThursday = new TableColumn<Appointment, String>("Heure");
		TableColumn<Appointment, String> firstNameThursday = new TableColumn<Appointment, String>("Nom");

		TableColumn<Appointment, String> dateFriday = new TableColumn<Appointment, String>(daysInFrench.get(friday));
		TableColumn<Appointment, String> lastNameFriday = new TableColumn<Appointment, String>("Heure");
		TableColumn<Appointment, String> firstNameFriday = new TableColumn<Appointment, String>("Nom");

		dateSaturday.getColumns().addAll(lastNameSaturday, firstNameSaturday);
		dateSunday.getColumns().addAll(lastNameSunday, firstNameSunday);

		dateMonday.getColumns().addAll(lastNameMonday, firstNameMonday);
		dateTuesday.getColumns().addAll(lastNameTuesday, firstNameTuesday);

		dateWendsday.getColumns().addAll(lastNameWendsday, firstNameWendsday);
		dateThursday.getColumns().addAll(lastNameThursday, firstNameThursday);

		dateFriday.getColumns().addAll(lastNameFriday, firstNameFriday);

		lastNameSaturday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.15));
		firstNameSaturday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.2));

		lastNameSunday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.15));
		firstNameSunday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.2));

		lastNameMonday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.15));
		firstNameMonday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.2));

		lastNameTuesday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.15));
		firstNameTuesday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.2));

		lastNameWendsday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.15));
		firstNameWendsday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.2));

		lastNameThursday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.15));
		firstNameThursday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.2));

		lastNameFriday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.15));
		firstNameFriday.prefWidthProperty().bind(tabAppointment.widthProperty().multiply(0.2));

		lastNameSaturday.setCellValueFactory(new PropertyValueFactory("date1"));
		firstNameSaturday.setCellValueFactory(new PropertyValueFactory("hour1"));

		lastNameSunday.setCellValueFactory(new PropertyValueFactory("date2"));
		firstNameSunday.setCellValueFactory(new PropertyValueFactory("hour2"));

		lastNameMonday.setCellValueFactory(new PropertyValueFactory("date3"));
		firstNameMonday.setCellValueFactory(new PropertyValueFactory("hour3"));

		lastNameTuesday.setCellValueFactory(new PropertyValueFactory("date4"));
		firstNameTuesday.setCellValueFactory(new PropertyValueFactory("hour4"));

		lastNameWendsday.setCellValueFactory(new PropertyValueFactory("date5"));
		firstNameWendsday.setCellValueFactory(new PropertyValueFactory("hour5"));

		lastNameThursday.setCellValueFactory(new PropertyValueFactory("date6"));
		firstNameThursday.setCellValueFactory(new PropertyValueFactory("hour6"));

		lastNameFriday.setCellValueFactory(new PropertyValueFactory("date7"));
		firstNameFriday.setCellValueFactory(new PropertyValueFactory("hour7"));

		tabAppointment.getColumns().addAll(dateSaturday, dateSunday, dateMonday, dateTuesday, dateWendsday,
				dateThursday, dateFriday);

		ArrayList<Appointment> productsArray = new ArrayList<Appointment>();

		Appointment saturday1 = new Appointment("09:15", "Atiko");
		Appointment sunday1 = new Appointment("09:25", "Atiko123");
		Appointment monday1 = new Appointment("09:35", "Atiko Fares");
		Appointment tuesday1 = new Appointment("09:45", "Kachi");
		Appointment wendsday1 = new Appointment("09:55", "Sachi");
		Appointment thursday1 = new Appointment("10:05", "Torchi");
		Appointment friday1 = new Appointment("12:15", "Marchi");
		Appointment ap = new Appointment(saturday1.getHour(), saturday1.getFullName(), sunday1.getHour(),
				sunday1.getFullName(), monday1.getHour(), monday1.getFullName(), tuesday1.getHour(),
				tuesday1.getFullName(), wendsday1.getHour(), wendsday1.getFullName(), thursday1.getHour(),
				thursday1.getFullName(), friday1.getHour(), friday1.getFullName());
		productsArray.add(ap);

		this.fillTablewithAndWithoutAppointement();

		this.realFillTableView();

		tabAppointment.setItems(FXCollections.observableArrayList(appointementArray7Days));

	}

	private ArrayList<Appointment> appointementArray7Days;

	private void realFillTableView() {

		appointementArray7Days = new ArrayList<Appointment>();
		LocalDate todaye = LocalDate.now();
		String saturday = (todaye.plusDays(1)).format(DateTimeFormatter.ISO_DATE);

		String sunday = (todaye.plusDays(2)).format(DateTimeFormatter.ISO_DATE);
		String monday = (todaye.plusDays(3)).format(DateTimeFormatter.ISO_DATE);
		String tuesday = (todaye.plusDays(4)).format(DateTimeFormatter.ISO_DATE);
		String wendsday = (todaye.plusDays(5)).format(DateTimeFormatter.ISO_DATE);
		String thursday = (todaye.plusDays(6)).format(DateTimeFormatter.ISO_DATE);
		String friday = (todaye.plusDays(7)).format(DateTimeFormatter.ISO_DATE);

		String[] allDays = new String[7];

		allDays[0] = saturday;
		allDays[1] = sunday;
		allDays[2] = monday;
		allDays[3] = tuesday;
		allDays[4] = wendsday;
		allDays[5] = thursday;
		allDays[6] = friday;

		System.out.println("date::::  " + sunday);

		for (String maladeAppointment : appointementDivision) {
			Appointment[] appointments = new Appointment[7];
			int indice = 0;
			for (String allDay : allDays) {
				boolean exist = false;

				for (Appointment app : this.arrayListAppointment) {

					String[] tabSplit = maladeAppointment.split("-");
					System.out.println("Tab sp0 " + tabSplit[0]);
					System.out.println("Tab sp1 " + tabSplit[1]);
					System.out.println("App Date " + app.getDate());
					System.out.println("AllDays " + allDay);

					if (app.getDate().equals(allDay) && app.getHour().compareTo(tabSplit[0]) >= 0
							&& app.getHour().compareTo(tabSplit[1]) <= 0) {
						System.out.println("app   " + app.getHour() + " appName " + app.getFullName());

						app.setHour(maladeAppointment);
						appointments[indice] = app;
						indice++;
						exist = true;
						break;

					}
				}

				if (!exist) {
					Appointment ap = new Appointment(maladeAppointment, "------------");
					// ap.setFullName("------------");
					appointments[indice] = ap;
					indice++;
				}
			}

			fllAppointementArray7Days(appointments);

		}

	}

	private void fllAppointementArray7Days(Appointment[] appointments) {

		appointementArray7Days.add(new Appointment(appointments[0].getHour(), appointments[0].getFullName(),
				appointments[1].getHour(), appointments[1].getFullName(), appointments[2].getHour(),
				appointments[2].getFullName(), appointments[3].getHour(), appointments[3].getFullName(),
				appointments[4].getHour(), appointments[4].getFullName(), appointments[5].getHour(),
				appointments[5].getFullName(), appointments[6].getHour(), appointments[6].getFullName()));
	}

	private final int RANGE = 15;
	private ArrayList<String> appointementDivision;

	private void fillTablewithAndWithoutAppointement() {

		appointementDivision = new ArrayList<String>();
		String begin = "00:00";
		String end = "00:00";
		int hour = 0, count = 0, minute = 0;
		while (!begin.equals("23:45")) {

			count++;
			begin = end;
			minute += RANGE;
			if (count == 4) {
				count = 0;
				hour = (hour + 1) % 24;
				minute = 0;

			}

			if (hour / 10 == 0) {
				if (minute == 0) {
					end = "0" + hour + ":00";
				} else {
					end = "0" + hour + ":" + minute;
				}

			} else {
				if (minute == 0) {

					end = hour + ":00";
				} else {
					end = hour + ":" + minute;
				}

			}
			// System.out.println(begin+"-"+end);
			appointementDivision.add(begin + "-" + end);
		}

	}

	public static void redactionFromFicheMalade() {

	}

	@FXML
	public void displayNbrSexePatient(MouseEvent event) {

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		String labID = this.cbLab.getSelectionModel().getSelectedItem();
		FXMLLoader root;
		Parent parent;

		try {

			root = new FXMLLoader(getClass().getResource("/application/views/statistics/nbrSexe.fxml"));

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

	@FXML
	public void showExpense(MouseEvent event) {

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.UNDECORATED);
		// AddPatientController controller = new AddPatientController(this.userId);
		FXMLLoader root;
		Parent parent;

		try {
			root = new FXMLLoader(getClass().getResource("/application/views/expense/expense.fxml"));
			System.out.println("atik");
			// root.setController(controller);
			parent = root.load();
			Scene scene = new Scene(parent);

			// Add listner for close the window

			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent ke) {
					if (ke.getCode() == KeyCode.ESCAPE) {
						AppointmentController.setSwitcher(0);
						primaryStage.close();
					}
				}
			});
			///////////////// End//////////////

//application.controllers.AddPatientController
			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.showAndWait();
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	@FXML
	public void showNegatoscope(MouseEvent event) {

		Stage primaryStage = new Stage();
		// primaryStage.initStyle(StageStyle.UNDECORATED);

		AnchorPane root = new AnchorPane();
		String styleDetetBB = String.format("-fx-background: #ffffff;" + "-fx-background-color: -fx-background;");
		root.setStyle(styleDetetBB);
		// root.setBackground(new Background(new BackgroundFill(Color.WHITE,
		// CornerRadii.EMPTY, Insets.EMPTY)));
		/*
		 * ColorAdjust colorAdjust = new ColorAdjust(); colorAdjust.setBrightness(+1.0);
		 * root.setEffect(colorAdjust);
		 */
		Scene scene = new Scene(root);
		primaryStage.setFullScreen(true);

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

	}

	@FXML
	void activation(MouseEvent event) {

		DataBaseConnection db = new DataBaseConnection();

		Hashtable<String, Boolean> hashTableSerials = db.hashTableSerials;

		if (!hashTableSerials.containsKey(this.serial.getText())) {

			this.showDialog("La clé que vous utilisez est érronée");

		} else if (hashTableSerials.get(this.serial.getText())) {

			this.showDialog("La clé a déja été utilisée");
		} else {

			// set serial to true in cloud db
			db.updateSerial(this.serial.getText());

			// set serial to machine in locale
			try {
				Statement stm = new SQLiteJDBC().getConnection().createStatement();
				String request = "INSERT INTO activations(mac_address) VALUES('" + DiskUtils.getSerialNumber("C")
						+ "')";
				stm.execute(request);
				System.out.println("Activations finished Locale");

				try {
					this.openRegisterView();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	private void openRegisterView() throws IOException {

		Stage stage = (Stage) this.stackPane.getScene().getWindow();
		stage.close();

		Stage primaryStage = new Stage();
		String ifLoged = "/application/views/login/Signup.fxml";
		Parent root = FXMLLoader.load(getClass().getResource(ifLoged));
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(root);

		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		// Scene scene = new Scene(root);

		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	private void showDialog(String message) {

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Text("Echec"));
		layout.setBody(new Text(message));

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

	@FXML
	private AnchorPane homeAnchor;

	@FXML
	private AnchorPane redactionAnchor;

	@FXML
	private AnchorPane statisticsAnchor;

	@FXML
	private AnchorPane prothesistAnchor;

	@FXML
	void showHome(MouseEvent event) {
		if (!this.homeAnchor.isVisible()) {
			new BounceInRight(this.homeAnchor).play();
			this.homeAnchor.setVisible(true);
			this.redactionAnchor.setVisible(false);
			this.statisticsAnchor.setVisible(false);
			this.prothesistAnchor.setVisible(false);
			this.agendaAnchor.setVisible(false);
			this.parametersAnchor.setVisible(false);
			// this.homeAnchor.setStyle("-fx-background-color: blue");
			this.homeButton.setId("button-selected");
			this.redactionButton.setId(null);
			this.statisticsButton.setId(null);
			// this.prothesistAnchor.setId(null);
			this.prothesistButton.setId(null);
			this.agendaButton.setId(null);
			this.parametersButton.setId(null);
		}
	}

	@FXML
	void showRedaction(MouseEvent event) {
		if (!this.redactionAnchor.isVisible()) {
			new BounceInRight(this.redactionAnchor).play();
			this.redactionAnchor.setVisible(true);
			this.homeAnchor.setVisible(false);
			this.parametersAnchor.setVisible(false);
			this.statisticsAnchor.setVisible(false);
			this.prothesistAnchor.setVisible(false);
			this.agendaAnchor.setVisible(false);
			this.redactionButton.setId("button-selected");
			this.homeButton.setId(null);
			this.statisticsButton.setId(null);
			this.parametersButton.setId(null);
			this.prothesistButton.setId(null);
			this.agendaButton.setId(null);
		}
	}

	@FXML
	void showStatistics(MouseEvent event) {
		if (!this.statisticsAnchor.isVisible()) {

			new BounceInRight(this.statisticsAnchor).play();
			this.statisticsAnchor.setVisible(true);
			this.homeAnchor.setVisible(false);
			this.redactionAnchor.setVisible(false);
			this.prothesistAnchor.setVisible(false);
			this.agendaAnchor.setVisible(false);
			this.parametersAnchor.setVisible(false);
			this.statisticsButton.setId("button-selected");
			this.homeButton.setId(null);
			this.redactionButton.setId(null);
			this.prothesistAnchor.setId(null);
			this.prothesistButton.setId(null);
			this.agendaButton.setId(null);
			this.parametersButton.setId(null);
		}
	}

	@FXML
	private AnchorPane prothesistButton;

	@FXML
	void showProthesist(MouseEvent event) {

		if (!this.prothesistAnchor.isVisible()) {
			new BounceInRight(this.prothesistAnchor).play();
			this.prothesistAnchor.setVisible(true);
			this.agendaAnchor.setVisible(false);
			this.homeAnchor.setVisible(false);
			this.redactionAnchor.setVisible(false);
			this.agendaAnchor.setVisible(false);
			this.parametersAnchor.setVisible(false);
			this.statisticsAnchor.setVisible(false);
			this.prothesistButton.setId("button-selected");
			this.homeButton.setId(null);
			this.statisticsButton.setId(null);
			this.redactionButton.setId(null);
			this.agendaButton.setId(null);
			this.parametersButton.setId(null);
		}
	}

	@FXML
	private AnchorPane agendaButton;
	@FXML
	private AnchorPane agendaAnchor;

	@FXML
	void showAgenda(MouseEvent event) {

		if (!this.agendaAnchor.isVisible()) {

			new BounceInRight(this.agendaAnchor).play();
			this.agendaAnchor.setVisible(true);
			this.homeAnchor.setVisible(false);
			this.redactionAnchor.setVisible(false);
			this.parametersAnchor.setVisible(false);
			this.statisticsAnchor.setVisible(false);
			this.prothesistAnchor.setVisible(false);
			this.agendaButton.setId("button-selected");
			this.prothesistButton.setId(null);
			this.homeButton.setId(null);
			this.statisticsButton.setId(null);
			this.redactionButton.setId(null);
			this.parametersButton.setId(null);

		}
	}

	@FXML
	private AnchorPane parametersButton;
	@FXML
	private AnchorPane parametersAnchor;

	@FXML
	void showParameters(MouseEvent event) {

		if (!this.parametersAnchor.isVisible()) {
			new BounceInRight(this.parametersAnchor).play();
			this.parametersAnchor.setVisible(true);
			this.homeAnchor.setVisible(false);
			this.redactionAnchor.setVisible(false);
			this.agendaAnchor.setVisible(false);
			this.statisticsAnchor.setVisible(false);
			this.prothesistAnchor.setVisible(false);
			this.prothesistAnchor.setVisible(false);
			this.parametersButton.setId("button-selected");
			this.prothesistButton.setId(null);
			this.homeButton.setId(null);
			this.statisticsButton.setId(null);
			this.redactionButton.setId(null);
			this.agendaButton.setId(null);
		}
	}

	@FXML
	private AnchorPane addPatientAnchor;

	@FXML
	void addPatientEvent(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.addPatientAnchor.getTransforms().add(scale);

	}

	@FXML
	void addPatientExited(MouseEvent event) {
		this.addPatientAnchor.getTransforms().clear();
	}

	// for négatoscope

	@FXML
	private AnchorPane negatoscopeAnchor;

	@FXML
	void negatoscopeEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.negatoscopeAnchor.getTransforms().add(scale);

	}

	@FXML
	void negatoscopeExited(MouseEvent event) {
		this.negatoscopeAnchor.getTransforms().clear();
	}

	// for Expense

	@FXML
	private AnchorPane expanseAnchor;

	@FXML
	void expenseEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.expanseAnchor.getTransforms().add(scale);

	}

	@FXML
	void expenseExited(MouseEvent event) {
		this.expanseAnchor.getTransforms().clear();
	}

	@FXML
	private Pane sliderTasks;

	@FXML
	private BorderPane borderPaneParent;

	@FXML
	private VBox vboxTasksSlider;

	@FXML
	private Label tasksLabel;
	private int nbTasks = 0;

	@FXML
	void showSliderTasks(MouseEvent event) {

		AnchorPane root = null;

		try {
			root = FXMLLoader.load(getClass().getResource("/application/views/taches/tasks.fxml"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// this.vboxTasksSlider.getChildren().add(root);
		new SlideInRight(sliderTasks).play();
		this.sliderTasks.setVisible(true);

		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setBrightness(-0.7);
		borderPaneParent.setEffect(colorAdjust);
		borderPaneParent.setDisable(true);
		// this.sliderTasks.toFront();
	}

	FadeTransition ft = null;

	@FXML
	void cancelSlideTasks(MouseEvent event) {
		ft = new FadeTransition(Duration.millis(500), sliderTasks);

		ft.setToValue(0);
		// ft.setAutoReverse(true);
		ft.setOnFinished(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent actionEvent) {
				sliderTasks.setOpacity(1.0);
				sliderTasks.setVisible(false);

				borderPaneParent.setEffect(null);
				borderPaneParent.setDisable(false);
				// System.out.println("bankkkkkkkkkkk");

			}
		});
		ft.play();

		/*
		 * new FadeOutRight(this.sliderTasks).onFinished(new EventHandler<ActionEvent>()
		 * {
		 * 
		 * @Override public void handle(ActionEvent event) { // TODO Auto-generated
		 * method stub sliderTasks.setVisible(false); } });
		 */

	}

	// statistics
	// 1 Benific
	@FXML
	private AnchorPane showBenific;

	@FXML
	void showBenificEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.showBenific.getTransforms().add(scale);

	}

	@FXML
	void showBenificExited(MouseEvent event) {
		this.showBenific.getTransforms().clear();
	}

	// 2 Sexe / Nbr Patient
	@FXML
	private AnchorPane showNbrPatientGenderAnchor;

	@FXML
	void showNbrPatientGenderEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.showNbrPatientGenderAnchor.getTransforms().add(scale);

	}

	@FXML
	void showNbrPatientGenderExited(MouseEvent event) {
		this.showNbrPatientGenderAnchor.getTransforms().clear();
	}

	// 3 Age / Nbr Patient
	@FXML
	private AnchorPane showNbrPatientAgeAnchor;

	@FXML
	void showNbrPatientAgeEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.showNbrPatientAgeAnchor.getTransforms().add(scale);

	}

	@FXML
	void showNbrPatientAgeExited(MouseEvent event) {
		this.showNbrPatientAgeAnchor.getTransforms().clear();
	}
	
	
	//Expense's statistic
	@FXML
	private AnchorPane  expenseProfitAnchor;
	
	@FXML
	void expenseProfitEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.expenseProfitAnchor.getTransforms().add(scale);

	}

	@FXML
	void expenseProfitExited(MouseEvent event) {
		this.expenseProfitAnchor.getTransforms().clear();
	}

	// prothÃ¨se

	// Fiche navette
	@FXML
	private AnchorPane showFicheNavetteAnchor;

	@FXML
	void showFicheNavetteEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.showFicheNavetteAnchor.getTransforms().add(scale);

	}

	@FXML
	void showFicheNavetteExited(MouseEvent event) {
		this.showFicheNavetteAnchor.getTransforms().clear();
	}

	// Encours d'éxécution
	@FXML
	private AnchorPane showEncourExecutionAnchor;

	@FXML
	void showEncourExecutionEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.showEncourExecutionAnchor.getTransforms().add(scale);

	}

	@FXML
	void showEncourExecutionExited(MouseEvent event) {
		this.showEncourExecutionAnchor.getTransforms().clear();
	}

	// comptabilité
	@FXML
	private AnchorPane showComptabilityAnchor;

	@FXML
	void showComptabilityEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.showComptabilityAnchor.getTransforms().add(scale);

	}

	@FXML
	void showComptabilityExited(MouseEvent event) {
		this.showComptabilityAnchor.getTransforms().clear();
	}

	// new Labe
	@FXML
	private AnchorPane newLabAnchor;

	@FXML
	void newLabEntered(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.newLabAnchor.getTransforms().add(scale);

	}

	@FXML
	void newLabExited(MouseEvent event) {
		this.newLabAnchor.getTransforms().clear();
	}

	// new Appointment
	@FXML
	private AnchorPane showRDVAnchor;

	@FXML
	void addAppointmentEntred(MouseEvent event) {

		// creating a 2D scale
		Scale scale = new Scale();

		// setting the X-y factors for the scale
		scale.setX(1.05);
		scale.setY(1.05);

		// setting the pivot points along which the scaling is done
		// scale.setPivotX(550);
		// scale.setPivotY(200);
		this.showRDVAnchor.getTransforms().add(scale);

	}

	@FXML
	void addAppointmentExited(MouseEvent event) {
		this.showRDVAnchor.getTransforms().clear();
	}

	private void initializeComboBoxSelelctUser() {

		Statement stm = null;
		ResultSet rs = null;
		String request = null;
		String index = "";
		ArrayList<String> cBSU = new ArrayList<String>();
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = "SELECT user_id,nom_fr,specialty FROM users";
			rs = stm.executeQuery(request);
			int i = 0;
			while (rs.next()) {
				i++;

				cBSU.add(rs.getInt(1) + "_" + rs.getString(2) + "_" + rs.getString(3));
				if (rs.getInt(1) == this.userId) {
					index = rs.getInt(1) + "_" + rs.getString(2) + "_" + rs.getString(3);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		this.comboBoxSelectUser.setItems(FXCollections.observableArrayList(cBSU));
		this.comboBoxSelectUser.setValue(index);

	}

	// From this i begin code without scenebuilder

	@FXML
	private JFXComboBox<String> comboBoxSelectUser;

	@FXML
	void slelectionnerUser(ActionEvent event) {

		this.slelectionnerDate(null);

	}

}
