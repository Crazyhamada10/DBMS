package command;

public class CreateDropDataBase extends CreateDrop{

	private String dataBaseName;
	
	public CreateDropDataBase() {
		super();
		dataBaseName = new String();
	}

	@Override
	public void setDataBaseName(String name) {
		this.dataBaseName = modifyName(name);
	}

	@Override
	public String getDataBaseName() {
		return dataBaseName;
	}

	@Override
	public String toString() {
		return new String(super.toString() + "DataBaseName: " + getDataBaseName() + "\n");
	}
	
}
