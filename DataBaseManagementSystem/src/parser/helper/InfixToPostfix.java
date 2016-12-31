package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Stack;

import accessories.SQLExceptions;
import accessories.StaticData;
import accessories.StaticData.BooleanValues;

public class InfixToPostfix {

	private static InfixToPostfix instance;
	private ArrayList<String> beforeInfixToPostfix;
	private ArrayList<BooleanValues> postfix;

	private InfixToPostfix() {
		
	}
	
	public static InfixToPostfix getInstance() {
		if (instance == null) {
			instance = new InfixToPostfix();
		}
		return instance;
	}
	
	public ArrayList<BooleanValues> infixToPostfix() throws SQLException {
		postfix = new ArrayList<StaticData.BooleanValues>();
		Stack<String> precedenceStack = new Stack<String>();
		for (String string : beforeInfixToPostfix) {
			if (StringNeededMethods.checkOpenParentheses(string)) {
				precedenceStack.push(string);
			} else if (StringNeededMethods.checkClosedParentheses(string)) {
				toDoAfterFoundingClosedParentheses(string, precedenceStack);
			} else {
				switchOnBooleanValues(string, precedenceStack);
			}
		}
		while (!precedenceStack.empty()) {
			if (StringNeededMethods.checkOpenParentheses(precedenceStack.peek())) {
				SQLExceptions.throwMissingParentheses();
			}
			postfix.add(StaticData.getBooleanValue(precedenceStack.pop()));
		}
		return postfix;
	}
	
	private void checkEmptyParentheses() throws SQLException {
		for (int i = 0; i < ArrayListNeededMethods.getSize(beforeInfixToPostfix) - 1; i ++) {
			if (StringNeededMethods.checkOpenParentheses(ArrayListNeededMethods.getIndex(beforeInfixToPostfix, i)) 
			 && StringNeededMethods.checkClosedParentheses(ArrayListNeededMethods.getIndex(beforeInfixToPostfix, i + 1))) {
				SQLExceptions.throwEmptyParentheses();
			}
		}
	}

	private void switchOnBooleanValues(String string, Stack<String> precedenceStack) throws SQLException {
		BooleanValues booleanValue = StaticData.getBooleanValue(string);
		switch (booleanValue) {
		case OTHER:
			postfix.add(booleanValue);
			break;
		default:
			checkPrecedence(booleanValue, precedenceStack);
			break;
		}
	}
	
	private void checkPrecedence(BooleanValues booleanValue, Stack<String> precedenceStack) throws SQLException {
		while (!precedenceStack.empty() 
			&& !StringNeededMethods.checkOpenParentheses(precedenceStack.peek())
			&& precedence(booleanValue) <= precedence(StaticData.getBooleanValue(precedenceStack.peek()))) {
			postfix.add(StaticData.getBooleanValue(precedenceStack.pop()));
		}
		precedenceStack.push(booleanValue.name());
	}
	
	private int precedence(BooleanValues booleanValue) {
		int precedenceValue = 0;
		if (booleanValue.equals(BooleanValues.NOT)) {
			precedenceValue ++;
		}
		return precedenceValue;
	}

	private void toDoAfterFoundingClosedParentheses(String string, Stack<String> precedenceStack) throws SQLException {
		while (!precedenceStack.empty() && !StringNeededMethods.checkOpenParentheses(precedenceStack.peek())) {
			postfix.add(StaticData.getBooleanValue(precedenceStack.pop()));
		}
		if (precedenceStack.empty()) {
			SQLExceptions.throwUnknownCommand();
		}
		precedenceStack.pop();
	}
	
