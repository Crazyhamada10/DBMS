package dBMS.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;

import accessories.NameOperatorValue;
import accessories.SQLExceptions;
import accessories.StaticData.BooleanValues;
import accessories.TableValues;

import command.ICommand;

import dataTypesController.Comparators;
import dataTypesController.DataTypeController;

public class Where {
	
	private static Where instance;
	private ArrayList<BooleanValues> postfix;
	
	private Where() {
		
	}
	
	public static Where getInstance() {
		if (instance == null) {
			instance = new Where();
		}
		return instance;
	}
	
	public ArrayList<Integer> where(ArrayList<Object> whereObjects, ICommand command, TableValues tableValues) throws SQLException {
		postfix = new ArrayList<BooleanValues>(command.getBooleanValues());
		ArrayList<Integer> notAffected = new ArrayList<Integer>();
		for (int i = 0; i < tableValues.getNumRows(); i++) {
			if (!getBoolean(tableValues.getcolNames(), tableValues.getRow(i), command, whereObjects)) {
				notAffected.add(i);
			}
		}
		return notAffected;
	}

	private boolean getBoolean(ArrayList<String> colNames, ArrayList<Object> row,
			ICommand commnd, ArrayList<Object> whereObjects) throws SQLException {
		ArrayList<NameOperatorValue> nameOperatorValues = commnd.getNameOperatorValues();
		ArrayList<Boolean> booleans = new ArrayList<Boolean>();
		for (int i = 0; i < whereObjects.size(); i++) {
			int compare = Comparators.getInstance().compare(
					row.get(colNames.indexOf(nameOperatorValues.get(i).getName())),
					whereObjects.get(i), DataTypeController.getType(whereObjects.get(i)));
			booleans.add(nameOperatorValues.get(i).check(compare));
		}
		return Execution(booleans);
	}
	
	
	private boolean Execution(ArrayList<Boolean> booleans) throws SQLException {
		Stack<Boolean> values = new Stack<Boolean>();
		int index = 0;
		for (BooleanValues booleanValue : postfix) {
			switch (booleanValue) {
			case NOT:
				notExecution(values);
				break;
			case AND:
				andExecution(values);
				break;
			case OR:
				orExecution(values);
				break;
			case OTHER:
				values.push(booleans.get(index));
				index++;
				break;
			}
		}
		if (values.size() > 1) {
			SQLExceptions.throwUnknownCommand();
		}
		return values.pop();
	}

	private void notExecution(Stack<Boolean> values) throws SQLException {
		if (values.size() < 1) {
			SQLExceptions.throwUnknownCommand();
		}
		boolean temp = values.pop();
		values.push(!temp);
	}
	
	private void andExecution(Stack<Boolean> values) throws SQLException {
		if (values.size() < 2) {
			SQLExceptions.throwUnknownCommand();
		}
		boolean temp1 = values.pop();
		boolean temp2 = values.pop();
		values.push(temp1 && temp2);
	}
	
	private void orExecution(Stack<Boolean> values) throws SQLException {
		if (values.size() < 2) {
			SQLExceptions.throwUnknownCommand();
		}
		boolean temp1 = values.pop();
		boolean temp2 = values.pop();
		values.push(temp1 || temp2);
	}
}
