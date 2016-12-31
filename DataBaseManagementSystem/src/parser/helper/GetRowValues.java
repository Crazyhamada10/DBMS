package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetRowValues {

private static GetRowValues instance;
	
	private GetRowValues() {
		
	}
	
	public static GetRowValues getInstance() {
		if (instance == null) {
			instance = new GetRowValues();
		}
		return instance;
	}
	
	public ArrayList<String> getRowValues(ArrayList<String> betweenParentheses) throws SQLException {
		ArrayList<String> values = new ArrayList<String>();
		ArrayList<ArrayList<String>> splited = new ArrayList<ArrayList<String>>(SplitByComma.getInstance().splitByComma(betweenParentheses));
		for (ArrayList<String> arrayList : splited) {
			values.add(GetCellValue.getInstance().getCellValue(arrayList));
		}
		return values;
	}

}
