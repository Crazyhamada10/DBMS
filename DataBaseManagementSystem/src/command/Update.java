package command;

import java.util.ArrayList;

import accessories.NameValue;

public class Update extends Where {
	
	private ArrayList<NameValue> nameValues;
	
	public Update() {
		super();
		nameValues = new ArrayList<NameValue> ();
	}
	
	@Override
	public ArrayList<NameValue> getNameValues() {
		return nameValues;
	}
	
	@Override
	public void setNameValues(ArrayList<NameValue> nameValues) {
		this.nameValues = nameValues;
	}
	
	@Override
	public ArrayList<String> getColumnNames() {
		ArrayList<String> columnNames = new ArrayList<String>();
		for (accessories.NameValue nameValue : nameValues) {
			columnNames.add(nameValue.getName());
		}
		return columnNames;
	}

	@Override
	public ArrayList<String> getRowValues() {
		ArrayList<String> columnNames = new ArrayList<String>();
		for (accessories.NameValue nameValue : nameValues) {
			columnNames.add(nameValue.getValue());
		}
		return columnNames;
	}

	@Override
	public String toString() {
		return new String (super.toString() + "NameValues:\n" + nameValues + "\n");
	}
}
