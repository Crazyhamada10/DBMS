package command;

public class DropTable extends CreateDrop{

	private String tableName;
	
	public DropTable() {
		tableName = new String();
	}

	@Override
	public void setTableName(String name) {
		this.tableName = modifyName(name);
	}
	
	@Override
	public String getTableName() {
		return tableName;
	}
	
	@Override
	public String toString() {
		return new String(super.toString() + "TableName: " + getTableName() +  "\n");
	}
}
