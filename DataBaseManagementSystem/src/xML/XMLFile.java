package xML;

import java.io.File;
import java.sql.SQLException;

import fileManipulator.FileManipulation;
import fileManipulator.IFile;
import fileManipulator.IFileReader;
import fileManipulator.IFileWriter;
import accessories.TableValues;

public class XMLFile extends FileManipulation implements IFile {

	private static XMLFile instance;
	private IFileReader reader;
	private IFileWriter writer;
	private String XMLString;
	private String XMLExtension;
	
	private XMLFile() {
		super();
		reader = XMLReader.getInstance();
		writer = XMLWriter.getInstance();
		XMLString = new String("_XML");
		XMLExtension = new String(".xml");
		
	}
	
	public static XMLFile getInstance() {
		if (instance == null) {
			instance = new XMLFile();
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
		super.createDataBaseFile(name + XMLString);
	}

	@Override
	public void checkDataBaseExistance(String name) throws SQLException {
		super.checkDataBaseExistance(name + XMLString);
	}

	@Override
	public void deleteDatabase(String dataBaseName) throws SQLException {
		super.deleteDatabase(dataBaseName + XMLString);
	}

	@Override
	public void deleteTable(String dataBaseName, String tableName)
			throws SQLException {
		super.deleteTable(dataBaseName + XMLString, tableName);
	}

	@Override
	public File GetTableFileToUpdate(String databaseName, String tableName)
			throws SQLException {
		return super.GetTableFileToUpdate(databaseName + XMLString, tableName, XMLExtension);
	}

	@Override
	public File GetTableFileToView(String databaseName, String tableName)
			throws SQLException {
		return super.GetTableFileToView(databaseName + XMLString, tableName, XMLExtension);
	}

	@Override
	public File GetDTDFileToUpdate(String databaseName, String tableName)
			throws SQLException {
		return super.GetDTDFileToUpdate(databaseName + XMLString, tableName);
	}

	@Override
	public void createDTD(File file, TableValues table) throws SQLException {
		super.createDTD(file, table);
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
		return super.createTableFile(databaseName + XMLString, tableName, XMLExtension);
	}

	@Override
	public File createNewDTDFile(String databaseName, String tableName)
			throws SQLException {
		return super.CreatNewDTDFile(databaseName + XMLString, tableName);
	}
}
