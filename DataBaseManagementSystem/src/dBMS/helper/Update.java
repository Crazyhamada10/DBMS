package dBMS.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.NameOperatorValue;
import accessories.SQLExceptions;
import accessories.TableValues;

import command.ICommand;

import dataTypesController.DataTypeController;
import fileManipulator.IFile;

public class Update {
	
	private static Update instance;
	private TableValues tableValues;
	
	private Update() {
		
	}
	
	public static Update getInstance() {
		if (instance == null) {
			instance = new Update();
		}
		return instance;
	}
	
	public int update(ICommand command, IFile file, String dataBaseName) throws SQLException {
		tableValues = file.read(file.GetTableFileToView(dataBaseName, command.getTableName()));
		int updatedRows = updateHelper(command);
		file.write(tableValues, file.GetTableFileToUpdate(dataBaseName, command.getTableName()));
		return updatedRows;
	}

	private int updateHelper(ICommand command) throws SQLException {
		ArrayList<Object> setObjects = convertToObjects(command.getColumnNames(), command.getRowValues());
		ArrayList<Integer> notToBeAffected = new ArrayList<Integer>();
		if (command.isWhere()) {
			ArrayList<Object> whereObjects = getObjectAfterWhere(command.getNameOperatorValues());
			notToBeAffected = Where.getInstance().where(whereObjects, command, tableValues);
		}
		updateOthers(notToBeAffected, setObjects, command.getColumnNames());
		return tableValues.getNumRows() - notToBeAffected.size();
	}
	
	private ArrayList<Object> convertToObjects(ArrayList<String> colNames, ArrayList<String> rowValues)
			throws SQLException {
		ArrayList<Object> row = new ArrayList<>();
		checkColsValid(colNames);
		for (int i = 0; i < colNames.size(); i ++) {
			int index = tableValues.getcolNames().indexOf(colNames.get(i));
			row.add(DataTypeController.convertData(tableValues.getColTypes().get(index), rowValues.get(i)));
		}
		return row;
	}

	private void checkColsValid(ArrayList<String> colMatching) throws SQLException {
		for (int i = 0; i < colMatching.size(); i++) {
			if (!tableValues.getcolNames().contains(colMatching.get(i))) {
				SQLExceptions.unknownColName();
			}
		}
	}
	
	private ArrayList<Object> getObjectAfterWhere(
			ArrayList<NameOperatorValue> nameOperatorValues) throws SQLException {
		ArrayList<String> colNames = new ArrayList<String>();
		ArrayList<String> rowValues = new ArrayList<String>();
		for (int i=0; i < nameOperatorValues.size(); i++) {
			colNames.add(nameOperatorValues.get(i).getName());
			rowValues.add(nameOperatorValues.get(i).getValue());
		}
		return convertToObjects(colNames, rowValues);
	}
	
	private void updateOthers(ArrayList<Integer> notToBeAffected, ArrayList<Object> setObjects, ArrayList<String> colNames) {
		for (int i=0; i < tableValues.getNumRows(); i ++) {
			if (!notToBeAffected.contains(i)) {
				for (int j = 0; j < colNames.size(); j ++) {
					tableValues.setValue(i,
							tableValues.getcolNames().indexOf(colNames.get(j)), setObjects.get(j));
				}
			}
		}
	}
}
