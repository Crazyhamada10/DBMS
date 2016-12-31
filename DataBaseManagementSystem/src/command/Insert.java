package command;

import java.util.ArrayList;
import java.util.Arrays;

public class Insert extends Command {

	private String tableName;
	private ArrayList<String> columnNames;
	private ArrayList<String> rowValues;
	
	public Insert() {
		tableName = new String();
		columnNames = new ArrayList<String>();
		rowValues = new ArrayList<String>();
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public void setTableName(String tableName) {
		this.tableName = modifyName(tableName);
	}

	@Override
	public ArrayList<String> getColumnNames() {
		return columnNames;
	}

	@Override
	public void setColumnNames(ArrayList<String> columnNames) {
		for (String string : columnNames) {
			this.columnNames.add(modifyName(string));
		}
	}

	@Override
	public ArrayList<String> getRowValues() {
		return rowValues;
	}
	
	@Override
	public void setRowValues(ArrayList<String> rowValues) {
		this.rowValues = new ArrayList<String>(rowValues);
	}
	
	@Override
	public String toString() {
		return new String (super.toString() + "TableName: " + getTableName() + "\n" 
				+ "ColumnNames: " + checkColumnNamesExistance() + "\n" + "RowValues: " + rowValues + "\n");
	}
	
	private ArrayList<String> checkColumnNamesExistance() {
		if (columnNames.isEmpty()) {
			return new ArrayList<String>(Arrays.asList("All Columns is inserted"));
		}
		return columnNames;
	}
}
