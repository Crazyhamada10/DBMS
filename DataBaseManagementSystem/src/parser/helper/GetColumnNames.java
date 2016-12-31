package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetColumnNames {
	
	private static GetColumnNames instance;


	public static GetColumnNames getInstance() {
		if (instance == null) {
			instance = new GetColumnNames();
		}
		return instance;
	}
	
	public ArrayList<String> getColumnNames(ArrayList<String> parts) throws SQLException {
		ArrayList<String> columnNames = new ArrayList<String>();
		ArrayList<String> columnNamesStrings = new ArrayList<String>(GetStrings.getInstance().getStrings(parts));
		ArrayList<ArrayList<String>> splited = new ArrayList<ArrayList<String>>(SplitByComma.getInstance().splitByComma(columnNamesStrings));
		for (ArrayList<String> arrayList : splited) {
			columnNames.add(GetColumnName.getInstance().getColumnName(arrayList));
		}
		return columnNames;
	}
}
