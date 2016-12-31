package accessories;


public class NameType {

	private String columnName;
	private StaticData.DataTypes dataType;
	
	public NameType(String columnName, StaticData.DataTypes dataType) {
		this.columnName = modifyName(columnName);
		this.dataType = dataType;
	}

	public String getColumnName() {
		return columnName;
	}

	public StaticData.DataTypes getColumnType() {
		return dataType;
	}
	
	public int getColumnTypeInteger() {
		switch (dataType) {
		case DATE:
			return java.sql.Types.DATE;
		case FLOAT:
			return java.sql.Types.FLOAT;
		case INT:
			return java.sql.Types.INTEGER;
		case VARCHAR:
			return java.sql.Types.VARCHAR;
		}
		return 0;
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
				+ ", Type: " + getColumnType().name() + "\n");
	}
}
