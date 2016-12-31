package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.GetNameOrder;
import parser.helper.GetStrings;
import parser.helper.SplitByComma;
import parser.helper.StringNeededMethods;
import accessories.NameOrder;

public class OrderBy {
	
	private static OrderBy instance;

	private OrderBy() {
		
	}
	
	public static OrderBy getInstance() {
		if (instance == null) {
			instance = new OrderBy();
		}
		return instance; 
	}
	
	public ArrayList<NameOrder> order(ArrayList<String> parts) throws SQLException {
		ArrayListNeededMethods.checkNonEmptiness(parts);
		StringNeededMethods.checkByString(ArrayListNeededMethods.popFirst(parts));
		ArrayList<NameOrder> nameOrders = new ArrayList<NameOrder>();
		ArrayList<String> orderStrings = new ArrayList<String>(GetStrings.getInstance().getStrings(parts));
		ArrayList<ArrayList<String>> splited = new ArrayList<ArrayList<String>>(SplitByComma.getInstance().splitByComma(orderStrings));
		for (ArrayList<String> arrayList : splited) {
			nameOrders.add(GetNameOrder.getInstance().getNameOrder(arrayList));
		}
		return nameOrders;
	}
}
