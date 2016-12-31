package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import accessories.StaticData;
import command.ICommand;

public class Create {
	
	private static Create instance;
	private ICommand createCommand;
	private StaticData.DataBaseTable dataBaseTable;

	private Create() {
		
	}

	public static Create getInstance() {
		if (instance == null) {
			instance = new Create();
		}
		return instance;
	}

	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		ArrayListNeededMethods.checkNonEmptiness(parts);
		dataBaseTable = StaticData.getDataBaseTable(ArrayListNeededMethods.popFirst(parts));
		switchOnCreate(parts);
		createCommand.setDataBaseTableType(dataBaseTable);
		return createCommand;
	}

	private void switchOnCreate(ArrayList<String> parts) throws SQLException {
		switch (dataBaseTable) {
		case DATABASE:
			createCommand = CreateDropDataBase.getInstance().check(parts);
			break;
		case TABLE:
			createCommand = CreateTable.getInstance().check(parts);		
			break;
		}
	}
	
	private void clear() {
		createCommand = null;
	}

}
