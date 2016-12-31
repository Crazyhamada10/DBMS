package parser.helper;

import java.sql.SQLException;

import accessories.SQLExceptions;
import accessories.StaticData.AttributeWords;


public class StringNeededMethods {

	private StringNeededMethods() {
		
	}
	
	public static boolean checkOpenParentheses(String part) {
		if(part.equals("(")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkUnion(String part) {
		if(part.equalsIgnoreCase("Union")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkAll(String part) {
		if(part.equalsIgnoreCase("All")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkFrom(String part) {
		if(part.equalsIgnoreCase("From")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkAsterisk(String part) {
		if(part.equals("*")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkClosedParentheses(String part) {
		if(part.equals(")")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkComma(String part) {
		if(part.equals(",")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkSingleQuote(String part) {
		if(part.equals("'")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkDoubleQuotes(String part) {
		if(part.equals("\"")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean checkWhere(String part) {
		if(part.equalsIgnoreCase("Where")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkOrder(String part) {
		if(part.equalsIgnoreCase("Order")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkDistinct(String part) {
		if(part.equalsIgnoreCase("Distinct")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkEqual(String part) {
		if(part.equalsIgnoreCase("=")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static void checkFromString(String part) throws SQLException{
		check2Strings(part, "From");
	}
	
	public static void checkTableString(String part) throws SQLException{
		check2Strings(part, "Table");
	}

	public static void checkColumnString(String part) throws SQLException {
		check2Strings(part, "Column");
	}
	
	public static void checkIntoString(String part) throws SQLException {
		check2Strings(part, "Into");
	}
	
	public static void checkValuesString(String part) throws SQLException {
		check2Strings(part, "Values");
	}

	public static void checkSetString(String part) throws SQLException {
		check2Strings(part, "Set");
	}
	
	public static void checkByString(String part) throws SQLException {
		check2Strings(part, "By");
	}
	
	public static void checkEqualChar(String part) throws SQLException {
		check2Strings(part, "=");
	}
	
	private static void check2Strings(String part, String value) throws SQLException {
		if(!part.equalsIgnoreCase(value)) {
			SQLExceptions.throwMissingWord(value);
		}
	}
	
	public static void checkNull(String part) throws SQLException {
		if(part.equalsIgnoreCase(AttributeWords.Null.name())) {
			SQLExceptions.throwNull();
		}		
	}
}
