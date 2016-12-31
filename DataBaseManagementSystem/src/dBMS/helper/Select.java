package dBMS.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import command.ICommand;
import dataTypesController.Comparators;
import dataTypesController.DataTypeController;
import fileManipulator.IFile;
import accessories.NameOperatorValue;
import accessories.SQLExceptions;
import accessories.TableValues;

public class Select {

	private static Select instance;
	private TableValues tableValues;
	
	private Select() {
		
	}
	
	public static Select getInstance() {
		if (instance == null) {
			instance = new Select();
		}
		return instance;
	}

	public TableValues select(ICommand command, IFile file, String dataBaseName) throws SQLException {
		tableValues = file.read(file.GetTableFileToView(dataBaseName, command.getTableName()));
		selectHelper(command);
		if (!command.isAllSelected()) {
			arrangeColumns(command.getColumnNames());			
		}
		if (command.isDistinct()) {
			removeSimilarities();
		}
		return tableValues;
	}

	private void selectHelper(ICommand command) throws SQLException {
		checkColsValid(command.getColumnNames());
		ArrayList<Integer> notToBeAffected = new ArrayList<Integer>();
		if (command.isWhere()) {
			ArrayList<Object> whereObjects = getObjectAfterWhere(command.getNameOperatorValues());
			notToBeAffected = Where.getInstance().where(whereObjects, command, tableValues);
		}
		selectOthers(notToBeAffected, command.getColumnNames());
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
	
	private void selectOthers(ArrayList<Integer> notToBeAffected, ArrayList<String> colNames) throws SQLException {
		if (!colNames.isEmpty()) {
			for (int i=0; i < tableValues.getNumCols(); i++) {
				if (!colNames.contains(tableValues.getcolNames().get(i))) {
					tableValues.removeColumn(tableValues.getcolNames().get(i));
					i --;
				}
			}
		}
		ArrayList <Boolean> booleans = castNotToBeAffected(notToBeAffected);
		for (int i=0; i < tableValues.getNumRows(); i ++) {
			if (booleans.get(i)) {
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
	
	private void arrangeColumns(ArrayList<String> colNames) {
		for (int i=0; i<colNames.size(); i++) {
			int index = tableValues.getcolNames().indexOf(colNames.get(i));
			tableValues.swap2columns(index, i);
		}
	}
	
	private void removeSimilarities() throws SQLException {
		for (int i = 0; i < tableValues.getNumRows(); i++) {
			for (int j = i + 1; j < tableValues.getNumRows(); j++) {
				if (isSimilar(i, j)) {
					tableValues.removeRow(j);
					j -- ;
				}
			}
		}
	}

	private boolean isSimilar(int index1, int index2) throws SQLException {
		for (int i=0; i < tableValues.getNumCols(); i++) {
			Object object1 = tableValues.getValue(index1, i);
			Object object2 = tableValues.getValue(index2, i);
			int type = tableValues.getType(i);
			int compare = Comparators.getInstance().compare(object1, object2, type);
			if (compare != 0) {
				return false;
			}
		}
		return true;
	}
}
