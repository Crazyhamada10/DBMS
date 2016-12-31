package fileManipulator;

import java.io.File;
import java.sql.SQLException;

import accessories.TableValues;

public interface IFile {

	void setPath (String directoryPath, String dataBaseName, String random) throws SQLException;
	
	void createDataBaseFile(String name) throws SQLException ;

	void checkDataBaseExistance(String name) throws SQLException ;
	
	void deleteDatabase(String dataBaseName) throws SQLException ;

	void deleteTable(String dataBaseName, String tableName) throws SQLException ;

	File GetTableFileToUpdate(String databaseName, String tableName) throws SQLException ;
	
	File GetTableFileToView(String databaseName, String tableName) throws SQLException;
	
	File GetDTDFileToUpdate(String databaseName, String tableName) throws SQLException;

	File createTableFile(String databaseName, String tableName) throws SQLException;
	
	File createNewDTDFile(String databaseName, String tableName) throws SQLException;
	
	void createDTD(File file , TableValues table) throws SQLException;

	void write(TableValues data, File fileToUpdate) throws SQLException;
	
	TableValues read(File source) throws SQLException;
}
