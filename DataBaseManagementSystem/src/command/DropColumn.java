package command;

public class DropColumn extends Alter {

	private String columnName;
	
	public DropColumn() {
		super();
		columnName = new String();
	}
	
	@Override
	public void setColumnName(String name) {
		this.columnName = modifyName(name);
	}
	
	@Override
	public String getColumnName() {
		return columnName;
	}

	@Override
	public String toString() {
		return new String(super.toString() + "ColumnName: " + getColumnName() +  "\n");
	}

}
