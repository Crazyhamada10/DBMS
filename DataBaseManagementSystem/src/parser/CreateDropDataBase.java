package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.SqlNameConstrains;
import command.ICommand;

public class CreateDropDataBase {

	private static CreateDropDataBase instance;
	private ICommand command;
	
	private CreateDropDataBase() {
		
	}
	
	public static CreateDropDataBase getInstance() {
		if (instance == null) {
			instance = new CreateDropDataBase();
		}
		return instance;
	}

	public ICommand check (ArrayList<String> parts) throws SQLException {
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		command.setDataBaseName(ArrayListNeededMethods.popFirst(parts));
		return command;
	}

	private void clear() {
		command = new command.CreateDropDataBase();
	}
}
