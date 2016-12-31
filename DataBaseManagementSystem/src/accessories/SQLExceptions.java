package accessories;

import java.sql.SQLException;

import log4j.Log4j;

public class SQLExceptions {

	private SQLExceptions() {
		
	}
	
	public static void throwNull() throws SQLException {
		throw new SQLException(logtoFile(new String("Values of cells can't be insert to null.")));
	}

	public static void closedStatement() throws SQLException {
		throw new SQLException(logtoFile("Statement is closed"));
	}
	
	
	
	public static void throwUnknownCommand() throws SQLException {
		throw new SQLException(logtoFile(new String("Unknown Command.")));
	}
	
	public static void throwUnknownOrder() throws SQLException {
		throw new SQLException(logtoFile(new String("Unknown Order type.")));
	}

	public static void throwUnknownFile() throws SQLException {
		throw new SQLException(logtoFile(new String("Unknown File type.")));
	}
	
	public static void throwDifferentNumberColumns() throws SQLException {
		throw new SQLException(logtoFile(new String("Number of columns in first select is not equal to that in the second one.")));
	}
	
	public static void throwNotCompitableColumnNumbers() throws SQLException {
		throw new SQLException(logtoFile(new String("Number of Column Names is not equal to Number of Row Values.")));
	}
	
	public static void throwMissingSemiColon() throws SQLException {
		throw new SQLException(logtoFile(new String("Missing SemiColon.")));
	}
	
	public static void throwMissingParentheses() throws SQLException {
		throw new SQLException(logtoFile(new String("Missing Parentheses.")));
	}
	
	public static void throwEmptyParentheses() throws SQLException {
		throw new SQLException(logtoFile(new String("Empty Parentheses.")));
	}

	public static void throwReservedWord(String word) throws SQLException {
		throw new SQLException(logtoFile(new String(word + " is a reserved word.")));
	}

	public static void throwWordMaxLength() throws SQLException {
		throw new SQLException(logtoFile(new String("Maximum name length is 30 characters.")));
	}
	
	public static void throwWordStartWithChar() throws SQLException {
		throw new SQLException(logtoFile(new String("Names must begin with a character.")));
	}

	public static void throwNotAppliedChar(char character) throws SQLException {
		throw new SQLException(logtoFile(new String("Names can't contain " + character + ".")));
	}
	
	public static void throwUnknownDataType() throws SQLException {
		throw new SQLException(logtoFile(new String("Unknown Data Type.")));
	}
	
	public static void throwUnknownDataBaseTable() throws SQLException {
		throw new SQLException(logtoFile(new String("Unknown Drop or Create identifer.")));
	}

	public static void throwUnknownAlter() throws SQLException {
		throw new SQLException(logtoFile(new String("Unknown Alter identifer.")));
	}

	public static void throwMissingSingleQuote() throws SQLException {
		throw new SQLException(logtoFile(new String("Missing single quote.")));	
	}
	
	public static void throwMissingOperator() throws SQLException {
		throw new SQLException(logtoFile(new String("Missing Operator.")));	
	}
	
	public static void throwMissingDoubleQuotes() throws SQLException {
		throw new SQLException(logtoFile(new String("Missing double quotes.")));	
	}
	
	public static void throwMissingWord(String missed) throws SQLException {
		throw new SQLException(logtoFile(new String(missed + " word is Missing.")));	
	}
	
	public static void throwRepeatedColumnNames() throws SQLException {
		throw new SQLException(logtoFile(new String("Repeated column Names.")));	
	}
	
	public static void throwOrderColumnNotFound() throws SQLException {
		throw new SQLException(logtoFile(new String("Can't order by a column that is not selected.")));
	}

	public static void createNameError() throws SQLException {
		throw new SQLException(logtoFile("please give name to the file you request to create"));
	}

	public static void deleteNameError() throws SQLException {
		throw new SQLException(logtoFile("please give name to the file you request to delete"));
	}

	public static void uptdateNameError() throws SQLException {
		throw new SQLException(logtoFile("please give name to the file you request to update"));
	}

	public static void unfoundDataBase() throws SQLException {
		throw new SQLException(logtoFile("DataBase file does not exist"));
	}

	public static void unfoundFile() throws SQLException {
		throw new SQLException(logtoFile("file does not exists"));
	}

	public static void unfoundTable() throws SQLException {
		throw new SQLException(logtoFile("table file does not exist"));
	}

	public static void undefinedProtocol() throws SQLException {
		throw new SQLException(logtoFile("unknown protocol"));
	}

	public static void unusedDataBase() throws SQLException {
		throw new SQLException(logtoFile("No database is used"));
	}

	public static void unsupportedDataType() throws SQLException {
		throw new SQLException(logtoFile("unsupported data type"));
	}
	
	public static void repeatedNames() throws SQLException {
		throw new SQLException(logtoFile("Duplicate coloumn names"));
	}
	
	public static void dataIncosistencyError() throws SQLException {
		throw new SQLException(logtoFile("Data inconsistent with table"));
	}
	
	public static void incompitableDataType() throws SQLException {
		throw new SQLException(logtoFile("Data types inconsistent with table"));
	}
	
	public static void unknownColName() throws SQLException {
		throw new SQLException(logtoFile("coloumn name is not found"));
	}
	
	public static void pathError() throws SQLException {
		throw new SQLException(logtoFile("errors with retriving directory of program"));
	}
	
	public static void unknownOperator() throws SQLException {
		throw new SQLException(logtoFile("unknown operator in postfix"));
	}
	
	public static void wrongCommand() throws SQLException {
		throw new SQLException(logtoFile("this command does not return any thing"));
	}
	
	public static void wrongInfixPostFix() throws SQLException {
		throw new SQLException(logtoFile("Wrong Expression"));
	}
	
	public static void missingNameFile() throws SQLException {
		throw new SQLException(logtoFile("please give name to the dtd file you request to create!!!"));
	}
	
	public static void missingDataBaseName() throws SQLException {
		throw new SQLException(logtoFile("please give name to the file you request to delete!!!"));
	}

	public static void canNotReadFile() throws SQLException {
		throw new SQLException(logtoFile("File can't be read."));
	}
	
	public static void fileNotFound() throws SQLException {
		throw new SQLException(logtoFile("File can't be found."));
	}
	
	public static void canNotParseFile() throws SQLException {
		throw new SQLException(logtoFile("File can't be parsed."));
	}
	
	public static void throwExistingTable() throws SQLException {
		throw new SQLException(logtoFile("Existing Table."));
	}
	
	public static void canNotWriteFile() throws SQLException {
		throw new SQLException(logtoFile("File canot be written."));
	}

	private static String logtoFile(String string) {
		Log4j.getInstance().error(string);
		return string;
	}
	
}
