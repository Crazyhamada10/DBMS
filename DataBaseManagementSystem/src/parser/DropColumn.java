package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.SqlNameConstrains;
import command.ICommand;

public class DropColumn {

	private static DropColumn instance;
	private ICommand dropColumnCommand;
	
	private DropColumn() {
		
	}

	public static DropColumn getInstance() {
		if (instance == null) {
			instance = new DropColumn();
		}
		return instance;
	}
	
	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		dropColumnCommand.setColumnName(ArrayListNeededMethods.popFirst(parts));
		return dropColumnCommand;
	}

	private void clear() {
		dropColumnCommand = new command.DropColumn();		
	}
}
