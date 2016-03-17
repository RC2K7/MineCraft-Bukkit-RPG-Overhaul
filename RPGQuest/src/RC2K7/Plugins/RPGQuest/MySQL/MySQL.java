package RC2K7.Plugins.RPGQuest.MySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.bukkit.Bukkit;


public class MySQL {
	
	String host;
	int port;
	String database;
	String table;
	String username;
	String password;
	
	public MySQL(String username, String password, String host, int port, String database, String table)
	{
		this.username = username;
		this.password = password;
		this.host = host;
		this.port = port;
		this.database = database;
		this.table = table;
	}
	
	public synchronized void update(String player, int experience, String inv)
	{
		try 
		{
			Class.forName("com.mysql.jdbc.Driver");
			
			String url = "jdbc:mysql://" + host + ":" + port + "/" + database;
			
			Connection con = DriverManager.getConnection(url, username, password);
			Statement st = con.createStatement();
			
			try{
			String sql = "CREATE TABLE " + this.table + " (PlayerName TEXT NOT NULL, Experience INT)";
			st.executeUpdate(sql);
			}catch(Exception e)
			{}
			
			ResultSet rs = st.executeQuery("SELECT * FROM " + this.table + " WHERE PlayerName='" + player + "'");
			
			if(rs.next() && rs.getString("PlayerName") != null)
			{
				st.executeUpdate("UPDATE " + this.table + " SET Experience=" + experience + ", Inventory='" + inv + "' WHERE PlayerName='" + player + "'");
			}else
			{
				st.executeUpdate("INSERT INTO " + this.table + " VALUES('" + player + "'," + experience + ",'" + inv + "')");
			}
			
			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			Bukkit.broadcastMessage(e.getMessage());
		}
	}

}
