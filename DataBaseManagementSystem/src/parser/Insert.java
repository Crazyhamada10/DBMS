package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.SQLExceptions;
import parser.helper.ArrayListNeededMethods;
import parser.helper.CheckRepeatedColumnNames;
import parser.helper.GetBetweenParentheses;
import parser.helper.GetColumnName;
import parser.helper.GetRowValues;
import parser.helper.SplitByComma;
import parser.helper.SqlNameConstrains;
import parser.helper.StringNeededMethods;
import command.ICommand;

public class Insert {

	private static Insert instance;
	private ICommand insertCommand;
	private ArrayList<String> columnNames;
	private ArrayList<String> rowValues;

	private Insert() {
		
	}

	public static Insert getInstance() {
		if (instance == null) {
			instance = new Insert();
		}
		return instance;
	}
	
	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		StringNeededMethods.checkIntoString(ArrayListNeededMethods.popFirst(parts));
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		insertCommand.setTableName(ArrayListNeededMethods.popFirst(parts));
		ArrayListNeededMethods.checkNonEmptiness(parts);
		if (StringNeededMethods.checkOpenParentheses(ArrayListNeededMethods.getFirst(parts))) {
			getColNames(parts);
		}
		ArrayListNeededMethods.checkNonEmptiness(parts);
		StringNeededMethods.checkValuesString(ArrayListNeededMethods.popFirst(parts));		
		getValues(parts);
		insertCommand.setRowValues(rowValues);
		CheckRepeatedColumnNames.getInstance().check(columnNames);
		insertCommand.setColumnNames(columnNames);
		checkCompatibilityOfArraySizes();
		return insertCommand;
	}

	private void clear() {
		insertCommand = new command.Insert();		
		rowValues = new ArrayList<String>();
		columnNames = new ArrayList<String>();
	}
	
	private void getColNames(ArrayList<String> parts) throws SQLException {
		ArrayList<String> betweenParentheses = GetBetweenParentheses.getInstance().getBetweenParentheses(parts);
		ArrayList<ArrayList<String>> splited = SplitByComma.getInstance().splitByComma(betweenParentheses);
		for (ArrayList<String> arrayList : splited) {
			columnNames.add(GetColumnName.getInstance().getColumnName(arrayList));
		}
	}
	
	private void getValues(ArrayList<String> parts) throws SQLException {
		ArrayList<String> betweenParentheses = GetBetweenParentheses.getInstance().getBetweenParentheses(parts);
		rowValues = new ArrayList<String>(GetRowValues.getInstance().getRowValues(betweenParentheses));
	}
	
	private void checkCompatibilityOfArraySizes() throws SQLException {
		if (ArrayListNeededMethods.getSize(columnNames) 
				!= ArrayListNeededMethods.getSize(rowValues) 
				&& ArrayListNeededMethods.getSize(columnNames) != 0) {
			SQLExceptions.throwNotCompitableColumnNumbers();
		}
	}
	
}
