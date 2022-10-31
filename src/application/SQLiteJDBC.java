package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteJDBC {
	
	//creation patient data base
	  public Connection getConnection() {
	      Connection c = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:src/application/sqlite/cabinet_dentaire_patient.db");
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Opened database cabinet_entaire_patient successfully");
	      return c;
	   }
	  
	  // creation data base proth�se
	  public Connection getConnectionProthese() {
	      Connection c = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:src/application/sqlite/cabinet_dentaire_prothese.db");
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Opened database cabinet_entaire_prothes successfully");
	      return c;
	   }
	  
	  //creation data base Appointment
	  public Connection getConnectionAppointment() {
	      Connection c = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         c = DriverManager.getConnection("jdbc:sqlite:src/application/sqlite/appointment.db");
	      } catch ( Exception e ) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      System.out.println("Opened database appointment successfully");
	      return c;
	   }
	}