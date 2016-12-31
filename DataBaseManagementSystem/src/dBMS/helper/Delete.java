package dBMS.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.NameOperatorValue;
import accessories.SQLExceptions;
import accessories.TableValues;
import command.ICommand;
import dataTypesController.DataTypeController;
import fileManipulator.IFile;

public class Delete {

	private static Delete instance;
	private TableValues tableValues;
	
	private Delete() {
		
	}
	
	public static Delete getInstance() {
		if (instance == null) {
			instance = new Delete();
		}
		return instance;
	}
	
	public int delete(ICommand command, IFile file, String dataBaseName) throws SQLException {
		tableValues = file.read(file.GetTableFileToView(dataBaseName, command.getTableName()));
		int deletedRows = deleteHelper(command);
		file.write(tableValues, file.GetTableFileToUpdate(dataBaseName, command.getTableName()));
		return deletedRows;
	}

	private int deleteHelper(ICommand command) throws SQLException {
		ArrayList<Integer> notToBeAffected = new ArrayList<Integer>();
		if (command.isWhere()) {
			ArrayList<Object> whereObjects = getObjectAfterWhere(command.getNameOperatorValues());
			notToBeAffected = Where.getInstance().where(whereObjects, command, tableValues);
		}
		int numberOfRows = tableValues.getNumRows();
		deleteOthers(notToBeAffected);
		return numberOfRows - notToBeAffected.size();
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
	
	private void deleteOthers(ArrayList<Integer> notToBeAffected) {
		ArrayList <Boolean> booleans = castNotToBeAffected(notToBeAffected);
		for (int i=0; i < tableValues.getNumRows(); i ++) {
			if (!booleans.get(i)) {
				tableValues.removeRow(i);
				booleans.remove(i);
				i --;
			}
		}
	}
	
	private ArrayList<Boolean> castNotToBeAffected(ArrayList<Integer> notTobeAffected) {
		ArrayList <Boolean> booleans = new ArrayList<Boolean>();
		for (int i = 0; i < tableValues.getNumRows(); i++) {
			if (notTobeAffected.contains(i)) {
				booleans.add(true);
			} else {
				booleans.add(false);
			}
		}
		return booleans;
	}
	
}
