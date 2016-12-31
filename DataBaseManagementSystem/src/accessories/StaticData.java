package accessories;

import java.sql.SQLException;

public final class StaticData {
	
	public static enum DataTypes {VARCHAR, INT, FLOAT, DATE};
	public static enum CommandsTypes {SELECT, INSERT, DELETE, CREATE, DROP, ALTER, USE, UPDATE, UNION};
	public static enum DataBaseTable {DATABASE, TABLE};
	public static enum AlterEnum {ADD, DROP};
	public static enum BooleanValues {AND, OR, NOT, OTHER};
	public static enum Order {ASC, DESC};
	public static enum AttributeWords {NumberOfColumns, NumberOfRows, Column, Type,
		Rows, RowNumber, Null, yes, ColumnTypes, ColumnNames, Details, Data, TableName};
	public static enum FileType {XML, JSON, PB};

	private StaticData () {
		
	}
	
	public static CommandsTypes getCommandType(String type) throws SQLException{
		CommandsTypes commandsType = null;
		try {
			commandsType =  CommandsTypes.valueOf(type.toUpperCase());
		} catch (Exception e) {
			SQLExceptions.throwUnknownCommand();			
		}
		return commandsType;
	}
	
	public static DataTypes getDataType(String type) throws SQLException{
		DataTypes dataType = null;
		try {
			dataType = DataTypes.valueOf(type.toUpperCase());
		} catch (Exception e) {
			SQLExceptions.throwUnknownDataType();	
		}
		return dataType;
	}
	
	public static DataBaseTable getDataBaseTable(String type) throws SQLException{
		DataBaseTable dataBaseTable = null;
		try {
			dataBaseTable = DataBaseTable.valueOf(type.toUpperCase());
		} catch (Exception e) {
			SQLExceptions.throwUnknownDataBaseTable();
		}
		return dataBaseTable;
	}
	
	public static AlterEnum getAlterEnum(String type) throws SQLException{
		AlterEnum alterEnum = null;
		try {
			alterEnum = AlterEnum.valueOf(type.toUpperCase());
		} catch (Exception e) {
			SQLExceptions.throwUnknownAlter();
		}
		return alterEnum;
	}
	
	public static BooleanValues getBooleanValue(String type) throws SQLException{
		BooleanValues booleanValue = null;
		try {
			booleanValue = BooleanValues.valueOf(type.toUpperCase());
		} catch (Exception e) {
			SQLExceptions.throwUnknownCommand();			
		}
		return booleanValue;
	}
	
	public static Order getOrder(String type) throws SQLException{
		Order order = null;
		try {
			order = Order.valueOf(type.toUpperCase());
		} catch (Exception e) {
			SQLExceptions.throwUnknownOrder();
		}
		return order;
	}
	
	public static FileType getfileType(String type) throws SQLException{
		FileType fileType = null;
		try {
			fileType = FileType.valueOf(type.toUpperCase());
		} catch (Exception e) {
			SQLExceptions.throwUnknownFile();
		}
		return fileType;
	}
}
