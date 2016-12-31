package dBMS.helper;

import java.sql.SQLException;

import accessories.TableValues;

import command.ICommand;

import fileManipulator.IFile;

public class Alter {

	private static Alter instance;
	
	private Alter() {
		
	}
	
	public static Alter getInstance() {
		if (instance == null) {
			instance = new Alter();
		}
		return instance;
	}
	
	public void alterAdd(ICommand command, IFile file, String dataBaseName) throws SQLException {
		TableValues tableValues = file.read(file.GetTableFileToView(dataBaseName, command.getTableName()));
		tableValues.addColumn(command.getNameType().getColumnName(), command.getNameTypeInteger());
		file.write(tableValues, file.GetTableFileToUpdate(dataBaseName, command.getTableName()));
		file.createDTD(file.GetDTDFileToUpdate(dataBaseName, command.getTableName()), tableValues);
	}
	
	public void alterDrop(ICommand command, IFile file, String dataBaseName) throws SQLException {
		TableValues tableValues = file.read(file.GetTableFileToView(dataBaseName, command.getTableName()));
		tableValues.removeColumn(command.getColumnName());
		file.write(tableValues, file.GetTableFileToUpdate(dataBaseName, command.getTableName()));
		file.createDTD(file.GetDTDFileToUpdate(dataBaseName, command.getTableName()), tableValues);
	}
}
