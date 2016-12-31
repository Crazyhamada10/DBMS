package command;

import java.util.ArrayList;

import accessories.NameType;

public class CreateTable extends CreateDrop{

	private String tableName;
	private ArrayList<accessories.NameType> nameTypes;
	
	public CreateTable() {
		tableName = new String();
		nameTypes = new ArrayList<accessories.NameType>();
	}

	@Override
	public void setTableName(String name) {
		this.tableName = modifyName(name);
	}
	
	@Override
	public String getTableName() {
		return tableName;
	}
	
	@Override
	public ArrayList<NameType> getColumnNameTypes() {
		return nameTypes;
	}

	@Override
	public void setColumnNameTypes(ArrayList<NameType> nameTypes) {
		this.nameTypes = new ArrayList<NameType>(nameTypes);
	}

	@Override
	public ArrayList<String> getColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		for (accessories.NameType nameType : nameTypes) {
			columnNames.add(nameType.getColumnName());
		}
		return columnNames;
	}

	@Override
	public ArrayList<Integer> getColumnTypeIntegers() {
		ArrayList<Integer> columnTypes = new ArrayList<Integer>();
		for (accessories.NameType nameType : nameTypes) {
			columnTypes.add(nameType.getColumnTypeInteger());
		}
		return columnTypes;
	}
	
	
	@Override
	public String toString() {
		return new String(super.toString() + "TableName: " + getTableName() + "\n" + "NameTypes:\n" + nameTypes + "\n");
	}
}
