package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.CheckRepeatedColumnNames;
import parser.helper.GetNameValues;
import parser.helper.SqlNameConstrains;
import parser.helper.StringNeededMethods;
import accessories.SQLExceptions;
import command.ICommand;

public class Update {

	private static Update instance;
	private ICommand updateCommand;
	
	private Update() {
		
	}

	public static Update getInstance() {
		if (instance == null) {
			instance = new Update();
		}
		return instance;
	}
	
	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		updateCommand.setTableName(ArrayListNeededMethods.popFirst(parts));
		ArrayListNeededMethods.checkNonEmptiness(parts);		
		StringNeededMethods.checkSetString(ArrayListNeededMethods.popFirst(parts));
		updateCommand.setNameValues(GetNameValues.getInstance().getNameValues(parts));
		CheckRepeatedColumnNames.getInstance().check(updateCommand.getColumnNames());
		if	(checkWhereExistance(parts)) {
			Where.getInstance().where(updateCommand, parts);
		}
		return updateCommand;
	}

	private void clear() {
		updateCommand = new command.Update();		
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
