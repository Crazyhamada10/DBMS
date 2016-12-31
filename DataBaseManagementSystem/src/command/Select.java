package command;

import java.util.ArrayList;
import java.util.Arrays;

import accessories.NameOrder;

public class Select extends Where {

	private boolean distinct;
	private ArrayList<String> columnNames;
	private ArrayList<NameOrder> forOrdering;

	public Select() {
		super();
		distinct = false;
		columnNames = new ArrayList<String>();
		forOrdering = new ArrayList<NameOrder>();
	}
	
	@Override	
	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}
	
	@Override
	public boolean isDistinct() {
		return distinct;
	}

	@Override
	public void setColumnNames(ArrayList<String> columnNames) {
		for (String string : columnNames) {
			this.columnNames.add(modifyName(string));
		}
	}

	@Override
	public ArrayList<String> getColumnNames() {
		return columnNames;
	}

	@Override
	public void setForOrdering(ArrayList<NameOrder> forOrdering) {
		this.forOrdering = forOrdering;
	}
	
	@Override
	public ArrayList<NameOrder> getForOrdering() {
		return forOrdering;
	}
	
	@Override
	public ArrayList<String> getOrderNames() {
		ArrayList<String> orderNames = new ArrayList<String>();
		for (NameOrder part : forOrdering) {
			orderNames.add(part.getColumnName());
		}
		return orderNames;
	}
	
	@Override
	public boolean isOrdered() {
		if(forOrdering.isEmpty()) {
			return false;			
		} else {
			return true;
		}
	}

	@Override
	public boolean isAllSelected() {
		if(columnNames.isEmpty()) {
			return true;			
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return new String (super.toString() + getString() + "\n");
	}

	private String getString() {
		String string1 = new String("Distinct: " + distinct + "\n");
		String string2 = new String("ColumnNames: " + checkColumnNamesExistance() + "\n");
		String string3 = new String(checkForOrderingExistance());
		return string1 + string2 + string3;
	}
	
	private ArrayList<String> checkColumnNamesExistance() {
		if (isAllSelected()) {
			return new ArrayList<String>(Arrays.asList("All columns is selected"));
		}
		return columnNames; 
	}
	
	private String checkForOrderingExistance() {
		if (forOrdering.isEmpty()) {
			return new String();
		}
		return new String("ForOrdering:\n" + forOrdering);
	}
}
 