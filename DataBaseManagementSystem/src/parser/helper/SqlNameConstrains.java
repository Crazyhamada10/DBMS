package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import accessories.SQLExceptions;

public class SqlNameConstrains {

	private static SqlNameConstrains instance;
	private static int nameMaxLength = 30;
	private static ArrayList<String> reservedWords = new ArrayList<>(
			Arrays.asList("create", "drop", "select", "update", "from", "insert", "values", "into", "delete", "table",
						  "database", "set", "use", "where", "asc", "desc", "or", "and", "order", "by", "distinct",
						  "alter", "other", "not", "column", "union"));

	private SqlNameConstrains() {

	}

	public static SqlNameConstrains getInstance() {
		if (instance == null) {
			instance = new SqlNameConstrains();
		}
		return instance;
	}

	public  void checkName(String realName) throws SQLException{
		String name = new String(realName.toLowerCase());
		if (reservedWords.contains(name)) {
			SQLExceptions.throwReservedWord(realName);
		}
		if (name.length() > nameMaxLength) {
			SQLExceptions.throwWordMaxLength();
		}
		if (!Character.isLetter(name.charAt(0))) {
			SQLExceptions.throwWordStartWithChar();			
		}
		for (int i = 0; i < name.length(); i++) {
			if (name.charAt(i) != '$' && name.charAt(i) != '_' && !Character.isLetter(name.charAt(i))
					&& !Character.isDigit(name.charAt(i))) {
				SQLExceptions.throwNotAppliedChar(name.charAt(i));
			}
		}
	}
	
}
