package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import accessories.StaticData;
import command.ICommand;

public class Drop {
	
	private static Drop instance;
	private ICommand dropCommand;
	private StaticData.DataBaseTable dataBaseTable;

	private Drop() {
		
	}

	public static Drop getInstance() {
		if (instance == null) {
			instance = new Drop();
		}
		return instance;
	}

	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		dataBaseTable = StaticData.getDataBaseTable(ArrayListNeededMethods.popFirst(parts));
		switchOnDrop(parts);
		dropCommand.setDataBaseTableType(dataBaseTable);
		return dropCommand;
	}

	private void switchOnDrop(ArrayList<String> parts) throws SQLException {
		switch (dataBaseTable) {
		case DATABASE:
			dropCommand = CreateDropDataBase.getInstance().check(parts);
			break;
		case TABLE:
			dropCommand = DropTable.getInstance().check(parts);		
			break;
		}
	}

	private void clear() {
		dropCommand = null;
	}

}
