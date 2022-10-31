package application.controllers.carrousell;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;

public class fullDisplayRadioController implements Initializable {

	private String radio;
	
	@FXML
	private AnchorPane anchorRadios;

	public fullDisplayRadioController(String radio) {
		this.radio=radio;
	}
	
	
	Rectangle2D screenBounds = Screen.getPrimary().getBounds();
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		
		
		anchorRadios.setMinWidth(this.screenBounds.getWidth());
		anchorRadios.setMinHeight(this.screenBounds.getHeight());
		
		anchorRadios.setPrefWidth(this.screenBounds.getWidth());
		anchorRadios.setPrefHeight(this.screenBounds.getHeight()-90);
		
		anchorRadios.setMaxWidth(this.screenBounds.getWidth());
		anchorRadios.setMaxHeight(this.screenBounds.getHeight()-90);
		
		anchorRadios.setStyle("-fx-background-color:white");
		
		File file=new File(this.radio);
		ImageView radio=new ImageView(file.toURI().toString());
		
		radio.setFitWidth(this.screenBounds.getWidth());
		radio.setFitHeight(this.screenBounds.getHeight()-90);
		radio.setPreserveRatio(true);
		this.centerImage(radio);
		anchorRadios.getChildren().add(radio);
	
		
	}
	public void centerImage(ImageView imageView) {
        Image img = imageView.getImage();
        if (img != null) {
            double w = 0;
            double h = 0;

            double ratioX = imageView.getFitWidth() / img.getWidth();
            double ratioY = imageView.getFitHeight() / img.getHeight();

            double reducCoeff = 0;
            if(ratioX >= ratioY) {
                reducCoeff = ratioY;
            } else {
                reducCoeff = ratioX;
            }

            w = img.getWidth() * reducCoeff;
            h = img.getHeight() * reducCoeff;

            imageView.setX((imageView.getFitWidth() - w) / 2);
            imageView.setY((imageView.getFitHeight() - h) / 2);

        }
    }
}
