package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.SQLExceptions;

public class GetColumnName {
	
	private static GetColumnName instance;
	
	private GetColumnName() {
		
	}
	
	public static GetColumnName getInstance() {
		if (instance == null) {
			instance = new GetColumnName();
		}
		return instance;
	}
	
	public String getColumnName(ArrayList<String> name) throws SQLException {
		if (ArrayListNeededMethods.getSize(name) != 1) {
			SQLExceptions.throwUnknownCommand();
		}
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(name));
		return ArrayListNeededMethods.popFirst(name);
	}
}
