package fileManipulator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

import accessories.SQLExceptions;
import accessories.TableValues;

public abstract class FileManipulation {

	private File programFile;
	
	protected FileManipulation() {
		
	}

	protected void setPath(String directoryPath , String directoryName , String random) throws SQLException {
		File upper = new File(directoryPath);
		if(!upper.exists())
			SQLExceptions.pathError();
		else{
			File[] level1 = upper.listFiles();
			for (File f1 : level1) {
				if (f1.getName().equals(directoryName)) {
					File[] level2 = f1.listFiles();
					for(File f2 : level2){
						if(f2.getName().equals(random)){
							return;
						}
					}
					File f2 = new File(f1.getAbsolutePath() + File.separator + random);
					f2.mkdirs();
					this.programFile = f2;
					return;
				}
			}
			File f1 = new File(directoryPath + File.separator + directoryName);
			f1.mkdirs();
			File f2 = new File(f1.getAbsolutePath() + File.separator + random);
			f2.mkdirs();
			programFile = f2;
		}
	}
	
	protected void createDataBaseFile(String name) throws SQLException {	
		File found = getFile(name, programFile);
		if (found != null) {
			deleteDir(found);
		}
		File newFile = new File(programFile.getPath() + File.separator + name);
		newFile.mkdirs();
//		int overwrite = 0;
//		if (found != null) {
//			overwrite = JOptionPane.showConfirmDialog(
//							null,
//							"Database already exists do you want overwrite?",
//							"File Exists",
//							JOptionPane.YES_NO_OPTION
//					);
//		}
//		if (found != null && overwrite == JOptionPane.YES_OPTION) {
//			deleteDir(found);
//		}
//		if (found == null || overwrite == JOptionPane.YES_OPTION) {
//			File newFile = new File(programFile.getPath() + File.separator + name);
//			newFile.mkdirs();
//		}
	}
	
	protected void checkDataBaseExistance(String name) throws SQLException {
		File database = getFile(name, programFile);
		if (database == null) {
			SQLExceptions.unfoundDataBase();
		}
	}

	private File getFile(String name, File continer) throws SQLException {
		File[] data = continer.listFiles();
		for (File db : data) {
			if (db.getName().equals(name)) {
				return db;
			}
		}
		return null;
	}

	protected void deleteDatabase(String dataBaseName) throws SQLException {
		File databaseFile = getFile(dataBaseName, programFile);
		if (databaseFile != null) {
			deleteDir(databaseFile);
		} else {
			SQLExceptions.unfoundDataBase();			
		}
	}
	
	protected void deleteTable(String dataBaseName, String tableName) throws SQLException {
		File databaseFile = getFile(dataBaseName, programFile);
		if (databaseFile != null) {
			File tableFile = getFile(tableName, databaseFile);
			if (tableFile != null) {
				deleteDir(tableFile);
			} else {
				SQLExceptions.unfoundTable();				
			}
		} else {
			SQLExceptions.unfoundDataBase();			
		}
	}
	
	private void deleteDir(File file) {
		File[] contents = file.listFiles();
		if (contents != null) {
			for (File f : contents) {
				deleteDir(f);
			}
		}
		file.delete();
	}
	
	protected File createTableFile(String databaseName, String tableName, String extension) throws SQLException {
		File database = getFile(databaseName, programFile);
		if (database == null) {
			SQLExceptions.fileNotFound();
		}
		File[] tableFiles = database.listFiles();
		for (File tableFile : tableFiles) {
			if (tableFile.getName().equals(tableName)) {
				SQLExceptions.throwExistingTable();
			}
		}
		File tableFolder = new File(database.getPath() + File.separator + tableName);
		tableFolder.mkdirs();
		String path = tableFolder.getPath() + File.separator + tableName + extension;
		return new File(path);
	}

