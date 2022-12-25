package application.controllers.prothese;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Observable;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXTextField;

import application.Products;
import application.SQLiteJDBC;
import application.controllers.DiagnostiqueDetaille;
import application.controllers.login.ActivationController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddLaboController implements Initializable {

	@FXML
    private AnchorPane stackPane;
	
public void setLightDarkMode() {
		
		if(!ActivationController.getMode()) {
			this.stackPane.getStylesheets().clear();
			
			this.stackPane.getStylesheets().add("/css/light_mode/buttonSyle.css");
			
			this.stackPane.getStylesheets().add("/css/light_mode/styles.css");
			this.stackPane.getStylesheets().add("/css/light_mode/styles2.css");
			this.stackPane.getStylesheets().add("/css/light_mode/tableView.css");
			
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		this.initializeProductsTable();
		
		//Set Light/Dark mode as user chosed
				this.setLightDarkMode();

	}

	@FXML
	private Button addButton;

	@FXML
	private JFXTextField tNom;

	@FXML
	private JFXTextField tAddresse;

	@FXML
	private JFXTextField tPhone;

	private int switchController = 0;

	@FXML
	private TableView<Products> productsTable;

	@FXML
	private JFXTextField type;

	@FXML
	private JFXTextField price;

	@FXML
	void addLabo(ActionEvent event) {

		String nom = tNom.getText();
		String addresse = tAddresse.getText();
		String phone = tPhone.getText();

		PreparedStatement stm = null;
		ResultSet rs=null;
		try {
			String request = "INSERT INTO labos(nom,adresse,phone) " + "VALUES(?,?,?);";
			stm = new SQLiteJDBC().getConnectionProthese().prepareStatement(request,Statement.RETURN_GENERATED_KEYS);
			stm.setString(1, nom);
			stm.setString(2, addresse);
			stm.setString(3, phone);

			/*
			 * String request = "INSERT INTO labos(nom,adresse,phone) " + "VALUES('" + nom +
			 * "','" + addresse + "','" + phone + "');"; stm.execute(request);
			 */
			stm.executeUpdate();
			rs=stm.getGeneratedKeys();
			//System.out.println(+" KeyGen");
			int keyGen=rs.getInt(1);
			
			stm.close();
			
			for (Products products : productsArray) {
				
				request = "INSERT INTO labo_products(type,price,labo_id) " + "VALUES(?,?,?);";
				stm = new SQLiteJDBC().getConnectionProthese().prepareStatement(request);
				stm.setString(1, products.getType());
				stm.setFloat(2, products.getPrice());
				stm.setInt(3,keyGen);
				
				
				System.out.println("Key Gen "+keyGen+" products "+products.getType()+" "+products.getPrice());

				/*
				 * String request = "INSERT INTO labos(nom,adresse,phone) " + "VALUES('" + nom +
				 * "','" + addresse + "','" + phone + "');"; stm.execute(request);
				 */
				stm.executeUpdate();
				stm.close();
			}
			
			
			
			
			
			this.switchController = 1;
			addButton.getScene().getWindow().hide();
			Stage stage = (Stage) addButton.getScene().getWindow();
			stage.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			
			
		}finally {
			try {
				rs.close();
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}

	public int getSwitchController() {
		return switchController;
	}

	@FXML
	void changerPositionAddresse(KeyEvent event) {
		switch (event.getCode()) {

		case UP:
			tNom.requestFocus();
			break;
		case DOWN:
			tPhone.requestFocus();
			break;

		}

	}

	@FXML
	void changerPositionNom(KeyEvent event) {
		switch (event.getCode()) {

		case DOWN:
			tAddresse.requestFocus();
			break;

		}

	}

	@FXML
	void changerPositionPhone(KeyEvent event) {
		switch (event.getCode()) {

		case UP:
			tAddresse.requestFocus();
			break;
		case DOWN:
			type.requestFocus();
			break;
		case ENTER:
			addLabo(null);
			break;

		}

	}
	@FXML
	void changePositionType(KeyEvent event) {
		switch (event.getCode()) {

		case RIGHT:
			price.requestFocus();
			break;
		

		}
	}
	
	private ArrayList<Products> productsArray=new ArrayList<Products>();
	   @FXML
	    void addProduct(ActionEvent event) {
		   
		   productsArray.add(new Products(this.type.getText(), Float.parseFloat(this.price.getText())));
		  
		   productsTable.setItems(FXCollections.observableArrayList(productsArray));

		   this.price.setText("");this.type.setText("");
		   
		   type.requestFocus();
	    }
	   
	   @FXML
	    void removeProduct(ActionEvent event) {
		   
		   
		   
		   productsArray.remove(productsTable.getSelectionModel().getSelectedItem());
		   productsTable.setItems(FXCollections.observableArrayList(productsArray));
		   
		   

	    }
	   
	   @FXML
	    void addProducts(KeyEvent event) {

		   switch (event.getCode()) {

			
			case ENTER:
				addProduct(null);
				break;
			case LEFT:
				type.requestFocus();

			}
		   
		   
	    }
	   
	   
	   private void initializeProductsTable() {
			// initialiser tableau diagnostique
			TableColumn<Products, String> typeColumn = new TableColumn<Products, String>("Type");
			TableColumn<Products, Float> priceColumn = new TableColumn<Products, Float>("Prix");
			
			// width of column of table
			typeColumn.prefWidthProperty().bind(productsTable.widthProperty().multiply(0.65));
			priceColumn.prefWidthProperty().bind(productsTable.widthProperty().multiply(0.35));
			
			typeColumn.setResizable(false);
			priceColumn.setResizable(false);
			

			typeColumn.setCellValueFactory(new PropertyValueFactory("type"));
			priceColumn.setCellValueFactory(new PropertyValueFactory("price"));
			

			// ajouter les colones et leur row au tableaux
			productsTable.getColumns().addAll(typeColumn,priceColumn);
			
	   }

}
