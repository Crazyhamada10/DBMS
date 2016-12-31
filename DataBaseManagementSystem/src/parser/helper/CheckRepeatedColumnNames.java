package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import accessories.SQLExceptions;

public class CheckRepeatedColumnNames {

	private static CheckRepeatedColumnNames instance;
	
	private CheckRepeatedColumnNames() {
		
	}
	
	public static CheckRepeatedColumnNames getInstance() {
		if (instance == null) {
			instance = new CheckRepeatedColumnNames();
		}
		return instance;
	}
	
	public void check(ArrayList<String> columnNames) throws SQLException {
		ArrayList<String> columnNamesCopy = new ArrayList<String>(columnNames);
		List<String> columnList = columnNamesCopy.subList(0, columnNamesCopy.size());
		for (String string : columnList) {
			string = string.toLowerCase();
		}
		Collections.sort(columnList);
		for (int i = 0; i < columnList.size() - 1; i++) {
			if (columnList.get(i).equals(columnList.get(i + 1))) {
				SQLExceptions.throwRepeatedColumnNames();
			}
		}
	}
	
}
