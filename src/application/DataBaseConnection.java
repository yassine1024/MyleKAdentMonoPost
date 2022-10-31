package application;

import java.sql.*;
import java.util.Hashtable;

import com.mysql.cj.jdbc.MysqlDataSource;

public class DataBaseConnection {

	public Hashtable<String, Boolean> hashTableSerials;
	
	private String user = "u171516181_kadent_user";
	private String passw = "kachiR09@";
	private String serverName = "145.14.151.151";
	private int port = 3306;
	private String dbName = "u171516181_kadent_db";
	public Connection conn;
	private MysqlDataSource datasource;

	public DataBaseConnection() {
		this.hashTableSerials= new Hashtable<String, Boolean>();
		
		datasource = new MysqlDataSource();
		datasource.setPassword(passw);
		datasource.setUser(user);
		datasource.setServerName(serverName);
		datasource.setDatabaseName(dbName);
		datasource.setPort(port);

		
		try {
			this.conn = this.datasource.getConnection();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM activations");
			System.out.println("Connexion faite avec succée");
			while (rs.next()) {
				//System.out.println("Activations key: " + rs.getString(1)+" Activation "+rs.getBoolean(2));
			this.hashTableSerials.put(rs.getString(1), rs.getBoolean(2));
			
			System.out.println("hhhhhhhhhh  "+this.hashTableSerials.get(rs.getString(1)));
			
			}
			rs.close();
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void updateSerial(String serial) {
		
		try {
			this.conn = this.datasource.getConnection();
			Statement stmt = conn.createStatement();
			stmt.execute("UPDATE activations SET status= '"+1+"' WHERE serial = '"+serial+"'");
			
			stmt.close();
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public Hashtable<String, Boolean> getHashTableSerials() {
		return hashTableSerials;
	}
	
	

	/*public void getConn() throws SQLException {
		
	}
	*/
}
