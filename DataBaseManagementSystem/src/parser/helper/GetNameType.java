package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.NameType;
import accessories.SQLExceptions;
import accessories.StaticData;

public class GetNameType {

	private static GetNameType instance;
	
	private GetNameType() {
		
	}
	
	public static GetNameType getInstance() {
		if (instance == null) {
			instance = new GetNameType();
		}
		return instance;
	}
	
	public NameType getNameType(ArrayList<String> nameType) throws SQLException {
		if (ArrayListNeededMethods.getSize(nameType) != 2) {
			SQLExceptions.throwUnknownCommand();
		}
		String name = ArrayListNeededMethods.popFirst(nameType);
		SqlNameConstrains.getInstance().checkName(name);
		NameType constructNameType = new NameType(name,
				StaticData.getDataType(ArrayListNeededMethods.popFirst(nameType)));
		return constructNameType;
	}
	
}