	protected File GetDTDFileToUpdate(String databaseName, String tableName) throws SQLException {
		File database = getFile(databaseName, programFile);
		if (database == null) {
			SQLExceptions.unfoundFile();	
		}
		File tableFolder = getFile(tableName, database);
		if (tableFolder != null) {
			File table = getFile(tableName + ".dtd", tableFolder);
			if (table != null) {
				deleteDir(table);
				String path = tableFolder.getPath() + File.separator + tableName + ".dtd";
				File temp = new File(path);
				return temp;
			} else {
				SQLExceptions.unfoundTable();				
			}
		} else {
			SQLExceptions.unfoundTable();			
		}
		return null;
	}
	
	protected File CreatNewDTDFile(String databaseName, String tableName) throws SQLException {
		File database = getFile(databaseName, programFile);
		if (database == null) {
			SQLExceptions.unfoundFile();
		}
		File tableFolder = getFile(tableName, database);
		if (tableFolder != null) {
			File table = getFile(tableName + ".dtd", tableFolder);
			if (table != null) {
				deleteDir(table);
			}
			String path = tableFolder.getPath() + File.separator + tableName + ".dtd";
			return new File(path);
		} else {
			SQLExceptions.unfoundTable();			
		}
		return null;
	}
	
 	protected File GetTableFileToUpdate(String databaseName, String tableName, String extension) throws SQLException {
		File database = getFile(databaseName, programFile);
		if (database == null) {
			SQLExceptions.unfoundFile();	
		}
		File tableFolder = getFile(tableName, database);
		if (tableFolder != null) {
			File table = getFile(tableName + extension, tableFolder);
			if (table != null) {
				deleteDir(table);
				String path = tableFolder.getPath() + File.separator + tableName + extension;
				File temp = new File(path);
				return temp;
			} else {
				SQLExceptions.unfoundTable();				
			}
		} else {
			SQLExceptions.unfoundTable();			
		}
		return null;
	}
	
	protected File GetTableFileToView(String databaseName, String tableName, String extension) throws SQLException{
		File databaseFile = getFile(databaseName, programFile);
		if (databaseFile != null) {
			File tableFile = getFile(tableName, databaseFile);
			if (tableFile != null) {
				return getFile(tableName + extension, tableFile);
			} else {
				SQLExceptions.unfoundTable();
			}
		} else {
			SQLExceptions.unfoundDataBase();			
		}
		return null;
	}

	protected void createDTD(File file, TableValues table) throws SQLException{
		String path = file.getAbsolutePath();
		String tableName = table.getTableName();
		ArrayList<String> colNames = table.getcolNames();
		PrintWriter writer = null;
		File dtd = new File(path);
		try {
			writer = new PrintWriter(dtd);
		} catch (FileNotFoundException e) {
			SQLExceptions.fileNotFound();
		}
		writeHard(writer, tableName, colNames);
		for (int i = 0; i < colNames.size(); i++) {
			writer.println("<!ELEMENT " + colNames.get(i) + " (#PCDATA)>");
		}
		for (int i = 0; i < colNames.size(); i++) {
			writer.println("<!ATTLIST " + tableName + " col" + (i + 1) + " CDATA #REQUIRED>");
		}
		for (int i = 0; i < colNames.size(); i++) {
			writer.println("<!ATTLIST " + tableName + " colType" + (i + 1) + " CDATA #REQUIRED>");
		}
		writer.println("<!ATTLIST " + tableName + " nomOfCols CDATA #REQUIRED>");
		writer.println("<!ATTLIST " + tableName + " nomOfRows CDATA #REQUIRED>");
		writer.println("<!ATTLIST ID IDValue CDATA #REQUIRED>");
		writer.close();
	}

	private void writeHard(PrintWriter writer, String tableName, ArrayList<String> colNames) {
		writer.print("");
		writer.println("<!ELEMENT " + tableName + " (ID+)>");
		writer.print("<!ELEMENT ID (");
		for (int i = 0; i < colNames.size(); i++) {
			writer.print(colNames.get(i));
			if (i != colNames.size() - 1)
				writer.print("+, ");
			else
				writer.println("+)>");
		}
	}
	
}
