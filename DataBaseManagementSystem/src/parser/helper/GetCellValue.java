package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.SQLExceptions;

public class GetCellValue {

	private static GetCellValue instance;
	
	private GetCellValue() {
		
	}
	
	public static GetCellValue getInstance() {
		if (instance == null) {
			instance = new GetCellValue();
		}
		return instance;
	}
	
	public String getCellValue(ArrayList<String> cellStrings) throws SQLException {
		StringBuilder cellValue = new StringBuilder();
		boolean isString = true;
		if (StringNeededMethods.checkSingleQuote(ArrayListNeededMethods.getFirst(cellStrings))) {
			checkSingleQuoteAtBeginEnd(cellStrings);
		} else if (StringNeededMethods.checkDoubleQuotes(ArrayListNeededMethods.getFirst(cellStrings))) {
			checkDoubleQuotesAtBeginEnd(cellStrings);
		} else {
			isString = false;
		}
		for (String string : cellStrings) {
			cellValue.append(string);
		}
		if (!isString) {
			actAsNumber(cellValue.toString());
		}
		StringNeededMethods.checkNull(cellValue.toString());
		return cellValue.toString();
	}
	
	private void checkSingleQuoteAtBeginEnd(ArrayList<String> cellStrings) throws SQLException {
		ArrayListNeededMethods.removeFirst(cellStrings);
		if (StringNeededMethods.checkSingleQuote(ArrayListNeededMethods.getLast(cellStrings))) {
			ArrayListNeededMethods.removeLast(cellStrings);				
		} else {
			SQLExceptions.throwMissingSingleQuote();
		}
	}
	
	private void checkDoubleQuotesAtBeginEnd(ArrayList<String> cellStrings) throws SQLException {
		ArrayListNeededMethods.removeFirst(cellStrings);
		if (StringNeededMethods.checkDoubleQuotes(ArrayListNeededMethods.getLast(cellStrings))) {
			ArrayListNeededMethods.removeLast(cellStrings);				
		} else {
			SQLExceptions.throwMissingDoubleQuotes();
		}
	}
	
	private void actAsNumber(String number) throws SQLException {
		try {
			Float.parseFloat(number);
		} catch (Exception e) {
			SQLExceptions.throwMissingDoubleQuotes();
		}
	}
}
