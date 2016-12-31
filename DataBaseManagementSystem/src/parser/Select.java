package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.CheckRepeatedColumnNames;
import parser.helper.GetColumnNames;
import parser.helper.SqlNameConstrains;
import parser.helper.StringNeededMethods;

import command.ICommand;

public class Select {

	private static Select instance;
	private ICommand selectCommand;
	private ArrayList<String> columnNames;

	private Select() {
		
	}

	public static Select getInstance() {
		if (instance == null) {
			instance = new Select();
		}
		return instance;
	}
	
	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		checkDistinct(parts);
		checkAsterisk(parts);
		ArrayListNeededMethods.checkNonEmptiness(parts);
		StringNeededMethods.checkFromString(ArrayListNeededMethods.popFirst(parts));
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		selectCommand.setTableName(ArrayListNeededMethods.popFirst(parts));
		if (checkWhereExistance(parts)) {
			Where.getInstance().where(selectCommand, parts);
		}
		if (checkOrderExistance(parts)) {
			selectCommand.setForOrdering(OrderBy.getInstance().order(parts));
			CheckRepeatedColumnNames.getInstance().check(selectCommand.getOrderNames());
		}
		return selectCommand;
	}

	private void clear() {
		selectCommand = new command.Select();
		columnNames = new ArrayList<String>();
	}
	
	private void checkDistinct(ArrayList<String> parts) throws SQLException {
		ArrayListNeededMethods.checkNonEmptiness(parts);
		if (StringNeededMethods.checkDistinct(ArrayListNeededMethods.getFirst(parts))) {
			ArrayListNeededMethods.removeFirst(parts);
			selectCommand.setDistinct(true);
		}
	}
	
	private void checkAsterisk(ArrayList<String> parts) throws SQLException{
		ArrayListNeededMethods.checkNonEmptiness(parts);
		if (StringNeededMethods.checkAsterisk(ArrayListNeededMethods.getFirst(parts))) {
			ArrayListNeededMethods.removeFirst(parts);
		} else {
			columnNames = GetColumnNames.getInstance().getColumnNames(parts);
			CheckRepeatedColumnNames.getInstance().check(columnNames);
			selectCommand.setColumnNames(columnNames);
		}
	}
	
	private boolean checkWhereExistance(ArrayList<String> parts) throws SQLException {
		boolean isWhere = false;
		try {
			ArrayListNeededMethods.checkEmptiness(parts);
		} catch (Exception e) {
			if(StringNeededMethods.checkWhere(ArrayListNeededMethods.getFirst(parts))) {
				ArrayListNeededMethods.removeFirst(parts);
				isWhere = true;
			}
		}
		return isWhere;
	}
	
	private boolean checkOrderExistance(ArrayList<String> parts) throws SQLException {
		boolean isOrder = false;
		try {
			ArrayListNeededMethods.checkEmptiness(parts);
		} catch (Exception e) {
			if(StringNeededMethods.checkOrder(ArrayListNeededMethods.getFirst(parts))) {
				ArrayListNeededMethods.removeFirst(parts);
				isOrder = true;
			}
		}
		return isOrder;
	}

}
