package command;

import accessories.StaticData.DataBaseTable;

public abstract class CreateDrop extends Command {

	private DataBaseTable dataBaseTable;

	protected CreateDrop() {
		super();
	}

	@Override
	public void setDataBaseTableType(DataBaseTable dataBaseTable) {
		this.dataBaseTable = dataBaseTable;
	}

	@Override
	public DataBaseTable getDataBaseTableType() {
		return dataBaseTable;
	}
	
	@Override
	public String toString() {
		return new String(super.toString() + "DataBaseTable: " + getDataBaseTableType().name() + "\n");
	}
}
