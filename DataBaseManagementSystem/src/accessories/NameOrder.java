package accessories;

import accessories.StaticData.Order;


public class NameOrder {

	private String columnName;
	private Order order;
	
	public NameOrder() {
		columnName = new String();
	}

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = modifyName(columnName);
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
	
	private String modifyName(String name) {
		if (name.length() > 1) {
			name = new String(name.toLowerCase());
			String firstLetter = name.substring(0, 1).toUpperCase();
			String modified = new String(firstLetter + name.substring(1));
			return modified;
		} else {
			return name.toUpperCase();
		}
	}
	
	@Override
	public String toString() {
		return new String ("ColumnName: " + getColumnName() 
				+ ", Order: " + getOrder().name() + "\n");
	}
}
