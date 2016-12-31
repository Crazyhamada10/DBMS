package protocolBuffer;

import java.io.File;
import java.sql.SQLException;

import fileManipulator.FileManipulation;
import fileManipulator.IFile;
import fileManipulator.IFileReader;
import fileManipulator.IFileWriter;
import accessories.TableValues;

public class PBFile extends FileManipulation implements IFile {
	
	private static PBFile instance;
	private IFileReader reader;
	private IFileWriter writer;
	private String PBString;
	private String PBExtension;
	
	private PBFile() {
		super();
		reader = PBReader.getInstance();
		writer = PBWriter.getInstance();
		PBString = new String("_PROTO");
		PBExtension = new String(".proto");
	}
	
	public static PBFile getInstance() {
		if (instance == null) {
			instance = new PBFile();
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
		super.createDataBaseFile(name + PBString);
	}

	@Override
	public void checkDataBaseExistance(String name) throws SQLException {
		super.checkDataBaseExistance(name + PBString);
	}

	@Override
	public void deleteDatabase(String dataBaseName) throws SQLException {
		super.deleteDatabase(dataBaseName + PBString);
	}

	@Override
	public void deleteTable(String dataBaseName, String tableName)
			throws SQLException {
		super.deleteTable(dataBaseName + PBString, tableName);
	}

	@Override
	public File GetTableFileToUpdate(String databaseName, String tableName)
			throws SQLException {
		return super.GetTableFileToUpdate(databaseName + PBString, tableName, PBExtension);
	}

	@Override
	public File GetTableFileToView(String databaseName, String tableName)
			throws SQLException {
		return super.GetTableFileToView(databaseName + PBString, tableName, PBExtension);
	}

	@Override
	public File GetDTDFileToUpdate(String databaseName, String tableName)
			throws SQLException {
		//return super.GetDTDFileToUpdate(databaseName + PBString, tableName);
		return null;
	}

	@Override
	public void createDTD(File file, TableValues table) throws SQLException {
		//super.createDTD(file, table);
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
		return super.createTableFile(databaseName + PBString, tableName, PBExtension);
	}

	@Override
	public File createNewDTDFile(String databaseName, String tableName)
			throws SQLException {
		//return super.CreatNewDTDFile(databaseName + PBString, tableName);
		return null;
	}
}
