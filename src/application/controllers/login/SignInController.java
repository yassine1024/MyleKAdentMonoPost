package application.controllers.login;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.controlsfx.control.Notifications;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import application.SQLiteJDBC;
import application.controllers.HomeController;
import application.cryptage.Cryptage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class SignInController implements Initializable {

	@FXML
	private TextField userAR;

	@FXML
	private TextField specialty;
	@FXML
	private TextField specialtyAR;
	@FXML
	private TextField phone;

	@FXML
	private TextField adresse;

	@FXML
	private PasswordField passwordC;

	@FXML
	private TextField user;

	@FXML
	private TextField mail;

	@FXML
	private PasswordField password;

	@FXML
	private Button loginIcon;
	SQLiteJDBC sl;
	private final String REGEX_MAIL = "^(.+)@(.+)$";;
	// private final String REGEX_PASSWORD =
	// "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		sl = new SQLiteJDBC();

	}

	@FXML
	void changerPositionMail(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:
			System.out.println("Down");
			password.requestFocus();
			break;
		case UP:
			phone.requestFocus();
			;

		}
	}

	@FXML
	void changerPositionPassword(KeyEvent event) {
		switch (event.getCode()) {

		case UP:
			mail.requestFocus();
			break;
		case DOWN:
			passwordC.requestFocus();
			break;

		}
	}

	@FXML
	void changerPositionUser(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:
			System.out.println("Down");
			userAR.requestFocus();
			break;

		}
	}

	@FXML
	void changerPositionUserAR(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:

			specialty.requestFocus();
			break;
		case UP:
			user.requestFocus();
			break;

		}
	}

	@FXML
	void changerPositionSpecialty(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:

			specialtyAR.requestFocus();
			break;
		case UP:
			userAR.requestFocus();
			break;

		}
	}

	@FXML
	void changerPositionSpecialtyAR(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:

			adresse.requestFocus();
			break;
		case UP:
			specialty.requestFocus();
			break;

		}
	}

	@FXML
	void changerPositionPhone(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:

			mail.requestFocus();
			break;
		case UP:
			adresse.requestFocus();
			break;

		}
	}

	@FXML
	void changerPositionAdresse(KeyEvent event) {
		switch (event.getCode()) {
		case DOWN:

			phone.requestFocus();
			break;
		case UP:
			specialtyAR.requestFocus();
			break;

		}
	}

	@FXML
	void changerPositionCPassword(KeyEvent event) {
		switch (event.getCode()) {
		case ENTER:

			signIn(null);
			break;
		case UP:
			password.requestFocus();
			break;

		}
	}

	@FXML
	void login(MouseEvent event) {

		Stage stage = (Stage) loginIcon.getScene().getWindow();
		stage.close();

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		Parent root;

		try {

			root = FXMLLoader.load(getClass().getResource("../../views/login/Login.fxml"));

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

	@FXML
	private StackPane stackPane;

	@FXML
	void signIn(MouseEvent event) {
		/*
		 * *********************Begin snapSound attributes
		 *******************************/
		/*String ssound = "assets//snapSound.mp3";
		Media sound = new Media(new File(ssound).toURI().toString());
		MediaPlayer mediaPlayer = new MediaPlayer(sound);
		/**********************
		 * End snapSound a ,, <qsxde'tgfrezdfgy-ttributes
		 * 
		 * +*96398520
		 **********************************/

		//mediaPlayer.play();
		
		String user = this.user.getText();
		String userAR = this.userAR.getText();
		String spec = this.specialty.getText();
		String specAR = this.specialtyAR.getText();
		String adr = this.adresse.getText();
		String phone = this.phone.getText();
		String mail = this.mail.getText();
		String password = "";

		try {
			password = Cryptage.cryptageSha(this.password.getText());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Pattern pattern = Pattern.compile(this.REGEX_MAIL);
		Matcher matcher = pattern.matcher(mail);
		System.out.println(" mail : " + matcher.matches());
		if (!matcher.matches()) {
			/*
			 * Notifications notification =
			 * Notifications.create().title("Echec").text("le mail  est incorrect")
			 * .graphic(null).hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_CENTER)
			 * .onAction(new EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 */
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Echec"));
			layout.setBody(new Text("le mail  est incorrect"));

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
		} /*
			 * else {
			 * 
			 * //pattern = Pattern.compile(this.REGEX_PASSWORD); matcher =
			 * pattern.matcher(this.password.getText()); if (!matcher.matches()) {
			 * 
			 * Notifications notification = Notifications.create().title("Echec")
			 * .text("1) Le mot de passe doit contenir au minimum 8 charact�res; \n" +
			 * "2) Contient au moins un miniscule; \n" +
			 * "3) Contient au moins un majiscule; \n" +
			 * "4) Contient au moins un chiffre; \n" +
			 * "5) Contient au moins un charct�re Sp�ciale.")
			 * .graphic(null).hideAfter(Duration.seconds(20)).position(Pos.BOTTOM_CENTER)
			 * .onAction(new EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 * 
			 * JFXDialogLayout layout=new JFXDialogLayout(); layout.setHeading(new
			 * Text("Echec"));
			 * 
			 * VBox vbox =new VBox(); vbox.getChildren().addAll(new
			 * Text("1) Le mot de passe doit contenir au minimum 8 charactères;"),new
			 * Text("2) Contient au moins une miniscule;"),new
			 * Text("3) Contient au moins une majiscule"),new
			 * Text("4) Contient au moins un chiffre;"),new
			 * Text("5) Contient au moins un charctère Spéciale")); layout.setBody(vbox);
			 * JFXDialog dialog=new JFXDialog(stackPane, layout,
			 * JFXDialog.DialogTransition.CENTER);
			 * 
			 * JFXButton ok= new JFXButton("Annuler"); ok.setOnAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * @Override public void handle(ActionEvent event) { dialog.close();
			 * 
			 * } });
			 * 
			 * layout.setActions(ok); dialog.show();
			 * 
			 * }
			 */ else {

			if (!this.password.getText().equals(this.passwordC.getText())) {
				/*
				 * Notifications notification = Notifications.create().title("Echec")
				 * .text("Il faut que les deux mot de passe soit les m�mes.").graphic(null)
				 * .hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_CENTER) .onAction(new
				 * EventHandler<ActionEvent>() {
				 * 
				 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
				 * System.out.println("notification"); } }); notification.show();
				 */
				JFXDialogLayout layout = new JFXDialogLayout();
				layout.setHeading(new Text("Echec"));
				layout.setBody(new Text("Il faut que les deux mot de passe soit les même"));

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
				// launch the request
				ResultSet rs = null;
				PreparedStatement stm = null;
				try {
					String request = "INSERT INTO users(nom_fr,nom_ar,specialty,specialty_ar,address,phone,mail,password,if_auth,password_clear) VALUES(?,?,?,?,?,?,?,?,?,?)";

					stm = sl.getConnection().prepareStatement(request, Statement.RETURN_GENERATED_KEYS);

					stm.setString(1, user);
					stm.setString(2, userAR);
					stm.setString(3, spec);
					stm.setString(4, specAR);
					stm.setString(5, adr);
					stm.setString(6, phone);
					stm.setString(7, mail);
					stm.setString(8, password);
					stm.setBoolean(9, false);
					stm.setString(10, this.password.getText());

					stm.executeUpdate();
					rs = stm.getGeneratedKeys();
					System.out.println("RS: " + rs.getInt(1));

					// open Home Page
					Stage stage = (Stage) loginIcon.getScene().getWindow();
					stage.close();

					Stage primaryStage = new Stage();
					primaryStage.initStyle(StageStyle.TRANSPARENT);
					Parent root;

					try {
						ActivationController homeController = new ActivationController(user, rs.getInt(1));

						FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/views/login/activation.fxml"));

						// HomeController homeController = new HomeController(user, rs.getInt(1));
						// HomeController.initLogo(user);

						fxmlLoader.setController(homeController);
						root = (Parent) fxmlLoader.load();

						Scene scene = new Scene(root);
						scene.setUserData(fxmlLoader);
						scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
						// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
						primaryStage.setScene(scene);
						primaryStage.show();

					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {

					try {
						if (rs != null) {
							rs.close();
						} else {
							System.out.println("RS NULLL");
						}

						stm.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
		}
	}

}
