package dBMS.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.SQLExceptions;
import accessories.TableValues;
import command.ICommand;
import dataTypesController.DataTypeController;
import fileManipulator.IFile;

public class Insert {

	private static Insert instance;
	private TableValues tableValues;
	
	private Insert() {
		
	}

	public static Insert getInstance() {
		if (instance == null) {
			instance = new Insert();
		}
		return instance;
	}
	
	public void insert(ICommand command, IFile file, String dataBaseName) throws SQLException {
	    tableValues = file.read(file.GetTableFileToView(dataBaseName, command.getTableName()));
		addRow(command);
		file.write(tableValues, file.GetTableFileToUpdate(dataBaseName, command.getTableName()));
	}
	
	private void addRow(ICommand command) throws SQLException {
		if (command.getColumnNames().size() == 0 && command.getRowValues().size() != tableValues.getNumCols()) {
			SQLExceptions.dataIncosistencyError();
		}
		tableValues.addRow(convertToObjects(command.getColumnNames(), command.getRowValues()));
	}

	private ArrayList<Object> convertToObjects(ArrayList<String> colNames, ArrayList<String> rowValues)
			throws SQLException {
		ArrayList<Object> row = new ArrayList<>();
		if (colNames.size() == 0) {
			for (int i = 0; i < tableValues.getNumCols(); i++) {
				row.add(DataTypeController.convertData(
						tableValues.getColTypes().get(i), rowValues.get(i)));
			}
		} else {
			checkColsValid(colNames);
			for (int i = 0; i < tableValues.getNumCols(); i ++) {
				int index = colNames.indexOf(tableValues.getcolNames().get(i));
				if (index == -1) {
					row.add(null);
				} else {
					row.add(DataTypeController.convertData(tableValues.getColTypes().get(i), rowValues.get(index)));
				}
			}
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
}

