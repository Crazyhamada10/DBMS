package parser.helper;

import java.sql.SQLException;
import java.util.ArrayList;

import accessories.NameValue;

public class GetNameValue {

	private static GetNameValue instance;
	
	private GetNameValue() {
		
	}
	
	public static GetNameValue getInstance() {
		if (instance == null) {
			instance = new GetNameValue();
		}
		return instance;
	}

	public NameValue getNameValue(ArrayList<String> nameValueStrings) throws SQLException {
		NameValue nameValue = new NameValue();
		ArrayListNeededMethods.checkNonEmptiness(nameValueStrings);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(nameValueStrings));
		nameValue.setName(ArrayListNeededMethods.popFirst(nameValueStrings));
		ArrayListNeededMethods.checkNonEmptiness(nameValueStrings);
		StringNeededMethods.checkEqualChar(ArrayListNeededMethods.popFirst(nameValueStrings));
		ArrayListNeededMethods.checkNonEmptiness(nameValueStrings);
		nameValue.setValue(GetCellValue.getInstance().getCellValue(nameValueStrings));
		return nameValue;
	}
}
