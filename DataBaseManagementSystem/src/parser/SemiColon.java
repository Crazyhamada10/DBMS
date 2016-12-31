package parser;

import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;

public class SemiColon {

	private static SemiColon instance;
	
	private SemiColon() {

	}

	public static SemiColon getInstance() {
		if (instance == null) {
			instance = new SemiColon();
		}
		return instance;
	}

	public void check(ArrayList<String> parts) {
		if (ArrayListNeededMethods.getLast(parts).equals(";")) {
			ArrayListNeededMethods.removeLast(parts);
		}
	}
	
}
