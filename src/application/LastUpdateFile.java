package application;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.util.Duration;

public class LastUpdateFile extends Application {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//LastUpdateFile luf= new LastUpdateFile();
		
		launch(args);
		

	}
	
	public File getLatestFilefromDir(String dirPath){
	    File dir = new File(dirPath);
	    File[] files = dir.listFiles();
	    if (files == null || files.length == 0) {
	        return null;
	    }

	    File lastModifiedFile = files[0];
	    for (int i = 1; i < files.length; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	       }
	    }
	    return lastModifiedFile;
	}

	

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		System.out.println("kawter");
		
		Timeline tm = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {
					
					
					@Override
					public void handle(ActionEvent event) {
						// TODO Auto-generated method stub
						File f = getLatestFilefromDir("C:\\Users\\ACER-8.1\\Downloads\\Music");
						
						if(f!=null) {
						System.out.println("files: "+f.getName());
						}else {
							System.out.println("Pour le moment est vide...");
						}
						
						
					}
				}));
				
				
				tm.setCycleCount(Timeline.INDEFINITE);
				tm.play();
	}

}
