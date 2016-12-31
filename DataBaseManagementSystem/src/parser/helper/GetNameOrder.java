package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.NameOrder;
import accessories.StaticData;
import accessories.StaticData.Order;

public class GetNameOrder {

	private static GetNameOrder instance;
	
	private GetNameOrder() {
		
	}
	
	public static GetNameOrder getInstance() {
		if (instance == null) {
			instance = new GetNameOrder();
		}
		return instance;
	}
	
	public NameOrder getNameOrder(ArrayList<String> nameOrderString) throws SQLException {
		NameOrder nameOrder = new NameOrder();
		ArrayListNeededMethods.checkNonEmptiness(nameOrderString);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(nameOrderString));
		nameOrder.setColumnName(ArrayListNeededMethods.popFirst(nameOrderString));
		nameOrder.setOrder(checkOrder(nameOrderString));
		return nameOrder;
	}
	
	private Order checkOrder(ArrayList<String> orderString) throws SQLException {
		Order order = Order.ASC;
		try {
			ArrayListNeededMethods.checkEmptiness(orderString);
		} catch (Exception e) {
			String orderName = ArrayListNeededMethods.popFirst(orderString);
			order = StaticData.getOrder(orderName);
			ArrayListNeededMethods.checkEmptiness(orderString);
		}
		return order;
	}
}
