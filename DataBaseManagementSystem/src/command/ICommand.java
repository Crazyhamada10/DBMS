package command;

import java.util.ArrayList;

import accessories.NameOperatorValue;
import accessories.NameOrder;
import accessories.NameType;
import accessories.NameValue;
import accessories.StaticData.AlterEnum;
import accessories.StaticData.BooleanValues;
import accessories.StaticData.CommandsTypes;
import accessories.StaticData.DataBaseTable;

// TODO: Auto-generated Javadoc
/**
 * The Interface ICommand.
 */
public interface ICommand {
	
	/**
	 * Sets the command type.
	 * From all Commands
	 * @param commandType the new command type
	 */
	void setCommandType(CommandsTypes commandType);
	
	/**
	 * Gets the command type.
	 * From all Commands
	 * @return the command type
	 */
	CommandsTypes getCommandType();
	
	/**
	 * Sets the data base name.
	 * Use, Create DataBase, Drop DataBase
	 * @param name the new data base name
	 */
	void setDataBaseName(String name);
	
	/**
	 * Gets the data base name.
	 * Use, Create DataBase, Drop DataBase
	 * @return the data base name
	 */
	String getDataBaseName();
	
	/**
	 * Sets the table name.
	 * Create table, Drop table, insert, update, select, alter, delete
	 * @param name the new table name
	 */
	void setTableName(String name);
	
	/**
	 * Gets the table name.
	 * Create table, Drop table, insert, update, select, alter, delete
	 * @return the table name
	 */
	String getTableName();
	
	/**
	 * Sets the column name.
	 * Alter drop column
	 * @param name the new column name
	 */
	void setColumnName(String name);

	/**
	 * Gets the column name.
	 * Alter drop column
	 * @return the column name
	 */
	String getColumnName();	

	/**
	 * Sets the data base table type.
	 * Create, Drop
	 * @param dataBaseTable the new data base table type
	 */
	void setDataBaseTableType(DataBaseTable dataBaseTable);
	
	/**
	 * Gets the data base table type.
	 * Create, Drop
	 * @return the data base table type
	 */
	DataBaseTable getDataBaseTableType();

	/**
	 * Sets the column name types.
	 * Create table
	 * @param nameTypes the new column name types
	 */
	void setColumnNameTypes(ArrayList<NameType> nameTypes);

	/**
	 * Gets the column name types.
	 * Create table
	 * @return the column name types
	 */
	ArrayList<NameType> getColumnNameTypes();

	/**
	 * Sets the column names.
	 * Insert, Select
	 * @param columnNames the new column names
	 */
	void setColumnNames(ArrayList<String> columnNames);
	
	/**
	 * Gets the column names.
	 * Insert, Select
	 * @return the column names
	 */
	ArrayList<String> getColumnNames();

	/**
	 * Gets the column names integers.
	 * Create Table
	 * @return the column type Integers
	 */
	ArrayList<Integer> getColumnTypeIntegers();
	
	/**
	 * Gets the column name integer.
	 * Add column
	 * @return the column type Integer
	 */	
	int getNameTypeInteger();

	/**
	 * Sets the row values.
	 * Insert
	 * @param rowValues the new row values
	 */
	void setRowValues(ArrayList<String> rowValues);
	
	/**
	 * Gets the row values.
	 * Insert
	 * @return the row values
	 */
	ArrayList<String> getRowValues();
	
	/**
	 * Sets the alter type.
	 * Alter
	 * @param alterType the new alter type
	 */
	void setAlterType(AlterEnum alterType);
	
	/**
	 * Gets the alter type.
	 * Alter
	 * @return the alter type
	 */
	AlterEnum getAlterType();
	
	/**
	 * Sets the name type.
	 * Alter add column
	 * @param nameType the new name type
	 */
	void setNameType(NameType nameType);

	/**
	 * Gets the name type.
	 * Alter add column
	 * @return the name type
	 */
	NameType getNameType();
	
	/**
	 * Sets the name operator values.
	 * Any thing include Where: select, update, delete
	 * @param nameOperatorValues the new name operator values
	 */
	void setNameOperatorValues(ArrayList<NameOperatorValue> nameOperatorValues);
	
	/**
	 * Gets the name operator values.
	 * Any thing include Where: select, update, delete
	 * @return the name operator values
	 */
	ArrayList<NameOperatorValue> getNameOperatorValues();
	
	/**
	 * Sets the boolean values.
	 * Any thing include Where: select, update, delete
	 * @param booleanValues the new boolean values
	 */
	void setBooleanValues(ArrayList<BooleanValues> booleanValues);
	
	/**
	 * Gets the boolean values.
	 * Any thing include Where: select, update, delete
	 * @return the boolean values
	 */
	ArrayList<BooleanValues> getBooleanValues();

	/**
	 * Sets the name values.
	 * Update
	 * @param nameValues the new name values
	 */
	void setNameValues(ArrayList<NameValue> nameValues);
	
	/**
	 * Gets the name values.
	 * Update
	 * @return the name values
	 */
	ArrayList<NameValue> getNameValues();

	/**
	 * Sets the distinct.
	 * Select, Union
	 * @param distinct the new distinct
	 */
	void setDistinct(boolean distinct);
	
	/**
	 * Sets the for ordering.
	 * Select
	 * @param forOrdering the new for ordering
	 */
	void setForOrdering(ArrayList<NameOrder> forOrdering);
	
	/**
	 * Gets the for ordering.
	 * Select
	 * @return the for ordering
	 */
	ArrayList<NameOrder> getForOrdering();

	/**
	 * Gets the order names.
	 * Select
	 * @return the order names
	 */
	ArrayList<String> getOrderNames();

	/**
	 * Checks if is where.
	 * Select
	 * @return true, if is where
	 */
	boolean isWhere();
	
	/**
	 * Checks if is distinct.
	 * Select, Union
	 * @return true, if is distinct
	 */
	boolean isDistinct();
	
	/**
	 * Checks if is ordered.
	 * Select
	 * @return true, if is ordered
	 */
	boolean isOrdered();
	
	/**
	 * Checks if is all selected.
	 * Select
	 * @return true, if is all selected
	 */
	boolean isAllSelected();
	
	/**
	 * Sets the select one.
	 * Union
	 * @param select1 the new select one
	 */
	void setSelectOne(ICommand select1);
	
	/**
	 * Gets the select one.
	 * Union
	 * @return the select one
	 */
	ICommand getSelectOne();
	
	/**
	 * Sets the select two.
	 * Union
	 * @param select2 the new select two
	 */
	void setSelectTwo(ICommand select2);
	
	/**
	 * Gets the select two.
	 * Union
	 * @return the select two
	 */
	ICommand getSelectTwo();

}
