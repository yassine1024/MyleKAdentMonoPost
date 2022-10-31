package application.controllers.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Hashtable;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;

import animatefx.animation.BounceIn;
import animatefx.animation.BounceInRight;
import animatefx.animation.FadeIn;
import animatefx.animation.FadeOutDown;
import animatefx.animation.Flash;
import animatefx.animation.Pulse;
import application.DataBaseConnection;
import application.DiskUtils;
import application.SQLiteJDBC;
import application.cryptage.Cryptage;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class LicenseActivationController implements Initializable {

	@FXML
	private StackPane stackPane;
	@FXML
	private JFXTextField serial;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		
		
	    
		
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

			//set serial to true in cloud db
			db.updateSerial(this.serial.getText());
			
			//set serial to machine in locale
			try {
				
				Process p = Runtime.getRuntime().exec("reg query \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\" /v ProductId");
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			    String line = "";
			    String line1= reader.readLine();
			    System.out.println("line1 "+line1);
			    String line2= reader.readLine();
			    System.out.println("line2 "+line2);
			    String line3= reader.readLine();
			    System.out.println("line3 "+line3);
			    
			    String productID = Cryptage.cryptageSha(line3);
				Statement stm = new SQLiteJDBC().getConnection().createStatement();
				String request = "INSERT INTO activations(mac_address) VALUES('"+productID+"')";
				stm.execute(request);
				System.out.println("Activations finished Locale");
				
				
				try {
					this.openRegisterView();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					this.showDialog("L'activation de la clÃ© nÃ©cÃ©ssite Internet. VÃ©rifier votre connexion");
					e.printStackTrace();
				}
				
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				this.showDialog("L'activation de la clÃ© nÃ©cÃ©ssite Internet. VÃ©rifier votre connexion");
				e.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
		}

	}
	private void openRegisterView() throws IOException {
		
		
		Stage stage = (Stage) this.stackPane.getScene().getWindow();
		stage.close();
		
		Stage primaryStage = new Stage();
		String ifLoged="/application/views/login/Login.fxml";
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
	    void showHome(MouseEvent event) {

	    	new BounceInRight(this.homeAnchor).play();
	    	this.homeAnchor.setVisible(true);
	    	this.redactionAnchor.setVisible(false);
	    	this.statisticsAnchor.setVisible(false);
	    }

	    @FXML
	    void showRedaction(MouseEvent event) {
	    	
	    	
	    	new BounceInRight(this.redactionAnchor).play();
	    	this.homeAnchor.setVisible(false);
	    	this.redactionAnchor.setVisible(!this.redactionAnchor.isVisible());
	    	this.statisticsAnchor.setVisible(false);
	    }

	    @FXML
	    void showStatistics(MouseEvent event) {
	    	
	    	new BounceInRight(this.statisticsAnchor).play();
	    	this.homeAnchor.setVisible(false);
	    	this.redactionAnchor.setVisible(false);
	    	this.statisticsAnchor.setVisible(!this.statisticsAnchor.isVisible());
	    }
	
	
	 

}
