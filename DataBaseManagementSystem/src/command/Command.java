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

public abstract class Command implements ICommand{
	CommandsTypes commandType;
	
	protected Command() {

	}
	
	@Override
	public void setCommandType(CommandsTypes commandType) {
		this.commandType = commandType;
	}
	
	@Override
	public CommandsTypes getCommandType() {
		return commandType;
	}
	
	protected String modifyName(String name) {
		if (name.length() > 1) {
			name = new String(name.toLowerCase());
			String firstLetter = name.substring(0, 1).toUpperCase();
			String modified = new String(firstLetter + name.substring(1));
			return modified;
		} else {
			return name.toUpperCase();
		}
	}
	
	@Override
	public String toString() {
		return new String("CommandType: " + commandType.name() + "\n");
	}

	@Override
	public void setDataBaseName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getDataBaseName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTableName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataBaseTableType(DataBaseTable dataBaseTable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public DataBaseTable getDataBaseTableType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColumnNameTypes(ArrayList<NameType> nameTypes) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<NameType> getColumnNameTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setColumnNames(ArrayList<String> columnNames) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> getColumnNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setRowValues(ArrayList<String> rowValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<String> getRowValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setAlterType(AlterEnum alterType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public AlterEnum getAlterType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNameOperatorValues(
			ArrayList<NameOperatorValue> nameOperatorValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<NameOperatorValue> getNameOperatorValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setBooleanValues(ArrayList<BooleanValues> booleanValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<BooleanValues> getBooleanValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNameValues(ArrayList<NameValue> nameValues) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<NameValue> getNameValues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDistinct(boolean distinct) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setForOrdering(ArrayList<NameOrder> forOrdering) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ArrayList<NameOrder> getForOrdering() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isWhere() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDistinct() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOrdered() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAllSelected() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setColumnName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getColumnName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setNameType(NameType nameType) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public NameType getNameType() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ArrayList<String> getOrderNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectOne(ICommand select1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ICommand getSelectOne() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSelectTwo(ICommand select2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public ICommand getSelectTwo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Integer> getColumnTypeIntegers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getNameTypeInteger() {
		// TODO Auto-generated method stub
		return 0;
	}

}
