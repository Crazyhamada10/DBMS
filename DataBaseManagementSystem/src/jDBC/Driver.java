package jDBC;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;

//import javax.print.DocFlavor.STRING;

public class Driver implements java.sql.Driver {

	private ArrayList<String> urlNames = new ArrayList<>();
	private String dbPath;
	private String dbName;
	private String random;
	private String protocol;

	public Driver() {
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		cutUrl(url);
		/// url is wrong
		if (urlNames.size() != 3 || !urlNames.get(0).toLowerCase().equals("jdbc")
				|| !(urlNames.get(1).equals("xmldb") || urlNames.get(1).equals("jsondb")
					|| urlNames.get(1).equals("pbdb") || urlNames.get(1).equals("altdb"))
				|| !urlNames.get(2).equals("//localhost")) {
			return false;
		} else {
			//// url seems good :D
			this.protocol = urlNames.get(1);
			return true;
		}
	}

	@Override
	public Connection connect(String url, Properties info) throws SQLException {
		/*
		 * Attempts to make a database connection to the given URL. The driver
		 * should return "null" if it realizes it is the wrong kind of driver to
		 * connect to the given URL. This will be common, as when the JDBC
		 * driver manager is asked to connect to a given URL it passes the URL
		 * to each loaded driver in turn. The driver should throw an
		 * SQLException if it is the right driver to connect to the given URL
		 * but has trouble connecting to the database.
		 */
		// checking the database and creating it
		if (acceptsURL(url)) {
			cutPath(info.get("path").toString());
			return (new jDBC.Connection(this, dbPath, dbName, random, protocol));
		}
		return null;
	}

	// get path where directory exists and database name and directory name
	private void cutPath(String path) {
		int token = 0;
		urlNames = new ArrayList<>();
		StringBuilder cut = new StringBuilder();
		int i;
		for (i = path.length() - 1; i >= 0 && token < 2; i--) {
			for (int j = i ; j >= 0 && path.charAt(j) != File.separatorChar ; j--,i--) {
				cut.append(path.charAt(j));
			}
			urlNames.add(cut.reverse().toString());
			cut = new StringBuilder();
			token++;
		}
		dbPath = path.substring(0, i + 1);
		dbName = urlNames.get(1);
		random = urlNames.get(0);
	}

	private void cutUrl(String url) {
		/// string is token jdbc:Subprotocol:Databasename
		/// arraylist (jdbc,Subprotocol,Databasename) is made
		urlNames = new ArrayList<>();
		StringBuilder cut = new StringBuilder();
		for (int i = 0; i < url.length(); i++) {
			if (url.charAt(i) == ':') {
				urlNames.add(cut.toString());
				cut = new StringBuilder();
			} else {
				cut.append(url.charAt(i));
			}
		}
		urlNames.add(cut.toString());
		cut = new StringBuilder();
	}

	@Override
	public int getMajorVersion() {
		return -1;
	}

	@Override
	public int getMinorVersion() {
		return -1;
	}

	@Override
	public Logger getParentLogger() throws UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		return new DriverPropertyInfo[0];
	}

	@Override
	public boolean jdbcCompliant() {
		return false;
	}

}
