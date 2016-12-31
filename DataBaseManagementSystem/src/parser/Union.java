package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.ArrayListNeededMethods;
import parser.helper.StringNeededMethods;
import accessories.SQLExceptions;
import accessories.StaticData;

import command.ICommand;

public class Union {

	private static Union instance;
	private ICommand unionCommand;
	
	public static Union getInstance() {
		if (instance == null) {
			instance = new Union();
		}
		return instance;
	}
	
	public ICommand checkParts(ICommand selectOne, ArrayList<String> parts) throws SQLException {
		clear();
		unionCommand.setCommandType(StaticData.CommandsTypes.UNION);
		unionCommand.setSelectOne(selectOne);
		unionCommand.getSelectOne().setCommandType(StaticData.CommandsTypes.SELECT);
		ArrayListNeededMethods.checkNonEmptiness(parts);
		if (StringNeededMethods.checkAll(ArrayListNeededMethods.getFirst(parts))) {
			ArrayListNeededMethods.removeFirst(parts);
			unionCommand.setDistinct(false);
		}
		unionCommand.setSelectTwo(getSecondSelect(parts));
		unionCommand.getSelectTwo().setCommandType(StaticData.CommandsTypes.SELECT);
		checkColumnNamesSize();
		return unionCommand;
	}
	
	private void clear() {
		unionCommand = new command.Union();
	}

	private ICommand getSecondSelect(ArrayList<String> parts) throws SQLException {
		if (!StaticData.getCommandType(ArrayListNeededMethods.popFirst(parts)).equals(StaticData.CommandsTypes.SELECT)) {
			SQLExceptions.throwUnknownCommand();
		}
		return Select.getInstance().check(parts);
	}
	
	private void checkColumnNamesSize() throws SQLException {
		if (!(unionCommand.getSelectOne().isAllSelected() || unionCommand.getSelectTwo().isAllSelected())) {
			if (ArrayListNeededMethods.getSize(unionCommand.getSelectOne().getColumnNames())
					!= ArrayListNeededMethods.getSize(unionCommand.getSelectTwo().getColumnNames())) {
				SQLExceptions.throwDifferentNumberColumns();
			}
		}
	}

}
