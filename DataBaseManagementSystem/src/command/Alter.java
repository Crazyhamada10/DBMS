package command;

import accessories.StaticData.AlterEnum;

public abstract class Alter extends Command {
	
	private AlterEnum alterType;
	private String tableName;
	
	protected Alter() {
		super();
		tableName = new String();
	}

	@Override
	public AlterEnum getAlterType() {
		return alterType;
	}

	@Override
	public void setAlterType(AlterEnum alterType) {
		this.alterType = alterType;
	}

	@Override
	public String getTableName() {
		return tableName;
	}
	
	@Override
	public void setTableName(String name) {
		this.tableName = modifyName(name);
	}

	@Override
	public String toString() {
		return new String(super.toString() + "TableName: " + getTableName() + "\n" + "AlterType: " + getAlterType().name() + "\n");
	}
	
}
