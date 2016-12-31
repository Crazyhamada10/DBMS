package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.SQLExceptions;

public class GetBetweenParentheses {

	private static GetBetweenParentheses instance;
	
	private GetBetweenParentheses() {
		
	}

	public static GetBetweenParentheses getInstance() {
		if (instance == null) {
			instance = new GetBetweenParentheses();
		}
		return instance;
	}
	
	public ArrayList<String> getBetweenParentheses(ArrayList<String> parts) throws SQLException {
		ArrayList<String> betweenParentheses = new ArrayList<String>();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		if (StringNeededMethods.checkOpenParentheses(ArrayListNeededMethods.getFirst(parts))) {
			ArrayListNeededMethods.removeFirst(parts);
			int countParts = 0;
			boolean singleQuote = false, doubleQuotes = false;
			boolean closedParentheses = false;
			for (String part : parts) {
				if (!doubleQuotes && StringNeededMethods.checkSingleQuote(part)) {
					singleQuote = !singleQuote;
				} else if (!singleQuote && StringNeededMethods.checkDoubleQuotes(part)) {
					doubleQuotes = !doubleQuotes;
				} else if (!singleQuote && !doubleQuotes && StringNeededMethods.checkClosedParentheses(part)) {
					closedParentheses = true;
					break;
				}
				countParts ++;
			}
			if (!closedParentheses) {
				SQLExceptions.throwMissingParentheses();
			}
			if (countParts == 0) {
				SQLExceptions.throwEmptyParentheses();
			}
			betweenParentheses = ArrayListNeededMethods.popSubList(parts, countParts);
			ArrayListNeededMethods.removeFirst(parts);
		} else {
			SQLExceptions.throwMissingParentheses();
		}
		return betweenParentheses;		
	}
}
