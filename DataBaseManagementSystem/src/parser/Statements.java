package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.ConvertStringtoParts;
import parser.helper.StringNeededMethods;
import accessories.SQLExceptions;
import accessories.StaticData;

import command.ICommand;

public class Statements {

	private static Statements instance;
	private static int SQLStatementMinLength = 2;

	private StaticData.CommandsTypes commandType;
	private ArrayList<String> parts;
	private ICommand toBeExecuted;
	
	private Statements () {

	}

	public static Statements getInstance() {
		if (instance == null) {
			instance = new Statements();
		}
		return instance;
	}
	
	public ICommand Check(String toParse) throws SQLException{
		clear();
		firstSyntaxChecks(toParse);
		commandType = StaticData.getCommandType(ArrayListNeededMethods.popFirst(parts));
		switchOnCommands();
		toBeExecuted.setCommandType(commandType);
		checkUnionCommand();
		ArrayListNeededMethods.checkEmptiness(parts);
		return toBeExecuted;
	}
	
	private void switchOnCommands() throws SQLException {
		switch (commandType) {
		case USE:
			use();
			break;
		case CREATE:
			create();
			break;
		case DROP:
			drop();
			break;
		case INSERT:
			insert();
			break;
		case SELECT:
			select();
			break;
		case UPDATE:
			update();
			break;
		case DELETE:
			delete();
			break;
		case ALTER:
			alter();
			break;
		default:
			SQLExceptions.throwUnknownCommand();
		}
	}
	
	private void clear() {
		toBeExecuted = null;
		parts = new ArrayList<String>();
	}
	
	private void firstSyntaxChecks(String toParse) throws SQLException {
		parts = new ArrayList<String>(ConvertStringtoParts.changeToParts(toParse));
		if (ArrayListNeededMethods.getSize(parts) < SQLStatementMinLength) {
			SQLExceptions.throwUnknownCommand();
		}
		SemiColon.getInstance().check(parts);
	}
	
	private void use() throws SQLException {
		toBeExecuted = Use.getInstance().check(parts);
	}
	
	private void create() throws SQLException {
		toBeExecuted = Create.getInstance().check(parts);
	}
	
	private void drop() throws SQLException {
		toBeExecuted = Drop.getInstance().check(parts);
	}
	
	private void insert() throws SQLException {
		toBeExecuted = Insert.getInstance().check(parts);
	}
	
	private void select() throws SQLException {
		toBeExecuted = Select.getInstance().check(parts);
	}
	
	private void update() throws SQLException {
		toBeExecuted = Update.getInstance().check(parts);
	}
	
	private void delete() throws SQLException {
		toBeExecuted = Delete.getInstance().check(parts);
	}

	private void alter() throws SQLException {
		toBeExecuted = Alter.getInstance().check(parts);
	}
	
	private void checkUnionCommand() throws SQLException {
		try {
			ArrayListNeededMethods.checkNonEmptiness(parts);
			if (commandType.equals(StaticData.CommandsTypes.SELECT) && !toBeExecuted.isOrdered() 
			 && StringNeededMethods.checkUnion(ArrayListNeededMethods.popFirst(parts))) {
				toBeExecuted = Union.getInstance().checkParts(toBeExecuted, parts);
			}
		} catch (SQLException e) {
			
		}
	}
}
