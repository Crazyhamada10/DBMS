package accessories;

public class NameValue {

	private String value;
	private String name;
	
	public NameValue() {
		name = new String();
		value = new String();
	}
	
	public void setName(String name) {
		this.name = modifyName(name);
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
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
		return new String ("Name: " + getName() + ", Value: " + getValue() + "\n");
	}

}
