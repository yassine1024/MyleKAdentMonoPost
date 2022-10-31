package application.controllers.prothese;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;

import com.google.zxing.NotFoundException;
import com.google.zxing.WriterException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.validation.RequiredFieldValidator;

import application.Labo;
import application.MyQr;
import application.SQLiteJDBC;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class RecordShuttleController implements Initializable {

	private Hashtable<String, Integer> typeOfJob;
	private ArrayList<String> fullNameList;
	private int labID;

	public RecordShuttleController(int labID, ArrayList<String> fullNameList) {
		// TODO Auto-generated constructor stub

		this.labID = labID;
		this.fullNameList = fullNameList;
		this.typeOfJob = new Hashtable<String, Integer>();

	}

	@FXML
	void changePositionFullName(KeyEvent event) {

		switch (event.getCode()) {
		case DOWN:

			this.type.requestFocus();
			break;

		default:
			break;
		}
	}

	@FXML
	void changePositionQte(KeyEvent event) {

		switch (event.getCode()) {
		case DOWN:

			this.remarque.requestFocus();
			break;

		case UP:
			this.color.requestFocus();;
			break;
		}
	}

	@FXML
	void changePositionRemarq(KeyEvent event) {

		switch (event.getCode()) {
		case UP:

			this.quantity.requestFocus();
			break;

		case ENTER:
			this.saveRecordShuttles(null);
			break;
		}
		
	}

	@FXML
	void changePositionTeinte(KeyEvent event) {

		switch (event.getCode()) {
		case DOWN:

			this.quantity.requestFocus();
			break;

		case UP:
			this.type.requestFocus();;
			break;
		}
		
	}

	@FXML
	void changePositionType(KeyEvent event) {

		switch (event.getCode()) {
		case DOWN:

			this.color.requestFocus();
			break;

		case UP:
			this.fullName.requestFocus();;
			break;
		}
		
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub

		System.out.println("keyGen Record " + this.labID);

		TextFields.bindAutoCompletion(this.fullName, this.fullNameList);
		initializeType();
		TextFields.bindAutoCompletion(this.type, Collections.list(this.typeOfJob.keys()));
		initializeValidators();

	}

	private void initializeValidators() {
		// TODO Auto-generated method stub

		// validate type
		RequiredFieldValidator validatorType = new RequiredFieldValidator();

		this.type.getValidators().add(validatorType);
		validatorType.setMessage("Ce type de travail n'éxiste plus");

		this.type.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {// when focus lost
					if (!typeOfJob.containsKey(type.getText())) {// !textField.getText().matches("[1-5]\\.[0-9]|6\\.0")
						type.setText("");

					}
					type.validate();

				}
			}
		});

		// validate number
		RequiredFieldValidator validatorQuantity = new RequiredFieldValidator();

		this.quantity.getValidators().add(validatorQuantity);
		validatorQuantity.setMessage("Il faut un numéro");

		this.quantity.focusedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				// TODO Auto-generated method stub
				if (!newValue) {// when focus lost
					if (!quantity.getText().matches("[+-]?\\d+(?:\\.\\d+)?")) {// !textField.getText().matches("[1-5]\\.[0-9]|6\\.0")
						quantity.setText("");

					}
					quantity.validate();

				}
			}
		});

	}

	private void initializeType() {
		// TODO Auto-generated method stub

		Statement stm = null;
		ResultSet rs = null;

		try {
			stm = new SQLiteJDBC().getConnectionProthese().createStatement();
			String request = "SELECT lp.labo_prodcut_id, lp.type FROM labo_products lp,labos l WHERE lp.labo_id= '"
					+ this.labID + "' AND lp.labo_id=l.labo_id ; ";

			rs = stm.executeQuery(request);
			while (rs.next()) {

				this.typeOfJob.put(rs.getString(2), rs.getInt(1));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println(e.getMessage());

		} finally {
			try {
				rs.close();
				stm.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

	@FXML
	private JFXTextField fullName;

	@FXML
	private JFXTextField type;

	@FXML
	private JFXTextField color;

	@FXML
	private JFXTextArea remarque;
	@FXML
	private JFXTextField quantity;

	@FXML
	private JFXButton jfxButtonSaveRecordShuttles;
	
	@FXML
	void saveRecordShuttles(ActionEvent event) {
		
		
		

		if (!this.type.validate() || !this.quantity.validate()) {
			System.out.println("incorrect type");
		} else {

			PreparedStatement stmt = null;
			ResultSet rs = null;
			String request = "";
			LocalDateTime myDate = LocalDateTime.now();
			DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("dd-MM-YYYY");
			request = "INSERT INTO record_shuttles(full_name,labo_product_id,teinte,quantity,date,remarque,validate) "
					+ " VALUES(?,?,?,?,?,?,?);";
			try {
				stmt = new SQLiteJDBC().getConnectionProthese().prepareStatement(request,
						Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, this.fullName.getText());
				System.out.println(this.typeOfJob.get(this.type.getText()) + " LLLLLLLLabbb");
				stmt.setInt(2, this.typeOfJob.get(this.type.getText()));
				stmt.setString(3, this.color.getText());
				stmt.setFloat(4, Float.parseFloat(this.quantity.getText()));
				stmt.setString(5, myDate.format(myFormat));
				stmt.setString(6, this.remarque.getText());
				stmt.setInt(7, 0);

				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				// System.out.println(+" KeyGen");
				int numberRecord = rs.getInt(1);

				rs.close();
				stmt.close();

				request = "SELECT * FROM labos WHERE labo_id = ? ;";

				stmt = new SQLiteJDBC().getConnectionProthese().prepareStatement(request);
				stmt.setInt(1, this.labID);
				rs = stmt.executeQuery();
				Labo infLabo = null;
				if (rs.next()) {
					infLabo = new Labo(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4));
				}

				rs.close();
				stmt.close();

				try {
					this.printRecordShuttle(numberRecord, infLabo, myDate.format(myFormat));
				} catch (DocumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				try {
					rs.close();
					stmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		Stage stage = (Stage) this.jfxButtonSaveRecordShuttles.getScene().getWindow();
		stage.close();
		

	}

	Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 24, Font.BOLD);
	Font catFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
	Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD);
	Font ordinaryFont = new Font(Font.FontFamily.TIMES_ROMAN, 14);

	Font redFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL, BaseColor.RED);
	// Font subFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);
	Font smallBold = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);

	// print fiche navette
	void printRecordShuttle(int numberRecord, Labo infoLab, String today)
			throws DocumentException, IOException, SQLException {

		BaseFont ArialBase = BaseFont.createFont("C:\\Windows\\Fonts\\arialbd.ttf", BaseFont.IDENTITY_H,
				BaseFont.EMBEDDED);
		Font ArialFont = new Font(ArialBase, 15, Font.BOLD);
		Font ArialFontOrdinary = new Font(ArialBase, 14);

		// helper.parseXHtml(writer, document, htmlIn, in, Charset.forName("UTF-8"),
		// FontFactory.getFontImp());
		// Font arabFont = FontFactory.getFont("C:\\Windows\\Fonts\\arabtype.ttf");

		// ((FontFactory) arabFont).getFontImp().defaultEncoding = BaseFont.IDENTITY_H;

		/**********************
		 * begin generating the folders
		 ************************************/
		LocalDateTime myDate = LocalDateTime.now();
		// DateTimeFormatter myFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

		// File theDir = new File("C:\\Users\\LTO\\Desktop\\lib jar\\FARES");
		String tableTheDir[] = { "assets\\sqlite\\data\\ficheNavette\\" + myDate.getYear(),
				"assets\\sqlite\\data\\ficheNavette\\" + myDate.getYear() + "\\" + myDate.getMonthValue(),
				"assets\\sqlite\\data\\ficheNavette\\" + myDate.getYear() + "\\" + myDate.getMonthValue() + "\\"
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
		/**********************
		 * End generating the folders
		 ***************************************/

		// *******************begins of creating the PDF Document***********/

		String minuteSecondMiliSecond = myDate.getHour() + "" + myDate.getMinute() + "" + myDate.getSecond() + ""
				+ myDate.getNano();

		String file_name = "assets\\sqlite\\data\\ficheNavette\\" + myDate.getYear() + "\\" + myDate.getMonthValue() + "\\"
				+ myDate.getDayOfMonth() + "\\" + minuteSecondMiliSecond + ".pdf";

		/****************************
		 * Begin generating PDF Arret_de_travail
		 *************************/

		Document document = new Document();

		PdfWriter writer = null;
		try {
			writer = PdfWriter.getInstance(document, new FileOutputStream(file_name));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (DocumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
//open the pdf document
		document.open();

		Paragraph preface = new Paragraph();

		Paragraph p = new Paragraph();
		p.setFont(headerFont);
		p.add(new Chunk("Fiche Navette").setUnderline(0.9f, -2f));
		p.setAlignment(Element.ALIGN_CENTER);

		Paragraph cabinet = new Paragraph();
		cabinet.setFont(catFont);
		cabinet.add(new Chunk("Laboratoir Prothèse").setUnderline(0.9f, -2f));
		Paragraph cabinet2 = new Paragraph();
		cabinet2.setFont(catFont);
		cabinet2.add(new Chunk(infoLab.getName()).setUnderline(0.9f, -2f));

		PdfPTable pdfCell = new PdfPTable(2);
		pdfCell.setWidthPercentage(100);

		PdfPCell cellNL = new PdfPCell(new Paragraph(infoLab.getAddress(), ordinaryFont));

		// seconde row
		PdfPCell cellSpL = new PdfPCell(new Paragraph(infoLab.getPhone(), ordinaryFont));

		Paragraph address = null;

		if (numberRecord / 10 == 0) {
			address = new Paragraph("N°= 00000" + numberRecord, subFont);
		} else if (numberRecord / 100 == 0) {
			address = new Paragraph("N°= 0000" + numberRecord, subFont);
		} else if (numberRecord / 1000 == 0) {
			address = new Paragraph("N°= 000" + numberRecord, subFont);
		} else if (numberRecord / 10000 == 0) {
			address = new Paragraph("N°= 00" + numberRecord, subFont);
		} else if (numberRecord / 100000 == 0) {
			address = new Paragraph("N°= 0" + numberRecord, subFont);
		} else {
			address = new Paragraph("N°= " + numberRecord, subFont);
		}

		Paragraph phone = new Paragraph("Date: " + today, ordinaryFont);

		String IMG = "assets//teeth2.jpg";

		cellNL.setBorder(Rectangle.NO_BORDER);
		cellNL.setPaddingTop(25f);
		pdfCell.addCell(cellNL);
		PdfPCell cellNull = new PdfPCell(new Paragraph(""));
		cellNull.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(cellNull);

		cellSpL.setPaddingLeft(15f);

		cellSpL.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(cellSpL);

		Image img = Image.getInstance(IMG);
		img.scaleAbsolute(100f, 100f);

		PdfPCell imgCell = new PdfPCell(img);
		imgCell.setPaddingLeft(30f);
		imgCell.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(imgCell);

		PdfPCell addressCell = new PdfPCell(address);
		addressCell.setBorder(Rectangle.NO_BORDER);
		pdfCell.addCell(addressCell);
		pdfCell.addCell(cellNull);

		PdfPCell phoneCell = new PdfPCell(phone);
		phoneCell.setBorder(Rectangle.NO_BORDER);
		// phoneCell.setPaddingLeft(20f);
		pdfCell.addCell(phoneCell);
		pdfCell.addCell(cellNull);

		pdfCell.addCell(cellNull);

		Paragraph pPrincipale = new Paragraph();

		pPrincipale.add(cabinet);
		pPrincipale.add(cabinet2);
		pPrincipale.add(pdfCell);

		preface.add(pPrincipale);
		addEmptyLine(preface, 1);
		preface.add(p);

		// Paragraph preface = new Paragraph();
		// We add one empty line ===> add jump
		addEmptyLine(preface, 3);

		Paragraph pr1 = new Paragraph();
		Paragraph pr2 = new Paragraph();
		Paragraph pr3 = new Paragraph();
		Paragraph pr4 = new Paragraph();
		Paragraph pr5 = new Paragraph();
		pr1.setFont(ordinaryFont);
		pr2.setFont(ordinaryFont);
		pr3.setFont(ordinaryFont);
		pr4.setFont(ordinaryFont);
		pr5.setFont(ordinaryFont);
		pr1.add("Nom & prénom: " + this.fullName.getText());
		addEmptyLine(pr1, 1);
		pr2.add("Type de travail: " + this.type.getText());
		addEmptyLine(pr2, 1);
		pr3.add("Quantitée: " + this.quantity.getText());
		addEmptyLine(pr3, 1);
		pr4.add("Teinte: " + this.color.getText());
		addEmptyLine(pr4, 1);
		pr5.add("Remarque: " + this.remarque.getText());
		addEmptyLine(pr5, 2);
		preface.add(pr1);
		preface.add(pr2);
		preface.add(pr3);
		preface.add(pr4);
		preface.add(pr5);

		addEmptyLine(preface, 1);

		// generateQrCode
		try {
			MyQr.init(file_name, "assets/QrCode.png");
		} catch (NotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (WriterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		Image qRImage = Image
				.getInstance("assets/QrCode.png");
		qRImage.scaleAbsolute(100f, 100f);

		qRImage.setAbsolutePosition(0f, 0f);
		document.add(qRImage);

		// **********************Begin open the PDF
		// File*********************************
		File myFile = new File(file_name);
		try {
			Desktop.getDesktop().open(myFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// ######################End of the open PDF
		// File#################################

		try {
			document.add(preface);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			document.close();
		}

		// close the document

		/****************************
		 * End generating PDF FicheNavette
		 ***************************/
		
		this.jfxButtonSaveRecordShuttles.setDisable(false);
	}

	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(new Paragraph(" "));
		}
	}

}
