package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;
import java.sql.DriverManager;
import java.io.IOException;
import java.sql.SQLException;

public class JdbcConnectUtil {

	private static JdbcConnectUtil util;
	private static Properties props = new Properties();
	
	private JdbcConnectUtil() {}
	
	public static JdbcConnectUtil getInstance() {
		if(util == null) {
			util = new JdbcConnectUtil();
		}
		return util;
	}
	
	//
	public Connection getConnection() {
		Connection con = null;
		try {
				ClassLoader classLoader = getClass().getClassLoader();
				InputStream in = classLoader.getResourceAsStream("jdbc.properties");
			
				String url="";
				String username="";
				String password="";
			
				props.load(in);
				url = props.getProperty("url");
				username = props.getProperty("username");
				password = props.getProperty("password");
				con = DriverManager.getConnection(url, username, password);
			
		}catch(IOException e) {
			e.printStackTrace();
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return con;
	}
}
