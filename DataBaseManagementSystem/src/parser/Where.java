package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.GetNameOperatorValue;
import parser.helper.GetStrings;
import parser.helper.InfixToPostfix;
import accessories.NameOperatorValue;
import accessories.StaticData.BooleanValues;

import command.ICommand;

public class Where {

	private static Where instance;
	private ArrayList<NameOperatorValue> nameOperatorValues;  
	private ArrayList <BooleanValues> booleanValues;
	
	
	private Where() {
		
	}
	
	public static Where getInstance() {
		if (instance == null) {
			instance = new Where();
		}
		return instance;
	}

	public void where (ICommand whereCommand, ArrayList<String> parts) throws SQLException {
		clear();
		ArrayList<String> whereStrings = new ArrayList<String>(GetStrings.getInstance().getStrings(parts));
		InfixToPostfix infixToPostfix = InfixToPostfix.getInstance();
		ArrayList<ArrayList<String>> splited = infixToPostfix.splitByAndOrNot(whereStrings);
		for (ArrayList<String> arrayList : splited) {
			nameOperatorValues.add(GetNameOperatorValue.getInstance().getNameOperatorValue(arrayList));
		}
		booleanValues = infixToPostfix.infixToPostfix();
		whereCommand.setNameOperatorValues(nameOperatorValues);
		whereCommand.setBooleanValues(booleanValues);
		infixToPostfix.tryExecution(getBooleans());
	}

	private ArrayList<Boolean> getBooleans() {
		ArrayList<Boolean> booleans = new ArrayList<Boolean>();
		for (int i = 0; i < nameOperatorValues.size(); i++) {
			booleans.add(false);
		}
		return booleans;
	}

	private void clear() {
		nameOperatorValues = new ArrayList<NameOperatorValue>();
		booleanValues = new ArrayList<BooleanValues>();
	}
}