	public ArrayList<ArrayList<String>> splitByAndOrNot(ArrayList<String> whereStrings) throws SQLException {
		beforeInfixToPostfix = new ArrayList<String>();
		ArrayList<ArrayList<String>> splited = new ArrayList<ArrayList<String>>();
		String part;
		boolean singleQuote = false, doubleQuotes = false;
		for (int i = 0; i < ArrayListNeededMethods.getSize(whereStrings); i++) {
			part = new String(ArrayListNeededMethods.getIndex(whereStrings, i));
			if (!doubleQuotes && StringNeededMethods.checkSingleQuote(part)) {
				singleQuote = !singleQuote;
			} else if (!singleQuote && StringNeededMethods.checkDoubleQuotes(part)) {
				doubleQuotes = !doubleQuotes;
			}
			if (!singleQuote && !doubleQuotes && checkEndnessNameOperatorValue(part)) {
				if (i > 0) {
					splited.add(ArrayListNeededMethods.popSubList(whereStrings, i));
					beforeInfixToPostfix.add(StaticData.BooleanValues.OTHER.name());
				}
				beforeInfixToPostfix.add(ArrayListNeededMethods.popFirst(whereStrings));
				i = -1;
			}
		}
		ArrayList<String> temp = ArrayListNeededMethods.popSubList(whereStrings, ArrayListNeededMethods.getSize(whereStrings));
		if (temp.size() > 0) {
			splited.add(temp);
			beforeInfixToPostfix.add(StaticData.BooleanValues.OTHER.name());
		}
		checkNotValidation();
		checkEmptyParentheses();
		return splited;
	}

	private void checkNotValidation() throws SQLException {
		String string =  ArrayListNeededMethods.getLast(beforeInfixToPostfix);
		if (string.equalsIgnoreCase(StaticData.BooleanValues.NOT.name())) {
			SQLExceptions.throwUnknownCommand();
		}
		for (int i = 0; i < ArrayListNeededMethods.getSize(beforeInfixToPostfix) - 1; i ++) {
			if (ArrayListNeededMethods.getIndex(beforeInfixToPostfix, i).equalsIgnoreCase(StaticData.BooleanValues.NOT.name())) {
				string = new String(ArrayListNeededMethods.getIndex(beforeInfixToPostfix, i + 1));
				if (!(StringNeededMethods.checkOpenParentheses(string) || string.equalsIgnoreCase(StaticData.BooleanValues.OTHER.name()))) {
					SQLExceptions.throwUnknownCommand();
				}
			}
		}
	}
	
	private boolean checkEndnessNameOperatorValue(String current) {
		if (StringNeededMethods.checkOpenParentheses(current) 
		 || StringNeededMethods.checkClosedParentheses(current)
		 || checkBooleanType(current)) {
			return true;
		}
		return false;
	}
	
	private boolean checkBooleanType(String current) {
		try {
			StaticData.getBooleanValue(current);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean tryExecution(ArrayList<Boolean> booleans) throws SQLException {
		Stack<Boolean> values = new Stack<Boolean>();
		int index = 0;
		for (BooleanValues booleanValue : postfix) {
			switch (booleanValue) {
			case NOT:
				notExecution(values);
				break;
			case AND:
				andExecution(values);
				break;
			case OR:
				orExecution(values);
				break;
			case OTHER:
				values.push(booleans.get(index));
				index++;
				break;
			}
		}
		if (values.size() > 1) {
			SQLExceptions.throwUnknownCommand();
		}
		return values.pop();
	}

	private void notExecution(Stack<Boolean> values) throws SQLException {
		if (values.size() < 1) {
			SQLExceptions.throwUnknownCommand();
		}
		boolean temp = values.pop();
		values.push(!temp);
	}
	
	private void andExecution(Stack<Boolean> values) throws SQLException {
		if (values.size() < 2) {
			SQLExceptions.throwUnknownCommand();
		}
		boolean temp1 = values.pop();
		boolean temp2 = values.pop();
		values.push(temp1 && temp2);
	}
	
	private void orExecution(Stack<Boolean> values) throws SQLException {
		if (values.size() < 2) {
			SQLExceptions.throwUnknownCommand();
		}
		boolean temp1 = values.pop();
		boolean temp2 = values.pop();
		values.push(temp1 || temp2);
	}

}
