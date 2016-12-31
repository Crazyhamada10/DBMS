package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.NameValue;

public class GetNameValues {
private static GetNameValues instance;
	
	private GetNameValues() {
		
	}
	
	public static GetNameValues getInstance() {
		if (instance == null) {
			instance = new GetNameValues();
		}
		return instance;
	}
	
	public ArrayList<NameValue> getNameValues(ArrayList<String> parts) throws SQLException {
		ArrayList<NameValue> nameValues = new ArrayList<NameValue>();
		ArrayList<String> setStrings = new ArrayList<String>(GetStrings.getInstance().getStrings(parts));
		ArrayList<ArrayList<String>> splited = new ArrayList<ArrayList<String>>(
				SplitByComma.getInstance().splitByComma(setStrings));
		for (ArrayList<String> partSplit : splited) {
			nameValues.add(GetNameValue.getInstance().getNameValue(partSplit));
		}
		return nameValues;
	}
}
