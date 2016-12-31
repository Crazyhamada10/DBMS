package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.SqlNameConstrains;
import parser.helper.StringNeededMethods;
import accessories.SQLExceptions;
import command.ICommand;

public class Delete {

	private static Delete instance;
	private ICommand deleteCommand;

	private Delete() {
		
	}

	public static Delete getInstance() {
		if (instance == null) {
			instance = new Delete();
		}
		return instance;
	}
	
	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		StringNeededMethods.checkFromString(ArrayListNeededMethods.popFirst(parts));
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		deleteCommand.setTableName(ArrayListNeededMethods.popFirst(parts));
		if	(checkWhereExistance(parts)) {
			Where.getInstance().where(deleteCommand, parts);
		}
		return deleteCommand;
	}

	private void clear() {
		deleteCommand = new command.Delete();		
	}
	
	private boolean checkWhereExistance(ArrayList<String> parts) throws SQLException {
		boolean isWhere = false;
		try {
			ArrayListNeededMethods.checkEmptiness(parts);
		} catch (Exception e) {
			if(StringNeededMethods.checkWhere(ArrayListNeededMethods.popFirst(parts))) {
				isWhere = true;
			} else {
				SQLExceptions.throwMissingWord("Where");
			}
		}
		return isWhere;
	}
}
