package parser;

import java.sql.SQLException;
import java.util.ArrayList;

import parser.helper.GetNameType;
import command.ICommand;

public class AddColumn {

	private static AddColumn instance;
	private ICommand addColumnCommand;

	private AddColumn() {
		
	}

	public static AddColumn getInstance() {
		if (instance == null) {
			instance = new AddColumn();
		}
		return instance;
	}

	public ICommand check(ArrayList<String> parts) throws SQLException{
		clear();
		addColumnCommand.setNameType(GetNameType.getInstance().getNameType(parts));
		return addColumnCommand;
	}

	private void clear() {
		addColumnCommand = new command.AddColumn();
	}

}
