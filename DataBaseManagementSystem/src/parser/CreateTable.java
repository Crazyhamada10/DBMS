package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.CheckRepeatedColumnNames;
import parser.helper.GetBetweenParentheses;
import parser.helper.GetNameType;
import parser.helper.SplitByComma;
import parser.helper.SqlNameConstrains;
import accessories.NameType;
import command.ICommand;

public class CreateTable {

	private static CreateTable instance;
	private ICommand command;
	private ArrayList<NameType> nameTypes;
	
	private CreateTable() {
		
	}
	
	public static CreateTable getInstance() {
		if (instance == null) {
			instance = new CreateTable();
		}
		return instance;
	}

	public ICommand check (ArrayList<String> parts) throws SQLException {
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		command.setTableName(ArrayListNeededMethods.popFirst(parts));
		getNameTypes(parts);
		command.setColumnNameTypes(nameTypes);
		CheckRepeatedColumnNames.getInstance().check(command.getColumnNames());
		return command;
	}

	private void clear() {
		command = new command.CreateTable();
		nameTypes = new ArrayList<NameType>();
	}
	
	private void getNameTypes(ArrayList<String> parts) throws SQLException {
		ArrayList<String> betweenParentheses = GetBetweenParentheses.getInstance().getBetweenParentheses(parts);
		ArrayList<ArrayList<String>> nameTypeStrings = SplitByComma.getInstance().splitByComma(betweenParentheses);
		for (ArrayList<String> nameType : nameTypeStrings) {
			nameTypes.add(GetNameType.getInstance().getNameType(nameType));
		}
	}
	
}
