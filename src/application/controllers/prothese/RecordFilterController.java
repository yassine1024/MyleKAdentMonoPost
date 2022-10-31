package application.controllers.prothese;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXCheckBox;

import application.FicheNavette;
import application.Products;
import application.SQLiteJDBC;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class RecordFilterController implements Initializable {

	@FXML
	private TableView<FicheNavette> tabRecordFilter;

	@FXML
	private JFXCheckBox checkAll;

	@FXML
	void sendRecord(ActionEvent event) {

		PreparedStatement stmt=null;
		
		String request=null;
		
		ArrayList<FicheNavette> arrayDelete=new ArrayList<FicheNavette>();
		
		for (FicheNavette ficheNavette : arrayRecordShuttles) {
			
			if(ficheNavette.getCheckBox().isSelected()) {
				request = "UPDATE  record_shuttles SET validate=? "
						+ "WHERE record_shuttle_id=?";
				
				try {
					stmt=new SQLiteJDBC().getConnectionProthese().prepareStatement(request);
					stmt.setInt(1, 1);
					stmt.setInt(2, ficheNavette.getId());
					
					stmt.executeUpdate();
					stmt.close();
					
					arrayDelete.add(ficheNavette);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}finally {
					try {
						stmt.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		}
		
		arrayRecordShuttles.removeAll(arrayDelete);
		
		tabRecordFilter.setItems(FXCollections.observableArrayList(arrayRecordShuttles));
		
		//this.intializeRecord();
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		arrayRecordShuttles=new ArrayList<FicheNavette>();
		this.initializeColumnOfTable();
		this.intializeRecord();
	}

	
	private ArrayList<FicheNavette> arrayRecordShuttles;
	
	private void intializeRecord() {
		// TODO Auto-generated method stub

		Statement stm=null;
		ResultSet rs=null;
		String request="";
		
		try {
			stm=new SQLiteJDBC().getConnectionProthese().createStatement();
			request=" SELECT R.record_shuttle_id, R.full_name, R.teinte, R.date, R.quantity, R.remarque, L.type, L.price FROM record_shuttles R INNER JOIN labo_products L ON R.labo_product_id = L.labo_prodcut_id  WHERE R.validate = 0 ";
			rs=stm.executeQuery(request);
			while(rs.next()) {
				
				arrayRecordShuttles.add(new FicheNavette(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getFloat(5), rs.getString(6), rs.getString(7), rs.getFloat(8)));
			}
			
			 tabRecordFilter.setItems(FXCollections.observableArrayList(arrayRecordShuttles));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private void initializeColumnOfTable() {
		// initialiser tableau diagnostique
		TableColumn<FicheNavette, String> dateColumn = new TableColumn<FicheNavette, String>("Date");
		TableColumn<FicheNavette, String> fullNameColumn = new TableColumn<FicheNavette, String>("Nom");
		TableColumn<FicheNavette, String> typeColumn = new TableColumn<FicheNavette, String>("Type");
		TableColumn<FicheNavette, Float> priceColumn = new TableColumn<FicheNavette, Float>("PrixU");
		TableColumn<FicheNavette, Float> quantityColumn = new TableColumn<FicheNavette, Float>("Qte");
		TableColumn<FicheNavette, Float> totalePriceColumn = new TableColumn<FicheNavette, Float>("PrixT");
		TableColumn<FicheNavette, String> colorColumn = new TableColumn<FicheNavette, String>("Tente");
		TableColumn<FicheNavette, String> remarqueColumn = new TableColumn<FicheNavette, String>("Remarque");
		TableColumn<FicheNavette, JFXCheckBox> sendColumn = new TableColumn<FicheNavette, JFXCheckBox>("Accpt");

		// width of column of table
		// typeColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.07));

		dateColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.1));
		fullNameColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.13));
		typeColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.13));
		priceColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.07));
		quantityColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.07));
		totalePriceColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.1));
		colorColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.1));
		remarqueColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.25));
		sendColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.05));

		dateColumn.setResizable(false);
		fullNameColumn.setResizable(false);
		typeColumn.setResizable(false);
		priceColumn.setResizable(false);
		quantityColumn.setResizable(false);
		totalePriceColumn.setResizable(false);
		colorColumn.setResizable(false);
		remarqueColumn.setResizable(false);
		sendColumn.setResizable(false);

		dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory("fullName"));
		typeColumn.setCellValueFactory(new PropertyValueFactory("type"));
		priceColumn.setCellValueFactory(new PropertyValueFactory("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory("quantity"));
		totalePriceColumn.setCellValueFactory(new PropertyValueFactory("totalePrice"));
		colorColumn.setCellValueFactory(new PropertyValueFactory("color"));
		remarqueColumn.setCellValueFactory(new PropertyValueFactory("remarque"));
		sendColumn.setCellValueFactory(new PropertyValueFactory("checkBox"));

		// ajouter les colones et leur row au tableaux
		tabRecordFilter.getColumns().addAll(dateColumn, fullNameColumn, typeColumn, priceColumn, quantityColumn,
				totalePriceColumn, colorColumn, remarqueColumn, sendColumn);

	}
	
	  @FXML
	    void enableOrDisableAll(MouseEvent event) {

		  for (FicheNavette ficheNavette : arrayRecordShuttles) {
			  
			  if(!this.checkAll.isSelected()) {
				  ficheNavette.getCheckBox().setSelected(true);
			  }else {
				  ficheNavette.getCheckBox().setSelected(false);
			  }
			
		}
		  tabRecordFilter.setItems(FXCollections.observableArrayList(arrayRecordShuttles));
		  
	    }


}
