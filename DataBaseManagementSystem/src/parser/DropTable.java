package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.SqlNameConstrains;
import command.ICommand;

public class DropTable {

	private static DropTable instance;
	private ICommand command;
	
	private DropTable() {
		
	}
	
	public static DropTable getInstance() {
		if (instance == null) {
			instance = new DropTable();
		}
		return instance;
	}

	public ICommand check (ArrayList<String> parts) throws SQLException {
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		command.setTableName(ArrayListNeededMethods.popFirst(parts));
		return command;
	}

	private void clear() {
		command = new command.DropTable();
	}
}
