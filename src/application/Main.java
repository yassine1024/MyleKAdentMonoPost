package application;

import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EnumSet;
import java.util.Set;

import animatefx.animation.FadeIn;
import application.cryptage.Cryptage;
//import developeWithSqlite.SQLiteJDBC;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

public class Main extends Application {
	
	//https://coolors.co/56e39f-59c9a5-5b6c5d-3b2c35-2a1f2d
	
	public static boolean ifStillFree=true;
	
	private static String ifLoged="/application/views/login/Signup.fxml";
	@Override
	public void start(Stage primaryStage) {
		try {
			
			
			
			/*
			 * Parent root = FXMLLoader.load(getClass().getResource("views/Home.fxml"));
			 * primaryStage.initStyle(StageStyle.TRANSPARENT); Scene scene = new
			 * Scene(root); scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			 * //scene.getStylesheets().add(getClass().getResource("application.css").
			 * toExternalForm()); primaryStage.setScene(scene); primaryStage.show();
			 */
			// ../application/views/login/
			// for launch login
			
			//String ssound = "assets//homeSound.mp3";
	        //Media sound = new Media(new File(ssound).toURI().toString());
	        //MediaPlayer mediaPlayer = new MediaPlayer(sound);
	       // mediaPlayer.play();
		
			if(checkTrialOption50Patient()) {
			this.checkSerialActivation();
			}
			//ifLoged="/application/views/login/activation.fxml";
			Parent root = FXMLLoader.load(getClass().getResource(ifLoged));
			primaryStage.initStyle(StageStyle.TRANSPARENT);
			Scene scene = new Scene(root);
			
			//begin handling
			
			final BooleanProperty ctrlPressed = new SimpleBooleanProperty(false);
			final BooleanProperty shiftPressed = new SimpleBooleanProperty(false);
			
			// Wire up properties to key events:
			scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent ke) {
					// TODO Auto-generated method stub
					//System.out.println("ke.getCode: "+ke.getCode());
					if (ke.getCode() == KeyCode.CONTROL) {
			            ctrlPressed.set(true);
			            System.out.println("PRESS ENTER");
			            if(shiftPressed.get()) {
			            	System.out.println("Control And Escape Clicked 1...................");
			            }
			        } else if (ke.getCode() == KeyCode.SHIFT) {
			            shiftPressed.set(true);
			            System.out.println("PRESS SHIFT");
			            if(ctrlPressed.get()) {
			            	System.out.println("Control And Escape Clicked 2...................");
			            }
			        }
				}
			    
			});

			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			    @Override
			    public void handle(KeyEvent ke) {
			        if (ke.getCode() == KeyCode.CONTROL) {
			            ctrlPressed.set(false);
			            System.out.println("RELEASE ENTER");
			        } else if (ke.getCode() == KeyCode.SHIFT) {
			            shiftPressed.set(false);
			            System.out.println("RELEASE SHIFT");
			        }
			    }
			});
			
			
			//end handling
			
			
			
			
			scene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			// Scene scene = new Scene(root);

			// scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			
			new FadeIn(root).play();
			
			} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

	   // try {
			/*Process p = Runtime.getRuntime().exec("reg query \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\" /v ProductId");
		
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
		    String line = "";
		    String line1= reader.readLine();
		    System.out.println("line1 "+line1);
		    String line2= reader.readLine();
		    System.out.println("line2 "+line2);
		    String line3= reader.readLine();
		    System.out.println("line3 "+line3);
		    while ((line = reader.readLine()) != null) {
		        System.out.println(line);
		    }
		    */
	  /*  } catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
*/
		// instance the dataBase in the first time
		try {
			createDB_AndTables();
			createDBProtheseAndTables();
			createDBAppointmentAndTables();
			
			createViewsDB();
			// new SQLiteJDBC().getConnectionProthese();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		launch(args);
	}
	
	private static void createViewsDB() throws SQLException {
		
		Statement stm = new SQLiteJDBC().getConnection().createStatement();
		
		// begin creation expenses_sum view
		String request = "CREATE VIEW IF NOT EXISTS expenses_sum AS "
				+ " SELECT date_heur, Sum(expense) "
				+ " FROM expenses "
				+ " GROUP BY date_heur "
				;
				

		stm.execute(request);
		System.out.println("expenses_sum view created with succes ");
		// end creation historic_users table
		
		stm.close();
		
		
		
	}

	private static void createDBProtheseAndTables() throws SQLException {
		// TODO Auto-generated method stub

		Statement stm = new SQLiteJDBC().getConnectionProthese().createStatement();

		String request = "CREATE TABLE IF NOT EXISTS labos(labo_id INTEGER PRIMARY KEY AUTOINCREMENT," + "nom TEXT,"
				+ "adresse TEXT, " + "phone TEXT );";
		stm.execute(request);

		System.out.println("Table labos created with success");
		
		 request = "CREATE TABLE IF NOT EXISTS labo_products(labo_prodcut_id INTEGER PRIMARY KEY AUTOINCREMENT," + "type TEXT,"
				+ "price REAL, " + " labo_id INTEGER, " + "FOREIGN KEY (labo_id) REFERENCES labos(labo_id)"
						+ " ON DELETE CASCADE ON UPDATE NO ACTION" + ");";
		stm.execute(request);

		System.out.println("Table labo_products created with success");
		
		 request = "CREATE TABLE IF NOT EXISTS record_shuttles(record_shuttle_id INTEGER PRIMARY KEY AUTOINCREMENT," + "full_name TEXT,"
					
				 +" teinte TEXT, remarque TEXT, date TEXT, quantity REAL, "
				 + "labo_product_id INTEGER, " + " validate INTEGER, "
				 + "FOREIGN KEY (labo_product_id) REFERENCES labo_products(labo_product_id)"
					+ " ON DELETE CASCADE ON UPDATE NO ACTION" 
					
							+ " );";
			stm.execute(request);

			System.out.println("Table record_shuttles created with success");

		request = "CREATE TABLE IF NOT EXISTS labos_data(labos_data_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "type TEXT," + "prix REAL,date TEXT,qte INTEGER," + "labo_id INTEGER, " + "FOREIGN KEY (labo_id) REFERENCES labos(labo_id)"
				+ " ON DELETE CASCADE ON UPDATE NO ACTION" + ");";

		System.out.println("Table labos_data created with success");
		stm.execute(request);
		
		
		
		stm.close();

	}
	private static void createDBAppointmentAndTables() throws SQLException {
		// TODO Auto-generated method stub

		Statement stm = new SQLiteJDBC().getConnectionAppointment().createStatement();

		String request = "CREATE TABLE IF NOT EXISTS appointment(appointment_id INTEGER PRIMARY KEY AUTOINCREMENT," + "nom TEXT,"
				+" phone TEXT, appointment_date TEXT, appointment_hour TEXT  );";
		stm.execute(request);

		System.out.println("Table appointment created with success");

		
		stm.execute(request);
		
		
		
		stm.close();

	}

	
	private static void createDB_AndTables() throws SQLException {

		SQLiteJDBC sl = new SQLiteJDBC();

		Statement stm = sl.getConnection().createStatement();

		// begin creation the user table
		String request = "CREATE TABLE IF NOT EXISTS users(" + "user_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nom_fr TEXT," 
				+ "nom_ar TEXT,"
				+ "specialty TEXT," 
				+ "specialty_ar TEXT," 
				+"address TEXT,"
				+"municipality TEXT,"
				+"town TEXT,"
				+"phone TEXT,"
				+ "mail TEXT,"
				+ "password TEXT, "
				+ "if_auth BOOLEAN, "
				+ "type INTEGER, "
				+ "password_clear TEXT)";

		stm.execute(request);
		System.out.println("users table created with succes ");
		// end creation user table
		
		

		// begin creation the historic_users table
		request = "CREATE TABLE IF NOT EXISTS historic_users(historic_users_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "user_id INTEGER," + "date_heur TEXT," + "action TEXT, "
				+ "FOREIGN KEY (user_id) REFERENCES users(user_id)" + " ON DELETE CASCADE ON UPDATE NO ACTION" + ");";

		stm.execute(request);
		System.out.println("historic_users table created with succes ");
		// end creation historic_users table
		// begin creation the historic_users table
				request = "CREATE TABLE IF NOT EXISTS tasks(task_id INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ "user_id INTEGER," + "date_heur TEXT," + "task TEXT, "
						+ "FOREIGN KEY (user_id) REFERENCES users(user_id)" + " ON DELETE CASCADE ON UPDATE NO ACTION" + ");";

				stm.execute(request);
				System.out.println("tasks table created with succes ");
				// end creation historic_users table
				// begin creation the expenses table
				request = "CREATE TABLE IF NOT EXISTS expenses(expense_id INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ "expense FLOAT," + "date_heur TEXT," + "motif TEXT); ";
						

				stm.execute(request);
				System.out.println("expenses table created with succes ");
				// end creation expenses table
				
		// begin creation table malade
		request = "CREATE TABLE IF NOT EXISTS malades(" + "malade_id INTEGER PRIMARY KEY AUTOINCREMENT," + "nom TEXT,"
				+ "prenom TEXT," + "age INTEGER," + "sexe TEXT," + "adresse TEXT," + "telephone TEXT,"
				+ "fonction TEXT,date_creation TEXT);";
		stm.execute(request);
		System.out.println("malades table created with succes ");
		
		
		
		// end creation table malade
		
		// begin creation table benefits
				request = "CREATE TABLE IF NOT EXISTS benefits(" + "date TEXT PRIMARY KEY ,"
						 + "benefit REAL );";
				stm.execute(request);
				System.out.println("benefits table created with succes ");
				
				
				
				// end creation table benefits

		// begin creation the orders table
				 request = "CREATE TABLE IF NOT EXISTS orders(" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ "malade_id INTEGER," 
						+ "file_name TEXT,"
						+ "file_path TEXT," 
						+ " FOREIGN KEY (malade_id) REFERENCES malades(malade_id)" 
						+" ON DELETE CASCADE ON UPDATE NO ACTION);"
						;

				stm.execute(request);
				System.out.println("Orders table created with succes ");
				// end creation orders table
		
		// begin creation the radios table
		 request = "CREATE TABLE IF NOT EXISTS radios(" + "id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "malade_id INTEGER," 
				+ "file_name TEXT,"
				+ "file_path TEXT," 
				+ " FOREIGN KEY (malade_id) REFERENCES malades(malade_id)" 
				+" ON DELETE CASCADE ON UPDATE NO ACTION);"
				;

		stm.execute(request);
		System.out.println("radios table created with succes ");
		// end creation radios table
		
		// begin creation table historic_malades
		request = "CREATE TABLE IF NOT EXISTS historic_malades(historic_malade_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "malade_id INTEGER,user_id INTEGER" + ",date_arriver TEXT,paye REAL, "
				+ "FOREIGN KEY (malade_id) REFERENCES malades(malade_id)" + " ON DELETE CASCADE ON UPDATE NO ACTION, "
				+ "FOREIGN KEY (user_id) REFERENCES users(user_id)" + " ON DELETE CASCADE ON UPDATE NO ACTION"

				+ ");";
		stm.execute(request);
		System.out.println("historic_malades table created with succes ");
		// end creation table historic_malades

		// begin creation table motifs
		request = "CREATE TABLE IF NOT EXISTS motifs(" + "motif_id INTEGER PRIMARY KEY AUTOINCREMENT," + "nom TEXT);";
		stm.execute(request);
		System.out.println("motifs table created with succes ");
		// end creation table motifs

		// begin creation table etatgenerets
		request = "CREATE TABLE IF NOT EXISTS etat_generets(" + "etat_generet_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nom TEXT);";

		stm.execute(request);
		System.out.println("etat_generets table created with succes ");
		// end creation table etatgenerets

		// begin creation table consultations
		request = "CREATE TABLE IF NOT EXISTS consultations(" + "consultation_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "malade_id INTEGER," + "etat_generet_id INTEGER," + "date_consultation TEXT," + "motif_id INTEGER,"
				+ "FOREIGN KEY (malade_id) REFERENCES malades(malade_id)" + " ON DELETE CASCADE ON UPDATE NO ACTION,"
				+ " FOREIGN KEY (etat_generet_id) REFERENCES etat_generets (etat_generet_id)"
				+ " ON DELETE CASCADE ON UPDATE NO ACTION," + " FOREIGN KEY (motif_id) REFERENCES motifs (motif_id)"
				+ " ON DELETE CASCADE ON UPDATE NO ACTION);";
		stm.execute(request);
		System.out.println("consultations table created with succes ");
		// end creation table consultations

		//
		// begin creation table diagnostic
		request = "CREATE TABLE IF NOT EXISTS diagnostics(" + "diagnostic_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "consultation_id INTEGER," + "diagnostique TEXT," + "traitement TEXT," + "devis REAL,"
				+ "num_diagnostique INTEGER,"
				+ "FOREIGN KEY (consultation_id) REFERENCES consultations (consultation_id)"
				+ " ON DELETE CASCADE ON UPDATE NO ACTION);";

		stm.execute(request);
		System.out.println("diagnostics table created with succes ");
		// end creation table diagnostic

		// begin creation table diagnostics_detaille
		request = "CREATE TABLE IF NOT EXISTS diagnostics_detaille(diagnostic_detaille_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "diagnostic_id INTEGER," + "acte TEXT," + "paye REAL," + "date_payement TEXT,"

				+ "FOREIGN KEY (diagnostic_id) REFERENCES diagnostics(diagnostic_id)"
				+ " ON DELETE CASCADE ON UPDATE NO ACTION);";

		stm.execute(request);
		System.out.println("diagnostics_detaille table created with succes ");
		// end creation table diagnostic

		// begin creation table medications
		//

		request = "CREATE TABLE IF NOT EXISTS medications(" + "medication_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "nom TEXT);";
		stm.execute(request);
		System.out.println("medications table created with succes ");

		// end creation table medications

		// begin creation table comporte_medications
		request = "CREATE TABLE IF NOT EXISTS comporte_medications("
				+ "comporte_medication_id INTEGER PRIMARY KEY AUTOINCREMENT," +

				"medication_id INTEGER," + "consultation_id  INTEGER,"
				+ "FOREIGN KEY (medication_id) REFERENCES medications (medication_id)"
				+ " ON DELETE CASCADE ON UPDATE NO ACTION,"
				+ "FOREIGN KEY (consultation_id) REFERENCES consultations (consultation_id)"
				+ " ON DELETE CASCADE ON UPDATE NO ACTION);";

		stm.execute(request);
		System.out.println("medications table created with succes ");
		
		request = "CREATE TABLE IF NOT EXISTS activations(" + "activation_id INTEGER PRIMARY KEY AUTOINCREMENT,"
				+ "mac_address TEXT);";
		stm.execute(request);
		System.out.println("activations table created with succes ");
		// end creation table comporte_medications
		// System.out.println("tammmmmmmmmmmmmmmarrrrrraaa");

		// begin creation table payements
		/*
		 * request = "CREATE TABLE IF NOT EXISTS payements(" +
		 * "payement_id INTEGER PRIMARY KEY AUTOINCREMENT," + "diagnostic_id INTEGER," +
		 * "acte TEXT," + "num_diagnostique INTEGER," +
		 * "FOREIGN KEY (diagnostic_id) REFERENCES diagnostics (diagnostic_id)" +
		 * "ON DELETE CASCADE ON UPDATE NO ACTION);"; stm.execute(request);
		 */
		// end creation table payements
		System.out.println("all tables were created successfully");

		//checked if the user was loged
				request="SELECT * FROM users";
				ResultSet rs=stm.executeQuery(request);
				if(rs.next()) {
					ifLoged="views/login/Login.fxml";
					//use this view only for fast test
		
				}
				rs.close();
		stm.close();

	}
	
	
	private void checkSerialActivation() throws SQLException {
		
		Statement stm=null;
		ResultSet rs= null;
		
		
		String request  = "SELECT mac_address,COUNT(*)  FROM activations";
		rs= new SQLiteJDBC().getConnection().createStatement().executeQuery(request);
		
		int size =0;

		if (rs != null) 

		{

		    // moves cursor to the last row

		  size = rs.getInt(2); // get row id 

		}
		System.out.println(size);
		if(size>0) {
			boolean ifMacAddressExist= false;
			Process p;
			try {
				p = Runtime.getRuntime().exec("reg query \"HKEY_LOCAL_MACHINE\\SOFTWARE\\Microsoft\\Windows NT\\CurrentVersion\" /v ProductId");
			
				BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			    String line = "";
			    String line1= reader.readLine();
			    System.out.println("line1 "+line1);
			    String line2= reader.readLine();
			    System.out.println("line2 "+line2);
			    String line3= reader.readLine();
			    
			    String productID = Cryptage.cryptageSha(line3);
			    while(rs.next()) {
					if(rs.getString(1).equals(productID)) {
						ifMacAddressExist= true;
						break;
					}
				}
				if(!ifMacAddressExist) {
					ifLoged="/application/views/login/licenseMachine.fxml";
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		   
			
		}else {
			ifLoged="/application/views/login/licenseMachine.fxml";
		}
	}

	
private boolean checkTrialOption50Patient() throws SQLException {
		
		Statement stm=null;
		ResultSet rs= null;
		
		
		String request  = "SELECT COUNT(*)  FROM malades";
		rs= new SQLiteJDBC().getConnection().createStatement().executeQuery(request);
		
		int size =0;

		if (rs != null) 

		{

		    // moves cursor to the last row

		  size = rs.getInt(1); // get row id 

		}
		System.out.println(size);
		if(size>=50) {
			ifStillFree=false;
			return true;
			
		}
		return false;
	}

public static void setIfStillFree(boolean ifSF) {
	ifStillFree= ifSF;
}

	
}
