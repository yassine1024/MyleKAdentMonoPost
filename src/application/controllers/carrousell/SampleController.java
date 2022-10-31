package application.controllers.carrousell;

import java.io.File;
import java.net.URL;
import java.util.LinkedList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.util.Duration;

public class SampleController implements Initializable {

	
	 @FXML
	    private StackPane pane1;

	   
	    @FXML	
	    private Label slideNum;

	    
public void translateAnimation(double duration,Node node,double width) {
	TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(duration), node);
	translateTransition.setByX(width);
	translateTransition.play();
}

LinkedList<AnchorPane> listAnchorRadios;

@FXML
private JFXButton backButton;
@FXML
private JFXButton nextButton;
@FXML
private AnchorPane root;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		//translateAnimation(0.5, pane1, 829);
		
		
		
		listAnchorRadios=new LinkedList<AnchorPane>();
		
		
		for (String string : radiosList) {
			
			System.out.println("kkkk "+string);
			AnchorPane anchorRadios=new AnchorPane();
			
			//anchorRadios.setPadding(new Insets(90, 0, 0, 0));
			anchorRadios.setMinWidth(this.screenBounds.getWidth());
			anchorRadios.setMinHeight(this.screenBounds.getHeight());
			
			anchorRadios.setPrefWidth(this.screenBounds.getWidth());
			anchorRadios.setPrefHeight(this.screenBounds.getHeight()-90);
			
			anchorRadios.setMaxWidth(this.screenBounds.getWidth());
			anchorRadios.setMaxHeight(this.screenBounds.getHeight()-90);
			
			anchorRadios.setStyle("-fx-background-color:white");
			
			File file=new File(string);
			ImageView radio=new ImageView(file.toURI().toString());
			
			radio.setFitWidth(this.screenBounds.getWidth());
			radio.setFitHeight(this.screenBounds.getHeight()-90);
			radio.setPreserveRatio(true);
			this.centerImage(radio);
			anchorRadios.getChildren().add(radio);
			
			listAnchorRadios.add(anchorRadios);
		}
		
		//pane1.getChildren().addAll(listAnchorRadios);
		
		//root.getChildren().addAll(0, listAnchorRadios);
		
		pane1.getChildren().addAll(listAnchorRadios);
		/*if(!listAnchorRadios.isEmpty()) {
		translateAnimation(0.5, listAnchorRadios.get(1), 829);
		translateAnimation(0.5, listAnchorRadios.get(2), 829);
		}*/
		if(listAnchorRadios.size()==0) {
			
			AnchorPane anchorRadios=new AnchorPane();
			
			//anchorRadios.setPadding(new Insets(90, 0, 0, 0));
			anchorRadios.setMinWidth(this.screenBounds.getWidth());
			anchorRadios.setMinHeight(this.screenBounds.getHeight());
			
			anchorRadios.setPrefWidth(this.screenBounds.getWidth());
			anchorRadios.setPrefHeight(this.screenBounds.getHeight()-90);
			
			anchorRadios.setMaxWidth(this.screenBounds.getWidth());
			anchorRadios.setMaxHeight(this.screenBounds.getHeight()-90);
			
			anchorRadios.setStyle("-fx-background-color:white");
			
			File file=new File("C:\\Users\\ACER-8.1\\eclipse-workspace\\cabinet_medicale\\src\\images\\error.png");
			ImageView radio=new ImageView(file.toURI().toString());
			
			radio.setFitWidth(this.screenBounds.getWidth());
			radio.setFitHeight(this.screenBounds.getHeight()-90);
			radio.setPreserveRatio(true);
			this.centerImage(radio);
			anchorRadios.getChildren().add(radio);
			
			backButton.setVisible(false);
			nextButton.setVisible(false);
			pane1.getChildren().add(anchorRadios);
		
			
		}else {
		for(int i=1;i<listAnchorRadios.size();i++) {
			translateAnimation(0.5, listAnchorRadios.get(i), screenBounds.getWidth());
		}
		
		}
	}
	
	private Rectangle2D screenBounds = Screen.getPrimary().getBounds();
	int show=0;
	
	 @FXML
	    void next(ActionEvent event) {

		/* if(show==0) {
			 translateAnimation(0.5, listAnchorRadios.get(1), -1*829);
			 show++;
		 }else if(show==1) {
			 translateAnimation(0.5, listAnchorRadios.get(2), -1*829);
			 show++;
		 }*/
		 
		 for(int i=0;i<listAnchorRadios.size()-1;i++) {
			 if(show==i) {
				 translateAnimation(0.5, listAnchorRadios.get(i+1), -1*screenBounds.getWidth()); 
				 show++;
				 break;
			 }
		 }
	    }
	 
	@FXML
    void back(ActionEvent event) {
		 /*if(show==1) {
			 translateAnimation(0.5, listAnchorRadios.get(1), 829);
			 show--;
		 }else if(show==2) {
			 translateAnimation(0.5, listAnchorRadios.get(2), 829);
			 show--; 
		 }*/
		for(int i=1;i<listAnchorRadios.size();i++) {
			 if(show==i) {
				 translateAnimation(0.5, listAnchorRadios.get(i), screenBounds.getWidth()); 
				 show--;
				 break;
			 }
		 }
    }

   
	
	private LinkedList<String> radiosList;
	
	public SampleController(LinkedList<String> radiosList) {
		
		this.radiosList=radiosList;
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
