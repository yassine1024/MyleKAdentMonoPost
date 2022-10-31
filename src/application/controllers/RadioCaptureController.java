package application.controllers;

import java.io.File;
import java.net.URL;
import java.nio.file.WatchService;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class RadioCaptureController implements Initializable {

	@FXML
	private StackPane stackPane;

	@FXML
	private VBox vBoxRadios;

	String pathFile = "C:\\Users\\ACER-8.1\\Downloads\\Music";

	@FXML
	JFXTextField textFieldPath;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		System.out.println("kawter");

		Timeline tm = new Timeline(new KeyFrame(Duration.seconds(5), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// TODO Auto-generated method stub
				File f = getLatestFilefromDir(textFieldPath.getText());

				if (f != null) {
					System.out.println(textFieldPath.getText() + "\\" + f.getName());
					File file = new File(textFieldPath.getText() + "\\" + f.getName());
					ImageView radio = new ImageView(file.toURI().toString());
					radio.setFitWidth(400);
					radio.setFitHeight(250);
					vBoxRadios.getChildren().add(radio);
				} else {
					System.out.println("Pour le moment est vide...");

				}

			}
		}));

		tm.setCycleCount(Timeline.INDEFINITE);
		tm.play();
	}

	public File getLatestFilefromDir(String dirPath) {

		if (!textFieldPath.getText().equals("") && textFieldPath.getText() != null) {
			dirPath = textFieldPath.getText();
		} else {
			return null;
		}
		File dir = new File(dirPath);
		File[] files = dir.listFiles();
		if (files == null || files.length == 0) {
			return null;
		}

		File lastModifiedFile = null;
		for (int i = 0; i < files.length; i++) {
			if (!files[i].isDirectory()) {
				String name = files[i].getName();
				String ext = name.substring(name.lastIndexOf("."));
				ext = ext.toUpperCase();
				System.out.println("extension:::: "+ext);
				if (ext.equals(".JPEG") || ext.equals(".JPG") || ext.equals(".PNG") || ext.equals(".GIF") || ext.equals(".TIFF")
						|| ext.equals(".PSD") || ext.equals(".PDF") || ext.equals(".EPS") || ext.equals(".AI")) {
					if(lastModifiedFile==null) {
						lastModifiedFile = files[i];
					}
					else if (lastModifiedFile.lastModified() < files[i].lastModified()) {
						lastModifiedFile = files[i];
					}
				}
			}
		}
		System.out.println("Selected is ::::  "+lastModifiedFile.getName());
		return lastModifiedFile;
	}

}
