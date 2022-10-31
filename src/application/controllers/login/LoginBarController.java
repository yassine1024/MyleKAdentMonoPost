package application.controllers.login;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class LoginBarController implements Initializable {
	  @FXML
	    private FontAwesomeIcon collapseIcon;

	    @FXML
	    private FontAwesomeIcon closeIcon;

	
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}
	
	 @FXML
	    void close(MouseEvent event) {
		 Alert alert=new Alert(AlertType.CONFIRMATION);
		 alert.setTitle("Fermer");
		 alert.setHeaderText(null);
		 alert.setContentText("Voulez vous fermer?");
		  Optional<ButtonType> action =alert.showAndWait();
		  if (action.get()==ButtonType.OK) {
			 // System.out.println("OK");
			  Stage stage = (Stage) closeIcon.getScene().getWindow();
				stage.close();
			
		}
		  
		 
	    }

	    @FXML
	    void collapse(MouseEvent event) {
	    	
	    	Stage stage = (Stage) collapseIcon.getScene().getWindow();
	    	
	    	stage.setIconified(true);
	    	
	    	//    ((Stage)((FontAwesomeIcon)e.getSource()).getScene().getWindow()).setIconified(true);
	    

	    }


}
