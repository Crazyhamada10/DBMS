package command;

public class Use extends Command {

	private String dataBaseName;
	
	public Use() {
		super();
	}
	
	@Override
	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = modifyName(dataBaseName);
	}

	@Override
	public String getDataBaseName() {
		return dataBaseName;
	}
	
	@Override
	public String toString() {
		return new String(super.toString() + "DataBaseName: " + dataBaseName + "\n");
	}
}
