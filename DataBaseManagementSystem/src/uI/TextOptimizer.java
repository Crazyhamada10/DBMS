package uI;

import java.util.ArrayList;

public class TextOptimizer {

	private int[] maxLength;
	private static TextOptimizer instance;	
	
	private TextOptimizer() {
	}

	public static TextOptimizer getInstance() {
		if (instance == null) {
			instance = new TextOptimizer();
		}
		return instance;
	}
	
	public void drawTable(ArrayList<String> colNames, ArrayList<ArrayList<Object>> table) {
		table.add(0, new ArrayList<Object>(colNames));
		mobilizeString(table);
		getLength(table);
		for (int i = 0; i < table.size(); i++) {
			if (i == 0)
				drawFLines();
			else
				drawLines();
			System.out.println();
			System.out.print("|");
			for (int j = 0; j < table.get(i).size(); j++) {
				System.out.print(table.get(i).get(j));
				drawSpaces(maxLength[j] - table.get(i).get(j).toString().length());
			}
			System.out.println();
		}
		drawFLines();
		System.out.println();
	}

	private void mobilizeString(ArrayList<ArrayList<Object>> table) {
		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < table.get(i).size(); j++) {
				table.get(i)
						.set(j, "   " + table.get(i).get(j).toString() + "   ");
			}
		}
	}

	private void getLength(ArrayList<ArrayList<Object>> table) {
		maxLength = new int[table.get(0).size()];
		for (int i = 0; i < table.size(); i++) {
			for (int j = 0; j < table.get(i).size(); j++) {
				maxLength[j] = Math.max(maxLength[j], table.get(i).get(j).toString()
						.length());
			}
		}
	}

	private void drawFLines() {
		for (int i = 0; i < maxLength.length; i++) {
			System.out.print("+");
			for (int j = 0; j < maxLength[i]; j++)
				System.out.print("-");
		}
		System.out.print("+");
	}

	private void drawLines() {
		for (int i = 0; i < maxLength.length; i++) {
			System.out.print("|");
			for (int j = 0; j < maxLength[i]; j++)
				System.out.print("-");
		}
		System.out.print("|");
	}

	private void drawSpaces(int it) {
		for (int i = 0; i < it; i++) {
			System.out.print(" ");
		}
		System.out.print("|");
	}

}