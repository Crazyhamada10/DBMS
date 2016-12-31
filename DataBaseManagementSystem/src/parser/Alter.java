package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.SqlNameConstrains;
import parser.helper.StringNeededMethods;
import accessories.StaticData;
import accessories.StaticData.AlterEnum;
import command.ICommand;

public class Alter {

	private static Alter instance;
	private ICommand alterCommand;
	private AlterEnum alterType;
	private String tableName;
	
	private Alter() {
		
	}

	public static Alter getInstance() {
		if (instance == null) {
			instance = new Alter();
		}
		return instance;
	}
	
	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		StringNeededMethods.checkTableString(ArrayListNeededMethods.popFirst(parts));
		ArrayListNeededMethods.checkNonEmptiness(parts);
		SqlNameConstrains.getInstance().checkName(ArrayListNeededMethods.getFirst(parts));
		tableName = new String(ArrayListNeededMethods.popFirst(parts));
		ArrayListNeededMethods.checkNonEmptiness(parts);
		alterType = StaticData.getAlterEnum(ArrayListNeededMethods.popFirst(parts));
		switchOnAlter(parts);
		alterCommand.setTableName(tableName);
		alterCommand.setAlterType(alterType);
		return alterCommand;
	}

	private void switchOnAlter(ArrayList<String> parts) throws SQLException {
		switch (alterType) {
		case ADD:
			alterCommand = AddColumn.getInstance().check(parts);
			break;
		case DROP:
			ArrayListNeededMethods.checkNonEmptiness(parts);
			StringNeededMethods.checkColumnString(ArrayListNeededMethods.popFirst(parts));
			alterCommand = DropColumn.getInstance().check(parts);		
			break;
		}
	}

	private void clear() {
		alterCommand = null;		
	}
}
