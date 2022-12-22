package application.controllers.statistics;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import application.Benefit;
import application.FicheNavette;
import application.SQLiteJDBC;
import application.controllers.login.ActivationController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class BenefitController implements Initializable {

	@FXML
	private AnchorPane prothese;

	@FXML
	private TableView<Benefit> tabComtabilite;

	@FXML
	private JFXDatePicker dateDebutLab;

	@FXML
	private JFXDatePicker dateFinLab;

	@FXML
	private Button btnPrintBell;

	
	@FXML
    private Text tfTotaleBenefit;

    @FXML
    private Text tfBeneficeMensuelle;
    
    @FXML
    private JFXComboBox<String> comboBoxSelectUser;

	Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
	Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	Font ordinaryFont = new Font(Font.FontFamily.TIMES_ROMAN, 14);

	Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	// Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
	
	
	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}
	
	
	@FXML
	private StackPane stackPane;
	
	@FXML
	void facturer(MouseEvent event) throws DocumentException, IOException {
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("YYYY-MM-dd");
		LocalDate date=dateFinLab.getValue();
		String dateFin = date.format(myFormat);
		date=dateDebutLab.getValue();
		String dateDebut = date.format(myFormat);
		if ((dateFin.compareTo(dateDebut)) >= 0) {
			
			LocalDateTime myDate = LocalDateTime.now();
			// DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// File theDir = new File("C:\\Users\\LTO\\Desktop\\lib jar\\FARES");
			String tableTheDir[] = { "assets\\sqlite\\data\\facturation\\" + myDate.getYear(),
					"assets\\sqlite\\data\\facturation\\" + myDate.getYear() + "\\" + myDate.getMonthValue(),
					"assets\\sqlite\\data\\facturation\\" + myDate.getYear() + "\\" + myDate.getMonthValue() + "\\"
							+ myDate.getDayOfMonth() };

			// if the directory does not exist, create it
			// ********************begins of creation directories***************/
			for (String string : tableTheDir) {
				File theDir = new File(string);
				if (!theDir.exists()) {
					System.out.println("creating directory: " + theDir.getName());
					boolean result = false;

					try {
						theDir.mkdir();
						result = true;
					} catch (SecurityException se) {
						// handle it
					}
					if (result) {
						System.out.println("DIR created");
					}
				}
			}
			// ##################"end of creating of directories##############

			// *******************begins of creating the PDF Document***********/

			String minuteSecondMiliSecond = myDate.getHour() + "" + myDate.getMinute() + "" + myDate.getSecond() + ""
					+ myDate.getNano();

			String file_name = "assets\\sqlite\\data\\facturation\\" + myDate.getYear() + "\\" + myDate.getMonthValue()
					+ "\\" + myDate.getDayOfMonth() + "\\" + minuteSecondMiliSecond + ".pdf";
			Document document = new Document();

			PdfWriter.getInstance(document, new FileOutputStream(file_name));

			document.open();
			/*
			 * Paragraph para1 = new Paragraph(
			 * "Ù�Ø§ØªÙ€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€ÙˆØ±Ø© Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù‡Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ø§ØªÙ€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù�Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€"
			 * );
			 * 
			 * document.add(para1);
			 * 
			 */

			Paragraph preface = new Paragraph();
			// We add one empty line ===> add jump
			// preface.setAlignment(Element.ALIGN_RIGHT);
			// preface.setAlignment(Element.ALIGN_CENTER);
			addEmptyLine(preface, 1);
			// Lets write a big header
			Paragraph p = new Paragraph("Facture", headerFont);
			p.setAlignment(Element.ALIGN_CENTER);
			Paragraph pNameLab = new Paragraph(dateDebut + "  jusqu'au  " + dateFin, redFont);
			Paragraph pNumBill = new Paragraph("facture N:09542", redFont);
			// Paragraph pDate = new Paragraph(dateDebut+" jusqu'au "+dateFin, redFont);

			p.setAlignment(Element.ALIGN_CENTER);

			pNameLab.setAlignment(Element.ALIGN_LEFT);
			pNumBill.setAlignment(Element.ALIGN_LEFT);
			// pDate.setAlignment(Element.ALIGN_RIGHT);
			Paragraph pPrincipale = new Paragraph();

			pPrincipale.add(pNameLab);
			pPrincipale.add(pNumBill);
			// pPrincipale.add(pDate);
			pPrincipale.setAlignment(Element.ALIGN_JUSTIFIED_ALL);

			// preface.add(pNameLab);

			//
			preface.add(pPrincipale);
			// preface.add(pNumBill);

			preface.add(p);
			// preface.add(pDate);
			// preface.addAll({pNameLab,pNumBill,p,pDate})
			// preface.add(p);

//Paragraph p2=new Paragraph( BaseFont.IDENTITY_H,"Ù�Ù€Ù€Ù€Ø§ØªÙ€Ù€Ù€Ù€Ù€Ù€ÙˆØ±", catFont);
			addEmptyLine(preface, 3);

			// addEmptyLine(preface, 1);

			PdfPTable table = new PdfPTable(2);
			PdfPCell cell1 = new PdfPCell(new Phrase("Date",ordinaryFont));
			PdfPCell cell2 = new PdfPCell(new Phrase("Bénéfice",ordinaryFont));
			
			table.addCell(cell1);
			
			table.addCell(cell2);
			
			
			table.setHeaderRows(1);

			double totale=0.0;
			
			
			int userIdAfterParse=0;
			boolean ifAll=false;
			if(this.comboBoxSelectUser.getValue().equals("Afficher tout")) {
				ifAll= true;
			}else {
				userIdAfterParse= Integer.parseInt( this.comboBoxSelectUser.getValue().split("_")[0]);
			}
			
			for (Benefit labDetaille : this.arrayRecordShuttles) {

				System.out.println("Debut "+dateDebut+" Fin "+dateFin);
				
				if (labDetaille.getDate().compareTo(dateDebut)>=0 && labDetaille.getDate().compareTo(dateFin)<=0 ) {

					table.addCell(labDetaille.getDate());
					table.addCell(labDetaille.getBenefit()+"");
					totale+=labDetaille.getBenefit();
					
				}
			}

			PdfPCell cellNull = new PdfPCell(new Paragraph(""));
			table.addCell(cellNull);
			table.addCell(cellNull);
			table.addCell(cellNull);
			table.addCell(cellNull);
			table.addCell(cellNull);
			table.addCell(cellNull);
			table.addCell(new Paragraph("TOTALE: ",smallBold));
			table.addCell(new Paragraph(String.format("%.2f",totale),smallBold));
			
			document.add(preface);
			document.add(table);
//413
			/*
			 * PdfPTable table = new PdfPTable(5); PdfPCell cell1 = new PdfPCell(new
			 * Phrase("Ø§Ù„Ø²Ù…Ø§Ù†")); PdfPCell cell2 = new PdfPCell(new Phrase("Ø§Ù„Ù†ÙˆØ¹"));
			 * PdfPCell cell3 = new PdfPCell(new
			 * Phrase("Ù…Ø¨Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù„Øº Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€ÙˆØ­Ù€Ù€Ù€Ù€Ù€Ø¯Ø©")); PdfPCell cell4 = new
			 * PdfPCell(new Phrase("Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€ÙƒÙ€Ù€Ù…ÙŠØ©")); PdfPCell cell5 = new PdfPCell(new
			 * Phrase("Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù…Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ù€Ø¨Ù€Ù€Ù€Ù€Ù€Ù„Øº Ø§Ù„Ù€Ù€Ù€Ù€Ù€Ù€Ù€ÙƒÙ€Ù€Ù€Ù€Ù„Ù€Ù€Ù€Ù€Ù€ÙŠ"));
			 * table.addCell(cell1); table.addCell(cell2); table.addCell(cell3);
			 * table.addCell(cell4); table.addCell(cell5); table.setHeaderRows(1); for
			 * (LabDetaille labDetaille : this.reduceLabDetailleArray) {
			 * table.addCell(labDetaille.getDate()); table.addCell(labDetaille.getType());
			 * table.addCell(labDetaille.getPrixUnitaire() + "");
			 * table.addCell(labDetaille.getQte() + "");
			 * table.addCell(labDetaille.getPrixGlobal() + ""); }
			 * 
			 * document.add(table);
			 */
			document.close();
			// System.out.println()

			// **********************Begin open the PDF
			// File*********************************
			File myFile = new File(file_name);
			Desktop.getDesktop().open(myFile);
			// ######################End of the open PDF
			// File#################################

			System.out.println("Finished");

			// ###################End of creating the PDF Document################
		} else {
			String content = "La date de dÃ©but est supÃ©rieur de la date de fin!";
			/*
			 * Notifications notification =
			 * Notifications.create().title("Echec").text(content).graphic(null)
			 * .hideAfter(Duration.seconds(2)).position(Pos.BOTTOM_CENTER) .onAction(new
			 * EventHandler<ActionEvent>() {
			 * 
			 * public void handle(ActionEvent arg0) { // TODO Auto-generated method stub
			 * System.out.println("notification"); } }); notification.show();
			 */
			JFXDialogLayout layout = new JFXDialogLayout();
			layout.setHeading(new Text("Echec"));
			layout.setBody(new Text(content));

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
	}

	private ArrayList<Benefit> arrayRecordShuttles;

	
public void setLightDarkMode() {
		
		if(!ActivationController.getMode()) {
			System.out.println("Change mode...");
			this.stackPane.getStylesheets().clear();
			
			this.stackPane.getStylesheets().add("/css/light_mode/buttonSyle.css");
			this.stackPane.getStylesheets().add("/css/light_mode/tableView.css");
			this.stackPane.getStylesheets().add("/css/light_mode/styles.css");
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		arrayRecordShuttles = new ArrayList<Benefit>();
		this.initializeColumnOfTable();
		this.intializeRecord();
		this.initializeComboBoxSelelctUser();
		
		this.setLightDarkMode();
	}

	private void initializeColumnOfTable() {
		// initialiser tableau diagnostique
		TableColumn<Benefit, String> dateColumn = new TableColumn<Benefit, String>("Date");
		TableColumn<Benefit, Double> benefitColumn = new TableColumn<Benefit, Double>("Bénéfice");
		

		// width of column of table
		// typeColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.07));

		dateColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.6));
		benefitColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.4));
		
		dateColumn.setResizable(false);
		benefitColumn.setResizable(false);
		

		dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
		benefitColumn.setCellValueFactory(new PropertyValueFactory("benefit"));
		
		// ajouter les colones et leur row au tableaux
		tabComtabilite.getColumns().addAll(dateColumn, benefitColumn);
		
		this.setLightDarkMode();

	}

	private void intializeRecord() {
		// TODO Auto-generated method stub

		Statement stm = null;
		ResultSet rs = null;
		String request = "";
		double totaleBenefit=0;
		int counter=0;
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = " SELECT date,benefit FROM benefits ; ";
			rs = stm.executeQuery(request);
			
			while (rs.next()) {

				arrayRecordShuttles.add(new Benefit(rs.getDouble(2), rs.getString(1)));
				//System.out.println(rs.getDouble(2)+"  "+ rs.getString(1));
				totaleBenefit+=rs.getDouble(2);
				counter++;
			}

			this.tfTotaleBenefit.setText(totaleBenefit+"");
			this.tfBeneficeMensuelle.setText(totaleBenefit/counter+"");
			tabComtabilite.setItems(FXCollections.observableArrayList(arrayRecordShuttles));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
private void initializeComboBoxSelelctUser() {
		
		
		Statement stm = null;
		ResultSet rs = null;
		String request = null;
String index="";
		ArrayList<String> cBSU= new ArrayList<String>();
		try {
			stm = new SQLiteJDBC().getConnection().createStatement();
			request = "SELECT user_id,nom_fr,specialty FROM users";
			rs = stm.executeQuery(request);
			int i=0;
			while (rs.next()) {
				i++;

				cBSU.add(rs.getInt(1)+"_"+rs.getString(2)+"_"+rs.getString(3));
				if(rs.getInt(1)== ActivationController.userId) {
					index = rs.getInt(1)+"_"+rs.getString(2)+"_"+rs.getString(3);
				}
			}
			cBSU.add("Afficher tout");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		this.comboBoxSelectUser.setItems(FXCollections.observableArrayList(cBSU));
		this.comboBoxSelectUser.setValue(index);

		
	}


	//This uses for uploading content every change the user
	@FXML
	void slelectionnerUser(ActionEvent event) {
	
		
		
		System.out.println(ActivationController.userId);
		
	}
	
}
