package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

public class GetStrings {

	private static GetStrings instance;
	
	private GetStrings() {

	}
	
	public static GetStrings getInstance() {
		if (instance == null) {
			instance = new GetStrings();
		}
		return instance;
	}
	
	public ArrayList<String> getStrings(ArrayList<String> parts) throws SQLException {
		boolean singleQuote = false, doubleQuote = false;
		ArrayList<String> whereStrings = new ArrayList<String>();
		int countParts = 0;
		for (String part : parts) {
			if (!doubleQuote && StringNeededMethods.checkSingleQuote(part)) {
				singleQuote = !singleQuote;
			} else if (!singleQuote && StringNeededMethods.checkDoubleQuotes(part)) {
				doubleQuote = !doubleQuote;
			} else if (!singleQuote && !doubleQuote && checkCertainWords(part)) {
				break;
			}
			countParts ++;
		}
		whereStrings = ArrayListNeededMethods.popSubList(parts, countParts);
		ArrayListNeededMethods.checkNonEmptiness(whereStrings);
		return whereStrings;
	}
	
	private boolean checkCertainWords(String part) {
		return StringNeededMethods.checkOrder(part) || StringNeededMethods.checkWhere(part) 
			|| StringNeededMethods.checkFrom(part) || StringNeededMethods.checkUnion(part);
	}
}
