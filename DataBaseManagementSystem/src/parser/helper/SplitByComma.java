package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

public class SplitByComma {
	
	private static SplitByComma instance;
	
	private SplitByComma() {
		
	}
	
	public static SplitByComma getInstance() {
		if (instance == null) {
			instance = new SplitByComma();
		}
		return instance;
	}

	public ArrayList<ArrayList<String>> splitByComma(ArrayList<String> betweenParentheses) throws SQLException {
		ArrayList<ArrayList<String>> splited = new ArrayList<ArrayList<String>>();
		String part;
		boolean singleQuote = false, doubleQuotes = false;
		for (int i=0; i < ArrayListNeededMethods.getSize(betweenParentheses); i++) {
			part = new String(ArrayListNeededMethods.getIndex(betweenParentheses, i));
			if (!doubleQuotes && StringNeededMethods.checkSingleQuote(part)) {
				singleQuote = !singleQuote;
			} else if (!singleQuote && StringNeededMethods.checkDoubleQuotes(part)) {
				doubleQuotes = !doubleQuotes;
			}
			if (!singleQuote && !doubleQuotes && StringNeededMethods.checkComma(part)) {
				splited.add(ArrayListNeededMethods.popSubList(betweenParentheses, i));
				ArrayListNeededMethods.removeFirst(betweenParentheses);
				i = -1;
			}
		}
		ArrayListNeededMethods.checkNonEmptiness(betweenParentheses);
		splited.add(ArrayListNeededMethods.popSubList(betweenParentheses, betweenParentheses.size()));
		return splited;
	}
}
