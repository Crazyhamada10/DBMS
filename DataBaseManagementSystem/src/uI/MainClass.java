package uI;

import jDBC.Connection;
import jDBC.Driver;
import jDBC.ResultSet;
import jDBC.ResultSetMetaData;
import jDBC.Statement;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;

public class MainClass {

	public static void main(String args[]) {
		MainClass T = new MainClass();
		Scanner in = new Scanner(System.in);
		Connection connection = T.makeConnection(T.getProtocol(in));
		Statement statement = null;
		try {
			statement = (Statement) connection.createStatement();
		} catch (SQLException e1) {
			System.out.println(e1.getMessage());
		}
		while (true) {
			T.go();
			if (in.hasNext()) {
				String syntax = new String(in.nextLine().toString());
				if (!syntax.trim().equals("")) {
					try {
						statement.execute(syntax);
						if(statement.getResultSet()!=null){
							ResultSet resultset = (ResultSet) statement.getResultSet();
							ResultSetMetaData meta = resultset.getMetaData();
							ArrayList<ArrayList<Object>> table = meta.getTable();
							TextOptimizer.getInstance().drawTable(meta.getColNames(), table);
						}
					} catch (Exception e) {
						System.out.println(e.getMessage());
					}
				}
			}
		}
	}
	
	private String getProtocol(Scanner in){
		go();
		System.out.print("Enter protocol: ");
		String ret = new String();
		while(true){
			String protocol = in.next();
			if(protocol.toLowerCase().equals("xml")) {
				ret = "xmldb";				
			} else if(protocol.toLowerCase().equals("json")) {
				ret = "jsondb";				
			} else if(protocol.toLowerCase().equals("pb")) {
				ret = "pbdb";
			} else {
				System.out.println("Unknown protocol type");				
			}
			return ret;
		}
	}
	
	private Connection makeConnection(String protocol){
		Driver driver = new Driver();
		Properties info = new Properties();
		File dbDir = new File(System.getProperty("java.io.tmpdir") + File.separator + 
				"jdbc" + File.separator + Math.round((((float) Math.random()) * 100000)));
		info.put("path", dbDir.getAbsoluteFile());
		Connection connection = null;
		try {
			connection = (Connection) driver.connect("jdbc:" + protocol + "://localhost", info);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}	
		return connection;
	}
	
	private void go(){
		System.out.print(">> ");
	}
}
