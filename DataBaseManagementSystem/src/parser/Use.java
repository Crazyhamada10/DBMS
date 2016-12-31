package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.SqlNameConstrains;
import command.ICommand;

public class Use {

	private static Use instance;
	private ICommand useCommand;
	
	private Use() {
		
	}

	public static Use getInstance() {
		if (instance == null) {
			instance = new Use();
		}
		return instance;
	}
	
	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		useCommand.setDataBaseName(ArrayListNeededMethods.popFirst(parts));
		return useCommand;
	}

	private void clear() {
		useCommand = new command.Use();
	}
}
