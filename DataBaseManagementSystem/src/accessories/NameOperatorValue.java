package accessories;


public class NameOperatorValue extends NameValue{
	
	private String operator;

	public NameOperatorValue() {
		super();
		operator = new String();

	}

	public void setOperator(String operator) {
		this.operator = operator;
	}
	
	public String getOperator() {
		return this.operator;
	}
	
	@Override
	public String toString() {
		return new String ("Name: " + getName() + ", Value: " + getValue() + ", Operator: " + getOperator() + "\n");
	}
	
	public boolean check(int compare) {
		switch (getOperator()) {
		case "=":
			if (compare == 0) {
				return true;
			}
			break;
		case "!=":
			if (compare != 0) {
				return true;
			}
			break;		
		case "<":
			if (compare < 0) {
				return true;
			}
			break;
		case ">":
			if (compare > 0) {
				return true;
			}
			break;
		case "<=":
			if (compare <= 0) {
				return true;
			}
			break;
		case ">=":
			if (compare >= 0) {
				return true;
			}
			break;
		}
		return false;
	}
	
	
}
