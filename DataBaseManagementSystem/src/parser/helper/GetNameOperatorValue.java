package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.NameOperatorValue;
import accessories.SQLExceptions;

public class GetNameOperatorValue {

	private static GetNameOperatorValue instance;
	
	private GetNameOperatorValue() {
		
	}
	
	public static GetNameOperatorValue getInstance() {
		if (instance == null) {
			instance = new GetNameOperatorValue();
		}
		return instance;
	}

	public NameOperatorValue getNameOperatorValue(ArrayList<String> nameOperatorValueStrings) throws SQLException {
		NameOperatorValue nameOperatorValue = new NameOperatorValue();
		ArrayListNeededMethods.checkNonEmptiness(nameOperatorValueStrings);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(nameOperatorValueStrings));
		nameOperatorValue.setName(ArrayListNeededMethods.popFirst(nameOperatorValueStrings));
		ArrayListNeededMethods.checkNonEmptiness(nameOperatorValueStrings);		
		nameOperatorValue.setOperator(checkOperator(nameOperatorValueStrings));
		ArrayListNeededMethods.checkNonEmptiness(nameOperatorValueStrings);
		nameOperatorValue.setValue(GetCellValue.getInstance().getCellValue(nameOperatorValueStrings));
		return nameOperatorValue;
	}
	
	private String checkOperator(ArrayList<String> nameOperatorValueStrings) throws SQLException {
		String operator = null;
		String firstOperatorPart = ArrayListNeededMethods.popFirst(nameOperatorValueStrings);
		ArrayListNeededMethods.checkNonEmptiness(nameOperatorValueStrings);
		String secondOperatorPart = ArrayListNeededMethods.getFirst(nameOperatorValueStrings);
		String twoOperatorParts = new String(firstOperatorPart + secondOperatorPart);
		if (checkTwoOperatorParts(twoOperatorParts)) {
			if (twoOperatorParts.equals("<>")) {
				twoOperatorParts = new String("!=");
			}
			ArrayListNeededMethods.removeFirst(nameOperatorValueStrings);
			operator = new String(twoOperatorParts);
		} else if (checkOneOperator(firstOperatorPart)) {
			operator = new String(firstOperatorPart);
		} else {
			SQLExceptions.throwMissingOperator();
		}
		return operator;
	}
	
	private boolean checkTwoOperatorParts(String operator) {
		if (operator.equals("<>") || operator.equals("!=") 
		 || operator.equals("<=") || operator.equals(">=")) {
			return true;
		} else {
			return false;
		}
	}
	
	private boolean checkOneOperator(String operator) {
		if (operator.equals("<") || operator.equals(">") 
		 || operator.equals("=")) {
			return true;
		} else {
			return false;
		}
	}
}
