package dBMS.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.NameOrder;
import accessories.SQLExceptions;
import accessories.TableValues;
import accessories.StaticData.Order;
import command.ICommand;
import dataTypesController.Comparators;

public class OrderBy {
	
	private static OrderBy instance;
	private TableValues tableValues;
	
	private OrderBy() {
		
	}
	
	public static OrderBy getInstance() {
		if (instance == null) {
			instance = new OrderBy();
		}
		return instance;
	}
	
	public TableValues order(ICommand command, TableValues tableValues) throws SQLException {
		this.tableValues = tableValues;
		checkSelectedColumns(command.getForOrdering());
		sort(command);
		return this.tableValues;
	}

	private void checkSelectedColumns(ArrayList<NameOrder> nameOrders) throws SQLException {
		for (int i=0; i<nameOrders.size(); i++) {
			int index = tableValues.getcolNames().indexOf(nameOrders.get(i).getColumnName());
			if (index == -1) {
				SQLExceptions.unknownColName();
			}
		}
	}

	private void sort(ICommand command) throws SQLException {
		for (int i = 0; i < tableValues.getNumRows(); i++) {
			boolean flag = false;
			for (int j = 0; j < tableValues.getNumRows() - 1 - i; j++) {
				if (compare2rows(j, j+1, command.getForOrdering(), 0)) {
					flag = true;
					tableValues.swap2rows(j, j + 1);
				}
			}
			if (!flag) {
				break;
			}
		}
	}

	private boolean compare2rows(int index1, int index2, ArrayList<NameOrder> nameOrders, int compareIndex) throws SQLException {
		if (compareIndex == nameOrders.size()) {
			return false;
		}
		int getIndexColumnCompare = tableValues.getcolNames().indexOf(nameOrders.get(compareIndex).getColumnName());
		Object object1 = tableValues.getValue(index1, getIndexColumnCompare);
		Object object2 = tableValues.getValue(index2, getIndexColumnCompare);
		int type = tableValues.getType(getIndexColumnCompare);
		int compare = Comparators.getInstance().compare(object1, object2, type);
		if (compare == 0) {
			return compare2rows(index1, index2, nameOrders, compareIndex + 1);
		} else {
			return orderSwitch(nameOrders.get(compareIndex).getOrder(), compare);
		}
	}

	private boolean orderSwitch(Order order, int compare) {
		switch (order) {
		case ASC:
			if (compare > 0) {
				return true;
			}
			break;
		case DESC:
			if (compare < 0) {
				return true;
			}
			break;
		}
		return false;
	}
	
}
