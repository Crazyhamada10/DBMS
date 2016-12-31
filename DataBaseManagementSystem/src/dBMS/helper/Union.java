package dBMS.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.NameOrder;
import accessories.SQLExceptions;
import accessories.TableValues;
import command.ICommand;
import dataTypesController.Comparators;
import fileManipulator.IFile;

public class Union {
	
	private static Union instance;
	private TableValues tableValues1;
	private TableValues tableValues2;
	
	private Union() {
		
	}
	
	public static Union getInstance() {
		if (instance == null) {
			instance = new Union();
		}
		return instance;
	}

	public TableValues union(ICommand command, IFile file, String dataBaseName) throws SQLException {
		tableValues1 = Select.getInstance().select(command.getSelectOne(), file, dataBaseName);
		tableValues2 = Select.getInstance().select(command.getSelectTwo(), file, dataBaseName);
		modifyTables();
		if (command.getSelectTwo().isOrdered()) {
			command.getSelectOne().setForOrdering(checkColumnsPresentInSecond(command.getSelectTwo().getForOrdering()));
			tableValues1 = OrderBy.getInstance().order(command.getSelectOne(), tableValues1);
		}
		if (command.isDistinct()) {
			removeSimilarities();
		}
		return tableValues1;
	}
	
	private void modifyTables() throws SQLException {
		checkColumnTypes();
		copyRows();
	}

	private void checkColumnTypes() throws SQLException {
		ArrayList<Integer> types1 = tableValues1.getColTypes();
		ArrayList<Integer> types2 = tableValues2.getColTypes();
		if (types1.size() != types2.size()) {
			SQLExceptions.dataIncosistencyError();
		}
		for (int i = 0; i< types1.size(); i++) {
			if (types1.get(i) != types2.get(i)) {
				SQLExceptions.dataIncosistencyError();				
			}
		}
	}
	
	private void copyRows() throws SQLException {
		for (int i = 0; i < tableValues2.getNumRows(); i ++) {
			tableValues1.addRow(tableValues2.getRow(i));
		}
	}
	
	private void removeSimilarities() throws SQLException {
		for (int i = 0; i < tableValues1.getNumRows(); i++) {
			for (int j = i + 1; j < tableValues1.getNumRows(); j++) {
				if (isSimilar(i, j)) {
					tableValues1.removeRow(j);
					j -- ;
				}
			}
		}
	}

	private boolean isSimilar(int index1, int index2) throws SQLException {
		for (int i=0; i < tableValues1.getNumCols(); i++) {
			Object object1 = tableValues1.getValue(index1, i);
			Object object2 = tableValues1.getValue(index2, i);
			int type = tableValues1.getType(i);
			int compare = Comparators.getInstance().compare(object1, object2, type);
			if (compare != 0) {
				return false;
			}
		}
		return true;
	}
	
	private ArrayList<NameOrder> checkColumnsPresentInSecond(ArrayList<NameOrder> forOrdering) {
		for (int i = 0; i < forOrdering.size(); i++) {
			if (tableValues2.getcolNames().contains(forOrdering.get(i).getColumnName())) {
				int index = tableValues2.getcolNames().indexOf(forOrdering.get(i).getColumnName());
				forOrdering.get(i).setColumnName(tableValues1.getcolNames().get(index));
			}
		}
		return forOrdering;
	}
}
