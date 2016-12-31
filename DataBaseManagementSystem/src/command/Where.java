package command;

import java.util.ArrayList;

import accessories.NameOperatorValue;
import accessories.StaticData.BooleanValues;

public abstract class Where extends Command{

	private String tableName;
	private ArrayList<NameOperatorValue> nameOperatorValues;  
	private ArrayList <BooleanValues> booleanValues;
	
	protected Where() {
		super();
		tableName = new String();
		nameOperatorValues = new ArrayList<NameOperatorValue>();
		booleanValues = new ArrayList<BooleanValues>();
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
	public void setNameOperatorValues(
			ArrayList<NameOperatorValue> nameOperatorValues) {
		this.nameOperatorValues = nameOperatorValues;
	}
	
	@Override
	public ArrayList<NameOperatorValue> getNameOperatorValues() {
		return nameOperatorValues;
	}
	
	@Override
	public void setBooleanValues(ArrayList<BooleanValues> booleanValues) {
		this.booleanValues = booleanValues;
	}
	
	@Override
	public ArrayList<BooleanValues> getBooleanValues() {
		return booleanValues;
	}
	
	@Override
	public boolean isWhere() {
		if(nameOperatorValues.isEmpty()) {
			return false;
		} else {
			return true;
		}
		
	}

	@Override
	public String toString() {
		return new String (super.toString() + getString() + "\n");
	}

	private String getString() {
		String string1 = new String("TableName: " + tableName);
		String string2 = new String(operatorValuesExistance());
		return string1 + string2;
	}
	
	private String operatorValuesExistance() {
		if (isWhere()) {
			return new String( "\n" + "NameOperatorValues:\n" + nameOperatorValues + "\n" + "BooleanValues:\n" + booleanValues); 			
		}
		return new String();
	}
}
