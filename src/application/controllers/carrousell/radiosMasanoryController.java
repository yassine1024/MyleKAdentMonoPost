package application.controllers.carrousell;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXMasonryPane;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class radiosMasanoryController implements Initializable {

	@FXML
	private JFXMasonryPane masonaryPane;
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		this.initializeRadios();
	}
	
	
private void initializeRadios() {
		// TODO Auto-generated method stub
		
	Tooltip ttDetailed = new Tooltip();
	ttDetailed.setText("Double cick sur le radio pour afficher.");
	ttDetailed.setStyle("-fx-font: normal bold 13 Langdon; " + "-fx-base: #AE3522; " + "-fx-text-fill: orange;");

	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
	for (String string : radiosList) {
		
		System.out.println("kkkk "+string);
		AnchorPane anchorRadios=new AnchorPane();
		
		//anchorRadios.setPadding(new Insets(90, 0, 0, 0));
		
		
		File file=new File(string);
		ImageView radio=new ImageView(file.toURI().toString());
		
		radio.setFitWidth(70);
		radio.setFitHeight(70);
		radio.setPreserveRatio(true);
		anchorRadios.getChildren().add(radio);
		//this.centerImage(radio);
		//anchorRadios.getChildren().add(radio);
		
		radio.setOnMousePressed(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				// TODO Auto-generated method stub
				Stage primaryStage = new Stage();
				//primaryStage.setFullScreen(true);
				//primaryStage.initStyle(StageStyle.UNDECORATED);
				fullDisplayRadioController controller = new fullDisplayRadioController(string);
				FXMLLoader root;
				Parent parent;

				try {
					root = new FXMLLoader(getClass().getResource("/application/views/carrousell/fullDisplayRadio.fxml"));

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
		});
		//listAnchorRadios.add(anchorRadios);
		radio.setCursor(Cursor.HAND);
		masonaryPane.getChildren().add(anchorRadios);
	}
	
	
	}


private LinkedList<String> radiosList;
	
	public  radiosMasanoryController(LinkedList<String> radiosList) {
		
		this.radiosList=radiosList;
	}

}
