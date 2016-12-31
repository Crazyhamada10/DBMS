package dBMS;

import java.sql.SQLException;

import command.ICommand;

import fileManipulator.IFile;

public class DataBase {

	private static DataBase instance;
	private IFile file;
	
	public DataBase() {

	}

	public static DataBase getInstance() {
		if (instance == null) {
			instance = new DataBase();
		}
		return instance;
	}
	
	public void setFile(IFile file) {
		this.file = file;
	}

	public int createDataBase(ICommand command) throws SQLException {
		file.createDataBaseFile(command.getDataBaseName());
		return 0;
	}

	public int dropDataBase(ICommand command) throws SQLException {
		file.deleteDatabase(command.getDataBaseName());
		return 0;
	}
	
}
