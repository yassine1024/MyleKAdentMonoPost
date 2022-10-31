package application.controllers.prothese;

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
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;

import application.FicheNavette;
import application.SQLiteJDBC;
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

public class BillController implements Initializable {

	@FXML
	private AnchorPane prothese;

	@FXML
	private TableView<FicheNavette> tabComtabilite;

	@FXML
	private JFXDatePicker dateDebutLab;

	@FXML
	private JFXDatePicker dateFinLab;

	@FXML
	private Button btnPrintBell;

	
	
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
		DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-YYYY");
		LocalDate date=dateFinLab.getValue();
		String dateFin = date.format(myFormat);
		date=dateDebutLab.getValue();
		String dateDebut = date.format(myFormat);
		if ((dateFin.compareTo(dateDebut)) >= 0) {
			
			LocalDateTime myDate = LocalDateTime.now();
			// DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			// File theDir = new File("C:\\Users\\LTO\\Desktop\\lib jar\\FARES");
			String tableTheDir[] = { "C:\\sqlite\\data\\facturation\\" + myDate.getYear(),
					"C:\\sqlite\\data\\facturation\\" + myDate.getYear() + "\\" + myDate.getMonthValue(),
					"C:\\sqlite\\data\\facturation\\" + myDate.getYear() + "\\" + myDate.getMonthValue() + "\\"
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

			String file_name = "C:\\sqlite\\data\\facturation\\" + myDate.getYear() + "\\" + myDate.getMonthValue()
					+ "\\" + myDate.getDayOfMonth() + "\\" + minuteSecondMiliSecond + ".pdf";
			Document document = new Document();

			PdfWriter.getInstance(document, new FileOutputStream(file_name));

			document.open();
			/*
			 * Paragraph para1 = new Paragraph(
			 * "فاتــــــــــــــــــــــــــــــــــــــــورة الـــــــــــــــــــــــــهـــــــاتــــــــــــــــفــــــــــــ"
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

//Paragraph p2=new Paragraph( BaseFont.IDENTITY_H,"فـــاتــــــور", catFont);
			addEmptyLine(preface, 3);

			// addEmptyLine(preface, 1);

			PdfPTable table = new PdfPTable(8);
			PdfPCell cell1 = new PdfPCell(new Phrase("Date",ordinaryFont));
			PdfPCell cell2 = new PdfPCell(new Phrase("Type",ordinaryFont));
			PdfPCell cell3 = new PdfPCell(new Phrase("Prix_Unitaire",ordinaryFont));
			PdfPCell cell4 = new PdfPCell(new Phrase("Quantité",ordinaryFont));
			PdfPCell cell5 = new PdfPCell(new Phrase("Prix_Totale",ordinaryFont));
			PdfPCell cell6 = new PdfPCell(new Phrase("Nom",ordinaryFont));
			PdfPCell cell7 = new PdfPCell(new Phrase("Tente",ordinaryFont));
			PdfPCell cell8 = new PdfPCell(new Phrase("Remarque",ordinaryFont));
			
			table.addCell(cell1);
			table.addCell(cell6);
			table.addCell(cell2);
			table.addCell(cell3);
			table.addCell(cell4);
			table.addCell(cell5);
			table.addCell(cell7);
			table.addCell(cell8);
			
			table.setHeaderRows(1);

			double totale=0.0;
			for (FicheNavette labDetaille : this.arrayRecordShuttles) {

				System.out.println("Debut "+dateDebut+" Fin "+dateFin);
				if (labDetaille.getDate().compareTo(dateDebut)>=0 && labDetaille.getDate().compareTo(dateFin)<=0 ) {

					table.addCell(labDetaille.getDate());
					table.addCell(labDetaille.getFullName());
					table.addCell(labDetaille.getType());
					table.addCell(String.format("%.2f",labDetaille.getPrice()) + "");
					table.addCell(labDetaille.getQuantity()+"");
					table.addCell(String.format("%.2f",labDetaille.getTotalePrice())+"");
					
					table.addCell(labDetaille.getColor());
					table.addCell(labDetaille.getRemarque());
					totale+=labDetaille.getTotalePrice();
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
			 * Phrase("الزمان")); PdfPCell cell2 = new PdfPCell(new Phrase("النوع"));
			 * PdfPCell cell3 = new PdfPCell(new
			 * Phrase("مبـــــــــلغ الــــــــوحـــــدة")); PdfPCell cell4 = new
			 * PdfPCell(new Phrase("الــــــكــمية")); PdfPCell cell5 = new PdfPCell(new
			 * Phrase("الــــــــــمـــــــــبـــــلغ الـــــــكــــلـــــي"));
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
			String content = "La date de début est supérieur de la date de fin!";
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

	private ArrayList<FicheNavette> arrayRecordShuttles;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		arrayRecordShuttles = new ArrayList<FicheNavette>();
		this.initializeColumnOfTable();
		this.intializeRecord();
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

		// width of column of table
		// typeColumn.prefWidthProperty().bind(tabRecordFilter.widthProperty().multiply(0.07));

		dateColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.1));
		fullNameColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.13));
		typeColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.13));
		priceColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.07));
		quantityColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.07));
		totalePriceColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.1));
		colorColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.1));
		remarqueColumn.prefWidthProperty().bind(tabComtabilite.widthProperty().multiply(0.3));

		dateColumn.setResizable(false);
		fullNameColumn.setResizable(false);
		typeColumn.setResizable(false);
		priceColumn.setResizable(false);
		quantityColumn.setResizable(false);
		totalePriceColumn.setResizable(false);
		colorColumn.setResizable(false);
		remarqueColumn.setResizable(false);

		dateColumn.setCellValueFactory(new PropertyValueFactory("date"));
		fullNameColumn.setCellValueFactory(new PropertyValueFactory("fullName"));
		typeColumn.setCellValueFactory(new PropertyValueFactory("type"));
		priceColumn.setCellValueFactory(new PropertyValueFactory("price"));
		quantityColumn.setCellValueFactory(new PropertyValueFactory("quantity"));
		totalePriceColumn.setCellValueFactory(new PropertyValueFactory("totalePrice"));
		colorColumn.setCellValueFactory(new PropertyValueFactory("color"));
		remarqueColumn.setCellValueFactory(new PropertyValueFactory("remarque"));

		// ajouter les colones et leur row au tableaux
		tabComtabilite.getColumns().addAll(dateColumn, fullNameColumn, typeColumn, priceColumn, quantityColumn,
				totalePriceColumn, colorColumn, remarqueColumn);

	}

	private void intializeRecord() {
		// TODO Auto-generated method stub

		Statement stm = null;
		ResultSet rs = null;
		String request = "";

		try {
			stm = new SQLiteJDBC().getConnectionProthese().createStatement();
			request = " SELECT R.record_shuttle_id, R.full_name, R.teinte, R.date, R.quantity, R.remarque, L.type, L.price FROM record_shuttles R INNER JOIN labo_products L ON R.labo_product_id = L.labo_prodcut_id  WHERE R.validate = 1 ";
			rs = stm.executeQuery(request);
			while (rs.next()) {

				arrayRecordShuttles.add(new FicheNavette(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getString(4), rs.getFloat(5), rs.getString(6), rs.getString(7), rs.getFloat(8)));
			}

			tabComtabilite.setItems(FXCollections.observableArrayList(arrayRecordShuttles));

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
