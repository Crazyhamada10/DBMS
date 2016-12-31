package jSON;

import java.io.File;
import java.sql.SQLException;

import fileManipulator.FileManipulation;
import fileManipulator.IFile;
import fileManipulator.IFileReader;
import fileManipulator.IFileWriter;
import accessories.TableValues;

public class JSONFile extends FileManipulation implements IFile {
	
	private static JSONFile instance;
	private IFileReader reader;
	private IFileWriter writer;
	private String JSONString;
	private String JSONExtension;
	
	private JSONFile() {
		super();
		reader = JSONReader.getInstance();
		writer = JSONWriter.getInstance();
		JSONString = new String("_JSON");
		JSONExtension = new String(".json");
	}
	
	public static JSONFile getInstance() {
		if (instance == null) {
			instance = new JSONFile();
		} 
		return instance;
	}
	@Override
	public void setPath (String directoryPath,
			String dataBaseName, String random) throws SQLException {
		super.setPath(directoryPath, dataBaseName, random);
	}
	
	
	@Override
	public void createDataBaseFile(String name) throws SQLException {
		super.createDataBaseFile(name + JSONString);
	}

	@Override
	public void checkDataBaseExistance(String name) throws SQLException {
		super.checkDataBaseExistance(name + JSONString);
	}

	@Override
	public void deleteDatabase(String dataBaseName) throws SQLException {
		super.deleteDatabase(dataBaseName + JSONString);
	}

	@Override
	public void deleteTable(String dataBaseName, String tableName)
			throws SQLException {
		super.deleteTable(dataBaseName + JSONString, tableName);
	}

	@Override
	public File GetTableFileToUpdate(String databaseName, String tableName)
			throws SQLException {
		return super.GetTableFileToUpdate(databaseName + JSONString, tableName, JSONExtension);
	}

	@Override
	public File GetTableFileToView(String databaseName, String tableName)
			throws SQLException {
		return super.GetTableFileToView(databaseName + JSONString, tableName, JSONExtension);
	}

	@Override
	public File GetDTDFileToUpdate(String databaseName, String tableName)
			throws SQLException {
		//return super.GetDTDFileToUpdate(databaseName + JSONString, tableName);
		return null;
	}

	@Override
	public void createDTD(File file, TableValues table) throws SQLException {
		// super.createDTD(file, table);
	}

	@Override
	public void write(TableValues source, File fileToUpdate) throws SQLException {
		writer.write(source, fileToUpdate);
	}

	@Override
	public TableValues read(File source) throws SQLException {
		return reader.read(source);
	}

	@Override
	public File createTableFile(String databaseName, String tableName)
			throws SQLException {
		return super.createTableFile(databaseName + JSONString, tableName, JSONExtension);
	}

	@Override
	public File createNewDTDFile(String databaseName, String tableName)
			throws SQLException {
		//return super.CreatNewDTDFile(databaseName + JSONString, tableName);
		return null;
	}
}
