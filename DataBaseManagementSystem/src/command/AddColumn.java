package command;

import accessories.NameType;

public class AddColumn extends Alter {

	private NameType nameType;
	
	public AddColumn() {
		super();
	}

	@Override
	public NameType getNameType() {
		return nameType;
	}

	@Override
	public void setNameType(NameType nameType) {
		this.nameType = nameType;
	}
	
	@Override
	public int getNameTypeInteger() {
		return nameType.getColumnTypeInteger();
	}
	
	@Override
	public String toString() {
		return new String (super.toString() + "NameType: " + nameType + "\n");
	}

}
