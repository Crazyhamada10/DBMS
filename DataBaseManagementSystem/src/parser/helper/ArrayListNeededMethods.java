package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

public class ArrayListNeededMethods {

	private ArrayListNeededMethods() {
		
	}
	
	public static String getFirst(ArrayList<String> parts) {
		return parts.get(0);
	}
	
	public static void removeFirst(ArrayList<String> parts) {
		parts.remove(0);
	}

	public static String popFirst(ArrayList<String> parts) {
		String first = getFirst(parts);
		removeFirst(parts);
		return first;
	}
	
	public static String getLast(ArrayList<String> parts) {
		return parts.get(getSize(parts)-1);
	}
	
	public static void removeLast(ArrayList<String> parts) {
		parts.remove(getSize(parts)-1);
	}

	public static String popLast(ArrayList<String> parts) {
		String last = getLast(parts);
		removeLast(parts);
		return last;
	}
	
	public static String getIndex(ArrayList<String> parts, int index) {
		return parts.get(index);
	}
	
	public static void removeIndex(ArrayList<String> parts, int index) {
		parts.remove(index);
	}

	public static String popIndex(ArrayList<String> parts, int index) {
		String indexPart = getIndex(parts, index);
		removeIndex(parts, index);
		return indexPart;
	}
	
	public static int getSize(ArrayList<String> parts) {
		return parts.size();
	}
	
	public static void checkEmptiness(ArrayList<String> parts) throws SQLException {
		if (!parts.isEmpty()) {
			accessories.SQLExceptions.throwUnknownCommand();
		}
	}
	
	public static void checkNonEmptiness(ArrayList<String> parts) throws SQLException {
		if (parts.isEmpty()) {
			accessories.SQLExceptions.throwUnknownCommand();
		}
	}
	
	public static ArrayList<String> popSubList(ArrayList<String> parts, int index) {
		ArrayList<String> subList = new ArrayList <String> (parts.subList(0, index));
		for (int i = 0; i < index; i++) {
			removeFirst(parts);
		}
		return subList;
	}

}
