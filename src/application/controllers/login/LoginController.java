package application.controllers.login;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Hashtable;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import animatefx.animation.FadeIn;
import animatefx.animation.SlideInLeft;
import animatefx.animation.SlideInRight;
import application.RandomString;
import application.SQLiteJDBC;
import application.cryptage.Cryptage;
import javafx.animation.FadeTransition;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class LoginController implements Initializable {
	@FXML
	private TextField user;

	@FXML
	private PasswordField password;

	@FXML
	private Text signInIcone;

	@FXML
	private VBox loginVBox;
	@FXML
	private VBox forgetPasswdVBox;

	@FXML
	private StackPane stackPane;
	SQLiteJDBC sl;

	public void initialize(URL arg0, ResourceBundle arg1) {

		loatSplashScreen();
		// TODO Auto-generated method stub
		this.initializeEmails();
		this.initializeValidator();
		sl = new SQLiteJDBC();

	}

	@FXML
	void changerPositionMail(KeyEvent event) {

		switch (event.getCode()) {
		case DOWN:
			// System.out.println("Down");
			password.requestFocus();
			break;

		}

	}

	@FXML
	void changerPositionPassword(KeyEvent event) {

		switch (event.getCode()) {
		case UP:
			// System.out.println("Down");
			user.requestFocus();
			break;
		case ENTER:
			login(null);
			break;

		}

	}

	@FXML
	void login(MouseEvent event) {

		String password = "";
		try {
			password = Cryptage.cryptageSha(this.password.getText());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String user = this.user.getText();

		// launch the request
		ResultSet rs = null;
		PreparedStatement stm = null;
		try {
			String request = "select * FROM users WHERE mail=? AND password=?";

			stm = new SQLiteJDBC().getConnection().prepareStatement(request);
			stm.setString(1, user);
			stm.setString(2, password);
			// String request = "select * FROM users WHERE mail='" + user + "' AND
			// password='" + password + "' ";

			rs = stm.executeQuery();
			if (!rs.next()) {
				/*
				 * Notifications notification = Notifications.create().title("Echec")
				 * .text("le mail ou le mot de passe est incorrect").graphic(null).hideAfter(
				 * Duration.seconds(2)) .position(Pos.BOTTOM_CENTER).onAction(new
				 * EventHandler<ActionEvent>() {
				 * 
				 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
				 * System.out.println("notification"); } }); notification.show();
				 */
				JFXDialogLayout layout = new JFXDialogLayout();
				layout.setHeading(new Text("Echec"));
				layout.setBody(new Text("le mail ou le mot de passe est incorrect"));

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

				LocalDate myDate = LocalDate.now();
				DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

				Stage stage = (Stage) signInIcone.getScene().getWindow();
				stage.close();

				Stage primaryStage = new Stage();
				primaryStage.initStyle(StageStyle.TRANSPARENT);
				Parent root;

				try {
					ActivationController homeController = new ActivationController(rs.getString("nom_fr"),
							rs.getInt("user_id"));

					FXMLLoader fxmlLoader = new FXMLLoader(
							getClass().getResource("/application/views/login/activation.fxml"));

					int user_id = rs.getInt("user_id");
					stm.close();
					// System.out.println("nom : " + rs.getString("nom_fr") + " id : " +
					// rs.getInt("user_id"));

					request = "INSERT INTO historic_users(user_id,date_heur,action) VALUES(?,?,?)";

					stm = new SQLiteJDBC().getConnection().prepareStatement(request);
					// request = "INSERT INTO historic_users(user_id,date_heur,action) " +
					// "VALUES('"
					// + rs.getInt("user_id") + "', '" + myDate.format(myFormat) + "','Connexion')";
					stm.setInt(1, user_id);
					stm.setString(2, myDate.format(myFormat));
					stm.setString(3, "Connexion");

					stm.executeUpdate();

					fxmlLoader.setController(homeController);
					root = (Parent) fxmlLoader.load();

					Scene scene = new Scene(root);
					scene.setUserData(fxmlLoader);
					// begin handling

					final BooleanProperty ctrlPressed = new SimpleBooleanProperty(false);
					final BooleanProperty oPressed = new SimpleBooleanProperty(false);

					// final BooleanProperty dPressed = new SimpleBooleanProperty(false);
					final BooleanProperty mPressed = new SimpleBooleanProperty(false);
					final BooleanProperty rPressed = new SimpleBooleanProperty(false);
					final BooleanProperty pPressed = new SimpleBooleanProperty(false);
					final BooleanProperty hPressed = new SimpleBooleanProperty(false);
					// create new malade
					final BooleanProperty nPressed = new SimpleBooleanProperty(false);
					// create new lab
					final BooleanProperty lPressed = new SimpleBooleanProperty(false);
					// create new task
					final BooleanProperty tPressed = new SimpleBooleanProperty(false);
					// LogOut
					final BooleanProperty dPressed = new SimpleBooleanProperty(false);

					// Parameters
					final BooleanProperty cPressed = new SimpleBooleanProperty(false);
//Agenda
					final BooleanProperty aPressed = new SimpleBooleanProperty(false);

					
					
					// Wire up properties to key events:
					scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

						
						@Override
						public void handle(KeyEvent ke) {
							// TODO Auto-generated method stub
							if (new KeyCodeCombination(KeyCode.M, KeyCombination.CONTROL_ANY).match(ke)) {
							       System.out.println("Work for me");
							       homeController.showHome(null);
							    }
							if (new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_ANY).match(ke)) {
							       System.out.println("Work for me");
							       homeController.showRedaction(null);
							    }
							if (new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_ANY).match(ke)) {
							       System.out.println("Work for me");
							       homeController.showProthesist(null);
							    }
							if (new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY).match(ke)) {
							       System.out.println("Work for me");
							       homeController.showStatistics(null);
							    }
							if (new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_ANY).match(ke)) {
							       System.out.println("Work for me");
							       homeController.ajouterMalade(null);
							    }
							if (new KeyCodeCombination(KeyCode.L, KeyCombination.CONTROL_ANY).match(ke)) {
							       System.out.println("Work for me");
							       homeController.addProthese(null);
							    }
							if (new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_ANY).match(ke)) {
							       System.out.println("Work for me");
							       homeController.createNTask();
							    }
							if (new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY).match(ke)) {
							       System.out.println("Work for me");
							       homeController.showParameters(null);
							    }
							if (new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_ANY).match(ke)) {
							       System.out.println("Work for me");
							       homeController.showAgenda(null);
							    }
							
						/*	if (ke.getCode() == KeyCode.ALT_GRAPH) {
								ctrlPressed.set(true);
								System.out.println("PRESS ENTER");

							} else if (ke.getCode() == KeyCode.O) {
								oPressed.set(true);
								System.out.println("PRESS O");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.deconnect(null);
									ctrlPressed.set(false);
								}
							} else if (ke.getCode() == KeyCode.M) {
								mPressed.set(true);
								System.out.println("PRESS M");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.showHome(null);
									ctrlPressed.set(false);
								}
							} else if (ke.getCode() == KeyCode.R) {
								rPressed.set(true);
								System.out.println("PRESS R");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.showRedaction(null);
									ctrlPressed.set(false);
								}
							} else if (ke.getCode() == KeyCode.P) {
								pPressed.set(true);
								System.out.println("PRESS P");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.showProthesist(null);
									ctrlPressed.set(false);
								}
							} else if (ke.getCode() == KeyCode.S) { 
								hPressed.set(true);
								System.out.println("PRESS H");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.showStatistics(null);
									ctrlPressed.set(false);
								}
							} else if (ke.getCode() == KeyCode.N) {
								nPressed.set(true);
								System.out.println("PRESS N");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.ajouterMalade(null);
									ctrlPressed.set(false);
								}
							} else if (ke.getCode() == KeyCode.L) {
								lPressed.set(true);
								System.out.println("PRESS N");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.addProthese(null);
									ctrlPressed.set(false);
								}
							} else if (ke.getCode() == KeyCode.T) {
								tPressed.set(true);
								System.out.println("PRESS N");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.createNTask();
									ctrlPressed.set(false);
								}
							} else if (ke.getCode() == KeyCode.C) {
								cPressed.set(true);
								System.out.println("PRESS N");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.showParameters(null);
									ctrlPressed.set(false);
								}
							} else if (ke.getCode() == KeyCode.A) {
								aPressed.set(true);
								System.out.println("PRESS A");
								if (ctrlPressed.get()) {
									System.out.println("Control And D Clicked 2...................");
									homeController.showAgenda(null);
									ctrlPressed.set(false);
								}
							}

							else if (ke.getCode() == KeyCode.D) {

								
								 * Stage sb=(Stage) homeController.dec.getScene().getWindow(); sb.close();
								

								tPressed.set(true);
								System.out.println("PRESS N");

								if (ctrlPressed.get()) {

									homeController.deconnect(null);
									ctrlPressed.set(false);
								}

							}
							*/
						}

					});

					scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
						@Override
						public void handle(KeyEvent ke) {
							if (ke.getCode() == KeyCode.CONTROL) {
								ctrlPressed.set(false);
								System.out.println("RELEASE ENTER");
							}
						}
					});

					// end handling

					scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
					// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
					primaryStage.setScene(scene);
					primaryStage.show();

					new FadeIn(root).play();
				} catch (

				IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

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

	@FXML
	void signIn(MouseEvent event) {

		Stage stage = (Stage) signInIcone.getScene().getWindow();
		stage.close();

		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.TRANSPARENT);

		Parent root;

		try {

			root = FXMLLoader.load(getClass().getResource("../../views/login/Signup.fxml"));

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

	// *********************************BEGIN SPLASH SCREEN*******************

	@FXML
	private AnchorPane rootAnchore;
	@FXML
	private StackPane rootFatherA;

	private void loatSplashScreen() {
		// 4455
		try {
			StackPane pane = FXMLLoader.load(getClass().getResource("/application/views/splashScreen.fxml"));
			// pane.setMargin(rootAnchore, new Insets(200, 0, 0, 150));

			rootFatherA.getChildren().setAll(pane);
			// rootAnchore.setPadding(new Insets(200, 0, 0, 150));

			// I w'll create two fade transition 1->inTrasition 2->outTrinsition
			// 1)fadeIN
			FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), pane);
			fadeIn.setFromValue(0);
			fadeIn.setToValue(1);
			fadeIn.setCycleCount(1);
			// 1)fadeOUT
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), pane);
			fadeOut.setFromValue(1);
			fadeOut.setToValue(0);
			fadeOut.setCycleCount(1);

			fadeIn.play();

			fadeIn.setOnFinished((e) -> {
				fadeOut.play();
			});
			fadeOut.setOnFinished((e) -> {

				rootFatherA.prefWidth(600);
				rootFatherA.prefHeight(600);
				// StackPane pane =
				// FXMLLoader.load(getClass().getResource("/application/views/splashScreen.fxml"));
				rootFatherA.getChildren().setAll(stackPane);

			});

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	// #####################################END SPLASH SCREEN#####################

	@FXML
	void forgetPasswd(MouseEvent event) {

		new SlideInLeft(this.forgetPasswdVBox).play();
		this.forgetPasswdVBox.setVisible(true);
		this.loginVBox.setVisible(false);
	}
	
	@FXML
	void createAccount(MouseEvent event) throws IOException {

		System.out.println("Create Account");
		Stage stage = (Stage) signInIcone.getScene().getWindow();
		stage.close();
		
		Parent root = FXMLLoader.load(getClass().getResource("/application/views/login/Signup.fxml"));
		Stage primaryStage = new Stage();
		primaryStage.initStyle(StageStyle.TRANSPARENT);
		Scene scene = new Scene(root);
		scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
		// Scene scene = new Scene(root);

		// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	@FXML
	void returnLoginPage(MouseEvent event) {

		new SlideInRight(this.loginVBox).play();
		this.forgetPasswdVBox.setVisible(false);
		this.loginVBox.setVisible(true);
	}

	@FXML
	private JFXTextField emailLogin;

	@FXML
	private JFXTextField receptionEmail;

	@FXML
	void sendNewPassword(ActionEvent event) {

		RandomString gen = new RandomString(8, ThreadLocalRandom.current());

		// System.out.println(gen.nextString());
		String newPassword = gen.nextString();
		if (emailLogin.validate()) {
			System.out.println("Start upadte password in locale...");
			// 1 change the password in locale
			// Cryptage.cryptageSha(this.password.getText());
			String request = "UPDATE  users SET password=?,password_clear=? " + "WHERE user_id=?";
			PreparedStatement stmt;
			try {
				stmt = new SQLiteJDBC().getConnection().prepareStatement(request);
				stmt.setString(1, Cryptage.cryptageSha(newPassword));
				stmt.setString(2, newPassword);
				stmt.setInt(3, this.resetPassword.get(emailLogin.getText()));

				stmt.executeUpdate();

				System.out.println("Upadte password in locale successfull");
				stmt.close();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			// 2 send Email contain new password
			System.out.println("Email");
			final String username = "Myle dent";
			final String password = "(a+b)==(b+a);";
			String fromEmail = "myle.dent@gmail.com";
			String toEmail = receptionEmail.getText();

			Properties properties = new Properties();
			properties.setProperty("mail.transport.protocol", "smtp");
			properties.put("mail.smtp.auth", "true");
			properties.put("mail.smtp.starttls.enable", "true");
			properties.put("mail.host", "smtp.gmail.com");
			properties.put("mail.smtp.port", "465");

			properties.put("mail.smtp.socketFactory.port", "465");
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			properties.put("mail.smtp.socketFactory.fallback", "false");
			properties.setProperty("mail.smtp.quitwait", "false");

			// Get the Session object.// and pass username and password
			Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

				protected PasswordAuthentication getPasswordAuthentication() {

					return new PasswordAuthentication("myle.dent@gmail.com", "(a+b)==(b+a);");

				}

			});

			// start our mail message
			MimeMessage msg = new MimeMessage(session);
			try {
				// Create a default MimeMessage object.
				MimeMessage message = new MimeMessage(session);

				// Set From: header field of the header.
				message.setFrom(new InternetAddress(fromEmail));

				// Set To: header field of the header.
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));

				// Set Subject: header field
				message.setSubject("Myle Denty!");

				// Now set the actual message
				message.setText("Myle Denty");
				message.setText("changement du mot de passe");
				message.setText("Voici votre nouveau mot de passe de l'application MYLE Denty: " + newPassword);

				System.out.println("sending...");
				// Send message
				Transport.send(message);
				System.out.println("Sent message successfully....");
				this.showDialog("Votre mot passe a été changé avec succés. Vérifier votre boite mail", "Info");

			} catch (AddressException e) {
				// TODO Auto-generated catch block
				this.showDialog("Error 1 "+e.getMessage(), "Echec");

				e.printStackTrace();
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
				this.showDialog("Error 2 "+e.getMessage(), "Echec");
				e.printStackTrace();
			}
		}
	}

	Hashtable<String, Integer> resetPassword;

	private void initializeEmails() {
		this.resetPassword = new Hashtable<String, Integer>();
		// launch the request
		ResultSet rs = null;
		Statement stm = null;
		try {
			String request = "select * FROM users";

			stm = new SQLiteJDBC().getConnection().createStatement();

			// String request = "select * FROM users WHERE mail='" + user + "' AND
			// password='" + password + "' ";

			rs = stm.executeQuery(request);
			while (rs.next()) {

				if(rs.getBoolean("if_auth")) {
					this.user.setText(rs.getString("mail"));
					this.password.setText(rs.getString("password_clear"));
				}
				this.resetPassword.put(rs.getString("mail"), rs.getInt("user_id"));
			}
			rs.close();
			stm.close();

		} catch (SQLException e) {

		}

	}

	private void initializeValidator() {
		// TODO Auto-generated method stub

		// validate type
		RequiredFieldValidator validatorEmail = new RequiredFieldValidator();

		this.emailLogin.getValidators().add(validatorEmail);
		validatorEmail.setMessage("Cet Email n'�xiste pas");

		this.emailLogin.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {// when focus lost
					if (!resetPassword.containsKey(emailLogin.getText())) {// !textField.getText().matches("[1-5]\\.[0-9]|6\\.0")
						emailLogin.setText("");

					}
					emailLogin.validate();

				}
			}
		});

	}

	private void showDialog(String message, String title) {

		JFXDialogLayout layout = new JFXDialogLayout();
		layout.setHeading(new Text(title));
		layout.setBody(new Text(message));

		JFXDialog dialog = new JFXDialog(rootFatherA, layout, JFXDialog.DialogTransition.CENTER);

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
